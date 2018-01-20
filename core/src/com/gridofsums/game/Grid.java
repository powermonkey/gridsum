package com.gridofsums.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
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

import java.util.ArrayDeque;

/**
 * Created by Rod on 7/31/2017.
 */

public class Grid {
    int gridX;
    int gridY;
    int[][] gridField;
    TextureAtlas.AtlasRegion blueTile, grayTile, goBackImage, newGridImage, undoImage, exitImage, yellowTile;
    NinePatch patchBlue, patchGray, patchYellow;
    NinePatchDrawable patchDrawableBlue, patchDrawableGray, patchDrawableYellow;
    ImageButton goBack, newGrid, exit, undoMove;
    Table rootTable, table, menuTable;
    Stage stage;
    BitmapFont font15, font32, font20, font25, font30;
    Label tile[][], currentHigh, bestHigh, bestLow;
    final GridOfSums game;
    int tileSum, tileWidth, tileHeight, largestTile, lastTouchedTileX, lastTouchedTileY;
    final int size;
    Preferences prefs;
    boolean isTouched;
    ArrayDeque<UndoCoordinates> undoStack;
    UndoCoordinates undoCoordinates;
    final Label.LabelStyle tileStyle15;

    public Grid(GridOfSums gam, int size){
        this.game = gam;
        this.size = size;
        gridX = size;
        gridY = size;
        tileWidth = getTileWidth(size);
        tileHeight = getTileHeight(size);
        gridField = new int[gridX][gridY]; //contains grid values
        blueTile = GameAssetLoader.blockBlue;
        grayTile = GameAssetLoader.blockGray;
        yellowTile = GameAssetLoader.blockYellow;
        goBackImage = GameAssetLoader.backward;
        newGridImage = GameAssetLoader.exitLeft;
        undoImage = GameAssetLoader.refresh;

        exitImage = GameAssetLoader.door;
        font15 = GameAssetLoader.font15;
        font32 = GameAssetLoader.font32;
        font20 = GameAssetLoader.font20;
        font25 = GameAssetLoader.font25;
        font30 = GameAssetLoader.font30;

        patchBlue = new NinePatch(blueTile, 4, 4, 4, 4);
        patchGray = new NinePatch(grayTile, 4, 4, 4, 4);
        patchYellow = new NinePatch(yellowTile, 4, 4, 4, 4);
        patchDrawableBlue = new NinePatchDrawable(patchBlue);
        patchDrawableGray = new NinePatchDrawable(patchGray);
        patchDrawableYellow = new NinePatchDrawable(patchYellow);

        lastTouchedTileX = 0;
        lastTouchedTileY = 0;

        rootTable = new Table();
        rootTable.setFillParent(true);
        table = new Table();
        stage = new Stage(new FitViewport(480, 800), game.batch);
        undoStack = new ArrayDeque<UndoCoordinates>();
        undoCoordinates = new UndoCoordinates();

        tileStyle15 = new Label.LabelStyle();
        tileStyle15.background = patchDrawableBlue;
        tileStyle15.font = font15;

        Label.LabelStyle tileStyle32 = new Label.LabelStyle();
        tileStyle32.background = patchDrawableBlue;
        tileStyle32.font = font32;

        Label.LabelStyle tileStyle20 = new Label.LabelStyle();
        tileStyle20.background = patchDrawableBlue;
        tileStyle20.font = font20;

        Label.LabelStyle tileStyle25 = new Label.LabelStyle();
        tileStyle25.background = patchDrawableBlue;
        tileStyle25.font = font25;

        Label.LabelStyle tileStyle30 = new Label.LabelStyle();
        tileStyle30.background = patchDrawableBlue;
        tileStyle30.font = font30;

        tile = new Label[gridX][gridY];
        largestTile = 0;
        menuTable = new Table();
        Table scoreBoardTable = new Table();
        Table scoreBoardLeft = new Table();
        Table scoreBoardRight = new Table();

        Label.LabelStyle scoreLabelStyle = new Label.LabelStyle(tileStyle15);
        scoreLabelStyle.background = null;
        Label.LabelStyle scoreStyle = new Label.LabelStyle(tileStyle20);
        scoreStyle.background = patchDrawableBlue;

        Label currentHigestLabel = new Label("CURRENT\nHIGHEST", scoreLabelStyle);
        Label bestLowestLabel = new Label("BEST\nLOWEST", scoreLabelStyle);
        Label bestHighestLabel = new Label("BEST\nHIGHEST", scoreLabelStyle);

        currentHigestLabel.setAlignment(Align.center);
        bestLowestLabel.setAlignment(Align.center);
        bestHighestLabel.setAlignment(Align.center);

        currentHigh = new Label("-", new Label.LabelStyle(scoreStyle));
        bestLow = new Label("-", new Label.LabelStyle(scoreStyle));
        bestHigh = new Label("-", new Label.LabelStyle(scoreStyle));

        currentHigh.setAlignment(Align.center);
        bestLow.setAlignment(Align.center);
        bestHigh.setAlignment(Align.center);

        prefs = Gdx.app.getPreferences("GridOfSums");

        switch(size){
            case 3:
                if (!prefs.contains("GridThreeBestHighest")) {
                    prefs.putInteger("GridThreeBestHighest", 0);
                    prefs.flush();
                    if(prefs.getInteger("GridThreeBestHighest") == 0) {
                        bestHigh.setText("-");
                    }
                } else {
                    if(prefs.getInteger("GridThreeBestHighest") == 0) {
                        bestHigh.setText("-");
                    } else {
                        bestHigh.setText(Integer.toString(prefs.getInteger("GridThreeBestHighest")));
                    }
                }

                if (!prefs.contains("GridThreeBestLowest")) {
                    prefs.putInteger("GridThreeBestLowest", 0);
                    prefs.flush();
                    if(prefs.getInteger("GridThreeBestLowest") == 0){
                        bestLow.setText("-");
                    } else{
                        bestLow.setText(Integer.toString(prefs.getInteger("GridThreeBestLowest")));
                    }
                } else {
                    if(prefs.getInteger("GridThreeBestLowest") == 0){
                        bestLow.setText("-");
                    } else{
                        bestLow.setText(Integer.toString(prefs.getInteger("GridThreeBestLowest")));
                    }
                }
                break;
            case 4:
                if (!prefs.contains("GridFourBestHighest")) {
                    prefs.putInteger("GridFourBestHighest", 0);
                    prefs.flush();
                    if(prefs.getInteger("GridFourBestHighest") == 0) {
                        bestHigh.setText("-");
                    }
                } else {
                    if(prefs.getInteger("GridFourBestHighest") == 0) {
                        bestHigh.setText("-");
                    } else {
                        bestHigh.setText(Integer.toString(prefs.getInteger("GridFourBestHighest")));
                    }
                }

                if (!prefs.contains("GridFourBestLowest")) {
                    prefs.putInteger("GridFourBestLowest", 0);
                    prefs.flush();
                    if(prefs.getInteger("GridFourBestLowest") == 0){
                        bestLow.setText("-");
                    } else {
                        bestLow.setText(Integer.toString(prefs.getInteger("GridFourBestLowest")));
                    }
                } else {
                    if(prefs.getInteger("GridFourBestLowest") == 0){
                        bestLow.setText("-");
                    } else {
                        bestLow.setText(Integer.toString(prefs.getInteger("GridFourBestLowest")));
                    }
                }
                break;
            case 5:
                if (!prefs.contains("GridFiveBestHighest")) {
                    prefs.putInteger("GridFiveBestHighest", 0);
                    prefs.flush();
                    if(prefs.getInteger("GridFiveBestHighest") == 0) {
                        bestHigh.setText("-");
                    }
                } else {
                    if(prefs.getInteger("GridFiveBestHighest") == 0) {
                        bestHigh.setText("-");
                    } else {
                        bestHigh.setText(Integer.toString(prefs.getInteger("GridFiveBestHighest")));
                    }
                }

                if (!prefs.contains("GridFiveBestLowest")) {
                    prefs.putInteger("GridFiveBestLowest", 0);
                    prefs.flush();
                    if(prefs.getInteger("GridFiveBestLowest") == 0){
                        bestLow.setText("-");
                    } else {
                        bestLow.setText(Integer.toString(prefs.getInteger("GridFiveBestLowest")));
                    }
                } else {
                    if(prefs.getInteger("GridFiveBestLowest") == 0){
                        bestLow.setText("-");
                    } else {
                        bestLow.setText(Integer.toString(prefs.getInteger("GridFiveBestLowest")));
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("No such size");
        }

//        stage.setDebugAll(true);

        final int gridSize = size;
        for(int tileY = (gridY - 1) ; tileY >= 0; tileY--){
            for(int tileX = 0; tileX < gridX; tileX++){
                final int xTile = tileX;
                final int yTile = tileY;
                final Label.LabelStyle tile15 = tileStyle15;
                final Label.LabelStyle tile32 = tileStyle32;
                final Label.LabelStyle tile20 = tileStyle20;
                final Label.LabelStyle tile25 = tileStyle25;
                final Label.LabelStyle tile30 = tileStyle30;
                tile[xTile][yTile] = new Label(" ", tileStyle15); //TODO: change concatenation to append()
                tile[xTile][yTile].setAlignment(Align.center);
                gridField[xTile][yTile] = 0;
                tile[xTile][yTile].addListener(new InputListener() {
                    @Override
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        isTouched = true;
                        return true;
                    }

                    @Override
                    public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        if(isTouched && gridField[xTile][yTile] == 0){
                            tileSum = 0;
                            tileSum = tilesNear(xTile, yTile);

                            //change font size in tiles depending on number length
                            int length = String.valueOf(tileSum).length();
                            switch (gridSize) {
                                case 3:
                                    tile[xTile][yTile].setStyle(new Label.LabelStyle(tile32));
                                    break;
                                case 4:
                                    if (length > 2) {
                                        tile[xTile][yTile].setStyle(new Label.LabelStyle(tile25));
                                    } else {
                                        tile[xTile][yTile].setStyle(new Label.LabelStyle(tile30));
                                    }
                                    break;
                                case 5:
                                    if (length > 5) {
                                        tile[xTile][yTile].setStyle(new Label.LabelStyle(tile15));
                                    } else if (length > 3) {
                                        tile[xTile][yTile].setStyle(new Label.LabelStyle(tile20));
                                    } else {
                                        tile[xTile][yTile].setStyle(new Label.LabelStyle(tile25));
                                    }
                                    break;
                                default:
                                    throw new IllegalArgumentException("No such grid size");
                            }

                            if (tileSum > 0) {
                                gridField[xTile][yTile] = tileSum;
                                tile[xTile][yTile].setText(String.valueOf(tileSum));
                            } else {
                                gridField[xTile][yTile] = 1;
                                tile[xTile][yTile].setText(String.valueOf(1));
                            }

                            //get largest tile
                            getLargestTile();

                            currentHigh.setText(Integer.toString(largestTile));
                            if (allTilesFilled()) {
                                if (largestTile > getBestHighestScore(gridSize)) {
                                    setBestHighestScore(gridSize, largestTile);
                                    bestHigh.setText(Integer.toString(largestTile));
                                    bestHigh.getStyle().background = patchDrawableYellow;
                                }

                                if (getBestLowestScore(gridSize) == 0) {
                                    setBestLowestScore(gridSize, getBestHighestScore(gridSize));
                                    bestLow.setText(Integer.toString(largestTile));
                                    bestLow.getStyle().background = patchDrawableYellow;
                                } else if (largestTile < getBestLowestScore(gridSize)) {
                                    setBestLowestScore(gridSize, largestTile);
                                    bestLow.setText(Integer.toString(largestTile));
                                    bestLow.getStyle().background = patchDrawableYellow;
                                }

                                //set to yellow current high
                                currentHigh.getStyle().background = patchDrawableYellow;

                                //set previously touched tile back to blue
                                tile[lastTouchedTileX][lastTouchedTileY].getStyle().background = patchDrawableBlue;

                                //show largest tile after all tiles are clicked
                                for (int ty = (gridY - 1); ty >= 0; ty--) {
                                    for (int tx = 0; tx < gridX; tx++) {
                                        if (gridField[tx][ty] == largestTile) {
                                            tile[tx][ty].getStyle().background = patchDrawableYellow;
                                        }
                                    }
                                }
                                undoMove.clearListeners();
                            } else {
                                //set previously touched tile back to blue
                                tile[lastTouchedTileX][lastTouchedTileY].getStyle().background = patchDrawableBlue;

                                //tile undo stack
                                undoCoordinates = new UndoCoordinates(); //TODO: change this to without creating new objects
                                undoCoordinates.setXtile(xTile);
                                undoCoordinates.setYtile(yTile);

                                undoStack.push(undoCoordinates);

                                //save coordinates for last touched tile
                                lastTouchedTileX = xTile;
                                lastTouchedTileY = yTile;

                                //mark tile yellow for currently clicked tile
                                tile[xTile][yTile].getStyle().background = patchDrawableYellow;
                            }
                        }
                    }

                    @Override
                    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//                        tile[xTile][yTile].clearListeners();
                        isTouched = false;
                    }
                });
                table.add(tile[xTile][yTile]).center().width(tileWidth).height(tileHeight);
            }
            table.row();
        }
        table.pad(5);
        table.setBackground(patchDrawableGray);

        Table currentHighTile = new Table();
        Table bestLowestTile = new Table();
        Table bestHighestTile = new Table();

        currentHighTile.add(currentHigestLabel).padTop(10);
        currentHighTile.row();
        currentHighTile.add(currentHigh).width(100).height(50).padTop(10);
        currentHighTile.row();
        currentHighTile.setBackground(patchDrawableGray);

        bestLowestTile.add(bestLowestLabel).padTop(10);
        bestLowestTile.row();
        bestLowestTile.add(bestLow).width(100).height(50).padTop(10);
        bestLowestTile.row();
        bestLowestTile.setBackground(patchDrawableGray);

        bestHighestTile.add(bestHighestLabel).padTop(10);
        bestHighestTile.row();
        bestHighestTile.add(bestHigh).width(100).height(50).padTop(10);
        bestHighestTile.row();
        bestHighestTile.setBackground(patchDrawableGray);

        scoreBoardLeft.add(currentHighTile).fill().expandX();
        scoreBoardLeft.row();

        scoreBoardRight.add(bestLowestTile).fill().expandX();
        scoreBoardRight.add(bestHighestTile).fill().expandX();
        scoreBoardRight.row();

        scoreBoardTable.add(scoreBoardLeft).width(110).fill().expandX().center().left();
        scoreBoardTable.add(scoreBoardRight).width(220).fill().expandX().center().right();
        scoreBoardTable.row();

        goBack = new ImageButton(new TextureRegionDrawable(new TextureRegion(goBackImage)));
        newGrid = new ImageButton(new TextureRegionDrawable(new TextureRegion(newGridImage)));
        undoMove = new ImageButton(new TextureRegionDrawable(new TextureRegion(undoImage)));
        exit = new ImageButton(new TextureRegionDrawable(new TextureRegion(exitImage)));

        goBack.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainMenuScreen(game));
                return true;
            }
        });

        newGrid.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game, gridSize));
                return true;
            }
        });

        undoMove.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(undoStack.size() > 0) {
                    UndoCoordinates undoCoordinates = undoStack.pop();
                    int xtile = undoCoordinates.getXTile();
                    int ytile = undoCoordinates.getYTile();
                    gridField[xtile][ytile] = 0;
                    largestTile = 0;
                    getLargestTile();
                    tile[xtile][ytile].setText(" ");
                    tile[xtile][ytile].getStyle().background = patchDrawableBlue;
                    if(undoStack.size() == 1) {
                        UndoCoordinates lastTouched = undoStack.peek();
                        tile[lastTouched.getXTile()][lastTouched.getYTile()].getStyle().background = patchDrawableYellow;
                        lastTouchedTileX = lastTouched.getXTile();
                        lastTouchedTileY = lastTouched.getYTile();
                    } else if(undoStack.size() > 1) {
                        UndoCoordinates lastTouched = undoStack.peek();
                        tile[lastTouched.getXTile()][lastTouched.getYTile()].getStyle().background = patchDrawableYellow;
                        lastTouchedTileX = lastTouched.getXTile();
                        lastTouchedTileY = lastTouched.getYTile();
                    }
                    //get largest tile
                    if(largestTile == 0) {
                        currentHigh.setText("-");
                    } else {
                        currentHigh.setText(Integer.toString(largestTile));
                    }
                }
                return true;
            }
        });

        exit.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                dispose();
                GameAssetLoader.dispose();
                return true;
            }
        });

        menuTable.add(exit).space(20).width(70).height(70);
        menuTable.add(goBack).space(20).width(70).height(70);
        menuTable.add(newGrid).space(20).width(70).height(70);
        menuTable.add(undoMove).space(20).width(70).height(70);
        menuTable.setBackground(patchDrawableGray);

        rootTable.add(scoreBoardTable).padBottom(20).fill().padTop(10);
        rootTable.row();
        rootTable.add(table);
        rootTable.row();
        rootTable.add(menuTable).padTop(30);
        rootTable.row();
        rootTable.center().bottom().padBottom(50);
        stage.addActor(rootTable);
        Gdx.input.setInputProcessor(stage);
    }

    private int tilesNear(int x, int y){
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

    private int tileAt(int x, int y){
        if(x >= 0 && x < gridX && y >= 0 && y < gridY){
            return gridField[x][y];
        }else{
            return 0;
        }
    }

    private int getTileWidth(int size){
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

    private int getTileHeight(int size){
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

    private void getLargestTile(){
        for(int tileY = (gridY - 1) ; tileY >= 0; tileY--) {
            for (int tileX = 0; tileX < gridX; tileX++) {
                if(gridField[tileX][tileY] >= largestTile){
                    largestTile = gridField[tileX][tileY];
                }
            }
        }
    }

    private boolean allTilesFilled(){
        int numTiles = gridX * gridY;
        int countTiles = 0;
        for(int tileY = (gridY - 1) ; tileY >= 0; tileY--) {
            for (int tileX = 0; tileX < gridX; tileX++) {
                if(gridField[tileX][tileY] > 0){
                    countTiles = countTiles + 1;
                    if(countTiles == numTiles){
                        return  true;
                    }
                }
            }
        }
        return false;
    }

    private int getBestHighestScore(int size){
        int highest;
        switch(size){
            case 3:
                highest = prefs.getInteger("GridThreeBestHighest");
                break;
            case 4:
                highest = prefs.getInteger("GridFourBestHighest");
                break;
            case 5:
                highest = prefs.getInteger("GridFiveBestHighest");
                break;
            default:
                throw new IllegalArgumentException("No such size");
        }
        return highest;
    }

    private void setBestHighestScore(int size, int score){
        switch(size){
            case 3:
                prefs.putInteger("GridThreeBestHighest", score);
                prefs.flush();
                break;
            case 4:
                prefs.putInteger("GridFourBestHighest", score);
                prefs.flush();
                break;
            case 5:
                prefs.putInteger("GridFiveBestHighest", score);
                prefs.flush();
                break;
            default:
                throw new IllegalArgumentException("No such size");
        }
    }

    private int getBestLowestScore(int size){
        int highest;
        switch(size){
            case 3:
                highest = prefs.getInteger("GridThreeBestLowest");
                break;
            case 4:
                highest = prefs.getInteger("GridFourBestLowest");
                break;
            case 5:
                highest = prefs.getInteger("GridFiveBestLowest");
                break;
            default:
                throw new IllegalArgumentException("No such size");
        }
        return highest;
    }

    private void setBestLowestScore(int size, int score){
        switch(size){
            case 3:
                prefs.putInteger("GridThreeBestLowest", score);
                prefs.flush();
                break;
            case 4:
                prefs.putInteger("GridFourBestLowest", score);
                prefs.flush();
                break;
            case 5:
                prefs.putInteger("GridFiveBestLowest", score);
                prefs.flush();
                break;
            default:
                throw new IllegalArgumentException("No such size");
        }
    }

    public void render(float delta){
        stage.act(delta);
        stage.draw();
    }

    private void dispose(){
        stage.dispose();
        font15.dispose();
        font20.dispose();
        font25.dispose();
        font30.dispose();
        font32.dispose();
    }

    class UndoCoordinates
    {
        public int xTile;
        public int yTile;

        public UndoCoordinates setXtile(int xTile) {
            this.xTile = xTile;
            return this;
        }

        public UndoCoordinates setYtile(int yTile) {
            this.yTile = yTile;

            return this;
        }

        public int getXTile() {
            return this.xTile;
        }

        public int getYTile() {
            return this.yTile;
        }
    }
}
