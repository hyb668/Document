一、相关概念；
1... Drawable 就是一个可绘制的对象，
可能是一张位图   BitmapDrawable  , 
可能是一个图形  ShapeDrawable , 
可能是一个图层  LayerDrawable , 我们根据画图的需求，创建相应的可画对象
2... Canvas 画布，绘制的目的区域，用于绘图
3... Bitmap 位图，用于图的处理
4... Matrix 矩阵，此例中用于操作图片
二、 步骤
1... 把 Drawable 画到位图对象上
2... 对位图对象做缩放（或旋转等）操作
3... 把位图再转换成 Drawable
