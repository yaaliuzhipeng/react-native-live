//
//  LivePushViewManager.m
//
//  Created by lzp on 2020/3/6.
//  Copyright © 2020 Facebook. All rights reserved.
//

#import "LivePushViewManager.h"
#import "LivePushView.h"

@implementation LivePushViewManager

RCT_EXPORT_MODULE(LivePushView)

-(UIView *)view
{
  return [[LivePushView alloc] init];
}

@end
