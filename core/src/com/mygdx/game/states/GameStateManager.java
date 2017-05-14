package com.mygdx.game.states;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Stack;

/**
 * Created by Валеев on 13.02.2017.
 */

public class GameStateManager {

    private Stack<State> states;
    public GameStateManager(){
   states = new Stack<State>();
    }

    public void push(State state){
states.push(state);
    }
    public void pop(){
        states.pop().dispose();
    }
    public void set(State state){
        states.pop().dispose();
        states.push(state);
    }
    public void update(float dt){
    states.peek().update(dt);
    }
public void render(SpriteBatchWithRatio sb, ShapeRenderer shapeRenderer){
    states.peek().render(sb, shapeRenderer);
}


}
