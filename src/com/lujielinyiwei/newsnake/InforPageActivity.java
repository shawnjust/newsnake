package com.lujielinyiwei.newsnake;

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

import android.R.integer;

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


	private Scene scene;
	int background_now=0;

	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;

	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		return engineOptions;
	}

	@Override
	protected void onCreateResources() {

		mTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 2048, 2048,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mButtonAtlas = new BitmapTextureAtlas(getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		infor1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mTextureAtlas, this, "image/infor1.png", 0, 0);
		infor2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mTextureAtlas, this, "image/infor2.png",801,0);
		infor3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mTextureAtlas, this, "image/infor3.png",0,481);
		infor4 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mTextureAtlas, this, "image/infor4.png",801,481);
		infor5 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mTextureAtlas, this, "image/infor5.png",0,962);
		infor6 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mTextureAtlas, this, "image/infor6.png",801,962);
		
		
		mButton1 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				mButtonAtlas, this, "image/back_start.png", 0, 0, 3, 1);
		mButton2 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				mButtonAtlas, this, "image/up.png", 0, 70, 3, 1);
		mButton3 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				mButtonAtlas, this, "image/down.png", 0, 140, 3, 1);

		mTextureAtlas.load();
		mButtonAtlas.load();


		// TODO Auto-generated method stub

	}

	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		scene = new Scene();
		
		final SpriteBackground Background1 = new SpriteBackground(
				new Sprite(0, 0, infor1,
						this.getVertexBufferObjectManager()));
		final SpriteBackground Background2 = new SpriteBackground(
				new Sprite(0, 0, infor2,
						this.getVertexBufferObjectManager()));
		final SpriteBackground Background3 = new SpriteBackground(
				new Sprite(0, 0, infor3,
						this.getVertexBufferObjectManager()));
		final SpriteBackground Background4 = new SpriteBackground(
				new Sprite(0, 0, infor4,
						this.getVertexBufferObjectManager()));
		final SpriteBackground Background5 = new SpriteBackground(
				new Sprite(0, 0, infor5,
						this.getVertexBufferObjectManager()));
		final SpriteBackground Background6 = new SpriteBackground(
				new Sprite(0, 0, infor6,
						this.getVertexBufferObjectManager()));
		
		final SpriteBackground[] backgrounds={Background1,Background2,Background5,Background3,Background4,Background6};
		scene.setBackground(backgrounds[background_now]);


		 final ButtonSprite BackSprite1 = new ButtonSprite(50, 380, mButton1,
		 getVertexBufferObjectManager());
		 final ButtonSprite BackSprite2 = new ButtonSprite(150, 380, mButton2,
				 getVertexBufferObjectManager());
		 final ButtonSprite BackSprite3 = new ButtonSprite(250, 380, mButton3,
					getVertexBufferObjectManager());
		 
		 scene.registerTouchArea(BackSprite1);
		 scene.registerTouchArea(BackSprite2);
		 scene.registerTouchArea(BackSprite3);
		

		 scene.attachChild(BackSprite1);
		 scene.attachChild(BackSprite2);
		 scene.attachChild(BackSprite3);
		 BackSprite1.setOnClickListener(new OnClickListener() {
		 public void onClick(ButtonSprite arg0, float arg1, float arg2) {
		// // TODO Auto-generated method stub
			 InforPageActivity.this.finish();
		 }
		 });

		 BackSprite2.setOnClickListener(new OnClickListener() {
				 public void onClick(ButtonSprite arg0, float arg1, float arg2) {
					 
				// // TODO Auto-generated method stub
					 if(background_now==1)
					 {
						 BackSprite2.setEnabled(false);
						 background_now--;
						 scene.setBackground(backgrounds[background_now]);
					 }
					 else if(background_now==5){
						 BackSprite3.setEnabled(true);
						 background_now--;
						 scene.setBackground(backgrounds[background_now]);
					}
					else {
						background_now--;
						scene.setBackground(backgrounds[background_now]);
					}
				 }
		});
		BackSprite2.setEnabled(false);
		BackSprite3.setOnClickListener(new OnClickListener() {
				public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				// // TODO Auto-generated method stub
					if(background_now==4)
					{
						BackSprite3.setEnabled(false);
						background_now++;
						scene.setBackground(backgrounds[background_now]);
					}
					else if(background_now==0){
						BackSprite2.setEnabled(true);
						background_now++;
						scene.setBackground(backgrounds[background_now]);
					}
					else {
						background_now++;
						scene.setBackground(backgrounds[background_now]);
					}
	     		}
		});
		 
		// TODO Auto-generated method stub
		return scene;
	}

}
