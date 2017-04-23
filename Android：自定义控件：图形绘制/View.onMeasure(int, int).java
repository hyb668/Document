 	/**<b>������С</b>
	 * <pre>
	 * ����view����������ȷ��view�Ŀ�Ⱥ͸߶ȡ�
	 * ���������measure(int, int)�б����ã����뱻��д����ȷ����Ч�Ĳ���view�����ݡ�
	 * ����д�������ʱ���������setMeasuredDimension(int, int)���洢�����õ��Ŀ�Ⱥ͸߶�ֵ��
	 * ִ��ʧ�ܻᴥ��һ��IllegalStateException�쳣��
	 * ���ø�view��onMeasure(int, int)�ǺϷ���Ч���÷���
	 * view�Ļ�����������Ĭ��ȡ�䱳���ߴ磬�����������ĳߴ硣
	 * ��view������дonMeasure(int, int)���ṩ�����ݸ���׼ȷ�Ĳ�����ֵ��
	 * �������д������ȷ��������height��width������view����С�߶ȺͿ��(ͨ��getSuggestedMinimumHeight()��getSuggestedMinimumWidth()��ȡ)��
	 * </pre>
	 * @param widthMeasureSpec  �ṩview��ˮƽ�ռ�Ĺ��˵��
	 * @param heightMeasureSpec �ṩview�Ĵ�ֱ�ռ�Ĺ��˵��
	 * */
	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {	}
�ڼ̳�ViewGroup��ʱ����Ҫ��дonMeasure������
1. �� onMeasure(int, int) �����У��������setMeasuredDimension(int, int)�����������洢�����õ��Ŀ�Ⱥ͸߶�ֵ������ᴥ���쳣IllegalStateException��
2. �� onMeasure(int, int) �����У�������ú��ӵ�measure(int, int)������������view�Ĵ�С����viewʹ��width������height�������ṩconstraint��Ϣ��
3. ����˵���� ������������������һ���������  widthMeasureSpec�ṩview��ˮƽ�ռ�Ĺ��˵����heightMeasureSpec�ṩview�Ĵ�ֱ�ռ�Ĺ��˵����




View/ViewGroup�����onMeasure()�������÷���ʵ�ֵĹ������£�    
1 ���ñ�View��ͼ�����մ�С���ù��ܵ�ʵ��ͨ������setMeasuredDimension()����ȥ����ʵ�ʵĸ�(��Ӧ���ԣ�mMeasuredHeight)�Ϳ�(��Ӧ���ԣ�mMeasureWidth)   ��
2 �����View�����Ǹ�ViewGroup���ͣ���Ҫ��д��onMeasure()��������������ͼ���б�����measure()���̡�
2.1 ��ÿ������ͼ��measure()���̣���ͨ�����ø���ViewGroup.java�����measureChildWithMargins()����ȥʵ�֣�
      �÷����ڲ�ֻ�Ǽ򵥵ص�����View�����measure()������(����measureChildWithMargins()����ֻ��һ�����ɲ���򵥵�������ֱ�ӵ���View�����measure()����)��

//�ص�View��ͼ���onMeasure����  
private void onMeasure(int height , int width){  
 //���ø�view��ʵ�ʿ�(mMeasuredWidth)��(mMeasuredHeight)  
 //1���÷���������onMeasure���ã����߱��쳣��  
 setMeasuredDimension(h , l) ;  
   
 //2�������View��ViewGroup���ͣ��������ÿ����View����measure()����  
 int childCount = getChildCount() ;  
   
 for(int i=0 ;i<childCount ;i++){  
  //2.1�����ÿ����View��������  
  View child = getChildAt(i) ;  
    
  //����measure()���̾��Ǹ��ݹ����  
  //�÷���ֻ��һ�����������������measure()���� ;���� measureChild(child , h, i)������  
  measureChildWithMargins(child , h, i) ;   
    
  //��ʵ�����������Լ�д��Ӧ����˵����õİ취��ȥ�������ĸ÷�����ֱ�ӵ���view.measure()�����£�  
  //child.measure(h, l)  
 }  
}  
  
//�÷�������ʵ����ViewGroup.java�� ��  
protected  void measureChildWithMargins(View v, int height , int width){  
 v.measure(h,l)     
}  
