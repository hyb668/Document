/**����������ֻ���Ļ��ԭ���������ֻ���Ļ���Ͻ�*/ 
MotionEvent.getRawX();
/**���������ԭ�����������Ͻ�*/ 
MotionEvent.getX();  


/** ���㰴���¼� */
MotionEvent.ACTION_DOWN
/** ����̧���¼� */
MotionEvent.ACTION_UP
/** ��㰴���¼� */
MotionEvent.ACTION_POINTER_DOWN




private int firstX;
public boolean onTouchEvent(MotionEvent event)
{
	super.onTouchEvent(event);
	int secondX;
	if(event.getAction() == MotionEvent.ACTION_DOWN){
		firstX = (int) event.getX();//���µ�ʱ��ʼ��x��λ��
	}
	else if(event.getAction() == MotionEvent.ACTION_UP){
		secondX = (int)event.getX();//up��ʱ��x��λ��
		int distance = secondX - firstX;
		if (Math.abs(distance) <= 5) {
			//�����¼�
			triggerClick(event.getX(),event.getY());
		}
	}
	return true;
}