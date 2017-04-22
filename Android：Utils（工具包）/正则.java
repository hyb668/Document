package org.alex.model;

import java.util.regex.Pattern;

/**
 * 作者：Alex
 * 时间：2017/4/19 17:55
 * 简述：
 */
public class PatternEnum {
    /**
     * 必须 包含一个 英文字母 和 一个数字
     */
    public static String mustContainsEn$Num = "^(?![^a-zA-Z]+$)(?!\\D+$).{1,}$";
    /**
     * 只能有中文  和 中文符号 和 空格
     * 中文 [\u4E00-\u9FA5]
     * 不知道是什么 \uF900-\uFA2D    \uFF30-\uFFA0
     * ， 。、？！ ；  ： “”（ ）  《 》{} \uff0c\u0020\u3002\u3001\uff1f\uff01\uff1b\uff1a\u201c\u201d\uff08\uff09\u300a\u300b\u007b\u007d
     */
    public static Pattern onlyContainsCn$Symbol$Blank = Pattern.compile("([\u4E00-\u9FA5\uF900-\uFA2D\u0020\uFF0C·\u0020\u3002\u3001\uFF1F\uFF01\uFF1B\uFF1A\u201C\u201D\uFF08\uFF09\u300A\u300B\u0026\u007b\u007d])+");
    public static Pattern onlyContainsCn$Symbol = Pattern.compile("([\u4E00-\u9FA5\uF900-\uFA2D\u0020\uFF0C·\u3002\u3001\uFF1F\uFF01\uFF1B\uFF1A\u201C\u201D\uFF08\uFF09\u300A\u300B\u0026\u007b\u007d])+");
    public static Pattern onlyContainsCn = Pattern.compile("([\u4E00-\u9FA5\uF900-\uFA2D\u0020\uFF0C\u3002\u3001\uFF1F\uFF01\uFF1B\uFF1A\u201C\u201D\uFF08\uFF09\u300A\u300B\u0026\u007b\u007d])+");
    /**
     * 13 14 15 16 17 18 19 开头的11位  手机号码
     */
    public static Pattern phone = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$");
    /**
     * 允许所有的 英文 和 数字， 长度必须大于 1
     */
    public static Pattern wordsOver1 = Pattern.compile("^\\w{1,}$");
    /**
     * 允许所有的字符， 但是长度必须大于等于2
     */
    public static Pattern allCharacterOver2 = Pattern.compile("^.{2,}$");
}
