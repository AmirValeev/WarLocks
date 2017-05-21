package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


import com.mygdx.game.sprites.Button;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGame;
import com.mygdx.game.sprites.Card;
import com.mygdx.game.sprites.HeroCard;
import com.mygdx.game.sprites.Player;
import com.mygdx.game.sprites.Positions;
import com.mygdx.game.sprites.TypesOfPlayers;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;



class PlayState extends State {

    private boolean gameEnded;
    private int winner;

    private int whoHaveStroke = 1;
    private int numberOfStoke = 1;
    private int indexOfPickedCard = -1;

    private static final int CARD_COUNT = 6;


    private Texture background;
    private Texture skipBtn;
    private Texture baitPoint = new Texture("point.png");
    private Texture finishWindow;

    private ArrayList<HeroCard> cards;
    private Array<Positions> positions;
    private ArrayList<Positions> fightPositions;

    private Player player1;
    private Player player2;

    private Vector3 positionOfSkipBtn;
    private Rectangle fightZone;
    private boolean cardChoosed;

    private Matrix4 defaultMatrix;

    private BitmapFont bitmapForTextOfFirst;
    private BitmapFont bitmapForTextOfSecond;
    private BitmapFont verticalBitmap;

    private Texture healthPointBar;
    private Texture damagePin;
    private Texture hpPin;
    private Texture armorPin;

    private TimerForOneStroke timerForOneStroke;
    private Timer timer;

    private Texture backgroundRyab;
    private float velocityOfTransparency = 0;
    private float transparency = 0;
    private float transparencyForSounds = 0;


    private Music fonMusic;
    private Sound winSound;

    private Button backToMenu;
    private boolean buttonPressed;


    PlayState(GameStateManager gsm) {
        super(gsm);

        backgroundRyab = new Texture("backgroundRyab.png");

        background = new Texture("background.png");
        skipBtn = new Texture("skipBtn/skipBtnTiger.png");

        healthPointBar = new Texture("healthPointBar.png");

        armorPin = new Texture("statsPins/armorPin.png");
        damagePin = new Texture("statsPins/damagePin.png");
        hpPin = new Texture("statsPins/hpPin.png");

        positionOfSkipBtn = new Vector3((MyGame.WIGHT - skipBtn.getWidth()) * MyGame.ratioDeviceScreenToGameWight, (MyGame.HEIGHT / 2 - skipBtn.getHeight() / 2) * MyGame.ratioDeviceScreenToGameHeight, 0);

        fightZone = new Rectangle(0, (MyGame.HEIGHT / 2 - 20) * MyGame.ratioDeviceScreenToGameHeight, MyGame.WIGHT * MyGame.ratioDeviceScreenToGameWight, 40 * MyGame.ratioDeviceScreenToGameHeight);
        camera = new OrthographicCamera();
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        defaultMatrix = new Matrix4();


        bitmapForTextOfFirst = new BitmapFont(Gdx.files.internal("font/font.fnt"), false);
        bitmapForTextOfFirst.setColor(Color.WHITE);
        bitmapForTextOfFirst.getData().setScale(0.7f * MyGame.ratioDeviceScreenToGameWight, 0.5f * MyGame.ratioDeviceScreenToGameHeight);
        bitmapForTextOfSecond = new BitmapFont(Gdx.files.internal("font/font.fnt"), true);
        bitmapForTextOfSecond.setColor(Color.WHITE);
        verticalBitmap = new BitmapFont(Gdx.files.internal("font/font.fnt"), true);
        verticalBitmap.getData().setScale(0.7f * MyGame.ratioDeviceScreenToGameWight, 0.7f * MyGame.ratioDeviceScreenToGameHeight);
        verticalBitmap.setColor(Color.WHITE);

        fonMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/backgroundMusic.mp3"));
        fonMusic.setLooping(true);
        fonMusic.setVolume(0);
        fonMusic.play();

        winSound = Gdx.audio.newSound(Gdx.files.internal("sounds/winSound.mp3"));

        cards = new ArrayList<HeroCard>();
        positions = new Array<Positions>();
        fightPositions = new ArrayList<Positions>();
        cardChoosed = false;

        player1 = new Player(1, MyGame.WIGHT - Player.size, 0);
        player2 = new Player(2, 0, MyGame.HEIGHT - Player.size);

        float cardXDistance2 = MyGame.WIGHT - (MyGame.WIGHT - (7 * (Card.CARD_WIGHT / 2) + 10 * 5)) / 2 - Card.CARD_WIGHT;
        float cardXDistance1 = MyGame.WIGHT - cardXDistance2 - Card.CARD_WIGHT;

        float[] cardYDistance2 = {-10, 14, 24, 24, 14, -10};
        float[] cardYDistance1 = {456, 434, 422, 422, 434, 456};

        float[] angles = {-30, -18, -6, 6, 18, 30};

        for (int i = 0; i < CARD_COUNT; i++) {
            System.out.println();
            positions.add(new Positions(new Vector3(cardXDistance2, cardYDistance2[i], 0), 1, angles[i]));
            positions.add(new Positions(new Vector3(cardXDistance1, cardYDistance1[i], 0), 2, angles[i]));
            cards.add(new HeroCard(cardXDistance2, cardYDistance2[i], 1));
            cards.add(new HeroCard(cardXDistance1, cardYDistance1[i], 2));
            cardXDistance1 = cardXDistance1 + 10 + Card.CARD_WIGHT / 2;
            cardXDistance2 = cardXDistance2 - 10 - Card.CARD_WIGHT / 2;
        }

        timer = new Timer();
        timerForOneStroke = new TimerForOneStroke();
        timer.scheduleAtFixedRate(timerForOneStroke, 0, 1000);
        cardXDistance1 = 104 + 807 / 2 - (Card.CARD_WIGHT * 0.7f * 4 + 30 * 3) / 2;
        cardXDistance2 = 104 + 807 / 2 - (Card.CARD_WIGHT * 0.7f * 4 + 30 * 3) / 2;
        for (int i = 0; i < 4; i++) {
            fightPositions.add(new Positions(new Vector3(cardXDistance1 * MyGame.ratioDeviceScreenToGameWight, (MyGame.HEIGHT / 2 - Card.CARD_HEIGHT * 0.7f - 15) * MyGame.ratioDeviceScreenToGameHeight, 0), 1, 0));
            fightPositions.add(new Positions(new Vector3(cardXDistance2 * MyGame.ratioDeviceScreenToGameWight, (MyGame.HEIGHT / 2 - 25) * MyGame.ratioDeviceScreenToGameHeight, 0), 2, 0));
            cardXDistance1 = cardXDistance1 + (30 + Card.CARD_WIGHT) * 0.7f;
            cardXDistance2 = cardXDistance2 + (30 + Card.CARD_WIGHT) * 0.7f;
        }
        for (Positions pos : fightPositions) {
            pos.setUsage(false);
        }
        gameEnded = false;


    }


    @Override
    protected void handleInput(float dt) {
        if (!gameEnded) {
            cardOverlapsChecker(cards);
            skipStokeButtonChecker();
        }
    }


    @Override
    public void update(float dt) {
        fonMusic.setVolume(transparencyForSounds / 100);

        transparency = setTransparency(transparency, dt, 4);
        transparencyForSounds = setTransparency(transparencyForSounds, dt, 20);

        handleInput(dt);
        int countCardOfFirst = 0;
        int countCardOfSecond = 0;

        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).setTransparencyForEffects(dt, 4);
            if (cards.get(i).getStatusOfCard().equals("Is Dead")) {
                for (Positions pos : fightPositions) {
                    if (cards.get(i).getPosition() == pos.getPosition())
                        pos.setUsage(false);
                }
                switch (cards.get(i).numberOfPlayer) {
                    case 1:
                        player1.countOfActiveCard--;
                        break;
                    case 2:
                        player2.countOfActiveCard--;
                        break;
                }
                cards.remove(i);
            }
        }
        for (HeroCard card : cards)
            switch (card.numberOfPlayer) {
                case 1:
                    countCardOfFirst++;
                    break;
                case 2:
                    countCardOfSecond++;
                    break;
            }


        if (!gameEnded) {
            if (countCardOfFirst == 0) {
                gameEnded = true;
                timer.cancel();
                timerForOneStroke.cancel();
                finishWindow = new Texture("finishOfGame/windowPhoenix.png");
                winner = 1;
                backToMenu = new Button((MyGame.WIGHT - Button.WIGHT) / 2, MyGame.HEIGHT - 100, new Texture("menuAssets/toMenuBtn/backToMenuBefore.png"), new Texture("menuAssets/toMenuBtn/backToMenuAfter.png"));
                winSound.play();
            }
            if (countCardOfSecond == 0) {
                gameEnded = true;
                finishWindow = new Texture("finishOfGame/windowTiger.png");
                timer.cancel();
                timerForOneStroke.cancel();
                winner = 2;
                backToMenu = new Button((MyGame.WIGHT - Button.WIGHT) / 2, 100 - Button.HEIGHT, new Texture("menuAssets/toMenuBtn/backToMenuBefore.png"), new Texture("menuAssets/toMenuBtn/backToMenuAfter.png"));
                winSound.play();
            }
        }

        for (Card card : cards) {
            card.update(dt);
        }

        if (timerForOneStroke.getTime() <= 0) {
            swapOfStoke();
        }

    }

    @Override
    public void render(SpriteBatchWithRatio sb, ShapeRenderer shapeRenderer) {

        sb.begin();
        sb.setColor(1, 1, 1, 1);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setColor(Color.GREEN);
        sb.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.draw(background, 0, 0);
        sb.draw(skipBtn, positionOfSkipBtn.x, positionOfSkipBtn.y, skipBtn.getWidth(), skipBtn.getHeight(), 0, 0, skipBtn.getWidth(), skipBtn.getHeight(), false, true);

        defaultMatrix = sb.getTransformMatrix().cpy();
        Matrix4 m4VerticalText = new Matrix4();
        m4VerticalText.setToRotation(new Vector3(0, 0, 1), 90);

        if (timerForOneStroke.getTime() < 10)
            m4VerticalText.trn((MyGame.WIGHT - 65 * 0.7f) * MyGame.ratioDeviceScreenToGameWight, (MyGame.HEIGHT / 2 + -20 * 0.7f) * MyGame.ratioDeviceScreenToGameHeight, 0);
        else
            m4VerticalText.trn((MyGame.WIGHT - 65 * 0.7f) * MyGame.ratioDeviceScreenToGameWight, (MyGame.HEIGHT / 2 + -40 * 0.7f) * MyGame.ratioDeviceScreenToGameHeight, 0);


        sb.setTransformMatrix(m4VerticalText);
        verticalBitmap.draw(sb, "" + timerForOneStroke.getTime(), 0, 0);


        sb.setTransformMatrix(defaultMatrix);
        manaDrawer(sb, player1);
        manaDrawer(sb, player2);


        sb.setColor(1, 1, 1, transparencyForSounds / 100);
        for (Positions pos : positions)
            if (pos.isUsage())
                for (HeroCard card : cards)
                    if (card.getPosition().x == pos.getPosition().x * MyGame.ratioDeviceScreenToGameWight && card.getPosition().y == pos.getPosition().y * MyGame.ratioDeviceScreenToGameHeight)
                        cardDrawer(card, sb, shapeRenderer, 0.5f, pos.getAngle());
        for (HeroCard card : cards)
            if (card.isHaveActivated() && !card.onTouched && (!cardChoosed || !cards.get(indexOfPickedCard).isHaveActivated() || cards.get(indexOfPickedCard).numberOfPlayer == card.numberOfPlayer))
                cardDrawer(card, sb, shapeRenderer, 0.7f, 0);


        if (cardChoosed) {
            sb.setColor(1, 1, 1, transparency / 100);
            sb.draw(backgroundRyab, 0, 0);
            sb.setColor(1, 1, 1, 1);

            for (HeroCard card : cards)
                if (!card.onTouched && card.numberOfPlayer != cards.get(indexOfPickedCard).numberOfPlayer && card.isHaveActivated() && cards.get(indexOfPickedCard).isHaveActivated())
                    cardDrawer(card, sb, shapeRenderer, 0.7f, 0);

            cardDrawer(cards.get(indexOfPickedCard), sb, shapeRenderer, .9f, 0);
        }
        if (gameEnded){
            backToMenuChecker();
            sb.setColor(1, 1, 1, transparency / 100);
            sb.draw(backgroundRyab, 0, 0);
            sb.draw(finishWindow, 0, 0, finishWindow.getWidth() , finishWindow.getHeight(), 0, 0, finishWindow.getWidth(), finishWindow.getHeight(), false, true);
            switch (winner){
                case 2:
                    sb.draw(backToMenu.getTexture(), backToMenu.getPosition().x, backToMenu.getPosition().y, backToMenu.getTextureAfterTouched().getWidth(),backToMenu.getTextureAfterTouched().getHeight(),0,0, backToMenu.getTextureAfterTouched().getWidth(), backToMenu.getTextureAfterTouched().getHeight(), true, false);
                    break;
                case 1:
                    sb.draw(backToMenu.getTexture(), backToMenu.getPosition().x, backToMenu.getPosition().y, backToMenu.getTextureAfterTouched().getWidth(),backToMenu.getTextureAfterTouched().getHeight(),0,0, backToMenu.getTextureAfterTouched().getWidth(), backToMenu.getTextureAfterTouched().getHeight(), false, true);
                    break;
            }
            sb.setColor(1, 1, 1, 1);
        }

        shapeRenderer.end();
        sb.end();
    }

    @Override
    public void dispose() {
        player1.getPlayerTexture().dispose();
        player2.getPlayerTexture().dispose();
        background.dispose();
        backgroundRyab.dispose();
        healthPointBar.dispose();
        baitPoint.dispose();
        skipBtn.dispose();
        bitmapForTextOfFirst.dispose();
        bitmapForTextOfSecond.dispose();
        verticalBitmap.dispose();
        for (HeroCard card : cards) {
            card.getCardDefault().dispose();
            card.getManaForUseTexture().dispose();
            card.getCardTexture().dispose();
            card.getTypeOfHeroSkillTexture().dispose();
            card.getStatsBlock().getStatsBlockTexture().dispose();
            card.getCharacterTexture().dispose();
            card.getCardBackgroundTexture().dispose();
            card.getCardLightningTexture().dispose();
            card.effectTexture.dispose();
            card.getName().dispose();
        }
        fonMusic.dispose();
        backToMenu.dispose();
        winSound.dispose();
    }

    @Override
    public void resize(int w, int h) {
        camera.setToOrtho(true, w, h);
    }

    private void cardOverlapsChecker(ArrayList<HeroCard> cards) {
        int indexC = -1;
        for (HeroCard card : cards) {
            indexC++;
            if (Gdx.input.isTouched()) {
                if (Gdx.input.isTouched() && Gdx.input.getX() > card.getPosition().x && Gdx.input.getX()
                        < card.getPosition().x + HeroCard.CARD_WIGHT * MyGame.ratioDeviceScreenToGameWight && Gdx.input.getY()
                        > card.getPosition().y && Gdx.input.getY() < card.getPosition().y + HeroCard.CARD_HEIGHT * MyGame.ratioDeviceScreenToGameHeight && whoHaveStroke == card.numberOfPlayer && !card.isHaveUsed()) {
                    if (!cardChoosed) {
                        card.onTouched = true;
                        cardChoosed = true;
                    }
                }
                if (card.onTouched) {
                    touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                    card.getPosition().x = touch.x - 60 * MyGame.ratioDeviceScreenToGameWight;
                    card.getPosition().y = touch.y - 60 * MyGame.ratioDeviceScreenToGameHeight;
                    indexOfPickedCard = indexC;
                }
            } else {
                transparency = 0;
                velocityOfTransparency = 0;
                if (card.isHaveActivated()) {

                    double squareMax = 0;
                    int indexOfOver = -1;

                    for (int i = 0; i < cards.size(); i++) {
                        if (card.getCardRect().overlaps(cards.get(i).getCardRect()) && card.numberOfPlayer != cards.get(i).numberOfPlayer && card.onTouched && cards.get(i).isHaveActivated()) {
                            if (card.getPosition().x > cards.get(i).getPosition().x) {
                                if (squareMax < cards.get(i).getPosition().x + Card.CARD_WIGHT - card.getPosition().x) {
                                    squareMax = cards.get(i).getPosition().x + Card.CARD_WIGHT - card.getPosition().x;
                                    indexOfOver = i;
                                }
                            } else {
                                if (squareMax < card.getPosition().x + Card.CARD_WIGHT - cards.get(i).getPosition().x) {
                                    squareMax = card.getPosition().x + Card.CARD_WIGHT - cards.get(i).getPosition().x;
                                    indexOfOver = i;
                                }
                            }
                        }
                    }
                    if (indexOfOver > -1)
                        card.hit(cards.get(indexOfOver));


                    if ((card.getCardRect().overlaps(player1.getPlayerRect()) && card.numberOfPlayer == 2) || (card.getCardRect().overlaps(player2.getPlayerRect()) && card.numberOfPlayer == 1)) {
                        switch (card.numberOfPlayer) {
                            case 1:
                        }

                    }
                    card.getPosition().x = card.getOnFightRectPosition().x;
                    card.getPosition().y = card.getOnFightRectPosition().y;
                }                             // КОНЕЦ


                if (card.getCardRect().overlaps(fightZone) && !card.isHaveActivated() &&
                        (whoHaveStroke == 1 && player1.getMana() >= card.getManaForUse() && player1.countOfActiveCard < 4
                                || whoHaveStroke == 2 && player2.getMana() >= card.getManaForUse() && player2.countOfActiveCard < 4)) {

                    for (Positions pos : fightPositions) {
                        if (pos.getSideNumber() == card.numberOfPlayer && !pos.isUsage()) {
                            pos.setUsage(true);
                            card.setPosition(pos.getPosition());
                            switch (card.numberOfPlayer) {
                                case 1:
                                    player1.setMana(player1.getMana() - card.getManaForUse());
                                    player1.countOfActiveCard++;
                                    break;
                                case 2:
                                    player2.setMana(player2.getMana() - card.getManaForUse());
                                    player2.countOfActiveCard++;
                                    break;
                            }
                            break;
                        } else card.setPosition(card.getStartPos());
                    }
                    card.setHaveUsed(true);
                    card.setHaveActivated(true);

                    card.getOnFightRectPosition().x = card.getPosition().x;
                    card.getOnFightRectPosition().y = card.getPosition().y;

                } else if (!card.isHaveActivated()) {
                    card.getPosition().x = card.getStartPos().x;
                    card.getPosition().y = card.getStartPos().y;
                } else {
                    card.getPosition().x = card.getOnFightRectPosition().x;
                    card.getPosition().y = card.getOnFightRectPosition().y;
                }
                card.onTouched = false;
                cardChoosed = false;

            }
            card.getCardRect().setPosition(card.getPosition().x, card.getPosition().y);

        }
    }

    private void skipStokeButtonChecker() {
        if (Gdx.input.isTouched() && Gdx.input.getX() > positionOfSkipBtn.x && Gdx.input.getY() > positionOfSkipBtn.y && Gdx.input.getY() < positionOfSkipBtn.y + skipBtn.getHeight() * MyGame.ratioDeviceScreenToGameHeight && !cardChoosed) {
            if (whoHaveStroke == 1)
                skipBtn = new Texture("skipBtn/skipBtnTigerPressed.png");
            else
                skipBtn = new Texture("skipBtn/skipBtnPhoenixPressed.png");
        } else {
            if (whoHaveStroke == 1)
                skipBtn = new Texture("skipBtn/skipBtnTiger.png");
            else
                skipBtn = new Texture("skipBtn/skipBtnPhoenix.png");
        }

        if (Gdx.input.justTouched() && Gdx.input.getX() > positionOfSkipBtn.x && Gdx.input.getY() > positionOfSkipBtn.y && Gdx.input.getY() < positionOfSkipBtn.y + skipBtn.getHeight() * MyGame.ratioDeviceScreenToGameHeight) {
            swapOfStoke();
            if (whoHaveStroke == 1)
                skipBtn = new Texture("skipBtn/skipBtnTiger.png");
            else
                skipBtn = new Texture("skipBtn/skipBtnPhoenix.png");
        }
    }
    private void backToMenuChecker(){
        if (Gdx.input.isTouched()){
            if (Gdx.input.isTouched() && Gdx.input.getX() > backToMenu.getPosition().x && Gdx.input.getX() < backToMenu.getPosition().x + backToMenu.getTextureBeforeTouched().getWidth() * MyGame.ratioDeviceScreenToGameWight &&
                    Gdx.input.getY() > backToMenu.getPosition().y && Gdx.input.getY() < backToMenu.getPosition().y + backToMenu.getTextureBeforeTouched().getHeight() * MyGame.ratioDeviceScreenToGameHeight && !buttonPressed){
                buttonPressed = true;
                backToMenu.pressed = true;
                backToMenu.onTouched();
            }
        } else
        if (buttonPressed) {
            buttonPressed = false;
            backToMenu.pressed = false;
            backToMenu.onTouched();
            fonMusic.stop();
            gsm.set(new MenuState(gsm));
        }
    }


    private void swapOfStoke() {
        timer.cancel();

        if (whoHaveStroke == 2) {
            whoHaveStroke = 1;
            numberOfStoke++;
            if (player1.getManaPool() < 10) {
                player1.setManaPool(player1.getManaPool() + 1);
            }
            if (player2.getManaPool() < 10) {
                player2.setManaPool(player2.getManaPool() + 1);
            }
            player1.setMana(player1.getManaPool());
            player2.setMana(player2.getManaPool());
            if (numberOfStoke % 4 == 0)
                adderOfMissingCards();
            for (Card card : cards) card.setHaveUsed(false);
        } else whoHaveStroke = 2;

        timer = new Timer();
        timerForOneStroke = new TimerForOneStroke();
        timer.scheduleAtFixedRate(timerForOneStroke, 0, 1000);
    }

    private void adderOfMissingCards() {
        boolean isCardGiven1 = false;
        boolean isCardGiven2 = false;

        for (Positions pos : positions) {
            boolean busy = false;

            for (Card card : cards) {
                if (pos.getPosition().x * MyGame.ratioDeviceScreenToGameWight == card.getPosition().x && pos.getPosition().y * MyGame.ratioDeviceScreenToGameHeight == card.getPosition().y)
                    busy = true;
            }
            if (!busy && (pos.getSideNumber() == 1 && !isCardGiven1 || pos.getSideNumber() == 2 && !isCardGiven2)) {
                cards.add(new HeroCard(pos.getPosition().x, pos.getPosition().y, pos.getSideNumber()));
                switch (pos.getSideNumber()) {
                    case 1:
                        isCardGiven1 = true;
                        break;
                    case 2:
                        isCardGiven2 = true;
                        break;
                }
            }
        }
    }

    private void cardDrawer(HeroCard card, SpriteBatch sb, ShapeRenderer shapeRenderer, float scope, float angle) {
        if (card.onTouched) {
            sb.setColor(1, 1, 1, transparency / 100);
            sb.draw(card.getCardLightningTexture(), card.getPosition().x - (card.getCardLightningTexture().getWidth() / 2 - card.getCardDefault().getWidth() / 2) * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y - (card.getCardLightningTexture().getHeight() / 2 - card.getCardDefault().getHeight() / 2) * MyGame.ratioDeviceScreenToGameHeight, card.getCardLightningTexture().getWidth() * scope, card.getCardLightningTexture().getHeight() * scope);
               sb.setColor(1, 1, 1, 1);
        }

        if (card.numberOfPlayer == 1) {
            sb.setColor(1, 1, 1,  (100 - card.transparency) / 100);
            sb.draw(card.effectTexture, card.getPosition().x - (card.effectTexture.getWidth() - card.getCardDefault().getWidth()) / 2 * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y - (card.effectTexture.getHeight() - card.getCardDefault().getHeight()) / 2 * MyGame.ratioDeviceScreenToGameHeight,
                    card.effectTexture.getWidth() / 2, (card.effectTexture.getHeight() - card.getCardDefault().getHeight()) / 2, card.effectTexture.getWidth(), card.effectTexture.getHeight(), 1 * scope, 1 * scope, angle, 0, 0, card.effectTexture.getWidth(), card.effectTexture.getHeight(), false, false);
            sb.setColor(1, 1, 1, 1);
            if (!card.isHaveActivated())
                sb.draw(card.getCardTexture(), card.getPosition().x - (card.getCardTexture().getWidth() - card.getCardDefault().getWidth()) / 2 * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y - (card.getCardTexture().getHeight() - card.getCharacterTexture().getHeight()) * MyGame.ratioDeviceScreenToGameHeight,
                        card.getCardTexture().getWidth() / 2, card.getCardTexture().getHeight() - card.getCardDefault().getHeight(), card.getCardTexture().getWidth(), card.getCardTexture().getHeight(), 1 * scope, 1 * scope, angle, 0, 0, card.getCardTexture().getWidth(), card.getCardTexture().getHeight(), false, false);

            sb.draw(card.getCardBackgroundTexture(), card.getPosition().x + (Card.CARD_WIGHT - card.getCardBackgroundTexture().getWidth()) / 2 * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y + (Card.CARD_HEIGHT - card.getCardBackgroundTexture().getHeight()) / 2 * MyGame.ratioDeviceScreenToGameHeight,
                    card.getCardBackgroundTexture().getWidth() / 2, (card.getCardBackgroundTexture().getHeight() - card.getCardDefault().getHeight()) / 2, card.getCardBackgroundTexture().getWidth(), card.getCardBackgroundTexture().getHeight(), 1 * scope, 1 * scope, angle, 0, 0, card.getCardBackgroundTexture().getWidth(), card.getCardBackgroundTexture().getHeight(), false, false);

            if (!card.isHaveActivated())
                sb.draw(card.getName(), card.getPosition().x - (card.getCardTexture().getWidth() - card.getCardDefault().getWidth()) / 2 * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y - (card.getCardTexture().getHeight() - card.getCharacterTexture().getHeight()) * MyGame.ratioDeviceScreenToGameHeight,
                        card.getCardTexture().getWidth() / 2, card.getCardTexture().getHeight() - card.getCardDefault().getHeight(), card.getCardTexture().getWidth(), card.getCardTexture().getHeight(), 1 * scope, 1 * scope, angle, 0, 0, card.getCardTexture().getWidth(), card.getCardTexture().getHeight(), true, false);


            if (!card.isHaveActivated())
                sb.draw(card.getManaForUseTexture(), card.getPosition().x - (card.getCardTexture().getWidth() - card.getCardDefault().getWidth()) / 2 * MyGame.ratioDeviceScreenToGameWight + (card.getCardTexture().getWidth() - card.getManaForUseTexture().getWidth()) * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y - (card.getCardTexture().getHeight() - card.getCharacterTexture().getHeight()) * MyGame.ratioDeviceScreenToGameHeight + (card.getCardTexture().getHeight() - card.getManaForUseTexture().getHeight()) * MyGame.ratioDeviceScreenToGameHeight,
                        (-card.getCardBackgroundTexture().getWidth() + card.getManaForUseTexture().getWidth()) / 2, card.getManaForUseTexture().getHeight() - card.getCharacterTexture().getHeight(), card.getManaForUseTexture().getWidth(), card.getManaForUseTexture().getHeight(), 1 * scope, 1 * scope, angle, 0, 0, card.getManaForUseTexture().getWidth(), card.getManaForUseTexture().getHeight(), true, false);

            sb.draw(card.getCharacterTexture(), card.getPosition().x, card.getPosition().y, card.getCardDefault().getWidth() / 2, 0, card.getCharacterTexture().getWidth(), card.getCharacterTexture().getHeight(),
                    1 * scope, 1 * scope, angle, 0, 0, card.getCharacterTexture().getWidth(), card.getCharacterTexture().getHeight(), false, false);
            if (card.onTouched || card.isHaveActivated()) {
                sb.draw(card.getTypeOfHeroSkillTexture(), card.getPosition().x + ((Card.CARD_WIGHT - card.getCardBackgroundTexture().getWidth()) / 2 + card.getCardBackgroundTexture().getWidth() - card.getTypeOfHeroSkillTexture().getWidth() - 14) * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y + ((Card.CARD_HEIGHT - card.getCardBackgroundTexture().getHeight()) / 2 + card.getCardBackgroundTexture().getHeight() - card.getTypeOfHeroSkillTexture().getHeight() - 15) * MyGame.ratioDeviceScreenToGameHeight,
                        -card.getCardBackgroundTexture().getWidth() / 2 + card.getTypeOfHeroSkillTexture().getWidth() + 14, -card.getCardBackgroundTexture().getHeight() + 15 + card.getTypeOfHeroSkillTexture().getHeight() + (card.getCardBackgroundTexture().getHeight() - card.getCardDefault().getHeight()) / 2,
                        card.getTypeOfHeroSkillTexture().getWidth(), card.getTypeOfHeroSkillTexture().getHeight(), 1 * scope, 1 * scope, angle, 0, 0, card.getTypeOfHeroSkillTexture().getWidth(), card.getTypeOfHeroSkillTexture().getHeight(), false, false);
                bitmapForTextOfFirst.getData().setScale(0.3f * scope * MyGame.ratioDeviceScreenToGameWight, 0.3f * scope * MyGame.ratioDeviceScreenToGameHeight);

                sb.draw(damagePin, card.getPosition().x + ((Card.CARD_WIGHT - card.getCardBackgroundTexture().getWidth()) / 2 + card.getCardBackgroundTexture().getWidth() - damagePin.getWidth() - 12) * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y + ((Card.CARD_HEIGHT - card.getCardBackgroundTexture().getHeight()) / 2 + 15) * MyGame.ratioDeviceScreenToGameHeight, 12 + damagePin.getWidth() - card.getCardBackgroundTexture().getWidth() / 2, -15 + (card.getCardBackgroundTexture().getHeight() - card.getCardDefault().getHeight()) / 2,
                        damagePin.getWidth(), damagePin.getHeight(), 1 * scope, 1 * scope, angle, 0, 0, damagePin.getWidth(), damagePin.getHeight(), false, true);
                sb.draw(hpPin, card.getPosition().x + ((Card.CARD_WIGHT - card.getCardBackgroundTexture().getWidth()) / 2 + 12) * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y + ((Card.CARD_HEIGHT - card.getCardBackgroundTexture().getHeight()) / 2 + 15) * MyGame.ratioDeviceScreenToGameHeight, -12 + card.getCardBackgroundTexture().getWidth() / 2, -15 + (card.getCardBackgroundTexture().getHeight() - card.getCardDefault().getHeight()) / 2,
                        hpPin.getWidth(), hpPin.getHeight(), 1 * scope, 1 * scope, angle, 0, 0, hpPin.getWidth(), hpPin.getHeight(), false, false);
                if (scope == .9f) {
                    bitmapForTextOfFirst.draw(sb, Integer.toString(card.getDamage()), card.getPosition().x + ((Card.CARD_WIGHT - card.getCardBackgroundTexture().getWidth()) / 2 + card.getCardBackgroundTexture().getWidth() - damagePin.getWidth() - 12) * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y + ((Card.CARD_HEIGHT - card.getCardBackgroundTexture().getHeight()) / 2 + 40) * MyGame.ratioDeviceScreenToGameHeight);
                    bitmapForTextOfFirst.draw(sb, Integer.toString(card.getHealthPoints()), card.getPosition().x + ((Card.CARD_WIGHT - card.getCardBackgroundTexture().getWidth()) / 2 + 12) * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y + ((Card.CARD_HEIGHT - card.getCardBackgroundTexture().getHeight()) / 2 + 40) * MyGame.ratioDeviceScreenToGameHeight);
                  if (!card.isHaveActivated()) {
                      bitmapForTextOfFirst.getData().setScale(0.25f * scope * MyGame.ratioDeviceScreenToGameWight, 0.2f * scope * MyGame.ratioDeviceScreenToGameHeight);
                      bitmapForTextOfFirst.draw(sb, card.getSuperSkillDescription(), card.getPosition().x + ((Card.CARD_WIGHT - card.getCardBackgroundTexture().getWidth()) / 2 - 10) * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y + ((Card.CARD_HEIGHT - card.getCardBackgroundTexture().getHeight()) / 2 - 35) * MyGame.ratioDeviceScreenToGameHeight);
                  }
                  } else {
                    bitmapForTextOfFirst.draw(sb, Integer.toString(card.getDamage()), card.getPosition().x + ((Card.CARD_WIGHT - card.getCardBackgroundTexture().getWidth()) / 2 + card.getCardBackgroundTexture().getWidth() - damagePin.getWidth() - 12 - 5) * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y + ((Card.CARD_HEIGHT - card.getCardBackgroundTexture().getHeight()) / 2 + 35) * MyGame.ratioDeviceScreenToGameHeight);
                    bitmapForTextOfFirst.draw(sb, Integer.toString(card.getHealthPoints()), card.getPosition().x + ((Card.CARD_WIGHT - card.getCardBackgroundTexture().getWidth()) / 2 + 12 + 15) * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y + ((Card.CARD_HEIGHT - card.getCardBackgroundTexture().getHeight()) / 2 + 35) * MyGame.ratioDeviceScreenToGameHeight);
                }
            }

            sb.setTransformMatrix(defaultMatrix);
            sb.draw(baitPoint, -1000, -1000);

        } else {
            sb.setColor(1, 1, 1,  (100 - card.transparency) / 100);
            sb.draw(card.effectTexture, card.getPosition().x - (card.effectTexture.getWidth() - Card.CARD_WIGHT) / 2 * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y - (card.effectTexture.getHeight() - card.getCardDefault().getHeight()) / 2 * MyGame.ratioDeviceScreenToGameHeight,
                    card.effectTexture.getWidth() / 2, (card.effectTexture.getHeight() - card.getCardDefault().getHeight()) / 2 + Card.CARD_HEIGHT, card.effectTexture.getWidth(), card.effectTexture.getHeight(), 1 * scope, 1 * scope, angle, 0, 0, card.effectTexture.getWidth(), card.effectTexture.getHeight(), false, false);
            sb.setColor(1, 1, 1, 1);
            if (!card.isHaveActivated())
                sb.draw(card.getCardTexture(), card.getPosition().x + (Card.CARD_WIGHT - card.getCardTexture().getWidth()) / 2 * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y,
                        card.getCardTexture().getWidth() / 2, card.getCardDefault().getHeight(), card.getCardTexture().getWidth(), card.getCardTexture().getHeight(), 1 * scope, 1 * scope, angle, 0, 0, card.getCardTexture().getWidth(), card.getCardTexture().getHeight(), true, false);

            sb.draw(card.getCardBackgroundTexture(), card.getPosition().x + (Card.CARD_WIGHT - card.getCardBackgroundTexture().getWidth()) / 2 * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y + (Card.CARD_HEIGHT - card.getCardBackgroundTexture().getHeight()) / 2 * MyGame.ratioDeviceScreenToGameHeight,
                    card.getCardBackgroundTexture().getWidth() / 2, (card.getCardBackgroundTexture().getHeight() - card.getCardDefault().getHeight()) / 2 + card.getCardDefault().getHeight(), card.getCardBackgroundTexture().getWidth(), card.getCardBackgroundTexture().getHeight(), 1 * scope, 1 * scope, angle, 0, 0, card.getCardBackgroundTexture().getWidth(), card.getCardBackgroundTexture().getHeight(), false, false);

            if (!card.isHaveActivated())
                sb.draw(card.getName(), card.getPosition().x + (Card.CARD_WIGHT - card.getCardTexture().getWidth()) / 2 * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y,
                        card.getCardTexture().getWidth() / 2, card.getCardDefault().getHeight(), card.getCardTexture().getWidth(), card.getCardTexture().getHeight(), 1 * scope, 1 * scope, angle, 0, 0, card.getCardTexture().getWidth(), card.getCardTexture().getHeight(), false, true);


            sb.draw(card.getCharacterTexture(), card.getPosition().x, card.getPosition().y, card.getCharacterTexture().getWidth() / 2, card.getCardDefault().getHeight(),
                    card.getCharacterTexture().getWidth(), card.getCharacterTexture().getHeight(), 1 * scope, 1 * scope, angle,
                    0, 0, card.getCharacterTexture().getWidth(), card.getCharacterTexture().getHeight(), true, true);

            if (!card.isHaveActivated())
                sb.draw(card.getManaForUseTexture(), card.getPosition().x + (Card.CARD_WIGHT - card.getCardTexture().getWidth()) / 2 * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y,
                        card.getCardTexture().getWidth() / 2, card.getCardDefault().getHeight(), card.getManaForUseTexture().getWidth(), card.getManaForUseTexture().getHeight(), 1 * scope, 1 * scope, angle, 0, 0, card.getManaForUseTexture().getWidth(), card.getManaForUseTexture().getHeight(), false, true);

            if (card.onTouched || card.isHaveActivated()) {
                sb.draw(card.getTypeOfHeroSkillTexture(), card.getPosition().x + ((Card.CARD_WIGHT - card.getCardBackgroundTexture().getWidth()) / 2 + 12) * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y + ((Card.CARD_HEIGHT - card.getCardBackgroundTexture().getHeight()) / 2 + 15) * MyGame.ratioDeviceScreenToGameHeight, card.getCardBackgroundTexture().getWidth() / 2 - 12, (card.getCardBackgroundTexture().getHeight() - card.getCardDefault().getHeight()) / 2 + card.getCardDefault().getHeight() - 15,
                        card.getTypeOfHeroSkillTexture().getWidth(), card.getTypeOfHeroSkillTexture().getHeight(), 1 * scope, 1 * scope, angle, 0, 0, card.getTypeOfHeroSkillTexture().getWidth(), card.getTypeOfHeroSkillTexture().getHeight(), true, true);

                bitmapForTextOfSecond.getData().setScale(0.3f * scope * MyGame.ratioDeviceScreenToGameWight, 0.3f * scope * MyGame.ratioDeviceScreenToGameHeight);
                sb.draw(damagePin, card.getPosition().x + ((Card.CARD_WIGHT - card.getCardBackgroundTexture().getWidth()) / 2 + 12) * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y + ((Card.CARD_HEIGHT - card.getCardBackgroundTexture().getHeight()) / 2 + card.getCardBackgroundTexture().getHeight() - damagePin.getHeight() - 15) * MyGame.ratioDeviceScreenToGameHeight, card.getCardBackgroundTexture().getWidth() / 2 - 12, (card.getCardBackgroundTexture().getHeight() - card.getCardDefault().getHeight()) / 2 + card.getCardDefault().getHeight() - card.getCardBackgroundTexture().getHeight() + damagePin.getHeight() + 15,
                        damagePin.getWidth(), damagePin.getHeight(), 1 * scope, 1 * scope, angle, 0, 0, damagePin.getWidth(), damagePin.getHeight(), false, false);
                sb.draw(hpPin, card.getPosition().x + ((Card.CARD_WIGHT - card.getCardBackgroundTexture().getWidth()) / 2 + card.getCardBackgroundTexture().getWidth() - 12 - hpPin.getWidth()) * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y + ((Card.CARD_HEIGHT - card.getCardBackgroundTexture().getHeight()) / 2 + card.getCardBackgroundTexture().getHeight() - damagePin.getHeight() - 15) * MyGame.ratioDeviceScreenToGameHeight,
                        hpPin.getWidth() + 12 - card.getCardBackgroundTexture().getWidth() / 2, (card.getCardBackgroundTexture().getHeight() - card.getCardDefault().getHeight()) / 2 + card.getCardDefault().getHeight() - card.getCardBackgroundTexture().getHeight() + damagePin.getHeight() + 15, hpPin.getWidth(), hpPin.getHeight(), 1 * scope, 1 * scope, angle, 0, 0, hpPin.getWidth(), hpPin.getHeight(), false, true);
                if (scope == .9f) {
                    bitmapForTextOfSecond.draw(sb, Integer.toString(card.getDamage()), card.getPosition().x + ((Card.CARD_WIGHT - card.getCardBackgroundTexture().getWidth()) / 2 + 12 + 8) * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y + ((Card.CARD_HEIGHT - card.getCardBackgroundTexture().getHeight()) / 2 + card.getCardBackgroundTexture().getHeight() - damagePin.getHeight() - 15 + 5) * MyGame.ratioDeviceScreenToGameHeight);
                    bitmapForTextOfSecond.draw(sb, Integer.toString(card.getHealthPoints()), card.getPosition().x + ((Card.CARD_WIGHT - card.getCardBackgroundTexture().getWidth()) / 2 + card.getCardBackgroundTexture().getWidth() - 12 - 2 - hpPin.getWidth()) * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y + ((Card.CARD_HEIGHT - card.getCardBackgroundTexture().getHeight()) / 2 + card.getCardBackgroundTexture().getHeight() - damagePin.getHeight() - 15 + 4) * MyGame.ratioDeviceScreenToGameHeight);
                    if (!card.isHaveActivated()){
                        bitmapForTextOfSecond.getData().setScale(0.25f * scope * MyGame.ratioDeviceScreenToGameWight, 0.2f * scope * MyGame.ratioDeviceScreenToGameHeight);
                    bitmapForTextOfSecond.draw(sb, card.getSuperSkillDescription(), card.getPosition().x + ((Card.CARD_WIGHT - card.getCardBackgroundTexture().getWidth()) / 2 - 10) * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y + ((Card.CARD_HEIGHT - card.getCardBackgroundTexture().getHeight()) / 2 + 200) * MyGame.ratioDeviceScreenToGameHeight);
                }
                } else if (scope == .7f) {
                    bitmapForTextOfSecond.draw(sb, Integer.toString(card.getDamage()), card.getPosition().x + ((Card.CARD_WIGHT - card.getCardBackgroundTexture().getWidth()) / 2 + 12 + 19) * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y + ((Card.CARD_HEIGHT - card.getCardBackgroundTexture().getHeight()) / 2 + card.getCardBackgroundTexture().getHeight() - damagePin.getHeight() - 15 + 10) * MyGame.ratioDeviceScreenToGameHeight);
                    bitmapForTextOfSecond.draw(sb, Integer.toString(card.getHealthPoints()), card.getPosition().x + ((Card.CARD_WIGHT - card.getCardBackgroundTexture().getWidth()) / 2 + card.getCardBackgroundTexture().getWidth() - 12 - 8 - hpPin.getWidth()) * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y + ((Card.CARD_HEIGHT - card.getCardBackgroundTexture().getHeight()) / 2 + card.getCardBackgroundTexture().getHeight() - damagePin.getHeight() - 15 + 10) * MyGame.ratioDeviceScreenToGameHeight);
                }
            }
            sb.draw(baitPoint, -1000, -1000);

        }
    }

    private void manaDrawer(SpriteBatchWithRatio sb, Player player) {
        float manaDistanceX = (MyGame.WIGHT - player.manaBottle.getWidth() - 4) * MyGame.ratioDeviceScreenToGameWight;
        float manaDistanceY;
        if (player.name == TypesOfPlayers.TIGER)
            manaDistanceY = 0;
        else
            manaDistanceY = (MyGame.HEIGHT - player.manaBottle.getHeight()) * MyGame.ratioDeviceScreenToGameHeight;

        for (int i = 0; i < player.getManaPool(); i++) {
            sb.draw(player.manaBottleNull, manaDistanceX, manaDistanceY, player.manaBottleNull.getWidth(), player.manaBottleNull.getHeight());
            manaDistanceX = manaDistanceX - player.manaBottle.getWidth() * MyGame.ratioDeviceScreenToGameWight;
        }

        manaDistanceX = (MyGame.WIGHT - player.manaBottle.getWidth() - 4) * MyGame.ratioDeviceScreenToGameWight;

        for (int i = 0; i < player.getMana(); i++) {
            sb.draw(player.manaBottle, manaDistanceX, manaDistanceY, player.manaBottle.getWidth(), player.manaBottle.getHeight());
            manaDistanceX = manaDistanceX - player.manaBottle.getWidth() * MyGame.ratioDeviceScreenToGameWight;
        }

    }


    private class TimerForOneStroke extends TimerTask {

        private int time = 30;

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timer();
        }

        private void timer() {
            time--;
        }

        public int getTime() {
            return time;
        }

    }

    private float setTransparency(float transparency, float dt, float accelerationOfTransparency) {
        if (transparency < 100) {
            velocityOfTransparency = velocityOfTransparency + accelerationOfTransparency;
            velocityOfTransparency = velocityOfTransparency * dt;
            if (transparency + velocityOfTransparency < 100)
                transparency = transparency + velocityOfTransparency;
            else
                transparency = 100;
            velocityOfTransparency = velocityOfTransparency * (1 / dt);

        } else transparency = 100;
        return transparency;
    }
}

