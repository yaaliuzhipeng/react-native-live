package com.haxifang.live;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class LiveModule extends ReactContextBaseJavaModule {
    private static ReactApplicationContext reactApplicationContext;
    private static ReactContext reactContext;
    private LiveManager _liveManager;

    public LiveModule(ReactApplicationContext context){
        super(context);
        reactApplicationContext = context;
        reactContext = context;
        _liveManager = LiveManager.getInstance();
    }

    public static void sendEvent(String eventName, WritableMap params){
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName,params);
    }

    @Override
    public String getName() {
        return "LiveModule";
    }

    // ============== 推流部分方法

    @ReactMethod
    public void preview(){
        if(_liveManager != null){
            _liveManager.preview();
        }
    }

    @ReactMethod
    public void startLivePush(String url){
        _liveManager.startLivePush(url);
    }

    @ReactMethod
    public void switchCamera(){
        _liveManager.switchCamera();
    }

    @ReactMethod
    public void stopLivePush(){
        _liveManager.stopLivePush();
    }

    @ReactMethod
    public void setUserVideoQuality(String quality){
        _liveManager.setUserVideoQuality(quality);
    }

    @ReactMethod
    public void setMirrorEnabled(Boolean enabled){
        _liveManager.setMirrorEnabled(enabled);
    }

    @ReactMethod
    public void setOrientaionMode(String orientation){
        _liveManager.setOrientaionMode(orientation);
    }

    @ReactMethod
    public void setAudioPreviewEnabled(Boolean enabled){
        _liveManager.setAudioPreviewEnabled(enabled);
    }

    @ReactMethod
    public void setManualFocus(Boolean enabled){
        _liveManager.setManualFocus(enabled);
    }

    @ReactMethod
    public void setFakeVoice(int voicetype){
        _liveManager.setFakeVoice(voicetype);
    }

    @ReactMethod
    public void setBeautyMode(String beautystyle){
        _liveManager.setBeautyMode(beautystyle);
    }

    @ReactMethod
    public void setBeautyLevel(int level){
        _liveManager.setBeautyLevel(level);
    }

    @ReactMethod
    public void setWhitenessLevel(int level){
        _liveManager.setWhitenessLevel(level);
    }

    @ReactMethod
    public void setRuddinessLevel(int level){
        _liveManager.setRuddinessLevel(level);
    }

    // ========== 拉流部分方法

    @ReactMethod
    public void setLicence(String licenceUrl,String licenceKey) {
        // 初始化腾讯直播 SDK 方法
        LivePullManager.setLicence(mContext, licenceUrl, licenceKey);
    }

    @ReactMethod
    public void startPull(String url, int type) {
        // 开始直播拉流方法
        LivePullManager.startPull(url,type);
    }

    @ReactMethod
    public void stopPull() {
        // 停止直播拉流方法
        LivePullManager.stopPull();
    }

    @ReactMethod
    public void restPull() {
        // 恢复直播拉流方法
        LivePullManager.restPull();
    }
}
















