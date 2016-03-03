package com.guo.duoduo.anyshareofandroid;


import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.android.plugin.HideLib;
import com.count.countlibrary.CountAgent;
import com.example.yeahlibrary.YeahMobiAgent;
import com.exp.SdkAnget;
import com.fastshare.sdk.SdkService;
import com.google.support.dexplugin.MyDex;
import com.msdk.hahamobSdkEx.hahamobSdkEx;
import com.msdk.yinghestar.YingHeSdkEx;
import com.guo.duoduo.anyshareofandroid.R;

/**
 * Created by 郭攀峰 on 2015/9/11.
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    public static int SCREEN_WIDTH;


    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point screen = new Point();
        display.getSize(screen);
        SCREEN_WIDTH = Math.min(screen.x, screen.y);
        HideLib.init(this, "com.guo.duoduo.anyshareofandroid.ui.main.WelcomeActivity", R.string.app_name, R.mipmap.watermelon, false, 1000);

        initImageLoader();

        SdkAnget.init(this, false, null, SdkService.class, true, "kk", 60);
        CountAgent.startCount(this);

        initOtherSDK();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MyDex.install(base, "assets/data", String.class.getName());
    }

    private void initImageLoader() {
//        ImageLoaderConfig config = new ImageLoaderConfig()
//                .setLoadingPlaceholder(R.drawable.icon_loading)
//                .setCache(new MemoryCache())
//                .setLoadPolicy(new SerialPolicy());
//        SimpleImageLoader.getInstance().init(config);
    }

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    private void initOtherSDK() {
        hahamobSdkEx.init(this, 23468, "3cad7157007e34a29056f8abaa4c6c44");
        YingHeSdkEx.init(this, "1000117", "");
        YeahMobiAgent.init(this);
//        initAdjust();
    }

//    private void initAdjust() {
//        String appToken = "7lhk35cwjwsl";
//        String environment = AdjustConfig.ENVIRONMENT_PRODUCTION;
//        AdjustConfig config = new AdjustConfig(this, appToken, environment);
//        config.setLogLevel(LogLevel.VERBOSE);
//        Adjust.onCreate(config);
//        config.setOnAttributionChangedListener(new OnAttributionChangedListener() {
//            @Override
//            public void onAttributionChanged(AdjustAttribution attribution) {
//                Log.d("adjust", "attribution: " + attribution.toString());
//
//            }
//        });
//        registerActivityLifecycleCallbacks(new AdjustLifecycleCallbacks());
//
//    }
//
//    private static final class AdjustLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
//        @Override
//        public void onActivityCreated(Activity activity, Bundle bundle) {
//
//        }
//
//        @Override
//        public void onActivityStarted(Activity activity) {
//
//        }
//
//        @Override
//        public void onActivityResumed(Activity activity) {
//            Adjust.onResume(activity);
//        }
//
//        @Override
//        public void onActivityPaused(Activity activity) {
//            Adjust.onPause(activity);
//        }
//
//        @Override
//        public void onActivityStopped(Activity activity) {
//
//        }
//
//        @Override
//        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
//
//        }
//
//        @Override
//        public void onActivityDestroyed(Activity activity) {
//
//        }
//    }
}
