package com.myshroff.shakeshakecolor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.util.Log;
import android.view.Menu;
import android.graphics.Color;
import android.widget.Toast;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/* Margi - Testing Accelerator Sensor */

public class MainActivity extends Activity implements SensorEventListener{

	private SensorManager sensorManager;
	private boolean color = false;
	private View view;
	private long lastUpdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		view = findViewById(R.id.textView);
		view.setBackgroundColor(Color.WHITE);

		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		lastUpdate = System.currentTimeMillis();

		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			Log.i("ORIENTATION","LANDSCAPE");   
			 Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
			 
		}
		else
		{
			Log.i("ORIENTATION","POTRAIT");   
			 Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
		{
			getAccelerometer(event);
		}
		
		if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE)
		{
			getAccelerometer(event);
		}
	}

	private void getGyroscope(SensorEvent event)
	{
		
	}
	private void getAccelerometer(SensorEvent event) {
		float[] values = event.values;

		//Movement
		float x = values[0];
		float y = values[1];
		float z = values[2];

		float accelerationSqRoot = ( x *x + y * y + z *z ) 
				/ (Sensor.TYPE_GRAVITY * Sensor.TYPE_GRAVITY );

		long actualTime = System.currentTimeMillis();

		if(accelerationSqRoot >= 2)
		{
			if (actualTime - lastUpdate < 200) {
				return;
			}

			lastUpdate = actualTime;
			Toast.makeText(this, "Device was shuffed", Toast.LENGTH_SHORT)
			.show();


			if (color) {
				view.setBackgroundColor(Color.GREEN);

			} else {
				view.setBackgroundColor(Color.RED);
			}
			color = !color;
		}
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		sensorManager.registerListener(this, 
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 
				SensorManager.SENSOR_DELAY_NORMAL);
 
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			//Log.i("ORIENTATION","LANDSCAPE");   
			 Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
			//this.getResources().getConfiguration().orientation = Configuration.ORIENTATION_PORTRAIT;
			//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		else
		{
			//Log.i("ORIENTATION","POTRAIT");   
			 Toast.makeText(this, "potrait", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);

	    // Checks the orientation of the screen
	    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	        Toast.makeText(this, "OnConfigChanged - landscape", Toast.LENGTH_SHORT).show();
	       //here call activity A
	    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
	        Toast.makeText(this, "OnConfigChanged - portrait", Toast.LENGTH_SHORT).show();
	       //here call activity B

	    }
	}
	@Override
	protected void onPause()
	{
		super.onPause();
		sensorManager.unregisterListener(this);
		
	}

}
