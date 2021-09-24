package msuarez.utils.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;

import msuarez.utils.GUIElement;
import msuarez.utils.ResManager;

/**
 * Created by APCS on 12/15/2016.
 */

public class Text extends GUIElement {
    public String text = "";
    GlyphLayout glyphLayout = new GlyphLayout();

    @Override
    public void render() {
        Vector2 screenPos = resman.unproject(position);
        if (isOver()) {
            resman.font.setColor(0, 0, 0, 0);
        } else {
            resman.font.setColor(0.5f, 0.1f, 0.1f, 1);
        }

        resman.font.draw(resman.batch, text, screenPos.x, screenPos.y);
        glyphLayout.setText(resman.font, text);
    }

    public boolean isOver() {
        Vector2 mousePos = resman.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        Vector2 screenPos = resman.unproject(position);

        return (mousePos.x >= screenPos.x
                && mousePos.y <= screenPos.y
                && mousePos.x <= screenPos.x + glyphLayout.width
                && mousePos.y >= screenPos.y + glyphLayout.height);
    }

    @Override
    public void handleClick(int inputX, int inputY) {
        Vector2 screenPos = resman.unproject(position);
        Vector2 inputPos = resman.unproject(new Vector2(inputX, inputY));
        return;
    }

    @Override
    public void dispose() {
        
    }
}
