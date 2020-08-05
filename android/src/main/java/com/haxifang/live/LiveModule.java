package com.haxifang.live;

import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class LiveModule extends ReactContextBaseJavaModule {
    public static ReactApplicationContext reactApplicationContext;
    public static ReactContext reactContext;
    private LiveManager _liveManager;

    public LiveModule(ReactApplicationContext context){
        super(context);
        Log.i("TAG", "LiveModule: 创建livemodule实例");
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
    public void startPush(String url){
        _liveManager.startPush(url);
    }

    @ReactMethod
    public void switchCamera(){
        _liveManager.switchCamera();
    }

    @ReactMethod
    public void stopPush(){
        _liveManager.stopPush();
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

    @ReactMethod
    public void setPushOrientation(Boolean isPortrait){
        _liveManager.setPushOrientation(isPortrait);
    }

    // ========== 拉流部分方法

    @ReactMethod
    public void setLicence(String licenceUrl,String licenceKey) {
        // 初始化腾讯直播 SDK 方法
        Log.i("TAG", "setLicence: 设置license");
        _liveManager.setLicence(reactContext, licenceUrl, licenceKey);
    }

    @ReactMethod
    public void startPull(String url, int type) {
        // 开始直播拉流方法
        _liveManager.startPull(url,type);
    }

    @ReactMethod
    public void stopPull() {
        // 停止直播拉流方法
        _liveManager.stopPull();
    }

    @ReactMethod
    public void restPull() {
        // 恢复直播拉流方法
        _liveManager.restPull();
    }
}
















