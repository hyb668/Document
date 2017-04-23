void android.view.ViewGroup.onLayout(boolean changed, int l, int t, int r, int b)
调用场景：在view给其孩子设置尺寸和位置时被调用。
子view，包括孩子在内，必须重写onLayout(boolean, int, int, int, int)方法，并且调用各自的layout(int, int, int, int)方法。

参数说明：参数changed表示view有新的尺寸或位置；
参数l表示相对于父view的Left位置；
参数t表示相对于父view的Top位置；
参数r表示相对于父view的Right位置；
参数b表示相对于父view的Bottom位置。

//回调View视图里的onLayout过程 ,该方法只由ViewGroup类型实现  
private void onLayout(int left , int top , right , bottom){  
  
 //如果该View不是ViewGroup类型  
 //调用setFrame()方法设置该控件的在父视图上的坐标轴  
   
 setFrame(l ,t , r ,b) ;  
   
 //--------------------------  
   
 //如果该View是ViewGroup类型，则对它的每个子View进行layout()过程  
 int childCount = getChildCount() ;  
   
 for(int i=0 ;i<childCount ;i++){  
  //2.1、获得每个子View对象引用  
  View child = getChildAt(i) ;  
  //整个layout()过程就是个递归过程  
  child.layout(l, t, r, b) ;  
 }  
}  