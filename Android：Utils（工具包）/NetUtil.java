package org.alex.util;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * 作者：Alex
 * 时间：2016年09月03日    10:08
 * 简述：
 */

@SuppressWarnings("all")
public class NetUtil {
    public enum NetType {
        Any,

        Wifi,

        Mobile
    }
    /**
     * Class name of the {@link android.provider.Settings}.
     */
    private static final String ANDROID_PROVIDER_SETTINGS = "android.provider.Settings";
    /**
     * 打开网络设置页面
     */
    public static void openSetting() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1){
            openSetting("ACTION_WIFI_SETTINGS");
        }else{
            openSetting("ACTION_WIRELESS_SETTINGS");
        }
    }
    private static void openSetting(String actionName) {
        if(BaseUtil.isAppNull()){
            return ;
        }
        try {
            Class<?> settingsClass = Class.forName(ANDROID_PROVIDER_SETTINGS);
            Field actionWifiSettingsField = settingsClass.getDeclaredField(actionName);
            Intent settingIntent = new Intent(actionWifiSettingsField.get(null).toString());
            settingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BaseUtil.app().startActivity(settingIntent);
        } catch (Throwable e) {
            LogUtil.e(e);
        }
    }
    /**
     * 检测网络是否 可用
     *
     * @return Available returns true, unavailable returns false.
     */
    public static boolean isNetworkAvailable() {
        return isNetworkAvailable(NetType.Any);
    }

    /**
     *WiFi网络是否可用。
     * @return Open return true, close returns false.
     */
    public static boolean isWifiConnected() {
        return isNetworkAvailable(NetType.Wifi);
    }

    /**
     *手机网络是否可用
     *
     * @return Open return true, close returns false.
     */
    public static boolean isMobileConnected() {
        return isNetworkAvailable(NetType.Mobile);
    }

    /**
     *根据不同类型的网络，以确定是否该网络的连接。
     * @param netType from {@link NetType}.
     * @return Connection state return true, otherwise it returns false.
     */
    public static boolean isNetworkAvailable(NetType netType) {
        if(BaseUtil.isAppNull()){
            return false;
        }
        ConnectivityManager connectivity = (ConnectivityManager) BaseUtil.app().getSystemService(Context.CONNECTIVITY_SERVICE);
        Class<?> connectivityManagerClass = connectivity.getClass();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Method getAllNetworksMethod = connectivityManagerClass.getMethod("getAllNetworks");
                getAllNetworksMethod.setAccessible(true);
                Object[] networkArray = (Object[]) getAllNetworksMethod.invoke(connectivity);
                for (Object network : networkArray) {
                    Method getNetworkInfoMethod = connectivityManagerClass.getMethod("getNetworkInfo", Class.forName("android.net.Network"));
                    getNetworkInfoMethod.setAccessible(true);
                    NetworkInfo networkInfo = (NetworkInfo) getNetworkInfoMethod.invoke(connectivity, network);
                    if (isConnected(netType, networkInfo))
                        return true;
                }
            } catch (Throwable e) {
            }
        } else {
            try {
                Method getAllNetworkInfoMethod = connectivityManagerClass.getMethod("getAllNetworkInfo");
                getAllNetworkInfoMethod.setAccessible(true);
                Object[] networkInfoArray = (Object[]) getAllNetworkInfoMethod.invoke(connectivity);
                for (Object object : networkInfoArray) {
                    if (isConnected(netType, (NetworkInfo) object))
                        return true;
                }
            } catch (Throwable e) {
            }
        }
        return false;
    }

    /**
     * 根据不同类型的网络，以确定是否该网络的连接。
     *
     * @param netType     from {@link NetType}.
     * @param networkInfo from {@link NetworkInfo}.
     * @return Connection state return true, otherwise it returns false.
     */
    public static boolean isConnected(NetType netType, NetworkInfo networkInfo) {
        if (netType == NetType.Any && networkInfo != null && isConnected(networkInfo))
            return true;
        else if (netType == NetType.Wifi && networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI && isConnected(networkInfo))
            return true;
        else if (netType == NetType.Mobile && networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE && isConnected(networkInfo))
            return true;
        return false;
    }

    /**
     * 判断网络的连接。
     *
     * @param networkInfo from {@link NetworkInfo}.
     * @return Connection state return true, otherwise it returns false.
     */
    public static boolean isConnected(NetworkInfo networkInfo) {
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    /**
     * 检查是否GPRS可用。
     *
     * @return Open return true, close returns false.
     */
    public static boolean isGPRSOpen() {
        if(BaseUtil.isAppNull()){
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseUtil.app().getSystemService(Context.CONNECTIVITY_SERVICE);
        Class<?> cmClass = connectivityManager.getClass();
        try {
            Method getMobileDataEnabledMethod = cmClass.getMethod("getMobileDataEnabled");
            getMobileDataEnabledMethod.setAccessible(true);
            return (Boolean) getMobileDataEnabledMethod.invoke(connectivityManager);
        } catch (Throwable e) {
        }
        return false;
    }

    /**
     * 打开或关闭GPRS。
     *
     * @param isEnable Open to true, close to false.
     */
    public static void setGPRSEnable(boolean isEnable) {
        if(BaseUtil.isAppNull()){
            return ;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseUtil.app().getSystemService(Context.CONNECTIVITY_SERVICE);
        Class<?> cmClass = connectivityManager.getClass();
        try {
            Method setMobileDataEnabledMethod = cmClass.getMethod("setMobileDataEnabled", boolean.class);
            setMobileDataEnabledMethod.setAccessible(true);
            setMobileDataEnabledMethod.invoke(connectivityManager, isEnable);
        } catch (Throwable e) {
        }
    }

    /**
     * 获取ip 地址
     *
     * @return Such as: {@code 192.168.1.1}.
     */
    public static String getLocalIPAdr() {
        Enumeration<NetworkInterface> enumeration = null;
        try {
            enumeration = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            LogUtil.w(e);
        }
        if (enumeration != null) {
            // 遍历所用的网络接口
            while (enumeration.hasMoreElements()) {
                NetworkInterface nif = enumeration.nextElement();// 得到每一个网络接口绑定的地址
                Enumeration<InetAddress> inetAddresses = nif.getInetAddresses();
                // 遍历每一个接口绑定的所有ip
                if (inetAddresses != null)
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress ip = inetAddresses.nextElement();
                        if (!ip.isLoopbackAddress() && isIPv4Adr(ip.getHostAddress())) {
                            return ip.getHostAddress();
                        }
                    }
            }
        }
        return "";
    }

    /**
     * Ipv4 address check.
     */
    private static final Pattern IPV4_PATTERN = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");

    /**
     *检查是否有效的IPv4地址。
     * @param input the address string to check for validity.
     * @return True if the input parameter is a valid IPv4 address.
     */
    public static boolean isIPv4Adr(String input) {
        return IPV4_PATTERN.matcher(input).matches();
    }

	/* ===========以下是IPv6的检查，暂时用不到========== */

    // 未压缩过的IPv6地址检查
    private static final Pattern IPV6_STD_PATTERN = Pattern.compile("^[0-9a-fA-F]{1,4}(:[0-9a-fA-F]{1,4}){7}$");
    // 压缩过的IPv6地址检查
    private static final Pattern IPV6_HEX_COMPRESSED_PATTERN = Pattern.compile("^(([0-9A-Fa-f]{1,4}(:[0-9A-Fa-f]{1,4}){0,5})?)" +                                                              // 0-6
            "::" + "(([0-9A-Fa-f]{1,4}(:[0-9A-Fa-f]{1,4}){0,5})?)$");// 0-6 hex fields

    /**
     * 是否为有效 ipv6地址
     *
     * @param input IPV6 address.
     * @return True or false.
     * @see #isIPv6HexCompressedAdr(String)
     */
    public static boolean isIPv6StdAdr(final String input) {
        return IPV6_STD_PATTERN.matcher(input).matches();
    }

    /**
     *检查参数是否是有效的压缩IPv6地址。
     * @param input IPV6 address.
     * @return True or false.
     * @see #isIPv6StdAdr(String)
     */
    public static boolean isIPv6HexCompressedAdr(final String input) {
        int colonCount = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ':') {
                colonCount++;
            }
        }
        return colonCount <= 7 && IPV6_HEX_COMPRESSED_PATTERN.matcher(input).matches();
    }

    /**
     *
     *检查是否压缩或未压缩的IPv6地址。
     * @param input IPV6 address.
     * @return True or false.
     * @see #isIPv6HexCompressedAdr(String)
     * @see #isIPv6StdAdr(String)
     */
    public static boolean isIPv6Adr(final String input) {
        return isIPv6StdAdr(input) || isIPv6HexCompressedAdr(input);
    }
}

