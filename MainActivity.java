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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	private DrawingBoard drawingView;
	private ImageButton nowColor, brushBtn;
	private float smallBrush, mediumBrush, largeBrush;


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
		smallBrush = getResources().getInteger(R.integer.small_size);
		mediumBrush = getResources().getInteger(R.integer.medium_size);
		largeBrush = getResources().getInteger(R.integer.large_size);
		
		//set up the image button of the brush button and use onClickListener to monitor
		//find id in main layout xml
		brushBtn = (ImageButton)findViewById(R.id.brush_btn);
		brushBtn.setOnClickListener(this);
				
	}
	
	private void initialView() {
		
	}
	
	//use choose the color
	public void colorClicked(View view){
		//checked if the color that the user chosen is the default color 
		
		if(view != nowColor){
			ImageButton imgView = (ImageButton)view;
			String color = view.getTag().toString();
			System.out.println(color);
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
		//if the brush is clicked
		if(view.getId() == R.id.brush_btn){
			final Dialog brushDialog = new Dialog(this);
			brushDialog.setTitle("Brush Size");
			brushDialog.setContentView(R.layout.brush_dialog);		
		}
	}
}
