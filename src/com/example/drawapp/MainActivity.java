package com.example.drawapp;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	private DrawingBoard drawingView;
	private ImageButton nowColor;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//drawing view
		drawingView = (DrawingBoard)findViewById(R.id.blankdrawing);
		
		//get color palette and the default color
		LinearLayout colorLayout = (LinearLayout)findViewById(R.id.color_bar);	
		//get the first color as default color
		nowColor = (ImageButton)colorLayout.getChildAt(0);
		//set a new style for selected color
		nowColor.setImageDrawable(getResources().getDrawable(R.drawable.selected_color));
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void colorClicked(View view){
		
	}
}
