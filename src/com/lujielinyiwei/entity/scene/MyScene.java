package com.lujielinyiwei.entity.scene;

import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;

public abstract class MyScene extends Scene {

	protected final SimpleBaseGameActivity mContext;

	public MyScene(SimpleBaseGameActivity context) {
		this.mContext = context;
	}

	public SimpleBaseGameActivity getContext() {
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
}
