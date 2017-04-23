 	/**<b>测量大小</b>
	 * <pre>
	 * 测量view及其内容来确定view的宽度和高度。
	 * 这个方法在measure(int, int)中被调用，必须被重写来精确和有效的测量view的内容。
	 * 在重写这个方法时，必须调用setMeasuredDimension(int, int)来存储测量得到的宽度和高度值。
	 * 执行失败会触发一个IllegalStateException异常。
	 * 调用父view的onMeasure(int, int)是合法有效的用法。
	 * view的基本测量数据默认取其背景尺寸，除非允许更大的尺寸。
	 * 子view必须重写onMeasure(int, int)来提供其内容更加准确的测量数值。
	 * 如果被重写，子类确保测量的height和width至少是view的最小高度和宽度(通过getSuggestedMinimumHeight()和getSuggestedMinimumWidth()获取)。
	 * </pre>
	 * @param widthMeasureSpec  提供view的水平空间的规格说明
	 * @param heightMeasureSpec 提供view的垂直空间的规格说明
	 * */
	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {	}
在继承ViewGroup的时候，需要复写onMeasure方法，
1. 在 onMeasure(int, int) 方法中，必须调用setMeasuredDimension(int, int)方法，用来存储测量得到的宽度和高度值，否则会触发异常IllegalStateException；
2. 在 onMeasure(int, int) 方法中，必须调用孩子的measure(int, int)方法，测量出view的大小，父view使用width参数和height参数来提供constraint信息；
3. 参数说明： 两个参数，都是由上一层参数传入  widthMeasureSpec提供view的水平空间的规格说明，heightMeasureSpec提供view的垂直空间的规格说明。




View/ViewGroup对象的onMeasure()方法，该方法实现的功能如下：    
1 设置本View视图的最终大小，该功能的实现通过调用setMeasuredDimension()方法去设置实际的高(对应属性：mMeasuredHeight)和宽(对应属性：mMeasureWidth)   ；
2 如果该View对象是个ViewGroup类型，需要重写该onMeasure()方法，对其子视图进行遍历的measure()过程。
2.1 对每个子视图的measure()过程，是通过调用父类ViewGroup.java类里的measureChildWithMargins()方法去实现，
      该方法内部只是简单地调用了View对象的measure()方法。(由于measureChildWithMargins()方法只是一个过渡层更简单的做法是直接调用View对象的measure()方法)。

//回调View视图里的onMeasure过程  
private void onMeasure(int height , int width){  
 //设置该view的实际宽(mMeasuredWidth)高(mMeasuredHeight)  
 //1、该方法必须在onMeasure调用，否者报异常。  
 setMeasuredDimension(h , l) ;  
   
 //2、如果该View是ViewGroup类型，则对它的每个子View进行measure()过程  
 int childCount = getChildCount() ;  
   
 for(int i=0 ;i<childCount ;i++){  
  //2.1、获得每个子View对象引用  
  View child = getChildAt(i) ;  
    
  //整个measure()过程就是个递归过程  
  //该方法只是一个过滤器，最后会调用measure()过程 ;或者 measureChild(child , h, i)方法都  
  measureChildWithMargins(child , h, i) ;   
    
  //其实，对于我们自己写的应用来说，最好的办法是去掉框架里的该方法，直接调用view.measure()，如下：  
  //child.measure(h, l)  
 }  
}  
  
//该方法具体实现在ViewGroup.java里 。  
protected  void measureChildWithMargins(View v, int height , int width){  
 v.measure(h,l)     
}  
