package msuarez.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by APCS on 12/15/2016.
 */

public class GUI implements InputProcessor {
    private ArrayList<GUIElement> elements;
    private ResManager resman;

    public GUI(ResManager resman) {
        this.resman = resman;
        elements = new ArrayList<GUIElement>();
    }

    public void addElement(GUIElement e) {
        e.resman = this.resman;
        elements.add(e);
    }

    public void render() {
        for (GUIElement e : elements) {
            e.render();
        }
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
        for (GUIElement e : elements) {
            e.handleClick(screenX, screenY);
        }

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

    public void dispose() {
        for (GUIElement e : elements) {
            e.dispose();
        }
    }
}
