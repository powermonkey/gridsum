package com.gridofsums.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by Rod on 8/7/2017.
 */

public class HowToPlayScreen implements Screen{
    GridOfSums game;
    String howTo;
    BitmapFont font20;
    OrthographicCamera cam;
    Table table, rootTable;
    Stage stage;
    TextureAtlas.AtlasRegion step1Region, step2Region, step3Region, grayTile;
    Image step1, step2, step3 ;
    NinePatch patchGray;
    NinePatchDrawable patchDrawableGray;

    public HowToPlayScreen(GridOfSums gam){
        this.game = gam;
        font20 = GameAssetLoader.font15;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, GridOfSums.WIDTH, GridOfSums.HEIGHT);
        grayTile = GameAssetLoader.blockGray;
        patchGray = new NinePatch(grayTile, 4, 4, 4, 4);
        patchDrawableGray = new NinePatchDrawable(patchGray);
        howTo = "Choose an empty cell. If all its neighbors are empty, it puts '1'." +
                " Each time you choose a cell, you add up its neighbors and put the total in the cell you chose. ";
        step1Region = GameAssetLoader.step1;
        step2Region = GameAssetLoader.step2;
        step3Region = GameAssetLoader.step3;

        step1 = new Image(step1Region);
        step2 = new Image(step2Region);
        step3 = new Image(step3Region);

        stage = new Stage(new FitViewport(480, 800), game.batch);
        Gdx.input.setInputProcessor(stage);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font20;
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font20;
        buttonStyle.up = patchDrawableGray;
        buttonStyle.down = patchDrawableGray;

        Label step1Label = new Label("Clicking on empty cell in upper left will put 1.", labelStyle);
        Label step2Label = new Label("Clicking on empty cell in upper right will put 1.", labelStyle);
        Label step3Label = new Label("Clicking on empty cell in the middle will put 2.", labelStyle);

        TextButton okay = new TextButton("OKAY", buttonStyle);

        okay.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainMenuScreen(game));
                return true;
            }
        });

        table = new Table();
        rootTable = new Table();
        rootTable.setFillParent(true);

        table.add(step1Label).pad(10);
        table.row();
        table.add(step1);
        table.row();
        table.add(step2Label).pad(10);
        table.row();
        table.add(step2);
        table.row();
        table.add(step3Label).pad(10);
        table.row();
        table.add(step3);
        table.row();
        table.add(okay).width(100).height(40).pad(15);
        table.row();
        rootTable.add(table);
        rootTable.center().bottom().padBottom(15);
        stage.addActor(rootTable);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.35f, .35f, .35f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.graphics.requestRendering();
        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        font20.draw(game.batch, howTo, 5, 700, 470, Align.center, true);
        game.batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        font20.dispose();
    }
}
