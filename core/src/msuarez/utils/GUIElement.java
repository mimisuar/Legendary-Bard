package msuarez.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by APCS on 12/15/2016.
 */

public abstract class GUIElement {
    public Vector2 position = new Vector2();
    public Action onClick = null;
    public ResManager resman = null;
    public abstract void render();
    public abstract void handleClick(int inputX, int inputY);
    public abstract void dispose();
}
