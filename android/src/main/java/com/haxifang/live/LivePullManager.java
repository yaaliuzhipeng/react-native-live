package com.haxifang.live;

import android.content.Context;
import android.os.Bundle;
import android.view.Choreographer;
import android.view.View;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

public class LivePullManager {

    private static TXLivePlayer Player;
    private static TXCloudVideoView videoView;
    private static ThemedReactContext mContext;
    static String TAG = "天真调试";

    // 监听事件注册
    private static void sendEvent(ThemedReactContext reactContext, String eventName, WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

    // 初始化拉流播放
    public static TXCloudVideoView initVideoView(ThemedReactContext context) {

        mContext = context;
        Player = new TXLivePlayer(reactContext.getCurrentActivity());
        Player.setPlayerView(mCloudVideoView);
        videoView = new TXCloudVideoView(reactContext, null);

        Player.setPlayListener(new ITXLivePlayListener() {
            @Override
            public void onPlayEvent(int i, Bundle bundle) {
                // 直播 SDK 事件通知

                WritableMap params = Arguments.createMap();
                switch (i) {
                    case 2001:
                        params.putString("PLAY_EVT_CONNECT_SUCC", i + "");
                        break;
                    case 2002:
                        params.putString("PLAY_EVT_RTMP_STREAM_BEGIN", i + "");
                        break;
                    case 2003:
                        params.putString("PLAY_EVT_RCV_FIRST_I_FRAME", i + "");
                        break;
                    case 2004:
                        params.putString("PLAY_EVT_PLAY_BEGIN", i + "");
                        break;
                    case 2005:
                        params.putString("PLAY_EVT_PLAY_PROGRESS", i + "");
                        break;
                    case 2006:
                        params.putString("PLAY_EVT_PLAY_END", i + "");
                        break;
                    case 2007:
                        params.putString("PLAY_EVT_PLAY_LOADING", i + "");
                        break;
                    case 2008:
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
                sendEvent(mContext,"PLAY_EVT",params);

            }

            @Override
            public void onNetStatus(Bundle bundle) {

                WritableMap params = Arguments.createMap();
                params.putString("NET_STATUS_CPU_USAGE",bundle.getInt("CPU_USAGE") + "");
                params.putString("NET_STATUS_NET_SPEED",bundle.getInt("NET_SPEED") + "");
                params.putString("NET_STATUS_NET_JITTER",bundle.getInt("NET_JITTER") + "");
                sendEvent(mContext,"PLAY_EVT",params);

            }
        });

        return videoView;
    }

    // 初始化腾讯直播 SDK License 授权方法
    public static void setLicence(Context context, String licenceUrl, String licenceKey) {
        TXLiveBase.getInstance().setLicence(context, licenceUrl, licenceKey);
    }

    // 控制直播组件开始拉流方法
    public static void startPull(final String url, final int type) {
        if(Player == null){
            // 直播组件未注册，抛出异常

        } else {
            // 开始播放

            new Thread(new Runnable() {
                @Override
                public void run() {
                    mContext.getCurrentActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Player.startPlay(url, type);
                            manuallyLayoutChildren();
                            setupLayoutHack();
                        }
                    });
                }
            }).start();

        }
    }

    // 控制直播组件停止拉流方法
    public static void stopPull() {
        if(instance == null){

        } else {
            // 暂停播放
            if(Player != null && videoView != null){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mContext.getCurrentActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            
                                Player.stopPlay(true);
                                videoView.onDestroy();
                                // 调用通知 RN 刷新组件状态
                                manuallyLayoutChildren();
                                setupLayoutHack();
                            }
                    });
                    }
                }).start();
            }
        }
    }

    // 控制直播组件恢复拉流方法
    public static void restPull() {
        if(instance == null){

        } else {
            // 暂停播放

            new Thread(new Runnable() {
                @Override
                public void run() {
                    mContext.getCurrentActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Player.resume();

                            // 调用通知 RN 刷新组件状态
                            manuallyLayoutChildren();
                            setupLayoutHack();
                        }
                    });
                }
            }).start();

        }
    }

    // for fix addView not showing ====
    private static void setupLayoutHack() {
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            // 这个事件很关键，不然不能触发再次渲染，让 view 在RN里渲染成功!!
            @Override
            public void doFrame(long frameTimeNanos) {
                manuallyLayoutChildren();
                videoView.getViewTreeObserver().dispatchOnGlobalLayout();
                Choreographer.getInstance().postFrameCallback(this);
            }
        });
    }

    // 这个函数很关键，不然不能触发再次渲染，让 view 在RN里渲染成功!!
    private static void manuallyLayoutChildren() {
        for (int i = 0; i < videoView.getChildCount(); i++) {
            View child = videoView.getChildAt(i);
            child.measure(View.MeasureSpec.makeMeasureSpec(videoView.getMeasuredWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(videoView.getMeasuredHeight(), View.MeasureSpec.EXACTLY));
            child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
        }
    }


}
