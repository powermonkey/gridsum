package com.gridofsums.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GridOfSums extends Game {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public static final String TITLE = "TapRunner";
	SpriteBatch batch;

	public AdsController adsController;

	public GridOfSums(AdsController adsController){
		if (adsController != null) {
			this.adsController = adsController;
		} else {
			this.adsController = new DummyController();
		}
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		GameAssetLoader.load();
		GameAssetLoader.getLoadedAssets();
		Gdx.graphics.setContinuousRendering(false);
		this.setScreen(new MainMenuScreen(this));
		if(adsController.isWifiConnected()) {
			adsController.showBannerAd();
		}
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
