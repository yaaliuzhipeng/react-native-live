import {
  NativeModules,
  NativeEventEmitter,
  Platform,
  EmitterSubscription
} from "react-native";

export enum LIVE_TYPE {
  RTMP,
  FLV
}

interface EVENT_TYPE {
  PLAY_EVT_CONNECT_SUCC: string; //已经连接服务器 2001
  PLAY_EVT_RTMP_STREAM_BEGIN: string; //已经连接服务器，开始拉流（仅播放 RTMP 地址时会抛送） 2002
  PLAY_EVT_PLAY_BEGIN: string; //视频播放开始 2004
  PLAY_EVT_PLAY_END: string; //播放结束，HTTP-FLV 的直播流是不抛这个事件的
  PLAY_EVT_PLAY_LOADING: string; //视频播放进入缓冲状态，缓冲结束之后会有 PLAY_BEGIN 事件 2007
  PLAY_EVT_START_VIDEO_DECODER: string; //视频解码器开始启动（2.0 版本以后新增）2008
  PLAY_EVT_STREAM_SWITCH_SUCC: string; //直播流切换完成 2015
  PLAY_ERR_NET_DISCONNECT: string; //网络断连，且经多次重连亦不能恢复，更多重试请自行重启播放 (这个事件可用作FLV播放结束的判断标识) -2301
  PLAY_WARNING_RECONNECT: string; //网络连接错误，启动自动重连
  NET_STATUS_NET_SPEED: string; //当前的网络数据接收速度
  NET_STATUS_NET_JITTER: string; //网络抖动情况，抖动越大，网络越不稳定
}

const _play_events = [
  "PLAY_EVT_CONNECT_SUCC",
  "PLAY_EVT_RTMP_STREAM_BEGIN",
  "PLAY_EVT_PLAY_BEGIN",
  "PLAY_EVT_PLAY_END",
  "PLAY_EVT_PLAY_LOADING",
  "PLAY_EVT_START_VIDEO_DECODER",
  "PLAY_EVT_STREAM_SWITCH_SUCC",
  "PLAY_ERR_NET_DISCONNECT",
  "PLAY_WARNING_RECONNECT"
];
const _net_status = ["NET_STATUS_NET_SPEED", "NET_STATUS_NET_JITTER"];

var LiveModule: {
  setLicence: (licenceUrl: string, licenceKey: string) => void;
  startPull: (url: string, type?: LIVE_TYPE) => void;
  stopPull: () => void;
};

var listener: NativeEventEmitter; //事件接收器

var setLicence: (licenceUrl: string, licenceKey: string) => void; // 配置直播授权
var startPull: (url: string, type: LIVE_TYPE) => void; // 开始拉流
var stopPull: () => void; // 停止拉流

var subscribe: (
  event: keyof EVENT_TYPE,
  callback: (result: any) => void
) => EmitterSubscription; //创建监听事件

// Live模块
LiveModule = NativeModules.LiveModule;
// 事件监听器
listener = new NativeEventEmitter(LiveModule);

setLicence = (licenceUrl: string, licenceKey: string) => {
  LiveModule.setLicence(licenceUrl, licenceKey);
};
startPull = (url: string, type: LIVE_TYPE) => {

  if (type == LIVE_TYPE.RTMP) {
    LiveModule.startPull(url, 0);
  } else if (type == LIVE_TYPE.FLV) {
    LiveModule.startPull(url, 1);
  }else{
    LiveModule.startPull(url,0);
  }
};
stopPull = LiveModule.stopPull;

/**
 *  Example :
 *
 *  假设需要监听直播结束事件
 *  const EndSubscription = subscribe(EVENT_TYPE.PLAY_EVT_PLAY_END , (reminder) => {
 *      console.log("接收到结束事件通知，事件通知内容为: ",reminder.PLAY_EVT_PLAY_END );
 *  });
 *
 *  不再需要监听后记得销毁订阅
 *  EndSubscription.remove();
 */
subscribe = (event: keyof EVENT_TYPE, callback: (result: string) => any) => {
  /**
   *  可用监听事件类型：
   *   1. PLAY_EVT
   *   2. NET_STATUS
   */
  if (_play_events.indexOf(event) >= 0) {
    if (event == "PLAY_EVT_CONNECT_SUCC") {
      return listener.addListener("PLAY_EVT", reminder => {
        if (reminder.PLAY_EVT_CONNECT_SUCC) {
          callback(reminder.PLAY_EVT_CONNECT_SUCC);
        }
      });
    } else if (event == "PLAY_EVT_RTMP_STREAM_BEGIN") {
      return listener.addListener("PLAY_EVT", reminder => {
        if (reminder.PLAY_EVT_RTMP_STREAM_BEGIN) {
          callback(reminder.PLAY_EVT_RTMP_STREAM_BEGIN);
        }
      });
    } else if (event == "PLAY_EVT_PLAY_BEGIN") {
      return listener.addListener("PLAY_EVT", reminder => {
        if (reminder.PLAY_EVT_PLAY_BEGIN) {
          callback(reminder.PLAY_EVT_PLAY_BEGIN);
        }
      });
    } else if (event == "PLAY_EVT_PLAY_END") {
      return listener.addListener("PLAY_EVT", reminder => {
        if (reminder.PLAY_EVT_PLAY_END) {
          callback(reminder.PLAY_EVT_PLAY_END);
        }
      });
    } else if (event == "PLAY_EVT_PLAY_LOADING") {
      return listener.addListener("PLAY_EVT", reminder => {
        if (reminder.PLAY_EVT_PLAY_LOADING) {
          callback(reminder.PLAY_EVT_PLAY_LOADING);
        }
      });
    } else if (event == "PLAY_EVT_START_VIDEO_DECODER") {
      return listener.addListener("PLAY_EVT", reminder => {
        if (reminder.PLAY_EVT_START_VIDEO_DECODER) {
          callback(reminder.PLAY_EVT_START_VIDEO_DECODER);
        }
      });
    } else if (event == "PLAY_EVT_STREAM_SWITCH_SUCC") {
      return listener.addListener("PLAY_EVT", reminder => {
        if (reminder.PLAY_EVT_STREAM_SWITCH_SUCC) {
          callback(reminder.PLAY_EVT_STREAM_SWITCH_SUCC);
        }
      });
    } else if (event == "PLAY_ERR_NET_DISCONNECT") {
      return listener.addListener("PLAY_EVT", reminder => {
        if (reminder.PLAY_ERR_NET_DISCONNECT) {
          callback(reminder.PLAY_ERR_NET_DISCONNECT);
        }
      });
    } else if (event == "PLAY_WARNING_RECONNECT") {
      return listener.addListener("PLAY_EVT", reminder => {
        if (reminder.PLAY_WARNING_RECONNECT) {
          callback(reminder.PLAY_WARNING_RECONNECT);
        }
      });
    }
  }

  if (event == _net_status[0]) {
    return listener.addListener("NET_STATUS", reminder => {
      if (reminder.NET_STATUS_NET_SPEED) {
        callback(reminder.NET_STATUS_NET_SPEED);
      }
    });
  }
  return listener.addListener("NET_STATUS", reminder => {
    if (reminder.NET_STATUS_NET_JITTER) {
      callback(reminder.NET_STATUS_NET_JITTER);
    }
  });
};
export default  {
  setLicence,
  startPull,
  stopPull,
  LIVE_TYPE,
  subscribe
}
