package com.example.newsnake;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.graphics.Color;
import android.opengl.GLES20;

import com.example.entity.sprite.MyButtonSprite;

public class MainActivity extends SimpleBaseGameActivity {

	public static final int CAMERA_WIDTH = 800;
	public static final int CAMERA_HEIGHT = 480;

	private Camera camera;

	private BitmapTextureAtlas mHeadBitmapTextureAtlas;
	private TiledTextureRegion mHeadTextureRegion;

	private BitmapTextureAtlas mBodyBitmapTextureAtlas;
	private TiledTextureRegion mBodyTextureRegion;

	private BitmapTextureAtlas mAutoParallaxBackgroundTexture;
	private TextureRegion mGrassBackground;
	private TextureRegion mEndBackground;
	private TextureRegion mPauseBackground;

	private BitmapTextureAtlas mOnScreenControlTexture;
	private ITextureRegion mOnScreenControlBaseTextureRegion;
	private ITextureRegion mOnScreenControlKnobTextureRegion;

	private BitmapTextureAtlas mFoodBitmapTextureAtlas;
	private TiledTextureRegion mFood1TextureRegion;
	private TiledTextureRegion mFood2TextureRegion;
	private TiledTextureRegion mFood3TextureRegion;
	private TiledTextureRegion mFood4TextureRegion;

	private BitmapTextureAtlas mButtonBitmapTextureAtlas;
	private TiledTextureRegion mEndBackbuttonTextureRegion;
	private TiledTextureRegion mEndContinuebuttonTextureRegion;
	private TiledTextureRegion mEndHelpbuttonTextureRegion;

	private TiledTextureRegion mPauseContinueButtonTextureRegion;
	private TiledTextureRegion mPauseNewgameButtonTextureRegion;
	private TiledTextureRegion mPauseBackbuttonTextureRegion;

	private TiledTextureRegion mPausebuttonTextureRegion;

	private CameraScene mEndScene;
	private CameraScene mPauseScene;

	private MyIUpdateHandler mIUpdateHandler;

	private Font mFont;

	@Override
	public EngineOptions onCreateEngineOptions() {
		this.camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		return engineOptions;
	}

	@Override
	public void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("image/");

		this.mHeadBitmapTextureAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), 128, 128, TextureOptions.BILINEAR);
		this.mHeadTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mHeadBitmapTextureAtlas, this,
						"head_new.png", 0, 0, 1, 1);
		this.mHeadBitmapTextureAtlas.load();

		this.mBodyBitmapTextureAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), 128, 128, TextureOptions.BILINEAR);
		this.mBodyTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mBodyBitmapTextureAtlas, this,
						"body_new.png", 0, 0, 1, 1);
		this.mBodyBitmapTextureAtlas.load();

		this.mAutoParallaxBackgroundTexture = new BitmapTextureAtlas(
				this.getTextureManager(), 1024, 2048);
		this.mGrassBackground = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mAutoParallaxBackgroundTexture, this,
						"back1_new.png", 0, 0);
		this.mEndBackground = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mAutoParallaxBackgroundTexture, this,
						"endbackground.png", 0, 480);
		this.mPauseBackground = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mAutoParallaxBackgroundTexture, this,
						"pausebackground.png", 0, 960);
		this.mAutoParallaxBackgroundTexture.load();

		this.mOnScreenControlTexture = new BitmapTextureAtlas(
				this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		this.mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mOnScreenControlTexture, this,
						"onscreen_control_base_new.png", 0, 0);
		this.mOnScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mOnScreenControlTexture, this,
						"onscreen_control_knob_new.png", 128, 0);
		this.mOnScreenControlTexture.load();

		this.mFoodBitmapTextureAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), 64, 256, TextureOptions.BILINEAR);
		this.mFood1TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mFoodBitmapTextureAtlas, this,
						"food1_new.png", 0, 0, 1, 1);
		this.mFood2TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mFoodBitmapTextureAtlas, this,
						"food2_new.png", 0, 60, 1, 1);
		this.mFood3TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mFoodBitmapTextureAtlas, this,
						"food3_new.png", 0, 120, 1, 1);
		this.mFood4TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mFoodBitmapTextureAtlas, this,
						"food4_new.png", 0, 180, 1, 1);
		this.mFoodBitmapTextureAtlas.load();

		this.mButtonBitmapTextureAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), 512, 1024, TextureOptions.BILINEAR);
		this.mEndBackbuttonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mButtonBitmapTextureAtlas, this,
						"backbutton.png", 0, 0, 3, 1);
		this.mEndHelpbuttonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mButtonBitmapTextureAtlas, this,
						"helpbutton.png", 0, 120, 3, 1);
		this.mEndContinuebuttonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mButtonBitmapTextureAtlas, this,
						"continuebutton.png", 0, 240, 3, 1);
		this.mPauseContinueButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mButtonBitmapTextureAtlas, this,
						"pausecontinuebutton.png", 0, 360, 3, 1);
		this.mPauseNewgameButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mButtonBitmapTextureAtlas, this,
						"pausenewgamebutton.png", 0, 480, 3, 1);
		this.mPauseBackbuttonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mButtonBitmapTextureAtlas, this,
						"pausebackbutton.png", 0, 600, 3, 1);
		this.mPausebuttonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mButtonBitmapTextureAtlas, this,
						"pauseButton.png", 0, 720, 3, 1);
		this.mButtonBitmapTextureAtlas.load();

		FontFactory.setAssetBasePath("font/");
		final ITexture droidFontTexture = new BitmapTextureAtlas(
				this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		this.mFont = FontFactory.createFromAsset(this.getFontManager(),
				droidFontTexture, this.getAssets(), "Droid.ttf", 48, true,
				Color.WHITE);
		this.mFont.load();
	}

	@Override
	protected Scene onCreateScene() {

		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene();
		final SpriteBackground mainSceneBackground = new SpriteBackground(
				new Sprite(0, 0, mGrassBackground,
						this.getVertexBufferObjectManager()));
		scene.setBackground(mainSceneBackground);

		/************************************ �������ķָ��� ****************************************/

		this.mEndScene = new CameraScene(camera);
		this.mEndScene.setBackgroundEnabled(false);
		final Sprite endSceneBackGroundSprite = new Sprite(0, 0,
				mEndBackground, this.getVertexBufferObjectManager());
		mEndScene.attachChild(endSceneBackGroundSprite);

		final Text endGrade = new Text(0, 0, this.mFont, "", "XXXXXX".length(),
				this.getVertexBufferObjectManager());
		final Text endHighestGrade = new Text(0, 0, this.mFont, "",
				"XXXXXX".length(), this.getVertexBufferObjectManager());
		float endGradeX = 400 - endGrade.getWidth() / 2;
		float endGradeY = 175 - endGrade.getHeight() / 2;
		endGrade.setPosition(endGradeX, endGradeY);

		float endHighestGradeX = 400 - endHighestGrade.getWidth() / 2;
		float endHighestGradeY = 265 - endHighestGrade.getHeight() / 2;
		endHighestGrade.setPosition(endHighestGradeX, endHighestGradeY);

		mEndScene.attachChild(endGrade);
		mEndScene.attachChild(endHighestGrade);

		/** gameover������ʼ��ť **/
		final MyButtonSprite endContinueButton = new MyButtonSprite(
				560 - mEndContinuebuttonTextureRegion.getWidth() / 2,
				360 - mEndContinuebuttonTextureRegion.getHeight() / 2,
				mEndContinuebuttonTextureRegion,
				this.getVertexBufferObjectManager());

		mEndScene.registerTouchArea(endContinueButton);
		mEndScene.attachChild(endContinueButton);

		/** gameover����������ť��ť **/
		final MyButtonSprite endHelpButton = new MyButtonSprite(
				370 - mEndHelpbuttonTextureRegion.getWidth() / 2,
				360 - mEndHelpbuttonTextureRegion.getHeight() / 2,
				mEndHelpbuttonTextureRegion,
				this.getVertexBufferObjectManager());
		mEndScene.registerTouchArea(endHelpButton);
		mEndScene.attachChild(endHelpButton);

		/** gameover�������ذ�ť��ť **/
		final MyButtonSprite endBackButton = new MyButtonSprite(
				180 - mEndBackbuttonTextureRegion.getWidth() / 2,
				360 - mEndBackbuttonTextureRegion.getHeight() / 2,
				mEndBackbuttonTextureRegion,
				this.getVertexBufferObjectManager());

		mEndScene.registerTouchArea(endBackButton);
		mEndScene.attachChild(endBackButton);

		mEndScene.setTouchAreaBindingOnActionDownEnabled(true);

		/************************************ �������ķָ��� ****************************************/

		/************************************ �������ķָ��� ****************************************/

		this.mPauseScene = new CameraScene(camera);
		this.mPauseScene.setBackgroundEnabled(false);
		final Sprite pauseSceneBackGroundSprite = new Sprite(0, 0,
				mPauseBackground, this.getVertexBufferObjectManager());
		mPauseScene.attachChild(pauseSceneBackGroundSprite);

		/** gameover�����ָ���ť **/
		final MyButtonSprite pauseContinueButton = new MyButtonSprite(
				90 - mPauseContinueButtonTextureRegion.getWidth() / 2,
				120 - mPauseContinueButtonTextureRegion.getHeight() / 2,
				mPauseContinueButtonTextureRegion,
				this.getVertexBufferObjectManager());
		mPauseScene.registerTouchArea(pauseContinueButton);
		mPauseScene.attachChild(pauseContinueButton);

		/** gameover��������Ϸ��ť **/
		final MyButtonSprite pauseNewgameButton = new MyButtonSprite(
				90 - mPauseNewgameButtonTextureRegion.getWidth() / 2,
				240 - mPauseNewgameButtonTextureRegion.getHeight() / 2,
				mPauseNewgameButtonTextureRegion,
				this.getVertexBufferObjectManager());

		mPauseScene.registerTouchArea(pauseNewgameButton);
		mPauseScene.attachChild(pauseNewgameButton);

		/** gameover�������ذ�ť��ť **/
		final MyButtonSprite pauseBackButton = new MyButtonSprite(
				90 - mPauseBackbuttonTextureRegion.getWidth() / 2,
				360 - mPauseBackbuttonTextureRegion.getHeight() / 2,
				mPauseBackbuttonTextureRegion,
				this.getVertexBufferObjectManager());

		mPauseScene.registerTouchArea(pauseBackButton);
		mPauseScene.attachChild(pauseBackButton);

		mPauseScene.setTouchAreaBindingOnActionDownEnabled(true);
		/************************************ �������ķָ��� ****************************************/

		final MyButtonSprite pauseButton = new MyButtonSprite(20, CAMERA_HEIGHT
				- mPausebuttonTextureRegion.getHeight() - 20,
				mPausebuttonTextureRegion, this.getVertexBufferObjectManager());
		scene.attachChild(pauseButton);
		scene.setTouchAreaBindingOnActionDownEnabled(true);
		scene.registerTouchArea(pauseButton);
		pauseButton.setZIndex(20000);

		final float headCenterX = CAMERA_WIDTH / 4
				- this.mHeadTextureRegion.getWidth() / 2;
		final float headCenterY = CAMERA_HEIGHT / 4
				- this.mHeadTextureRegion.getHeight() / 2;
		final Sprite head = new Sprite(headCenterX, headCenterY,
				this.mHeadTextureRegion, this.getVertexBufferObjectManager());
		final MyHeadPhysicsHandler physicsHandler = new MyHeadPhysicsHandler(
				head);
		head.registerUpdateHandler(physicsHandler);
		scene.attachChild(head);
		head.setZIndex(10000);

		float foodCenterX = (float) (Math.random() * (CAMERA_WIDTH - 300) + 40 - this.mFood1TextureRegion
				.getWidth() / 2);
		float foodCenterY = (float) (Math.random() * (CAMERA_HEIGHT - 300) + 40 - this.mFood1TextureRegion
				.getHeight() / 2);
		final Sprite food1 = new Sprite(foodCenterX, foodCenterY,
				mFood1TextureRegion, this.getVertexBufferObjectManager());
		final Sprite food2 = new Sprite(CAMERA_WIDTH + 100,
				CAMERA_HEIGHT + 100, mFood2TextureRegion,
				this.getVertexBufferObjectManager());
		final Sprite food3 = new Sprite(CAMERA_WIDTH + 100,
				CAMERA_HEIGHT + 100, mFood3TextureRegion,
				this.getVertexBufferObjectManager());
		final Sprite food4 = new Sprite(CAMERA_WIDTH + 100,
				CAMERA_HEIGHT + 100, mFood4TextureRegion,
				this.getVertexBufferObjectManager());
		scene.attachChild(food1);
		scene.attachChild(food2);
		scene.attachChild(food3);
		scene.attachChild(food4);

		final float bodyCenterX = (CAMERA_WIDTH / 4 - this.mBodyTextureRegion
				.getWidth() / 2);
		final float bodyCenterY = (CAMERA_HEIGHT / 4 - this.mBodyTextureRegion
				.getHeight() / 2);
		final Sprite body1 = new Sprite(bodyCenterX, bodyCenterY,
				this.mBodyTextureRegion, this.getVertexBufferObjectManager());
		scene.attachChild(body1);
		scene.sortChildren();
		body1.setZIndex(9999);
		physicsHandler.addBody(body1);

		mIUpdateHandler = new MyIUpdateHandler() {
			Sprite food = food1;
			private boolean able = true;

			public void enable() {
				able = true;
			}

			public void disable() {
				able = false;
			}

			@Override
			public void reset() {
			}

			@Override
			public void onUpdate(float arg0) {
				if (!able) {
					return;
				}
				float foodPosX = food.getX() + food.getWidth() / 2;
				float foodPosY = food.getY() + food.getHeight() / 2;
				float headPosX = head.getX() + head.getWidth() / 2;
				float headPosY = head.getY() + head.getHeight() / 2;
				if ((foodPosX - headPosX) * (foodPosX - headPosX)
						+ (foodPosY - headPosY) * (foodPosY - headPosY) < 40 * 40) {
					Sprite body = new Sprite(CAMERA_WIDTH + 100,
							CAMERA_HEIGHT + 100,
							MainActivity.this.mBodyTextureRegion,
							MainActivity.this.getVertexBufferObjectManager());
					scene.attachChild(body);
					body.setZIndex(9999);
					scene.sortChildren();
					physicsHandler.addBody(body);
					food.setPosition(CAMERA_WIDTH + 100, CAMERA_HEIGHT + 100);
					float num = (float) (Math.random() * 4);
					if (num < 1) {
						food = food1;
					} else if (num < 2) {
						food = food2;
					} else if (num < 3) {
						food = food3;
					} else {
						food = food4;
					}

					food.setRotation((float) (Math.random() * 60 - 30));
					float foodCenterX = 0;
					float foodCenterY = 0;
					boolean collision = false;
					do {
						foodCenterX = (float) (Math.random()
								* (CAMERA_WIDTH - 80) + 40 - MainActivity.this.mFood1TextureRegion
								.getWidth() / 2);
						foodCenterY = (float) (Math.random()
								* (CAMERA_HEIGHT - 80) + 40 - MainActivity.this.mFood1TextureRegion
								.getHeight() / 2);
						collision = ((foodCenterX - headPosX)
								* (foodCenterX - headPosX)
								+ (foodCenterY - headPosY)
								* (foodCenterY - headPosY) < 40 * 40);
					} while ((foodCenterX > 580 && foodCenterY > 220)
							|| collision);
					food.setPosition(foodCenterX, foodCenterY);
				}
				boolean collision = false;
				Iterator<Sprite> temIte = physicsHandler.getBodyListIterator();
				for (int i = 0; i < 5; i++) {
					if (temIte.hasNext())
						temIte.next();
					else
						break;
				}
				while (temIte.hasNext()) {
					Sprite body = temIte.next();
					float bodyPosX = body.getX() + body.getWidth() / 2;
					float bodyPosY = body.getY() + body.getHeight() / 2;
					if ((Math.abs(bodyPosX - headPosX) < 20)
							&& (Math.abs(bodyPosY - headPosY) < 20)) {
						collision = true;
					}
				}
				if ((headPosX < 0) || (headPosY < 0)
						|| (headPosX > CAMERA_WIDTH)
						|| (headPosY > CAMERA_HEIGHT) || collision) {
					if (!able)
						return;
					this.disable();
					// MainActivity.this.mEngine.stop();
					physicsHandler.disable();
					// scene.clearUpdateHandlers();
					head.clearUpdateHandlers();
					Iterator<Sprite> ite = physicsHandler.getBodyListIterator();
					float timecount = 0.1f;
					while (ite.hasNext()) {
						Sprite body = ite.next();
						timecount += 0.05f;
						body.registerEntityModifier(new SequenceEntityModifier(
								new DelayModifier(timecount),
								new LoopEntityModifier(
										new SequenceEntityModifier(
												new AlphaModifier(0.2f, 1, 0),
												new AlphaModifier(0.2f, 0, 1),
												new DelayModifier(2f)))));
					}
					endSceneBackGroundSprite.setAlpha(0);
					endSceneBackGroundSprite
							.registerEntityModifier(new SequenceEntityModifier(
									new DelayModifier(0.5f),
									new ParallelEntityModifier(
											new AlphaModifier(0.3f, 0, 1),
											new ScaleModifier(0.2f, 1.2f, 1))));
					endBackButton.setAlpha(0);
					float X = endBackButton.getX();
					float Y = endBackButton.getY();
					endBackButton
							.registerEntityModifier(new SequenceEntityModifier(
									new DelayModifier(1f),
									new ParallelEntityModifier(
											new AlphaModifier(0.5f, 0, 1),
											new MoveModifier(0.5f, X, X,
													Y + 20, Y))));
					endHelpButton.setAlpha(0);
					X = endHelpButton.getX();
					Y = endHelpButton.getY();
					endHelpButton
							.registerEntityModifier(new SequenceEntityModifier(
									new DelayModifier(1.2f),
									new ParallelEntityModifier(
											new AlphaModifier(0.5f, 0, 1),
											new MoveModifier(0.5f, X, X,
													Y + 20, Y))));
					endContinueButton.setAlpha(0);
					X = endContinueButton.getX();
					Y = endContinueButton.getY();
					endContinueButton
							.registerEntityModifier(new SequenceEntityModifier(
									new DelayModifier(1.4f),
									new ParallelEntityModifier(
											new AlphaModifier(0.5f, 0, 1),
											new MoveModifier(0.5f, X, X,
													Y + 20, Y))));
					int grade = (physicsHandler.getBodyCount() - 1) * 10;
					endGrade.setText("" + grade);
					endGrade.setAlpha(0);
					X = endGrade.getX();
					Y = endGrade.getY();
					endGrade.registerEntityModifier(new SequenceEntityModifier(
							new DelayModifier(1.6f),
							new ParallelEntityModifier(new AlphaModifier(0.5f,
									0, 1), new MoveModifier(0.5f, X, X, Y + 20,
									Y))));

					int highestGrade = 0;
					BufferedInputStream Bufferedinput;
					try {
						Bufferedinput = new BufferedInputStream(
								openFileInput("Grade.data"));
						DataInputStream Datainput = new DataInputStream(
								Bufferedinput);
						highestGrade = Datainput.readInt();
						Datainput.close();
					} catch (IOException e) {
					}

					if (highestGrade < grade) {
						highestGrade = grade;
						try {
							BufferedOutputStream Bufoutput = new BufferedOutputStream(
									openFileOutput("Grade.data", MODE_PRIVATE));
							DataOutputStream output = new DataOutputStream(
									Bufoutput);
							output.writeInt(highestGrade);
							output.close();
						} catch (IOException e) {
						}
					}

					endHighestGrade.setText("" + highestGrade);
					endHighestGrade.setAlpha(0);
					X = endHighestGrade.getX();
					Y = endHighestGrade.getY();
					endHighestGrade
							.registerEntityModifier(new SequenceEntityModifier(
									new DelayModifier(1.8f),
									new ParallelEntityModifier(
											new AlphaModifier(0.5f, 0, 1),
											new MoveModifier(0.5f, X, X,
													Y + 20, Y))));
					scene.setChildScene(mEndScene);

					// MainActivity.this.mEngine.setScene(MainActivity.this.onCreateScene());
				}
			}
		};

		mIUpdateHandler.enable();
		scene.registerUpdateHandler(mIUpdateHandler);
		scene.sortChildren();

		final AnalogOnScreenControl analogOnScreenControl = new AnalogOnScreenControl(
				CAMERA_WIDTH
						- this.mOnScreenControlBaseTextureRegion.getWidth()
						- 70, CAMERA_HEIGHT
						- this.mOnScreenControlBaseTextureRegion.getHeight()
						- 40, this.camera,
				this.mOnScreenControlBaseTextureRegion,
				this.mOnScreenControlKnobTextureRegion, 0.1f, 200,
				this.getVertexBufferObjectManager(),
				new IAnalogOnScreenControlListener() {

					@Override
					public void onControlChange(
							final BaseOnScreenControl pBaseOnScreenControl,
							final float pValueX, final float pValueY) {

						if (pValueX == 0 && pValueY == 0) {
							// physicsHandler.setRadius(0);
						} else {
							physicsHandler.setSpeed(200);
							physicsHandler.setRadius(60);
							physicsHandler.setRotation(pValueX, pValueY);
						}
					}

					@Override
					public void onControlClick(
							final AnalogOnScreenControl pAnalogOnScreenControl) {
						// head.registerEntityModifier(new
						// SequenceEntityModifier(
						// new ScaleModifier(0.25f, 1, 1.5f),
						// new ScaleModifier(0.25f, 1.5f, 1)));
					}
				});
		analogOnScreenControl.getControlBase().setBlendFunction(
				GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		analogOnScreenControl.getControlBase().setAlpha(0.5f);
		analogOnScreenControl.getControlBase().setScaleCenter(0, 128);
		analogOnScreenControl.getControlBase().setScale(1.25f);
		analogOnScreenControl.getControlKnob().setScale(1.25f);
		analogOnScreenControl.refreshControlKnobPosition();

		scene.setChildScene(analogOnScreenControl);

		endContinueButton
				.setOnClickListener(new MyButtonSprite.OnClickListener() {

					@Override
					public void onClick(MyButtonSprite pButtonSprite,
							float pTouchAreaLocalX, float pTouchAreaLocalY) {
						MainActivity.this.mEngine.setScene(MainActivity.this
								.onCreateScene());
					}
				});

		endBackButton.setOnClickListener(new MyButtonSprite.OnClickListener() {

			@Override
			public void onClick(MyButtonSprite pButtonSprite,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				MainActivity.this.finish();
			}
		});

		endHelpButton.setOnClickListener(new MyButtonSprite.OnClickListener() {

			@Override
			public void onClick(MyButtonSprite pButtonSprite,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

			}
		});

		pauseNewgameButton
				.setOnClickListener(new MyButtonSprite.OnClickListener() {

					@Override
					public void onClick(MyButtonSprite pButtonSprite,
							float pTouchAreaLocalX, float pTouchAreaLocalY) {
						MainActivity.this.mEngine.setScene(MainActivity.this
								.onCreateScene());
					}
				});

		pauseBackButton
				.setOnClickListener(new MyButtonSprite.OnClickListener() {

					@Override
					public void onClick(MyButtonSprite pButtonSprite,
							float pTouchAreaLocalX, float pTouchAreaLocalY) {
						MainActivity.this.finish();
					}
				});

		pauseContinueButton
				.setOnClickListener(new MyButtonSprite.OnClickListener() {

					@Override
					public void onClick(MyButtonSprite pButtonSprite,
							float pTouchAreaLocalX, float pTouchAreaLocalY) {
						scene.clearChildScene();
						physicsHandler.enable();
						scene.setChildScene(analogOnScreenControl);
						pauseButton.setAlpha(1f);
					}
				});

		pauseButton.setOnClickListener(new MyButtonSprite.OnClickListener() {

			@Override
			public void onClick(MyButtonSprite pButtonSprite,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (physicsHandler.isable()) {
					physicsHandler.disable();
					scene.setChildScene(mPauseScene);
					pauseButton.setAlpha(0f);
				}
			}
		});

		return scene;
	}

	interface MyIUpdateHandler extends IUpdateHandler {
		public void enable();

		public void disable();
	}
}
