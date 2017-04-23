backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
backgroundPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));