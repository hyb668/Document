void android.view.ViewGroup.onLayout(boolean changed, int l, int t, int r, int b)
���ó�������view���亢�����óߴ��λ��ʱ�����á�
��view�������������ڣ�������дonLayout(boolean, int, int, int, int)���������ҵ��ø��Ե�layout(int, int, int, int)������

����˵��������changed��ʾview���µĳߴ��λ�ã�
����l��ʾ����ڸ�view��Leftλ�ã�
����t��ʾ����ڸ�view��Topλ�ã�
����r��ʾ����ڸ�view��Rightλ�ã�
����b��ʾ����ڸ�view��Bottomλ�á�

//�ص�View��ͼ���onLayout���� ,�÷���ֻ��ViewGroup����ʵ��  
private void onLayout(int left , int top , right , bottom){  
  
 //�����View����ViewGroup����  
 //����setFrame()�������øÿؼ����ڸ���ͼ�ϵ�������  
   
 setFrame(l ,t , r ,b) ;  
   
 //--------------------------  
   
 //�����View��ViewGroup���ͣ��������ÿ����View����layout()����  
 int childCount = getChildCount() ;  
   
 for(int i=0 ;i<childCount ;i++){  
  //2.1�����ÿ����View��������  
  View child = getChildAt(i) ;  
  //����layout()���̾��Ǹ��ݹ����  
  child.layout(l, t, r, b) ;  
 }  
}  