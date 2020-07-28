//
//  LivePullViewManager.m
//
//  Created by lzp on 2020/3/3.
//  Copyright © 2020 Facebook. All rights reserved.
//

#import "LivePullViewManager.h"
#import "LivePullView.h"
#import "LiveManager.h"

@implementation LivePullViewManager

RCT_EXPORT_MODULE(LivePullView)

- (UIView *)view
{
  return [[LivePullView alloc] init];
}

@end
