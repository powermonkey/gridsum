package com.gridofsums.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by Rod on 7/31/2017.
 */

public class Grid {
    int gridX;
    int gridY;
    int[][] gridField;
    Texture blueTile, grayTile, goBackImage, newGridImage, exitImage;
    NinePatch patchBlue, patchGray;
    NinePatchDrawable patchDrawableBlue, patchDrawableGray;
    ImageButton goBack, newGrid, exit;
    Table rootTable, table, menuTable, scoreBoardTable;
    Stage stage;
    BitmapFont font;
    Label tile[][], currentHigh, bestHigh;
    final GridOfSums game;
    int tileSum, tileWidth, tileHeight, largestTile;
    final int size;

    public Grid(GridOfSums gam, int size){
        this.game = gam;
        this.size = size;
        gridX = size;
        gridY = size;
        tileWidth = setTileWidth(size);
        tileHeight = setTileHeight(size);
        gridField = new int[gridX][gridY];
        blueTile = new Texture("Block_Type2_Blue.png");
        grayTile = new Texture("Block_Type2_Gray.png");
        goBackImage = new Texture("arrowLeft.png");
        newGridImage = new Texture("return.png");
        exitImage = new Texture("door.png");

        font = new BitmapFont();
        patchBlue = new NinePatch(blueTile, 4, 4, 4, 4);
        patchGray = new NinePatch(grayTile, 4, 4, 4, 4);
        patchDrawableBlue = new NinePatchDrawable(patchBlue);
        patchDrawableGray = new NinePatchDrawable(patchGray);

        rootTable = new Table();
        rootTable.setFillParent(true);
        table = new Table();
        stage = new Stage(new FitViewport(480, 800), game.batch);
        Label.LabelStyle tileStyle = new Label.LabelStyle(font, null);
        tileStyle.background = patchDrawableBlue;
        tile = new Label[gridX][gridY];
        largestTile = 0;
        menuTable = new Table();
        scoreBoardTable = new Table();

        Label.LabelStyle scoreStyle = new Label.LabelStyle(font, null);
        Label.LabelStyle scoreTileStyle = new Label.LabelStyle(font, null);
        Label currentHighLabel = new Label("HIGHEST", scoreStyle);
        Label bestHighLabel = new Label("BEST", scoreStyle);
        currentHighLabel.setAlignment(Align.center);
        bestHighLabel.setAlignment(Align.center);
        currentHigh = new Label("", scoreTileStyle);
        bestHigh = new Label("", scoreTileStyle);

        currentHigh.setAlignment(Align.center);
        bestHigh.setAlignment(Align.center);

//        stage.setDebugAll(true);

        for(int tileY = (gridY - 1) ; tileY >= 0; tileY--){
            for(int tileX = 0; tileX < gridX; tileX++){
                final int xTile = tileX;
                final int yTile = tileY;
                tile[xTile][yTile] = new Label(" ",tileStyle); //TODO: change concatenation to append()
                tile[xTile][yTile].setAlignment(Align.center);
                gridField[xTile][yTile] = 0;
                tile[xTile][yTile].addListener(new InputListener(){
                    @Override
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        tileSum = 0;
                        tileSum = tilesNear(xTile, yTile);
                        if(tileSum > 0) {
                            gridField[xTile][yTile] = tileSum;
                            tile[xTile][yTile].setText(String.valueOf(tileSum));
                        }else{
                            gridField[xTile][yTile] = 1;
                            tile[xTile][yTile].setText(String.valueOf(1));
                        }

                        //get largest tile
                        largestTile = getLargestTile();
                        currentHigh.setText(Integer.toString(largestTile));
                        //show largest tile after all tiles are clicked

                        return true;
                    }

                    @Override
                    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                        tile[xTile][yTile].clearListeners();
                    }
                });
                table.add(tile[xTile][yTile]).center().width(tileWidth).height(tileHeight);
            }
            table.row();
        }
        table.pad(10);
        table.setBackground(patchDrawableGray);

        Table highTile = new Table();
        Table bestTile = new Table();

        highTile.add(currentHighLabel).padTop(10);
        highTile.row();
        highTile.add(currentHigh).width(100).height(50);
        highTile.row();
        highTile.setBackground(patchDrawableGray);

        bestTile.add(bestHighLabel).padTop(10);
        bestTile.row();
        bestTile.add(bestHigh).width(100).height(50);
        bestTile.row();
        bestTile.setBackground(patchDrawableGray);

        scoreBoardTable.add(highTile);
        scoreBoardTable.add(bestTile);
        scoreBoardTable.row();

//        scoreBoardTable.add(currentHighLabel).fill().expand();
//        scoreBoardTable.add(bestHighLabel).fill().expand();
//        scoreBoardTable.row();
//        scoreBoardTable.add(currentHigh).width(100).height(70);
//        scoreBoardTable.add(bestHigh).width(100).height(70);
//        scoreBoardTable.row();
//        scoreBoardTable.setBackground(patchDrawableGray);

        goBack = new ImageButton(new TextureRegionDrawable(new TextureRegion(goBackImage)));
        newGrid = new ImageButton(new TextureRegionDrawable(new TextureRegion(newGridImage)));
        exit = new ImageButton(new TextureRegionDrawable(new TextureRegion(exitImage)));

        goBack.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainMenuScreen(game));
                return true;
            }
        });

        final int gridSize = size;
        newGrid.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game, gridSize));
                return true;
            }
        });

        exit.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });

        menuTable.add(goBack).space(20);
        menuTable.add(newGrid).space(20);
        menuTable.add(exit).space(20);
        menuTable.setBackground(patchDrawableGray);

        rootTable.add(scoreBoardTable).right().padBottom(20);
        rootTable.row();
        rootTable.add(table);
        rootTable.row();
        rootTable.add(menuTable).padTop(30);
        rootTable.row();
        stage.addActor(rootTable);
        Gdx.input.setInputProcessor(stage);
    }

    public int tilesNear(int x, int y){
        int sum = 0;

        sum += tileAt(x - 1,y - 1);  // SW
        sum += tileAt(x, y - 1);      // S
        sum += tileAt(x + 1, y - 1);  // SE
        sum += tileAt(x - 1, y);      // W
        sum += tileAt(x + 1, y);      // E
        sum += tileAt(x - 1, y + 1);  // NW
        sum += tileAt(x, y + 1);      // N
        sum += tileAt(x + 1, y + 1);  // NE

        return sum;

    }

    public int tileAt(int x, int y){
        if(x >= 0 && x < gridX && y >= 0 && y < gridY){
            return gridField[x][y];
        }else{
            return 0;
        }
    }

    public int setTileWidth(int size){
        int width;
        switch(size){
            case 3:
                width = 130;
                break;
            case 4:
                width = 100;
                break;
            case 5:
                width = 80;
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
                height = 130;
                break;
            case 4:
                height = 100;
                break;
            case 5:
                height = 80;
                break;
            default:
                throw new IllegalArgumentException("No such tile height size");
        }
        return height;
    }

    public int getLargestTile(){
        for(int tileY = (gridY - 1) ; tileY >= 0; tileY--) {
            for (int tileX = 0; tileX < gridX; tileX++) {
                if(gridField[tileX][tileY] >= largestTile){
                    largestTile = gridField[tileX][tileY];
                }
            }
        }
        return largestTile;
    }

    public void render(float delta){
        stage.act(delta);
        stage.draw();
    }

    public void dispose(){

    }
}
