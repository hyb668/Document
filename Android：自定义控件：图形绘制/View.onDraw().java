�������� draw()��ͼ����
     ��ViewRoot�����performTraversals()��������draw()����������Ƹ�View����ֵ��ע�����ÿ�η����ͼʱ������
  �����»���ÿ��View������ͼ����ֻ�����»�����Щ����Ҫ�ػ桱����ͼ��View���ڲ�����������һ����־λDRAWN������
��ͼ��Ҫ�ػ�ʱ���ͻ�Ϊ��View��Ӹñ�־λ��
 
   �������� ��
     mView.draw()��ʼ���ƣ�draw()����ʵ�ֵĹ������£�
          1 �����Ƹ�View�ı���
          2 ��Ϊ��ʾ�������һЩ׼������(��5�����������£�����Ҫ�Ľ����)          
          3������onDraw()����������ͼ����   (ÿ��View����Ҫ���ظ÷�����ViewGroup����Ҫʵ�ָ÷���)
          4������dispatchDraw ()������������ͼ(�����View���Ͳ�ΪViewGroup��������������ͼ������Ҫ���ظ÷���)
ֵ��˵�����ǣ�ViewGroup���Ѿ�Ϊ������д��dispatchDraw ()�Ĺ���ʵ�֣�Ӧ�ó���һ�㲻��Ҫ��д�÷��������������ظ���
  ����ʵ�־���Ĺ��ܡ�
 
            4.1 dispatchDraw()�����ڲ������ÿ������ͼ������drawChild()ȥ���»ص�ÿ������ͼ��draw()����(ע�⣬��� 
�ط�����Ҫ�ػ桱����ͼ�Ż����draw()����)��ֵ��˵�����ǣ�ViewGroup���Ѿ�Ϊ������д��dispatchDraw()�Ĺ���
ʵ�֣�Ӧ�ó���һ�㲻��Ҫ��д�÷��������������ظ��ຯ��ʵ�־���Ĺ��ܡ�
    
     5�����ƹ�����
 
  ���ǣ������������������ݹ���ȥ�ˡ�
    
// draw()����     ViewRoot.java  
// ����draw()��"������"��ViewRoot.java���performTraversals()������ �÷������������draw()������ʼ��ͼ  
private void  draw(){  
   
    //...  
 View mView  ;  
    mView.draw(canvas) ;    
      
    //....  
}  
  
//�ص�View��ͼ���onLayout���� ,�÷���ֻ��ViewGroup����ʵ��  
private void draw(Canvas canvas){  
 //�÷���������������  
 //1 �����Ƹ�View�ı���  
 //2��Ϊ���ƽ������һЩ׼������  
 //3������onDraw()����������ͼ����  
 //4������dispatchDraw()��������ÿ������ͼ��dispatchDraw()�Ѿ���Android�����ʵ���ˣ���ViewGroup�����С�  
      // Ӧ�ó������һ�㲻��Ҫ��д�÷����������Բ���÷����ķ�������һЩ�ر�����顣  
 //5�����ƽ����    
}  
  
//ViewGroup.java�е�dispatchDraw()������Ӧ�ó���һ�㲻��Ҫ��д�÷���  
@Override  
protected void dispatchDraw(Canvas canvas) {  
 //   
 //��ʵ�ַ����������£�  
 int childCount = getChildCount() ;  
   
 for(int i=0 ;i<childCount ;i++){  
  View child = getChildAt(i) ;  
  //����drawChild���  
  drawChild(child,canvas) ;  
 }       
}  
//ViewGroup.java�е�dispatchDraw()������Ӧ�ó���һ�㲻��Ҫ��д�÷���  
protected void drawChild(View child,Canvas canvas) {  
 // ....  
 //�򵥵Ļص�View�����draw()�������ݹ����ô�����ˡ�  
 child.draw(canvas) ;  
   
 //.........  
}  