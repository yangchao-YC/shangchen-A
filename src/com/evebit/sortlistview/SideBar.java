package com.evebit.sortlistview;

import com.evebit.shangchen.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * author tianbing*/
public class SideBar extends View {
	//触摸事件
	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	
	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(String s);
	}
	
	// 26个字母
	public static String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
				"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z", "#" };
	private int choose = -1;// 选中
	private Paint paint = new Paint();
	
	//定义一个textView 用来显示  选中空间的文本
	private TextView mTextDialog;
	
	public void setTextView(TextView mTextDialog) {
		this.mTextDialog = mTextDialog;
	}
	
	
	//三个构造方法分别对应不同情境
	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SideBar(Context context) {
		super(context);
	}
	
	/**
	 * 重写这个方法
	 */
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 获取焦点改变背景颜色.
		int height = getHeight();// 获取对应高度
		int width = getWidth(); // 获取对应宽度
		int singleHeight = height / b.length;// 获取每一个字母的高度

		for (int i = 0; i < b.length; i++) {
			paint.setColor(Color.rgb(33, 65, 98));
			// paint.setColor(Color.WHITE);
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			paint.setTextSize(20);
			// 选中的状态
			if (i == choose) {
				paint.setColor(Color.parseColor("#3399ff"));
				paint.setFakeBoldText(true);
			}
			// x坐标等于中间-字符串宽度的一半.
			float xPos = width / 2 - paint.measureText(b[i]) / 2;
			float yPos = singleHeight * i + singleHeight;
			canvas.drawText(b[i], xPos, yPos, paint);
			paint.reset();// 重置画笔
		}

	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();//获取这个时间的动作类型，或者叫方式
		final float y = event.getY();// 点击y坐标，这里是因为 我指在意需要y坐标的位置
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) (y / getHeight() * b.length);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.
		
		//不同动作类型对应不同的  触发事件
		switch (action) {
		case MotionEvent.ACTION_UP:
			setBackgroundDrawable(new ColorDrawable(0x00000000));
			choose = -1;//
			invalidate();
			if (mTextDialog != null) {
				mTextDialog.setVisibility(View.INVISIBLE);
			}
			break;

			//注意这里的代码代表什么意思，没有被选中的话  应该如何表现
		default:
			setBackgroundResource(R.drawable.sidebar_background);
			if (oldChoose != c) {
				if (c >= 0 && c < b.length) {
					if (listener != null) {
						listener.onTouchingLetterChanged(b[c]);
					}
					if (mTextDialog != null) {
						mTextDialog.setText(b[c]);
						mTextDialog.setVisibility(View.VISIBLE);
					}
					
					choose = c;
					invalidate();
				}
			}

			break;
		}
		return true;
	}
	
	/**
	 * 向外公开的方法
	 * 
	 * @param onTouchingLetterChangedListener
	 */
	
	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}
	
	
	
	
}
