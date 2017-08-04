package com.gridofsums.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
    Table rootTable, table, menuTable;
    Stage stage;
    BitmapFont font;
    Label tile[][], currentHigh, bestHigh, bestLow;
    final GridOfSums game;
    int tileSum, tileWidth, tileHeight, largestTile;
    final int size;
    Preferences prefs;

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
        Table scoreBoardTable = new Table();
        Table scoreBoardLeft = new Table();
        Table scoreBoardRight = new Table();

        Label.LabelStyle scoreStyle = new Label.LabelStyle(font, null);
        Label.LabelStyle scoreTileStyle = new Label.LabelStyle(font, null);
        Label currentHigestLabel = new Label("CURRENT", scoreStyle);
        Label bestLowestLabel = new Label("BEST\nLOWEST", scoreStyle);
        Label bestHighestLabel = new Label("BEST\nHIGHEST", scoreStyle);

        currentHigestLabel.setAlignment(Align.center);
        bestLowestLabel.setAlignment(Align.center);
        bestHighestLabel.setAlignment(Align.center);

        currentHigh = new Label("", scoreTileStyle);
        bestLow = new Label("", scoreTileStyle);
        bestHigh = new Label("", scoreTileStyle);

        currentHigh.setAlignment(Align.center);
        bestLow.setAlignment(Align.center);
        bestHigh.setAlignment(Align.center);

        prefs = Gdx.app.getPreferences("GridOfSums");

        switch(size){
            case 3:
                if (!prefs.contains("GridThreeBestHighest")) {
                    prefs.putInteger("GridThreeBestHighest", 0);
                    prefs.flush();
                    bestHigh.setText(Integer.toString(prefs.getInteger("GridThreeBestHighest")));
                } else {
                    bestHigh.setText(Integer.toString(prefs.getInteger("GridThreeBestHighest")));
                }

                if (!prefs.contains("GridThreeBestLowest")) {
                    prefs.putInteger("GridThreeBestLowest", 0);
                    prefs.flush();
                    if(prefs.getInteger("GridThreeBestLowest") == 0){
                        bestLow.setText("N/A");
                    } else{
                        bestLow.setText(Integer.toString(prefs.getInteger("GridThreeBestLowest")));
                    }
                } else {
                    if(prefs.getInteger("GridThreeBestLowest") == 0){
                        bestLow.setText("N/A");
                    } else{
                        bestLow.setText(Integer.toString(prefs.getInteger("GridThreeBestLowest")));
                    }
                }
                break;
            case 4:
                if (!prefs.contains("GridFourBestHighest")) {
                    prefs.putInteger("GridFourBestHighest", 0);
                    prefs.flush();
                    bestHigh.setText(Integer.toString(prefs.getInteger("GridFourBestHighest")));
                } else {
                    bestHigh.setText(Integer.toString(prefs.getInteger("GridFourBestHighest")));
                }

                if (!prefs.contains("GridFourBestLowest")) {
                    prefs.putInteger("GridFourBestLowest", 0);
                    prefs.flush();
                    if(prefs.getInteger("GridFourBestLowest") == 0){
                        bestLow.setText("N/A");
                    } else {
                        bestLow.setText(Integer.toString(prefs.getInteger("GridFourBestLowest")));
                    }
                } else {
                    if(prefs.getInteger("GridFourBestLowest") == 0){
                        bestLow.setText("N/A");
                    } else {
                        bestLow.setText(Integer.toString(prefs.getInteger("GridFourBestLowest")));
                    }
                }
                break;
            case 5:
                if (!prefs.contains("GridFiveBestHighest")) {
                    prefs.putInteger("GridFiveBestHighest", 0);
                    prefs.flush();
                    bestHigh.setText(Integer.toString(prefs.getInteger("GridFiveBestHighest")));
                } else {
                    bestHigh.setText(Integer.toString(prefs.getInteger("GridFiveBestHighest")));
                }

                if (!prefs.contains("GridFiveBestLowest")) {
                    prefs.putInteger("GridFiveBestLowest", 0);
                    prefs.flush();
                    if(prefs.getInteger("GridFiveBestLowest") == 0){
                        bestLow.setText("N/A");
                    } else {
                        bestLow.setText(Integer.toString(prefs.getInteger("GridFiveBestLowest")));
                    }
                } else {
                    if(prefs.getInteger("GridFiveBestLowest") == 0){
                        bestLow.setText("N/A");
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
                        if(allTilesFilled()){
                            if (largestTile > getBestHighestScore(gridSize)) {
                                setBestHighestScore(gridSize, largestTile);
                                bestHigh.setText(Integer.toString(largestTile));
                            }

                            if(getBestLowestScore(gridSize) == 0){
                                setBestLowestScore(gridSize, getBestHighestScore(gridSize));
                                bestLow.setText(Integer.toString(largestTile));
                            } else if (largestTile < getBestLowestScore(gridSize)) {
                                setBestLowestScore(gridSize, largestTile);
                                bestLow.setText(Integer.toString(largestTile));
                            }

                        }
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

        Table currentHighTile = new Table();
        Table bestLowestTile = new Table();
        Table bestHighestTile = new Table();

        currentHighTile.add(currentHigestLabel).padTop(10);
        currentHighTile.row();
        currentHighTile.add(currentHigh).width(100).height(50);
        currentHighTile.row();
        currentHighTile.setBackground(patchDrawableGray);

        bestLowestTile.add(bestLowestLabel).padTop(10);
        bestLowestTile.row();
        bestLowestTile.add(bestLow).width(100).height(50);
        bestLowestTile.row();
        bestLowestTile.setBackground(patchDrawableGray);

        bestHighestTile.add(bestHighestLabel).padTop(10);
        bestHighestTile.row();
        bestHighestTile.add(bestHigh).width(100).height(50);
        bestHighestTile.row();
        bestHighestTile.setBackground(patchDrawableGray);

        scoreBoardLeft.add(currentHighTile).fill().expand();
        scoreBoardLeft.row();

        scoreBoardRight.add(bestLowestTile).fill().expandX();
        scoreBoardRight.add(bestHighestTile).fill().expandX();
        scoreBoardRight.row();

        scoreBoardTable.add(scoreBoardLeft).width(120).fill().expandX().center().left();
        scoreBoardTable.add(scoreBoardRight).width(220).fill().expandX().center().right();
        scoreBoardTable.row();

        goBack = new ImageButton(new TextureRegionDrawable(new TextureRegion(goBackImage)));
        newGrid = new ImageButton(new TextureRegionDrawable(new TextureRegion(newGridImage)));
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

        exit.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                dispose();
                return true;
            }
        });

        menuTable.add(goBack).space(20);
        menuTable.add(newGrid).space(20);
        menuTable.add(exit).space(20);
        menuTable.setBackground(patchDrawableGray);

        rootTable.add(scoreBoardTable).padBottom(20).fill();
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

    public boolean allTilesFilled(){
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

    public int getBestHighestScore(int size){
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

    public void setBestHighestScore(int size, int score){
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

    public int getBestLowestScore(int size){
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

    public void setBestLowestScore(int size, int score){
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

    public void dispose(){
        stage.dispose();
        blueTile.dispose();
        grayTile.dispose();
        goBackImage.dispose();
        newGridImage.dispose();
        exitImage.dispose();
        font.dispose();
    }
}
