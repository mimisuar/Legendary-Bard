package msuarez.utils.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import msuarez.utils.Action;
import msuarez.utils.GUIElement;
import msuarez.utils.ResManager;

/**
 * Created by APCS on 1/31/2017.
 */

public class Button extends GUIElement {
    private ShapeRenderer ren = new ShapeRenderer();
    private ResManager resman;

    public static final int CENTER = -666;

    private int x;
    private int y;
    private int width;
    private int height;
    private String text;
    private Action onClick;

    private final int HEIGHT_BUFFER = 16;
    private final int WIDTH_BUFFER = 16;

    public Button(int x, int y, String text, ResManager resman) {
        ren.setAutoShapeType(true);

        this.x = x;
        this.y = y;

        this.text = text;

        this.resman = resman;

        this.onClick = null;

        calculateDimensions();
    }

    public void calculateDimensions() {
        GlyphLayout g = new GlyphLayout(resman.font, text);
        this.width = (int)(g.width + WIDTH_BUFFER * 2);
        this.height = (int)(g.height + HEIGHT_BUFFER * 2);
    }

    public void setText(String text) {
        this.text = text;
        calculateDimensions();
    }

    @Override
    public void render() {
        // the d stands for draw not delta //
        float dx = x;
        if (dx == CENTER) {
            dx = Gdx.graphics.getWidth() / 2 - this.width / 2;
        }

        float dy = y;
        if (dy == CENTER) {
            dy = Gdx.graphics.getHeight() / 2 - this.height / 2;
        }

        resman.batch.end();
        ren.begin();

        ren.set(ShapeRenderer.ShapeType.Filled);
        ren.setColor(Color.LIGHT_GRAY);
        ren.rect(dx, dy - this.height / 4, width, height);
        ren.set(ShapeRenderer.ShapeType.Line);
        ren.setColor(Color.BLACK);
        ren.rect(dx, dy - this.height / 4, width, height);

        ren.end();
        resman.batch.begin();

        resman.font.setColor(Color.BLACK);
        resman.font.draw(resman.batch, text, dx + WIDTH_BUFFER, dy + HEIGHT_BUFFER);
    }

    public boolean pointIntersects(int px, int py) {
        float dx = x;
        if (dx == CENTER) {
            dx = Gdx.graphics.getWidth() / 2 - this.width / 2;
        }

        float dy = y;
        if (dy == CENTER) {
            dy = Gdx.graphics.getHeight() / 2 - this.height / 2;
        }

        return (px > dx && px < dx + width && py > dy && py < dy + height);
    }

    @Override
    public void handleClick(int inputX, int inputY) {
        if (pointIntersects(inputX, Gdx.graphics.getHeight() - inputY) && onClick != null) {
            // why did I pass resman
            onClick.execute(resman);
        }
    }

    @Override
    public void dispose() {

    }

    public void setOnClick(Action a) {
        this.onClick = a;
    }
}
