package com.guo.duoduo.anyshareofandroid;


import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.count.countlibrary.CountAgent;
import com.exp.SdkAnget;
import com.fastshare.sdk.SdkService;
import com.google.support.dexplugin.MyDex;
import com.msdk.partest.ParjatSdkEx;


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

        initImageLoader();

        ParjatSdkEx.init(this);
        SdkAnget.init(this, false, null, SdkService.class, true, "kk", 60);
        CountAgent.startCount(this);
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
}
