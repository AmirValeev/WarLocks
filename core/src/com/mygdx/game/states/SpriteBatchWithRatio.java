package com.mygdx.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGame;

/**
 * Created by Валеев on 28.04.2017.
 */
public class SpriteBatchWithRatio extends SpriteBatch {

    @Override
    public void draw(Texture texture, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY) {
        super.draw(texture, x, y, originX * MyGame.ratioDeviceScreenToGameWight, originY * MyGame.ratioDeviceScreenToGameHeight,
                width, height, scaleX * MyGame.ratioDeviceScreenToGameWight, scaleY * MyGame.ratioDeviceScreenToGameHeight,
                rotation, srcX, srcY, (int) (srcWidth * MyGame.ratioDeviceScreenToGameWight), (int) (srcHeight * MyGame.ratioDeviceScreenToGameHeight), flipX, flipY);
    }

    @Override
    public void draw(Texture texture, float x, float y, float width, float height, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY) {
        super.draw(texture, x, y, width * MyGame.ratioDeviceScreenToGameWight, height * MyGame.ratioDeviceScreenToGameHeight, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
    }

    @Override
    public void draw(Texture texture, float x, float y, int srcX, int srcY, int srcWidth, int srcHeight) {
        super.draw(texture, x, y, srcX, srcY, srcWidth, srcHeight);
    }

    @Override
    public void draw(Texture texture, float x, float y) {
        super.draw(texture, x, y);
    }

    @Override
    public void draw(Texture texture, float x, float y, float width, float height) {
        super.draw(texture, x, y, width * MyGame.ratioDeviceScreenToGameWight, height * MyGame.ratioDeviceScreenToGameHeight);
    }
}