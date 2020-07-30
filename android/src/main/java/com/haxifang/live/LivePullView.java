package com.haxifang.live;


import android.util.Log;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

public class LivePullView extends SimpleViewManager {

    public static final String REACT_CLASS = "LivePullView";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected TXCloudVideoView createViewInstance(final ThemedReactContext reactContext) {

        LiveManager shared = LiveManager.getInstance();
        TXCloudVideoView pullview = new TXCloudVideoView(reactContext);
        shared._player = new TXLivePlayer(reactContext);
        shared._player.setPlayerView(pullview);
        shared._pullview = pullview;
        LivePullEvents listener = new LivePullEvents();
        shared._player.setPlayListener(listener);
        Log.i("TAG", "createViewInstance: 创建拉流view");
        return pullview;
    }
}
