# react-native-tencent-live

#### å®è£æ¹æ³ ð¨

```
yarn add git+https://github.com/yaaliuzhipeng/react-native-live.git
```

æè

```
npm install -D git+https://github.com/yaaliuzhipeng/react-native-live.git
```

### æ´æ°æ¨¡å

æä¹æ¥çå½åçæ¬ï¼æ§è¡ `yarn list | grep "react-native-tencent-live"`ï¼å°ä¼è¾åºä½ å½åé¡¹ç®å®è£ç react-native-tencent-live çæ¬

ç¶åå¯ä»¥ä½¿ç¨ npm update æ¥æ´æ°æ¨¡å

```
$ npm update react-native-live
```

æèä½¿ç¨ yarn upgrade æ¥æ´æ°

```
$ yarn upgrade react-native-live
```

#### éå¸¸æåµä¸ãRN 0.60 çæ¬å¾ä¸ä¼èªå¨é¾æ¥

â ï¸ å¦æé¾æ¥å¤±è´¥ãè¯·å°è¯æå¨é¾æ¥åº

æ§è¡ : react-native link react-native-live

âââ å¦ä½å¤æ­æ¯å¦èªå¨é¾æ¥æå â

ð æå¼é¡¹ç®ç iOS è·¯å¾ä¸ç podfile æä»¶

å¦æå­å¨ ð pod 'react-native-live', :path => '../node_modules/react-native-live' åè¯´æé¾æ¥æå

#### Android ä½¿ç¨æ³¨æ ð

Android ä¸ä½¿ç¨å½åéè¦æå¨æå¼ android/app/build.gradle è¿è¡æ´æ¹

```
1. å¨ defaultConfig ä¸æ·»å  ndk éç½®ã å¦ä¸

defaultConfig{
   ...
   ndk {
         abiFilters "armeabi", "armeabi-v7a"
   }
   ...
}

2. æ·»å æåéç½®åæ°

android {
	...

	packagingOptions {
        pickFirst '**/armeabi-v7a/libc++_shared.so'
        pickFirst '**/x86/libc++_shared.so'
        pickFirst '**/arm64-v8a/libc++_shared.so'
        pickFirst '**/x86_64/libc++_shared.so'
        pickFirst '**/x86/libjsc.so'
        pickFirst '**/armeabi-v7a/libjsc.so'
  }

	...
}
```

æåä¸æ­¥ãä¿®æ¹ proguard-rules.pro ï¼æ·»å ä¸é¢è¿ä¸è¡

```
-keep class com.tencent.mm.sdk.** { *; }
```

#### iOS ä½¿ç¨æ³¨æ ð

å¨æ£æ¥å®é¾æ¥æååï¼éè¦å¨å½ä»¤è¡ä¸­ cd å°å½åé¡¹ç®ç iOS è·¯å¾ä¸

æ§è¡ï¼ pod install

## æ¥å£è¯´æï¼ð

### ææµ ððð

ä¸å±å¯¼å¥ä¸¤ä¸ªæ¨¡å

**import { LivePullManager,LivePullView } from 'react-native-tencent-live';**

**ðLivePullView**

LivePullView æ¯è´è´£æ¸²æç´æ­ç»é¢çç»ä»¶ï¼Viewï¼

éè¦éç½®å¶å®½é«åæ°ãå¶ä»åæ°æ éè®¾ç½®

```
...
<LivePullView style={{height:1920,width:1080}}/>
...
```

**ðLivePullManager**

è¯¥æ¨¡åæ¯ææµéç½®çæ ¸å¿

å¶ä¸­ä¸å±åå« 4 ä¸ªæ¹æ³ï¼ä¸ä¸ªæä¸¾åæ°

ð¨ setLicence è¯¥æ¹æ³å¿é¡»å¨ä½¿ç¨ç´æ­åå¨å±è°ç¨ä¸æ¬¡ï¼ç¨æ¥éç½®è¾è®¯ç´æ­ SDK çææåå¯é¥

```
setLicence: ( licenceUrl:string,licenceKey:string ) => void
```

ð¨startPull è°ç¨è¯¥æ¹æ³ãå¼å§ææµãéè¦ä¼ å¥ **ææµå°å** ä»¥å **æµç±»å** ï¼ä¸»è¦ä¸º RTMP å FLV ä¸¤ç§, å¦æä¸ä¼ ç±»åçè¯åé»è®¤ä¸º RTMP ç±»åï¼ãè°ç¨è¯¥æ¹æ³åï¼å¦æç»ä»¶æ ä¸­å­å¨ <LivePullView /> çè¯åä¼å¼å§è¿æ¥æå¡å¨ï¼è·åç»é¢åé³é¢ï¼ç¶åä¾¿å¯ä»¥æ¥æ¶ç´æ­äºã

```
startPull:(url: string,type: LIVE_TYPE) => void

LIVE_TYPE:{
    RTMP: LIVE_TYPE,
    FLV: LIVE_TYPE
}

ä½¿ç¨æ¹æ³ï¼
LivePullManager.startPull("rtmp://...", LivePullManager.LIVE_TYPE.RTMP);
```

ð¨stopPull è°ç¨è¯¥æ¹æ³ãåæ­¢ææµã å¦æéåºç´æ­ï¼ä¸å®è¦è°ç¨è¯¥æ¹æ³ â ï¸â ï¸â ï¸ãå¦åå¯è½é æåå­æ³æ¼æèéªå±ç­ãæå·²ç»å¨åçç«¯ç LivePullView çéæ¯çå½å¨æä¸­è°ç¨äºè¯¥æ¹æ³ãä½ä¸ºäºä¿è¯ç¨³å®æ§ãå¨ä¸éè¦ç´æ­ view çæ¶ååè®°è¦è°ç¨è¯¥æ¹æ³ï¼ï¼ð

```
stopPull:() => void,
```

ð¨subscribe è°ç¨è¯¥æ¹æ³ï¼ä¼ å¥ä¸ä¸ªåè°å½æ°ãåè°å½æ°çè¿ä¸ªåæ°ä¼æ¯åçç«¯ç´æ­äºä»¶åéè¿æ¥çéç¥ææ¬ã

```
subscribe:(event: keyof EVENT_TYPE,callback: (result:any)=> void ) => EmitterSubscription
```

â ï¸ ä¸éè¦åçå¬æä¸ªäºä»¶åï¼ä¸å®è¦ç§»é¤è®¢é

subscribe ä½¿ç¨ç¤ºä¾

```
const begin_subscription = LivePullManager.subscribe(
'PLAY_EVT_CONNECT_SUCC',(result) => {
    console.log("è¿æ¥æå¡å¨æåå¦çå¬äºä»¶: ",result);
})

å½ä¸éè¦è¯¥äºä»¶çå¬å
begin_subscription.remove();
```

##### å½åå·²å°è£çäºä»¶æ:

|                              |       |                                                                                                  |
| ---------------------------- | ----- | ------------------------------------------------------------------------------------------------ |
| PLAY_EVT_CONNECT_SUCC        | 2001  | å·²ç»è¿æ¥æå¡å¨                                                                                   |
| PLAY_EVT_RTMP_STREAM_BEGIN   | 2002  | å·²ç»è¿æ¥æå¡å¨ï¼å¼å§ææµï¼ä»æ­æ¾ RTMP å°åæ¶ä¼æéï¼                                             |
| PLAY_EVT_PLAY_BEGIN          | 2004  | è§é¢æ­æ¾å¼å§                                                                                     |
| PLAY_EVT_PLAY_END            | 2006  | æ­æ¾ç»æï¼HTTP-FLV çç´æ­æµæ¯ä¸æè¿ä¸ªäºä»¶ç\*                                                    |
| PLAY_EVT_PLAY_LOADING        | 2007  | è§é¢æ­æ¾è¿å¥ç¼å²ç¶æï¼ç¼å²ç»æä¹åä¼æ PLAY_BEGIN äºä»¶                                           |
| PLAY_EVT_START_VIDEO_DECODER | 2008  | è§é¢è§£ç å¨å¼å§å¯å¨ï¼2.0 çæ¬ä»¥åæ°å¢ï¼                                                           |
| PLAY_EVT_STREAM_SWITCH_SUCC  | 2015  | ç´æ­æµåæ¢å®æ                                                                                   |
| PLAY_ERR_NET_DISCONNECT      | -2301 | ç½ç»æ­è¿ï¼ä¸ç»å¤æ¬¡éè¿äº¦ä¸è½æ¢å¤ï¼æ´å¤éè¯è¯·èªè¡éå¯æ­æ¾ (è¿ä¸ªäºä»¶å¯ç¨ä½ FLV æ­æ¾ç»æçå¤æ­æ è¯) |
| NET_STATUS_NET_SPEED         |       | å½åçç½ç»æ°æ®æ¥æ¶éåº¦                                                                           |
| NET_STATUS_NET_JITTER        |       | ç½ç»æå¨æåµï¼æå¨è¶å¤§ï¼ç½ç»è¶ä¸ç¨³å®\*                                                           |
|                              |       |                                                                                                  |

### æ¨æµ ððð

ä¸å±å¯¼å¥ä¸¤ä¸ªæ¨¡å

**import { LivePushManager,LivePushView } from 'react-native-tencent-live';**

**ðLivePushView**

è¯¥ç»ä»¶åªç¨äºå±ç¤ºæåå¤´é¢è§ï¼ç»ä¸»æ­èªå·±çï¼, å¿é¡»è®¾ç½®å®½é«åæ°ï¼å¶ä»æ éè®¾ç½®

```
...
<LivePushView style={{height:1920,width:1080}}/>
...
```

**ðLivePushManager**

è¯¥æ¨¡åæ¯æ¨æµéç½®çæ ¸å¿

å¶ä¸­ç®åä¸å±åå« 14 ä¸ªæ¹æ³

ð¨ preview è¯¥æ¹æ³å¨æ·»å å¥½ **LivePushView**ä¹åç¨äºè§¦åé¢è§ç»é¢æ¸²æãå¯å¨é¢è§éè¦åºç¨å·²ç»è·åæåå¤´æéï¼èç´æ­è¿éè¦éº¦åé£æéï¼â ï¸â ï¸â ï¸ æä»¥å¨è°ç¨è¯¥æ¹æ³åè¯·åç¡®ä¿åºç¨æåè·åå°è¿ä¸¤ä¸ªæéã

**ï¼æ³¨ï¼ç°å¨å·²é»è®¤æå¼æåå¤´é¢è§ï¼è¯¥æ¹æ³æ éåè°ç¨ï¼**

```
preview: () => void
```

ð¨ startPush è¯¥æ¹æ³ç¨äºå¯å¨æ¨æµãå³å°ç»é¢æ¨éåºå»ãä½¿å¾ææµç«¯ç¨æ·å¯ä»¥è·åç»é¢åé³é¢ãéè¦ä¼ å¥æ¨æµå°å

```
startPush: (url:string) => void
```

ð¨ stopPush è¯¥æ¹æ³ç¨äºåæ­¢æ¨æµï¼è°ç¨è¯¥æ¹æ³åä¼åæ­¢æ¨æµãå¹¶ä¸åçç«¯ä¼æ¸é¤åå»ºçé¢è§ç»é¢å®¹å¨ View å å®ç¸å³éç½®åæ°ãå¨ç»æç´æ­æ¶éè¦è°ç¨è¯¥æ¹æ³ï¼

```
stopPush: () => void
```

ð¨ switchCamera è¯¥æ¹æ³ç¨äºå¨ååæåå¤´ä¹é´è¿è¡åæ¢ï¼é»è®¤æå¼ åç½®æåå¤´

```
switchCamera: () => void
```

ð¨ setUserVideoQuality è¯¥æ¹æ³ç¨äºè®¾ç½®è§ä¼ç«¯çç»é¢æ¸æ°åº¦ãä¸»æ­ç«¯çç»é¢æ»æ¯ä»¥åç»æ¨éå°æå¡å¨ã

```
setUserVideoQuality: (quality: keyof QUALITY) => void

ä¸ä¸ªæ¸æ°åº¦éé¡¹ : LOW (æ­£å¸¸), MEDIUM (é«æ¸), HIGH (è¶æ¸)
```

ð¨ setMirrorEnabled ä¸»æ­ç»é¢æ¯å¦å¼å¯éåæ¨¡å¼ ( å¼å¯åä¸»æ­çå°çææºç»é¢åè§ä¼çå°çç»é¢ä¸è´)

```
setMirrorEnabled: (enabled: boolean) => void
```

ð¨ setOrientaionMode è®¾ç½®ç»é¢æ¹å( ç«å± ã æ¨ªå± )

```
setOrientaionMode: (orientation: keyof ORIENTATION) => void

ä¸¤ä¸ªæ¹åéé¡¹ï¼    PORTRAIT  , LANDSCAPE
```

ð¨ setAudioPreviewEnabled è³è¿å¼å³ï¼æå¼è³è¿åä¸»æ­å¯ä»¥å¨è³æºä¸­å®æ¶å¬å°ç´æ­é´éçå£°é³

```
setAudioPreviewEnabled: (enabled: boolean) => void
```

ð¨ setFakeVoice åå£° (æ¯ä» 50w åå¯ç¨)

```
setFakeVoice: (voicetype:VOICETYPE) => void

export enum VOICETYPE {
    close = 0,					//å³é­åå£°
    wild_kids,					//çå­©å­
    lolita,							//èè
    uncle,							//å¤§å
    heavy_metal,				//ééå±
    cold,								//æå
    foreigner,					//å¤å½äºº
    trapped_beast,			//å°å½
    fat_ass,						//æ­»è¥ä»
    electric,						//å¼ºçµæµ
    heavy_machine,			//éæºæ¢°
    ethereal						//ç©ºçµ
}
```

ð¨ setBeautyMode è®¾ç½®ç¾é¢æ¨¡å¼

```
setBeautyMode: (beautystyle: keyof BEAUTYSTYLE) => void

åæ°ä¸å±ä¸¤ä¸ªéé¡¹ :
	SMOOTH		//	åæ»
	NATURAL   //  èªç¶
```

ð¨ setBeautyLevel è®¾ç½®ç£¨ç®ç¨åº¦

```
setBeautyLevel: (level:number) => void

åæ°å¼èå´ï¼ 0 - 9
```

ð¨ setWhitenessLevel è®¾ç½®ç¾ç½ç¨åº¦

```
setWhitenessLevel: (level:number) => void

åæ°å¼èå´ï¼ 0 - 9
```

ð¨ setRuddinessLevel è®¾ç½®çº¢æ¶¦ç¨åº¦

```
setRuddinessLevel: (level:number) => void

åæ°å¼èå´ï¼ 0 - 9
```

ð¨ subscribe æ·»å çå¬äºä»¶,ç¨æ³åææµ

```
subscribe:(event: keyof EVENT_TYPE,callback:(result:any) => void ) => EmitterSubscription
```

ð ä¸éè¦çå¬äºä»¶åè®°å¾éæ¯ï¼ðððððð

å½åå·²æäºä»¶:

|                                 |       |                                                                       |
| ------------------------------- | ----- | --------------------------------------------------------------------- |
| PUSH_EVT_OPEN_CAMERA_SUCC       | 1001  | å·²ç»æåè¿æ¥å°è¾è®¯äºæ¨æµæå¡å¨ã                                      |
| PUSH_EVT_PUSH_BEGIN             | 1002  | ä¸æå¡å¨æ¡æå®æ¯ï¼ä¸åæ­£å¸¸ï¼åå¤å¼å§æ¨æµã                            |
| PUSH_EVT_OPEN_CAMERA_SUCC       | 1003  | æ¨æµå¨å·²æåæå¼æåå¤´ï¼é¨å Android ææºå¨æ­¤è¿ç¨éè¦èæ¶ 1s - 2sï¼ã |
| PUSH_ERR_OPEN_CAMERA_FAIL       | -1301 | æå¼æåå¤´å¤±è´¥ã                                                      |
| PUSH_ERR_OPEN_MIC_FAIL          | -1302 | æå¼éº¦åé£å¤±è´¥ã                                                      |
| PUSH_ERR_VIDEO_ENCODE_FAIL      | -1303 | è§é¢ç¼ç å¤±è´¥ã                                                        |
| PUSH_ERR_AUDIO_ENCODE_FAIL      | -1304 | é³é¢ç¼ç å¤±è´¥ã                                                        |
| PUSH_ERR_UNSUPPORTED_RESOLUTION | -1305 | ä¸æ¯æçè§é¢åè¾¨çã                                                  |
| PUSH_ERR_UNSUPPORTED_SAMPLERATE | -1306 | ä¸æ¯æçé³é¢éæ ·çã                                                  |
| PUSH_ERR_NET_DISCONNECT         | -1307 | ç½ç»æ­è¿ï¼ä¸ç»ä¸æ¬¡éè¿æ æï¼æ´å¤éè¯è¯·èªè¡éå¯æ¨æµã                  |
| PUSH_WARNING_NET_BUSY           | 1101  | ç½ç»ç¶åµä¸ä½³ï¼ä¸è¡å¸¦å®½å¤ªå°ï¼ä¸ä¼ æ°æ®åé»ã                            |
| PUSH_WARNING_SERVER_DISCONNECT  | 3004  | RTMP æå¡å¨ä¸»å¨æ­å¼è¿æ¥ï¼ä¼è§¦åéè¯æµç¨ï¼ã                           |
|                                 |       |                                                                       |
