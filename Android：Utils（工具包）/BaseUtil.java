package org.alex.util;

import android.app.Application;
import org.alex.helper.CrashHandler;

/**
 * 作者：Alex
 * 时间：2016年09月03日    09:59
 * 简述：
 */
@SuppressWarnings("all")
public class BaseUtil {
    private static Application application;
    private static boolean debug = true;
    private static BaseUtil baseUtil;

    private BaseUtil() {
    }

    public static BaseUtil getInstance() {
        if (baseUtil == null) {
            synchronized (BaseUtil.class) {
                baseUtil = (baseUtil == null) ? new BaseUtil() : baseUtil;
            }
        }
        return baseUtil;
    }

    /**
     * 初始化 Application
     */
    public BaseUtil init(Application application) {
        return app(application);
    }

    /**
     * 初始化 Application
     */
    public BaseUtil app(Application application) {
        BaseUtil.application = application;
        onCrashListener(null);
        return this;
    }


    /**
     * 是否处于debug模式
     */
    public BaseUtil debug(boolean debug) {
        BaseUtil.debug = debug;
        LogTrack.debug(debug);
        return this;
    }

    /**
     * 设置 Log 头
     */
    public BaseUtil headTag(String headTag) {
        LogTrack.headTag(headTag);
        return this;
    }

    /*设置共享存储的文件名称*/
    public BaseUtil spFileName(String fileName) {
        SpUtil.fileName(fileName);
        return this;
    }

    /**
     * 崩溃监听
     */
    public BaseUtil onCrashListener(CrashHandler.OnCrashListener onCrashListener) {
        CrashHandler crashHandler = new CrashHandler(BaseUtil.application);
        crashHandler.setOnCrashListener(onCrashListener);
        return this;
    }

    public static Application app() {
        return application;
    }

    /**
     * 处于debug模式， 不展示Log日志
     */
    public static boolean debug() {
        return debug;
    }

    public static final boolean isAppNull() {
        if (BaseUtil.app() == null) {
            LogTrack.e("请在 Application 中初始化 " + BaseUtil.class.getSimpleName() + " init(Application application);");
            return true;
        }
        return false;
    }

    public static final boolean isAppNotNull() {
        return !isAppNull();
    }

}

