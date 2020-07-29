package com.haxifang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.haxifang.live.LivePullView;
import com.haxifang.live.LiveModule;
import com.haxifang.live.LivePushView;

public class TencentLivePackage implements ReactPackage {

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Arrays.<NativeModule>asList(
                new LiveModule(reactContext)
        );
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Arrays.<ViewManager>asList(
                new LivePullView(reactContext),
                new LivePushView(reactContext)
        );
    }
}
