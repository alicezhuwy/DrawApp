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
import android.view.View.OnTouchListener;

public class DrawingBoard extends View implements OnTouchListener{
	
	//Drawing path
	private Path drawingPath;
	//Drawing and canvas paint
	private Paint drawingPaint, canvasPaint;
	//Set default color
	private int defaultColor = 0xFFFF6666;
	//Canvas, the user paths drawn with drawPaint will be drawn here
	private Canvas drawingCanvas;
	//Canvas bitmap
	private Bitmap canvasBitmap;
	//use lastBrushSize to keep tracking when the user switches to the eraser
	//and then back to use the brush to draw
	private float brushSize, lastBrushSize;
	//eraser
	public boolean erase = false;
	

//	//	//Because startDrawing is private method, then create a constructor to assign
//	//value to it. name same as class.
	public DrawingBoard(Context context, AttributeSet attrs){
		super(context, attrs);
		startDrawing();
	}

	//Setup everything for drawing
	private void startDrawing(){
		
		setOnTouchListener(this);
		drawingPath = new Path();
		//Paint flag that enables dithering when blitting.
		drawingPaint = new Paint(Paint.DITHER_FLAG);
		
		//instantiate brushSize and lastBrushSize
		brushSize = getResources().getDimension(R.dimen.small_brush);
		lastBrushSize = brushSize;
		drawingPaint.setStrokeWidth(brushSize);
		
		//methods of "Paint"
		//Set initial color, style and stroke of paint
		drawingPaint.setColor(defaultColor);
		//smooth out the edges
		drawingPaint.setAntiAlias(true);
		drawingPaint.setStrokeWidth(20);
		drawingPaint.setStyle(Paint.Style.STROKE);
		drawingPaint.setStrokeJoin(Paint.Join.ROUND);
		drawingPaint.setStrokeCap(Paint.Cap.ROUND);
		
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
	public boolean onTouch(View v, MotionEvent event) {
		//obtain touch point from user
		float fingerX = event.getX();
		float fingerY = event.getY();
		//draw shape button 3,4,5 
		//square
		Paint shapePaint = new Paint();
		shapePaint.setColor(defaultColor);
		shapePaint.setStyle(Paint.Style.FILL);
//		v.getId() != R.id.square_btn && v.getId() != R.id.circle_btn && v.getId() != R.id.triangle_btn
		
//		set condition when user has specific action

		if (v.getId() == R.id.square_btn){ 
			drawingCanvas.drawRect(fingerX, fingerY, fingerX+40, fingerY+40, shapePaint);	
		}
		
		//draw circle
		else if (v.getId() == R.id.circle_btn){
			drawingCanvas.drawCircle(fingerX, fingerY, 20, shapePaint);
		}
		
		//draw triangle
		else if (v.getId() == R.id.triangle_btn){
			Path path1 = new Path();
			path1.moveTo(fingerX, fingerY);
			path1.lineTo(fingerX+40, fingerY);
			path1.lineTo(fingerX+20, fingerY-34);
			path1.close();
			drawingCanvas.drawPath(path1, shapePaint);
		}
		
		else {
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
		}
		
			
		//invalidate and redraw,call invalidate and cause the onDraw to execute
		invalidate();
		return true;
	}
	
	
	//This is called during layout when the size of this view has changed.
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh){
		super.onSizeChanged(w, h, oldw, oldh);
		canvasBitmap = Bitmap.createBitmap(w, h,Bitmap.Config.ARGB_8888);
		drawingCanvas = new Canvas(canvasBitmap);
	}
	
	
	//define methods for function start here 
	//function bar, button 1, start a new draw
	public void startNewDrawing(){
		drawingCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
		invalidate();
	}
	
	//call by Main Activity, and reset color
	public void selectColor(String newColor){
		invalidate();
		defaultColor = Color.parseColor(newColor);
		drawingPaint.setColor(defaultColor);		
	}
	
	public void setSize(float newBrushSize){
		//Converts an unpacked complex data value holding a dimension to its final floating 
		//point value.
		//update the brush size with the passed value
		float brushPxUnit = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newBrushSize, getResources().getDisplayMetrics());
		brushSize = brushPxUnit;
		//paint use the brush we choose before
		drawingPaint.setStrokeWidth(brushSize);		
	}
	
	//to get the other size variable
	public void setLastSize(float lastSize){
		lastBrushSize = lastSize;
	}
	
	public float getLastBrushSize(){
		return lastBrushSize;
	}
		
	//erase method
	public void setErase(boolean isErase){
		//update the flag variable
		erase = isErase;
		if(erase) drawingPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		else drawingPaint.setXfermode(null);
	}
	

}

