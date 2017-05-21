package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGame;
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.State;

/**
 * Created by Валеев on 16.05.2017.
 */

public class Button {

    public static final float WIGHT = 394;
    public static final float HEIGHT = 78;

    private Vector3 position;
    private Texture textureBeforeTouched;
    private Texture textureAfterTouched;

    private Sound clickSound;

    private Texture texture;
    public boolean pressed = false;

    public Button(float x, float y, Texture textureBeforeTouched, Texture textureAfterTouched){
        position = new Vector3(x * MyGame.ratioDeviceScreenToGameWight, y * MyGame.ratioDeviceScreenToGameHeight, 0);
        this.textureAfterTouched = textureAfterTouched;
        this.textureBeforeTouched = textureBeforeTouched;
        texture = textureBeforeTouched;
        clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/clickSound.mp3"));
        clickSound.setVolume(10000, 0.05f);
    }

    public void onTouched(){
        clickSound.play();
        if (pressed)
            texture = textureAfterTouched;
        else
            texture = textureBeforeTouched;
    }

    public void dispose() {
        textureAfterTouched.dispose();
        textureBeforeTouched.dispose();
        texture.dispose();
        clickSound.dispose();
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getTextureBeforeTouched() {
        return textureBeforeTouched;
    }

    public Texture getTextureAfterTouched() {
        return textureAfterTouched;
    }

    public Texture getTexture() {return texture;}

}
