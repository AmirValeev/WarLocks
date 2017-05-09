package com.mygdx.game.sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGame;


public class Player  {
    public TypesOfPlayers name;

    private Texture playerTexture;
    private Rectangle playerRect;
    private Vector3 playerPos;

    public static final int size = 128;


    int healthPoints ;

    public int countOfActiveCard = 0;
    private int manaPool = 2;

    private int mana = manaPool;
    private String statusOfPlayer = "Is alive";

    public Texture manaBottleNull;
    public Texture manaBottle;

    public Player(int playerNumber, float x, float y){
        switch (playerNumber){
            case 2 :
                name = TypesOfPlayers.PHOENIX;
                manaBottleNull = new Texture("manaFolder/bottleNoManaPhoenix.png");
                manaBottle = new Texture("manaFolder/bottleWithManaPhoenix.png");
                break;
            case 1 :
                name = TypesOfPlayers.TIGER;
                manaBottleNull = new Texture("manaFolder/bottleNoManaTiger.png");
                manaBottle = new Texture("manaFolder/bottleWithManaTiger.png");
                break;
        }
        healthPoints = 300;
        playerPos = new Vector3(x * MyGame.ratioDeviceScreenToGameWight, y * MyGame.ratioDeviceScreenToGameHeight, 0);
        playerTexture = new Texture("players/player.png");
        playerRect = new Rectangle(x * MyGame.ratioDeviceScreenToGameWight, y * MyGame.ratioDeviceScreenToGameHeight, playerTexture.getWidth() * MyGame.ratioDeviceScreenToGameWight, playerTexture.getHeight() * MyGame.ratioDeviceScreenToGameHeight);
    }


    public Texture getPlayerTexture() {return playerTexture;}

    public Rectangle getPlayerRect() {return playerRect;}

    public Vector3 getPlayerPos() {return playerPos;}

    public String getStatusOfPlayer() {return statusOfPlayer;}

    public int getManaPool() {return manaPool;}

    public int getMana() {return mana;}

    public int getHealthPoint() {return healthPoints;}

    public void setManaPool(int manaPool) {this.manaPool = manaPool;}

    public void setStatusOfPlayer(String statusOfPlayer) {this.statusOfPlayer = statusOfPlayer;}

    public void setMana(int mana) {this.mana = mana;}



}
