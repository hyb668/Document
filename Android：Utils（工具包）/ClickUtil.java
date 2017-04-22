package com.qiyuan.qianguan.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
/**
 * <pre>
 *@Override
public boolean onKeyDown(int keyCode, KeyEvent event) 
{
	if (keyCode == KeyEvent.KEYCODE_BACK) {
		ClickUtil.getInstance().shortNormal(this, 1500, "再按一次返回键退出");
		return true;
	}
	return super.onKeyDown(keyCode, event);
}
 * </pre>
 * */
public class ClickUtil
{
	private static GradientDrawable gradientDrawableNormal;
	/**
	 * 开始任务的时间。
	 */
	private long mStartTime;
	private static ClickUtil clickUtil;
	private static int gravityNone = -100;
	public static ClickUtil getInstance()
	{
		if(clickUtil == null){
			synchronized (ClickUtil.class){
				clickUtil = (clickUtil==null) ? new ClickUtil():clickUtil;
				gradientDrawableNormal = new GradientDrawable();
				gradientDrawableNormal.setShape(GradientDrawable.RECTANGLE);
				gradientDrawableNormal.setColor(Color.parseColor("#99353535"));
				float radius = 10;
				gradientDrawableNormal.setCornerRadii(new float[]{radius , radius, radius, radius, radius, radius, radius, radius});
			}
		}
		return clickUtil;
	}
	/**
	 * 当某个动作要双击才执行时，调用此方法。
	 * 
	 * @param delayTime
	 *            判断双击的时间。
	 * @param msg
	 *            当第一次点击时，弹出的提示信息。如果为null，则不作提示。
	 */
	public void shortAtCenter(Context context, int delayTime, String text) {
		if (!doInDelayTime(delayTime)) {
			show(context, Gravity.CENTER, Toast.LENGTH_SHORT, text);
		}else{			
			((Activity)context).finish();
		}
	}

	/**
	 * 当某个动作要双击才执行时，调用此方法。
	 * 
	 * @param delayTime
	 *            判断双击的时间。
	 * @param msg
	 *            当第一次点击时，弹出的提示信息。如果为null，则不作提示。
	 */
	public void shortAtTop(Context context, int delayTime, String text) {
		if (!doInDelayTime(delayTime)) {
			show(context, Gravity.TOP, Toast.LENGTH_SHORT, text);
		}else{			
			((Activity)context).finish();
		}
		//shortAtCenter(context, msg);
	}
	/**
	 * 当某个动作要双击才执行时，调用此方法。
	 * 
	 * @param delayTime
	 *            判断双击的时间。
	 * @param msg
	 *            当第一次点击时，弹出的提示信息。如果为null，则不作提示。
	 */
	public void shortNormal(Context context, int delayTime, String text) {
		if (!doInDelayTime(delayTime)) {
			show(context, gravityNone, Toast.LENGTH_SHORT, text);
		}else{			
			((Activity)context).finish();
		}
		//shortAtCenter(context, msg);
	}
	public void longNormal(Context context, int delayTime, String text){
		if (!doInDelayTime(delayTime)) {
			show(context, Gravity.CENTER, Toast.LENGTH_LONG, text);
		}else{			
			((Activity)context).finish();
		}

	}
	public void longAtCenter(Context context, int delayTime, String text){
		if (!doInDelayTime(delayTime)) {
			show(context, Gravity.CENTER, Toast.LENGTH_LONG, text);
		}else{			
			((Activity)context).finish();
		}

	}
	public void longAtTop(Context context, int delayTime, String text){
		if (!doInDelayTime(delayTime)) {
			show(context, Gravity.TOP, Toast.LENGTH_LONG, text);
		}else{			
			((Activity)context).finish();
		}

	}
	/**
	 * 如果是在指定的时间内则执行doOnDoubleClick，否则返回false。
	 * 
	 * @param delayTime
	 *            指定的延迟时间。
	 * @return 当且仅当在指定的时间内时返回true,否则返回false。
	 */
	protected boolean doInDelayTime(int delayTime) {
		long nowTime = System.currentTimeMillis();
		if (nowTime - mStartTime <= delayTime) {
			mStartTime = -1;
			return true;
		}
		mStartTime = nowTime;
		return false;
	}
	@SuppressWarnings("deprecation")
	public void show(Context context, int gravity, int duration, String text) {
		Toast toast = Toast.makeText(context, text, duration);
		if(gravity!=gravityNone){
			toast.setGravity(gravity, 0, -100);
		}
		TextView textView = new TextView(context);
		textView.setTextColor(Color.parseColor("#FFFFFF"));
		textView.setPadding(dp2Px(8, context),dp2Px(8, context),dp2Px(8, context), dp2Px(8, context));
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		textView.setLayoutParams(params);
		textView.setText(text);
		textView.setBackgroundDrawable(gradientDrawableNormal);
		toast.setView(textView);
		toast.show();
	}
	protected String tag(){
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		//" getClassName = "++" getMethodName = "++" getLineNumber = "+stackTrace[i].getLineNumber());
		int index = 3;
		String fileName = stackTrace[index].getFileName();
		String methodName = stackTrace[index].getMethodName();
		int lineNumber = stackTrace[index].getLineNumber();
		for (int i = 0; i < stackTrace.length; i++){
			//Log.e("i = "+i+" TAG", "fileName = "+stackTrace[i].getFileName()+" methodName = "+stackTrace[i].getMethodName()+" lineNumber = "+stackTrace[i].getLineNumber());
		}
		return "["+fileName+":"+lineNumber+"]"+" # "+methodName;
	}
	/**数据转换: dp---->px*/
	private int dp2Px(float dp, Context context)
	{
		if (context == null) {
			return -1;
		}
		return (int) (dp * context.getResources().getDisplayMetrics().density);
	}
}
