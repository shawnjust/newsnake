package com.example.newsnake;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.content.Intent;
import android.graphics.Color;

public class InformationPageActivity extends SimpleBaseGameActivity {
	private TextureRegion mBackground;
	
	private BitmapTextureAtlas mTextureAtlas;
	private BitmapTextureAtlas mButtonAtlas;
	
	private TiledTextureRegion mButton;
	
	private Font mFont;
	
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
		
		mTextureAtlas=new BitmapTextureAtlas(getTextureManager(), 1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mButtonAtlas=new BitmapTextureAtlas(getTextureManager(), 128, 128,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		mBackground=BitmapTextureAtlasTextureRegionFactory.createFromAsset(mTextureAtlas, this, "image/topgrade.png",0,0);	
		mButton=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mButtonAtlas, this, "image/back.png", 0, 0,1,1);
		
		mTextureAtlas.load();
		mButtonAtlas.load();
		
		
		FontFactory.setAssetBasePath("font/");
		final ITexture droidFontTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		this.mFont = FontFactory.createFromAsset(this.getFontManager(), droidFontTexture, this.getAssets(), "Droid.ttf", 48, true, Color.BLACK);
		this.mFont.load();

		// TODO Auto-generated method stub
		
	}

	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		scene=new Scene();
		final SpriteBackground mainSceneBackground = new SpriteBackground(new Sprite(0, 0, mBackground,this.getVertexBufferObjectManager()));
		scene.setBackground(mainSceneBackground);
		
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

		
		final Text endGrade = new Text(0, 0, this.mFont, "","adsfdsfads".length(), this.getVertexBufferObjectManager());
		float endGradeX = 250 - endGrade.getWidth() / 2;
		float endGradeY = 220 - endGrade.getHeight() / 2;
		endGrade.setPosition(endGradeX, endGradeY);
		endGrade.setText("adsfdsafds");

		
		ButtonSprite BackSprite=new ButtonSprite(0, 380, mButton, getVertexBufferObjectManager());
		BackSprite.setOnClickListener(new OnClickListener() {
			
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				// TODO Auto-generated method stub

				InformationPageActivity.this.finish();
			}
		});
		scene.registerTouchArea(BackSprite);
		

		scene.attachChild(endGrade);
		scene.attachChild(BackSprite);
		// TODO Auto-generated method stub
		return scene;
	}

}

