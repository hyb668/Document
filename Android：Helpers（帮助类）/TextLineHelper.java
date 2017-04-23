package org.alex.helper;

import android.animation.ObjectAnimator;
import android.util.Property;
import android.widget.TextView;

/**
 * 作者：Alex
 * 时间：2016年09月03日    10:02
 * 简述：TexView 展开 与 折叠 的帮助类
 */

@SuppressWarnings("all")
public class TextLineHelper {
    private boolean isMaxLines;
    private int lines;
    private ObjectAnimator max2MinLinesObjectAnimator;
    private ObjectAnimator min2MaxLinesObjectAnimator;
    private Builder builder;
    private OnLineFinishListener onLineFinishListener;

    private TextLineHelper(Builder builder) {
        this.builder = builder;
        isMaxLines = false;
        max2MinLinesObjectAnimator = ObjectAnimator.ofInt(this, new LineProperty(Integer.class, "max2Min"), builder.maxLines, builder.minLines);
        min2MaxLinesObjectAnimator = ObjectAnimator.ofInt(this, new LineProperty(Integer.class, "min2Max"), builder.minLines, builder.maxLines);
        max2MinLinesObjectAnimator.setDuration(builder.max2MinDuration);
        min2MaxLinesObjectAnimator.setDuration(builder.min2MaxDuration);
    }

    public void toggleTextLayout() {
        if (isMaxLines) {
            max2MinLinesObjectAnimator.start();
        } else {
            min2MaxLinesObjectAnimator.start();
        }
        isMaxLines = !isMaxLines;
    }

    private final class LineProperty extends Property<TextLineHelper, Integer> {
        private String name;

        public LineProperty(Class<Integer> type, String name) {
            super(type, name);
            this.name = name;
        }

        @Override
        public Integer get(TextLineHelper object) {
            return object.getLines();
        }

        @Override
        public void set(TextLineHelper object, Integer value) {
            object.setLines(value);
            //LogUtil.e("value = " + value + " minLines = " + builder.minLines + " maxLines = " + builder.maxLines + " isMaxLines = " + isMaxLines + " name = " + name);
            if ((onLineFinishListener != null) && (value == builder.maxLines) && isMaxLines && "min2Max".equals(name)) {
                onLineFinishListener.onLineFinish(builder.textView, true);
            } else if ((onLineFinishListener != null) && (value == builder.minLines) && (!isMaxLines) && ("max2Min".equals(name))) {
                onLineFinishListener.onLineFinish(builder.textView, false);
            }
        }
    }

    private void setLines(int lines) {
        builder.textView.setMaxLines(lines);
        this.lines = lines;
    }

    private int getLines() {
        return lines;
    }

    public TextLineHelper onLineFinishListener(OnLineFinishListener onLineFinishListener) {
        this.onLineFinishListener = onLineFinishListener;
        return this;
    }

    public interface OnLineFinishListener {
        /**
         * @param textView
         * @param isMin2Max 是 由最小行数 到 最大行数
         */
        void onLineFinish(TextView textView, boolean isMin2Max);
    }

    public static class Builder {
        private long min2MaxDuration;
        private long max2MinDuration;
        private int minLines;
        private int maxLines;
        private TextView textView;

        public Builder textView(TextView textView) {
            this.textView = textView;
            return this;
        }

        public Builder min2MaxDuration(long min2MaxDuration) {
            this.min2MaxDuration = min2MaxDuration;
            return this;
        }

        public Builder max2MinDuration(long max2MinDuration) {
            this.max2MinDuration = max2MinDuration;
            return this;
        }

        public Builder minLines(int minLines) {
            this.minLines = minLines;
            return this;
        }

        public Builder maxLines(int maxLines) {
            this.maxLines = maxLines;
            return this;
        }

        public TextLineHelper build() {
            if (minLines < 1) {
                this.minLines = 1;
            }

            if ((maxLines > 30) || (maxLines < 1)) {
                this.maxLines = 30;
            }
            if (maxLines <= minLines) {
                maxLines = minLines + 1;
            }
            if (max2MinDuration < 20) {
                max2MinDuration = 200;
            }
            if (min2MaxDuration < 20) {
                min2MaxDuration = 200;
            }
            return new TextLineHelper(this);
        }
    }
}

