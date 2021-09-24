package msuarez.utils.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

import msuarez.utils.DynamicSound;
import msuarez.utils.GUIElement;

/**
 * Created by APCS on 1/4/2017.
 */

public class PianoInterface extends GUIElement {
    public static final int WHITE_KEYS = 7;
    public static final int BLACK_KEYS = 5;

    public static final int KEY_SCALE = 80;

    public ArrayList<Integer> noteList = new ArrayList<Integer>();
    public ArrayList<Integer> enemyShield;
    private boolean assistanceMode;

    private ShapeRenderer renderer;

    private class BlackKey {
        private float x;
        private float y;
        private int note;

        public static final float width = KEY_SCALE * 0.4f;
        public static final float height = KEY_SCALE * 1f;

        public BlackKey(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public void render(ShapeRenderer r) {
            Color key = Color.BLACK;
            Color outline = Color.WHITE;
            if (assistanceMode) {
                for (int i : enemyShield) {
                    if (note == i) {
                        key = new Color(204 / 255f, 204 / 255f, 255 / 255f, 255 / 255f);
                        outline = new Color(204 / 255f, 204 / 255f, 255 / 255f, 255 / 255f);
                    }
                }
            }

            r.set(ShapeRenderer.ShapeType.Filled);
            r.setColor(key);
            r.rect(x, y, width, height);
            r.set(ShapeRenderer.ShapeType.Line);
            r.setColor(outline);
            r.rect(x, y, width, height);
        }

        public boolean onClick(int mx, int my) {
            return (mx > x && my > y && mx < x + width && my < y + height);
        }
    }

    private class WhiteKey {
        private float x;
        private float y;
        private int note;

        public static final float width = KEY_SCALE * 0.75f;
        public static final float height = KEY_SCALE * 2;

        public WhiteKey(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public void render(ShapeRenderer r) {
            Color key = Color.WHITE;
            if (assistanceMode) {
                for (int i : enemyShield) {
                    if (note == i) {
                        key = new Color(204 / 255f, 204 / 255f, 255 / 255f, 255 / 255f);
                    }
                }
            }

            r.set(ShapeRenderer.ShapeType.Filled);
            r.setColor(key);
            r.rect(x, y, width, height);
        }

        public boolean onClick(int mx, int my) {
            return (mx > x && my > y && mx < x + width && my < y + height);
        }
    }

    private WhiteKey[] whiteKeys;
    private BlackKey[] blackKeys;
    private int width;

    public PianoInterface() {

        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        whiteKeys = new WhiteKey[WHITE_KEYS];

        width = ((WHITE_KEYS) * ((int)WhiteKey.width + 2));

        int note = 0;
        int x = (Gdx.graphics.getWidth() / 2 - width / 2);
        for (int i = 0; i < WHITE_KEYS; i++) {
            whiteKeys[i] = new WhiteKey(x + (i * (WhiteKey.width + 2)), 32);
        }

        blackKeys = new BlackKey[BLACK_KEYS];
        int count = 0;
        for (int i = 0; i < 7; i++) {
            if (i == 0 || i == 3) { continue; }

            int y = (int)(32 + WhiteKey.height - BlackKey.height) - 1;
            blackKeys[count++] = new BlackKey(x + (i * (WhiteKey.width + 2) - (BlackKey.width / 2)), y);
        }

        // I don't know any other way to do this :( //
        whiteKeys[0].note = 0;
        blackKeys[0].note = 1;
        whiteKeys[1].note = 2;
        blackKeys[1].note = 3;
        whiteKeys[2].note = 4;
        whiteKeys[3].note = 5;
        blackKeys[2].note = 6;
        whiteKeys[4].note = 7;
        blackKeys[3].note = 8;
        whiteKeys[5].note = 9;
        blackKeys[4].note = 10;
        whiteKeys[6].note = 11;

        Preferences p = Gdx.app.getPreferences("legendarybard");
        assistanceMode = p.getBoolean("assistancemode", true);
    }

    @Override
    public void render() {
        resman.batch.end();
        renderer.begin();

        for (int i = 0; i < WHITE_KEYS; i++) {
            whiteKeys[i].render(renderer);
        }

        for (int i = 0; i < BLACK_KEYS; i++) {
            blackKeys[i].render(renderer);
        }

        renderer.end();
        resman.batch.begin();
    }

    @Override
    public void handleClick(int inputX, int inputY) {
        inputY = Gdx.graphics.getHeight() - inputY;

        for (int i = 0; i < BLACK_KEYS; i++) {
            if (blackKeys[i].onClick(inputX, inputY)) {
                playNote(blackKeys[i].note);
                return;
            }
        }

        for (int i = 0; i < WHITE_KEYS; i++) {
            if (whiteKeys[i].onClick(inputX, inputY)) {
                playNote(whiteKeys[i].note);
                return;
            }
        }
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    public void playNote(int note) {
        DynamicSound.play(note, "piano");
        noteList.add(note);
    }

    public boolean getAssistanceMode() {
        return assistanceMode;
    }

    public void setAssistanceMode(boolean mode) {
        assistanceMode = mode;
    }
}
