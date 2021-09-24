package msuarez.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import msuarez.utils.Action;
import msuarez.utils.GUI;
import msuarez.utils.ResManager;
import msuarez.utils.gui.Button;
import msuarez.utils.gui.Text;

/**
 * Created by APCS on 12/15/2016.
 */

public class TitleScreen implements Screen, InputProcessor {
    private ResManager resman;
    private GUI gui;
    private boolean assistanceMode;
    private Texture titleImage;

    public TitleScreen(ResManager resman) {

        this.resman = resman;

        resman.loadMusic("music/bard-title.wav");
        resman.manager.finishLoading();
        final Music m = resman.manager.get("music/bard-title.wav", Music.class);
        m.setVolume(0.5f);
        m.setLooping(true);
        m.play();


        resman.manager.load("gfx/title.png", Texture.class);
        resman.manager.finishLoading();
        titleImage = resman.manager.get("gfx/title.png", Texture.class);

        gui = new GUI(resman);

        Button bPlay = new Button(Button.CENTER, 400, "Play", this.resman);
        bPlay.setOnClick(new Action() {
            @Override
            public void execute(ResManager resman) {
                m.stop();
                resman.setScreen(new FightScreen(resman));
            }
        });
        gui.addElement(bPlay);

        final Preferences p = Gdx.app.getPreferences("legendarybard");
        assistanceMode = p.getBoolean("assistancemode", true);
        final String msg = "Assist: ";
        final Button bOptions = new Button(Button.CENTER, 400 - 120,
                msg + (assistanceMode ? "On" : "Off"), this.resman);
        bOptions.setOnClick(new Action() {
            @Override
            public void execute(ResManager resman) {
                if (assistanceMode = !assistanceMode) {
                    bOptions.setText(msg + "On");
                } else {
                    bOptions.setText(msg + "Off");
                }

                p.putBoolean("assistancemode", assistanceMode);
                p.flush();
            }
        });

        gui.addElement(bOptions);

        Button bAbout = new Button(Button.CENTER, 400 - 240, "About", this.resman);
        gui.addElement(bAbout);
        bAbout.setOnClick(new Action() {
            @Override
            public void execute(ResManager resman) {
                resman.setScreen(new AboutScreen(resman));
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(gui);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        resman.viewport.apply();
        resman.batch.begin();
        resman.batch.draw(titleImage, Gdx.graphics.getWidth() / 2 - titleImage.getWidth() * 2,
                Gdx.graphics.getHeight() - titleImage.getHeight() * 4,
                titleImage.getWidth() * 4,
                titleImage.getHeight() * 4);
        gui.render();
        resman.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        resman.viewport.update(width, height, true);
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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        gui.touchDown(screenX, screenY, pointer, button);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
