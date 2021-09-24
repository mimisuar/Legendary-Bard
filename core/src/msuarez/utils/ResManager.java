package msuarez.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.File;

import msuarez.bardgame.BardGame;
import msuarez.bardgame.Player;
import msuarez.monsters.MonsterMaker;

/**
 * Created by APCS on 12/14/2016.
 */

// may god have mercy on my soul //
public class ResManager {
    public BitmapFont font;
    public SpriteBatch batch = new SpriteBatch();
    public AssetManager manager = new AssetManager();
    public Camera camera = new OrthographicCamera();
    public Viewport viewport = new StretchViewport(256, 240,camera);
    public MonsterMaker monsterMaker = new MonsterMaker();
    public ShaderProgram invertShader;
    public ShaderProgram grayscaleShader;
    public final BardGame game;
    public Player player;

    public ResManager(final BardGame game) {
        this.game = game;
        initFontLoading();
        loadFont("PrStart.ttf", 16);
        loadMonsters();

        ShaderProgram.pedantic = false;

        String vertProg = Gdx.files.internal("shaders/invert_vertex.glsl").readString();
        String fragProg = Gdx.files.internal("shaders/invert_frag.glsl").readString();

        invertShader = new ShaderProgram(vertProg, fragProg);
        if (!invertShader.isCompiled()) {
            System.out.println(invertShader.getLog());
        }

        vertProg = Gdx.files.internal("shaders/grayscale_vertex.glsl").readString();
        fragProg = Gdx.files.internal("shaders/grayscale_frag.glsl").readString();

        grayscaleShader = new ShaderProgram(vertProg, fragProg);
        if (!grayscaleShader.isCompiled()) {
            System.out.println(grayscaleShader.getLog());
        }

        player = new Player();
        player.loadProgress();

    }

    public void setScreen(Screen src) {
        this.game.setScreen(src);
    }

    public void initFontLoading() {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

    }

    public void loadFont(String fname, int size) {
        if (manager.isLoaded(fname, BitmapFont.class)) {
            manager.unload(fname);
        }

        FreeTypeFontLoaderParameter p = new FreeTypeFontLoaderParameter();
        p.fontFileName = fname;
        p.fontParameters.size = size;
        manager.load(fname, BitmapFont.class, p);
        manager.finishLoading();
        font =  manager.get(fname, BitmapFont.class);
    }

    public void loadMusic(String fname) {
        manager.load(fname, Music.class);
    }

    public Vector2 project(Vector2 coords) {
        Vector3 t = camera.project(new Vector3(coords, 0), 0, 0, camera.viewportWidth, camera.viewportHeight);
        return new Vector2(t.x, t.y);
    }

    public Vector2 unproject(Vector2 coords) {
        Vector3 t = camera.unproject(new Vector3(coords, 0), 0, 0, camera.viewportWidth, camera.viewportHeight);
        return new Vector2(t.x, t.y);
    }

    public void loadMonsters() {
        FileHandle monstersDir = Gdx.files.internal("monsters");
        if (monstersDir.isDirectory()) {
            FileHandle[] monstersFiles = monstersDir.list();

            for (FileHandle file : monstersFiles) {
                monsterMaker.load(file.path());
            }
        }
        monsterMaker.sort();
    }

    public void dispose() {
        font.dispose();
        batch.dispose();
        manager.dispose();
        invertShader.dispose();
    }
}
