// ��ȡ�ڵ�ǰ�����ڵľ�������
View.getLocationInWindow()

// ��ȡ��������Ļ�ڵľ������꣬ע�����ֵ��Ҫ����Ļ��������Ҳ���ǰ�����֪ͨ���ĸ߶ȡ�
View.getLocationOnScreen()

// ����һ���ǻ�ȡ�������������������ꡣ
View.getLeft();		//View�������ڸ������X����ֵ
View.getTop();		//View�Ϻ����ڸ������Y����ֵ
View.getRight();	//View�������ڸ������X����ֵ
View.getBottom();//View�º����ڸ������Y����ֵ
View.getWidth(); //View�Ŀ��
View.getHeight();//View�ĳ���
		
View.getLocationInWindow()�� View.getLocationOnScreen()��windowռ��ȫ��screenʱ��
����ֵ��ͬ����ͬ�ĵ����������Dialog��ʱ��
��Dialog��������Ļ�м�ʱ��
View.getLocationOnScreen()ȡ�õ�ֵҪ��View.getLocationInWindow()ȡ�õ�ֵҪ��

MotionEvent.ACTION_MASK = 0xff = 1111,1111
MotionEvent.ACTION_DOWN = 0x00 = 0000,0000
MotionEvent.ACTION_POINTER_DOWN = 0x05 = 0000,0101

View.setX() ֱ����������λ��

/*ˮƽ����ƫת��  android:transformPivotX */
setPivotX(float) 
/*ˮƽ������ƶ�����  android:translationX */
setTranslationX(float)  



