package msuarez.bardgame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Logger;

import msuarez.monsters.MonsterTemplateLoader;
import msuarez.screens.DemoScreen;
import msuarez.screens.FightScreen;
import msuarez.screens.TitleScreen;
import msuarez.utils.DynamicSound;
import msuarez.utils.ResManager;

public class BardGame extends Game {
	private ResManager resman;
	
	@Override
	public void create () {
		resman = new ResManager(this);
		resman.setScreen(new TitleScreen(resman));
		DynamicSound.resman = resman;
		DynamicSound.loadInstruments();
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		try {
			resman.dispose();
		}
		catch (GdxRuntimeException e) {
			Logger log = new Logger("Dispose", Application.LOG_ERROR);
			log.debug(e.getMessage());
		}
	}
}
