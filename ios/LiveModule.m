//
//  LiveModule.h
//
//  Created by lzp on 2020/3/3.
//  Copyright © 2020 Haxibiao. All rights reserved.
//

#import "LiveModule.h"

@implementation LiveModule

RCT_EXPORT_MODULE()





// ========= 拉流部分 ==============
RCT_EXPORT_METHOD(setLicence: (NSString *)licenceUrl licenceKey:(NSString *)licenceKey)
{
  LiveManager *shared = [LiveManager shared];
  [shared setLicence:licenceUrl andLicenceKey:licenceKey];
}

RCT_EXPORT_METHOD(startPull: (NSString *)url liveType:(int)liveType)
{
  LiveManager *shared = [LiveManager shared];
  [shared startPull:url withLiveType:liveType];
}

RCT_EXPORT_METHOD(stopPull)
{
  LiveManager *shared = [LiveManager shared];
  [shared stopPull];
}



// ============= 推流部分 ================


//推流开启预览
RCT_EXPORT_METHOD(preview)
{
  LiveManager *shared = [LiveManager shared];
  [shared preview];
}
//开始推流
RCT_EXPORT_METHOD(startPush: (NSString *)url)
{
  LiveManager *shared = [LiveManager shared];
  [shared startPush:url];
}
//切换推流的摄像头（前置，后置）
RCT_EXPORT_METHOD(switchCamera)
{
  LiveManager *shared = [LiveManager shared];
  [shared switchCamera];
}
//停止推流
RCT_EXPORT_METHOD(stopPush)
{
  LiveManager *shared = [LiveManager shared];
  [shared stopPush];
}
//设定画面清晰度(LOW,MEDIUM,HIGH)
RCT_EXPORT_METHOD(setUserVideoQuality: (NSString *)quality)
{
  // LOW , MEDIUM , HIGH
  LiveManager *shared = [LiveManager shared];
  [shared setUserVideoQuality:quality];
}
//切换主播端镜像开启关闭状态
RCT_EXPORT_METHOD(setMirrorEnabled: (BOOL)enabled)
{
  LiveManager *shared = [LiveManager shared];
  [shared setMirrorEnabled:enabled];
}
//设置横竖屏模式
RCT_EXPORT_METHOD(setOrientaionMode: (NSString *)orientation)
{
  //PORTRAIT  ,LANDSCAPE
  LiveManager *shared = [LiveManager shared];
  [shared setOrientaionMode:orientation];
}
//耳返开启关闭
RCT_EXPORT_METHOD(setAudioPreviewEnabled: (NSString *)enabled)
{
  LiveManager *shared = [LiveManager shared];
  [shared setAudioPreviewEnabled:enabled];
}
//开启手动对焦
RCT_EXPORT_METHOD(setManualFocus: (BOOL)enabled)
{
  LiveManager *shared = [LiveManager shared];
  [shared setManualFocus: enabled];
}
//变声器
RCT_EXPORT_METHOD(setFakeVoice: (int)voicetype)
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
  LiveManager *shared = [LiveManager shared];
  [shared setFakeVoice:voicetype];
}
//设置美颜风格 (自然，光滑)
RCT_EXPORT_METHOD(setBeautyMode: (NSString *)beautystyle)
{
  // SMOOTH , NATURAL
  LiveManager *shared = [LiveManager shared];
  [shared setBeautyMode:beautystyle];
}
//设置磨皮级别
RCT_EXPORT_METHOD(setBeautyLevel:(float)level)
{
  LiveManager *shared = [LiveManager shared];
  [shared setBeautyLevel:level];
}
//设置美白级别
RCT_EXPORT_METHOD(setWhitenessLevel:(float)level)
{
  LiveManager *shared = [LiveManager shared];
  [shared setWhitenessLevel:level];
}
//设置红润级别
RCT_EXPORT_METHOD(setRuddinessLevel:(float)level)
{
  LiveManager *shared = [LiveManager shared];
  [shared setRuddinessLevel:level];
}






#pragma mark 注册的可用事件

- (NSArray<NSString *> *)supportedEvents
{
  return @[
    @"PLAY_EVT",
    @"NET_STATUS",

    @"PLAY_EVT",
    @"ERROR_EVT",
    @"WARNING_EVT"
  ];
}

- (void)netStatusSendWithType:(NSString *)status withMessage:(NSString *)message
{
  if([status compare:NET_STATUS_NET_SPEED] == NSOrderedSame){
    [self sendEventWithName:@"NET_STATUS" body:@{@"NET_STATUS_NET_SPEED":message}];
  }else if([status compare:NET_STATUS_NET_JITTER] == NSOrderedSame){
    [self sendEventWithName:@"NET_STATUS" body:@{@"NET_STATUS_NET_JITTER":message}];
  }else{
    RCTLogError(@"网络状态监听类型参数未知");
  }
}

-(void) startObserving {
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(emitEventInternal:) name:@"emit-event" object:nil];
}

-(void) stopObserving {
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

-(void) emitEventInternal:(NSNotification *)notification{
    if([@"PLAY_EVT_CONNECT_SUCC" compare:[notification.userInfo objectForKey:@"type"]] == NSOrderedSame ){
        [self sendEventWithName:@"PLAY_EVT" body:@{@"PLAY_EVT_CONNECT_SUCC":[notification.userInfo objectForKey:@"message"]}];
        
    }else if( [@"PLAY_EVT_RTMP_STREAM_BEGIN" compare:[notification.userInfo objectForKey:@"type"]] == NSOrderedSame ){
        [self sendEventWithName:@"PLAY_EVT" body:@{@"PLAY_EVT_RTMP_STREAM_BEGIN":[notification.userInfo objectForKey:@"message"]}];
        
    }else if( [@"PLAY_EVT_PLAY_BEGIN" compare:[notification.userInfo objectForKey:@"type"]] == NSOrderedSame ){
        [self sendEventWithName:@"PLAY_EVT" body:@{@"PLAY_EVT_PLAY_BEGIN":[notification.userInfo objectForKey:@"message"]}];
        
    }else if( [@"PLAY_EVT_PLAY_END" compare:[notification.userInfo objectForKey:@"type"]] == NSOrderedSame ){
        [self sendEventWithName:@"PLAY_EVT" body:@{@"PLAY_EVT_PLAY_END":[notification.userInfo objectForKey:@"message"]}];
        
    }else if( [@"PLAY_EVT_PLAY_LOADING" compare:[notification.userInfo objectForKey:@"type"]] == NSOrderedSame ){
        [self sendEventWithName:@"PLAY_EVT" body:@{@"PLAY_EVT_PLAY_LOADING":[notification.userInfo objectForKey:@"message"]}];
        
    }else if( [@"PLAY_EVT_START_VIDEO_DECODER" compare:[notification.userInfo objectForKey:@"type"]] == NSOrderedSame ){
        [self sendEventWithName:@"PLAY_EVT" body:@{@"PLAY_EVT_START_VIDEO_DECODER":[notification.userInfo objectForKey:@"message"]}];
        
    }else if( [@"PLAY_EVT_STREAM_SWITCH_SUCC" compare:[notification.userInfo objectForKey:@"type"]] == NSOrderedSame ){
        [self sendEventWithName:@"PLAY_EVT" body:@{@"PLAY_EVT_STREAM_SWITCH_SUCC":[notification.userInfo objectForKey:@"message"]}];
        
    }else if( [@"PLAY_ERR_NET_DISCONNECT" compare:[notification.userInfo objectForKey:@"type"]] == NSOrderedSame ){
        [self sendEventWithName:@"PLAY_EVT" body:@{@"PLAY_ERR_NET_DISCONNECT":[notification.userInfo objectForKey:@"message"]}];
    }else if( [@"PLAY_WARNING_RECONNECT" compare: [notification.userInfo objectForKey:@"type"]] == NSOrderedSame ){
        [self sendEventWithName:@"PLAY_EVT" body:@{@"PLAY_ERR_NET_DISCONNECT":[notification.userInfo objectForKey:@"message"]}];
    }
    
    if( [NET_STATUS_NET_SPEED compare:[notification.userInfo objectForKey:@"type"]] == NSOrderedSame ){
        [self sendEventWithName:@"NET_STATUS" body:@{@"NET_STATUS_NET_SPEED":[notification.userInfo objectForKey:@"message"]}];
        
    }else if( [NET_STATUS_NET_JITTER compare:[notification.userInfo objectForKey:@"type"]] == NSOrderedSame ){
        [self sendEventWithName:@"NET_STATUS" body:@{@"NET_STATUS_NET_JITTER":[notification.userInfo objectForKey:@"message"]}];
    }
    
}

+(void) emitEvent: (NSDictionary *)payload
{
    // {type: @"", message: @""}
    [[NSNotificationCenter defaultCenter] postNotificationName:@"emit-event" object:nil userInfo:payload];
}

@end
