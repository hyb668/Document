package org.alex.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：Alex
 * 时间：2016年09月03日    10:14
 * 简述：
 */
@SuppressWarnings("all")
public class StringUtil {


    /**
     * 变成首字母大写
     * <br/>capitalizeFirstLetter(null)     =   null;
     * <br/>capitalizeFirstLetter("")       =   "";
     * <br/>capitalizeFirstLetter("2ab")    =   "2ab"
     * <br/>capitalizeFirstLetter("a")      =   "A"
     * <br/>capitalizeFirstLetter("ab")     =   "Ab"
     * <br/>capitalizeFirstLetter("Abc")    =   "Abc"
     */
    public static String firstChar2UpperCase(Object text) {
        if (isEmpty(text)) {
            return (text == null ? "" : text.toString());
        }
        String str = text.toString();
        char c = str.charAt(0);
        return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str : new StringBuilder(str.length()).append(Character.toUpperCase(c)).append(str.substring(1)).toString();
    }

    /**
     * 全角符号 --->半角符号
     * <br/>fullSymbols2Half(null) = null;
     * <br/>fullSymbols2Half("") = "";
     * <br/>fullSymbols2Half(new String(new char[] {12288})) = " ";
     * <br/>fullSymbols2Half("！＂＃＄％＆) = "!\"#$%&";
     */
    public static String fullSymbols2Half(Object text) {
        if (isEmpty(text)) {
            return (text == null ? "" : text.toString());
        }
        String str = text.toString();
        char[] source = str.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == 12288) {
                source[i] = ' ';
            } else if (source[i] >= 65281 && source[i] <= 65374) {
                source[i] = (char) (source[i] - 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * 半角符号---->全角符号
     * <br/>halfSymbols2Full(null) = null;
     * <br/>halfSymbols2Full("") = "";
     * <br/>halfSymbols2Full(" ") = new String(new char[] {12288});
     * <br/>halfSymbols2Full("!\"#$%&) = "！＂＃＄％＆";
     */
    public static String halfSymbols2Full(Object text) {
        if (isEmpty(text)) {
            return (text == null ? "" : text.toString());
        }
        String str = text.toString();
        char[] source = str.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == ' ') {
                source[i] = (char) 12288;
            } else if (source[i] >= 33 && source[i] <= 126) {
                source[i] = (char) (source[i] + 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * 判断是纯数字 [0-9]
     * <br/>不匹配, 返回false
     */
    public static boolean isNumric(Object text) {
        if (isEmpty(text)) {
            return false;
        }
        String str = text.toString();
        if (str != null && !str.trim().equalsIgnoreCase("")) {
            Pattern pattern = Pattern.compile("^[0-9]*$");
            Matcher matcher = pattern.matcher(str);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 判断是纯数字 [0-9]    正数、负数、和小数
     * <br/>不匹配, 返回false
     */
    public static boolean isNumric4Decimal(Object text) {
        if (isEmpty(text)) {
            return false;
        }
        String str = text.toString();
        if (str != null && !str.trim().equalsIgnoreCase("")) {
            Pattern pattern = Pattern.compile("^(\\-|\\+)?\\d+(\\.\\d+)?$");
            Matcher matcher = pattern.matcher(str);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 判断是纯数字 [0-9]    带1-2位小数的正数或负数
     * <br/>不匹配, 返回false
     */
    public static boolean isNumric4Decimal4FloatLength(Object text, int min, int max) {
        if (isEmpty(text)) {
            return false;
        }
        String str = text.toString();
        if (str != null && !str.trim().equalsIgnoreCase("")) {
            Pattern pattern = Pattern.compile("^(\\-)?\\d+(\\.\\d{" + min + "," + max + "})?$");
            Matcher matcher = pattern.matcher(str);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 判断是纯数字 [0-9]  正好 length 个
     * <br/>不匹配, 返回false
     */
    public static boolean isNumric4Length(Object text, int length) {
        if (isEmpty(text)) {
            return false;
        }
        String str = text.toString();
        if (str != null && !str.trim().equalsIgnoreCase("")) {
            Pattern pattern = Pattern.compile("^\\d{" + length + "}$");
            Matcher matcher = pattern.matcher(str);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 判断是纯数字 [0-9]  正好 [min, max]个
     * <br/>不匹配, 返回false
     */
    public static boolean isNumric4Length(Object text, int min, int max) {
        if (isEmpty(text)) {
            return false;
        }
        String str = text.toString();
        if (str != null && !str.trim().equalsIgnoreCase("")) {
            Pattern pattern = Pattern.compile("^\\d{" + min + "," + max + "}$");
            Matcher matcher = pattern.matcher(str);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 是数字开头
     *
     * @param text
     * @return
     */
    public static boolean isNumricStart(Object text) {
        if (text == null) {
            return false;
        }
        String str = text.toString();
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str.charAt(0) + "");
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断是纯英文字母, [a-z,A-Z]
     * <br/>不匹配, 返回false
     */
    public static boolean isEn(Object text) {
        if (isEmpty(text)) {
            return false;
        }
        String str = text.toString();

        if (str != null && !str.trim().equalsIgnoreCase("")) {
            Pattern pattern = Pattern.compile("[A-Za-z]+");
            Matcher matcher = pattern.matcher(str);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 判断是纯汉字
     * <br/>不匹配, 返回false
     */
    public static boolean isCn(Object text) {
        if (isEmpty(text)) {
            return false;
        }
        String str = text.toString();
        if (str != null && !str.trim().equalsIgnoreCase("")) {
            Pattern pattern = Pattern.compile("[\u4E00-\u9FA5\uF900-\uFA2D]+");
            Matcher matcher = pattern.matcher(str);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 仅仅 包含 汉字 和 数字
     * <br/>不匹配, 返回false
     */
    public static boolean isCn$Num(Object text) {
        if (isEmpty(text)) {
            return false;
        }
        String str = text.toString();
        if (str != null && !str.trim().equalsIgnoreCase("")) {
            Pattern pattern = Pattern.compile("[\u4E00-\u9FA5\uF900-\uFA2D0-9]+");
            Matcher matcher = pattern.matcher(str);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 判断含有汉字
     * <br/>不匹配, 返回false
     */
    public static boolean isHaveChinese(Objects text) {
        if (isEmpty(text)) {
            return false;
        }
        String str = text.toString();
        if (str != null && !str.trim().equalsIgnoreCase("")) {
            for (int i = 0; i < str.length(); i++) {
                char ss = str.charAt(i);
                boolean s = String.valueOf(ss).matches("[\u4E00-\u9FA5\uF900-\uFA2D]");
                if (s) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public static boolean isEn$Num(Object text) {
        if (isEmpty(text)) {
            return false;
        }
        String str = text.toString();
        if (str != null && !str.trim().equalsIgnoreCase("")) {
            Pattern pattern = Pattern.compile("^[A-Za-z0-9]+");
            Matcher matcher = pattern.matcher(str);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 判断字符个数, 是否满足长度范围[min, max]; 所有的文字,都算作一个字符
     * <br/>不匹配, 返回false
     */
    public static boolean isLengthOK4Pwd(Object text, int min, int max) {
        if (isEmpty(text)) {
            return false;
        }
        String str = text.toString();
        if (str != null && !str.trim().equalsIgnoreCase("")) {
            String Regular = "^[0-9a-zA-Z]\\w{" + (min - 1) + "," + (max - 1) + "}$";
            Pattern pattern = Pattern.compile(Regular, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(str);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 判断字符个数, 是否满足长度范围[min, max]; 所有的文字,都算作一个字符
     * <br/>不匹配, 返回false
     */
    public static boolean isLengthOK4ChineseAndWords(Object text, int min, int max) {
        if (isEmpty(text)) {
            return false;
        }
        String str = text.toString();
        if (str != null && !str.trim().equalsIgnoreCase("")) {
            String Regular = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D\\w]{" + min + "," + max + "}$";
            Pattern pattern = Pattern.compile(Regular, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(str);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 长度为[min, max]的所有字符
     * <br/>不匹配, 返回false
     */
    public static boolean isLengthOK4AllSymbols(Object text, int min, int max) {
        if (isEmpty(text)) {
            return false;
        }
        String str = text.toString();

        if (str != null && !str.trim().equalsIgnoreCase("")) {
            String Regular = "^.{" + min + "," + max + "}$";
            Pattern pattern = Pattern.compile(Regular, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(str);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 长度为[min, max]的   由数字、26个英文字母或者下划线组成的字符串
     * <br/>不匹配, 返回false
     */
    public static boolean isLengthOK4Words(Object text, int min, int max) {
        if (isEmpty(text)) {
            return false;
        }
        String str = text.toString();
        if (str != null && !str.trim().equalsIgnoreCase("")) {
            String Regular = "^\\w{" + min + "," + max + "}$";
            Pattern pattern = Pattern.compile(Regular, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(str);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 验证邮箱合法性
     * <br/>不匹配, 返回false
     */
    public static boolean isEmail(Object text) {
        if (isEmpty(text)) {
            return false;
        }
        String str = text.toString();
        if (str != null && !str.trim().equalsIgnoreCase("")) {
            String Regular = "^([a-z0-9A-Z]+[-|\\_.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern pattern = Pattern.compile(Regular, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(str);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isPhoneNum(Object text) {
        if (isEmpty(text)) {
            return false;
        }
        String str = text.toString();
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("(1)[0-9]{10}");
            Matcher m = p.matcher(str);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证身份证号码15位
     *
     * @param str 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard15(Object text) {
        if (isEmpty(text)) {
            return false;
        }
        String str = text.toString();
        String REGEX_ID_CARD15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
        return isMatch(REGEX_ID_CARD15, str);
    }

    /**
     * 验证身份证号码18位
     *
     * @param str 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard18(Object text) {
        if (isEmpty(text)) {
            return false;
        }
        String str = text.toString();
        String REGEX_ID_CARD18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";
        return isMatch(REGEX_ID_CARD18, str);
    }

    /**
     * 判断是否匹配正则
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMatch(String regex, CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

    /**
     * @param str
     * @param startLength 从第0个开始，到第 startLength 个保留
     * @param endLength   从最后一个开始 往前endLength 个保留
     * @param star        替换符
     */
    public static String replace(Object text, int startLength, int endLength, String star) {
        if (isEmpty(text)) {
            return (text == null ? "" : text.toString());
        }
        String str = text.toString();
        if ((str == null) || (startLength < 0) || (endLength > str.length())) {
            return str;
        }
        String start3 = str.substring(0, startLength);
        String end2 = str.substring(str.length() - endLength, str.length());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < (str.length() - startLength - endLength); i++) {
            builder.append(star);
        }
        return start3 + builder.toString() + end2;
    }

    /**
     * 反转字符串
     *
     * @param str 待反转字符串
     * @return 反转字符串
     */
    public static String reverse(Object text) {
        if (isEmpty(text)) {
            return (text == null ? "" : text.toString());
        }
        String str = text.toString();
        int len = length(str);
        if (len <= 1) return str;
        int mid = len >> 1;
        char[] chars = str.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null返回0，其他返回自身长度
     */
    public static int length(Object text) {
        return text == null ? 0 : text.toString().length();
    }

    /**
     * 简化字符串，删除空格键、tab键、换行键
     *
     * @param str
     * @return
     */
    public static String compact(Object text) {
        if (isEmpty(text)) {
            return (text == null ? "" : text.toString());
        }
        String str = text.toString();

        /*中文（全角）空格*/
        char CHAR_CHINESE_SPACE = '\u3000';
        char[] cs = new char[str.length()];
        int len = 0;
        for (int n = 0; n < cs.length; n++) {
            char c = str.charAt(n);
            if (c == ' ' || c == '\t' || c == '\r' || c == '\n' || c == CHAR_CHINESE_SPACE)
                continue;
            cs[len] = c;
            len++;
        }
        return new String(cs, 0, len);
    }

    /**
     * 编码
     */
    public static String encode(Object text) {
        if (isEmpty(text)) {
            text = "";
        }
        String str = text.toString();
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            LogTrack.e("有异常：" + e);
        }
        return str;
    }

    /**
     * 求两个字符串数组的并集，利用set的元素唯一性
     *
     * @param arr1
     * @param arr2
     * @return
     */
    public static String[] union(String[] arr1, String[] arr2) {
        Set<String> set = new HashSet<String>();
        for (String str : arr1) {
            set.add(str);
        }
        for (String str : arr2) {
            set.add(str);
        }
        String[] result = {};
        return set.toArray(result);
    }

    /**
     * 求两个字符串数组的交集
     *
     * @param arr1
     * @param arr2
     * @return
     */
    public static String[] intersect(String[] arr1, String[] arr2) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        LinkedList<String> list = new LinkedList<String>();
        for (String str : arr1) {
            if (!map.containsKey(str)) {
                map.put(str, Boolean.FALSE);
            }
        }
        for (String str : arr2) {
            if (map.containsKey(str)) {
                map.put(str, Boolean.TRUE);
            }
        }
        for (Map.Entry<String, Boolean> e : map.entrySet()) {
            if (e.getValue().equals(Boolean.TRUE)) {
                list.add(e.getKey());
            }
        }
        String[] result = {};
        return list.toArray(result);
    }

    /**
     * 求两个字符串数组的差集
     *
     * @param arr1
     * @param arr2
     * @return
     */
    public static String[] minus(String[] arr1, String[] arr2) {
        LinkedList<String> list = new LinkedList<String>();
        LinkedList<String> history = new LinkedList<String>();
        String[] longerArr = arr1;
        String[] shorterArr = arr2;
        //找出较长的数组来减较短的数组
        if (arr1.length > arr2.length) {
            longerArr = arr2;
            shorterArr = arr1;
        }
        for (String str : longerArr) {
            if (!list.contains(str)) {
                list.add(str);
            }
        }
        for (String str : shorterArr) {
            if (list.contains(str)) {
                history.add(str);
                list.remove(str);
            } else {
                if (!history.contains(str)) {
                    list.add(str);
                }
            }
        }

        String[] result = {};
        return list.toArray(result);
    }

    /**
     * 产生 length 个随机数 并组成一个 String
     */
    public static String getRandomIntString(int length) {
        return getRandomIntString(length, "");
    }

    /**
     * 产生 length 个随机数 并组成一个 String
     */
    public static String getRandomIntString(int length, String split) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            Random random = new Random();
            builder.append(random.nextInt(10) + split);
        }
        return builder.toString().substring(0, builder.length() - 1);
    }

    public static double string2Double(String text, double defaultValue) {
        if (TextUtils.isEmpty(text)) {
            Log.e(StringUtil.class.getSimpleName(), "text == null");
            return defaultValue;
        }
        try {
            return Double.parseDouble(text);
        } catch (Exception e) {
            Log.e(StringUtil.class.getSimpleName(), "有异常：" + e);
            return defaultValue;
        }
    }

    /**
     * 截取 111.00 中的整数部分 111
     */
    public static String getIntPart(Object text) {

        String tmp = "0";
        if (!isEmpty(text)) {
            tmp = text.toString();
        }

        if (!tmp.contains(".")) {
            return tmp;
        }
        try {
            tmp = tmp.split("\\.")[0];
        } catch (Exception e) {
            return tmp;
        }

        return tmp;
    }

    /**
     * 截取 111.00 中的小数部分 00
     */
    public static String getFloatPart(Object text) {
        String tmp = "0";
        if (!isEmpty(text)) {
            tmp = text.toString();
        }
        if (!tmp.contains(".")) {
            return text.toString();
        }
        try {
            tmp = tmp.split("\\.")[1];
        } catch (Exception e) {
            return tmp;
        }
        return tmp;
    }

    public static int string2Int(Object text, int defaultValue) {
        if (isEmpty(text)) {
            return defaultValue;
        }
        String str = text.toString();
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static float string2Float(Object text, int defaultValue) {
        if (isEmpty(text)) {
            return defaultValue;
        }
        String str = text.toString();
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将字符串转成MD5值
     *
     * @param string
     * @return
     */
    public static String string2MD5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
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
     * 从 最后一个 split开始，到text结束的字串（不包含split）
     */
    public static String subString4End(@NonNull Object text, String split) {
        if (isEmpty(text)) {
            return (text == null ? "" : text.toString());
        }
        String str = text.toString();
        int lastIndexOf = str.lastIndexOf(split);
        if (lastIndexOf < 0) {
            return str;
        }
        return str.substring(lastIndexOf + 1, str.length());
    }

    /**
     * 从 第一个，到split结束的字串（不包含split）
     */
    public static String subString4Start(@NonNull Object text, String split) {
        if (isEmpty(text)) {
            return (text == null ? "" : text.toString());
        }
        String str = text.toString();
        int startIndex = str.indexOf(split);
        if (startIndex < 0) {
            return str;
        }
        return str.substring(0, startIndex);
    }

    /**
     * 从 最后一个 split开始，到text结束的字串（不包含split）
     */
    private static String subString4Start2StringEnd(@NonNull Object text, String split) {
        if (isEmpty(text)) {
            return (text == null ? "" : text.toString());
        }
        String str = text.toString();
        int startIndex = str.indexOf(split);
        if (startIndex < 0) {
            startIndex = 0;
        }
        return str.substring(startIndex, str.length());
    }

    /**
     * 从 最后一个 split开始，到text结束的字串（不包含split）
     */
    private static String subString4Start2End(@NonNull Object text, String split) {
        if (isEmpty(text)) {
            return (text == null ? "" : text.toString());
        }
        String str = text.toString();
        int startIndex = str.indexOf(split);
        if (startIndex < 0) {
            startIndex = 0;
        }
        int lastIndexOf = str.lastIndexOf(split);
        if (lastIndexOf < 0) {
            return str;
        }
        return str.substring(startIndex, lastIndexOf);
    }

    public static String trim(@NonNull Object text, char oldChar) {
        if (isEmpty(text)) {
            return (text == null ? "" : text.toString());
        }
        String str = text.toString();
        char[] charArray = str.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < charArray.length; i++) {
            char indexChar = charArray[i];
            if (indexChar != oldChar) {
                builder.append(indexChar);
            }
        }
        return builder.toString();
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param str 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isSpace(Object text) {
        if (isEmpty(text)) {
            return true;
        }
        String str = text.toString();
        return (str == null || str.trim().length() == 0);
    }

    /**
     * 验证IP地址
     *
     * @param str 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIP(Object text) {
        if (isEmpty(text)) {
            return false;
        }
        String str = text.toString();
        String REGEX_IP = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
        return isMatch(REGEX_IP, str);
    }

    /**
     * 验证URL
     *
     * @param str 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isURL(Object text) {
        if (isEmpty(text)) {
            return false;
        }
        String str = text.toString();
        String REGEX_URL = "[a-zA-z]+://[^\\s]*";
        return isMatch(REGEX_URL, str);
    }

    /**
     * 判断两字符串是否相等
     *
     * @param strA 待校验字符串a
     * @param strB 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equals(Object a, Object b) {
        if (a == b) return true;
        if (a == null && b != null) return false;
        if (a != null && b == null) return false;
        String strA = a.toString();
        String strB = b.toString();
        int length;
        if (strA != null && strB != null && (length = strA.length()) == strB.length()) {
            if (strA instanceof String && strB instanceof String) {
                return strA.equals(strB);
            } else {
                for (int i = 0; i < length; i++) {
                    if (strA.charAt(i) != strB.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param strA 待校验字符串a
     * @param strB 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equalsIgnoreCase(Object a, Object b) {
        if (a == b) return true;
        if (a == null && b != null) return false;
        if (a != null && b == null) return false;
        String strA = a.toString();
        String strB = b.toString();
        return (strA == strB) || (strB != null) && (strA.length() == strB.length()) && strA.regionMatches(true, 0, strB, 0, strB.length());
    }

    /**
     * 判断String是null 或 ""
     */
    private static boolean isEmpty(Object text) {
        if (text == null) {
            return true;
        }
        return ((text.toString()).length() == 0);
    }
}

