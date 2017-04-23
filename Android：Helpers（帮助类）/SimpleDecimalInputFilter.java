package github.alex.decimaledittext.core;

import github.alex.util.LogUtil;
import android.annotation.TargetApi;
import android.os.Build;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.EditText;

/**理财类输入框格过滤器
 * 屏蔽复制粘贴、剪切功能
 * 限制 小数点以后的 位数为 floatLength
 * 限制最大输入金额为 maxValue
 * 不让输入0.00
 * 当输入 【0】 的时候， 自动生成 【0.】
 * 当输入【.】的时候，自动生成【0.】
 * 当原字符为【0.0】的时候，不让输入【0】
 * 当原字符为【0.0*】的时候（*非零），【.】不让删除
 * 当原字符为【*.00】的时候（*为非零整数），【*】不让删除
 * 当原字符为【*0.00】的时候，【*】不让删除
 * 当原字符为【*****.**】的时候，如果删除【.】造成新数据大于最大值，【.】不让删除
 * 当原字符为【*****.**】的时候，在整数部分键入新数据【#】，造成新数据大于最大值，【#】不让键入
 * 当原字符为【********】的时候，在整数部分键入新数据【.】，造成小数点个数大于 floatLength ，【.】不让键入
 * */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SimpleDecimalInputFilter implements InputFilter
{
	private EditText editText;
	private double maxValue;
	private int floatLength;
	/**屏蔽粘贴功能*/
	private boolean isNoPaste;
	/**解决 剪切  和 粘贴  的 冲突*/
	private boolean isAlexPaste;

	/**@param editText  输入框
	 * @param floatLength
	 * @param 最大输入金额 maxValue
	 * @param 整数部分最多个数 intLength
	 * @param 小数部分最多个数 floatLength */
	public SimpleDecimalInputFilter(EditText editText, double maxValue, int floatLength) {
		this.editText  = editText;
		this.maxValue = maxValue;
		this.floatLength = floatLength;
		editText.setLongClickable(false);
		editText.setTextIsSelectable(false); 
		isNoPaste = true;
		isAlexPaste = false;
	}
	/**
	 * @param source 当前键入、键出 的 字符
	 * @param dest 所有的字符（除当前键入之外）
	 * @param start 新输入的字符串起始下标
	 * @param end  新输入的字符串终点下标
	 * @param dstart 原内容发生改变，改变的起点坐标
	 * @param dend 原内容发生改变，改变的终点坐标
	 * end > start 表示键入数据；end = 0 = start 表示键出数据
	 * @return 当前键入 键出的字符
	 * */
	@Override  
	public CharSequence filter(CharSequence source, int start, int end,   Spanned dest, int dstart, int dend) {   
		/*光标位置*/
		int selectionStart = editText.getSelectionStart();
		if((dstart < dend) && (TextUtils.isEmpty(source))){

		} 
		if(dest == null){
			return "";
		}
		if( TextUtils.isEmpty(source) && (dend-dstart > 1)){
			/*屏蔽剪切*/
			isAlexPaste = true;
			editText.setText(dest);
			editText.setSelection(dest.length());
			return "";
		}
		if((!TextUtils.isEmpty(source)) && (end-start > 1)  && isNoPaste){
			if(isAlexPaste){
				isAlexPaste = false;
				return editText.getText();
			}
			/*屏蔽粘贴*/
			return "";
		} 
		String destValue = dest.toString();  
		/*输入框真实数据，原有数据 和 键入 键出 进行逻辑运算之后的 真实数据*/
		String trueFullText = "";
		/*当前 键入一个字符 ， subIndexStart 是键入的字符为止 之前的 所有字符*/
		String subIndexStart = "";
		/*当前 键入一个字符 ， subIndexStart 是键入的字符为止 之后的 所有字符*/
		String subIndexEnd = "";
		if(!TextUtils.isEmpty(dest)){
			/*插入一个字符*/
			subIndexStart = destValue.substring(0, dstart); 
			if(end > start){
				/*键入数据*/
				if(dend == 0){
					subIndexEnd = destValue.substring(0, dest.length());
				}else{
					subIndexEnd = destValue.substring(dend, dest.length());
				}
			}else{
				/*键出数据*/
				if(dend == 0){
					subIndexEnd = destValue.substring(0, dest.length());
				}else{
					subIndexEnd = destValue.substring(dend, dest.length());
				}
			}
		}
		LogUtil.e("到这了");
		trueFullText = subIndexStart+source+subIndexEnd;
		LogUtil.e("全内容 = "+trueFullText+" source = "+source+" dest = "+dest+" start = "+start+" end = "+end+" dstart = "+dstart+" dend = "+dend+" 光标下标 = "+selectionStart);

		String[] trueFullTextArray = trueFullText.split("\\.");  
		LogUtil.e("到这了");
		if (dest.length() == 0 && source.equals(".")) {  
			/*如果 一开始 就输入 . 自动补全成 0. */
			LogUtil.e("到这了");
			return "0.";  
		} 
		if("0".equals(source) && (dest.length() ==0)){
			/*如果 一开始 就输入 0 自动补全成 0. */
			LogUtil.e("到这了");
			return "0.";
		}

		LogUtil.e("到这了");
		if((selectionStart == 0) && (!TextUtils.isEmpty(destValue))&& ('.'  != destValue.charAt(1))){
			if("0".equals(source) || ".".equals(source)){
				LogUtil.e("到这了");
				/*如果原字符串不是空， 二个字符不是. 不让输入 0*/
				return "";
			}

		}
		LogUtil.e("到这了");
		if((!TextUtils.isEmpty(destValue)) && (destValue.length() >1)){
			LogUtil.e("destValue = "+destValue);
			if(destValue.length() > 2){
				LogUtil.e("destValue = "+destValue+" "+destValue.charAt(0)+" "+destValue.charAt(1)+" "+destValue.charAt(2));
			}
			LogUtil.e("到这了");
			if(('0'==destValue.charAt(0)) && (destValue.length()>2) && ('.'==destValue.charAt(1)) &&('0'==destValue.charAt(2)) && "0".equals(source)){
				/*原文本是 0.0  不让在输入0*/
				LogUtil.e("到这了");
				return "";
			}
			LogUtil.e("到这了");
			if((trueFullTextArray.length >1) && (trueFullTextArray[1].length() > floatLength)){
				LogUtil.e("到这了");
				return "";
			}
			LogUtil.e("到这了");
			/*原文本有 2 个以上 字符*/
			if(('.'==destValue.charAt(1)) && (selectionStart==0) && TextUtils.isEmpty(source)){
				LogUtil.e("到这了");
				/*如果 往前删除，剩下的 第一个字符 是 .   自动补全 0. */
				return destValue.charAt(0)+"";
			}
			LogUtil.e("到这了");
			if((destValue.length()>3) && ('.'==destValue.charAt(2)) && ('0'==destValue.charAt(1)) && ('0'==destValue.charAt(3))){
				LogUtil.e("到这了");
				/*【*0.00】时， *不让删除 */
				return "";
			}
			LogUtil.e("到这了");
			if((destValue.length() >1)  && ('0'==destValue.charAt(1))  && (selectionStart==1)){
				/*第二个字符 是 0  第一个 不让删除*/
				LogUtil.e("到这了");
				if((destValue.length() >2 ) && ('.'==destValue.charAt(2))  && TextUtils.isEmpty(source)){
					LogUtil.e("到这了");
					/*第二个字符 是 0  ，第三个是 .   ，第一个 不让删除*/
					return destValue.charAt(0)+"";
				}
				LogUtil.e("到这了"); 
				return destValue.charAt(0)+"";
			}
		}
		if((destValue.length()>2) &&('0'==destValue.charAt(0)) && ('.'==destValue.charAt(1)) && ('0'==destValue.charAt(2)) && (TextUtils.isEmpty(source))){
			LogUtil.e("到这了");
			return ".";
		}
		LogUtil.e("到这了");
		if((destValue.length() > 1) && ('.'==destValue.charAt(1)) && (selectionStart==1)){
			LogUtil.e("到这了");
			/*  【*.0*】的情况下，第一个* 不让删除  */
			if((destValue.length()>3) && ('.'==destValue.charAt(1)) && ('0'==destValue.charAt(2)) && ('0'==destValue.charAt(3)) && (TextUtils.isEmpty(source)) ){
				return destValue.charAt(0)+"";
			}
		}
		LogUtil.e("到这了");
		double trueFullTextDoubleValue = string2Double(trueFullText);

		if(trueFullText.contains(".")){
			LogUtil.e("到这了");
			/*当前  输入框 包含小数点*/
			if(trueFullTextArray.length > 1){
				LogUtil.e("到这了");
				if(       (trueFullTextArray[1].length() > floatLength)      ||         ( trueFullTextDoubleValue  >= maxValue )            ){
					LogUtil.e("到这了");
					return "";
				}
			}

		}else{
			LogUtil.e("到这了");
			/*当前 输入框 不包含 小数点*/
			if(   (trueFullTextDoubleValue  >= maxValue )     &&     ( !".".equals(source) ) ){
				if(destValue.contains(".")){
					return ".";
				}
				return "";
			}
		}

		return source;  
	}

	private double string2Double(String text){
		try
		{
			return Double.parseDouble(text);
		} catch (Exception e){
			return 0D;
		}

	}


}