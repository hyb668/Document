package com.alex.mvpapp.helper;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
@SuppressLint("HandlerLeak")
public abstract class CountDownTimer
{
	private static final int MSG = 1;
    private final long mMillisInFuture;
    private final long mCountdownInterval;
    private long mStopTimeInFuture;
    private long mPauseTimeInFuture;
    private boolean isStop = false;
    private boolean isPause = false;

    private String tag;

    /**
     * @param millisInFuture    总倒计时时间
     */
    public CountDownTimer(long millisInFuture) {
        this(millisInFuture,1000L, "");
    }
    
    /**
     * @param millisInFuture    总倒计时时间
     * @param tag
     */
    public CountDownTimer(long millisInFuture, String tag) {
        this(millisInFuture,1000L, tag);
    }
    
    /**
     * @param millisInFuture    总倒计时时间
     * @param countDownInterval 倒计时间隔时间
     */
    public CountDownTimer(long millisInFuture, long countDownInterval) {
        this(millisInFuture,countDownInterval, "");
    }
    /**
     * @param millisInFuture    总倒计时时间
     * @param countDownInterval 倒计时间隔时间
     */
    public CountDownTimer(long millisInFuture, long countDownInterval, String tag) {
        this.tag = tag;
        // 解决秒数有时会一开始就减去了2秒问题（如10秒总数的，刚开始就8999，然后没有不会显示9秒，直接到8秒）
        if (countDownInterval > 1000) millisInFuture += 15;
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
    }

    private synchronized CountDownTimer start(long millisInFuture) {
        isStop = false;
        if (millisInFuture <= 0) {
            onFinish();
            return this;
        }
        mStopTimeInFuture = SystemClock.elapsedRealtime() + millisInFuture;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return this;
    }

    /**
     * 开始倒计时
     */
    public synchronized final void start() {
        start(mMillisInFuture);
    }

    /**
     * 停止倒计时
     */
    public synchronized final void stop() {
        isStop = true;
        mHandler.removeMessages(MSG);
    }

    /**
     * 暂时倒计时
     * 调用{@link #restart()}方法重新开始
     */
    public synchronized final void pause() {
        if (isStop) return ;

        isPause = true;
        mPauseTimeInFuture = mStopTimeInFuture - SystemClock.elapsedRealtime();
        mHandler.removeMessages(MSG);
    }

    /**
     * 重新开始
     */
    public synchronized final void restart() {
        if (isStop || !isPause) return ;

        isPause = false;
        start(mPauseTimeInFuture);
    }

    /**
     * 倒计时间隔回调
     * @param millisUntilFinished 剩余毫秒数
     */
    public void onTick(long millisUntilFinished){

        int day = (int)(millisUntilFinished / (1000 * 60 * 60 * 24));
        int hour = (int)((millisUntilFinished % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        int minute = (int)((millisUntilFinished % (1000 * 60 * 60)) / (1000 * 60));
        int second = (int)((millisUntilFinished % (1000 * 60)) / 1000);
        int millisecond = (int)(millisUntilFinished % 1000);
        onTick(day, hour, minute, second, millisecond);
        onTick(day, hour, minute, second);
        onTick(hour, minute, second);
        onTick(minute, second);
    }
    /**
     * 倒计时间隔回调
     */
    public abstract void onTick(int minute, int second);
    /**
     * 倒计时间隔回调 
     */
    public void onTick(int hour, int minute, int second){
        
    }	
    /**
     * 倒计时间隔回调 
     */
    public void onTick(int day, int hour, int minute, int second){
    	
    }

    /**
     * 倒计时间隔回调 
     */
    public void onTick(int day, int hour, int minute, int second, int millisecond){
    	
    }
    /**
     * 倒计时结束回调
     */
    public abstract void onFinish();


    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            synchronized (CountDownTimer.this) {
                if (isStop || isPause) {
                    return;
                }

                final long millisLeft = mStopTimeInFuture - SystemClock.elapsedRealtime();
                if (millisLeft <= 0) {
                    onFinish();
                } else {
                    long lastTickStart = SystemClock.elapsedRealtime();
                    onTick(millisLeft);

                    // take into account user's onTick taking time to execute
                    long delay = lastTickStart + mCountdownInterval - SystemClock.elapsedRealtime();

                    // special case: user's onTick took more than interval to
                    // complete, skip to next interval
                    while (delay < 0) delay += mCountdownInterval;

                    sendMessageDelayed(obtainMessage(MSG), delay);
                }
            }
        }
    };
	public void destroy()
	{
		stop();
		if(mHandler!=null){
			mHandler.removeCallbacksAndMessages(null);
			mHandler = null;
		}
	}
}
