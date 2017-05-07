package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGame;

/**
 * Created by Валеев on 24.04.2017.
 */

public class StatsBlock {

    static final int STATSBLOCK_WEIGHT = 50;


    private float GRAVITY = 3 * MyGame.ratioDeviceScreenToGameWight;

    private Texture statsBlockTexture = new Texture("statsBlock.png");

    private Vector3 startPosition;
    private Vector3 position;

    private boolean active = false;
    private Vector3 velocity = new Vector3(1, 0, 0);

    StatsBlock(Vector3 position){
        this.position = position;
    }

    void update(float dt){

        if (active) {
            if (position.x <= 60 * MyGame.ratioDeviceScreenToGameWight) {
                velocity.add(GRAVITY, 0, 0);
                velocity.scl(dt);
                position.add(velocity.x, 0, 0);
                velocity.scl(1 / dt);
            }
                }
        else {
            position.set(new Vector3(0, 0, 0));
            velocity = new Vector3(0, 0, 0);
        }

    }


    public Texture getStatsBlockTexture() {return statsBlockTexture;}
    public Vector3 getPosition() {return position;}
    public void setActive(boolean active) {this.active = active;}
    public void setPosition(Vector3 position) {this.position = position;}
    public void setStartPosition(Vector3 startPosition) {this.startPosition = startPosition;}
    public Vector3 getStartPosition() {
        return startPosition;
    }
}
