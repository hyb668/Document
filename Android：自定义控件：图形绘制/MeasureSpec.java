一个MeasureSpec封装了父布局传递给子布局的布局要求，每个MeasureSpec代表了一组宽度和高度的要求。
一个MeasureSpec由大小和模式组成，它有三种模式：
UNSPECIFIED(未指定),父元素不对子元素施加任何束缚，子元素可以得到任意想要的大小；
EXACTLY(完全)，父元素决定自元素的确切大小，子元素将被限定在给定的边界里而忽略它本身大小；
AT_MOST(至多)，子元素至多达到指定大小的值。

	它常用的三个函数： 
	1.static int getMode(int measureSpec):根据提供的测量值(格式)提取模式(上述三个模式之一) 
	2.static int getSize(int measureSpec):根据提供的测量值(格式)提取大小值(这个大小也就是我们通常所说的大小) 
	3.static int makeMeasureSpec(int size,int mode):根据提供的大小值和模式创建一个测量值(格式)