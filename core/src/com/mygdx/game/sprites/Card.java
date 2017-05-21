package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGame;



/**
 * Created by Валеев on 24.01.2017.
 */

public abstract class Card  {


    public static final float CARD_WIGHT = 110;
    public static final float CARD_HEIGHT = 130;

    private Vector3 startPos;

    String name;

    int manaForUse;
    public int numberOfPlayer;
    public boolean onTouched;

    boolean haveUsed = false;
    private boolean slotUsage = true;
    private boolean haveActivated = false;

    private String statusOfCard = "Is Work";
    private Vector3 onFightRectPosition;


    private Vector3 position;
    Texture cardTexture;
    private Texture cardDefault = new Texture("cardFinal.png");
    Texture cardBackgroundTexture;




    private Texture cardLightningTexture = new Texture("cardLightning.png");
    private Rectangle cardRect;

    private StatsBlock statsBlock;

    Card(float x, float y) {
        position = new Vector3(x * MyGame.ratioDeviceScreenToGameWight, y * MyGame.ratioDeviceScreenToGameHeight, 0);
        cardBackgroundTexture = new Texture("cardBkgTexturs/commonWallCard.png");
        cardRect = new Rectangle(position.x, position.y, CARD_WIGHT * MyGame.ratioDeviceScreenToGameWight , CARD_HEIGHT * MyGame.ratioDeviceScreenToGameHeight);
        startPos = new Vector3(position.x, position.y, 0);
        onFightRectPosition = new Vector3();
        onTouched = false;
        statsBlock = new StatsBlock(new Vector3(position.x + (Card.CARD_WIGHT - StatsBlock.STATSBLOCK_WEIGHT) * MyGame.ratioDeviceScreenToGameWight, position.y,0));
    }

    public void update(float dt){
        statsBlock.setStartPosition(new Vector3(position.x + (Card.CARD_WIGHT - statsBlock.getStatsBlockTexture().getWidth()) * MyGame.ratioDeviceScreenToGameWight, position.y, 0));

        if (onTouched)
            statsBlock.setActive(true);
        else
            statsBlock.setActive(false);

        statsBlock.update(dt);

    }



    public Vector3 getPosition() {
        return position;
    }

    public Texture getCardTexture() {
        return cardTexture;
    }

    public Rectangle getCardRect() {
        return cardRect;
    }

    public Vector3 getStartPos() { return startPos; }

    public void setHaveActivated(boolean haveActivated) { this.haveActivated = haveActivated; }

    public boolean isHaveActivated() { return haveActivated; }

    public Vector3 getOnFightRectPosition() { return onFightRectPosition; }

    public String getStatusOfCard() { return statusOfCard; }

    public void setStatusOfCard(String statusOfCard) {this.statusOfCard = statusOfCard;}

    public boolean isHaveUsed() { return haveUsed; }

    public void setHaveUsed(boolean haveUsed) { this.haveUsed = haveUsed; }

    public int getManaForUse() {return manaForUse;}

    public void setStartPos(Vector3 startPos) {this.startPos = startPos;}

    public boolean isSlotUsage() {return slotUsage;}

    public void setSlotUsage(boolean slotUsage) {this.slotUsage = slotUsage;}

    public void setPosition(Vector3 position) {this.position = position;}

    public com.mygdx.game.sprites.StatsBlock getStatsBlock() {return statsBlock;}

    public Texture getCardLightningTexture() {return cardLightningTexture;}

    public Texture getCardDefault() {return cardDefault;}



}

