package github.common.util;
import android.os.CountDownTimer;
public class HourTimer
{
	/**需要定时 hour个小时*/
	private int hour;
	/**需要定时 minute个分钟*/
	private int minute;
	/**需要定时 second个秒钟*/
	private int second;
	private TickListener tickListener;
	private HourDownTimer hourDownTimer;
	public HourTimer(int hour, int minute, int second, TickListener tickListener) {
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		this.tickListener = tickListener;
		tickListener.onTick(hour, minute, second);
		long millisInFuture = hour * 1000 * 3600 + minute * 1000 * 60 + second * 1000;
		hourDownTimer = new HourDownTimer(millisInFuture , 1 * 1000);
	}
	public void start(){
		if(hourDownTimer!=null){
			hourDownTimer.start();
		}
	}
	public void cancel(){
		if(hourDownTimer!=null){
			hourDownTimer.cancel();
		}
	}
	public interface TickListener{
		public void onTick(int hour,int minute,int second);
	}
	private final class HourDownTimer extends CountDownTimer 
	{
		/**@param millisInFuture 总计时长
		 * @param countDownInterval 计时，间隔*/
		public HourDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}
		/**剩余时间*/
		@Override
		public void onTick(long millisUntilFinished)
		{
			second = (int) ((millisUntilFinished - hour*1000*3600 - minute*1000*60)/1000);
			if(second == 0)
			{
				if(minute>=0)
				{
					minute--;
					second = 59;
					if(minute < 0 && hour>0){
						hour -- ;
					}
				}
				else if(minute <0)
				{
					if(hour>0){
						hour -- ;
						minute = 59;
						second = 59;
					}
				}
			}
			minute = (int) ((millisUntilFinished - hour*1000*3600 - second*1000)/(1000*60));
			hour = (int) ((millisUntilFinished - minute*1000*60 - second*1000)/(1000*3600));
			tickListener.onTick(hour, minute , second);
		}
		/**计时完成*/
		@Override
		public void onFinish(){
			tickListener.onTick(0, 0, 0);
		}
	}
}
