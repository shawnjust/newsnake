package com.lujielinyiwei.newsnake;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class StartpageActivity extends SimpleBaseGameActivity {
	
	
	private BitmapTextureAtlas mTextureAtlas;
	private BitmapTextureAtlas mStartAtlas;

	
	private TextureRegion mGrassBackground;
	
	private TiledTextureRegion mTop;
	private TiledTextureRegion mStartpage;
	private TiledTextureRegion mStart;
	private TiledTextureRegion mInformation;
	private TiledTextureRegion mSnake;
	
	
	
	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;
	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		Camera camera = new Camera(0, 0, CAMERA_WIDTH,CAMERA_HEIGHT);
		final EngineOptions engineOptions = new EngineOptions(true,ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH,CAMERA_HEIGHT),camera);
		return engineOptions;
	}

	@Override
	protected void onCreateResources() {
		
		mTextureAtlas=new BitmapTextureAtlas(this.getTextureManager(), 1024, 512);
		mStartAtlas=new BitmapTextureAtlas(getTextureManager(), 1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		
		mGrassBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mTextureAtlas, this,"image/beginbackground.png", 0, 0);
		mTop=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mStartAtlas, this, "image/toplist.png", 0,0,3,1);		
		mStart=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mStartAtlas, this, "image/begin.png", 0,116,3,1);
		mInformation=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mStartAtlas, this, "image/information.png", 0,248,3,1);
		mSnake=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mStartAtlas, this, "image/snake.png", 0,359,1,1);
		
		
		mTextureAtlas.load();
		mStartAtlas.load();

		// TODO Auto-generated method stub
		
	}

	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene=new Scene();
		final SpriteBackground mainSceneBackground = new SpriteBackground(new Sprite(0, 0, mGrassBackground,this.getVertexBufferObjectManager()));
		scene.setBackground(mainSceneBackground);
		scene.setTouchAreaBindingOnActionDownEnabled(true);
		
		Sprite snakeSprite=new Sprite(75, 0, mSnake, getVertexBufferObjectManager());

		ButtonSprite StartSprite=new ButtonSprite(300, 110, mStart, getVertexBufferObjectManager());
		ButtonSprite TopSprite=new ButtonSprite(280, 313, mTop, getVertexBufferObjectManager());
		ButtonSprite InformationSprite=new ButtonSprite(300, 220, mInformation, getVertexBufferObjectManager());
		
		StartSprite.setOnClickListener(new OnClickListener() {
			
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(StartpageActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
		TopSprite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(StartpageActivity.this, InformationPageActivity.class);
				startActivity(intent);
			}
		});
		InformationSprite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(StartpageActivity.this, InforPageActivity.class);
				startActivity(intent);
			}
		});
		scene.registerTouchArea(StartSprite);
		scene.registerTouchArea(TopSprite);
		scene.registerTouchArea(InformationSprite);
		
		scene.attachChild(snakeSprite);
		scene.attachChild(TopSprite);
		scene.attachChild(StartSprite);
		scene.attachChild(InformationSprite);
		// TODO Auto-generated method stub
		return scene;
	}

}
