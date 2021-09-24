package msuarez.bardgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;

import static msuarez.utils.DynamicSound.resman;

/**
 * Created by APCS on 1/12/2017.
 */

public class Player {
    private int level = 1;
    private int maxHp = 3;
    private int hp = 3;
    private int attack = 1;
    private int painFlicker = 0;
    private float painTimer = 0f;
    private static float PAINTIMER = 0.1f;
    public boolean killed = false;

    public void saveProgress() {
        Preferences pref = Gdx.app.getPreferences("legendarybard");
        pref.putInteger("level", level);
        pref.putInteger("hp", maxHp);
        pref.putInteger("attack", attack);
        pref.flush();
    }

    public void loadProgress() {
        Preferences pref = Gdx.app.getPreferences("legendarybard");
        level = pref.getInteger("level", 1);
        hp = maxHp = pref.getInteger("hp", 3);
        attack = pref.getInteger("attack", 1);
    }

    private int getAttack() {
        return attack;
    }

    public void hurt(int monsterAttack) {
        this.hp -= monsterAttack;
        this.painFlicker = 1;
        this.painTimer = PAINTIMER;
        if (this.hp <= 0) {
            kill();
        }
    }

    public void kill() {
        this.painFlicker = 3;
        this.killed = true;
    }

    public int getPainFlicker() {
        return this.painFlicker;
    }

    public void heal() {

        this.hp = this.maxHp;
        this.killed = false;
    }

    public void update(float dt) {
        if (this.painFlicker > 0) {
            this.painTimer -= dt;

            if (this.painTimer <= 0) {
                this.painTimer = PAINTIMER;
                this.painFlicker--;
            }
        }
    }

    public void render(float dt) {


        Texture heart = resman.manager.get("gfx/heart.png", Texture.class);
        for (int i = 0; i < this.maxHp; i++) {
            if (i < hp) {
                resman.batch.setShader(null);
            } else {
                resman.batch.setShader(resman.grayscaleShader);
            }

            resman.batch.draw(heart, heart.getWidth() * (i), Gdx.graphics.getHeight() - heart.getHeight());

            resman.batch.setShader(null);
        }
    }

    public int getProgress() {
        return level;
    }

    public void incProgress() {
        level++;
    }
}
