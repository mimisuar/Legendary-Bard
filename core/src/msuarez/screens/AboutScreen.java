package msuarez.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;

import msuarez.utils.Action;
import msuarez.utils.GUI;
import msuarez.utils.ResManager;
import msuarez.utils.gui.Button;

/**
 * Created by APCS on 2/3/2017.
 */

public class AboutScreen implements Screen {
    private GUI gui;
    private ResManager resman;
    private String msg;

    public AboutScreen(ResManager resman) {
        gui = new GUI(resman);

        Button b = new Button(Button.CENTER, 80, "Back", resman);
        b.setOnClick(new Action() {
            @Override
            public void execute(ResManager resman) {
                resman.setScreen(new TitleScreen(resman));
            }
        });
        gui.addElement(b);

        final Button online = new Button(Button.CENTER, 160, "Watch Video", resman);
        online.setOnClick(new Action() {
            @Override
            public void execute(ResManager resman) {
                Gdx.net.openURI("https://www.youtube.com/watch?v=ZgtJ3lBNsQ4");
            }
        });
        gui.addElement(online);

        this.resman = resman;

        msg = "<The Legendary Bard>\n\n" +
                "A game of monsters and music.\n" +
                "This game is designed to\n" +
                "strength absolute\n" +
                "and relative pitch skills.\n" +
                "\n" +
                "Assist mode shows all notes\n" +
                "the monster will use.\n" +
                "\n\n" +
                "Code, music and art by Miguel Suarez\n" +
                "Code written in \n\tAndroidStudio\n" +
                "LibGDX was used for graphics, \ninput and audio\n" +
                "Music created with \n\tFamitracker\n" +
                "Art made with \n\tAseprite"
        ;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(gui);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);

        resman.batch.begin();
        gui.render();
        resman.font.setColor(Color.WHITE);
        resman.font.draw(resman.batch, msg, 16, Gdx.graphics.getHeight() - 64);
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

    }

    @Override
    public void dispose() {

    }
}
