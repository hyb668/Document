		DisplayMetrics metrics = getResources().getDisplayMetrics();
		chbWidth  = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, chbWidth, metrics);
		chbHeight  = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, chbHeight, metrics);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OptionLayout);
		
		
getDimension()方法，返回类型是float，他是没有做任何处理的数值。
getDimensionPixelOffset()，返回类型int，他会把计算结果直接强转成int型。
getDimensionPixelSize()，返回类型int，他会把计算结果四舍五入。
		
		/*dp*/
		chbHeight = array.getDimensionPixelOffset(R.styleable.OptionLayout_chb_height, chbHeight);
		/*color*/
		backgroundNormal = array.getColor(R.styleable.CheckTextView_backgroundNormal, backgroundNormal);
		/*res drawable*/
		backgroundResNormal = array.getResourceId(R.styleable.CheckTextView_backgroundResNormal, backgroundResNormal);
		/*sp*/
		textSizeNormal = array.getDimension(R.styleable.CheckTextView_textSizeNormal, textSizeNormal);		
		/*回收资源*/
		array.recycle();
		
//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
/*使用sp*/
setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeNormal);