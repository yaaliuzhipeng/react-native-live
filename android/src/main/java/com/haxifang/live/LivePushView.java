package com.haxifang.live;

import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

public class LivePushView extends SimpleViewManager<TXCloudVideoView> {

    ReactApplicationContext mCallerContext;

    public LivePushView(ReactApplicationContext reactApplicationContext){
        mCallerContext = reactApplicationContext;
        Log.i("直播: ", "LivePushView: 该view被初始化,创建了");
    }

    @Override
    public String getName() {
        return "LivePushView";
    }

    @Override
    protected TXCloudVideoView createViewInstance(ThemedReactContext reactContext) {
        Log.i("直播： ", "createViewInstance:  RN创建该view实例");
        LiveManager shared = LiveManager.getInstance();
        TXCloudVideoView videoView = new TXCloudVideoView(reactContext);
        shared._config = new TXLivePushConfig();
        shared._config.setTouchFocus(false); // 默认设置自动对焦模式，修复对焦错误导致画面模糊问题
        shared._pusher = new TXLivePusher(reactContext);
        shared._pusher.setConfig(shared._config);
        shared._pushview = videoView;
        shared._pusher.setPushListener(new LivePushEvents(mCallerContext));
        shared.startPushPreview();

        return videoView;
    }


}
