1... ��Բ
/**Բ��X���꣬Բ��Y���꣬Բ�뾶������*/
Canvas.drawCircle(float cx, float cy, float radius, Paint paint)
2... ����
Canvas.drawRect(0, 0, 40, 100, paint);     //���ƾ���  

3... ����·��
Path path = new Path();
path.moveTo(0, 0); //��ʼ��
path.lineTo(300, 400);  //������ 
path.lineTo(400, 300);  //������ 
Canvas.drawPath(path, paint);
4... ������
paint.setTextAlign(Align.CENTER);		
canvas.drawText("ajkfo ", 300, 300+(a-d)/2, paint);

/*���ø��µĳ�����*/
RectF rectF = new RectF(0, 0, 300, 200);
/*�ڶ���������x�뾶��������������y�뾶*/
canvas.drawRoundRect(rectF, 20, 20, paint);

//��ȡ�������        
canvas.getHeight();    
canvas.getWidth();              
//��ȡCanvas���������ܶ�        
canvas.getDensity();                
//��ȡ����������       
canvas.getDrawFilter();  

//clip��صķ�����Ҫ�ǶԻ���Canvas���вü�	
canvas.clipPath(path_STROKE);	
/*
*���						Region.Op.DIFFERENCE
*ȡ��						Region.Op.REPLACE
*����						Region.Op.UNION
*����						Region.Op.INTERSECT
*����ȥͬXOR		Region.Op.XOR
*Difference���෴���㣬���󻭵Ĳ�����ȥ����ǰ�Ĳ���				Region.Op.REVERSE_DIFFERENCE
*/
canvas.clipPath(path_STROKE, op);	
canvas.clipRect(rect);	
canvas.clipRect(rect, op);	
canvas.clipRect(left, top, right, bottom);	
canvas.clipRect(left, top, right, bottom, op);	
canvas.clipRegion(region);	
canvas.clipRegion(region, op);

//��Canvas���������ɫ		
canvas.drawARGB(a, r, g, b);		
canvas.drawColor(color);		
canvas.drawColor(color, mode);

//ͨ����ͬ������Canvas�ϻ�λͼ		
canvas.drawBitmap(bitmap, left, top, paint_STROKE);		
canvas.drawBitmap(bitmap, matrix, paint_STROKE);		
canvas.drawBitmap(bitmap, src, dst, paint_STROKE);		
canvas.drawBitmap(bitmap, src, dst, paint_STROKE);		
canvas.drawBitmap(colors, offset, stride, x, y, width, height, hasAlpha, paint_STROKE);		
canvas.drawBitmapMesh(bitmap, meshWidth, meshHeight, verts, vertOffset, colors, colorOffset, paint_STROKE);				

//ͨ����ͬ�Ĳ�����Canvas�ϻ�ͼƬ		
canvas.drawPicture(picture);		
canvas.drawPicture(picture, dst);		
canvas.drawPicture(picture, dst);

//��Canvas�ϻ�Բ����Բ
canvas.drawCircle(cx, cy, radius, paint_STROKE);
//����
canvas.drawArc(oval, startAngle, sweepAngle, useCenter, paint_STROKE);
//��Բ
canvas.drawOval(oval, paint_STROKE);

//��Canvas�ϻ���		
canvas.drawLine(startX, startY, stopX, stopY, paint_STROKE);		
canvas.drawLines(pts, paint_STROKE);		
canvas.drawLines(pts, offset, count, paint_STROKE);

//��Canvas�����ϻ���		
canvas.drawPoint(x, y, paint_STROKE);		
canvas.drawPoints(pts, paint_STROKE);		
canvas.drawPoints(pts, offset, count, paint_STROKE);

//��Canvas�����ϻ����λ�Բ�Ǿ���		
canvas.drawRect(r, paint_STROKE);		
canvas.drawRect(rect, paint_STROKE);		
canvas.drawRoundRect(rect, rx, ry, paint_STROKE);

//����Canvas�ڻ�����д�������Api		
canvas.drawPosText(text, pos, paint_STROKE);		
canvas.drawPosText(text, index, count, pos, paint_STROKE);				
canvas.drawText(text, x, y, paint_STROKE);		
canvas.drawText(text, index, count, x, y, paint_STROKE);		
canvas.drawText(text, start, end, x, y, paint_STROKE);		
canvas.drawText(text, start, end, x, y, paint_STROKE);				
canvas.drawTextOnPath(text, path_STROKE, hOffset, vOffset, paint_STROKE);		
canvas.drawTextOnPath(text, index, count, path_STROKE, hOffset, vOffset, paint_STROKE);

//���ƶ���(ָ�����)		
canvas.drawVertices(mode, vertexCount, verts, vertOffset, texs, texOffset, colors, colorOffset, indices, indexOffset, indexCount, paint_STROKE);

//��Canvas����һ��bitmap����		
canvas.setBitmap(bitmap);						
//��ȡ�������		
canvas.getHeight();		
canvas.getWidth();				
//��ȡ������Canvas���������ܶ�		
canvas.getDensity();		
canvas.setDensity(density);				
//��ȡ�����û���������		
canvas.getDrawFilter();		
canvas.setDrawFilter(filter);				
//��ȡCanvas�Ͻ�ȡ�ľ���		
canvas.getClipBounds();		
canvas.getClipBounds(bounds);					
//��ȡCanvas�ľ���		
canvas.getMatrix();		
canvas.getMatrix(ctm);		
canvas.setMatrix(matrix);				
//���ش�λͼ���ƻ�������������߶ȺͿ�ȡ���ͼ��һ��λͼ�������ֵ�߻ᵼ�´���		
canvas.getMaximumBitmapHeight();		
canvas.getMaximumBitmapWidth();				
//���ػ����ϵ�˽�ж�ջ״̬�ľ�����ͼ��		
canvas.getSaveCount();				
//�жϴ˻���ʹ��Ӳ������		
canvas.isHardwareAccelerated();				
//�����ǰ������װ���ǲ�͸���ķ���true		
canvas.isOpaque();				
//��ʱû���ù�����֪����ô��		
canvas.quickReject(path_STROKE, type);		
canvas.quickReject(rect, type);		
canvas.quickReject(left, top, right, bottom, type);

//���ص���save()֮ǰ״̬����������������޸ľ����ü����ϴα���״̬		
canvas.restore();		
canvas.restoreToCount(saveCount);				
//���浱ǰ����״̬��֮��ĵ� translate,scale,rotate,skew,concat or clipRect,clipPath�ȶ����ճ�����		
//������restore()�󣬻�ظ���save֮ǰ��Canvas״̬		
canvas.save();		
canvas.save(saveFlags);				
//��save()���ƣ������ڵĲ����½�һ�㣬����restore��ظ�ԭ״̬		
canvas.saveLayer(bounds, paint_STROKE, saveFlags);		
canvas.saveLayer(left, top, right, bottom, paint_STROKE, saveFlags);		
canvas.saveLayerAlpha(bounds, alpha, saveFlags);		
canvas.saveLayerAlpha(left, top, right, bottom, alpha, saveFlags);

//�û���Ť��		
canvas.skew(sx, sy);				
//�û�����ת		
canvas.rotate(degrees);		
canvas.rotate(degrees, px, py);				
//�û�������		
canvas.scale(sx, sy);		
canvas.scale(sx, sy, px, py);				
//�û�����ת		
canvas.translate(dx, dy);






