1... 画圆
/**圆心X坐标，圆心Y坐标，圆半径，画笔*/
Canvas.drawCircle(float cx, float cy, float radius, Paint paint)
2... 画方
Canvas.drawRect(0, 0, 40, 100, paint);     //绘制矩形  

3... 绘制路线
Path path = new Path();
path.moveTo(0, 0); //开始点
path.lineTo(300, 400);  //经过点 
path.lineTo(400, 300);  //经过点 
Canvas.drawPath(path, paint);
4... 画文字
paint.setTextAlign(Align.CENTER);		
canvas.drawText("ajkfo ", 300, 300+(a-d)/2, paint);

/*设置个新的长方形*/
RectF rectF = new RectF(0, 0, 300, 200);
/*第二个参数是x半径，第三个参数是y半径*/
canvas.drawRoundRect(rectF, 20, 20, paint);

//获取画布宽高        
canvas.getHeight();    
canvas.getWidth();              
//获取Canvas画布像素密度        
canvas.getDensity();                
//获取画布过滤器       
canvas.getDrawFilter();  

//clip相关的方法主要是对画布Canvas进行裁剪	
canvas.clipPath(path_STROKE);	
/*
*相减						Region.Op.DIFFERENCE
*取代						Region.Op.REPLACE
*并集						Region.Op.UNION
*交集						Region.Op.INTERSECT
*存异去同XOR		Region.Op.XOR
*Difference的相反运算，将后画的部分中去掉先前的部分				Region.Op.REVERSE_DIFFERENCE
*/
canvas.clipPath(path_STROKE, op);	
canvas.clipRect(rect);	
canvas.clipRect(rect, op);	
canvas.clipRect(left, top, right, bottom);	
canvas.clipRect(left, top, right, bottom, op);	
canvas.clipRegion(region);	
canvas.clipRegion(region, op);

//给Canvas画布填充颜色		
canvas.drawARGB(a, r, g, b);		
canvas.drawColor(color);		
canvas.drawColor(color, mode);

//通过不同参数在Canvas上画位图		
canvas.drawBitmap(bitmap, left, top, paint_STROKE);		
canvas.drawBitmap(bitmap, matrix, paint_STROKE);		
canvas.drawBitmap(bitmap, src, dst, paint_STROKE);		
canvas.drawBitmap(bitmap, src, dst, paint_STROKE);		
canvas.drawBitmap(colors, offset, stride, x, y, width, height, hasAlpha, paint_STROKE);		
canvas.drawBitmapMesh(bitmap, meshWidth, meshHeight, verts, vertOffset, colors, colorOffset, paint_STROKE);				

//通过不同的参数在Canvas上画图片		
canvas.drawPicture(picture);		
canvas.drawPicture(picture, dst);		
canvas.drawPicture(picture, dst);

//在Canvas上画圆和椭圆
canvas.drawCircle(cx, cy, radius, paint_STROKE);
//扇形
canvas.drawArc(oval, startAngle, sweepAngle, useCenter, paint_STROKE);
//椭圆
canvas.drawOval(oval, paint_STROKE);

//在Canvas上画线		
canvas.drawLine(startX, startY, stopX, stopY, paint_STROKE);		
canvas.drawLines(pts, paint_STROKE);		
canvas.drawLines(pts, offset, count, paint_STROKE);

//在Canvas画布上画点		
canvas.drawPoint(x, y, paint_STROKE);		
canvas.drawPoints(pts, paint_STROKE);		
canvas.drawPoints(pts, offset, count, paint_STROKE);

//在Canvas画布上画矩形或圆角矩形		
canvas.drawRect(r, paint_STROKE);		
canvas.drawRect(rect, paint_STROKE);		
canvas.drawRoundRect(rect, rx, ry, paint_STROKE);

//利用Canvas在画布上写文字相关Api		
canvas.drawPosText(text, pos, paint_STROKE);		
canvas.drawPosText(text, index, count, pos, paint_STROKE);				
canvas.drawText(text, x, y, paint_STROKE);		
canvas.drawText(text, index, count, x, y, paint_STROKE);		
canvas.drawText(text, start, end, x, y, paint_STROKE);		
canvas.drawText(text, start, end, x, y, paint_STROKE);				
canvas.drawTextOnPath(text, path_STROKE, hOffset, vOffset, paint_STROKE);		
canvas.drawTextOnPath(text, index, count, path_STROKE, hOffset, vOffset, paint_STROKE);

//绘制顶点(指多边形)		
canvas.drawVertices(mode, vertexCount, verts, vertOffset, texs, texOffset, colors, colorOffset, indices, indexOffset, indexCount, paint_STROKE);

//给Canvas设置一个bitmap画布		
canvas.setBitmap(bitmap);						
//获取画布宽高		
canvas.getHeight();		
canvas.getWidth();				
//获取和设置Canvas画布像素密度		
canvas.getDensity();		
canvas.setDensity(density);				
//获取和设置画布过滤器		
canvas.getDrawFilter();		
canvas.setDrawFilter(filter);				
//获取Canvas上截取的矩形		
canvas.getClipBounds();		
canvas.getClipBounds(bounds);					
//获取Canvas的矩阵		
canvas.getMatrix();		
canvas.getMatrix(ctm);		
canvas.setMatrix(matrix);				
//返回此位图绘制画布上允许的最大高度和宽度。试图用一个位图画比这个值高会导致错误。		
canvas.getMaximumBitmapHeight();		
canvas.getMaximumBitmapWidth();				
//返回画布上的私有堆栈状态的矩阵或截图。		
canvas.getSaveCount();				
//判断此画布使用硬件加速		
canvas.isHardwareAccelerated();				
//如果当前层引入装置是不透明的返回true		
canvas.isOpaque();				
//暂时没有用过，不知道怎么用		
canvas.quickReject(path_STROKE, type);		
canvas.quickReject(rect, type);		
canvas.quickReject(left, top, right, bottom, type);

//返回调用save()之前状态，是用来清除所有修改矩阵或裁剪自上次保存状态		
canvas.restore();		
canvas.restoreToCount(saveCount);				
//保存当前画布状态，之后的的 translate,scale,rotate,skew,concat or clipRect,clipPath等都会照常运行		
//但调用restore()后，会回复到save之前的Canvas状态		
canvas.save();		
canvas.save(saveFlags);				
//和save()类似，在现在的层上新建一层，调用restore后回复原状态		
canvas.saveLayer(bounds, paint_STROKE, saveFlags);		
canvas.saveLayer(left, top, right, bottom, paint_STROKE, saveFlags);		
canvas.saveLayerAlpha(bounds, alpha, saveFlags);		
canvas.saveLayerAlpha(left, top, right, bottom, alpha, saveFlags);

//让画布扭曲		
canvas.skew(sx, sy);				
//让画布旋转		
canvas.rotate(degrees);		
canvas.rotate(degrees, px, py);				
//让画布缩放		
canvas.scale(sx, sy);		
canvas.scale(sx, sy, px, py);				
//让画布翻转		
canvas.translate(dx, dy);






