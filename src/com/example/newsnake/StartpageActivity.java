package com.example.newsnake;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class StartpageActivity extends SimpleBaseGameActivity {
	private RepeatingSpriteBackground mBackground;
	
	
	private BitmapTextureAtlas mTextureAtlas;
	private BitmapTextureAtlas mStartAtlas;
	private BitmapTextureAtlas mTopAtlas;
	private BitmapTextureAtlas mInformationAtlas;
	private BitmapTextureAtlas mSnakeAtlas;
	
	private TiledTextureRegion mTop;
	private TiledTextureRegion mStartpage;
	private TiledTextureRegion mStart;
	private TiledTextureRegion mInformation;
	private TiledTextureRegion mSnake;
	
	private Scene scene;
	
	
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
		this.mBackground=new RepeatingSpriteBackground(CAMERA_WIDTH, CAMERA_HEIGHT, getTextureManager(), AssetBitmapTextureAtlasSource.create(getAssets(), "image/beginbackground.png"), getVertexBufferObjectManager());
		
		
		mTextureAtlas=new BitmapTextureAtlas(getTextureManager(), 800, 480, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mStartAtlas=new BitmapTextureAtlas(getTextureManager(), 800, 480, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mTopAtlas=new BitmapTextureAtlas(getTextureManager(), 800, 480, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mInformationAtlas=new BitmapTextureAtlas(getTextureManager(), 800, 480, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mSnakeAtlas=new BitmapTextureAtlas(getTextureManager(), 800, 480, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		mStartpage=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mTextureAtlas, this, "image/startpage.png", 0, 0,1,1);
		mTop=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mTopAtlas, this, "image/toplist.png", 0,0,3,1);		
		mStart=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mStartAtlas, this, "image/begin.png", 0,0,3,1);
		mInformation=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mInformationAtlas, this, "image/information.png", 0,0,3,1);
		mSnake=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mSnakeAtlas, this, "image/snake.png", 0,0,1,1);
		
		
		mTextureAtlas.load();
		mStartAtlas.load();
		mTopAtlas.load();
		mInformationAtlas.load();
		mSnakeAtlas.load();
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		scene=new Scene();
		scene.setBackground(mBackground);
		
		Sprite startSprite=new Sprite(0, 0, mStartpage, getVertexBufferObjectManager());
		Sprite snakeSprite=new Sprite(75, 0, mSnake, getVertexBufferObjectManager());

		ButtonSprite StartSprite=new ButtonSprite(300, 100, mStart, getVertexBufferObjectManager());
		ButtonSprite TopSprite=new ButtonSprite(300, 300, mTop, getVertexBufferObjectManager());
		ButtonSprite InformationSprite=new ButtonSprite(285, 210, mInformation, getVertexBufferObjectManager());
		
		StartSprite.setOnClickListener(new OnClickListener() {
			
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(StartpageActivity.this, MainActivity.class);
				startActivity(intent);
				StartpageActivity.this.finish();
			}
		});
		TopSprite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(StartpageActivity.this, InformationPageActivity.class);
				startActivity(intent);
				StartpageActivity.this.finish();
			}
		});
		scene.registerTouchArea(StartSprite);
		scene.registerTouchArea(TopSprite);
		
		scene.attachChild(startSprite);
		scene.attachChild(snakeSprite);
		scene.attachChild(TopSprite);
		scene.attachChild(StartSprite);
		scene.attachChild(InformationSprite);
		// TODO Auto-generated method stub
		return scene;
	}

}
