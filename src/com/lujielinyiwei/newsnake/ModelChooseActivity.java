package com.lujielinyiwei.newsnake;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.debug.Debug;

import android.content.Intent;
import android.util.Log;

public class ModelChooseActivity extends SimpleBaseGameActivity {

	private BitmapTextureAtlas mTextureAtlas;
	private BitmapTextureAtlas mStartAtlas;

	private TextureRegion mGrassBackground;

	private TiledTextureRegion mTop;
	// private TiledTextureRegion mStartpage;
	private TiledTextureRegion mStart;
	private TiledTextureRegion mInformation;

	private Music mMusic;
	private Sound mExplosionSound;

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

		mTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 1024,
				512);
		mStartAtlas = new BitmapTextureAtlas(getTextureManager(), 1024, 1024,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		mGrassBackground = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mTextureAtlas, this,
						"image/beginbackground.jpg", 0, 0);
		mTop = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				mStartAtlas, this, "image/toplist.png", 0, 0, 3, 1);
		mStart = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				mStartAtlas, this, "image/begin.png", 0, 116, 3, 1);
		mInformation = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(mStartAtlas, this,
						"image/information.png", 0, 248, 3, 1);

		mTextureAtlas.load();
		mStartAtlas.load();

		// TODO Auto-generated method stub

		MusicFactory.setAssetBasePath("mfx/");
		SoundFactory.setAssetBasePath("mfx/");
		try {
			this.mMusic = MusicFactory.createMusicFromAsset(
					this.mEngine.getMusicManager(), this, "background.mp3");
			this.mMusic.setLooping(true);
			this.mExplosionSound = SoundFactory.createSoundFromAsset(
					this.mEngine.getSoundManager(), this, "button1.wav");

		} catch (final IOException e) {
			Debug.e(e);
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		try {
			ModelChooseActivity.this.mMusic.pause();
		} catch (Exception e) {

		}
	}

	@Override
	public void onResume() {
		super.onResume();
		try {
			ModelChooseActivity.this.mMusic.seekTo(0);
			ModelChooseActivity.this.mMusic.play();
		} catch (Exception e) {
			Log.e("Hello", e.toString());
		}
	}

	@Override
	protected Scene onCreateScene() {
		ModelChooseActivity.this.mMusic.play();
		this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene();
		final SpriteBackground mainSceneBackground = new SpriteBackground(
				new Sprite(0, 0, mGrassBackground,
						this.getVertexBufferObjectManager()));
		scene.setBackground(mainSceneBackground);
		scene.setTouchAreaBindingOnActionDownEnabled(true);

		ButtonSprite StartSprite = new ButtonSprite(0, 160, mStart,
				getVertexBufferObjectManager());
		ButtonSprite TopSprite = new ButtonSprite(0, 360, mTop,
				getVertexBufferObjectManager());
		ButtonSprite InformationSprite = new ButtonSprite(0, 260, mInformation,
				getVertexBufferObjectManager());

		StartSprite.setOnClickListener(new OnClickListener() {

			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				// TODO Auto-generated method stub
				ModelChooseActivity.this.mExplosionSound.play();
				Intent intent = new Intent();
				intent.setClass(ModelChooseActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
		TopSprite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				ModelChooseActivity.this.mExplosionSound.play();

				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ModelChooseActivity.this,
						SensorModelActivity.class);
				startActivity(intent);
			}
		});
		InformationSprite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				ModelChooseActivity.this.mExplosionSound.play();

				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ModelChooseActivity.this,
						SensorModelActivity.class);
				startActivity(intent);
			}
		});
		scene.registerTouchArea(StartSprite);
		scene.registerTouchArea(TopSprite);
		scene.registerTouchArea(InformationSprite);

		scene.attachChild(StartSprite);
		scene.attachChild(InformationSprite);
		scene.attachChild(TopSprite);
		// TODO Auto-generated method stub
		return scene;
	}

}
