package msuarez.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import msuarez.utils.ResManager;

/**
 * Created by APCS on 12/14/2016.
 */

public class DemoScreen implements Screen {
    ResManager resman;

    public DemoScreen(ResManager resman) {
        this.resman = resman;
        resman.loadMusic("music/titlescreen.ogg");
        resman.manager.finishLoading();

        Music m = resman.manager.get("music/titlescreen.ogg", Music.class);
        m.play();
        m.setLooping(true);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT | Gdx.gl20.GL_DEPTH_BUFFER_BIT);
        resman.viewport.apply();
        resman.batch.begin();
        //resman.font.setColor(Color.BLACK);
        Vector2 coords = resman.viewport.unproject(new Vector2(40, 50));
        resman.font.draw(resman.batch, "Please rotate your phone!", coords.x, coords.y);
        resman.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        resman.viewport.update(width, height, false);
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
