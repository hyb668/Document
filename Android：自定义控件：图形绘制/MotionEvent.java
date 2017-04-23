/**相对于整个手机屏幕，原点在整个手机屏幕左上角*/ 
MotionEvent.getRawX();
/**相对于自身，原点在自身左上角*/ 
MotionEvent.getX();  


/** 单点按下事件 */
MotionEvent.ACTION_DOWN
/** 单点抬起事件 */
MotionEvent.ACTION_UP
/** 多点按下事件 */
MotionEvent.ACTION_POINTER_DOWN




private int firstX;
public boolean onTouchEvent(MotionEvent event)
{
	super.onTouchEvent(event);
	int secondX;
	if(event.getAction() == MotionEvent.ACTION_DOWN){
		firstX = (int) event.getX();//按下的时候开始的x的位置
	}
	else if(event.getAction() == MotionEvent.ACTION_UP){
		secondX = (int)event.getX();//up的时候x的位置
		int distance = secondX - firstX;
		if (Math.abs(distance) <= 5) {
			//单击事件
			triggerClick(event.getX(),event.getY());
		}
	}
	return true;
}