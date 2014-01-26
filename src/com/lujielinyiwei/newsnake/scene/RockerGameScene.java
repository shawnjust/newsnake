package com.lujielinyiwei.newsnake.scene;

import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.opengl.GLES20;

import com.lujielinyiwei.newsnake.StartpageActivity;
import com.lujielinyiwei.newsnake.staticdata.StaticData;

public class RockerGameScene extends BaseGameScene {

	public RockerGameScene(StartpageActivity context) {
		super(context);
	}

	private BitmapTextureAtlas mOnScreenControlTexture;

	private ITextureRegion mOnScreenControlBaseTextureRegion;
	private ITextureRegion mOnScreenControlKnobTextureRegion;

	@Override
	public void onCreateResources() {
		super.onCreateResources();
		this.mOnScreenControlTexture = new BitmapTextureAtlas(this.getContext()
				.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		this.mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mOnScreenControlTexture,
						this.getContext(), "onscreen_control_base_new.png", 0,
						0);
		this.mOnScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mOnScreenControlTexture,
						this.getContext(), "onscreen_control_knob_new.png",
						128, 0);
		this.mOnScreenControlTexture.load();
	}

	AnalogOnScreenControl analogOnScreenControl;

	@Override
	protected void initControler() {
		analogOnScreenControl = new AnalogOnScreenControl(
				StaticData.CAMERA_WIDTH
						- this.mOnScreenControlBaseTextureRegion.getWidth()
						- 70, StaticData.CAMERA_HEIGHT
						- this.mOnScreenControlBaseTextureRegion.getHeight()
						- 40, this.getCamera(),
				this.mOnScreenControlBaseTextureRegion,
				this.mOnScreenControlKnobTextureRegion, 0.1f, 200, this
						.getContext().getVertexBufferObjectManager(),
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

		RockerGameScene.this.setChildScene(analogOnScreenControl);
		// BaseGameScene.this.attachChild(analogOnScreenControl);
		// BaseGameScene.this.registerTouchArea(analogOnScreenControl);
	}

	@Override
	protected boolean isFoodPositionAvailable(float x, float y) {
		return (x < 560 || y < 220);
	}

	@Override
	protected void continueGame() {
		super.continueGame();
		RockerGameScene.this.clearChildScene();
		RockerGameScene.this.setChildScene(analogOnScreenControl);
		pauseButton.setAlpha(1f);
	}
}
