package github.alexcheung.viewlib;
import github.wyouflf.xutil.util.LogUtils;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.alex.pressview.R;
@SuppressLint({ "DrawAllocation", "Recycle" })
public class PressView extends View
{
	private Context context;
	/**控件的宽度*/
	private float pvWidth;
	/**控件的高度*/
	private float pvheigth;
	/**控件圆角化的半径*/
	private float pvCorner;
	private float textPadding;
	/**字体色*/
	private int textColor;
	/**字体大小*/
	private float textSize;
	private int textBackground;
	private Paint paint;
	private String textMsg;
	private RectF rect;
	public PressView(Context context) {
		super(context);
		this.context = context;
	}
	public PressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initPressView(attrs);
	}
	public PressView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		initPressView(attrs);
	}
	private void initPressView(AttributeSet attrs)
	{
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PressView);
		textPadding = array.getDimension(R.styleable.PressView_textPadding, R.dimen.textPadding_default);
		textBackground = array.getColor(R.styleable.PressView_textBackground, R.color.textBackground_default);
		textColor = array.getColor(R.styleable.PressView_textColor, R.color.textColor_default);
		textSize = array.getDimension(R.styleable.PressView_textSize, R.dimen.textSize_default);
		textMsg = array.getString(R.styleable.PressView_textMsg);
		array.recycle();
		LogUtils.e("textSize = "+textSize);
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		paint = new Paint();
		paint.setColor(textBackground);
		paint.setStyle(Paint.Style.FILL);
		pvWidth = 300;
		pvheigth = 200;
		pvCorner = 20;
		rect = new RectF(0, 0, textMsg.length()+textPadding, textMsg.length()+textPadding);
		canvas.drawRoundRect(rect, pvCorner, pvCorner, paint);
		//画文字：开始＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
		paint.setTextAlign(Align.CENTER);
		paint.setTextSize(textSize);
		paint.setColor(textColor);
		paint.setTextAlign(Paint.Align.CENTER);  
		if(textMsg != null){
			canvas.drawText(textMsg, 50, 50, paint);
		}
		//画文字：结束＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(measureHeight(widthMeasureSpec), measureHeight(heightMeasureSpec));  
	}
	private int measureHeight(int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int result=0; //结果
		int specMode=MeasureSpec.getMode(heightMeasureSpec);
		int specSize=MeasureSpec.getSize(heightMeasureSpec);
		switch (specMode) {
		case MeasureSpec.AT_MOST:  // 子容器可以是声明大小内的任意大小
			LogUtils.e("子容器可以是声明大小内的任意大小");
			LogUtils.e("大小为:"+specSize);
			result=specSize;
			break;
		case MeasureSpec.EXACTLY: //父容器已经为子容器设置了尺寸,子容器应当服从这些边界,不论子容器想要多大的空间.  比如EditTextView中的DrawLeft
			LogUtils.e("父容器已经为子容器设置了尺寸,子容器应当服从这些边界,不论子容器想要多大的空间");
			LogUtils.e("大小为:"+specSize);
			result=specSize;
			break;
		case MeasureSpec.UNSPECIFIED:  //父容器对于子容器没有任何限制,子容器想要多大就多大. 所以完全取决于子view的大小
			LogUtils.e("父容器对于子容器没有任何限制,子容器想要多大就多大");
			LogUtils.e("大小为:"+specSize);
			result=1500;
			break;
		default:
			break;
		}
		return result;
	}
}
