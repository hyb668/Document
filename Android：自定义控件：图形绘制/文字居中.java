	//�����֣���ʼ������������������������������������������������������������������
	paint.setTextAlign(Align.CENTER);
	paint.setTextSize(textSize);
	paint.setColor(textColor);
	FontMetricsInt fontMetrics = paint.getFontMetricsInt();  
	int baseline = (int) (rect.top + (rect.bottom - rect.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top);  
	paint.setTextAlign(Paint.Align.CENTER);  
	canvas.drawText("���", rect.centerX(), baseline, paint);
	//�����֣�����������������������������������������������������������������������