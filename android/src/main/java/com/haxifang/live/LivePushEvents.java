package com.haxifang.live;

import android.os.Bundle;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.tencent.rtmp.ITXLivePushListener;

import static com.tencent.rtmp.TXLiveConstants.PUSH_ERR_AUDIO_ENCODE_FAIL;
import static com.tencent.rtmp.TXLiveConstants.PUSH_ERR_NET_DISCONNECT;
import static com.tencent.rtmp.TXLiveConstants.PUSH_ERR_OPEN_CAMERA_FAIL;
import static com.tencent.rtmp.TXLiveConstants.PUSH_ERR_OPEN_MIC_FAIL;
import static com.tencent.rtmp.TXLiveConstants.PUSH_ERR_UNSUPPORTED_RESOLUTION;
import static com.tencent.rtmp.TXLiveConstants.PUSH_ERR_UNSUPPORTED_SAMPLERATE;
import static com.tencent.rtmp.TXLiveConstants.PUSH_ERR_VIDEO_ENCODE_FAIL;
import static com.tencent.rtmp.TXLiveConstants.PUSH_EVT_CONNECT_SUCC;
import static com.tencent.rtmp.TXLiveConstants.PUSH_EVT_OPEN_CAMERA_SUCC;
import static com.tencent.rtmp.TXLiveConstants.PUSH_EVT_PUSH_BEGIN;
import static com.tencent.rtmp.TXLiveConstants.PUSH_WARNING_NET_BUSY;
import static com.tencent.rtmp.TXLiveConstants.PUSH_WARNING_SERVER_DISCONNECT;

public class LivePushEvents implements ITXLivePushListener {
    
    private final String TAG = "直播推流事件";

    private static final String PLAY_EVT = "PLAY_EVT";
    private static final String ERROR_EVT = "ERROR_EVT";
    private static final String WARNING_EVT = "WARNING_EVT";


    @Override
    public void onPushEvent(int i, Bundle bundle) {
        ReactApplicationContext reactApplicationContext = LiveModule.reactApplicationContext;
        if(reactApplicationContext != null){
            if(i == PUSH_EVT_CONNECT_SUCC){
                Log.i(TAG, "onPushEvent: 已经成功连接到推流服务器");
                WritableMap map = Arguments.createMap();
                map.putString("PUSH_EVT_CONNECT_SUCC","已经成功连接到推流服务器");
                LiveModule.sendEvent(PLAY_EVT,map);
            }else if(i == PUSH_EVT_PUSH_BEGIN){
                Log.i(TAG, "onPushEvent: 与服务器握手完毕，一切正常，开始推流");
                WritableMap map = Arguments.createMap();
                map.putString("PUSH_EVT_PUSH_BEGIN","与服务器握手完毕，一切正常，开始推流");
                LiveModule.sendEvent(PLAY_EVT,map);
            }else if(i == PUSH_EVT_OPEN_CAMERA_SUCC){
                Log.i(TAG, "onPushEvent: 推流器已成功打开摄像头");
                WritableMap map = Arguments.createMap();
                map.putString("PUSH_EVT_OPEN_CAMERA_SUCC","推流器已成功打开摄像头");
                LiveModule.sendEvent(PLAY_EVT,map);
            }else if(i == PUSH_ERR_OPEN_CAMERA_FAIL){
                Log.i(TAG, "onPushEvent: 打开摄像头失败");
                WritableMap map = Arguments.createMap();
                map.putString("PUSH_ERR_OPEN_CAMERA_FAIL","打开摄像头失败");
                LiveModule.sendEvent(ERROR_EVT,map);
            }else if(i == PUSH_ERR_OPEN_MIC_FAIL){
                Log.i(TAG, "onPushEvent: 打开麦克风失败");
                WritableMap map = Arguments.createMap();
                map.putString("PUSH_ERR_OPEN_MIC_FAIL","打开麦克风失败");
                LiveModule.sendEvent(ERROR_EVT,map);
            }else if(i == PUSH_ERR_VIDEO_ENCODE_FAIL){
                Log.i(TAG, "onPushEvent: 视频编码失败");
                WritableMap map = Arguments.createMap();
                map.putString("PUSH_ERR_VIDEO_ENCODE_FAIL","视频编码失败");
                LiveModule.sendEvent(ERROR_EVT,map);
            }else if(i == PUSH_ERR_AUDIO_ENCODE_FAIL){
                Log.i(TAG, "onPushEvent: 音频编码失败");
                WritableMap map = Arguments.createMap();
                map.putString("PUSH_ERR_AUDIO_ENCODE_FAIL","音频编码失败");
                LiveModule.sendEvent(ERROR_EVT,map);
            }else if(i == PUSH_ERR_UNSUPPORTED_RESOLUTION){
                WritableMap map = Arguments.createMap();
                map.putString("PUSH_ERR_UNSUPPORTED_RESOLUTION","不支持的视频分辨率");
                LiveModule.sendEvent(ERROR_EVT,map);
            }else if(i == PUSH_ERR_UNSUPPORTED_SAMPLERATE){
                WritableMap map = Arguments.createMap();
                map.putString("PUSH_ERR_UNSUPPORTED_SAMPLERATE","不支持的音频采样率");
                LiveModule.sendEvent(ERROR_EVT,map);
            }else if(i == PUSH_ERR_NET_DISCONNECT){
                Log.i(TAG, "onPushEvent: 网络断连，且经三次重连无效，更多重试请自行重启推流");
                WritableMap map = Arguments.createMap();
                map.putString("PUSH_ERR_NET_DISCONNECT","网络断连，且经三次重连无效，更多重试请自行重启推流");
                LiveModule.sendEvent(ERROR_EVT,map);
            }else if(i == PUSH_WARNING_NET_BUSY){
                Log.i(TAG, "onPushEvent: 网络状况不佳：上行带宽太小，上传数据受阻");
                WritableMap map = Arguments.createMap();
                map.putString("PUSH_WARNING_NET_BUSY","网络状况不佳：上行带宽太小，上传数据受阻");
                LiveModule.sendEvent(WARNING_EVT,map);
            }else if(i == PUSH_WARNING_SERVER_DISCONNECT){
                Log.i(TAG, "onPushEvent: 服务器主动断开连接（会触发重试流程）");
                WritableMap map = Arguments.createMap();
                map.putString("PUSH_WARNING_SERVER_DISCONNECT","服务器主动断开连接（会触发重试流程）");
                LiveModule.sendEvent(WARNING_EVT,map);
            }else{

            }
        }
    }

    @Override
    public void onNetStatus(Bundle bundle) {

    }
}
