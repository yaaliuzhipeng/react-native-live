import { NativeModules, NativeEventEmitter, Platform, EmitterSubscription } from 'react-native';

interface EVENT_TYPE {
    PUSH_EVT_CONNECT_SUCC: string, //下面为  PLAY_EVT 事件类型
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

//直播推流控制器
var LiveModule: {
    preview: () => void,
    startPush: (url: string) => void,
    switchCamera: () => void,
    stopPush: () => void,
    setUserVideoQuality: (quality: string) => void,
    setMirrorEnabled: (enabled: boolean) => void,
    setOrientaionMode: (orientation: string) => void,
    setAudioPreviewEnabled: (enabled: boolean) => void,
    setManualFocus: (enabled: boolean) => void,
    setFakeVoice: (voicetype: number) => void,
    setBeautyMode: (beautystyle: string) => void,
    setBeautyLevel: (level: number) => void,
    setWhitenessLevel: (level: number) => void,
    setRuddinessLevel: (level: number) => void;
};

var listener: NativeEventEmitter; //事件接收器

var preview: () => void;
var startPush: (url: string) => void;
var switchCamera: () => void;
var stopPush: () => void;
var setUserVideoQuality: (quality: string) => void;
var setMirrorEnabled: (enabled: boolean) => void;
var setOrientaionMode: (orientation: string) => void;
var setAudioPreviewEnabled: (enabled: boolean) => void;
var setFakeVoice: (voicetype: number) => void;
var setBeautyMode: (beautystyle: string) => void;
var setBeautyLevel: (level: number) => void;
var setWhitenessLevel: (level: number) => void;
var setRuddinessLevel: (level: number) => void;
var setManualFocus: (enabled: boolean) => void;

//创建监听事件
var subscribe: (event: keyof EVENT_TYPE, callback: (result: any) => void) => EmitterSubscription;

//Live模块
LiveModule = NativeModules.LiveModule;
listener = new NativeEventEmitter(LiveModule);

/**
 *  打开摄像头预览，使用此方法前必须确保组件树中存在 LivePushView ，否则无效
 */
preview = () => {
    LiveModule.preview();
};

startPush = (url: string) => {
    LiveModule.startPush(url);
};

switchCamera = () => {
    LiveModule.switchCamera();
};

stopPush = () => {
    LiveModule.stopPush();
};

setUserVideoQuality = (quality: string) => {
    LiveModule.setUserVideoQuality(quality);
};

setMirrorEnabled = (enabled: boolean) => {
    LiveModule.setMirrorEnabled(enabled);
};

setOrientaionMode = (orientation: string) => {
    LiveModule.setOrientaionMode(orientation);
};

setAudioPreviewEnabled = (enabled: boolean) => {
    LiveModule.setAudioPreviewEnabled(enabled);
};

setManualFocus = (enabled: boolean) => {
    LiveModule.setManualFocus(enabled);
};

setFakeVoice = (voicetype: number) => {
    LiveModule.setFakeVoice(voicetype);
};

setBeautyMode = (beautystyle: string) => {
    LiveModule.setBeautyMode(beautystyle);
};

setBeautyLevel = (level: number) => {
    LiveModule.setBeautyLevel(level);
};

setWhitenessLevel = (level: number) => {
    LiveModule.setWhitenessLevel(level);
};

setRuddinessLevel = (level: number) => {
    LiveModule.setRuddinessLevel(level);
};

subscribe = (event: keyof EVENT_TYPE, callback: (result: any) => void) => {
    if (event == 'PUSH_EVT_CONNECT_SUCC') {
        return listener.addListener('PLAY_EVT', (reminder) => {
            if (reminder.PUSH_EVT_CONNECT_SUCC) {
                callback(reminder.PUSH_EVT_CONNECT_SUCC);
            }
        });
    } else if (event == 'PUSH_EVT_PUSH_BEGIN') {
        return listener.addListener('PLAY_EVT', (reminder) => {
            if (reminder.PUSH_EVT_PUSH_BEGIN) {
                callback(reminder.PUSH_EVT_PUSH_BEGIN);
            }
        });
    } else if (event == 'PUSH_EVT_OPEN_CAMERA_SUCC') {
        return listener.addListener('PLAY_EVT', (reminder) => {
            console.log('传给回调函数前拿到的事件结果为 : ', reminder.PUSH_EVT_OPEN_CAMERA_SUCC);
            if (reminder.PUSH_EVT_OPEN_CAMERA_SUCC) {
                callback(reminder.PUSH_EVT_OPEN_CAMERA_SUCC);
            }
        });
    } else if (event == 'PUSH_ERR_OPEN_CAMERA_FAIL') {
        //ERROR_EVT
        return listener.addListener('ERROR_EVT', (reminder) => {
            if (reminder.PUSH_ERR_OPEN_CAMERA_FAIL) {
                callback(reminder.PUSH_ERR_OPEN_CAMERA_FAIL);
            }
        });
    } else if (event == 'PUSH_ERR_OPEN_MIC_FAIL') {
        return listener.addListener('ERROR_EVT', (reminder) => {
            if (reminder.PUSH_ERR_OPEN_MIC_FAIL) {
                callback(reminder.PUSH_ERR_OPEN_MIC_FAIL);
            }
        });
    } else if (event == 'PUSH_ERR_VIDEO_ENCODE_FAIL') {
        return listener.addListener('ERROR_EVT', (reminder) => {
            if (reminder.PUSH_ERR_VIDEO_ENCODE_FAIL) {
                callback(reminder.PUSH_ERR_VIDEO_ENCODE_FAIL);
            }
        });
    } else if (event == 'PUSH_ERR_AUDIO_ENCODE_FAIL') {
        return listener.addListener('ERROR_EVT', (reminder) => {
            if (reminder.PUSH_ERR_AUDIO_ENCODE_FAIL) {
                callback(reminder.PUSH_ERR_AUDIO_ENCODE_FAIL);
            }
        });
    } else if (event == 'PUSH_ERR_UNSUPPORTED_RESOLUTION') {
        return listener.addListener('ERROR_EVT', (reminder) => {
            if (reminder.PUSH_ERR_UNSUPPORTED_RESOLUTION) {
                callback(reminder.PUSH_ERR_UNSUPPORTED_RESOLUTION);
            }
        });
    } else if (event == 'PUSH_ERR_UNSUPPORTED_SAMPLERATE') {
        return listener.addListener('ERROR_EVT', (reminder) => {
            if (reminder.PUSH_ERR_UNSUPPORTED_SAMPLERATE) {
                callback(reminder.PUSH_ERR_UNSUPPORTED_SAMPLERATE);
            }
        });
    } else if (event == 'PUSH_ERR_NET_DISCONNECT') {
        return listener.addListener('ERROR_EVT', (reminder) => {
            if (reminder.PUSH_ERR_NET_DISCONNECT) {
                callback(reminder.PUSH_ERR_NET_DISCONNECT);
            }
        });
    } else if (event == 'PUSH_WARNING_NET_BUSY') {
        return listener.addListener('WARNING_EVT', (reminder) => {
            if (reminder.PUSH_WARNING_NET_BUSY) {
                callback(reminder.PUSH_WARNING_NET_BUSY);
            }
        });
    } else {
        // 'PUSH_WARNING_SERVER_DISCONNECT'
        return listener.addListener('WARNING_EVT', (reminder) => {
            if (reminder.PUSH_WARNING_SERVER_DISCONNECT) {
                callback(reminder.PUSH_WARNING_SERVER_DISCONNECT);
            }
        });
    }
};

export {
    preview,
    startPush,
    switchCamera,
    stopPush,
    setUserVideoQuality,
    setMirrorEnabled,
    setOrientaionMode,
    setAudioPreviewEnabled,
    setManualFocus,
    setFakeVoice,
    setBeautyMode,
    setBeautyLevel,
    setWhitenessLevel,
    setRuddinessLevel,
    subscribe
};