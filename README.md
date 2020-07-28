# react-native-tencent-live

#### 安装方法 🔨

```
yarn add git+http://code.haxibiao.cn/packages/react-native-live.git
```

或者

```
npm install -D git+http://code.haxibiao.cn/packages/react-native-live.git
```

或者在项目 package.json 的 dependencies 下添加一行

```
"react-native-tencent-live":"git+http://code.haxibiao.cn/packages/react-native-live.git"
```

### 更新模块

怎么查看当前版本？执行 `yarn list | grep "react-native-tencent-live"`，将会输出你当前项目安装的 react-native-tencent-live 版本

然后可以使用 npm update 来更新模块

```
$ npm update react-native-live
```

或者使用 yarn upgrade 来更新

```
$ yarn upgrade react-native-live
```

#### 通常情况下、RN 0.60 版本往上会自动链接

⚠️ 如果链接失败、请尝试手动链接库

执行 : react-native link react-native-live

❓❓❓ 如何判断是否自动链接成功 ❓

🔆 打开项目的 iOS 路径下的 podfile 文件

如果存在 🏓 pod 'react-native-live', :path => '../node_modules/react-native-live' 则说明链接成功

#### Android 使用注意 🐒

Android 下使用当前需要手动打开 android/app/build.gradle 进行更改

```
1. 在 defaultConfig 下添加 ndk 配置。 如下

defaultConfig{
   ...
   ndk {
         abiFilters "armeabi", "armeabi-v7a"
   }
   ...
}

2. 添加打包配置参数

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

最后一步、修改 proguard-rules.pro ，添加下面这一行

```
-keep class com.tencent.mm.sdk.** { *; }
```

#### iOS 使用注意 🐒

在检查完链接成功后，需要在命令行中 cd 到当前项目的 iOS 路径下

执行： pod install

## 接口说明：🍎

### 拉流 🍖🍔🍟

一共导入两个模块

**import { LivePullManager,LivePullView } from 'react-native-tencent-live';**

**🔆LivePullView**

LivePullView 是负责渲染直播画面的组件（View）

需要配置其宽高参数、其他参数无需设置

```
...
<LivePullView style={{height:1920,width:1080}}/>
...
```

**🔆LivePullManager**

该模块是拉流配置的核心

其中一共包含 4 个方法，一个枚举参数

🔨 setLicence 该方法必须在使用直播前全局调用一次，用来配置腾讯直播 SDK 的授权和密钥

```
setLicence: ( licenceUrl:string,licenceKey:string ) => void
```

🔨startPull 调用该方法、开始拉流。需要传入 **拉流地址** 以及 **流类型** （主要为 RTMP 和 FLV 两种, 如果不传类型的话则默认为 RTMP 类型）。调用该方法后，如果组件树中存在 <LivePullView /> 的话则会开始连接服务器，获取画面和音频，然后便可以接收直播了。

```
startPull:(url: string,type: LIVE_TYPE) => void

LIVE_TYPE:{
    RTMP: LIVE_TYPE,
    FLV: LIVE_TYPE
}

使用方法：
LivePullManager.startPull("rtmp://...", LivePullManager.LIVE_TYPE.RTMP);
```

🔨stopPull 调用该方法、停止拉流。 如果退出直播，一定要调用该方法 ⚠️⚠️⚠️。否则可能造成内存泄漏或者闪屏等。我已经在原生端的 LivePullView 的销毁生命周期中调用了该方法。但为了保证稳定性。在不需要直播 view 的时候切记要调用该方法！！😈

```
stopPull:() => void,
```

🔨subscribe 调用该方法，传入一个回调函数。回调函数的这个参数会是原生端直播事件发送过来的通知文本。

```
subscribe:(event: keyof EVENT_TYPE,callback: (result:any)=> void ) => EmitterSubscription
```

⚠️ 不需要再监听某个事件后，一定要移除订阅

subscribe 使用示例

```
const begin_subscription = LivePullManager.subscribe(
'PLAY_EVT_CONNECT_SUCC',(result) => {
    console.log("连接服务器成功否监听事件: ",result);
})

当不需要该事件监听后
begin_subscription.remove();
```

##### 当前已封装的事件有:

|                              |       |                                                                                                  |
| ---------------------------- | ----- | ------------------------------------------------------------------------------------------------ |
| PLAY_EVT_CONNECT_SUCC        | 2001  | 已经连接服务器                                                                                   |
| PLAY_EVT_RTMP_STREAM_BEGIN   | 2002  | 已经连接服务器，开始拉流（仅播放 RTMP 地址时会抛送）                                             |
| PLAY_EVT_PLAY_BEGIN          | 2004  | 视频播放开始                                                                                     |
| PLAY_EVT_PLAY_END            | 2006  | 播放结束，HTTP-FLV 的直播流是不抛这个事件的\*                                                    |
| PLAY_EVT_PLAY_LOADING        | 2007  | 视频播放进入缓冲状态，缓冲结束之后会有 PLAY_BEGIN 事件                                           |
| PLAY_EVT_START_VIDEO_DECODER | 2008  | 视频解码器开始启动（2.0 版本以后新增）                                                           |
| PLAY_EVT_STREAM_SWITCH_SUCC  | 2015  | 直播流切换完成                                                                                   |
| PLAY_ERR_NET_DISCONNECT      | -2301 | 网络断连，且经多次重连亦不能恢复，更多重试请自行重启播放 (这个事件可用作 FLV 播放结束的判断标识) |
| NET_STATUS_NET_SPEED         |       | 当前的网络数据接收速度                                                                           |
| NET_STATUS_NET_JITTER        |       | 网络抖动情况，抖动越大，网络越不稳定\*                                                           |
|                              |       |                                                                                                  |

### 推流 🍖🍔🍟

一共导入两个模块

**import { LivePushManager,LivePushView } from 'react-native-tencent-live';**

**🔆LivePushView**

该组件只用于展示摄像头预览（给主播自己看）, 必须设置宽高参数，其他无需设置

```
...
<LivePushView style={{height:1920,width:1080}}/>
...
```

**🔆LivePushManager**

该模块是推流配置的核心

其中目前一共包含 14 个方法

🔨 preview 该方法在添加好 **LivePushView**之后用于触发预览画面渲染。启动预览需要应用已经获取摄像头权限，而直播还需要麦克风权限，⚠️⚠️⚠️ 所以在调用该方法前请先确保应用成功获取到这两个权限。

**（注：现在已默认打开摄像头预览，该方法无需再调用）**

```
preview: () => void
```

🔨 startPush 该方法用于启动推流、即将画面推送出去。使得拉流端用户可以获取画面和音频。需要传入推流地址

```
startPush: (url:string) => void
```

🔨 stopPush 该方法用于停止推流，调用该方法后会停止推流、并且原生端会清除创建的预览画面容器 View 和 它相关配置参数。在结束直播时需要调用该方法！

```
stopPush: () => void
```

🔨 switchCamera 该方法用于在前后摄像头之间进行切换，默认打开 前置摄像头

```
switchCamera: () => void
```

🔨 setUserVideoQuality 该方法用于设置观众端的画面清晰度。主播端的画面总是以原画推送到服务器。

```
setUserVideoQuality: (quality: keyof QUALITY) => void

三个清晰度选项 : LOW (正常), MEDIUM (高清), HIGH (超清)
```

🔨 setMirrorEnabled 主播画面是否开启镜像模式 ( 开启后主播看到的手机画面和观众看到的画面一致)

```
setMirrorEnabled: (enabled: boolean) => void
```

🔨 setOrientaionMode 设置画面方向( 竖屏 、 横屏 )

```
setOrientaionMode: (orientation: keyof ORIENTATION) => void

两个方向选项：    PORTRAIT  , LANDSCAPE
```

🔨 setAudioPreviewEnabled 耳返开关，打开耳返则主播可以在耳机中实时听到直播间里的声音

```
setAudioPreviewEnabled: (enabled: boolean) => void
```

🔨 setFakeVoice 变声 (支付 50w 后可用)

```
setFakeVoice: (voicetype:VOICETYPE) => void

export enum VOICETYPE {
    close = 0,					//关闭变声
    wild_kids,					//熊孩子
    lolita,							//萝莉
    uncle,							//大叔
    heavy_metal,				//重金属
    cold,								//感冒
    foreigner,					//外国人
    trapped_beast,			//困兽
    fat_ass,						//死肥仔
    electric,						//强电流
    heavy_machine,			//重机械
    ethereal						//空灵
}
```

🔨 setBeautyMode 设置美颜模式

```
setBeautyMode: (beautystyle: keyof BEAUTYSTYLE) => void

参数一共两个选项 :
	SMOOTH		//	光滑
	NATURAL   //  自然
```

🔨 setBeautyLevel 设置磨皮程度

```
setBeautyLevel: (level:number) => void

参数值范围： 0 - 9
```

🔨 setWhitenessLevel 设置美白程度

```
setWhitenessLevel: (level:number) => void

参数值范围： 0 - 9
```

🔨 setRuddinessLevel 设置红润程度

```
setRuddinessLevel: (level:number) => void

参数值范围： 0 - 9
```

🔨 subscribe 添加监听事件,用法同拉流

```
subscribe:(event: keyof EVENT_TYPE,callback:(result:any) => void ) => EmitterSubscription
```

😈 不需要监听事件后记得销毁！🔆🍟🍔🍉🍖🍔

当前已有事件:

|                                 |       |                                                                       |
| ------------------------------- | ----- | --------------------------------------------------------------------- |
| PUSH_EVT_OPEN_CAMERA_SUCC       | 1001  | 已经成功连接到腾讯云推流服务器。                                      |
| PUSH_EVT_PUSH_BEGIN             | 1002  | 与服务器握手完毕，一切正常，准备开始推流。                            |
| PUSH_EVT_OPEN_CAMERA_SUCC       | 1003  | 推流器已成功打开摄像头（部分 Android 手机在此过程需要耗时 1s - 2s）。 |
| PUSH_ERR_OPEN_CAMERA_FAIL       | -1301 | 打开摄像头失败。                                                      |
| PUSH_ERR_OPEN_MIC_FAIL          | -1302 | 打开麦克风失败。                                                      |
| PUSH_ERR_VIDEO_ENCODE_FAIL      | -1303 | 视频编码失败。                                                        |
| PUSH_ERR_AUDIO_ENCODE_FAIL      | -1304 | 音频编码失败。                                                        |
| PUSH_ERR_UNSUPPORTED_RESOLUTION | -1305 | 不支持的视频分辨率。                                                  |
| PUSH_ERR_UNSUPPORTED_SAMPLERATE | -1306 | 不支持的音频采样率。                                                  |
| PUSH_ERR_NET_DISCONNECT         | -1307 | 网络断连，且经三次重连无效，更多重试请自行重启推流。                  |
| PUSH_WARNING_NET_BUSY           | 1101  | 网络状况不佳：上行带宽太小，上传数据受阻。                            |
| PUSH_WARNING_SERVER_DISCONNECT  | 3004  | RTMP 服务器主动断开连接（会触发重试流程）。                           |
|                                 |       |                                                                       |
