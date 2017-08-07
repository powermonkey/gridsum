package com.gridofsums.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by Rod on 8/7/2017.
 */

public class GameAssetLoader {
    public static TextureAtlas atlas;
    public static TextureAtlas.AtlasRegion blockYellow, blockBlue, blockGray, arrowLeft, arrowRight, backward, door, refresh;
    public static BitmapFont font15, font20, font25, font30, font32, font40;
    static GameAssetManager manager;

    public static void load() {
        manager = new GameAssetManager();
        manager.loadImages();
        manager.loadFonts();
        manager.manager.finishLoading();
    }

    public static void getLoadedAssets(){
        atlas = manager.manager.get("packedimages/gridofsums.atlas");
        blockBlue = atlas.findRegion("Block_Type2_Blue");
        blockGray = atlas.findRegion("Block_Type2_Gray");
        blockYellow = atlas.findRegion("Block_Type2_Yellow");
        arrowLeft = atlas.findRegion("backward");
        arrowRight = atlas.findRegion("forward");
        backward = atlas.findRegion("arrowLeft");
        door = atlas.findRegion("door");
        refresh = atlas.findRegion("refresh");

        font15 = manager.manager.get("fonts/clearsans15/clearsans.fnt");
        font20 = manager.manager.get("fonts/clearsans20/clearsans.fnt");
        font25 = manager.manager.get("fonts/clearsans25/clearsans.fnt");
        font30 = manager.manager.get("fonts/clearsans30/clearsans.fnt");
        font32 = manager.manager.get("fonts/clearsans32/clearsans.fnt");
        font40 = manager.manager.get("fonts/clearsans40/clearsans.fnt");
    }

    public static void dispose() {
        manager.dispose();
        font15.dispose();
        font20.dispose();
        font25.dispose();
        font30.dispose();
        font32.dispose();
        font40.dispose();
        atlas.dispose();
    }
}