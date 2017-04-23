流程三、 draw()绘图过程
     由ViewRoot对象的performTraversals()方法调用draw()方法发起绘制该View树，值得注意的是每次发起绘图时，并不
  会重新绘制每个View树的视图，而只会重新绘制那些“需要重绘”的视图，View类内部变量包含了一个标志位DRAWN，当该
视图需要重绘时，就会为该View添加该标志位。
 
   调用流程 ：
     mView.draw()开始绘制，draw()方法实现的功能如下：
          1 、绘制该View的背景
          2 、为显示渐变框做一些准备操作(见5，大多数情况下，不需要改渐变框)          
          3、调用onDraw()方法绘制视图本身   (每个View都需要重载该方法，ViewGroup不需要实现该方法)
          4、调用dispatchDraw ()方法绘制子视图(如果该View类型不为ViewGroup，即不包含子视图，不需要重载该方法)
值得说明的是，ViewGroup类已经为我们重写了dispatchDraw ()的功能实现，应用程序一般不需要重写该方法，但可以重载父类
  函数实现具体的功能。
 
            4.1 dispatchDraw()方法内部会遍历每个子视图，调用drawChild()去重新回调每个子视图的draw()方法(注意，这个 
地方“需要重绘”的视图才会调用draw()方法)。值得说明的是，ViewGroup类已经为我们重写了dispatchDraw()的功能
实现，应用程序一般不需要重写该方法，但可以重载父类函数实现具体的功能。
    
     5、绘制滚动条
 
  于是，整个调用链就这样递归下去了。
    
// draw()过程     ViewRoot.java  
// 发起draw()的"发号者"在ViewRoot.java里的performTraversals()方法， 该方法会继续调用draw()方法开始绘图  
private void  draw(){  
   
    //...  
 View mView  ;  
    mView.draw(canvas) ;    
      
    //....  
}  
  
//回调View视图里的onLayout过程 ,该方法只由ViewGroup类型实现  
private void draw(Canvas canvas){  
 //该方法会做如下事情  
 //1 、绘制该View的背景  
 //2、为绘制渐变框做一些准备操作  
 //3、调用onDraw()方法绘制视图本身  
 //4、调用dispatchDraw()方法绘制每个子视图，dispatchDraw()已经在Android框架中实现了，在ViewGroup方法中。  
      // 应用程序程序一般不需要重写该方法，但可以捕获该方法的发生，做一些特别的事情。  
 //5、绘制渐变框    
}  
  
//ViewGroup.java中的dispatchDraw()方法，应用程序一般不需要重写该方法  
@Override  
protected void dispatchDraw(Canvas canvas) {  
 //   
 //其实现方法类似如下：  
 int childCount = getChildCount() ;  
   
 for(int i=0 ;i<childCount ;i++){  
  View child = getChildAt(i) ;  
  //调用drawChild完成  
  drawChild(child,canvas) ;  
 }       
}  
//ViewGroup.java中的dispatchDraw()方法，应用程序一般不需要重写该方法  
protected void drawChild(View child,Canvas canvas) {  
 // ....  
 //简单的回调View对象的draw()方法，递归就这么产生了。  
 child.draw(canvas) ;  
   
 //.........  
}  