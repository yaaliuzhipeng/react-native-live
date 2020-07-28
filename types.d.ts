import { EmitterSubscription, ViewStyle } from 'react-native';

export enum LIVE_TYPE {
    RTMP,
    FLV
}

export interface QUALITY {
    LOW: string,
    MEDIUM: string,
    HIGH: string;
}

export interface ORIENTATION {
    PORTRAIT: string,
    LANDSCAPE: string;
}

export interface BEAUTYSTYLE {
    SMOOTH: string,
    NATURAL: string;
}

//  VOICECHANGER_TYPE_0          = 0,     ///< 关闭变声
//  VOICECHANGER_TYPE_1          = 1,     ///< 熊孩子
//  VOICECHANGER_TYPE_2          = 2,     ///< 萝莉
//  VOICECHANGER_TYPE_3          = 3,     ///< 大叔
//  VOICECHANGER_TYPE_4          = 4,     ///< 重金属
//  VOICECHANGER_TYPE_5          = 5,     ///< 感冒
//  VOICECHANGER_TYPE_6          = 6,     ///< 外国人
//  VOICECHANGER_TYPE_7          = 7,     ///< 困兽
//  VOICECHANGER_TYPE_8          = 8,     ///< 死肥仔
//  VOICECHANGER_TYPE_9          = 9,     ///< 强电流
//  VOICECHANGER_TYPE_10         = 10,    ///< 重机械
//  VOICECHANGER_TYPE_11         = 11,    ///< 空灵
export enum VOICETYPE {
    close = 0,
    wild_kids,
    lolita,
    uncle,
    heavy_metal,
    cold,
    foreigner,
    trapped_beast,
    fat_ass,
    electric,
    heavy_machine,
    ethereal
}
export interface EVENT_TYPE {
    PLAY_EVT_CONNECT_SUCC: string, //已经连接服务器 2001
    PLAY_EVT_RTMP_STREAM_BEGIN: string,  //已经连接服务器，开始拉流（仅播放 RTMP 地址时会抛送） 2002
    PLAY_EVT_PLAY_BEGIN: string, //视频播放开始 2004
    PLAY_EVT_PLAY_END: string, //播放结束，HTTP-FLV 的直播流是不抛这个事件的
    PLAY_EVT_PLAY_LOADING: string, //视频播放进入缓冲状态，缓冲结束之后会有 PLAY_BEGIN 事件 2007
    PLAY_EVT_START_VIDEO_DECODER: string, //视频解码器开始启动（2.0 版本以后新增）2008
    PLAY_EVT_STREAM_SWITCH_SUCC: string, //直播流切换完成 2015
    PLAY_ERR_NET_DISCONNECT: string, //网络断连，且经多次重连亦不能恢复，更多重试请自行重启播放 (这个事件可用作FLV播放结束的判断标识) -2301
    PLAY_WARNING_RECONNECT: string, //网络错误，自动重连
    NET_STATUS_NET_SPEED: string, //当前的网络数据接收速度
    NET_STATUS_NET_JITTER: string, //网络抖动情况，抖动越大，网络越不稳定



    PUSH_EVT_CONNECT_SUCC: string, //下面为  PLAY_EVT 事件类型 ||  
    PUSH_EVT_PUSH_BEGIN: string,
    PUSH_EVT_OPEN_CAMERA_SUCC: string,
    PUSH_ERR_OPEN_CAMERA_FAIL: string, // 下面为 ERROR_EVT 事件类型
    PUSH_ERR_OPEN_MIC_FAIL: string,
    PUSH_ERR_VIDEO_ENCODE_FAIL: string,
    PUSH_ERR_AUDIO_ENCODE_FAIL: string,
    PUSH_ERR_UNSUPPORTED_RESOLUTION: string,
    PUSH_ERR_UNSUPPORTED_SAMPLERATE: string,
    PUSH_ERR_NET_DISCONNECT: string,
    PUSH_WARNING_NET_BUSY: string, // 下面为 WARNING_EVT 事件类型
    PUSH_WARNING_SERVER_DISCONNECT: string;
}

declare const LivePullManager: {
    setLicence: (licenceUrl: string, licenceKey: string) => void;
    startPull: (url: string, type?: LIVE_TYPE) => void;
    stopPull: () => void;

    LIVE_TYPE: {
        RTMP: LIVE_TYPE,
        FLV: LIVE_TYPE;
    },
    subscrib: (event: keyof EVENT_TYPE, callback: (result: any) => void) => EmitterSubscription;
};



declare const LivePushManager: {
    preview: () => void;
    startPush: (url: string) => void;
    switchCamera: () => void;
    stopPush: () => void;
    setUserVideoQuality: (quality: string) => void;
    setMirrorEnabled: (enabled: boolean) => void;
    setOrientaionMode: (orientation: string) => void;
    setAudioPreviewEnabled: (enabled: boolean) => void;
    setFakeVoice: (voicetype: number) => void;
    setBeautyMode: (beautystyle: string) => void;
    setBeautyLevel: (level: number) => void;
    setWhitenessLevel: (level: number) => void;
    setRuddinessLevel: (level: number) => void;
    setManualFocus: (enabled: boolean) => void;

    //创建监听事件
    subscribe: (event: keyof EVENT_TYPE, callback: (result: any) => void) => EmitterSubscription,

};

declare const LivePullView: (props: { style: ViewStyle; }) => JSX.Element;
declare const LivePushView: (props: { style: ViewStyle; }) => JSX.Element;

export { LivePullView, LivePullManager, LivePushManager, LivePushView };