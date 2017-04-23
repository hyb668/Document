  private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            /*精确值模式，指定具体数值*/
            result = specSize;
        } else {
            /*先设置一个默认大小*/
            result = (int) dp2px(48);
            /*
            *最大值模式，
            * layout_width 或 layout_height 为 wrap_content 时，
            * 控件大小随控件的内容变化而变化，此时控件尺寸只要不超过父控件允许的最大尺寸即可。
            * */
            if (specMode == MeasureSpec.AT_MOST) {
                /*取出我们指定的大小和 specSize 中最小的一个来作为最后的测量值*/
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            /*精确值模式，指定具体数值*/
            result = specSize;
        } else {
            /*先设置一个默认大小*/
            result = (int) dp2Px(48);
            /*
            *最大值模式，
            * layout_width 或 layout_height 为 wrap_content 时，
            * 控件大小随控件的内容变化而变化，此时控件尺寸只要不超过父控件允许的最大尺寸即可。
            * */
            if (specMode == MeasureSpec.AT_MOST) {
                /*取出我们指定的大小和 specSize 中最小的一个来作为最后的测量值*/
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

