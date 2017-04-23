Path.Direction.CCW：是counter-clockwise缩写，指创建逆时针方向的矩形路径；
Path.Direction.CW：是clockwise的缩写，指创建顺时针方向的矩形路径；

1. 画扇形（弧线）
addArc(RectF oval, float startAngle, float sweepAngle)
第二个参数为0时的位置是矩形右边1/2高度的点，90为矩形底部1/2宽的位置，如此如此....正数为顺时针旋转，负数是逆时针旋转。
第三个参数是图形绘制角度，上图第三个参数为180，如果是-180，那么图形倒过来。

2. 画圆
addCircle(float x, float y, float radius, Path.Direction dir)
第一、二参数是圆心坐标，第三参数是半径，第四参数是顺时针画还是逆时针画（啥玩意？）。

3. 画椭圆
addOval(RectF oval, Path.Direction dir)

4. addPath(Path src, float dx, float dy)


复制一份Path，包含被复制的src的一切，并向X与Y轴方向移动第二、三参数的距离。

5. addRect(RectF rect, Path.Direction dir)

6. 画个矩形
addRect(float left, float top, float right, float bottom, Path.Direction dir)
四个参数对应与原点的相对距离的是个点。

7. 画圆角矩形
addRoundRect(RectF rect, float rx, float ry, Path.Direction dir)
第二、三个参数为0时就是个矩形，为360时，就是个椭圆。
第二个参数指X轴方向的角度，决定了与参考矩形的横线交点位置，0-360决定交点范围为 角点与线中点之间的某点。

8. arcTo(RectF oval, float startAngle, float sweepAngle)
等同于arcTo(RectF oval, float startAngle, float sweepAngle, boolean false)。测试发现：从之前的最后一点开始画线到画椭圆的开始点，接着画个椭圆。

9. arcTo(RectF oval, float startAngle, float sweepAngle, boolean forceMoveTo)
如果最后一个参数为true，那么等同于addArc(RectF oval, float startAngle, float sweepAngle)。

10. cubicTo(float x1, float y1, float x2, float y2, float x3, float y3)
画贝塞尔曲线。前四个参数是两个控制点，最后俩个参数是终止点。
起始点通过moveTo(float x, float y)或者setLastPoint(float dx, float dy)方法设置。
关于贝塞尔曲线，可以去网上找找资料。某人的博客，关于此曲线。

11.moveTo(float x, float y)
设置下一个图形的开始点。

12.setLastPoint(float dx, float dy)
设置图形的最后一个点位置。如果画的是个封闭图形，而这个点不在图形线上，
那么这个点与最后一个图形连上线完成封闭。如图，本来画了个圆角矩形，最后setLastPoint了一下。

13.close()
关闭当前图形，如果最后一点不是开始的那点，那么从最后一点画线到开始点。简而言之，画三角型只需要画俩条线，再调此方法能三角型就完成了。

14.lineTo(float x,float y)
 画一条从最后一点到当前点的线。

15.rLineTo(float dx,float dy)
 画一条从线，以最后一点为参照点参数为偏移量。

