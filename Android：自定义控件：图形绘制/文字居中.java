	//画文字：开始＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
	paint.setTextAlign(Align.CENTER);
	paint.setTextSize(textSize);
	paint.setColor(textColor);
	FontMetricsInt fontMetrics = paint.getFontMetricsInt();  
	int baseline = (int) (rect.top + (rect.bottom - rect.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top);  
	paint.setTextAlign(Paint.Align.CENTER);  
	canvas.drawText("你好", rect.centerX(), baseline, paint);
	//画文字：结束＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝