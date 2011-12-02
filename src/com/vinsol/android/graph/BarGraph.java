package com.vinsol.android.graph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.View;

public class BarGraph extends View {

	private Paint paint;
	private Double max;
	private Double[] values;
	private int height;
	private int width;
	private int verDiff;
	private int horDiff;
	private String[] horLabels;
	
	public BarGraph(Context context,Double[] _values,String[] _horLabels,String title) {
		super(context);
		values = _values;
		paint = new Paint();
		horLabels = _horLabels;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		max = getMax();
		height = getHeight();
		width = getWidth();
		paint.setColor(Color.parseColor("#000000"));
		drawVerticalLine(canvas);
		drawHorinzontalLine(canvas);
		drawVerticalLabels(canvas);
		drawGraph(canvas);
	}
	
	private void drawVerticalLabels(Canvas canvas) {
		int originX = (int) ((15.83/100)*width);
		int topY = (int) ((Double)(85.76/100)*height);
		int interval = (int) (max/5);
		int value = 0;
		paint.setTextAlign(Align.RIGHT);
		paint.setTextSize(16.0f);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		for(int i=0;i<6;i++){
			canvas.drawText(value+"", originX-5, topY+5, paint);
			value = value + interval;
			topY = (int) (topY-(verDiff/5));
		}
		paint = new Paint();
		paint.setColor(Color.parseColor("#000000"));
	}

	private void drawGraph(Canvas canvas) {
		int barWidth = (int) ((6.04/100)*width);
		int topY = (int) ((Double)(85.76/100)*height);
		int originX = (int) ((15.83/100)*width);
		int interval = (int) (horDiff/values.length);
		int value = 0;
		int finalValue;
		Paint textPaint = new Paint();
		textPaint.setTextAlign(Align.RIGHT);
		textPaint.setTextSize(16.0f);
		textPaint.setTypeface(Typeface.DEFAULT_BOLD);
		
		for(int i = 0 ;i<values.length;i++){
			finalValue = value+originX+barWidth;
			value = value + interval;
			RectF mRectF = new RectF(originX+value, topY-(int)((values[i]/max)*verDiff), finalValue, topY);
			canvas.drawRect(mRectF,paint);
			canvas.drawText(horLabels[i], originX+value, topY+20, textPaint);
		}
	}

	private void drawHorinzontalLine(Canvas canvas) {
		int originX = (int) ((15.83/100)*width);
		int originY = (int) ((Double)(85.76/100)*height);
		int rightX = (int) ((93.75/100)*width);
		int rightY = originY;
		horDiff = rightX - originX;
		canvas.drawLine(originX, originY, rightX, rightY, paint);
	}

	private void drawVerticalLine(Canvas canvas){
		int originX = (int) ((15.83/100)*width);
		int originY = (int) ((9.0/100)*height);
		int topX = originX;
		int topY = (int) ((Double)(85.76/100)*height);
		verDiff = topY - originY;
		canvas.drawLine(originX, originY, topX, topY, paint);
	}
	private Double getMax() {
		Double largest = 0.0;
		for (int i = 0; i < values.length; i++)
			if (values[i] > largest)
				largest = values[i];
		return largest;
	}
}