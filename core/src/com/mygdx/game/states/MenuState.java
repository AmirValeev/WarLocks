package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Валеев on 13.02.2017.
 */

public class MenuState extends State {

    public MenuState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    protected void handleInput(float dt) {
        if (Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }

    }

    @Override
    public void update(float dt) {
     handleInput(dt);
    }

    @Override
    public void render(SpriteBatchWithRatio sb, ShapeRenderer shapeRenderer) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void resize(int wight, int height) {
        camera.setToOrtho(true, wight, height);
    }
}
