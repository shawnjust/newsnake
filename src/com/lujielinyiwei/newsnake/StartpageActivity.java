package com.lujielinyiwei.newsnake;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.lujielinyiwei.entity.scene.MyScene;
import com.lujielinyiwei.newsnake.scene.StartpageScene;

public class StartpageActivity extends SimpleBaseGameActivity {

	MyScene currentScene = new StartpageScene(this);

	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;

	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		engineOptions.getAudioOptions().setNeedsSound(true);
		return engineOptions;
	}

	@Override
	protected void onCreateResources() {
		currentScene.onCreateResources();

	}

	@Override
	public void onPause() {
		super.onPause();
		currentScene.onLeave();

	}

	@Override
	public void onResume() {
		super.onResume();
		currentScene.onBack();

	}

	@Override
	protected Scene onCreateScene() {
		currentScene.onCreateScene();

		return currentScene;
	}

}
