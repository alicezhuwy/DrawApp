package com.example.drawapp;

import java.util.UUID;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	private DrawingBoard drawingView;
	private ImageButton nowColor, brushBtn, eraseBtn, newBtn, exitBtn, recBtn, cirBtn, triBtn;
	private float smallSize, mediumSize, largeSize;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//retrieve layout from xml file, set "activity_main" as main layout
		setContentView(R.layout.activity_main);
		
		//drawing view， retrieve the blank draw defined in layout
		drawingView = (DrawingBoard)findViewById(R.id.blankdrawing);
		
		//get color palette and the default color, retrieve from layout
		LinearLayout colorLayout = (LinearLayout)findViewById(R.id.color_bar);	
		
		//get the first color as default color
		nowColor = (ImageButton)colorLayout.getChildAt(0);
		
		//set a new style for selected color
		nowColor.setImageDrawable(getResources().getDrawable(R.drawable.selected_color));
				
		//store dimension values of three brush
		smallSize = getResources().getInteger(R.integer.small_size);
		mediumSize = getResources().getInteger(R.integer.medium_size);
		largeSize = getResources().getInteger(R.integer.large_size);
	
		//set the initial brush size
		drawingView.setSize(mediumSize);
		
		//set up the image button of the brush button and use onClickListener to monitor
		//find id in main layout xml
		brushBtn = (ImageButton)findViewById(R.id.brush_btn);
		brushBtn.setOnClickListener(this);
		
		//set up the image button of the erase and use onClickListener to monitor it
		eraseBtn = (ImageButton)findViewById(R.id.erase_btn);
		eraseBtn.setOnClickListener(this);
		
		//start a new draw
		newBtn = (ImageButton)findViewById(R.id.new_btn);
		newBtn.setOnClickListener(this);
		
		//Exit app
		exitBtn = (ImageButton)findViewById(R.id.exit_btn);
		exitBtn.setOnClickListener(this);
		
		//set up the rectangle button
		recBtn = (ImageButton)findViewById(R.id.square_btn);
		recBtn.setOnClickListener(this);
		
		//set up the circle button
		cirBtn = (ImageButton)findViewById(R.id.circle_btn);
		cirBtn.setOnClickListener(this);
		
		//set up the triganle button
		triBtn = (ImageButton)findViewById(R.id.triangle_btn);
		triBtn.setOnClickListener(this);
		
	}
	
	
	//use choose the color
	public void colorClicked(View view){
		//when user choose color, erase function will not be called
		drawingView.setErase(false);
		
		//retrieve the last brush size after user using eraser
		drawingView.setSize(drawingView.getLastBrushSize());
		
		//checked if the color that the user chosen is the default color 
		if(view != nowColor){
			ImageButton imgView = (ImageButton)view;
			String color = view.getTag().toString();
			drawingView.selectColor(color);	
		
		imgView.setImageDrawable(getResources().getDrawable(R.drawable.selected_color));
		nowColor.setImageDrawable(getResources().getDrawable(R.drawable.colors));
		nowColor=(ImageButton)view;
		}
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub, response to the brushBtn
		//Add a new xml file in layout for the dialog
		
		//function bar, button 1, start a new drawing
		if(view.getId() == R.id.new_btn){
			AlertDialog.Builder newDrawingDialog = new AlertDialog.Builder(this);
			newDrawingDialog.setTitle("New Drawing");
			newDrawingDialog.setMessage("Start new drawing, and you will lose the current drawing, are you sure?");
			newDrawingDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					drawingView.startNewDrawing();
					dialog.dismiss();			
				}
			});
			newDrawingDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});	
			newDrawingDialog.show();
		}
		
		//function bar, button 2, choose a brush size, conditional statement for the brush button
		else if(view.getId() == R.id.brush_btn){
			final Dialog brushDialog = new Dialog(this);
			brushDialog.setTitle("Brush Size");
			brushDialog.setContentView(R.layout.size_dialog);
			
			//listen clicks on the three size buttons
			//small brush
			ImageButton smallBtn = (ImageButton)brushDialog.findViewById(R.id.small_size);
			smallBtn.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					drawingView.setSize(smallSize);
					drawingView.setLastSize(smallSize);
					drawingView.setErase(false);
					brushDialog.dismiss();
				}
			});
			
			//medium brush
			ImageButton mediumBtn = (ImageButton)brushDialog.findViewById(R.id.medium_size);
			mediumBtn.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					drawingView.setSize(mediumSize);
					drawingView.setLastSize(mediumSize);
					//set back to drawing in case they erased before
					drawingView.setErase(false);
					brushDialog.dismiss();
				}
			});
			
			//large brush
			ImageButton largeBtn = (ImageButton)brushDialog.findViewById(R.id.large_size);
			largeBtn.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					drawingView.setSize(largeSize);
					drawingView.setLastSize(largeSize);
					drawingView.setErase(false);
					brushDialog.dismiss();
				}
			});
			
			//display the brush dialog
			brushDialog.show();
		}
		
		

		
		
		//function bar, button 6, conditional statement for the erase button 
		else if (view.getId() == R.id.erase_btn){
			final Dialog eraserDialog = new Dialog(this);
			eraserDialog.setTitle("Eraser Size");
			eraserDialog.setContentView(R.layout.size_dialog);
			
			//small size
			ImageButton smallBtn = (ImageButton)eraserDialog.findViewById(R.id.small_size);
			smallBtn.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					drawingView.setErase(true);
					drawingView.setSize(smallSize);
					eraserDialog.dismiss();
				}
			});
			
			//medium size
			ImageButton mediumBtn = (ImageButton)eraserDialog.findViewById(R.id.medium_size);
			mediumBtn.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					drawingView.setErase(true);
					drawingView.setSize(mediumSize);
					eraserDialog.dismiss();
				}
			});
			
			//large size
			ImageButton largeBtn = (ImageButton)eraserDialog.findViewById(R.id.large_size);
			largeBtn.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					drawingView.setErase(true);
					drawingView.setSize(largeSize);
					eraserDialog.dismiss();
				}
			});
			
			//display eraser dialog
			eraserDialog.show();
		}
		
		
		//exit button
		else if (view.getId() == R.id.exit_btn){
			AlertDialog.Builder exitDialog = new AlertDialog.Builder(this);
			exitDialog.setTitle("Exit Let's Draw");
			exitDialog.setMessage("Are you sure to exit?");
			exitDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					dialog.dismiss();
					finish();
					System.exit(0);
				}
			});
			
			exitDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					dialog.cancel();
				}
			});
			exitDialog.show();
		}

		//function bar, button 3, draw circle
		else if (view.getId() == R.id.circle_btn){
			drawingView.setId(R.id.circle_btn);
		}
		
		//function bar, button 4, draw rectangle
		else if (view.getId() == R.id.square_btn){
			drawingView.setId(R.id.square_btn);
		}
		
//		//function bar, button 5, draw triangle
		else if (view.getId() == R.id.triangle_btn){
			drawingView.setId(R.id.triangle_btn);
		}
		
	}
	
	
}
