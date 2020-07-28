//
//  LivePushListener.m
//
//  Created by lzp on 2020/3/6.
//  Copyright © 2020 Facebook. All rights reserved.
//
#import "LivePushListener.h"
#import "LiveModule.h"

@implementation LivePushListener

- (void) onPushEvent:(int)EvtID withParam:(NSDictionary *)param
{
  switch (EvtID) {
    case PUSH_EVT_CONNECT_SUCC:
      //已经成功连接到腾讯云推流服务器。 1001
      [LiveModule emitEvent:@{@"type": @"PUSH_EVT_CONNECT_SUCC" ,@"message": @"已经成功连接到推流服务器"}];
      break;
    case PUSH_EVT_PUSH_BEGIN:
      //与服务器握手完毕，一切正常，准备开始推流。 1002
      [LiveModule emitEvent:@{@"type": @"PUSH_EVT_PUSH_BEGIN" ,@"message": @"与服务器握手完毕，一切正常，准备开始推流"}];
      break;
    case PUSH_EVT_OPEN_CAMERA_SUCC:
      //推流器已成功打开摄像头（部分 Android 手机在此过程需要耗时1s - 2s）。 1003
      NSLog(@"摄像头打开成功");
      [LiveModule emitEvent:@{@"type": @"PUSH_EVT_OPEN_CAMERA_SUCC" ,@"message": @"推流器已成功打开摄像头"}];
      break;
    case PUSH_ERR_OPEN_CAMERA_FAIL:
      //打开摄像头失败。 -1301
      [LiveModule emitEvent:@{@"type": @"PUSH_ERR_OPEN_CAMERA_FAIL" ,@"message": @"打开摄像头失败"}];
      break;
    case PUSH_ERR_OPEN_MIC_FAIL:
      //打开麦克风失败。 -1302
      [LiveModule emitEvent:@{@"type": @"PUSH_ERR_OPEN_MIC_FAIL" ,@"message": @"打开麦克风失败"}];
      break;
    case PUSH_ERR_VIDEO_ENCODE_FAIL:
      //视频编码失败。 -1303
      [LiveModule emitEvent:@{@"type": @"PUSH_ERR_VIDEO_ENCODE_FAIL" ,@"message": @"视频编码失败"}];
      break;
    case PUSH_ERR_AUDIO_ENCODE_FAIL:
      //音频编码失败。 -1304
      [LiveModule emitEvent:@{@"type": @"PUSH_ERR_AUDIO_ENCODE_FAIL" ,@"message": @"音频编码失败"}];
      break;
    case PUSH_ERR_UNSUPPORTED_RESOLUTION:
      //不支持的视频分辨率。 -1305
      [LiveModule emitEvent:@{@"type": @"PUSH_ERR_UNSUPPORTED_RESOLUTION" ,@"message": @"不支持的视频分辨率"}];
      break;
    case PUSH_ERR_UNSUPPORTED_SAMPLERATE:
      //不支持的音频采样率。 -1306
      [LiveModule emitEvent:@{@"type": @"PUSH_ERR_UNSUPPORTED_SAMPLERATE" ,@"message": @"不支持的音频采样率"}];
      break;
    case PUSH_ERR_NET_DISCONNECT:
      //网络断连，且经三次重连无效，更多重试请自行重启推流。 -1307
      [LiveModule emitEvent:@{@"type": @"PUSH_ERR_NET_DISCONNECT" ,@"message": @"网络断连，且经三次重连无效，更多重试请自行重启推流"}];
      break;
    case PUSH_WARNING_NET_BUSY:
      //网络状况不佳：上行带宽太小，上传数据受阻。 1101
      [LiveModule emitEvent:@{@"type": @"PUSH_WARNING_NET_BUSY" ,@"message": @"网络状况不佳：上行带宽太小，上传数据受阻"}];
      break;
    case PUSH_WARNING_SERVER_DISCONNECT:
      //RTMP 服务器主动断开连接（会触发重试流程）。 3004
      [LiveModule emitEvent:@{@"type": @"PUSH_WARNING_SERVER_DISCONNECT" ,@"message": @"RTMP 服务器主动断开连接（会触发重试流程）"}];
      break;
    default:
      NSLog(@"推流事件错误：未知事件 %d",EvtID);
      break;
  }
}

- (void)onNetStatus:(NSDictionary *)param {
  
}


@end
