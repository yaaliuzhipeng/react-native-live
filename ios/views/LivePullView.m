//
//  LivePullView.m
//
//  Created by lzp on 2020/3/3.
//  Copyright © 2020 Facebook. All rights reserved.
//
#import "LivePullView.h"
#import "LiveManager.h"
#import "LivePullListener.h"

@implementation LivePullView

-(id)init
{
  LiveManager *shared = [LiveManager shared];
  
  if(self = [super init]){
    NSLog(@"直播: TXLivePlayer视图初始化完成");
    TXLivePlayer *player = [[TXLivePlayer alloc]init];
    [player setupVideoWidget:CGRectZero containView:self insertIndex:0];
    
    shared._player = player;
  }
  return self;
}

@end
