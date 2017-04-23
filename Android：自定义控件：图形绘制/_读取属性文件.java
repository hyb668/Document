package github.alex.iconinputlayout;

/**
 * 作者：alex
 * 时间：2016/8/1 10:50
 * 博客地址：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class IconInputLayout extends RelativeLayout {

    private void initView(AttributeSet attrs) {
        MyOnClickListener onClickListener = new MyOnClickListener();
        MyTextWatcher textWatcher = new MyTextWatcher();
        Context context = getContext();
        pwdVisibility = false;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IconInputLayout);
        rightLeftPadding = typedArray.getDimensionPixelSize(R.styleable.IconInputLayout_right_leftPadding, 0);
        rightRightPadding = typedArray.getDimensionPixelSize(R.styleable.IconInputLayout_right_rightPadding, 0);
        leftLeftPadding = typedArray.getDimensionPixelSize(R.styleable.IconInputLayout_left_leftPadding, 0);
        leftRightPadding = typedArray.getDimensionPixelSize(R.styleable.IconInputLayout_left_rightPadding, 0);
        rightFunction = typedArray.getInteger(R.styleable.IconInputLayout_right_function, 0);
        int leftResId = typedArray.getResourceId(R.styleable.IconInputLayout_leftDrawableResId, -1);
        rightResId = typedArray.getResourceId(R.styleable.IconInputLayout_rightDrawableResId, -1);
        rightResId2 = typedArray.getResourceId(R.styleable.IconInputLayout_rightDrawableResId2, -1);
        String leftText = typedArray.getString(R.styleable.IconInputLayout_leftText);
        String hint = typedArray.getString(R.styleable.IconInputLayout_hint);
        String text = typedArray.getString(R.styleable.IconInputLayout_text);
        String digits = typedArray.getString(R.styleable.IconInputLayout_digits);
        float textSizeHint = typedArray.getDimension(R.styleable.IconInputLayout_textSizeHint, 0F);
        int textColorHint = typedArray.getColor(R.styleable.IconInputLayout_textColorHint, Color.parseColor("#555555"));
        int leftTextColor = typedArray.getColor(R.styleable.IconInputLayout_leftTextColor, Color.parseColor("#000000"));
        float leftTextSize = typedArray.getDimension(R.styleable.IconInputLayout_leftTextSize, 0F);
        int textColor = typedArray.getColor(R.styleable.IconInputLayout_textColor, Color.parseColor("#000000"));
        int textCursorDrawableResId = typedArray.getResourceId(R.styleable.IconInputLayout_textCursorDrawable, -1);
        float textSize = typedArray.getDimension(R.styleable.IconInputLayout_textSize, 0F);
        inputType = typedArray.getInt(R.styleable.IconInputLayout_inputType, EditorInfo.TYPE_NULL);
        int maxLength = typedArray.getInt(R.styleable.IconInputLayout_maxLength, -1);
        textSize = (textSize <= 0) ? sp2px(14) : textSize;
        leftTextSize = (leftTextSize <= 0) ? sp2px(14) : leftTextSize;
        textSizeHint = (textSizeHint <= 0) ? sp2px(14) : textSizeHint;
        
        /*回收资源*/
        typedArray.recycle();
    }

    
    /**
     * 数据转换: dp---->px
     */
    private float dp2px(float dp) {
        return dp * getContext().getResources().getDisplayMetrics().density;
    }

    /**
     * sp转px
     */
    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getContext().getResources().getDisplayMetrics());
    }
}
