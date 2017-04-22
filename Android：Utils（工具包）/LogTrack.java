package org.alex.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 作者：Alex
 * 时间：2016/08/21 22 49
 * 简述：
 */
@SuppressLint("DefaultLocale")
@SuppressWarnings("all")
public class LogTrack {
    private static String headTag = LogTrack.class.getSimpleName();
    private static boolean isDebug = true;

    /**
     * 打印 日志
     */
    public static void debug(boolean isDebug) {
        LogTrack.isDebug = isDebug;
    }

    private static boolean debug() {
        return isDebug;
    }


    /**
     * 设置 消息 头
     *
     * @param headTag
     */
    public static void headTag(String headTag) {
        if (isNotEmpty(headTag)) {
            LogTrack.headTag = headTag;
        }
    }

    private LogTrack() {
    }

    public static void v(Object msg) {
        printLog(false, 1, (String) null, new Object[]{msg});
    }


    public static void v(Object msg, boolean isPrintTrack) {
        printLog(isPrintTrack, 1, (String) null, new Object[]{msg});
    }

    public static void v(Object msg, Object target, boolean isPrintTrack) {
        printLog(isPrintTrack, 1, (String) null, new Object[]{msg + " >>> " + target});
    }

    public static void d(Object msg) {
        printLog(false, 2, (String) null, new Object[]{msg});
    }

    public static void d(Object msg, boolean isPrintTrack) {
        printLog(isPrintTrack, 2, (String) null, new Object[]{msg});
    }

    public static void d(Object msg, Object target, boolean isPrintTrack) {
        printLog(isPrintTrack, 2, (String) null, new Object[]{msg + " >>> " + target});
    }

    public static void i(Object msg) {
        printLog(false, 3, (String) null, new Object[]{msg});
    }


    public static void i(Object msg, boolean isPrintTrack) {
        printLog(isPrintTrack, 3, (String) null, new Object[]{msg});
    }

    public static void i(Object msg, Object target, boolean isPrintTrack) {
        printLog(isPrintTrack, 3, (String) null, new Object[]{msg + " >>> " + target});
    }

    public static void w(Object msg) {
        printLog(false, 4, (String) null, new Object[]{msg});
    }


    public static void w(Object msg, boolean isPrintTrack) {
        printLog(isPrintTrack, 4, (String) null, new Object[]{msg});
    }

    public static void w(Object msg, Object target, boolean isPrintTrack) {
        printLog(isPrintTrack, 4, (String) null, new Object[]{msg + " >>> " + target});
    }

    public static void e(Object msg) {
        printLog(false, 5, (String) null, new Object[]{msg});
    }

    public static void e(Object msg, boolean isPrintTrack) {
        printLog(isPrintTrack, 5, (String) null, new Object[]{msg});
    }

    public static void e(Object msg, Object target, boolean isPrintTrack) {
        printLog(isPrintTrack, 5, (String) null, new Object[]{msg + " >>> " + target});
    }

    public static void wtf(Object msg) {
        printLog(false, 6, (String) null, new Object[]{msg});
    }

    public static void wtf(Object msg, boolean isPrintTrack) {
        printLog(isPrintTrack, 6, (String) null, new Object[]{msg});
    }

    public static void wtf(Object msg, Object target, boolean isPrintTrack) {
        printLog(isPrintTrack, 6, (String) null, new Object[]{msg + " >>> " + target});
    }

    private static void printLog(boolean isPrintTrack, int type, String tagStr, Object... objects) {
        if (debug()) {
            String[] contents = wrapperContent(5, tagStr, objects);
            String tag = headTag;
            StringBuilder headBuilder = new StringBuilder();
            if (isPrintTrack) {
                headBuilder.append(" 调用顺序 ");
            }
            String tmpSplit = "-->";
            for (int i = Thread.currentThread().getStackTrace().length - 1; (i >= 0) && isPrintTrack; i--) {
                String path = wrapperContent(i, tagStr, objects)[2];
                if (isMineOwnThread(path)) {
                    headBuilder.append("\n" + path + tmpSplit);
                }
            }
            if (isPrintTrack && (headBuilder.length() > tmpSplit.length())) {
                headBuilder = headBuilder.delete(headBuilder.length() - tmpSplit.length(), headBuilder.length());
            }

            String msg = contents[2] + headBuilder.toString() + contents[1];
            short maxLength = 4000;
            int countOfSub = msg.length() / maxLength;
            int index = 0;
            if (countOfSub > 0) {
                for (int i = 0; i < countOfSub; ++i) {
                    String sub = msg.substring(index, index + maxLength);
                    printSub(type, tag, sub);
                    index += maxLength;
                }
                printSub(type, tag, msg.substring(index, msg.length()));
            } else {
                printSub(type, tag, msg);
            }
        }
    }

    private static String[] wrapperContent(int stackTraceIndex, String tagStr, Object... objects) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement targetElement = stackTrace[stackTraceIndex];
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = subString4Start(classNameInfo[classNameInfo.length - 1], "$") + ".java";
        }

        String methodName = targetElement.getMethodName();
        int lineNumber = targetElement.getLineNumber();
        if (lineNumber < 0) {
            lineNumber = 0;
        }

        String methodNameShort = methodName.substring(0, 1) + methodName.substring(1);
        String tag = tagStr == null ? className : tagStr;
        if (TextUtils.isEmpty(tag)) {
            tag = "LogCat";
        }

        String msg = objects == null ? "Log with null object" : getObjectsString(objects);
        String headString = " ";
        String result = classNameInfo[classNameInfo.length - 1];
        if (result.contains("$")) {
            if (className.contains("$")) {
                headString = "[ (" + subString4Start(className, "$") + ".java:" + lineNumber + ")#" + subString4Start2StringEnd(result, "$") + "#" + methodNameShort + " ] ";
            } else {
                headString = "[ (" + className + ":" + lineNumber + ")#" + subString4Start2StringEnd(result, "$") + "#" + methodNameShort + " ] ";
            }
        } else {
            headString = "[ (" + className + ":" + lineNumber + ")#" + methodNameShort + " ] ";
        }

        return new String[]{tag, msg, headString};
    }

    private static String getObjectsString(Object... objects) {
        if (objects.length > 1) {
            StringBuilder var4 = new StringBuilder();
            var4.append("\n");

            for (int i = 0; i < objects.length; ++i) {
                Object object1 = objects[i];
                if (object1 == null) {
                    var4.append("Param").append("[").append(i).append("]").append(" = ").append("null").append("\n");
                } else {
                    var4.append("Param").append("[").append(i).append("]").append(" = ").append(object1.toString()).append("\n");
                }
            }

            return var4.toString();
        } else {
            Object object = objects[0];
            return object == null ? "null" : object.toString();
        }
    }

    private static void printSub(int type, String tag, String sub) {
        switch (type) {
            case 1:
                Log.v(tag, sub);
                break;
            case 2:
                Log.d(tag, sub);
                break;
            case 3:
                Log.i(tag, sub);
                break;
            case 4:
                Log.w(tag, sub);
                break;
            case 5:
                Log.e(tag, sub);
                break;
            case 6:
                Log.wtf(tag, sub);
        }

    }

    /**
     * 从 第一个，到split结束的字串（不包含split）
     */
    private static String subString4Start(@NonNull String text, String split) {
        if (TextUtils.isEmpty(text)) {
            return "";
        }
        int startIndex = text.indexOf("$");
        if (startIndex < 0) {
            return text;
        }
        return text.substring(0, startIndex);
    }

    /**
     * 从 最后一个 split开始，到text结束的字串（不包含split）
     */
    private static String subString4End(@NonNull String text, String split) {
        if (TextUtils.isEmpty(text)) {
            return "";
        }
        int lastIndexOf = text.lastIndexOf(split);
        if (lastIndexOf < 0) {
            return text;
        }
        return text.substring(lastIndexOf + 1, text.length());
    }

    /**
     * 从 最后一个 split开始，到text结束的字串（不包含split）
     */
    private static String subString4Start2StringEnd(@NonNull String text, String split) {
        if (TextUtils.isEmpty(text)) {
            return "";
        }
        int startIndex = text.indexOf("$");
        if (startIndex < 0) {
            startIndex = 0;
        }
        return text.substring(startIndex, text.length());
    }
    /**
     * 对象为空
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            return (((String) obj).length() == 0);
        }
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }
        if (obj instanceof Collection) {
            return ((List) obj).isEmpty();
        }
        if (obj.getClass().isArray()) {
            return (Array.getLength(obj) == 0);
        }
        if (obj instanceof Object[]) {
            return (((Object[]) obj).length == 0);
        }
        if (obj instanceof JSONObject) {
            return ((JSONObject) obj).length() <= 0;
        }
        if (obj instanceof JSONArray) {
            return ((JSONArray) obj).length() <= 0;
        }
        if (obj instanceof android.util.SparseArray) {
            return (((android.util.SparseArray) obj).size() == 0);
        }
        if (obj instanceof android.util.SparseIntArray) {
            return (((android.util.SparseIntArray) obj).size() == 0);
        }
        if (obj instanceof android.util.SparseBooleanArray) {
            return (((android.util.SparseBooleanArray) obj).size() == 0);
        }
        if (obj instanceof android.support.v4.util.SimpleArrayMap) {
            return (((android.support.v4.util.SimpleArrayMap) obj).size() == 0);
        }
        if (obj instanceof android.support.v4.util.LongSparseArray) {
            return (((android.support.v4.util.LongSparseArray) obj).size() == 0);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (obj instanceof android.util.LongSparseArray) {
                return (((android.util.LongSparseArray) obj).size() <= 0);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (obj instanceof android.util.SparseLongArray) {
                return (((android.util.SparseLongArray) obj).size() == 0);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (obj instanceof android.util.ArraySet) {
                return ((android.util.ArraySet) obj).isEmpty();
            }
        }
        return ((obj.toString()).length() <= 0);
    }

    /**
     * 对象非空
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    private static boolean isMineOwnThread(String contents) {
        String array[] = new String[]{"" + "[ (Method.java", "[ (ActivityThread.java", "[ (Looper.java", "[ (Handler.java", "[ (ActivityThread.java", "[ (Instrumentation.java", "[ (Activity.java", "[ (Thread.java", "[ (VMStack.java", "[ (ZygoteInit.java", "[ (" + LogTrack.class.getSimpleName() + ".java"};
        boolean isMine = true;
        for (int i = 0; i < array.length; i++) {
            if (contents.startsWith(array[i])) {
                return false;
            }
        }
        return true;
    }
}