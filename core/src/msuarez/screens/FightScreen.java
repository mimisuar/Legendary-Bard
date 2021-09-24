package msuarez.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

import msuarez.monsters.Monster;
import msuarez.utils.DynamicSound;
import msuarez.utils.GUI;
import msuarez.utils.ResManager;
import msuarez.utils.gui.PianoInterface;

/**
 * Created by APCS on 12/20/2016.
 */

public class FightScreen implements Screen, InputProcessor {
    ResManager resman;
    Monster enemy = null;
    GUI gui;
    PianoInterface pi;
    ArrayList<Integer> notesToHit = null;
    ArrayList<Integer> pianoNotes = null;

    int currentNote = 0;
    float noteTimer = 0;
    final float NOTE_TIMER = 1f;

    final Music bg;

    public FightScreen(ResManager resman) {
        resman.player.saveProgress();
        this.resman = resman;

        resman.loadMusic("music/bard-battle.wav");
        resman.manager.finishLoading();
        bg = resman.manager.get("music/bard-battle.wav", Music.class);
        bg.setLooping(true);
        bg.setVolume(0.15f);
        bg.play();

        gui = new GUI(resman);
        pi = new PianoInterface();
        gui.addElement(pi);
        pianoNotes = pi.noteList;
        resman.player.heal();
    }

    public void loadMonsterAssets() {
        if (enemy == null) {
            return;
        }

        if (!resman.manager.isLoaded(enemy.img, Texture.class)) {
            resman.manager.load(enemy.img, Texture.class);
            resman.manager.finishLoading();
        }
    }

    public void unloadMonsterAssets() {
        if (enemy == null) {
            return;
        }

        if (resman.manager.isLoaded(enemy.img, Texture.class)) {
            resman.manager.unload(enemy.img);
        }

        enemy = null;
    }

    @Override
    public void show() {
        resman.manager.load("sfx/hitsfx.wav", Sound.class);
        resman.manager.load("sfx/failsfx.wav", Sound.class);
        resman.manager.load("gfx/heart.png", Texture.class);

        if (enemy == null) {
            createMonster();
        }

        InputMultiplexer m = new InputMultiplexer();
        m.addProcessor(this);
        m.addProcessor(gui);
        Gdx.input.setInputProcessor(m);
    }

    @Override
    public void render(float delta) {
        if (resman.player.getPainFlicker() % 2 == 1) {
            Gdx.gl.glClearColor(1, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        } else {
            Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            resman.batch.begin();
            if (enemy != null) {
                enemy.render(delta, resman);
            }
            playEnemySound(delta);
            checkNotes();
            resman.player.render(delta);
            enemyDead();

            gui.render();
            resman.batch.end();
        }

        resman.player.update(delta);

        if (resman.player.killed && resman.player.getPainFlicker() <= 0) {
            // goto game over state //
            bg.stop();
            GameOverScreen s = new GameOverScreen(resman);
            s.setPlayerKilledBy(enemy);
            resman.setScreen(s);
        }
    }

    public void playEnemySound(float dt) {
        if (notesToHit.size() == 0)
            return;

        if (noteTimer > 0) {
            noteTimer -= dt;
            if (noteTimer <= 0) {
                if (enemy.shield > 0)
                    DynamicSound.play(notesToHit.get(currentNote), "organ");
                currentNote++;

                if (currentNote >= notesToHit.size()) {
                    noteTimer = 2 * NOTE_TIMER;
                    currentNote = 0;
                } else {
                    noteTimer = NOTE_TIMER;
                }
            }
        }


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
        unloadMonsterAssets();
    }

    @Override
    public void dispose() {
        gui.dispose();
        unloadMonsterAssets();
    }

    public void checkNotes() {
        if (notesToHit.size() == 0 || enemy.shield == 0) {
            return;
        }

        if (pianoNotes.size() > notesToHit.size()) {
            // assume that the player hit the incorrect notes //
            playerMistake();
            return;
        }

        for (int i = 0; i < pianoNotes.size(); i++) {
            if (!pianoNotes.get(i).equals(notesToHit.get(i))) {
                // the player hit the incorrect note //
                playerMistake();
                return;
            }
        }

        if (pianoNotes.size() < notesToHit.size()) {
            return;
        }

        currentNote = 0;
        noteTimer = NOTE_TIMER;
        notesToHit.clear();
        pianoNotes.clear();
        enemy.shield--;

        if (enemy.shield > 0) {
            enemy.flicker();
            enemy.playHitSound(resman);
            enemy.generateNoteList();
        } else {
            enemy.shieldRegen();
        }


    }

    public void playerMistake() {
        Sound hurt = resman.manager.get("sfx/failsfx.wav", Sound.class);
        hurt.stop();
        hurt.play();
        pianoNotes.clear();
        resman.player.hurt(enemy.attack);
    }

    public void enemyDead() {
        if (enemy == null || enemy.hp > 0) {
            return;
        }

        resman.player.incProgress();

        createMonster();
    }

    public void createMonster() {
        resman.player.saveProgress();
        enemy = resman.monsterMaker.makeRandomMonster();
        notesToHit = enemy.noteList;
        noteTimer = NOTE_TIMER;
        pi.enemyShield = enemy.shieldRange;
        loadMonsterAssets();
        resman.player.heal();
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
        //Vector2 worldPos = resman.project(new Vector2(screenX, screenY));
        if (enemy != null) {
            enemy.onAttack(resman, screenX, Gdx.graphics.getHeight() - screenY);
        }

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
