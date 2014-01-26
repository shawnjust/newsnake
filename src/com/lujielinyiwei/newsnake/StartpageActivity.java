package com.lujielinyiwei.newsnake;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Stack;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.view.KeyEvent;

import com.lujielinyiwei.entity.scene.MyScene;
import com.lujielinyiwei.newsnake.scene.StartpageScene;

public class StartpageActivity extends SimpleBaseGameActivity {

	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;

	Stack<MyScene> sceneStack;

	public StartpageActivity() {
		sceneStack = new Stack<MyScene>();
		sceneStack.push(new StartpageScene(this));
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
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
		sceneStack.peek().onCreateResources();

	}

	@Override
	public void onPause() {
		super.onPause();
		sceneStack.peek().onLeave();

	}

	@Override
	public void onResume() {
		super.onResume();
		sceneStack.peek().onBack();

	}

	@Override
	protected Scene onCreateScene() {
		sceneStack.peek().onCreateScene();

		return sceneStack.peek();
	}

	public void startScene(Class<?> newScene) {
		try {
			Constructor<?> con = newScene
					.getConstructor(new Class[] { StartpageActivity.class });
			MyScene scene = (MyScene) con.newInstance(new Object[] { this });
			createNewScene(scene);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void createNewScene(MyScene scene) {
		MyScene priviousScene = sceneStack.peek();

		sceneStack.push(scene);
		sceneStack.peek().onCreateResources();
		sceneStack.peek().onCreateScene();

		StartpageActivity.this.getEngine().setScene(sceneStack.peek());

		priviousScene.onLeave();
		priviousScene.onUnloadResources();

	}

	private void popScene() {
		MyScene priviousScene = sceneStack.pop();
		MyScene currentScene = sceneStack.peek();

		currentScene.onLoadResources();

		StartpageActivity.this.getEngine().setScene(currentScene);

		priviousScene.onLeave();
		priviousScene.onUnloadResources();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (sceneStack.peek().onKeyDown(keyCode, event)) {
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (sceneStack.size() <= 1) {
				return super.onKeyDown(keyCode, event);
			} else {
				popScene();
				return true;
			}
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
}
