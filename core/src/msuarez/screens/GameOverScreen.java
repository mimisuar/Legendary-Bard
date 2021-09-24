package msuarez.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import msuarez.monsters.Monster;
import msuarez.utils.Action;
import msuarez.utils.GUI;
import msuarez.utils.ResManager;
import msuarez.utils.gui.Button;

/**
 * Created by APCS on 1/12/2017.
 */

public class GameOverScreen implements Screen, InputProcessor {
    private Monster playerKilledBy = null;
    private ResManager resman;
    private GUI gui;

    public GameOverScreen(ResManager resman) {

        this.resman = resman;

        resman.loadMusic("music/bard-gameover.wav");
        resman.manager.finishLoading();
        Music go = resman.manager.get("music/bard-gameover.wav", Music.class);
        go.setVolume(0.5f);
        go.play();

        gui = new GUI(resman);

        Button bContinue = new Button(Button.CENTER, 200, "Continue", resman);
        bContinue.setOnClick(new Action() {
            @Override
            public void execute(ResManager resman) {
                resman.setScreen(new FightScreen(resman));
            }
        });
        gui.addElement(bContinue);

        Button bMainMenu = new Button(Button.CENTER, 120, "Back to Menu", resman);
        bMainMenu.setOnClick(new Action() {
            @Override
            public void execute(ResManager resman) {
                resman.setScreen(new TitleScreen(resman));
            }
        });
        gui.addElement(bMainMenu);
    }

    public void setPlayerKilledBy(Monster m) {
        playerKilledBy = m;
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
        return false;
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

    @Override
    public void show() {
        resman.manager.load("gfx/Grave.png", Texture.class);
        resman.manager.finishLoading();
        Gdx.input.setInputProcessor(gui);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);
        resman.batch.begin();
        resman.font.setColor(Color.WHITE);
        resman.font.draw(resman.batch, "Player was killed by\n" + playerKilledBy.name + "!",
                0, Gdx.graphics.getHeight());

        Texture t = resman.manager.get("gfx/Grave.png", Texture.class);
        Sprite s = new Sprite(t);
        s.setScale(4, 4);
        s.setPosition(Gdx.graphics.getWidth() / 2 - (t.getWidth() * s.getScaleX() / 4),
                Gdx.graphics.getHeight() / 2 - (t.getHeight() * s.getScaleY()) / 2);
        s.draw(resman.batch);

        gui.render();

        resman.batch.end();
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
        resman.manager.unload("gfx/Grave.png");
    }

    @Override
    public void dispose() {

    }
}
