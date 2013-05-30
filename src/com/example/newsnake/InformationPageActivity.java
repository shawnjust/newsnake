package com.example.newsnake;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.graphics.Color;

public class InformationPageActivity extends SimpleBaseGameActivity {
	private RepeatingSpriteBackground mBackground;
	
	private BitmapTextureAtlas mTextureAtlas;
	
	private TiledTextureRegion mTop;
	
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
		this.mBackground=new RepeatingSpriteBackground(CAMERA_WIDTH, CAMERA_HEIGHT, getTextureManager(), AssetBitmapTextureAtlasSource.create(getAssets(), "image/beginbackground.png"), getVertexBufferObjectManager());
		
		mTextureAtlas=new BitmapTextureAtlas(getTextureManager(), 800, 480, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mTop=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mTextureAtlas, this, "image/topgrade.png", 0,0,1,1);		

		
		mTextureAtlas.load();
		
		
		
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
		scene.setBackground(mBackground);
		
		
		final Text endGrade = new Text(0, 0, this.mFont, "","XXXXXX".length(), this.getVertexBufferObjectManager());
		float endGradeX = 400 - endGrade.getWidth() / 2;
		float endGradeY = 175 - endGrade.getHeight() / 2;
		endGrade.setPosition(endGradeX, endGradeY);
		scene.attachChild(endGrade);

		Sprite topSprite=new Sprite(0, 0, mTop, getVertexBufferObjectManager());
		
		scene.attachChild(topSprite);
		// TODO Auto-generated method stub
		return scene;
	}

}

