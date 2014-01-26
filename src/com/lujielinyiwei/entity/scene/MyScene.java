package com.lujielinyiwei.entity.scene;

import org.andengine.entity.scene.Scene;

import android.view.KeyEvent;

import com.lujielinyiwei.newsnake.StartpageActivity;

public abstract class MyScene extends Scene {

	protected final StartpageActivity mContext;

	public MyScene(StartpageActivity context) {
		this.mContext = context;
	}

	public StartpageActivity getContext() {
		return this.mContext;
	}

	public abstract void onCreateResources();

	public void onLoadResources() {

	}

	public void onUnloadResources() {
	}

	public abstract void onLeave();

	public abstract void onBack();

	public abstract void onCreateScene();

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}
}
