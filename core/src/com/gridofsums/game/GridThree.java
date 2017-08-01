package com.gridofsums.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by Rod on 7/31/2017.
 */

public class GridThree {
    final static int GRID_X = 3;
    final static int GRID_Y = 3;
    int[][] gridField;
    Texture blueTile, grayTile;
    NinePatch patchBlue, patchGray;
    NinePatchDrawable patchDrawableBlue, patchDrawableGray;
    Table rootTable, table;
    Stage stage;
    BitmapFont font;
    Label tile[][];
    GridOfSums game;
    int tileSum;

    public GridThree(GridOfSums gam){
        this.game = gam;
        gridField = new int[GRID_X][GRID_Y];
        blueTile = new Texture("Block_Type2_Blue.png");
        grayTile = new Texture("Block_Type2_Gray.png");

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
        tile = new Label[3][3];

//        stage.setDebugAll(true);

        int ctr = 1;
        for(int tileY = (GRID_Y - 1) ; tileY >= 0; tileY--){
            for(int tileX = 0; tileX < GRID_X; tileX++){
                final int xTile = tileX;
                final int yTile = tileY;
                tile[xTile][yTile] = new Label("tile"+ctr,tileStyle); //TODO: change concatenation to append()
                gridField[xTile][yTile] = 0;
                tile[xTile][yTile].addListener(new InputListener(){
                    @Override
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        tileSum = 0;
                        updateGrid();
                        tileSum = tilesNear(xTile, yTile);
                        if(tileSum > 0) {
                            gridField[xTile][yTile] = tileSum;
                            tile[xTile][yTile].setText(String.valueOf(tileSum));
                        }else{
                            gridField[xTile][yTile] = 1;
                            tile[xTile][yTile].setText(String.valueOf(1));
                        }

                        return true;
                    }

                    @Override
                    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                        tile[xTile][yTile].clearListeners();
                    }
                });
                table.add(tile[xTile][yTile]).center().pad(5);
                ctr++;
            }
            table.row();
        }
        table.setBackground(patchDrawableGray);
        rootTable.add(table).center().center();
        stage.addActor(rootTable);
        Gdx.input.setInputProcessor(stage);
    }

    public void updateGrid(){

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
        if(x >= 0 && x < GRID_X && y >= 0 && y < GRID_Y){
            return gridField[x][y];
        }else{
            return 0;
        }
    }

    public void render(float delta){
        stage.act(delta);
        stage.draw();
    }
}
