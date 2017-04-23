// 获取在当前窗口内的绝对坐标
View.getLocationInWindow()

// 获取在整个屏幕内的绝对坐标，注意这个值是要从屏幕顶端算起，也就是包括了通知栏的高度。
View.getLocationOnScreen()

// 下面一组是获取相对在它父窗口里的坐标。
View.getLeft();		//View左竖线在父窗体的X坐标值
View.getTop();		//View上横线在父窗体的Y坐标值
View.getRight();	//View右竖线在父窗体的X坐标值
View.getBottom();//View下横线在父窗体的Y坐标值
View.getWidth(); //View的宽度
View.getHeight();//View的长度
		
View.getLocationInWindow()和 View.getLocationOnScreen()在window占据全部screen时，
返回值相同，不同的典型情况是在Dialog中时。
当Dialog出现在屏幕中间时，
View.getLocationOnScreen()取得的值要比View.getLocationInWindow()取得的值要大。

MotionEvent.ACTION_MASK = 0xff = 1111,1111
MotionEvent.ACTION_DOWN = 0x00 = 0000,0000
MotionEvent.ACTION_POINTER_DOWN = 0x05 = 0000,0101

View.setX() 直接设置坐标位置

/*水平方向偏转量  android:transformPivotX */
setPivotX(float) 
/*水平方向的移动距离  android:translationX */
setTranslationX(float)  



