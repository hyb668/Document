package com.qiyuan.qianguan.helper;

import org.xutils.common.util.LogUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
public class SensorHelper
{
	private SensorManager sensorManager;
	private Vibrator vibrator;
	private Handler handler;
	private SensorEventListener sensorEventListener;
	private OnShakeListener onShakeListener;
	private static final int whatShaking = 100;
	/**可以吊起震动   并响应回调*/
	private boolean canShake;
	private StringBuilder builder;
	/**x轴上的 重力感应*/
	protected static final int xAxis = 14;	
	/**y轴上的 重力感应*/
	protected static final int yAxis = 14;	
	/**z轴上的 重力感应*/
	protected static final int zAxis = 14 ;
	public SensorHelper(Context context) {
		builder = new StringBuilder();
		handler = new SensorHandler();
		canShake = true;
		sensorManager = (SensorManager) context.getSystemService(Activity.SENSOR_SERVICE);   
		vibrator = (Vibrator) context.getSystemService(Activity.VIBRATOR_SERVICE);   
	}
	public void registerListener()
	{
		if (sensorManager != null) {// 注册监听器  
			sensorEventListener = new MySensorEventListener();
			sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);   
			// 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率   
		}
	}
	public void unregisterListener(){
		if (sensorManager != null) {
			sensorManager.unregisterListener(sensorEventListener);			
		}
	}
	public void  canShake(boolean canVibrator){
		this.canShake = canVibrator;
	}
	public boolean canShake(){
		return canShake;
	}
	private class MySensorEventListener implements SensorEventListener
	{
		@Override   
		public void onSensorChanged(SensorEvent event) {   
			/*传感器信息改变时执行该方法   */ 
			float[] values = event.values;   
			/*x轴方向的重力加速度，向右为正  */
			float x = values[0];  
			/*y轴方向的重力加速度，向前为正  */
			float y = values[1]; 
			/* z轴方向的重力加速度，向上为正  */
			float z = values[2];  
			/* 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
			 * 如果不敏感请自行调低该数值,低于10的话就不行了,因为z轴上的加速度本身就已经达到10了   
			 * 这里最好是19， 魅族手机摇晃，是不会超过20的（最多19.xxx），会被坑死*/
			LogUtil.w("x = "+x+" y = "+y+" z = "+z);
			builder.append("\nx = "+x+" y = "+y+" z = "+z);
			/*
			 * xAxis = 10;  yAxis = 18
			 * */
			if ((Math.abs(x) > xAxis) || (Math.abs(y) > yAxis) /*|| (Math.abs(z) > zAxis)*/) {   
				LogUtil.e("canVibrator = "+canShake);
				if(canShake){
					vibrator.vibrate(500);   
					Message msg = new Message();   
					msg.what = whatShaking;   
					handler.sendMessage(msg);   					
				}else{
					LogUtil.e("不可以吊起震动");
				}
			}  
		}   
		@Override   
		public void onAccuracyChanged(Sensor sensor, int accuracy) {   

		} 
	}
	@SuppressLint("HandlerLeak")
	private final class SensorHandler extends Handler
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			if((whatShaking == msg.what) && (onShakeListener!=null)){
				onShakeListener.onShaking();
			}
		}
	}
	public void setOnShakeListener(OnShakeListener onShakeListener){
		this.onShakeListener = onShakeListener;
	}
	public interface OnShakeListener{
		public void onShaking();
	}

	
}
