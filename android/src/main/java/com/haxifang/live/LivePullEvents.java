package com.haxifang.live;

import android.os.Bundle;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.tencent.rtmp.ITXLivePlayListener;

public class LivePullEvents implements ITXLivePlayListener {
    private final String TAG = "直播拉流事件: ";
    @Override
    public void onPlayEvent(int i, Bundle bundle) {
        // 直播 SDK 事件通知
        WritableMap params = Arguments.createMap();
        switch (i) {
            case 2001:
                Log.i(TAG, "onPlayEvent: PLAY_EVT_CONNECT_SUCC");
                params.putString("PLAY_EVT_CONNECT_SUCC", i + "");
                break;
            case 2002:
                Log.i(TAG, "onPlayEvent: PLAY_EVT_RTMP_STREAM_BEGIN");
                params.putString("PLAY_EVT_RTMP_STREAM_BEGIN", i + "");
                break;
            case 2003:
                Log.i(TAG, "onPlayEvent: PLAY_EVT_RCV_FIRST_I_FRAME");
                params.putString("PLAY_EVT_RCV_FIRST_I_FRAME", i + "");
                break;
            case 2004:
                Log.i(TAG, "onPlayEvent: PLAY_EVT_PLAY_BEGIN");
                params.putString("PLAY_EVT_PLAY_BEGIN", i + "");
                break;
            case 2005:
                Log.i(TAG, "onPlayEvent: PLAY_EVT_PLAY_PROGRESS");
                params.putString("PLAY_EVT_PLAY_PROGRESS", i + "");
                break;
            case 2006:
                Log.i(TAG, "onPlayEvent: PLAY_EVT_PLAY_END");
                params.putString("PLAY_EVT_PLAY_END", i + "");
                break;
            case 2007:
                Log.i(TAG, "onPlayEvent: PLAY_EVT_PLAY_LOADING");
                params.putString("PLAY_EVT_PLAY_LOADING", i + "");
                break;
            case 2008:
                Log.i(TAG, "onPlayEvent: PLAY_EVT_START_VIDEO_DECODER");
                params.putString("PLAY_EVT_START_VIDEO_DECODER", i + "");
                break;
            case 2009:
                params.putString("PLAY_EVT_CHANGE_RESOLUTION", i + "");
                break;
            case 2010:
                params.putString("PLAY_EVT_GET_PLAYINFO_SUCC", i + "");
                break;
            case 2011:
                params.putString("PLAY_EVT_CHANGE_ROTATION", i + "");
                break;
            case 2012:
                params.putString("PLAY_EVT_GET_MESSAGE", i + "");
                break;
            case 2013:
                params.putString("PLAY_EVT_VOD_PLAY_PREPARED", i + "");
                break;
            case 2014:
                params.putString("PLAY_EVT_VOD_LOADING_END", i + "");
                break;
            case 2015:
                params.putString("PLAY_EVT_STREAM_SWITCH_SUCC", i + "");
                break;
            case -2301:
                params.putString("PLAY_ERR_NET_DISCONNECT",i + "");
                Log.i("直播拉流监听事件: ","严重错误 -2301");
            case 2103:
                params.putString("PLAY_WARNING_RECONNECT",i + "");
                Log.i(TAG, "onPlayEvent: 直播重启连接事件触发");
                break;
        }
        LiveModule.sendEvent("PLAY_EVT",params);
    }

    @Override
    public void onNetStatus(Bundle bundle) {
        WritableMap params = Arguments.createMap();
        params.putString("NET_STATUS_CPU_USAGE",bundle.getInt("CPU_USAGE") + "");
        params.putString("NET_STATUS_NET_SPEED",bundle.getInt("NET_SPEED") + "");
        params.putString("NET_STATUS_NET_JITTER",bundle.getInt("NET_JITTER") + "");
        LiveModule.sendEvent("PLAY_EVT",params);
    }
}
