package com.alex.mvpapp.util;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import org.alex.util.BaseUtil;

/**
 * ���ߣ�Alex
 * ʱ�䣺2016/11/8 17:55
 * ������
 */
public class AppStoreUtil {
    /**
     * ������Ӧ���̵�app�������
     *
     * @param marketPkg Ӧ���̵���� ,���Ϊ""����ϵͳ����Ӧ���̵��б��û�ѡ��,�����ת��Ŀ���г���Ӧ��������棬ĳЩӦ���̵���ܻ�ʧ��
     */
    public static void launchAppStoreDetail(String marketPkg) {
        launchAppStoreDetail(BaseUtil.app().getPackageName(), marketPkg);
    }

    /**
     * ������Ӧ���̵�app�������
     *
     * @param appPkg    Ŀ��App�İ���
     * @param marketPkg Ӧ���̵���� ,���Ϊ""����ϵͳ����Ӧ���̵��б��û�ѡ��,�����ת��Ŀ���г���Ӧ��������棬ĳЩӦ���̵���ܻ�ʧ��
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
         * �ȸ��г�
         */
        public static final String googlePlay = "com.android.vending";
        /**
         * ��ѶӦ�ñ�
         */
        public static final String tengxun = "com.tencent.android.qqdownloader";
        /**
         * 360 �ֻ�����
         */
        public static final String _360 = "com.qihoo.appstore";
        /**
         * �ٶ�Ӧ���г�
         */
        public static final String baidu = "com.baidu.appsearch";
        /**
         * С��
         */
        public static final String xiaomi = "com.xiaomi.market";
        /**
         * �㶹��
         */
        public static final String wandoujia = "com.wandoujia.phoenix2";
        /**
         * ��Ϊ
         */
        public static final String huawei = "com.huawei.appmarket";
        /**
         * �Ա��ֻ�����
         */
        public static final String taobaoAppcenter = "com.taobao.appcenter";
        /**
         * ��׿�г�
         */
        public static final String anzhuo = "com.hiapk.marketpho";

        /**
         * �����г�
         */
        public static final String anzhi = "cn.goapk.market";
    }
}
