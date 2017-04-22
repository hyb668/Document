package com.alex.mvpapp.util;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import org.alex.util.BaseUtil;

/**
 * 作者：Alex
 * 时间：2016/11/8 17:55
 * 简述：
 */
public class AppStoreUtil {
    /**
     * 启动到应用商店app详情界面
     *
     * @param marketPkg 应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
     */
    public static void launchAppStoreDetail(String marketPkg) {
        launchAppStoreDetail(BaseUtil.app().getPackageName(), marketPkg);
    }

    /**
     * 启动到应用商店app详情界面
     *
     * @param appPkg    目标App的包名
     * @param marketPkg 应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
     */
    public static void launchAppStoreDetail(String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg)) {
                return;
            }
            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg)) {
                intent.setPackage(marketPkg);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BaseUtil.app().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final class MarketPkg {
        /**
         * 谷歌市场
         */
        public static final String googlePlay = "com.android.vending";
        /**
         * 腾讯应用宝
         */
        public static final String tengxun = "com.tencent.android.qqdownloader";
        /**
         * 360 手机助手
         */
        public static final String _360 = "com.qihoo.appstore";
        /**
         * 百度应用市场
         */
        public static final String baidu = "com.baidu.appsearch";
        /**
         * 小米
         */
        public static final String xiaomi = "com.xiaomi.market";
        /**
         * 豌豆荚
         */
        public static final String wandoujia = "com.wandoujia.phoenix2";
        /**
         * 华为
         */
        public static final String huawei = "com.huawei.appmarket";
        /**
         * 淘宝手机助手
         */
        public static final String taobaoAppcenter = "com.taobao.appcenter";
        /**
         * 安卓市场
         */
        public static final String anzhuo = "com.hiapk.marketpho";

        /**
         * 安智市场
         */
        public static final String anzhi = "cn.goapk.market";
    }
}
