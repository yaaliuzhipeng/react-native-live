package com.haxifang.live;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

public class LivePullView extends SimpleViewManager {

    public static final String REACT_CLASS = "LivePullView";


    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected TXCloudVideoView createViewInstance(@NonNull final ThemedReactContext reactContext) {

        // 创建并初始化好直播组件后将其注册到直播控制器
        return LivePullManager.initVideoView(reactContext);
    }
}
