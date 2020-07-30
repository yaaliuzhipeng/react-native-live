package com.haxifang.live;

import android.content.Context;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;

import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

import static com.tencent.rtmp.TXLiveConstants.*;

public class LiveManager {
    private static final String TAG = "直播 - LiveManager ";
    private static LiveManager _instance = new LiveManager();

    private LiveManager() {
    }

    public static LiveManager getInstance() {
        if (_instance == null) {
            _instance = new LiveManager();
        }
        return _instance;
    }


    public TXLivePusher _pusher;
    public TXLivePushConfig _config;
    public TXCloudVideoView _pushview;
    public TXCloudVideoView _pullview;
    public TXLivePlayer _player;

    /**==============================
     *
     *  拉流部分
     *
     *  ==============================
     */
    // 初始化腾讯直播 SDK License 授权方法
    public void setLicence(Context context, String licenceUrl, String licenceKey) {
        TXLiveBase.getInstance().setLicence(context, licenceUrl, licenceKey);
    }

    // 控制直播组件开始拉流方法
    public void startPull(final String url, final int type) {
        if(_player == null){
            // 直播组件未注册，抛出异常
        } else {
            // 开始播放
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LiveModule.reactContext.getCurrentActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i(TAG, "run: 拉流地址"+url+"拉流类型: "+type);
                            _player.startPlay(url, type);
                            manuallyLayoutChildren();
                            setupLayoutHack();
                        }
                    });
                }
            }).start();

        }
    }

    // 控制直播组件停止拉流方法
    public void stopPull() {

        // 暂停播放
        if(_player != null && _pullview != null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LiveModule.reactContext.getCurrentActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            _player.stopPlay(true);
                            _pullview.onDestroy();
                            // 调用通知 RN 刷新组件状态
                            manuallyLayoutChildren();
                            setupLayoutHack();
                        }
                    });
                }
            }).start();
        }

    }

    // 控制直播组件恢复拉流方法
    public void restPull() {

        // 暂停播放

        new Thread(new Runnable() {
            @Override
            public void run() {
                LiveModule.reactContext.getCurrentActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _player.resume();
                        // 调用通知 RN 刷新组件状态
                        manuallyLayoutChildren();
                        setupLayoutHack();
                    }
                });
            }
        }).start();

    }

    // for fix addView not showing ====
    private void setupLayoutHack() {
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            // 这个事件很关键，不然不能触发再次渲染，让 view 在RN里渲染成功!!
            @Override
            public void doFrame(long frameTimeNanos) {
                manuallyLayoutChildren();
                _pullview.getViewTreeObserver().dispatchOnGlobalLayout();
                Choreographer.getInstance().postFrameCallback(this);
            }
        });
    }

    // 这个函数很关键，不然不能触发再次渲染，让 view 在RN里渲染成功!!
    private void manuallyLayoutChildren() {
        for (int i = 0; i < _pullview.getChildCount(); i++) {
            View child = _pullview.getChildAt(i);
            child.measure(View.MeasureSpec.makeMeasureSpec(_pullview.getMeasuredWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(_pullview.getMeasuredHeight(), View.MeasureSpec.EXACTLY));
            child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
        }
    }



    /**==============================
     *
     *  推流部分
     *
     *  ==============================
     */

    /**
     * 启动摄像头预览
     */
    public void preview() {
        Log.i(TAG, "preview: 调用启动预览方法");
        if (this._pusher != null) {
            if (this._pushview != null) {
                this._pusher.startCameraPreview(_pushview);
            } else {
                Log.i(TAG, "preview:  预览容器view为null");
            }
        } else {
            Log.i(TAG, "preview:  pusher为null");
        }
    }

    /**
     * 启动推流
     */
    public void startPush(String url) {
        Log.i(TAG, "startPush: 调用推流");
        if(this._pusher != null){
            Log.i(TAG, "startPush: pusher不为null");
            this._pusher.startPusher(url);
        }else{
            Log.i(TAG, "startPush: pusher为null");
        }
    }

    /**
     * 停止推流
     */
    public void stopPush() {
        if(this._pusher != null){
            this._pusher.stopCameraPreview(true);
            this._pusher.stopPusher();
        }

        this._pusher = null;
        this._config = null;
        this._pushview = null;
    }

    /**
     * 切换推流的摄像头（前置，后置）
     */
    public void switchCamera() {
        if(this._pusher != null){
            this._pusher.switchCamera();
        }
    }

    /**
     * 设置观众端画面清晰度
     */
    public void setUserVideoQuality(String quality) {
        if(this._pusher != null){
            if (quality.equals("LOW")) {
                this._pusher.setVideoQuality(VIDEO_QUALITY_STANDARD_DEFINITION, true, false);
            } else if (quality.equals("MEDIUM")) {
                this._pusher.setVideoQuality(VIDEO_QUALITY_HIGH_DEFINITION, true, false);
            } else if (quality.equals("HIGH")) {
                this._pusher.setVideoQuality(VIDEO_QUALITY_HIGH_DEFINITION, true, false);
            } else {
                Log.i(TAG, "setUserVideoQuality:  画面清晰度参数错误，传入的参数为 " + quality);
            }
        }
    }

    /**
     * 镜像
     */
    public void setMirrorEnabled(Boolean enabled) {
        if(this._pusher != null){
            this._pusher.setMirror(enabled);
        }
    }

    /**
     * 设置直播方向
     */
    public void setOrientaionMode(String orientation) {
        if(this._pusher != null){
            if (orientation.equals("PORTRAIT")) {
                this._config.setHomeOrientation(VIDEO_ANGLE_HOME_DOWN);
                this._pusher.setConfig(this._config);
                this._pusher.setRenderRotation(0);
            } else if (orientation.equals("LANDSCAPE")) {
                this._config.setHomeOrientation(VIDEO_ANGLE_HOME_RIGHT);
                this._pusher.setConfig(this._config);
                this._pusher.setRenderRotation(90);
            } else {
                Log.i(TAG, "setOrientaionMode: 暂只支持 竖屏 和 横屏(home键在右)两种, 参数请传入 PORTRAIT 或者 LANDSCAPE");
            }
        }
    }

    /**
     * 耳返开启关闭
     */
    public void setAudioPreviewEnabled(Boolean enabled) {
        if(this._pusher != null){
            this._config.enableAudioEarMonitoring(enabled);
            this._pusher.setConfig(this._config);
        }
    }
    /**
     *  设置对焦模式
     */
    public void setManualFocus(Boolean enabled){
        if(this._pusher != null){
            this._config.setTouchFocus(enabled);
            this._pusher.setConfig(this._config);
        }
    }

    /**
     * 变声器
     */
    public void setFakeVoice(int voicetype) {
        if(this._pusher != null){
            switch (voicetype) {
                case 0:
                    this._pusher.setVoiceChangerType(VOICECHANGER_TYPE_0);
                    break;
                case 1:
                    this._pusher.setVoiceChangerType(VOICECHANGER_TYPE_1);
                    break;
                case 2:
                    this._pusher.setVoiceChangerType(VOICECHANGER_TYPE_2);
                    break;
                case 3:
                    this._pusher.setVoiceChangerType(VOICECHANGER_TYPE_3);
                    break;
                case 4:
                    this._pusher.setVoiceChangerType(VOICECHANGER_TYPE_4);
                    break;
                case 5:
                    this._pusher.setVoiceChangerType(VOICECHANGER_TYPE_5);
                    break;
                case 6:
                    this._pusher.setVoiceChangerType(VOICECHANGER_TYPE_6);
                    break;
                case 7:
                    this._pusher.setVoiceChangerType(VOICECHANGER_TYPE_7);
                    break;
                case 8:
                    this._pusher.setVoiceChangerType(VOICECHANGER_TYPE_8);
                    break;
                case 9:
                    this._pusher.setVoiceChangerType(VOICECHANGER_TYPE_9);
                    break;
                case 10:
                    this._pusher.setVoiceChangerType(VOICECHANGER_TYPE_1);
                    break;
                case 11:
                    this._pusher.setVoiceChangerType(VOICECHANGER_TYPE_1);
                    break;
                default:
                    Log.i(TAG, "setFakeVoice: 变声器参数错误");
            }

        }
    }

    /**
     *  设置美颜风格
     * @param beautystyle
     */
    public void setBeautyMode(String beautystyle){
        if(this._pusher != null){
            if(beautystyle.equals("SMOOTH")){
                this._pusher.getBeautyManager().setBeautyStyle(BEAUTY_STYLE_SMOOTH);
            }else if(beautystyle.equals("NATURAL")){
                this._pusher.getBeautyManager().setBeautyStyle(BEAUTY_STYLE_NATURE);
            }else{
                Log.i(TAG, "setBeautyMode: 暂不支持其他的美颜风格参数");
            }
        }
    }
    /**
     * 设置磨皮级别
     */
    public void setBeautyLevel(int level){
        if(this._pusher != null){
            int value = 0;
            if(level < 0) value = 0;
            value = Math.min(level, 9);
            this._pusher.getBeautyManager().setBeautyLevel(value);
        }
    }
    /**
     * 设置美白级别
     */
    public void setWhitenessLevel(int level) {
        if(this._pusher != null){
            int value = 0;
            if(level < 0) value = 0;
            value = Math.min(level, 9);
            this._pusher.getBeautyManager().setWhitenessLevel(value);
        }
    }
    /**
     *  设置红润级别
     */
    public void setRuddinessLevel(int level){
        if(this._pusher != null){
            int value = 0;
            if(level < 0) value = 0;
            value = Math.min(level, 9);
            this._pusher.getBeautyManager().setRuddyLevel(value);
        }
    }

    /**
     * 设置横屏推流
     */
    public void setPushOrientation(boolean isPortrait){
        if(isPortrait){
            _config.setHomeOrientation(VIDEO_ANGLE_HOME_DOWN);
            _pusher.setConfig(_config);
            _pusher.setRenderRotation(0);
        }else{
            _config.setHomeOrientation(VIDEO_ANGLE_HOME_RIGHT);
            _pusher.setConfig(_config);

            //为了保证本地渲染是正的、设置渲染角度为90度
            _pusher.setRenderRotation(90);
        }
    }

}









