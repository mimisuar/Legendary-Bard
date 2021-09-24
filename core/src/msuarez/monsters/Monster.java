package msuarez.monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

import msuarez.utils.DynamicSound;
import msuarez.utils.ResManager;

/**
 * Created by APCS on 11/29/2016.
 */

public class Monster {
    public int hp;
    public int maxHp;
    public int shield;
    public int maxShield;
    public int exp;
    public int attack;
    public ArrayList<Integer> shieldRange;
    public String name;
    public String img;

    private float shieldTimer = 0;
    private static float SHIELDTIMER = 3f;
    private static float FLICKERTIMER = 0.1f;
    private float flickerTimer = 0;
    private int flicker = 0;

    public ArrayList<Integer> noteList = new ArrayList<Integer>();

    static final float scale = 4.0f;

    public Monster(MonsterTemplate m) {
        this.hp = m.hp;
        this.maxHp = this.hp;
        this.shield = m.shield;
        this.maxShield = this.shield;
        this.exp = m.exp;
        this.attack = m.attack;
        this.shieldRange = m.shieldRange;
        this.name = m.name;
        this.img = m.img;

        generateNoteList();
    }

    public void render(float dt, ResManager resman) {
        if (flickerTimer > 0) {
            flickerTimer -= dt;

            if (flickerTimer <= 0) {
                flickerTimer = FLICKERTIMER;
                flicker--;

                if (flicker == 0) { flickerTimer = 0; }
            }
        }

        if (shield == 0 && shieldTimer > 0) {
            shieldTimer -= dt;

            if (shieldTimer <= 0) {
                shieldTimer = 0;
                shield = maxShield;
                generateNoteList();
            }
        }


        renderTexture(resman);
        renderInfo(resman);
    }

    public void renderTexture(ResManager resman) {
        Texture t = resman.manager.get(this.img, Texture.class);

        if (this.shield > 0) { resman.batch.setShader(resman.invertShader); }

        if (this.flicker % 2 == 0) {
            resman.batch.draw(
                    t,
                    calcX(t),
                    calcY(t),
                    t.getWidth() * scale,
                    t.getHeight() * scale
            );
        }
        resman.batch.setShader(null);
    }

    public void renderInfo(ResManager resman) {
        Texture t = resman.manager.get(this.img, Texture.class);
        String txt = this.name + ": " + Integer.toString(this.hp) + "/" + Integer.toString(this.maxHp);

        float healthPercent = (this.hp * 1.0f / this.maxHp * 1.0f);
        if (healthPercent >= 0.75) {
            resman.font.setColor(Color.WHITE);
        } else if (healthPercent >= 0.25) {
            resman.font.setColor(Color.YELLOW);
        } else {
            resman.font.setColor(Color.RED);
        }

        GlyphLayout g = new GlyphLayout();
        g.setText(resman.font, txt);

        resman.font.draw(resman.batch, txt, Gdx.graphics.getWidth() / 2 - (g.width) / 2,
                Gdx.graphics.getHeight() - (t.getHeight() * scale * 2));
    }

    public void onAttack(ResManager resman, int tx, int ty) {
        if (this.shield > 0 || this.flicker > 0) {
            return;
        }

        Texture t = resman.manager.get(this.img, Texture.class);
        int mx, my;
        int mw, mh;
        mx = calcX(t);
        my = calcY(t);
        mw = (int)(t.getWidth() * scale);
        mh = (int)(t.getHeight() * scale);

        // wew //
        if (tx > mx && tx < mx + mw && ty > my && ty < my + mh) {
            playHitSound(resman);
            hp--;
            flicker();
        }
    }

    public void generateNoteList() {
        noteList.clear();
        int length = (int)(Math.random() * 3) + 2;

        for (int i = 0; i < length; i++) {
            noteList.add(shieldRange.get((int)(Math.random() * shieldRange.size())));
        }

        while (noteList.get(0).equals(noteList.get(1))) {
            noteList.remove(1);
            noteList.add(1, shieldRange.get((int)(Math.random() * shieldRange.size())));
        }
    }

    public void flicker() {
        flickerTimer = FLICKERTIMER;
        flicker = 9;
    }

    public void shieldRegen() {
        shieldTimer = SHIELDTIMER;
    }

    int calcX(Texture img) {
        return (int)(Gdx.graphics.getWidth() / 2 - (img.getWidth() * scale) / 2);
    }

    int calcY(Texture img) {
        return (int)(Gdx.graphics.getHeight() - (img.getHeight() * scale * 1.5f));
    }

    public void playHitSound(ResManager resman) {
        Sound hit = resman.manager.get("sfx/hitsfx.wav", Sound.class);
        hit.stop();
        hit.play();
    }
}
