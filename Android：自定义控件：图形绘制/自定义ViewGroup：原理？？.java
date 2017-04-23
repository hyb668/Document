android绘制view的过程简单描述 :  整个View树的绘图流程是在ViewRoot.java类的performTraversals()函数展开的，该函数做的执行过程可简单概况为
根据之前设置的状态，依次执行判断是否需要“测量”视图大小(measure)、是否需要重新“布局”视图的位置(layout)、以及是否需要“重绘” (draw)，
自定义ViewGroup需要复写两个方法：
onMeasure：测量 子View 的宽高，设置自己的宽高；
onLayout：设置 子View 的位置

1、 onMeasure：根据 子View 的布局文件，为 子View 设置测量模式、测量值
测量结果 = 测量模式 + 测量值；
测量模式有 3 种：
1) EXACTLY 精确值， 如100dip，match_parent
2) AT_MOST 最大值，wrap_content
3) UNSPECIFIED 不定值，子控件想要多大就多大，如ScrollView

每一个ViewGroup对应一个LayoutParams
子View.getlayoutParams();  -> 父控件的 LayoutParams
View通过LayoutParams类告诉其父视图它想要的大小(即，长度和宽度)。

FlowLayout的LayoutParams只需要使用 子View 之间的间距值，所以
FlowLayout对应的LayoutParams是 MarginLayoutParams

