//
//  LivePullListener.m
//
//  Created by lzp on 2020/3/4.
//  Copyright © 2020 Facebook. All rights reserved.
//
#import "LivePullListener.h"
#import "LiveModule.h"

@implementation LivePullListener

-(id) init
{
    if(self = [super init]){
        NSLog(@"直播 代理对象初始化完成");
    }
    return self;
}

- (void)onPlayEvent:(int)EvtID withParam:(NSDictionary *)param
{
    switch (EvtID) {
        case PLAY_EVT_CONNECT_SUCC:
            //已经连接服务器 2001
            NSLog(@"连接服务器成功");
            [LiveModule emitEvent:@{@"type":@"PLAY_EVT_CONNECT_SUCC",@"message":@"连接服务器成功"}];
            break;
        case PLAY_EVT_RTMP_STREAM_BEGIN:
            //已经连接服务器，开始拉流（仅播放 RTMP 地址时会抛送） 2002
            NSLog(@"开始拉取直播数据");
            [LiveModule emitEvent:@{@"type":@"PLAY_EVT_RTMP_STREAM_BEGIN",@"message":@"开始拉取直播数据"}];
            break;
        case PLAY_EVT_PLAY_BEGIN:
            //视频播放开始 2004
            NSLog(@"开始直播");
            [LiveModule emitEvent:@{@"type":@"PLAY_EVT_PLAY_BEGIN",@"message":@"开始直播"}];
            break;
        case PLAY_EVT_PLAY_END:
            //播放结束，HTTP-FLV 的直播流是不抛这个事件的
            NSLog(@"直播结束");
            [LiveModule emitEvent:@{@"type":@"PLAY_EVT_PLAY_END",@"message":@"直播结束"}];
            break;
        case PLAY_EVT_PLAY_LOADING:
            //视频播放进入缓冲状态，缓冲结束之后会有 PLAY_BEGIN 事件 2007
            NSLog(@"直播缓冲中");
            [LiveModule emitEvent:@{@"type":@"PLAY_EVT_PLAY_LOADING",@"message":@"直播缓冲中"}];
            break;
        case PLAY_EVT_START_VIDEO_DECODER:
            //视频解码器开始启动（2.0 版本以后新增）2008
            NSLog(@"直播开始解码");
            [LiveModule emitEvent:@{@"type":@"PLAY_EVT_START_VIDEO_DECODER",@"message":@"直播开始解码"}];
            break;
        case PLAY_EVT_STREAM_SWITCH_SUCC:
            //直播流切换完成 2015
            NSLog(@"直播清晰度切换成功");
            [LiveModule emitEvent:@{@"type":@"PLAY_EVT_STREAM_SWITCH_SUCC",@"message":@"直播清晰度切换成功"}];
            break;
        case PLAY_ERR_NET_DISCONNECT:
            //网络断连，且经多次重连亦不能恢复，更多重试请自行重启播放 (这个事件可用作FLV播放结束的判断标识) -2301
            NSLog(@"网络无法重连、请尝试重启播放");
            [LiveModule emitEvent:@{@"type":@"PLAY_ERR_NET_DISCONNECT",@"message":@"网络无法重连、请尝试重启播放"}];
            break;
        case PLAY_WARNING_RECONNECT:
            NSLog(@"网络连接失败、正在重连");
            [LiveModule emitEvent:@{@"type":@"PLAY_WARNING_RECONNECT",@"message":@"网络连接失败、正在重连"}];
        default:
            break;
    }
}

- (void)onNetStatus:(NSDictionary *)param
{
    //当前的网络数据接收速度
    NSString *net_speed = [param objectForKey:NET_STATUS_NET_SPEED];
    
    //网络抖动情况，抖动越大，网络越不稳定
    NSString *net_jitter = [param objectForKey:NET_STATUS_NET_JITTER];
    
    [LiveModule emitEvent:@{@"type":@"NET_STATUS_NET_SPEED",@"message":net_speed}];
    [LiveModule emitEvent:@{@"type":@"NET_STATUS_NET_JITTER",@"message":net_jitter}];
    
}

@end
