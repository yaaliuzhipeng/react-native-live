//
//  LiveManager.m
//
//  Created by lzp on 2020/3/3.
//  Copyright © 2020 Facebook. All rights reserved.
//

#import "LiveManager.h"
#import <React/RCTLog.h>

@implementation LiveManager

static LiveManager *_instance = nil;

+(instancetype) shared
{
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        if(_instance == nil){
            _instance = [[super allocWithZone:NULL] init];
        }
    });
    return _instance;
}

+(instancetype)allocWithZone:(struct _NSZone *)zone
{
    return [LiveManager shared];
}

#pragma mark 配置直播授权
- (void)setLicence:(NSString *)url andLicenceKey:(NSString *)key
{
    if([url compare:@""] == NSOrderedSame || [key compare:@""] == NSOrderedSame){
        RCTLogError(@"腾讯直播License地址(URL)或者秘钥(Key)为空!");
    }else{
        [TXLiveBase setLicenceURL:url key:key];
        NSLog(@"直播：配置的url 和 key 为 %@ , %@",url,key);
    }
}

#pragma mark 开始播放（拉流）
- (void)startPull:(NSString *)url withLiveType:(int)livetype
{
    if(self._player == nil){
        RCTLogError(@"还未创建直播View，请确保组件树中已经存在直播view");
    }else{
        
        if(self._pull_delegate == nil){
            self._pull_delegate = [LivePullListener new];
            self._player.delegate = self._pull_delegate;
        }
        
        switch (livetype) {
            case 0:
                NSLog(@"直播：准备拉流，协议类型为 %d 拉流地址 %@",livetype,url);
                // RTMP 协议类型直播
                [self._player startPlay:url type:PLAY_TYPE_LIVE_RTMP];
                break;
            case 1:
                NSLog(@"直播：准备拉流，协议类型为 %d 拉流地址 %@",livetype,url);
                // FLV 协议类型直播
                [self._player startPlay:url type:PLAY_TYPE_LIVE_FLV];
                break;
            default:
                RCTLogError(@"参数不匹配！类型只有 RTMP 和 FLV ");
                break;
        }
    }
}

#pragma mark 停止播放 (拉流)
- (void)stopPull
{
    
    dispatch_async(dispatch_get_main_queue(), ^{
        NSLog(@"直播：准备停止播放");
        [self._player stopPlay];
        [self._player removeVideoWidget];
        self._player = nil;
        self._pull_delegate = nil;
    });
}

#pragma mark 推流开启预览
-(void)preview
{
    if(self._pusher != nil){
        NSLog(@"pusher 不为空");
        if(self._pushview != nil){
            NSLog(@"本地预览view也不为空");
            dispatch_async(dispatch_get_main_queue(), ^{
                [self._pusher startPreview: self._pushview];
            });
        }
    }else{
        
    }
}

#pragma mark 切换推流的摄像头（前置，后置）
-(void)switchCamera
{
    [self._pusher switchCamera];
}

#pragma mark 开始推流
-(void)startPush:(NSString *)url
{
    [self._pusher startPush:url];
}

#pragma mark 停止推流
-(void)stopPush
{
    dispatch_async(dispatch_get_main_queue(), ^{
        [self._pusher stopPreview]; //启动了摄像头预览，结束推流时将其关闭。
        [self._pusher stopPush];
        
        //TODO: 清理管理类保存的直播推流相关对象
        self._config = nil;
        self._pusher = nil;
        self._pushview = nil;
    });
}

#pragma mark 设定画面清晰度(LOW,MEDIUM,HIGH)
-(void) setUserVideoQuality:(NSString *)quality
{
    if([quality compare:@"LOW"] == NSOrderedSame){
        [self._pusher setVideoQuality:VIDEO_QUALITY_STANDARD_DEFINITION adjustBitrate:YES adjustResolution:NO];
    }else if ([quality compare:@"MEDIUM"] == NSOrderedSame){
        [self._pusher setVideoQuality:VIDEO_QUALITY_HIGH_DEFINITION adjustBitrate:YES adjustResolution:NO];
    }else if ([quality compare:@"HIGH"] == NSOrderedSame){
        [self._pusher setVideoQuality:VIDEO_QUALITY_SUPER_DEFINITION adjustBitrate:YES adjustResolution:NO];
    }else{
        RCTLogError(@"画面清晰度参数错误，请传入 LOW , MEDIUM , HIGH 中的一个");
    }
}

#pragma mark 切换主播端镜像开启关闭状态
-(void) setMirrorEnabled:(BOOL)enabled
{
    [self._pusher setMirror:enabled];
}

#pragma mark 设置横竖屏模式
-(void) setOrientaionMode:(NSString *)orientation
{
    if([orientation compare:@"PORTRAIT"]){
        //竖屏
        self._config.homeOrientation = HOME_ORIENTATION_DOWN;
        [self._pusher setConfig:self._config];
        [self._pusher setRenderRotation:0];
    }else if([orientation compare:@"LANDSCAPE"]){
        //横屏
        self._config.homeOrientation = HOME_ORIENTATION_RIGHT;
        [self._pusher setConfig:self._config];
        [self._pusher setRenderRotation:90];
    }else{
        RCTLogError(@"暂只支持 竖屏 和 横屏(home键在右)两种, 参数请传入 PORTRAIT 或者 LANDSCAPE");
    }
}

#pragma mark 耳返开启关闭
-(void)setAudioPreviewEnabled:(BOOL)enabled
{
    self._config.enableAudioPreview = enabled;
    [self._pusher setConfig:self._config];
}

#pragma mark 是否开启手动对焦
-(void)setManualFocus: (BOOL)enabled
{
    self._config.touchFocus = enabled;
    [self._pusher setConfig: self._config];
}

#pragma mark 变声器
-(void)setFakeVoice:(int)voicetype
{
    //  VOICECHANGER_TYPE_0          = 0,     ///< 关闭变声
    //  VOICECHANGER_TYPE_1          = 1,     ///< 熊孩子
    //  VOICECHANGER_TYPE_2          = 2,     ///< 萝莉
    //  VOICECHANGER_TYPE_3          = 3,     ///< 大叔
    //  VOICECHANGER_TYPE_4          = 4,     ///< 重金属
    //  VOICECHANGER_TYPE_5          = 5,     ///< 感冒
    //  VOICECHANGER_TYPE_6          = 6,     ///< 外国人
    //  VOICECHANGER_TYPE_7          = 7,     ///< 困兽
    //  VOICECHANGER_TYPE_8          = 8,     ///< 死肥仔
    //  VOICECHANGER_TYPE_9          = 9,     ///< 强电流
    //  VOICECHANGER_TYPE_10         = 10,    ///< 重机械
    //  VOICECHANGER_TYPE_11         = 11,    ///< 空灵
    switch (voicetype) {
        case 0:
            [self._pusher setVoiceChangerType:VOICECHANGER_TYPE_0];
            break;
        case 1:
            [self._pusher setVoiceChangerType:VOICECHANGER_TYPE_1];
            break;
        case 2:
            [self._pusher setVoiceChangerType:VOICECHANGER_TYPE_2];
            break;
        case 3:
            [self._pusher setVoiceChangerType:VOICECHANGER_TYPE_3];
            break;
        case 4:
            [self._pusher setVoiceChangerType:VOICECHANGER_TYPE_4];
            break;
        case 5:
            [self._pusher setVoiceChangerType:VOICECHANGER_TYPE_5];
            break;
        case 6:
            [self._pusher setVoiceChangerType:VOICECHANGER_TYPE_6];
            break;
        case 7:
            [self._pusher setVoiceChangerType:VOICECHANGER_TYPE_7];
            break;
        case 8:
            [self._pusher setVoiceChangerType:VOICECHANGER_TYPE_8];
            break;
        case 9:
            [self._pusher setVoiceChangerType:VOICECHANGER_TYPE_9];
            break;
        case 10:
            [self._pusher setVoiceChangerType:VOICECHANGER_TYPE_10];
            break;
        case 11:
            [self._pusher setVoiceChangerType:VOICECHANGER_TYPE_11];
            break;
        default:
            RCTLogError(@"无效变声参数");
            break;
    }
    
}

#pragma mark 设置美颜风格 (自然，光滑)
-(void)setBeautyMode:(NSString *)beautystyle
{
    if([beautystyle compare:@"SMOOTH"] == NSOrderedSame ){
        [[self._pusher getBeautyManager] setBeautyStyle:TXBeautyStyleSmooth];
    }else if([beautystyle compare:@"NATURAL"] == NSOrderedSame){
        [[self._pusher getBeautyManager] setBeautyStyle:TXBeautyStyleNature];
    }else {
        RCTLogError(@"暂不支持其他的美颜风格参数");
    }
}

#pragma mark 设置磨皮级别
-(void)setBeautyLevel:(float)level
{
    float value = 0;
    if(level <= 0){
        value = 0;
    }else if(level >= 9.0){
        value = 9.0;
    }else{
        value = level;
    }
    
    [[self._pusher getBeautyManager] setBeautyLevel:value];
}

#pragma mark 设置美白级别
-(void)setWhitenessLevel:(float)level
{
    float value = 0;
    if(level <= 0){
        value = 0;
    }else if(level >= 9.0){
        value = 9.0;
    }else{
        value = level;
    }
    [[self._pusher getBeautyManager] setWhitenessLevel:value];
}

#pragma mark 设置红润级别
-(void)setRuddinessLevel:(float)level
{
    float value = 0;
    if(level <= 0){
        value = 0;
    }else if(level >= 9.0){
        value = 9.0;
    }else{
        value = level;
    }
    [[self._pusher getBeautyManager] setRuddyLevel:value];
    
}

#pragma mark 设置推流方向
-(void) setPushOrientation:(BOOL) isPortrait
{
    if(isPortrait == YES) {
        self._config.homeOrientation = HOME_ORIENTATION_DOWN;
        [self._pusher setConfig:self._config];
        [self._pusher setRenderRotation:0];
    }else{
        self._config.homeOrientation = HOME_ORIENTATION_RIGHT;
        [self._pusher setConfig:self._config];
        [self._pusher setRenderRotation:90];
    }
}

@end
