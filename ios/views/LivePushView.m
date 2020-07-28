//
//  LivePushView.m
//
//  Created by lzp on 2020/3/6.
//  Copyright © 2020 Facebook. All rights reserved.
//
#import "LivePushView.h"
#import "LiveManager.h"
#import "LivePushListener.h"

@implementation LivePushView

-(id) init
{
  LiveManager *shared = [LiveManager shared];
  
  if(self = [super init]){
    shared._config = [[TXLivePushConfig alloc] init];
    shared._pusher = [[TXLivePush alloc] initWithConfig: shared._config];
    shared._pushview = self;
    
    shared._push_delegate = [[LivePushListener alloc] init];
    shared._pusher.delegate = shared._push_delegate;
    
    [shared preview];
  }
  return self;
}


@end
