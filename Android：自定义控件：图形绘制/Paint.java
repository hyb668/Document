1.1... 实心+消除锯齿  Style.FILL_AND_STROKE
1.2... 空心  Style.STROKE
2.1... 画笔颜色
2.1... paint.setColor(Color.BLUE);  
2.2... 字体大小
2.2... paint.setTextSize(20);   
2.3... 画笔粗细
2.3... paint.setStrokeWidth(10);
//设置是否使用抗锯齿功能，会消耗较大资源，绘制图形速度会变慢。
paint.setAntiAlias(boolean aa); 


/*充满*/
paint.setStyle(Paint.Style.FILL);


