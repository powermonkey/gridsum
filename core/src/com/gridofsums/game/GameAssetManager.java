package com.gridofsums.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by Rod on 8/7/2017.
 */

public class GameAssetManager {
    public final AssetManager manager = new AssetManager();
    public final String imagesPack = "packedimages/gridofsums.atlas";
    public final String font15 = "fonts/clearsans15/clearsans.fnt";
    public final String font20 = "fonts/clearsans20/clearsans.fnt";
    public final String font25 = "fonts/clearsans25/clearsans.fnt";
    public final String font30 = "fonts/clearsans30/clearsans.fnt";
    public final String font32 = "fonts/clearsans32/clearsans.fnt";
    public final String font40 = "fonts/clearsans40/clearsans.fnt";

    public void loadImages(){
        manager.load(imagesPack, TextureAtlas.class);
    }

    public void loadFonts(){
        manager.load(font15, BitmapFont.class);
        manager.load(font20, BitmapFont.class);
        manager.load(font25, BitmapFont.class);
        manager.load(font30, BitmapFont.class);
        manager.load(font32, BitmapFont.class);
        manager.load(font40, BitmapFont.class);
    }

    public void dispose(){
        manager.dispose();
    }
}
