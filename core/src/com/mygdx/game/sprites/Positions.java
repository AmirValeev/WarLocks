package com.mygdx.game.sprites;

import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGame;

/**
 * Created by Валеев on 31.03.2017.
 */

public class Positions {


    private float angle;
    private boolean usage;
    private Vector3 position;
    private int sideNumber;



    public Positions(Vector3 position, int sideNumber, float angle) {
        this.position = new Vector3(position.x , position.y , 0 );
        this.sideNumber = sideNumber;
        usage = true;
        this.angle = angle;
    }

    public int getSideNumber() {return sideNumber;}

    public Vector3 getPosition() {return position;}

    public boolean isUsage() {return usage;}

    public void setUsage(boolean usage) {this.usage = usage;}

    public float getAngle() {return angle;}


}
