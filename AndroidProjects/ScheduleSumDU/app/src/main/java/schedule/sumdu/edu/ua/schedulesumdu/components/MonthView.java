package schedule.sumdu.edu.ua.schedulesumdu.components;

import java.util.Date;
import java.util.GregorianCalendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MonthView extends View {
	private static final int MAX_SIZE_MEASURE = 1000;
	private static final String DAYS_OF_WEEK[] = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
	private static final int NUM_DAYS = 7;
	private static final int DIV_TEXT_SIZE = 15;
	private static final int DX_SQUARE = 3;
	private static final int DY_SQUARE = 3;
	
	private Paint mPaintTextDays, mPaintTextDigit, mPaintChecked;
	private int mBeginDay, mMaxDays, mCheckedDay;
	private int mWidthSq, mHeightSq;
	private Date mCurrentYearMonth;
	
	public MonthView(Context context) {
		super(context);
		initComponent();
	}
	
	public MonthView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initComponent();
	}

	public MonthView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initComponent();
	}

	private void initComponent() {
		mBeginDay = 0;
		mMaxDays = 31;
		mCheckedDay = -1;
		
		mPaintTextDays = new Paint();
		mPaintTextDays.setColor(Color.BLUE);
		
		mPaintTextDigit = new Paint();
		mPaintTextDigit.setColor(Color.BLACK);
		
		mPaintChecked = new Paint();
		mPaintChecked.setColor(Color.GREEN);
	}
	
	private int measure(int dimens)	{
		int specMode = MeasureSpec.getMode(dimens);
		int specSize = MeasureSpec.getSize(dimens);
		
		if (specMode == MeasureSpec.UNSPECIFIED) return MAX_SIZE_MEASURE;
			else return specSize;
	}
	
	@Override
	protected void onMeasure(int widthMeasure,int heightMeasure) {
		int wM = measure(widthMeasure);
		int hM = measure(heightMeasure);
		
		int sizeText = hM/DIV_TEXT_SIZE;
		mPaintTextDays.setTextSize(sizeText);
		mPaintTextDigit.setTextSize(sizeText);
		mPaintChecked.setTextSize(sizeText+5);
		
		setMeasuredDimension(wM,hM);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int maxX = this.getMeasuredWidth();
		int maxY = this.getMeasuredHeight();
		
		mWidthSq = maxX / NUM_DAYS;
		mHeightSq = maxY / NUM_DAYS;
		
		for (int i=0;i<NUM_DAYS;i++) 
			canvas.drawText(DAYS_OF_WEEK[i], i*mWidthSq+DX_SQUARE,
					mHeightSq-DY_SQUARE, mPaintTextDays);
		
		int pointerDay = 0;
		for (int iy=1;iy<NUM_DAYS;iy++)
			for (int ix=0;ix<NUM_DAYS;ix++) {
				int day = pointerDay - mBeginDay;
				
				if ((day >= 0)&&(day < mMaxDays)) {
					String textDigit = String.valueOf(day+1);
					if (textDigit.length() < 2) textDigit = " " + textDigit;
					
					if (day == mCheckedDay)
						canvas.drawText(textDigit, ix*mWidthSq+DX_SQUARE,
								iy*mHeightSq+mHeightSq-DY_SQUARE, mPaintChecked);
					else
						canvas.drawText(textDigit, ix*mWidthSq+DX_SQUARE,
								iy*mHeightSq+mHeightSq-DY_SQUARE, mPaintTextDigit);
				}
								
				pointerDay++;
			}
	}
	
	public void setDateM(Date d) {
		mCurrentYearMonth = d;
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(d);
		
		mMaxDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		
		if (mCheckedDay >= mMaxDays) mCheckedDay = mMaxDays - 1;
		if (mCheckedDay < 0) mCheckedDay = cal.get(GregorianCalendar.DAY_OF_MONTH)-1;
		
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
		mBeginDay = cal.get(GregorianCalendar.DAY_OF_WEEK)-1;
		
		this.postInvalidate();
	}
	
	public Date getDateM() {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(mCurrentYearMonth);
		if ((mCheckedDay>=0)&&(mCheckedDay<mMaxDays))
			cal.set(GregorianCalendar.DAY_OF_MONTH, mCheckedDay+1);
		return cal.getTime();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent me) {
		if (me.getAction() == MotionEvent.ACTION_DOWN) {
			int xCanvas = (int) (me.getX()/mWidthSq);
			int yCanvas = (int) ((me.getY()/mHeightSq)-1);
			int day = yCanvas*NUM_DAYS + xCanvas - mBeginDay;
			
			if ((day >= 0)&&(day < mMaxDays)) {
				mCheckedDay = day;
				mCurrentYearMonth = getDateM();
				this.postInvalidate();
			}
		}
		
		return true;
	}
}
