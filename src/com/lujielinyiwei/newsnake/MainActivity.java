package com.lujielinyiwei.newsnake;

import java.util.Stack;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.lujielinyiwei.entity.scene.MyScene;
import com.lujielinyiwei.newsnake.scene.BaseGameScene;

public class MainActivity extends SimpleBaseGameActivity {

	public static final int CAMERA_WIDTH = 800;
	public static final int CAMERA_HEIGHT = 480;

	private Camera camera;

	BaseGameScene baseGameScene;
//	baseGameScene = new BaseGameScene(this);

	

	@Override
	public EngineOptions onCreateEngineOptions() {
		this.camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		engineOptions.getAudioOptions().setNeedsSound(true);
		return engineOptions;
	}

	@Override
	public void onPause() {
		super.onPause();
		if (baseGameScene != null) {
			baseGameScene.onLeave();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (baseGameScene != null) {
			baseGameScene.onBack();
		}
	}

	@Override
	public void onCreateResources() {
		baseGameScene.onCreateResources();
	}

	@Override
	protected Scene onCreateScene() {
		baseGameScene.onCreateScene();

		return baseGameScene;
	}

	interface MyIUpdateHandler extends IUpdateHandler {
		public void enable();

		public void disable();
	}
}
