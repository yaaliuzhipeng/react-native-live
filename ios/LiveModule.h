//
//  LiveModule.h
//
//  Created by lzp on 2020/3/3.
//  Copyright © 2020 Facebook. All rights reserved.
//

#import <React/RCTEventEmitter.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTLog.h>
#import "LiveManager.h"

@interface LiveModule : RCTEventEmitter <RCTBridgeModule>

+(void) emitEvent: (NSDictionary *)payload;

@end
