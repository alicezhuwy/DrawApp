package com.example.drawapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.TypedValue;

public class DrawingBoard extends View {
	
	//Drawing path
	private Path drawingPath;
	//Drawing and canvas paint
	private Paint drawingPaint, canvasPaint;
	//Set default color
	private int defaultColor = 0xFFFF0000;
	//Canvas, the user paths drawn with drawPaint will be drawn here
	private Canvas drawingCanvas;
	//Canvas bitmap
	private Bitmap canvasBitmap;
	//use lastBrushSize to keep tracking when the user switches to the eraser
	//and then back to use the brush to draw
	private float brushSize, lastBrushSize;

	
	//Because startDrawing is private method, then create a constructor to assign
	//value to it. name same as class.
	public DrawingBoard(Context context, AttributeSet attrs){
		super(context, attrs);
		startDrawing();
	}

	//Setup everything for drawing
	private void startDrawing(){
		drawingPath = new Path();
		drawingPaint = new Paint();
		
		//instantiate brushSize and lastBrushSize
//		brushSize = getResources().getDimension(R.dimen.medium_brush);
//		lastBrushSize = brushSize;
//		drawingPaint.setStrokeWidth(brushSize);
		
		//methods of "Paint"
		//Set initial color, style and stroke of paint
		drawingPaint.setColor(defaultColor);
		//smooth out the edges
		drawingPaint.setAntiAlias(true);
		drawingPaint.setStrokeWidth(20);
		drawingPaint.setStyle(Paint.Style.STROKE);
		drawingPaint.setStrokeJoin(Paint.Join.ROUND);
		drawingPaint.setStrokeCap(Paint.Cap.ROUND);
		//Paint flag that enables dithering when blitting.
		canvasPaint = new Paint(Paint.DITHER_FLAG);				
	}
	
	
	//This is called during layout when the size of this view has changed.
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh){
		super.onSizeChanged(w, h, oldw, oldh);
		canvasBitmap = Bitmap.createBitmap(w, h,Bitmap.Config.ARGB_8888);
		drawingCanvas = new Canvas(canvasBitmap);
	}
	
	
	//let the above works, each time the user draws, it will validate the view, 
	//causing the "onDraw" method to executed 
	@Override
	protected void onDraw(Canvas canvas){
		//draw on specific bitmap
		canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
		//draw on specific path using specific paint
		canvas.drawPath(drawingPath, drawingPaint);
	}
	
	
	//get touch input from user
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//obtain touch point from user
		float fingerX = event.getX();
		float fingerY = event.getY();
		
		//set condition when user has specific action
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			drawingPath.moveTo(fingerX, fingerY);
			break;
		case MotionEvent.ACTION_MOVE:
			drawingPath.lineTo(fingerX, fingerY);
			break;
		//record the draw when user release finger
		case MotionEvent.ACTION_UP:
			drawingCanvas.drawPath(drawingPath, drawingPaint);
			drawingPath.reset();
			break;
		default:
			return false;
		}
		
		//invalidate and redraw,call invalidate and cause the onDraw to execute
		invalidate();
		return true;
	}
	
	
	//call by Main Activity, and reset color
	public void selectColor(String newColor){
		invalidate();
		defaultColor = Color.parseColor(newColor);
		drawingPaint.setColor(defaultColor);		
	}
	
//	public void setBrushSize(float newBrushSize){
//		//Converts an unpacked complex data value holding a dimension to its final floating 
//		//point value.
//		//update the brush size with the passed value
//		float brushPxUnit = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newBrushSize, getResources().getDisplayMetrics());
//		brushSize = brushPxUnit;
//		drawingPaint.setStrokeWidth(brushSize);		
//	}
//	
//	//to get the other size variable
//	public void setLastBrushSize(float lastSize){
//		lastBrushSize = lastSize;
//	}
//	
//	public float getLastBrushSize(){
//		return lastBrushSize;
//	}
//	
//	public void startNew(){
//		
//	}
}

