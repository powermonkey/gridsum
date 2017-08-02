package com.gridofsums.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Rod on 7/31/2017.
 */

public class GameScreen implements Screen{
    final GridOfSums game;
    Grid grid;
    OrthographicCamera cam;

    public GameScreen(final GridOfSums gam, int size){
        this.game = gam;
//        int size = 3; //select grid size
        grid = new Grid(game, size);
        cam = new OrthographicCamera();
        cam.setToOrtho(false, GridOfSums.WIDTH, GridOfSums.HEIGHT);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.65f, .65f, .65f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(cam.combined);
        grid.render(delta);
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
