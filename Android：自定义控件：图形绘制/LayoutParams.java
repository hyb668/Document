public class LinearLayout extends ViewGroup {  

    @Override  
    public LayoutParams generateLayoutParams(AttributeSet attrs) {  
        return new LinearLayout.LayoutParams(getContext(), attrs);  
    }  
    @Override  
    protected LayoutParams generateDefaultLayoutParams() {  
        //该LinearLayout是水平方向还是垂直方向  
        if (mOrientation == HORIZONTAL) {   
            return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);  
        } else if (mOrientation == VERTICAL) {  
            return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);  
        }  
        return null;  
    }  
    @Override  
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {  
        return new LayoutParams(p);  
    }  
    /** 
     * Per-child layout information associated with ViewLinearLayout. 
     *  
     * @attr ref android.R.styleable#LinearLayout_Layout_layout_weight 
     * @attr ref android.R.styleable#LinearLayout_Layout_layout_gravity 
     */ //自定义的LayoutParams类  
    public static class LayoutParams extends ViewGroup.MarginLayoutParams {  
        /** 
         * Indicates how much of the extra space in the LinearLayout will be 
         * allocated to the view associated with these LayoutParams. Specify 
         * 0 if the view should not be stretched. Otherwise the extra pixels 
         * will be pro-rated among all views whose weight is greater than 0. 
         */  
        @ViewDebug.ExportedProperty(category = "layout")  
        public float weight;      //  见于属性，android:layout_weight=""  ;  
        /** 
         * Gravity for the view associated with these LayoutParams. 
         * 
         * @see android.view.Gravity 
         */  
        public int gravity = -1;  // 见于属性， android:layout_gravity=""  ;   
        /** 
         * {@inheritDoc} 
         */  
        public LayoutParams(Context c, AttributeSet attrs) {  
            super(c, attrs);  
            TypedArray a =c.obtainStyledAttributes(attrs, com.android.internal.R.styleable.LinearLayout_Layout);  
            weight = a.getFloat(com.android.internal.R.styleable.LinearLayout_Layout_layout_weight, 0);  
            gravity = a.getInt(com.android.internal.R.styleable.LinearLayout_Layout_layout_gravity, -1);  
  
            a.recycle();  
        }  
        /** 
         * {@inheritDoc} 
         */  
        public LayoutParams(int width, int height) {  
            super(width, height);  
            weight = 0;  
        }  
        /** 
         * Creates a new set of layout parameters with the specified width, height 
         * and weight. 
         * 
         * @param width the width, either {@link #MATCH_PARENT}, 
         *        {@link #WRAP_CONTENT} or a fixed size in pixels 
         * @param height the height, either {@link #MATCH_PARENT}, 
         *        {@link #WRAP_CONTENT} or a fixed size in pixels 
         * @param weight the weight 
         */  
        public LayoutParams(int width, int height, float weight) {  
            super(width, height);  
            this.weight = weight;  
        }  
        public LayoutParams(ViewGroup.LayoutParams p) {  
            super(p);  
        }  
        public LayoutParams(MarginLayoutParams source) {  
            super(source);  
        }  
    }  
    ...  
}  