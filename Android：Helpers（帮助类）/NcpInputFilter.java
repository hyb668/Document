package org.alex.helper;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.TransformationMethod;
import android.widget.EditText;

/**
 * no copy cut
 * 不可以 剪切 、粘贴 的过滤器
 * editText.setFilters(new InputFilter[]{new NcpInputFilter(editText)});
 */
public class NcpInputFilter implements InputFilter {
    private EditText editText;
    /**
     * 屏蔽粘贴功能
     */
    private boolean isNoPaste;
    /**
     * 解决 剪切  和 粘贴  的 冲突
     */
    private boolean isAlexPaste;
    /**
     * 解决 切换输入框 可见性，带来的 冲突问题
     */
    private TransformationMethod transformationMethod;
    /**
     * 解决 切换输入框 可见性，带来的 冲突问题
     */
    private int inputType;

    public NcpInputFilter(EditText editText) {
        this.editText = editText;
        isNoPaste = true;
        isAlexPaste = false;
        transformationMethod = editText.getTransformationMethod();
        inputType = editText.getInputType();
        //setTransformationMethod
    }

    /**
     * @param source 当前键入、键出 的 字符
     * @param dest   所有的字符（除当前键入之外）
     * @param start  新输入的字符串起始下标
     * @param end    新输入的字符串终点下标
     * @param dstart 原内容发生改变，改变的起点坐标
     * @param dend   原内容发生改变，改变的终点坐标
     *               end > start 表示键入数据；end = 0 = start 表示键出数据
     * @return 当前键入 键出的字符
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if (dest == null) {
            return "";
        }
        if (TextUtils.isEmpty(source) && (dend - dstart > 1)) {
            /*屏蔽剪切*/
            isAlexPaste = true;
            editText.setText(dest);
            editText.setSelection(dest.length());
            return "";
        }
        if ((!TextUtils.isEmpty(source)) && (end - start > 1) && isNoPaste) {
            if (isAlexPaste) {
                isAlexPaste = false;
                return editText.getText();
            }
            /*屏蔽粘贴*/
            TransformationMethod method = editText.getTransformationMethod();
            boolean methodEquals = method.getClass().getSimpleName().equalsIgnoreCase(transformationMethod.getClass().getSimpleName());
            boolean inputTypeEquals = inputType == editText.getInputType();
            if (methodEquals && inputTypeEquals) {
                return "";
            } else {
                transformationMethod = method;
                inputType = editText.getInputType();
                return source;
            }
        }
        return source;
    }

}
