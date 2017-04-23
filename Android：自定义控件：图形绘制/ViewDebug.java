// 4.4.2
package android.widget;

@RemoteView
public class LinearLayout extends ViewGroup {
    // ���Բ��ֵķ�����
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    /**
     * Don't show any dividers.
     */
    public static final int SHOW_DIVIDER_NONE = 0;
    /**
     * Show a divider at the beginning of the group.
     */
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    /**
     * Show dividers between each item in the group.
     */
    public static final int SHOW_DIVIDER_MIDDLE = 2;
    /**
     * Show a divider at the end of the group.
     */
    public static final int SHOW_DIVIDER_END = 4;

    // ����Linear�Ƿ��ǻ��߶����
    @ViewDebug.ExportedProperty(category = "layout")
    private boolean mBaselineAligned = true;

    // �����ǰlayout����һ�����߶���layout��һ���֣������ָ����ǰlayout��һ����Ԫ��
    // ��Ϊ��ǰlayout�Ļ���
    @ViewDebug.ExportedProperty(category = "layout")
    private int mBaselineAlignedChildIndex = -1;

    // ���߶���Ŀؼ����붥���ľ��루û�е�ǰ�ؼ���margin��
    @ViewDebug.ExportedProperty(category = "measurement")
    private int mBaselineChildTop = 0;

    // ���Բ��ֵķ���
    @ViewDebug.ExportedProperty(category = "measurement")
    private int mOrientation;

    @ViewDebug.ExportedProperty(category = "measurement", flagMapping = {
            @ViewDebug.FlagToString(mask = -1,
                equals = -1, name = "NONE"),
            @ViewDebug.FlagToString(mask = Gravity.NO_GRAVITY,
                equals = Gravity.NO_GRAVITY,name = "NONE"),
            @ViewDebug.FlagToString(mask = Gravity.TOP,
                equals = Gravity.TOP, name = "TOP"),
            @ViewDebug.FlagToString(mask = Gravity.BOTTOM,
                equals = Gravity.BOTTOM, name = "BOTTOM"),
            @ViewDebug.FlagToString(mask = Gravity.LEFT,
                equals = Gravity.LEFT, name = "LEFT"),
            @ViewDebug.FlagToString(mask = Gravity.RIGHT,
                equals = Gravity.RIGHT, name = "RIGHT"),
            @ViewDebug.FlagToString(mask = Gravity.START,
                equals = Gravity.START, name = "START"),
            @ViewDebug.FlagToString(mask = Gravity.END,
                equals = Gravity.END, name = "END"),
            @ViewDebug.FlagToString(mask = Gravity.CENTER_VERTICAL,
                equals = Gravity.CENTER_VERTICAL, name = "CENTER_VERTICAL"),
            @ViewDebug.FlagToString(mask = Gravity.FILL_VERTICAL,
                equals = Gravity.FILL_VERTICAL, name = "FILL_VERTICAL"),
            @ViewDebug.FlagToString(mask = Gravity.CENTER_HORIZONTAL,
                equals = Gravity.CENTER_HORIZONTAL, name = "CENTER_HORIZONTAL"),
            @ViewDebug.FlagToString(mask = Gravity.FILL_HORIZONTAL,
                equals = Gravity.FILL_HORIZONTAL, name = "FILL_HORIZONTAL"),
            @ViewDebug.FlagToString(mask = Gravity.CENTER,
                equals = Gravity.CENTER, name = "CENTER"),
            @ViewDebug.FlagToString(mask = Gravity.FILL,
                equals = Gravity.FILL, name = "FILL"),
            @ViewDebug.FlagToString(mask = Gravity.RELATIVE_LAYOUT_DIRECTION,
                equals = Gravity.RELATIVE_LAYOUT_DIRECTION, name = "RELATIVE")
        })
    // �ӿؼ�Ԫ�ص�����
    private int mGravity = Gravity.START | Gravity.TOP;

    @ViewDebug.ExportedProperty(category = "measurement")
    private int mTotalLength;

    // �ܵ�Ȩ��
    @ViewDebug.ExportedProperty(category = "layout")
    private float mWeightSum;

    @ViewDebug.ExportedProperty(category = "layout")
    private boolean mUseLargestChild;

    // ���ڼ���baseline��
    // �����СΪVERTICAL_GRAVITY_COUNT 0��INDEX_CENTER_VERTICAL ...
    private int[] mMaxAscent;// baseline�ӿؼ��������Ƕ���
    private int[] mMaxDescent;// baseline�ӿؼ��ײ����Ƕ���

    private static final int VERTICAL_GRAVITY_COUNT = 4;
    private static final int INDEX_CENTER_VERTICAL = 0;
    private static final int INDEX_TOP = 1;
    private static final int INDEX_BOTTOM = 2;
    private static final int INDEX_FILL = 3;

    private Drawable mDivider;
    private int mDividerWidth;
    private int mDividerHeight;
    private int mShowDividers;
    private int mDividerPadding;

    // ���췽��
    public LinearLayout(Context context) {
        super(context);
    }

    // ���췽��
    public LinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    // ���췽��
    public LinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs,
                com.android.internal.R.styleable.LinearLayout, defStyle, 0);

        int index = a.getInt(com.android.internal.R.styleable.LinearLayout_orientation, -1);
        // 1.�������Բ��ֵķ���
        if (index >= 0) {
            setOrientation(index);
        }
        // 2.�������Բ��ֵ�gravity
        index = a.getInt(com.android.internal.R.styleable.LinearLayout_gravity, -1);
        if (index >= 0) {
            setGravity(index);
        }
        // 3.�Ƿ���߶���
        boolean baselineAligned = a.getBoolean(R.styleable.LinearLayout_baselineAligned, true);
        if (!baselineAligned) {
            setBaselineAligned(baselineAligned);
        }
        // 4.Ȩ�صĺ�ֵ
        mWeightSum = a.getFloat(R.styleable.LinearLayout_weightSum, -1.0f);
        // 5.��Ϊ�������ֵ�һ����ʱ�����ڲ��ĸ��ؼ���Ϊ����
        mBaselineAlignedChildIndex =
                a.getInt(com.android.internal.R.styleable.LinearLayout_baselineAlignedChildIndex, -1);
        // 6.�Ƿ��������Ԫ�صĳߴ���Ϊ��׼�ٴβ���
        mUseLargestChild = a.getBoolean(R.styleable.LinearLayout_measureWithLargestChild, false);
        // 7.Ԫ�ؼ�ķָ�
        setDividerDrawable(a.getDrawable(R.styleable.LinearLayout_divider));
        mShowDividers = a.getInt(R.styleable.LinearLayout_showDividers, SHOW_DIVIDER_NONE);
        mDividerPadding = a.getDimensionPixelSize(R.styleable.LinearLayout_dividerPadding, 0);

        a.recycle();
    }
��������

    // ����
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mOrientation == VERTICAL) {
            // ��ֱ����Ĳ���
            measureVertical(widthMeasureSpec, heightMeasureSpec);
        } else {
            // ˮƽ����Ĳ���
            measureHorizontal(widthMeasureSpec, heightMeasureSpec);
        }
    }
    // ��ֱ����
    void measureVertical(int widthMeasureSpec, int heightMeasureSpec) {
        //��view�ĸ߶Ⱥ�
        mTotalLength = 0;
        int maxWidth = 0;
        int childState = 0;
        // ��Ȩ�ؿؼ������
        int alternativeMaxWidth = 0;
        // Ȩ�ؿؼ��������
        int weightedMaxWidth = 0;
        boolean allFillParent = true;
        // Ȩ�غ�ֵ
        float totalWeight = 0;
        // �ӿؼ�������
        final int count = getVirtualChildCount();

        // ���ģʽ���߶�ģʽ
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        boolean matchWidth = false;

        final int baselineChildIndex = mBaselineAlignedChildIndex;        
        final boolean useLargestChild = mUseLargestChild;

        int largestChildHeight = Integer.MIN_VALUE;
//------------------------------------------------------------------------------------------------------------
        // See how tall everyone is. Also remember max width.
        // ���������ӿؼ�������ܵĸ߶ȣ������ mBaselineChildTop����Ϣ
        for (int i = 0; i < count; ++i) {
            // 1.��ȡ��Ӧ���ӿؼ�
            final View child = getVirtualChildAt(i);
            // 2.�ӿؼ�Ϊ�գ���������
            if (child == null) {
                mTotalLength += measureNullChild(i);
                continue;
            }
            // 3.�ӿؼ�GONE���ˣ���������
            if (child.getVisibility() == View.GONE) {
               i += getChildrenSkipCount(child, i);
               continue;
            }
            // 4.��¼divider�ĸ߶�
            if (hasDividerBeforeChildAt(i)) {
                mTotalLength += mDividerHeight;
            }
            // 5.�ۼ�Ȩ��
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();
            totalWeight += lp.weight;

            // 6.����
            if (heightMode == MeasureSpec.EXACTLY && lp.height == 0 && lp.weight > 0) {
                // �����Ǿ�ȷģʽ������£��ӿؼ�layout_height=0dp weight����0
                // ��ǰ�޷������ӿؼ��ĸ߶ȣ����ǿ����Ȱ�marginֵ���뵽��ֵ��
                // �������ʣ��ؼ���Ȩֵ�����¾����Ӧ�ĸ߶�
                final int totalLength = mTotalLength;
                mTotalLength = Math.max(totalLength, totalLength + lp.topMargin + lp.bottomMargin);
            } else {
                int oldHeight = Integer.MIN_VALUE;

                if (lp.height == 0 && lp.weight > 0) {
                    // �����������������ʹ��� heightMode���Ǿ�ȷ�����ļ� wrap_conentģʽ
                    // Ҳ����˵��������ԽСԽ�ã��㻹������Ȩֵ���ʣ��ؼ��ǲ����ܵģ�
                    // ֻ��������wrap_contentģʽ
                    oldHeight = 0;
                    lp.height = LayoutParams.WRAP_CONTENT;
                }

                // �ӿؼ�����
                measureChildBeforeLayout(
                       child, i, widthMeasureSpec, 0, heightMeasureSpec,
                       totalWeight == 0 ? mTotalLength : 0);

                // ��һ���е���˼
                // ������˼��˵����������wrap_content���ӿؼ�����height==0 Ȩ�ش���0�����
                // ��Ȼ��wrap_content�����ӿؼ��ˣ�����Ҫ������ǣ�����
                // ͨ��height==0�Ϳ��Ա�ʶ����������������if��֧�ı��һ�£���������������������������
                if (oldHeight != Integer.MIN_VALUE) {
                   lp.height = oldHeight;
                }

                // �ۻ������ܵĸ߶�
                final int childHeight = child.getMeasuredHeight();
                final int totalLength = mTotalLength;
                mTotalLength = Math.max(totalLength, totalLength + childHeight + lp.topMargin +
                       lp.bottomMargin + getNextLocationOffset(child));

                // �������߶�ֵ
                if (useLargestChild) {
                    largestChildHeight = Math.max(childHeight, largestChildHeight);
                }
            }

            // 7.���mBaselineChildTop
            if ((baselineChildIndex >= 0) && (baselineChildIndex == i + 1)) {
               mBaselineChildTop = mTotalLength;
            }

            // 8.Ҳ����˵��baselineChildIndex���治�ܳ���Ȩֵ���ӿؼ�
            if (i < baselineChildIndex && lp.weight > 0) {
                throw new RuntimeException("A child of LinearLayout with index "
                        + "less than mBaselineAlignedChildIndex has weight > 0, which "
                        + "won't work.  Either remove the weight, or don't set "
                        + "mBaselineAlignedChildIndex.");
            }

            // 9.��ȷ��棬����Ҫ��ԽСԽ�ã�wrap_conent���ӿؼ�Ҫ��fillParent
            boolean matchWidthLocally = false;
            if (widthMode != MeasureSpec.EXACTLY && lp.width == LayoutParams.MATCH_PARENT) {
                // ��¼�������
                matchWidth = true;
                matchWidthLocally = true;
            }
            // 10.���������
            final int margin = lp.leftMargin + lp.rightMargin;
            final int measuredWidth = child.getMeasuredWidth() + margin;
            maxWidth = Math.max(maxWidth, measuredWidth);
            childState = combineMeasuredStates(childState, child.getMeasuredState());
            // 11.����allFillParent��ֵ
            allFillParent = allFillParent && lp.width == LayoutParams.MATCH_PARENT;
            if (lp.weight > 0) {
                // ����Ȩ��Ԫ�ص������
                weightedMaxWidth = Math.max(weightedMaxWidth,
                        matchWidthLocally ? margin : measuredWidth);
            } else {
                // ���·�Ȩ��Ԫ�ص������
                alternativeMaxWidth = Math.max(alternativeMaxWidth,
                        matchWidthLocally ? margin : measuredWidth);
            }
            i += getChildrenSkipCount(child, i);
        }
//------------------------------------------------------------------------------------------------------------
        // �ж����һ��Ԫ�غ����Ƿ�Ҫ�ӷָ�����Ҫ�Ļ�����Ҫ���ۻ��߶�
        if (mTotalLength > 0 && hasDividerBeforeChildAt(count)) {
            mTotalLength += mDividerHeight;
        }

        // �󲼾���wrap_content��Ϊ�ƶ�������£���ָ����useLargestChild
        // ���ܸ߶ȣ��ᱻ����ˢ��
        if (useLargestChild &&
                (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED)) {
            mTotalLength = 0;

            for (int i = 0; i < count; ++i) {
                final View child = getVirtualChildAt(i);

                if (child == null) {
                    mTotalLength += measureNullChild(i);
                    continue;
                }

                if (child.getVisibility() == GONE) {
                    i += getChildrenSkipCount(child, i);
                    continue;
                }

                final LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)
                        child.getLayoutParams();
                // Account for negative margins
                final int totalLength = mTotalLength;
                mTotalLength = Math.max(totalLength, totalLength + largestChildHeight +
                        lp.topMargin + lp.bottomMargin + getNextLocationOffset(child));
            }
        }
//------------------------------------------------------------------------------------------------------------
        // �ۼ�padding
        mTotalLength += mPaddingTop + mPaddingBottom;

        int heightSize = mTotalLength;
        // �����õ���Сֵȡ��
        heightSize = Math.max(heightSize, getSuggestedMinimumHeight());
        // Reconcile our calculated size with the heightMeasureSpec
        // ���ָ������߶ȣ�������ļ������۶�󣬶�Ҫ����ֵ��£
        // ͬ�����������wrapcontent�����߶ȣ�ҲҪ����ֵ��£
        int heightSizeAndState = resolveSizeAndState(heightSize, heightMeasureSpec, 0);
        heightSize = heightSizeAndState & MEASURED_SIZE_MASK;

        // ��������Ŀ��ֵ֮��Ĳ�࣡���ֻ������ȨֵԪ�����ϣ�Ȩ���ǰ�˫�н���
        // ���������ͣ�Ҳ��������С����
        int delta = heightSize - mTotalLength;
        if (delta != 0 && totalWeight > 0.0f) {
//------------------------------------------------------------------------------------------------------------
            // �����ļ���������Ȩֵ��������ô���㶼ûʲô�ã�������������
            float weightSum = mWeightSum > 0.0f ? mWeightSum : totalWeight;

            mTotalLength = 0;
            // �������е���Ԫ��
            for (int i = 0; i < count; ++i) {
                // 1.��ȡ��Ӧ����Ԫ��
                final View child = getVirtualChildAt(i);
                // 2.GONE���Ĳ�����
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                // 3.��ȡ��Ӧ������  
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();

                float childExtra = lp.weight;
                if (childExtra > 0) {
                    // Child said it could absorb extra space -- give him his share
                    int share = (int) (childExtra * delta / weightSum);
                    weightSum -= childExtra;
                    delta -= share;

                    final int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,
                            mPaddingLeft + mPaddingRight +
                                    lp.leftMargin + lp.rightMargin, lp.width);

                    if ((lp.height != 0) || (heightMode != MeasureSpec.EXACTLY)) {
                        // height����0dp ���� �󲼾���wrapcontent�������
                        int childHeight = child.getMeasuredHeight() + share;
                        // ���Ѿ�����Ļ����ϣ��������ͣ�������������������������������������
                        if (childHeight < 0) {
                            childHeight = 0;
                        }
                        child.measure(childWidthMeasureSpec,
                                MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY));
                    } else {  
                        // ����㰴����ȷ��д�� height=0dp weight����0����
                        // ��ô���󲼾�����ȷ������£�OKû���⣬ʹ��ʣ��ռ�
                        child.measure(childWidthMeasureSpec,
                                MeasureSpec.makeMeasureSpec(share > 0 ? share : 0,
                                        MeasureSpec.EXACTLY));
                    }

                    // Child may now not fit in vertical dimension.
                    childState = combineMeasuredStates(childState, child.getMeasuredState()
                            & (MEASURED_STATE_MASK>>MEASURED_HEIGHT_STATE_SHIFT));
                }

                final int margin =  lp.leftMargin + lp.rightMargin;
                final int measuredWidth = child.getMeasuredWidth() + margin;
                maxWidth = Math.max(maxWidth, measuredWidth);

                boolean matchWidthLocally = widthMode != MeasureSpec.EXACTLY &&
                        lp.width == LayoutParams.MATCH_PARENT;

                alternativeMaxWidth = Math.max(alternativeMaxWidth,
                        matchWidthLocally ? margin : measuredWidth);

                allFillParent = allFillParent && lp.width == LayoutParams.MATCH_PARENT;

                final int totalLength = mTotalLength;
                mTotalLength = Math.max(totalLength, totalLength + child.getMeasuredHeight() +
                        lp.topMargin + lp.bottomMargin + getNextLocationOffset(child));
            }

            // Add in our padding
            mTotalLength += mPaddingTop + mPaddingBottom;
            // TODO: Should we recompute the heightSpec based on the new total length?
//------------------------------------------------------------------------------------------------------------
        } else {
            alternativeMaxWidth = Math.max(alternativeMaxWidth,
                                           weightedMaxWidth);
            // ��ʹû��ʣ��ռ䣬���Ǵ󲼾���wrapcontent�������ָ����useLargestChild
            // Ҳ��Ҫ��������weight���ӿؼ����е���
            if (useLargestChild && heightMode != MeasureSpec.EXACTLY) {
                for (int i = 0; i < count; i++) {
                    final View child = getVirtualChildAt(i);

                    if (child == null || child.getVisibility() == View.GONE) {
                        continue;
                    }

                    final LinearLayout.LayoutParams lp =
                            (LinearLayout.LayoutParams) child.getLayoutParams();

                    float childExtra = lp.weight;
                    if (childExtra > 0) {
                        // ����ԭ������largestChildHeightΪ׼
                        child.measure(
                                MeasureSpec.makeMeasureSpec(child.getMeasuredWidth(),
                                        MeasureSpec.EXACTLY),
                                MeasureSpec.makeMeasureSpec(largestChildHeight,
                                        MeasureSpec.EXACTLY));
                    }
                }
            }
        }

        if (!allFillParent && widthMode != MeasureSpec.EXACTLY) {
            maxWidth = alternativeMaxWidth;
        }

        maxWidth += mPaddingLeft + mPaddingRight;

        // Check against our minimum width
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());

        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
                heightSizeAndState);

        if (matchWidth) {
            forceUniformWidth(count, heightMeasureSpec);
        }
    }

    private void forceUniformWidth(int count, int heightMeasureSpec) {
        // Pretend that the linear layout has an exact size.
        int uniformMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(),
                MeasureSpec.EXACTLY);
        for (int i = 0; i< count; ++i) {
           final View child = getVirtualChildAt(i);
           if (child.getVisibility() != GONE) { 
               LinearLayout.LayoutParams lp = ((LinearLayout.LayoutParams)child.getLayoutParams());

               if (lp.width == LayoutParams.MATCH_PARENT) {
                   // ��ʱ����߶�
                   int oldHeight = lp.height;
                   lp.height = child.getMeasuredHeight();

                   // ���¼���
                   measureChildWithMargins(child, uniformMeasureSpec, 0, heightMeasureSpec, 0);
                   // �ָ��߶�
                   lp.height = oldHeight;
               }
           }
        }
    }
    // ˮƽ����
    void measureHorizontal(int widthMeasureSpec, int heightMeasureSpec) {
        mTotalLength = 0;
        int maxHeight = 0;
        int childState = 0;
        int alternativeMaxHeight = 0;
        int weightedMaxHeight = 0;
        boolean allFillParent = true;
        float totalWeight = 0;

        final int count = getVirtualChildCount();

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        boolean matchHeight = false;

        if (mMaxAscent == null || mMaxDescent == null) {
            mMaxAscent = new int[VERTICAL_GRAVITY_COUNT];
            mMaxDescent = new int[VERTICAL_GRAVITY_COUNT];
        }

        final int[] maxAscent = mMaxAscent;
        final int[] maxDescent = mMaxDescent;

        maxAscent[0] = maxAscent[1] = maxAscent[2] = maxAscent[3] = -1;
        maxDescent[0] = maxDescent[1] = maxDescent[2] = maxDescent[3] = -1;

        final boolean baselineAligned = mBaselineAligned;
        final boolean useLargestChild = mUseLargestChild;

        final boolean isExactly = widthMode == MeasureSpec.EXACTLY;

        int largestChildWidth = Integer.MIN_VALUE;
//------------------------------------------------------------------------------------------------------------
        // See how wide everyone is. Also remember max height.
        for (int i = 0; i < count; ++i) {
            // 1.��ȡ��Ӧ�Ŀؼ�
            final View child = getVirtualChildAt(i);
            // 2.�ؼ�Ϊ�գ������
            if (child == null) {
                mTotalLength += measureNullChild(i);
                continue;
            }
            // 3.�ؼ���GONE���ģ�Ҳ����
            if (child.getVisibility() == GONE) {
                i += getChildrenSkipCount(child, i);
                continue;
            }
            // 4.�ۼ�dividerWidth
            if (hasDividerBeforeChildAt(i)) {
                mTotalLength += mDividerWidth;
            }
            // 5.�ۼ�Ȩ��
            final LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)
                    child.getLayoutParams();
            totalWeight += lp.weight;

            if (widthMode == MeasureSpec.EXACTLY && lp.width == 0 && lp.weight > 0) {
                if (isExactly) {
                    // ����Ǿ�ȷ��ֵ�ģ���Ҫ����ʣ��ռ���ټ���
                    // ���ۼ�margin
                    mTotalLength += lp.leftMargin + lp.rightMargin;
                } else {
                    // ɶX,�����֧������
                    final int totalLength = mTotalLength;
                    mTotalLength = Math.max(totalLength, totalLength +
                            lp.leftMargin + lp.rightMargin);
                }

                // Baseline ��Ҫ����ģ���߶��й�
                if (baselineAligned) {
                    final int freeSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                    child.measure(freeSpec, freeSpec);
                }
            } else {
                int oldWidth = Integer.MIN_VALUE;
                if (lp.width == 0 && lp.weight > 0) {
                    // �󲼾���wrapcontent������£��ӿؼ���Ҫ��ʣ��ռ䣬ʡʡ��
                    oldWidth = 0;
                    lp.width = LayoutParams.WRAP_CONTENT;
                }
                // �ȼ���һ��
                measureChildBeforeLayout(child, i, widthMeasureSpec,
                        totalWeight == 0 ? mTotalLength : 0,
                        heightMeasureSpec, 0);
                // �ٰѱ�Ǳ�ʶһ��
                if (oldWidth != Integer.MIN_VALUE) {
                    lp.width = oldWidth;
                }
                // �����С�Ż�����ɶJB��
                final int childWidth = child.getMeasuredWidth();
                if (isExactly) {
                    mTotalLength += childWidth + lp.leftMargin + lp.rightMargin +
                            getNextLocationOffset(child);
                } else {
                    final int totalLength = mTotalLength;
                    mTotalLength = Math.max(totalLength, totalLength + childWidth + lp.leftMargin +
                           lp.rightMargin + getNextLocationOffset(child));
                }
                // ���Ҫ��¼��������£����¼���ĳߴ�
                if (useLargestChild) {
                    largestChildWidth = Math.max(childWidth, largestChildWidth);
                }
            }

            boolean matchHeightLocally = false;
            if (heightMode != MeasureSpec.EXACTLY && lp.height == LayoutParams.MATCH_PARENT) {
                // �߶ȱ��
                matchHeight = true;
                matchHeightLocally = true;
            }

            final int margin = lp.topMargin + lp.bottomMargin;
            final int childHeight = child.getMeasuredHeight() + margin;
            childState = combineMeasuredStates(childState, child.getMeasuredState());
            // baseLine�����е�С����
            if (baselineAligned) {
                final int childBaseline = child.getBaseline();
                if (childBaseline != -1) {
                    // Translates the child's vertical gravity into an index
                    // in the range 0..VERTICAL_GRAVITY_COUNT
                    // ����ӿؼ��Լ�û�ж���gravity����ʹ�ø����ֵģ��Լ���������ʹ���Լ���
                    // ������һ�������Ľ�� 111 0000
                    final int gravity = (lp.gravity < 0 ? mGravity : lp.gravity)
                            & Gravity.VERTICAL_GRAVITY_MASK;
                    // ������һ������������λ��λ11
                    final int index = ((gravity >> Gravity.AXIS_Y_SHIFT)
                            & ~Gravity.AXIS_SPECIFIED) >> 1;
                    // index = 0 ���Ǿ���
                    // index = 1 ����
                    // index = 2 ����
                    // index = 3 �������˾��ϣ��������˾���

                    // ÿһ��ģʽ�£�baseline�ӿؼ��������Ƕ��
                    // �ӿؼ������������Ƕ��
                    maxAscent[index] = Math.max(maxAscent[index], childBaseline);
                    maxDescent[index] = Math.max(maxDescent[index], childHeight - childBaseline);
                }
            }

            maxHeight = Math.max(maxHeight, childHeight);

            allFillParent = allFillParent && lp.height == LayoutParams.MATCH_PARENT;
            if (lp.weight > 0) {
                // weight�������
                weightedMaxHeight = Math.max(weightedMaxHeight,
                        matchHeightLocally ? margin : childHeight);
            } else {
                // ��weight�������
                alternativeMaxHeight = Math.max(alternativeMaxHeight,
                        matchHeightLocally ? margin : childHeight);
            }
            i += getChildrenSkipCount(child, i);
        }
//------------------------------------------------------------------------------------------------------------
        if (mTotalLength > 0 && hasDividerBeforeChildAt(count)) {
            mTotalLength += mDividerWidth;
        }

        // ˮƽlayout�У�baselineAligned���ڵ����壬���ǵ���maxHeight������
        if (maxAscent[INDEX_TOP] != -1 ||
                maxAscent[INDEX_CENTER_VERTICAL] != -1 ||
                maxAscent[INDEX_BOTTOM] != -1 ||
                maxAscent[INDEX_FILL] != -1) {
            final int ascent = Math.max(maxAscent[INDEX_FILL],
                    Math.max(maxAscent[INDEX_CENTER_VERTICAL],
                    Math.max(maxAscent[INDEX_TOP], maxAscent[INDEX_BOTTOM])));
            final int descent = Math.max(maxDescent[INDEX_FILL],
                    Math.max(maxDescent[INDEX_CENTER_VERTICAL],
                    Math.max(maxDescent[INDEX_TOP], maxDescent[INDEX_BOTTOM])));
            maxHeight = Math.max(maxHeight, ascent + descent);
        }
//------------------------------------------------------------------------------------------------------------
        if (useLargestChild &&
                (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED)) {
            // ʹ������ӿؼ�����  �󲼾���wrapConent��UNSPECIFIED�����
            mTotalLength = 0;
            // ���������ӿؼ�
            for (int i = 0; i < count; ++i) {
                // 1.��ȡ��Ӧ���ӿؼ�
                final View child = getVirtualChildAt(i);
                // 2.�ӿؼ�Ϊ�յ���� ����
                if (child == null) {
                    mTotalLength += measureNullChild(i);
                    continue;
                }
                // 3.�ӿؼ�GONE������� ����
                if (child.getVisibility() == GONE) {
                    i += getChildrenSkipCount(child, i);
                    continue;
                }

                final LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)
                        child.getLayoutParams();
                if (isExactly) {
                    // �����֧�����𣿴��
                    mTotalLength += largestChildWidth + lp.leftMargin + lp.rightMargin +
                            getNextLocationOffset(child);
                } else {
                    // ���¼���
                    final int totalLength = mTotalLength;
                    mTotalLength = Math.max(totalLength, totalLength + largestChildWidth +
                            lp.leftMargin + lp.rightMargin + getNextLocationOffset(child));
                }
            }
        }
//------------------------------------------------------------------------------------------------------------
        // Add in our padding
        mTotalLength += mPaddingLeft + mPaddingRight;
        int widthSize = mTotalLength;
        // Check against our minimum width
        widthSize = Math.max(widthSize, getSuggestedMinimumWidth());

        // Reconcile our calculated size with the widthMeasureSpec
        int widthSizeAndState = resolveSizeAndState(widthSize, widthMeasureSpec, 0);
        widthSize = widthSizeAndState & MEASURED_SIZE_MASK;

        // Either expand children with weight to take up available space or
        // shrink them if they extend beyond our current bounds
        int delta = widthSize - mTotalLength;
        if (delta != 0 && totalWeight > 0.0f) {
//------------------------------------------------------------------------------------------------------------
            // �����е�Ȩ������
            float weightSum = mWeightSum > 0.0f ? mWeightSum : totalWeight;

            maxAscent[0] = maxAscent[1] = maxAscent[2] = maxAscent[3] = -1;
            maxDescent[0] = maxDescent[1] = maxDescent[2] = maxDescent[3] = -1;
            maxHeight = -1;
            mTotalLength = 0;
            for (int i = 0; i < count; ++i) {
                final View child = getVirtualChildAt(i);
                if (child == null || child.getVisibility() == View.GONE) {
                    continue;
                }
                final LinearLayout.LayoutParams lp =
                        (LinearLayout.LayoutParams) child.getLayoutParams();

                float childExtra = lp.weight;
                // ��������Ȩ�ص�Ԫ�� ����ֱ��������
                if (childExtra > 0) {
                    // Child said it could absorb extra space -- give him his share
                    int share = (int) (childExtra * delta / weightSum);
                    weightSum -= childExtra;
                    delta -= share;

                    final int childHeightMeasureSpec = getChildMeasureSpec(
                            heightMeasureSpec,
                            mPaddingTop + mPaddingBottom + lp.topMargin + lp.bottomMargin,
                            lp.height);

                    if ((lp.width != 0) || (widthMode != MeasureSpec.EXACTLY)) {
                        int childWidth = child.getMeasuredWidth() + share;
                        if (childWidth < 0) {
                            childWidth = 0;
                        }

                        child.measure(
                            MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
                            childHeightMeasureSpec);
                    } else {
                        // child was skipped in the loop above. Measure for this first time here
                        child.measure(MeasureSpec.makeMeasureSpec(
                                share > 0 ? share : 0, MeasureSpec.EXACTLY),
                                childHeightMeasureSpec);
                    }

                    // Child may now not fit in horizontal dimension.
                    childState = combineMeasuredStates(childState,
                            child.getMeasuredState() & MEASURED_STATE_MASK);
                }

                // ����д��ë���尡
                if (isExactly) {
                    mTotalLength += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin +
                            getNextLocationOffset(child);
                } else {
                    final int totalLength = mTotalLength;
                    mTotalLength = Math.max(totalLength, totalLength + child.getMeasuredWidth() +
                            lp.leftMargin + lp.rightMargin + getNextLocationOffset(child));
                }

                boolean matchHeightLocally = heightMode != MeasureSpec.EXACTLY &&
                        lp.height == LayoutParams.MATCH_PARENT;

                final int margin = lp.topMargin + lp .bottomMargin;
                int childHeight = child.getMeasuredHeight() + margin;
                maxHeight = Math.max(maxHeight, childHeight);
                alternativeMaxHeight = Math.max(alternativeMaxHeight,
                        matchHeightLocally ? margin : childHeight);

                allFillParent = allFillParent && lp.height == LayoutParams.MATCH_PARENT;

                // ���¼���maxAscent��maxDescent
                if (baselineAligned) {
                    final int childBaseline = child.getBaseline();
                    if (childBaseline != -1) {
                        // Translates the child's vertical gravity into an index in the range 0..2
                        final int gravity = (lp.gravity < 0 ? mGravity : lp.gravity)
                                & Gravity.VERTICAL_GRAVITY_MASK;
                        final int index = ((gravity >> Gravity.AXIS_Y_SHIFT)
                                & ~Gravity.AXIS_SPECIFIED) >> 1;

                        maxAscent[index] = Math.max(maxAscent[index], childBaseline);
                        maxDescent[index] = Math.max(maxDescent[index],
                                childHeight - childBaseline);
                    }
                }
            }

            // �浰�۵Ĵ��룬д���˷���
            mTotalLength += mPaddingLeft + mPaddingRight;
            if (maxAscent[INDEX_TOP] != -1 ||
                    maxAscent[INDEX_CENTER_VERTICAL] != -1 ||
                    maxAscent[INDEX_BOTTOM] != -1 ||
                    maxAscent[INDEX_FILL] != -1) {
                final int ascent = Math.max(maxAscent[INDEX_FILL],
                        Math.max(maxAscent[INDEX_CENTER_VERTICAL],
                        Math.max(maxAscent[INDEX_TOP], maxAscent[INDEX_BOTTOM])));
                final int descent = Math.max(maxDescent[INDEX_FILL],
                        Math.max(maxDescent[INDEX_CENTER_VERTICAL],
                        Math.max(maxDescent[INDEX_TOP], maxDescent[INDEX_BOTTOM])));
                maxHeight = Math.max(maxHeight, ascent + descent);
            }
//------------------------------------------------------------------------------------------------------------
        } else {
            alternativeMaxHeight = Math.max(alternativeMaxHeight, weightedMaxHeight);
            if (useLargestChild && widthMode != MeasureSpec.EXACTLY) {
                for (int i = 0; i < count; i++) {
                    final View child = getVirtualChildAt(i);
                    if (child == null || child.getVisibility() == View.GONE) {
                        continue;
                    }
                    final LinearLayout.LayoutParams lp =
                            (LinearLayout.LayoutParams) child.getLayoutParams();

                    float childExtra = lp.weight;
                    if (childExtra > 0) {
                        child.measure(
                                MeasureSpec.makeMeasureSpec(largestChildWidth, MeasureSpec.EXACTLY),
                                MeasureSpec.makeMeasureSpec(child.getMeasuredHeight(),
                                        MeasureSpec.EXACTLY));
                    }
                }
            }
        }

        if (!allFillParent && heightMode != MeasureSpec.EXACTLY) {
            maxHeight = alternativeMaxHeight;
        }

        maxHeight += mPaddingTop + mPaddingBottom;

        // Check against our minimum height
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());

        setMeasuredDimension(widthSizeAndState | (childState&MEASURED_STATE_MASK),
                resolveSizeAndState(maxHeight, heightMeasureSpec,
                        (childState<<MEASURED_HEIGHT_STATE_SHIFT)));

        if (matchHeight) {
            forceUniformHeight(count, widthMeasureSpec);
        }
    }

    private void forceUniformHeight(int count, int widthMeasureSpec) {
        int uniformMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(),
                MeasureSpec.EXACTLY);
        for (int i = 0; i < count; ++i) {
           final View child = getVirtualChildAt(i);
           if (child.getVisibility() != GONE) { 
               LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();

               if (lp.height == LayoutParams.MATCH_PARENT) {
                   // Temporarily force children to reuse their old measured width
                   // FIXME: this may not be right for something like wrapping text?
                   int oldWidth = lp.width;
                   lp.width = child.getMeasuredWidth();

                   // Remeasure with new dimensions
                   measureChildWithMargins(child, widthMeasureSpec, 0, uniformMeasureSpec, 0);
                   lp.width = oldWidth;
               }
           }
        }
    }
���ֲ���

    // ����
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mOrientation == VERTICAL) {
            layoutVertical(l, t, r, b);
        } else {
            layoutHorizontal(l, t, r, b);
        }
    }
    // ��ֱ����
    void layoutVertical(int left, int top, int right, int bottom) {
        final int paddingLeft = mPaddingLeft;

        int childTop;// ��ʼ���Ƶ�Y����
        int childLeft;

        // �ؼ��ܿ��
        final int width = right - left;
        // ���Ի��Ƶ���X����
        int childRight = width - mPaddingRight;
        // ����ʹ�õĻ��ƿ��
        int childSpace = width - paddingLeft - mPaddingRight;

        final int count = getVirtualChildCount();

        final int majorGravity = mGravity & Gravity.VERTICAL_GRAVITY_MASK;
        final int minorGravity = mGravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;

        // ��ʼ���Ƶ�Y����
        switch (majorGravity) {
           case Gravity.BOTTOM:
               // �󲼾ֳ���  mPaddingTop+ʣ��ռ�
               childTop = mPaddingTop + bottom - top - mTotalLength;
               break;

               // mTotalLength contains the padding already
           case Gravity.CENTER_VERTICAL:
               childTop = mPaddingTop + (bottom - top - mTotalLength) / 2;
               break;

           case Gravity.TOP:
           default:
               childTop = mPaddingTop;
               break;
        }

        for (int i = 0; i < count; i++) {
            final View child = getVirtualChildAt(i);
            if (child == null) {
                childTop += measureNullChild(i);
            } else if (child.getVisibility() != GONE) {
                // 1.ֻ���㲻��GONE����Ԫ��
                final int childWidth = child.getMeasuredWidth();// ��ȡ�����Ŀ��
                final int childHeight = child.getMeasuredHeight();// ��ȡ�����ĸ߶�

                final LinearLayout.LayoutParams lp =
                        (LinearLayout.LayoutParams) child.getLayoutParams();
                // 2.�Լ�û������gravity��ʹ�ø����ֵ�
                int gravity = lp.gravity;
                if (gravity < 0) {
                    gravity = minorGravity;
                }
                final int layoutDirection = getLayoutDirection();
                final int absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection);
                // ���㿪ʼ���Ƶ�X����
                switch (absoluteGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
                    case Gravity.CENTER_HORIZONTAL:
                        childLeft = paddingLeft + ((childSpace - childWidth) / 2)
                                + lp.leftMargin - lp.rightMargin;
                        break;

                    case Gravity.RIGHT:
                        childLeft = childRight - childWidth - lp.rightMargin;
                        break;

                    case Gravity.LEFT:
                    default:
                        childLeft = paddingLeft + lp.leftMargin;
                        break;
                }
                // ���¿�ʼ���Ƶ�Y����
                if (hasDividerBeforeChildAt(i)) {
                    childTop += mDividerHeight;
                }

                childTop += lp.topMargin;
                // Ϊ�ӿؼ����û�������
                setChildFrame(child, childLeft, childTop + getLocationOffset(child),
                        childWidth, childHeight);
                // ���¿�ʼ���Ƶ�Y���꣬Ϊ��һ���ؼ���׼��
                childTop += childHeight + lp.bottomMargin + getNextLocationOffset(child);

                i += getChildrenSkipCount(child, i);
            }
        }
    }
    // ˮƽ����
    void layoutHorizontal(int left, int top, int right, int bottom) {
        final boolean isLayoutRtl = isLayoutRtl();
        final int paddingTop = mPaddingTop;

        int childTop;// ��ʼ���Ƶ�Y����
        int childLeft;// ��ʼ���Ƶ�X����

        // �ܸ߶�
        final int height = bottom - top;
        // ���Ի��Ƶ�Y���꼫��
        int childBottom = height - mPaddingBottom; 

        // ���Ի��Ƶĸ߶�
        int childSpace = height - paddingTop - mPaddingBottom;

        final int count = getVirtualChildCount();

        final int majorGravity = mGravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        final int minorGravity = mGravity & Gravity.VERTICAL_GRAVITY_MASK;

        final boolean baselineAligned = mBaselineAligned;

        final int[] maxAscent = mMaxAscent;
        final int[] maxDescent = mMaxDescent;
        /// ���㿪ʼ���Ƶ�X����
        final int layoutDirection = getLayoutDirection();
        switch (Gravity.getAbsoluteGravity(majorGravity, layoutDirection)) {
            case Gravity.RIGHT:
                // mTotalLength contains the padding already
                childLeft = mPaddingLeft + right - left - mTotalLength;
                break;

            case Gravity.CENTER_HORIZONTAL:
                // mTotalLength contains the padding already
                childLeft = mPaddingLeft + (right - left - mTotalLength) / 2;
                break;

            case Gravity.LEFT:
            default:
                childLeft = mPaddingLeft;
                break;
        }

        // XXX ��ν�İ������İ�....
        // �����ң����Ǵ��ҵ���...
        int start = 0;
        int dir = 1;
        //In case of RTL, start drawing from the last child.
        if (isLayoutRtl) {
            start = count - 1;
            dir = -1;
        }

        for (int i = 0; i < count; i++) {
            int childIndex = start + dir * i;
            final View child = getVirtualChildAt(childIndex);

            if (child == null) {
                childLeft += measureNullChild(childIndex);
            } else if (child.getVisibility() != GONE) {
                final int childWidth = child.getMeasuredWidth();
                final int childHeight = child.getMeasuredHeight();
                int childBaseline = -1;

                final LinearLayout.LayoutParams lp =
                        (LinearLayout.LayoutParams) child.getLayoutParams();
                // ����MATCH_PARENT�Ĳ��������Ͱ����Լ��ķ�ʽ�����㿪ʼ���Ƶ�Y����
                if (baselineAligned && lp.height != LayoutParams.MATCH_PARENT) {
                    childBaseline = child.getBaseline();
                }
                // �Լ�û��gravity������£��ô󲼾ֵ�
                int gravity = lp.gravity;
                if (gravity < 0) {
                    gravity = minorGravity;
                }
                // ���㵱ǰ�ӿؼ��Ŀ�ʼ����Y����
                // ��˵��ɶ�����أ�˵��TOPһ��Ķ���
                // BOTTOMһ��Ķ��� ���е�һ��Ķ���
                switch (gravity & Gravity.VERTICAL_GRAVITY_MASK) {
                    case Gravity.TOP:
                        // �Ӷ�
                        childTop = paddingTop + lp.topMargin;
                        if (childBaseline != -1) {
                            // ΢��ƫ�ʹ����뵽�󲼾ֵ�baseLine��
                            childTop += maxAscent[INDEX_TOP] - childBaseline;
                        }
                        break;

                    case Gravity.CENTER_VERTICAL:
                        childTop = paddingTop + ((childSpace - childHeight) / 2)
                                + lp.topMargin - lp.bottomMargin;
                        break;

                    case Gravity.BOTTOM:
                        childTop = childBottom - childHeight - lp.bottomMargin;
                        if (childBaseline != -1) {
                            int descent = child.getMeasuredHeight() - childBaseline;
                            childTop -= (maxDescent[INDEX_BOTTOM] - descent);
                        }
                        break;
                    default:
                        childTop = paddingTop;
                        break;
                }

                if (hasDividerBeforeChildAt(childIndex)) {
                    childLeft += mDividerWidth;
                }
                // ���»�ͼX����
                childLeft += lp.leftMargin;
                setChildFrame(child, childLeft + getLocationOffset(child), childTop,
                        childWidth, childHeight);
                // ������һ���ؼ��Ļ�ͼX����
                childLeft += childWidth + lp.rightMargin +
                        getNextLocationOffset(child);

                i += getChildrenSkipCount(child, childIndex);
            }
        }
    }
�ӿڲ���

    // ������
    int getChildrenSkipCount(View child, int index) {
        return 0;
    }

    // ������
    int measureNullChild(int childIndex) {
        return 0;
    }

    // ������
    void measureChildBeforeLayout(View child, int childIndex,
            int widthMeasureSpec, int totalWidth, int heightMeasureSpec,
            int totalHeight) {
        measureChildWithMargins(child, widthMeasureSpec, totalWidth,
                heightMeasureSpec, totalHeight);
    }

    // ������
    int getLocationOffset(View child) {
        return 0;
    }

    // ������
    int getNextLocationOffset(View child) {
        return 0;
    }

    // divider��ص�
    public void setShowDividers(int showDividers) {
        if (showDividers != mShowDividers) {
            requestLayout();
        }
        mShowDividers = showDividers;
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public int getShowDividers() {
        return mShowDividers;
    }

    public Drawable getDividerDrawable() {
        return mDivider;
    }

    public void setDividerDrawable(Drawable divider) {
        if (divider == mDivider) {
            return;
        }
        mDivider = divider;
        if (divider != null) {
            mDividerWidth = divider.getIntrinsicWidth();
            mDividerHeight = divider.getIntrinsicHeight();
        } else {
            mDividerWidth = 0;
            mDividerHeight = 0;
        }
        setWillNotDraw(divider == null);
        requestLayout();
    }

    public void setDividerPadding(int padding) {
        mDividerPadding = padding;
    }

    public int getDividerPadding() {
        return mDividerPadding;
    }

    public int getDividerWidth() {
        return mDividerWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDivider == null) {
            return;
        }

        if (mOrientation == VERTICAL) {
            drawDividersVertical(canvas);
        } else {
            drawDividersHorizontal(canvas);
        }
    }

    void drawDividersVertical(Canvas canvas) {
        final int count = getVirtualChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getVirtualChildAt(i);

            if (child != null && child.getVisibility() != GONE) {
                if (hasDividerBeforeChildAt(i)) {
                    final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    final int top = child.getTop() - lp.topMargin - mDividerHeight;
                    drawHorizontalDivider(canvas, top);
                }
            }
        }

        if (hasDividerBeforeChildAt(count)) {
            final View child = getVirtualChildAt(count - 1);
            int bottom = 0;
            if (child == null) {
                bottom = getHeight() - getPaddingBottom() - mDividerHeight;
            } else {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                bottom = child.getBottom() + lp.bottomMargin;
            }
            drawHorizontalDivider(canvas, bottom);
        }
    }

    void drawDividersHorizontal(Canvas canvas) {
        final int count = getVirtualChildCount();
        final boolean isLayoutRtl = isLayoutRtl();
        for (int i = 0; i < count; i++) {
            final View child = getVirtualChildAt(i);

            if (child != null && child.getVisibility() != GONE) {
                if (hasDividerBeforeChildAt(i)) {
                    final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    final int position;
                    if (isLayoutRtl) {
                        position = child.getRight() + lp.rightMargin;
                    } else {
                        position = child.getLeft() - lp.leftMargin - mDividerWidth;
                    }
                    drawVerticalDivider(canvas, position);
                }
            }
        }

        if (hasDividerBeforeChildAt(count)) {
            final View child = getVirtualChildAt(count - 1);
            int position;
            if (child == null) {
                if (isLayoutRtl) {
                    position = getPaddingLeft();
                } else {
                    position = getWidth() - getPaddingRight() - mDividerWidth;
                }
            } else {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (isLayoutRtl) {
                    position = child.getLeft() - lp.leftMargin - mDividerWidth;
                } else {
                    position = child.getRight() + lp.rightMargin;
                }
            }
            drawVerticalDivider(canvas, position);
        }
    }

    void drawHorizontalDivider(Canvas canvas, int top) {
        mDivider.setBounds(getPaddingLeft() + mDividerPadding, top,
                getWidth() - getPaddingRight() - mDividerPadding, top + mDividerHeight);
        mDivider.draw(canvas);
    }

    void drawVerticalDivider(Canvas canvas, int left) {
        mDivider.setBounds(left, getPaddingTop() + mDividerPadding,
                left + mDividerWidth, getHeight() - getPaddingBottom() - mDividerPadding);
        mDivider.draw(canvas);
    }

    // �Ƿ���߶���
    public boolean isBaselineAligned() {
        return mBaselineAligned;
    }

    // ���û��߶���
    @android.view.RemotableViewMethod
    public void setBaselineAligned(boolean baselineAligned) {
        mBaselineAligned = baselineAligned;
    }

    // �Ƿ������ߴ����²���
    public boolean isMeasureWithLargestChildEnabled() {
        return mUseLargestChild;
    }

    // �Ƿ������ߴ����²���
    @android.view.RemotableViewMethod
    public void setMeasureWithLargestChildEnabled(boolean enabled) {
        mUseLargestChild = enabled;
    }

    // ��ȡ��׼��
    @Override
    public int getBaseline() {
        // 1.С��0  ��Ȼ�쳣
        if (mBaselineAlignedChildIndex < 0) {
            return super.getBaseline();
        }
        // 2.index���������࣬��Ȼ�쳣
        if (getChildCount() <= mBaselineAlignedChildIndex) {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout "
                    + "set to an index that is out of bounds.");
        }
        // 3.��ȡ��Ӧ����Ԫ��
        final View child = getChildAt(mBaselineAlignedChildIndex);
        // 4.��ȡ��Ӧ��Ԫ�صĻ�׼��
        final int childBaseline = child.getBaseline();

        if (childBaseline == -1) {
            if (mBaselineAlignedChildIndex == 0) {
                // this is just the default case, safe to return -1
                return -1;
            }
            // the user picked an index that points to something that doesn't
            // know how to calculate its baseline.
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout "
                    + "points to a View that doesn't know how to get its baseline.");
        }

        // 5.��ȡ��׼�ӿؼ����붥���ľ���
        int childTop = mBaselineChildTop;

        if (mOrientation == VERTICAL) {
            // ��������Ǵ�ֱ�������׼�߻�Ҫ����
            final int majorGravity = mGravity & Gravity.VERTICAL_GRAVITY_MASK;
            if (majorGravity != Gravity.TOP) {
               switch (majorGravity) {
                   case Gravity.BOTTOM:
                        // ����
                       childTop = mBottom - mTop - mPaddingBottom - mTotalLength;
                       break;

                   case Gravity.CENTER_VERTICAL:
                        // ����
                       childTop += ((mBottom - mTop - mPaddingTop - mPaddingBottom) -
                               mTotalLength) / 2;
                       break;
               }
            }
        }

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();
        // ���붥���ľ���  +��topMargin + ��׼��
        return childTop + lp.topMargin + childBaseline;
    }

    // ���ĸ�Ԫ����Ϊ��׼��
    public int getBaselineAlignedChildIndex() {
        return mBaselineAlignedChildIndex;
    }

    // ���ĸ�Ԫ����Ϊ��׼��
    @android.view.RemotableViewMethod
    public void setBaselineAlignedChildIndex(int i) {
        if ((i < 0) || (i >= getChildCount())) {
            throw new IllegalArgumentException("base aligned child index out "
                    + "of range (0, " + getChildCount() + ")");
        }
        mBaselineAlignedChildIndex = i;
    }

    // ��ȡindexλ�õ��ӿؼ�
    View getVirtualChildAt(int index) {
        return getChildAt(index);
    }

    // ��ȡ�ӿؼ�������
    int getVirtualChildCount() {
        return getChildCount();
    }

    // ��ȡȨ�غ�ֵ
    public float getWeightSum() {
        return mWeightSum;
    }

    // ����Ȩ�غ�ֵ
    @android.view.RemotableViewMethod
    public void setWeightSum(float weightSum) {
        mWeightSum = Math.max(0.0f, weightSum);
    }

    // Determines where to position dividers between children.
    protected boolean hasDividerBeforeChildAt(int childIndex) {
        if (childIndex == 0) {
            // ��һ��Ԫ�أ�����������Ҫչʾdivider ��begging��
            return (mShowDividers & SHOW_DIVIDER_BEGINNING) != 0;
        } else if (childIndex == getChildCount()) {
            // ���һ��Ԫ��
            return (mShowDividers & SHOW_DIVIDER_END) != 0;
        } else if ((mShowDividers & SHOW_DIVIDER_MIDDLE) != 0) {
            //�м��Ԫ��
            boolean hasVisibleViewBefore = false;
            for (int i = childIndex - 1; i >= 0; i--) {
                if (getChildAt(i).getVisibility() != GONE) {
                    hasVisibleViewBefore = true;
                    break;
                }
            }
            return hasVisibleViewBefore;
        }
        return false;
    }

    private void setChildFrame(View child, int left, int top, int width, int height) {        
        child.layout(left, top, left + width, top + height);
    }

    // ���÷���
    public void setOrientation(int orientation) {
        if (mOrientation != orientation) {
            mOrientation = orientation;
            requestLayout();
        }
    }

    // ��ȡ����
    public int getOrientation() {
        return mOrientation;
    }

    // ���ö��뷽ʽ
    @android.view.RemotableViewMethod
    public void setGravity(int gravity) {
        if (mGravity != gravity) {
            if ((gravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) == 0) {
                gravity |= Gravity.START;
            }

            if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == 0) {
                gravity |= Gravity.TOP;
            }

            mGravity = gravity;
            requestLayout();
        }
    }

    @android.view.RemotableViewMethod
    public void setHorizontalGravity(int horizontalGravity) {
        final int gravity = horizontalGravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if ((mGravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) != gravity) {
            mGravity = (mGravity & ~Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) | gravity;
            requestLayout();
        }
    }

    @android.view.RemotableViewMethod
    public void setVerticalGravity(int verticalGravity) {
        final int gravity = verticalGravity & Gravity.VERTICAL_GRAVITY_MASK;
        if ((mGravity & Gravity.VERTICAL_GRAVITY_MASK) != gravity) {
            mGravity = (mGravity & ~Gravity.VERTICAL_GRAVITY_MASK) | gravity;
            requestLayout();
        }
    }

    // ������
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LinearLayout.LayoutParams(getContext(), attrs);
    }

    // ������
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        if (mOrientation == HORIZONTAL) {
            return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        } else if (mOrientation == VERTICAL) {
            return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        }
        return null;
    }

    // ������
    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }


    // Override to allow type-checking of LayoutParams.
    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LinearLayout.LayoutParams;
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(LinearLayout.class.getName());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(LinearLayout.class.getName());
    }
LayoutParams����

    // �������� layout_weight  layout_gravity
    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        // Ȩ��
        @ViewDebug.ExportedProperty(category = "layout")
        public float weight;

        @ViewDebug.ExportedProperty(category = "layout", mapping = {
            @ViewDebug.IntToString(from =  -1,                       to = "NONE"),
            @ViewDebug.IntToString(from = Gravity.NO_GRAVITY,        to = "NONE"),
            @ViewDebug.IntToString(from = Gravity.TOP,               to = "TOP"),
            @ViewDebug.IntToString(from = Gravity.BOTTOM,            to = "BOTTOM"),
            @ViewDebug.IntToString(from = Gravity.LEFT,              to = "LEFT"),
            @ViewDebug.IntToString(from = Gravity.RIGHT,             to = "RIGHT"),
            @ViewDebug.IntToString(from = Gravity.START,            to = "START"),
            @ViewDebug.IntToString(from = Gravity.END,             to = "END"),
            @ViewDebug.IntToString(from = Gravity.CENTER_VERTICAL,   to = "CENTER_VERTICAL"),
            @ViewDebug.IntToString(from = Gravity.FILL_VERTICAL,     to = "FILL_VERTICAL"),
            @ViewDebug.IntToString(from = Gravity.CENTER_HORIZONTAL, to = "CENTER_HORIZONTAL"),
            @ViewDebug.IntToString(from = Gravity.FILL_HORIZONTAL,   to = "FILL_HORIZONTAL"),
            @ViewDebug.IntToString(from = Gravity.CENTER,            to = "CENTER"),
            @ViewDebug.IntToString(from = Gravity.FILL,              to = "FILL")
        })
        // ���뷽ʽ
        public int gravity = -1;

        // ���췽��
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a =
                    c.obtainStyledAttributes(attrs, com.android.internal.R.styleable.LinearLayout_Layout);

            weight = a.getFloat(com.android.internal.R.styleable.LinearLayout_Layout_layout_weight, 0);
            gravity = a.getInt(com.android.internal.R.styleable.LinearLayout_Layout_layout_gravity, -1);

            a.recycle();
        }

        // ���췽��
        public LayoutParams(int width, int height) {
            super(width, height);
            weight = 0;
        }

        // ���췽��
        public LayoutParams(int width, int height, float weight) {
            super(width, height);
            this.weight = weight;
        }

        // ���췽��
        public LayoutParams(ViewGroup.LayoutParams p) {
            super(p);
        }

        // ���췽��
        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
        }

        // ���췽��
        public LayoutParams(LayoutParams source) {
            super(source);

            this.weight = source.weight;
            this.gravity = source.gravity;
        }

        @Override
        public String debug(String output) {
            return output + "LinearLayout.LayoutParams={width=" + sizeToString(width) +
                    ", height=" + sizeToString(height) + " weight=" + weight +  "}";
        }
    }
}