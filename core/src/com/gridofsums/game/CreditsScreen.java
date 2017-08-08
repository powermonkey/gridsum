package com.gridofsums.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by Rod on 8/8/2017.
 */

public class CreditsScreen implements Screen{
    GridOfSums game;
    BitmapFont font;
    Stage stage;
    Table table1, table2, table3, rootTable;
    OrthographicCamera cam;
    TextureAtlas.AtlasRegion grayTile, blueTile;
    NinePatch patchGray, patchBlue;
    NinePatchDrawable patchDrawableGray, patchDrawableBlue;

    public CreditsScreen(GridOfSums gam){
        this.game = gam;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, GridOfSums.WIDTH, GridOfSums.HEIGHT);
        grayTile = GameAssetLoader.blockGray;
        blueTile = GameAssetLoader.blockBlue;
        font = GameAssetLoader.font20;
        patchGray = new NinePatch(grayTile, 4, 4, 4, 4);
        patchBlue = new NinePatch(blueTile, 4, 4, 4, 4);
        patchDrawableBlue = new NinePatchDrawable(patchBlue);
        patchDrawableGray = new NinePatchDrawable(patchGray);

        stage = new Stage(new FitViewport(480, 800), game.batch);
        Gdx.input.setInputProcessor(stage);
        rootTable = new Table();
        rootTable.setFillParent(true);
        table1 = new Table();
        table2 = new Table();
        table3 = new Table();


        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        //Tile textures
        Label tilesLabel = new Label("Tile Textures by ", labelStyle);
        Label tilesCredit = new Label("Master484", labelStyle);
        Label tilesLicense1 = new Label("CC-BY 3.0", labelStyle);
        Label tilesLicense2 = new Label("OGA-BY 3.0", labelStyle);

        //Icons
        Label iconsLabel = new Label("Icons by ", labelStyle);
        Label iconsCredit = new Label("Kenny", labelStyle);
        Label iconsLicense = new Label("CC0", labelStyle);

        //Fonts
        Label fontsLabel = new Label("Fonts by ", labelStyle);
        Label fontsCredit1 = new Label("Codeman38", labelStyle);
        Label fontsCredit2 = new Label("Clear Sans", labelStyle);

        //Tester
        Label testerLabel = new Label("Tester", labelStyle);
        Label testerCredit = new Label("Donna Marie", labelStyle);

        //okay button
        Label okay = new Label("OKAY", labelStyle);
        okay.setAlignment(Align.center);

        tilesCredit.setColor(Color.BLUE);
        tilesLicense1.setColor(Color.BLUE);
        tilesLicense2.setColor(Color.BLUE);

        iconsCredit.setColor(Color.BLUE);
        iconsLicense.setColor(Color.BLUE);

        fontsCredit1.setColor(Color.BLUE);
        fontsCredit2.setColor(Color.BLUE);

        testerCredit.setColor(Color.BLUE);

        tilesCredit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("http://m484games.ucoz.com/");
            }
        });
        tilesLicense1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("https://creativecommons.org/licenses/by/3.0/");
            }
        });
        tilesLicense2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("http://static.opengameart.org/OGA-BY-3.0.txt");
            }
        });

        iconsCredit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("http://www.kenney.nl/");
            }
        });
        iconsLicense.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("https://creativecommons.org/publicdomain/zero/1.0/");
            }
        });

        fontsCredit1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("http://www.zone38.net/");
            }
        });
        fontsCredit2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("https://01.org/clear-SANS/");
            }
        });

        okay.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainMenuScreen(game));
                return true;
            }
        });

        table1.add(tilesLabel);
        table1.row();
        table1.add(tilesCredit);
        table1.row();
        table1.add(tilesLicense1);
        table1.row();
        table1.add(tilesLicense2).padBottom(30);
        table1.row();

        table1.add(iconsLabel);
        table1.row();
        table1.add(iconsCredit);
        table1.row();
        table1.add(iconsLicense).padBottom(30);
        table1.row();

        table1.add(fontsLabel);
        table1.row();
        table1.add(fontsCredit1);
        table1.row();
        table1.add(fontsCredit2).padBottom(30);
        table1.row();

        table1.add(testerLabel);
        table1.row();
        table1.add(testerCredit);
        table1.row();
        table1.center().center().pad(50);
        table1.setBackground(patchDrawableBlue);

        table2.add(table1);
        table2.setBackground(patchDrawableGray);

        table3.add(okay).width(100).height(50).center().center();
        table3.row();
        table3.setBackground(patchDrawableGray);

        rootTable.add(table2).fillX();
        rootTable.row();
        rootTable.add(table3).pad(10);
        rootTable.row();
        rootTable.center().center();

        stage.addActor(rootTable);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.35f, .35f, .35f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(cam.combined);
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
        font.dispose();
    }
}
