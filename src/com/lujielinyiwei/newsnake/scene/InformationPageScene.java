package com.lujielinyiwei.newsnake.scene;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
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

import android.R.bool;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import com.lujielinyiwei.entity.scene.MyScene;
import com.lujielinyiwei.newsnake.InforPageActivity;
import com.lujielinyiwei.newsnake.SensorModelActivity;
import com.lujielinyiwei.newsnake.StartpageActivity;
import com.lujielinyiwei.newsnake.scene.BaseGameScene.MyIUpdateHandler;
import com.lujielinyiwei.newsnake.staticdata.StaticData;

public class InformationPageScene extends MyScene{

	public InformationPageScene(StartpageActivity context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	private SpriteBackground mainSceneBackground;
	
	private Camera camera;
	
	private TextureRegion mBackground;

	private BitmapTextureAtlas mTextureAtlas;

	private Music mMusic;
	private Sound mExplosionSound;

	private Font mFont;

	private Scene scene;
	
	private ITexture droidFontTexture;

	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;
	
	
	@Override
	public void onCreateResources() {
		this.camera = new Camera(0, 0, CAMERA_WIDTH,
				CAMERA_HEIGHT);
		
		
		this.mTextureAtlas = new BitmapTextureAtlas(this.getContext().getTextureManager(), 1024, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
				
		this.mBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mTextureAtlas, getContext(), "image/topgrade.png", 0, 0);
		
		this.mTextureAtlas.load();
		
		FontFactory.setAssetBasePath("font/");
		
		this.droidFontTexture = new BitmapTextureAtlas(
				this.getContext().getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		
		this.mFont = FontFactory.createFromAsset(this.getContext().getFontManager(),
				this.droidFontTexture, this.getContext().getAssets(), "Droid.ttf", 60, true,
				Color.BLACK);
		
		this.mFont.load();
		
		MusicFactory.setAssetBasePath("mfx/");
		SoundFactory.setAssetBasePath("mfx/");
		try {
			this.mMusic = MusicFactory.createMusicFromAsset(this.getContext()
					.getEngine().getMusicManager(), this.getContext(),
					"background.mp3");
			this.mMusic.setLooping(true);
			this.mExplosionSound = SoundFactory.createSoundFromAsset(
					this.getContext().getEngine().getSoundManager(), this.getContext(),
					"button1.wav");
		} catch (final IOException e) {
			Debug.e(e);
		}
	}

	@Override
	public void onLeave() {
		try {
			InformationPageScene.this.mMusic.pause();
		} catch (Exception e) {

		}
		
	}

	@Override
	public void onBack() {
		try {
			InformationPageScene.this.mMusic.seekTo(0);
			InformationPageScene.this.mMusic.play();
		} catch (Exception e) {
		}
	}

	@Override
	public void onCreateScene() {
		this.mMusic.play();
		
		
		mainSceneBackground = new SpriteBackground(
				new Sprite(0, 0, mBackground,
						this.getContext().getVertexBufferObjectManager()));
		InformationPageScene.this.setBackground(mainSceneBackground);
		
		
		int highestGrade = 0;
		BufferedInputStream Bufferedinput;
		try {
			Bufferedinput = new BufferedInputStream(this.getContext().openFileInput("Grade.data"));
			DataInputStream Datainput = new DataInputStream(Bufferedinput);
			highestGrade = Datainput.readInt();
			Datainput.close();
		} catch (IOException e) {
		}

		final Text endGrade = new Text(0, 0, this.mFont, "", "XXXXX".length(),
				this.getContext().getVertexBufferObjectManager());
		float endGradeX = 300 - endGrade.getWidth() / 2;
		float endGradeY = 240 - endGrade.getHeight() / 2;
		endGrade.setPosition(endGradeX, endGradeY);

		endGrade.setText("" + highestGrade);
		InformationPageScene.this.attachChild(endGrade);


	}
}
