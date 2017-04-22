package com.gomejr.myf.core.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;

/**
 * 作者：Alex
 * 时间：2016年10月23日
 * 简述：
 */
@SuppressWarnings("all")
public class DeviceUtil {
    private static final String FILE_NAME = "DeviceSharedPreferences";
    private static final String key_uuid = "uuid";
    private static Application application;

    public static void init(Application app) {
        application = app;
    }

    private static Context context() {
        return application;
    }

    private static SharedPreferences sharedPreferences() {
        if (context() == null) {
            return null;
        }
        SharedPreferences sharedPreferences = context().getSharedPreferences(FILE_NAME, Context.MODE_MULTI_PROCESS);
        return sharedPreferences;
    }

    public static String getUUID() {

        String uuid = getString(key_uuid);
        if (!TextUtils.isEmpty(uuid)) {
            return uuid;
        }
        StringBuilder builder = new StringBuilder();
        String imei = getIMEI();
        if (!TextUtils.isEmpty(imei)) {
            uuid = stringToMD5(imei);
            putString(key_uuid, uuid);
            return uuid;
        }
        String uniquePsuedoID = getUniquePsuedoID();
        if (!TextUtils.isEmpty(uniquePsuedoID)) {
            uuid = stringToMD5(uniquePsuedoID);
            putString(key_uuid, uuid);
            return uuid;
        }
        uuid = stringToMD5("000000000000000");
        putString(key_uuid, uuid);
        return uuid;
    }

    public static String getUniquePsuedoID() {
        String m_szDevIDShort = "" +
                Build.BOARD.length() % 10 +
                Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 +
                Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 +
                Build.HOST.length() % 10 +
                Build.ID.length() % 10 +
                Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 +
                Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 +
                Build.TYPE.length() % 10 +
                Build.USER.length() % 10 +
                Build.SERIAL.length() % 10 +
                Build.FINGERPRINT.length() % 10;
        return m_szDevIDShort;
    }

    /**
     * 获取手机DEVICE_ID | IMEI参数
     */
    public static String getIMEI() {
        TelephonyManager manager = (TelephonyManager) context().getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getDeviceId();
    }

    /**
     * 获取MAC 地址
     * <pre> 可以使用手机WiFi或蓝牙的MAC地址作为设备标识，但是并不推荐这么做，原因有以下两点：
     * 硬件限制：并不是所有的设备都有WiFi和蓝牙硬件，硬件不存在自然也就得不到这一信息。
     * 获取的限制：如果WiFi没有打开过，是无法获取其Mac地址的；而蓝牙是只有在打开的时候才能获取到其Mac地址。
     * </pre>
     */
    public static String getMacAddr() {
        WifiManager manager = (WifiManager) context().getSystemService(Context.WIFI_SERVICE);
        return manager.getConnectionInfo().getMacAddress();
    }

    /**
     * 获取 ANDROID_ID
     * ANDROID_ID是设备第一次启动时产生和存储的64bit的一个数，当设备被wipe后该数重置。
     */
    public static String getAndroidId() {
        return Settings.Secure.getString(context().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取手机生产商
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取手机号码，可能为 null
     */
    public static String getPhoneNum() {
        TelephonyManager manager = (TelephonyManager) context().getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getLine1Number();
    }

    /**
     * 获取设备型号
     */
    public static String getDeviceName() {
        return Build.MANUFACTURER + "-" + Build.BRAND + "-" + Build.BOARD + "-" + Build.SERIAL;
    }

    /**
     * 将字符串转成MD5值
     *
     * @param string
     * @return
     */
    private static String stringToMD5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            LogTrack.e(e);
            return null;
        } catch (UnsupportedEncodingException e) {
            LogTrack.e(e);
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

    /**
     * 判断是否平板设备
     *
     * @param context
     * @return true:平板,false:手机
     */
    public static boolean isTabletDevice() {
        return (context().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 判断是否手机设备
     *
     * @param context
     * @return true:手机,false:平板
     */
    public static boolean isPhoneDevice() {
        return !isTabletDevice();
    }

    /**
     * 获取渠道名
     *
     * @param ctx 此处习惯性的设置为activity，实际上context就可以
     * @return 如果没有获取成功，那么返回值为空
     */
    public static String getUMChannelName() {
        String channelName = "000";
        try {
            PackageManager packageManager = context().getPackageManager();
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context().getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelName = applicationInfo.metaData.getString("UMENG_CHANNEL");
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            LogTrack.e(e);
        }
        return channelName;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static int getVersionCode() {
        try {
            PackageManager manager = context().getPackageManager();
            PackageInfo info = manager.getPackageInfo(context().getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            LogTrack.e(e);
            return 1;
        }
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersionName() {
        try {
            PackageManager manager = context().getPackageManager();
            PackageInfo info = manager.getPackageInfo(context().getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            LogTrack.e(e);
            return "1.0";
        }
    }

    /**
     * 获取ip地址
     *
     * @return
     */
    public static String getHostIP() {
        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            Log.i("yao", "SocketException");
            e.printStackTrace();
        }
        return hostIp;
    }

    /**
     * 取出String, 失败, 返回null
     *
     * @param key 键, !null, !""
     */
    private static String getString(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        return getString(key, null);
    }

    /**
     * 取出String, 失败, 返回 defaultValue
     *
     * @param key 键, !null, !""
     */
    private static String getString(String key, String defaultValue) {
        SharedPreferences sharedPreferences = sharedPreferences();
        if (sharedPreferences == null) {
            return null;
        }
        return sharedPreferences.getString(key, defaultValue);
    }

    /**
     * 移除键值对, 失败, 返回false
     *
     * @param key 键, !null, !""
     */
    private static boolean remove(String key) {
        if (TextUtils.isEmpty(key)) {
            return false;
        }
        SharedPreferences sharedPreferences = sharedPreferences();
        if (sharedPreferences == null) {
            return false;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();

        return editor.remove(key).commit();
    }

    /**
     * 存入String数据
     * <br/>失败, 返回false
     *
     * @param key   键, !null, !""
     * @param value 值
     */
    private static boolean putString(String key, String value) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
            return false;
        }
        SharedPreferences sharedPreferences = sharedPreferences();
        if (sharedPreferences == null) {
            return false;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }
}
