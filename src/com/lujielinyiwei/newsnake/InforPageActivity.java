package com.lujielinyiwei.newsnake;

import java.io.IOException;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
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

public class InforPageActivity extends SimpleBaseGameActivity {
	private TextureRegion infor1;
	private TextureRegion infor2;
	private TextureRegion infor3;
	private TextureRegion infor4;
	private TextureRegion infor5;
	private TextureRegion infor6;

	private BitmapTextureAtlas mTextureAtlas;
	private BitmapTextureAtlas mButtonAtlas;

	private TiledTextureRegion mButton1;
	private TiledTextureRegion mButton2;
	private TiledTextureRegion mButton3;

	private Sound mExplosionSound;

	private Scene scene;
	int background_now = 0;

	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;

	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		engineOptions.getAudioOptions().setNeedsSound(true);
		return engineOptions;
	}

	@Override
	protected void onCreateResources() {

		mTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 2048, 2048,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mButtonAtlas = new BitmapTextureAtlas(getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		infor1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mTextureAtlas, this, "image/infor1.jpg", 0, 0);
		infor2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mTextureAtlas, this, "image/infor2.jpg", 801, 0);
		infor3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mTextureAtlas, this, "image/infor3.jpg", 0, 481);
		infor4 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mTextureAtlas, this, "image/infor4.jpg", 801, 481);
		infor5 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mTextureAtlas, this, "image/infor5.jpg", 0, 962);
		infor6 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mTextureAtlas, this, "image/infor6.jpg", 801, 962);

		mButton1 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				mButtonAtlas, this, "image/back_start.png", 0, 0, 3, 1);
		mButton2 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				mButtonAtlas, this, "image/up.png", 0, 100, 3, 1);
		mButton3 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				mButtonAtlas, this, "image/down.png", 0, 170, 3, 1);

		mTextureAtlas.load();
		mButtonAtlas.load();

		SoundFactory.setAssetBasePath("mfx/");
		try {
			this.mExplosionSound = SoundFactory.createSoundFromAsset(
					this.mEngine.getSoundManager(), this, "button2.wav");

		} catch (final IOException e) {
			Debug.e(e);
		}

		// TODO Auto-generated method stub

	}

	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		scene = new Scene();

		final Sprite backgroundinfor1 = new Sprite(0, 0, infor1,
				this.getVertexBufferObjectManager());
		final Sprite backgroundinfor2 = new Sprite(0, 0, infor2,
				this.getVertexBufferObjectManager());
		final Sprite backgroundinfor3 = new Sprite(0, 0, infor3,
				this.getVertexBufferObjectManager());
		final Sprite backgroundinfor4 = new Sprite(0, 0, infor4,
				this.getVertexBufferObjectManager());
		final Sprite backgroundinfor5 = new Sprite(0, 0, infor5,
				this.getVertexBufferObjectManager());
		final Sprite backgroundinfor6 = new Sprite(0, 0, infor6,
				this.getVertexBufferObjectManager());

		final Sprite[] backgroundSprites = { backgroundinfor1,
				backgroundinfor2, backgroundinfor5, backgroundinfor3,
				backgroundinfor4, backgroundinfor6 };
		// final SpriteBackground Background1 = new SpriteBackground(
		// new Sprite(0, 0, infor1,
		// this.getVertexBufferObjectManager()));
		// final SpriteBackground Background2 = new SpriteBackground(
		// new Sprite(0, 0, infor2,
		// this.getVertexBufferObjectManager()));
		// final SpriteBackground Background3 = new SpriteBackground(
		// new Sprite(0, 0, infor3,
		// this.getVertexBufferObjectManager()));
		// final SpriteBackground Background4 = new SpriteBackground(
		// new Sprite(0, 0, infor4,
		// this.getVertexBufferObjectManager()));
		// final SpriteBackground Background5 = new SpriteBackground(
		// new Sprite(0, 0, infor5,
		// this.getVertexBufferObjectManager()));
		// final SpriteBackground Background6 = new SpriteBackground(
		// new Sprite(0, 0, infor6,
		// this.getVertexBufferObjectManager()));

		// final SpriteBackground[]
		// backgrounds={Background1,Background2,Background5,Background3,Background4,Background6};
		// scene.setBackground(backgrounds[background_now]);

		final ButtonSprite BackSprite1 = new ButtonSprite(50, 380, mButton1,
				getVertexBufferObjectManager());
		final ButtonSprite BackSprite2 = new ButtonSprite(150, 380, mButton2,
				getVertexBufferObjectManager());
		final ButtonSprite BackSprite3 = new ButtonSprite(250, 380, mButton3,
				getVertexBufferObjectManager());

		BackSprite1.setZIndex(9999);
		BackSprite2.setZIndex(9999);
		BackSprite3.setZIndex(9999);
		scene.sortChildren();

		scene.registerTouchArea(BackSprite1);
		scene.registerTouchArea(BackSprite2);
		scene.registerTouchArea(BackSprite3);

		scene.attachChild(backgroundinfor6);
		scene.attachChild(backgroundinfor4);
		scene.attachChild(backgroundinfor3);
		scene.attachChild(backgroundinfor5);
		scene.attachChild(backgroundinfor2);
		scene.attachChild(backgroundinfor1);
		scene.attachChild(BackSprite1);
		scene.attachChild(BackSprite2);
		scene.attachChild(BackSprite3);
		BackSprite1.setOnClickListener(new OnClickListener() {
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				// // TODO Auto-generated method stub
				InforPageActivity.this.mExplosionSound.play();

				InforPageActivity.this.finish();
			}
		});

		// BackSprite2.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(ButtonSprite arg0, float arg1, float arg2) {
		// float X=backgroundinfor6.getX();
		// float Y=backgroundinfor6.getY();
		// backgroundinfor6.registerEntityModifier(new SequenceEntityModifier(
		// new ParallelEntityModifier(
		// new AlphaModifier(0.5f, 0, 1),
		// new MoveModifier(0.5f, X, X-100,
		// Y, Y))
		// ));
		//
		// // TODO Auto-generated method stub
		//
		// }
		// });
		BackSprite2.setOnClickListener(new OnClickListener() {
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {

				InforPageActivity.this.mExplosionSound.play();

				// // TODO Auto-generated method stub
				if (background_now == 1) {
					BackSprite2.setEnabled(false);
					background_now--;
					float X = backgroundSprites[background_now].getX();
					float Y = backgroundSprites[background_now].getY();
					backgroundSprites[background_now]
							.registerEntityModifier(new SequenceEntityModifier(
									new ParallelEntityModifier(
											new MoveModifier(0.5f, X, 0, Y, Y))));
				} else if (background_now == 5) {
					BackSprite3.setEnabled(true);
					background_now--;
					float X = backgroundSprites[background_now].getX();
					float Y = backgroundSprites[background_now].getY();
					backgroundSprites[background_now]
							.registerEntityModifier(new SequenceEntityModifier(
									new ParallelEntityModifier(
											new MoveModifier(0.5f, X, 0, Y, Y))));
				} else {
					background_now--;
					float X = backgroundSprites[background_now].getX();
					float Y = backgroundSprites[background_now].getY();
					backgroundSprites[background_now]
							.registerEntityModifier(new SequenceEntityModifier(
									new ParallelEntityModifier(
											new MoveModifier(0.5f, X, 0, Y, Y))));
				}
			}
		});
		BackSprite2.setEnabled(false);
		BackSprite3.setOnClickListener(new OnClickListener() {
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				
				InforPageActivity.this.mExplosionSound.play();

				// // TODO Auto-generated method stub
				if (background_now == 4) {
					BackSprite3.setEnabled(false);
					float X = backgroundSprites[background_now].getX();
					float Y = backgroundSprites[background_now].getY();
					backgroundSprites[background_now]
							.registerEntityModifier(new SequenceEntityModifier(
									new ParallelEntityModifier(
											new MoveModifier(0.5f, X, 800, Y, Y))));
					background_now++;
				} else if (background_now == 0) {
					BackSprite2.setEnabled(true);
					float X = backgroundSprites[background_now].getX();
					float Y = backgroundSprites[background_now].getY();
					backgroundSprites[background_now]
							.registerEntityModifier(new SequenceEntityModifier(
									new ParallelEntityModifier(
											new MoveModifier(0.5f, X, 800, Y, Y))));
					background_now++;

				} else {
					float X = backgroundSprites[background_now].getX();
					float Y = backgroundSprites[background_now].getY();
					backgroundSprites[background_now]
							.registerEntityModifier(new SequenceEntityModifier(
									new ParallelEntityModifier(
											new MoveModifier(0.5f, X, 800, Y, Y))));
					background_now++;
				}
			}
		});

		// TODO Auto-generated method stub
		return scene;
	}

}
