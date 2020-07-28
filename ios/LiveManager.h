//
//  LiveManager.h
//
//  Created by lzp on 2020/3/3.
//  Copyright © 2020 Facebook. All rights reserved.
//
#import "LivePullListener.h"
#import "LivePushListener.h"
#import <Foundation/Foundation.h>
@import TXLiteAVSDK_Professional;

@interface LiveManager : NSObject

+ (instancetype) shared;

@property (nonatomic,strong) TXLivePlayer         *_player;
@property (nonatomic,strong) LivePullListener     *_pull_delegate;

@property (nonatomic,strong) TXLivePush           *_pusher;
@property (nonatomic,strong) UIView               *_pushview;
@property (nonatomic,strong) TXLivePushConfig     *_config;
@property (nonatomic,strong) LivePushListener     *_push_delegate;


// 配置直播Licence授权 (必须在使用直播前调用)
- (void)setLicence:(NSString *)url andLicenceKey:(NSString *)key;
// 拉流
- (void)startPull:(NSString *)url withLiveType:(int)livetype;
// 停止拉流
- (void) stopPull;

-(void)preview;
-(void)startPush:(NSString *)url;
-(void)stopPush;
- (void) switchCamera;
- (void) setUserVideoQuality:(NSString *)quality;
- (void) setMirrorEnabled:(BOOL) enabled;
- (void) setOrientaionMode: (NSString *)orientation;
- (void) setAudioPreviewEnabled: (BOOL) enabled;
- (void) setManualFocus: (BOOL) enabled;
- (void) setFakeVoice: (int)voicetype;
- (void) setBeautyMode: (NSString *)beautystyle;
- (void) setBeautyLevel: (float) level;
- (void) setWhitenessLevel: (float) level;
- (void) setRuddinessLevel: (float) level;

@end

