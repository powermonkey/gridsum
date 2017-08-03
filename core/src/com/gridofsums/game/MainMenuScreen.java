package com.gridofsums.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by Rod on 7/31/2017.
 */

public class MainMenuScreen implements Screen{
    final GridOfSums game;
    OrthographicCamera cam;
    Texture blueTile, grayTile, forwardImage, backwardImage;
    NinePatch patchBlue, patchGray;
    NinePatchDrawable patchDrawableBlue, patchDrawableGray;
    Table rootTable, tableScroller, scrollTable, table, tableThree, tableFour, tableFive, menuTable;
    Stage stage;
    BitmapFont font;
    Label tile[][], start;
    Label.LabelStyle tileStyle;
    ScrollPane scroller;
    ImageButton forward, backward;
    float scrollX;
    int[] gridSize;
    int sizeCounter, sizeSelect;

    public MainMenuScreen(final GridOfSums gam){
        this.game = gam;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, GridOfSums.WIDTH, GridOfSums.HEIGHT);
        blueTile = new Texture("Block_Type2_Blue.png");
        grayTile = new Texture("Block_Type2_Gray.png");
        forwardImage = new Texture("forward.png");
        backwardImage = new Texture("backward.png");
        font = new BitmapFont();
        patchBlue = new NinePatch(blueTile, 4, 4, 4, 4);
        patchGray = new NinePatch(grayTile, 4, 4, 4, 4);
        patchDrawableBlue = new NinePatchDrawable(patchBlue);
        patchDrawableGray = new NinePatchDrawable(patchGray);
        rootTable = new Table();
        rootTable.setFillParent(true);
        stage = new Stage(new FitViewport(480, 800), game.batch);
        scrollX = 0;
        gridSize = new int[]{3, 4, 5};
        sizeCounter = 0;
        sizeSelect = 3;

        tileStyle = new Label.LabelStyle(font, null);
        tileStyle.background = patchDrawableBlue;

//        stage.setDebugAll(true);

        tableThree = new Table();
        tableFour = new Table();
        tableFive = new Table();

        tableThree = createTable(3, 3);
        tableFour = createTable(4, 4);
        tableFive = createTable(5, 5);

        scrollTable = new Table();
        scrollTable.add(tableThree).space(50).padBottom(15);
        scrollTable.add(tableFour).space(50).padBottom(15);
        scrollTable.add(tableFive).space(50).padBottom(15);
        scrollTable.row();

        scroller = new ScrollPane(scrollTable);
        scroller.setOverscroll(false, false);
        scroller.setTouchable(Touchable.disabled);

        forward = new ImageButton(new TextureRegionDrawable(new TextureRegion(forwardImage)));
        backward = new ImageButton(new TextureRegionDrawable(new TextureRegion(backwardImage)));
        backward.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(scrollX > 0) {
                    scrollX -= 298;
                    sizeSelect = gridSize[sizeCounter = sizeCounter - 1];
                }
                scroller.scrollTo(scrollX,0,248,0,true,false);
                return true;
            }
        });
        forward.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(scrollX < 546) {
                    scrollX += 298;
                    sizeSelect = gridSize[sizeCounter = sizeCounter + 1];
                }
                scroller.scrollTo(scrollX,0,248,0,true,false);
                return true;
            }
        });

        tableScroller = new Table();
        menuTable = new Table();

        tableScroller.add(backward);
        tableScroller.add(scroller).width(248).height(320);
        tableScroller.add(forward);
        tableScroller.row();

        Label.LabelStyle menuLabelStyle = new Label.LabelStyle(font, null);
        menuLabelStyle.background = patchDrawableGray;
        start = new Label("START", menuLabelStyle);
        start.setAlignment(Align.center);
        start.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game, sizeSelect));
                return true;
            }
        });
        menuTable.add(start).center();

        rootTable.add(tableScroller);
        rootTable.row();
        rootTable.add(menuTable).padTop(20);
        rootTable.row();
        stage.addActor(rootTable);

    }

    public Table createTable(int x, int y){
        table = new Table();
        Table outerTable = new Table();
        Table labelTable = new Table();
        int tileWidth = setTileWidth(x);
        int tileHeight = setTileHeight(y);
        tile = new Label[x][y];
        for(int tileY = (y - 1) ; tileY >= 0; tileY--) {
            for (int tileX = 0; tileX < x; tileX++) {
                final int xTile = tileX;
                final int yTile = tileY;
                tile[xTile][yTile] = new Label(" ", tileStyle); //TODO: change concatenation to append()
                tile[xTile][yTile].setAlignment(Align.center);
                table.add(tile[xTile][yTile]).center().width(tileWidth).height(tileHeight);
            }
            table.row();
        }
        Label.LabelStyle gridLabelStyle = new Label.LabelStyle(font, null);
        Label gridLabel = new Label(x+" X "+y, gridLabelStyle);
        labelTable.add(gridLabel).center().colspan(x).padTop(20);
        labelTable.row();

        table.setBackground(patchDrawableGray);
        outerTable.add(table);
        outerTable.row();
        outerTable.add(labelTable);
        return outerTable;
    }

    public int setTileWidth(int size){
        int width;
        switch(size){
            case 3:
                width = 80;
                break;
            case 4:
                width = 60;
                break;
            case 5:
                width = 48;
                break;
            default:
                throw new IllegalArgumentException("No such tile width size");
        }
        return width;
    }

    public int setTileHeight(int size){
        int height;
        switch(size){
            case 3:
                height = 80;
                break;
            case 4:
                height = 60;
                break;
            case 5:
                height = 48;
                break;
            default:
                throw new IllegalArgumentException("No such tile height size");
        }
        return height;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.65f, .65f, .65f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(cam.combined);
        Gdx.input.setInputProcessor(stage);
        stage.act(delta);
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

    }
}
