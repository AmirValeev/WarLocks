package com.mygdx.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

/**
 * Created by Валеев on 13.02.2017.
 */

public abstract class State {
    protected OrthographicCamera camera;
    protected Vector3 touch;
    protected GameStateManager gsm;


   public State(GameStateManager gsm){
       this.gsm=gsm;
       camera = new OrthographicCamera();
       touch = new Vector3();
   }
protected abstract void handleInput(float dt);

    public abstract void update(float dt);
    public abstract void render(SpriteBatchWithRatio sb, ShapeRenderer shapeRenderer);
    public abstract void dispose();
    public abstract void resize(int wight, int height);

}
