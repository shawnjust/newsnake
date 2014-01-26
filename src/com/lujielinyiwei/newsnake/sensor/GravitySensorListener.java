package com.lujielinyiwei.newsnake.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class GravitySensorListener implements SensorEventListener {

	private SensorEventListener listener = null;

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		if (this.listener != null) {
			this.listener.onAccuracyChanged(sensor, accuracy);
		}
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if (this.listener != null) {
			this.listener.onSensorChanged(event);
		}
	}

	public void setListener(SensorEventListener listener) {
		this.listener = listener;
	}
	
	public void clearListener(){
		this.listener = null;
	}

}
