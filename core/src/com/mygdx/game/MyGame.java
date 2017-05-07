package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.MenuState;
import com.mygdx.game.states.SpriteBatchWithRatio;

public class MyGame extends ApplicationAdapter {

	public static final float WIGHT = 960;
	public static final float HEIGHT = 576;

	public static float ratioDeviceScreenToGameHeight;
	public static float ratioDeviceScreenToGameWight;

	private GameStateManager gsm;
	private SpriteBatchWithRatio batch;
	private ShapeRenderer shapeRenderer;

	@Override
	public void create() {

		ratioDeviceScreenToGameHeight =  Gdx.graphics.getHeight() / MyGame.HEIGHT;
		ratioDeviceScreenToGameWight = (Gdx.graphics.getWidth() / MyGame.WIGHT);

		batch = new SpriteBatchWithRatio();
		shapeRenderer = new ShapeRenderer();
		gsm = new GameStateManager();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new MenuState(gsm));
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch, shapeRenderer);
	}
}