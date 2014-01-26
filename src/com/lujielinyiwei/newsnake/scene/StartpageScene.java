package com.lujielinyiwei.newsnake.scene;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
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
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseBackIn;
import org.andengine.util.modifier.ease.EaseBackOut;
import org.andengine.util.modifier.ease.EaseExponentialIn;
import org.andengine.util.modifier.ease.EaseExponentialOut;

import android.content.Intent;
import android.util.Log;

import com.lujielinyiwei.entity.scene.MyScene;
import com.lujielinyiwei.newsnake.InforPageActivity;
import com.lujielinyiwei.newsnake.InformationPageActivity;
import com.lujielinyiwei.newsnake.SensorModelActivity;
import com.lujielinyiwei.newsnake.StartpageActivity;

public class StartpageScene extends MyScene {

	public StartpageScene(StartpageActivity context) {
		super(context);
	}

	private BitmapTextureAtlas mTextureAtlas;
	private BitmapTextureAtlas mStartAtlas;
	private BitmapTextureAtlas mModelChoseAtlas;

	private TextureRegion mGrassBackground;

	private TiledTextureRegion mTop;
	// private TiledTextureRegion mStartpage;
	private TiledTextureRegion mStart;
	private TiledTextureRegion mInformation;
	private TiledTextureRegion mRockerModel;
	private TiledTextureRegion mGravityModel;
	private TiledTextureRegion mBackModel;
	private TextureRegion mSnakeTitle;

	private Music mMusic;
	private Sound mExplosionSound;

	ButtonSprite startSprite;
	ButtonSprite topSprite;
	ButtonSprite informationSprite;
	Sprite snakeSprite;
	ButtonSprite rockerModelSprite;
	ButtonSprite gravityModelSprite;
	ButtonSprite backModelSprite;

	boolean hasInitialized = false;

	public boolean isHasInitialized() {
		return hasInitialized;
	}

	public void setHasInitialized(boolean hasInitialized) {
		this.hasInitialized = hasInitialized;
	}

	@Override
	public void onCreateResources() {
		this.mTextureAtlas = new BitmapTextureAtlas(getContext()
				.getTextureManager(), 1024, 512, TextureOptions.DEFAULT);
		this.mStartAtlas = new BitmapTextureAtlas(getContext()
				.getTextureManager(), 1024, 1024, TextureOptions.DEFAULT);

		this.mGrassBackground = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mTextureAtlas, getContext(),
						"image/beginbackground.jpg", 0, 0);
		this.mTop = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(mStartAtlas, getContext(),
						"image/toplist.png", 0, 0, 3, 1);
		this.mStart = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(mStartAtlas, getContext(),
						"image/begin.png", 0, 116, 3, 1);
		this.mInformation = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(mStartAtlas, getContext(),
						"image/information.png", 0, 248, 3, 1);
		this.mTextureAtlas.load();
		this.mStartAtlas.load();

		this.mModelChoseAtlas = new BitmapTextureAtlas(getContext()
				.getTextureManager(), 2048, 1024,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		this.mRockerModel = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(mModelChoseAtlas, getContext(),
						"image/modelchoserocker.png", 0, 0, 3, 1);
		this.mGravityModel = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(mModelChoseAtlas, getContext(),
						"image/modelchosegravity.png", 0, 356, 3, 1);
		this.mBackModel = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(mModelChoseAtlas, getContext(),
						"image/modelchoseback.png", 0, 712, 3, 1);
		this.mSnakeTitle = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mModelChoseAtlas, getContext(),
						"image/snake.png", 1092, 0);
		this.mModelChoseAtlas.load();

		MusicFactory.setAssetBasePath("mfx/");
		SoundFactory.setAssetBasePath("mfx/");
		try {
			this.mMusic = MusicFactory.createMusicFromAsset(getContext()
					.getEngine().getMusicManager(), getContext(),
					"background.mp3");
			this.mMusic.setLooping(true);
			this.mExplosionSound = SoundFactory.createSoundFromAsset(
					getContext().getEngine().getSoundManager(), getContext(),
					"button1.wav");
		} catch (final IOException e) {
			Debug.e(e);
		}
	}

	@Override
	public void onLeave() {
		if (isHasInitialized()) {
			try {
				this.mMusic.pause();
			} catch (Exception e) {

			}
			initializeBeginButtonPosition();
			initializeModelButtonPosition();
		}
	}

	@Override
	public void onBack() {
		if (isHasInitialized()) {
			try {
				this.mMusic.seekTo(0);
				this.mMusic.play();
			} catch (Exception e) {
				Log.e("Hello", e.toString());
			}
			// showBeginButton();
		}
	}

	@Override
	public void onCreateScene() {
		this.mMusic.play();
		getContext().getEngine().registerUpdateHandler(new FPSLogger());
		final SpriteBackground mainSceneBackground = new SpriteBackground(
				new Sprite(0, 0, mGrassBackground, getContext()
						.getVertexBufferObjectManager()));
		this.setBackground(mainSceneBackground);
		this.setTouchAreaBindingOnActionDownEnabled(true);

		// 开始游戏、游戏说明、最高分按钮
		startSprite = new ButtonSprite(-300, 160, mStart, getContext()
				.getVertexBufferObjectManager());
		topSprite = new ButtonSprite(-300, 360, mTop, getContext()
				.getVertexBufferObjectManager());
		informationSprite = new ButtonSprite(-300, 260, mInformation,
				getContext().getVertexBufferObjectManager());

		snakeSprite = new Sprite(200, 20, this.mSnakeTitle, getContext()
				.getVertexBufferObjectManager());

		showBeginButton();

		rockerModelSprite = new ButtonSprite(0, 0, mRockerModel, getContext()
				.getVertexBufferObjectManager());
		rockerModelSprite.setX(210 - rockerModelSprite.getWidth() / 2);
		rockerModelSprite.setY(-400);

		gravityModelSprite = new ButtonSprite(0, 0, mGravityModel, getContext()
				.getVertexBufferObjectManager());
		gravityModelSprite.setX(590 - gravityModelSprite.getWidth() / 2);
		gravityModelSprite.setY(-400);

		backModelSprite = new ButtonSprite(0, 0, mBackModel, getContext()
				.getVertexBufferObjectManager());
		backModelSprite.setX(400 - backModelSprite.getWidth() / 2);
		// backModelSprite.setY(CAMERA_HEIGHT - 15 -
		// backModelSprite.getHeight());
		backModelSprite.setY(480 + 15);

		startSprite.setOnClickListener(new OnClickListener() {

			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				StartpageScene.this.mExplosionSound.play();

				disappearBeginButton(new IEntityModifierListener() {
					@Override
					public void onModifierStarted(
							final IModifier<IEntity> pModifier,
							final IEntity pItem) {
					}

					@Override
					public void onModifierFinished(
							final IModifier<IEntity> pEntityModifier,
							final IEntity pEntity) {
						showModelButton();
					}
				});

			}
		});
		topSprite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				StartpageScene.this.mExplosionSound.play();

				disappearBeginButton(new IEntityModifierListener() {
					@Override
					public void onModifierStarted(
							final IModifier<IEntity> pModifier,
							final IEntity pItem) {
					}

					@Override
					public void onModifierFinished(
							final IModifier<IEntity> pEntityModifier,
							final IEntity pEntity) {
						Intent intent = new Intent();
						intent.setClass(StartpageScene.this.getContext(),
								InformationPageActivity.class);
						StartpageScene.this.getContext().startActivity(intent);

					}
				});
			}
		});
		informationSprite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				StartpageScene.this.mExplosionSound.play();

				disappearBeginButton(new IEntityModifierListener() {
					@Override
					public void onModifierStarted(
							final IModifier<IEntity> pModifier,
							final IEntity pItem) {
					}

					@Override
					public void onModifierFinished(
							final IModifier<IEntity> pEntityModifier,
							final IEntity pEntity) {
						Intent intent = new Intent();
						intent.setClass(StartpageScene.this.getContext(),
								InforPageActivity.class);
						StartpageScene.this.getContext().startActivity(intent);

					}
				});
			}
		});

		rockerModelSprite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {

				disappearModelButton(new IEntityModifierListener() {

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						// Intent intent = new Intent();
						// intent.setClass(StartpageScene.this.getContext(),
						// MainActivity.class);
						// getContext().startActivity(intent);
						StartpageScene.this.getContext().startScene(RockerGameScene.class);
					}

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {

					}

				});

			}
		});

		gravityModelSprite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {

				disappearModelButton(new IEntityModifierListener() {

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						Intent intent = new Intent();
						intent.setClass(StartpageScene.this.getContext(),
								SensorModelActivity.class);
						getContext().startActivity(intent);
					}

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {

					}

				});
			}
		});

		backModelSprite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				disappearModelButton(new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						showBeginButton();
					}
				});
			}
		});

		this.registerTouchArea(startSprite);
		this.registerTouchArea(informationSprite);
		this.registerTouchArea(topSprite);
		this.registerTouchArea(rockerModelSprite);
		this.registerTouchArea(gravityModelSprite);
		this.registerTouchArea(backModelSprite);

		this.attachChild(startSprite);
		this.attachChild(informationSprite);
		this.attachChild(topSprite);
		this.attachChild(snakeSprite);
		this.attachChild(rockerModelSprite);
		this.attachChild(gravityModelSprite);
		this.attachChild(backModelSprite);

		setHasInitialized(true);
	}

	private void showBeginButton() {
		float y = startSprite.getY();
		startSprite.clearEntityModifiers();
		startSprite.registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(0f), new MoveModifier(0.5f, -300, 0, y, y,
						EaseBackOut.getInstance())));
		y = informationSprite.getY();
		informationSprite.clearEntityModifiers();
		informationSprite.registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(0.2f), new MoveModifier(0.5f, -300, 0, y, y,
						EaseBackOut.getInstance())));
		y = topSprite.getY();
		topSprite.clearEntityModifiers();
		topSprite.registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(0.4f), new MoveModifier(0.5f, -300, 0, y, y,
						EaseBackOut.getInstance())));

		float x = snakeSprite.getX();
		snakeSprite.registerEntityModifier(new MoveModifier(0.5f, x, x, -400,
				15, EaseBackOut.getInstance()));
	}

	private void initializeBeginButtonPosition() {

		startSprite.clearEntityModifiers();
		startSprite.setX(0);

		informationSprite.clearEntityModifiers();
		informationSprite.setX(0);

		topSprite.clearEntityModifiers();
		topSprite.setX(0);

		snakeSprite.clearEntityModifiers();
		snakeSprite.setY(15);
	}

	private void disappearBeginButton(
			IEntityModifierListener pEntityModifierListener) {
		float y = startSprite.getY();
		startSprite
				.registerEntityModifier(new SequenceEntityModifier(
						new MoveModifier(0.5f, 0, -300, y, y, EaseBackIn
								.getInstance())));
		y = informationSprite.getY();
		informationSprite.registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(0.2f), new MoveModifier(0.5f, 0, -300, y, y,
						EaseBackIn.getInstance())));
		y = topSprite.getY();
		if (pEntityModifierListener != null) {
			topSprite.registerEntityModifier(new SequenceEntityModifier(
					pEntityModifierListener, new DelayModifier(0.4f),
					new MoveModifier(0.5f, 0, -300, y, y, EaseBackIn
							.getInstance())));
		} else {
			topSprite.registerEntityModifier(new SequenceEntityModifier(
					new DelayModifier(0.4f), new MoveModifier(0.5f, 0, -300, y,
							y, EaseBackIn.getInstance())));
		}

		float x = snakeSprite.getX();
		snakeSprite.registerEntityModifier(new MoveModifier(0.5f, x, x, 15,
				-400, EaseBackIn.getInstance()));
	}

	private void showModelButton() {
		float x = rockerModelSprite.getX();
		rockerModelSprite.registerEntityModifier(new MoveModifier(0.5f, x, x,
				-400, 15, EaseExponentialOut.getInstance()));
		x = gravityModelSprite.getX();
		gravityModelSprite.registerEntityModifier(new MoveModifier(0.5f, x, x,
				-400, 15, EaseExponentialOut.getInstance()));

		x = backModelSprite.getX();
		float y = 480 - 15 - backModelSprite.getHeight();
		backModelSprite.registerEntityModifier(new MoveModifier(0.5f, x, x,
				y + 415, y, EaseExponentialOut.getInstance()));
	}

	private void initializeModelButtonPosition() {
		rockerModelSprite.setY(-400);
		gravityModelSprite.setY(-400);
		backModelSprite.setY(480 + 15);
	}

	private void disappearModelButton(
			IEntityModifierListener pEntityModifierListener) {
		float x = rockerModelSprite.getX();
		rockerModelSprite.registerEntityModifier(new MoveModifier(0.5f, x, x,
				15, -400, EaseExponentialIn.getInstance()));
		x = gravityModelSprite.getX();
		gravityModelSprite.registerEntityModifier(new MoveModifier(0.5f, x, x,
				15, -400, EaseExponentialIn.getInstance()));

		float y = 480 - 15 - backModelSprite.getHeight();
		if (pEntityModifierListener != null) {
			x = backModelSprite.getX();
			backModelSprite.registerEntityModifier(new MoveModifier(0.5f, x, x,
					y, y + 415, pEntityModifierListener, EaseExponentialIn
							.getInstance()));
		} else {
			x = backModelSprite.getX();
			backModelSprite.registerEntityModifier(new MoveModifier(0.5f, x, x,
					y, y + 415, EaseExponentialIn.getInstance()));
		}
	}

}
