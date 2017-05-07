package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGame;
import com.mygdx.game.sprites.Card;
import com.mygdx.game.sprites.HeroCard;
import com.mygdx.game.sprites.Player;
import com.mygdx.game.sprites.Positions;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import sun.rmi.runtime.Log;


class PlayState extends State {

    private int whoHaveStroke = 1;
    private int numberOfStoke = 1;
    private int indexOfPickedCard = -1;

    private static final int CARD_COUNT = 6;


    private Texture background;
    private Texture skipBtn;


    private ArrayList<HeroCard> cards;
    private Array<Positions> positions;
    private ArrayList<Positions> fightPositions;

    private Player player1;
    private Player player2;

    private Vector3 positionOfSkipBtn;
    private Rectangle fightZone;
    private boolean cardChoosed;
    private BitmapFont bitmapForTextOfFirst;
    private BitmapFont bitmapForTextOfSecond;

    private Texture healthPointBar;

    private TimerForOneStroke timerForOneStroke;
    private Timer timer;

    private Texture backgroundRyab;
    private float velocityOfTransparency = 0;
    private float transparency = 0;

    Texture point = new Texture("point.png");

   // HeroCard TESTCARD;


     PlayState(GameStateManager gsm) {
          super(gsm);



         backgroundRyab = new Texture("backgroundRyab.png");

          background = new Texture("background.png");
          skipBtn = new Texture("skipBtn/skipBtn.png");
         healthPointBar = new Texture("healthPointBar.png");
          positionOfSkipBtn = new Vector3((MyGame.WIGHT - skipBtn.getWidth()) * MyGame.ratioDeviceScreenToGameWight, (MyGame.HEIGHT/2 - skipBtn.getHeight()/2) * MyGame.ratioDeviceScreenToGameHeight, 0);

          fightZone = new Rectangle(0, (MyGame.HEIGHT / 2 - 20) * MyGame.ratioDeviceScreenToGameHeight, MyGame.WIGHT * MyGame.ratioDeviceScreenToGameWight, 40 * MyGame.ratioDeviceScreenToGameHeight);
          camera = new OrthographicCamera();
          camera.setToOrtho(true,Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );


          bitmapForTextOfFirst = new BitmapFont(false);
          bitmapForTextOfFirst.setColor(Color.WHITE);
          bitmapForTextOfSecond = new BitmapFont(true);
          bitmapForTextOfSecond.setColor(Color.WHITE);

          cards = new ArrayList<HeroCard>();
          positions = new Array<Positions>();
          fightPositions = new ArrayList<Positions>();
          cardChoosed = false;

          player1 = new Player(1,MyGame.WIGHT - Player.size, 0);
          player2 = new Player(2,0, MyGame.HEIGHT - Player.size);

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
         timer.scheduleAtFixedRate(timerForOneStroke,0,1000);
         cardXDistance1 = MyGame.WIGHT / 2 - (Card.CARD_WIGHT * 0.8f * 4 + 30 * 3) / 2;
         cardXDistance2 = MyGame.WIGHT / 2 - (Card.CARD_WIGHT * 0.8f * 4 + 30 * 3) / 2 ;
         for(int i = 0 ; i < 4 ; i++){
             fightPositions.add(new Positions(new Vector3(cardXDistance1 * MyGame.ratioDeviceScreenToGameWight, (MyGame.HEIGHT / 2 - Card.CARD_HEIGHT - 15) * 0.8f * MyGame.ratioDeviceScreenToGameHeight,0), 1, 0));
             fightPositions.add(new Positions(new Vector3(cardXDistance2 * MyGame.ratioDeviceScreenToGameWight, (MyGame.HEIGHT / 2 + 30) * MyGame.ratioDeviceScreenToGameHeight * 0.8f, 0), 2, 0));
             cardXDistance1 = cardXDistance1 + (15 + Card.CARD_WIGHT) * 0.8f;
             cardXDistance2 = cardXDistance2 + (15 + Card.CARD_WIGHT) * 0.8f;
         }
         for (Positions pos : fightPositions) {
             pos.setUsage(false);
         }

       //   TESTCARD = new HeroCard(MyGame.WIGHT * MyGame.ratioDeviceScreenToGameWight / 2, MyGame.HEIGHT * MyGame.ratioDeviceScreenToGameHeight / 2, 2);
     }


    @Override
    protected void handleInput(float dt) {
        cardOverlapsChecker(cards);
        skipStokeButtonChecker();

    }

    @Override
    public void update(float dt) {
        //TESTCARD.ANGLETEST++;

        if (transparency < 100 )
        {
            float accelerationOfTransparency = 6;
            velocityOfTransparency = velocityOfTransparency + accelerationOfTransparency;
            velocityOfTransparency = velocityOfTransparency * dt;
            if (transparency + velocityOfTransparency < 100)
              transparency = transparency + velocityOfTransparency;
             else
              transparency = 100;
            velocityOfTransparency = velocityOfTransparency * (1 / dt);

        }
        else transparency = 100;

        handleInput(dt);
        int countCardOfFirst = 0;
        int countCardOfSecond = 0;

        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getStatusOfCard().equals("Is Dead")) {
                for (Positions pos : fightPositions) {
                    if (cards.get(i).getPosition() == pos.getPosition())
                    pos.setUsage(false);
                }
                switch (cards.get(i).numberOfPlayer) {
                    case 1 : player1.countOfActiveCard--; countCardOfFirst++; break;
                    case 2 : player2.countOfActiveCard--; countCardOfSecond++; break;
                }
                cards.remove(i);
            }

    }
     //   if (countCardOfFirst = 0
        for (Card card : cards){
            card.update(dt);
        }

        if (timerForOneStroke.getTime() <= 0) {
            swapOfStoke();
        }

    }

    @Override
    public void render(SpriteBatchWithRatio sb, ShapeRenderer shapeRenderer) {


        sb.begin();
        sb.setColor(1,1,1,1);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setColor(Color.GREEN);
        sb.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.draw(background, 0, 0);
        sb.draw(skipBtn, positionOfSkipBtn.x, positionOfSkipBtn.y);
        sb.draw(player2.getPlayerTexture(), player2.getPlayerPos().x, player2.getPlayerPos().y,player2.getPlayerTexture().getWidth(), player2.getPlayerTexture().getHeight(), 1, 1,
                player2.getPlayerTexture().getWidth(), player2.getPlayerTexture().getHeight(), true, true );
        sb.draw(player1.getPlayerTexture(), player1.getPlayerPos().x, player1.getPlayerPos().y);

        bitmapForTextOfSecond.draw(sb,"" + timerForOneStroke.getTime(),0,200);
        bitmapForTextOfFirst.draw(sb,""+player1.getMana(),MyGame.WIGHT-20,Card.CARD_WIGHT);
        bitmapForTextOfSecond.draw(sb,""+player2.getMana(),18,MyGame.HEIGHT-Card.CARD_HEIGHT-10);

      //  cardDrawer(TESTCARD,sb, shapeRenderer, 0.7f, TESTCARD.ANGLETEST);


        for (Positions pos : positions)
            if (pos.isUsage())
                for (HeroCard card : cards)
                    if (card.getPosition().x == pos.getPosition().x * MyGame.ratioDeviceScreenToGameWight && card.getPosition().y == pos.getPosition().y * MyGame.ratioDeviceScreenToGameHeight)
                        cardDrawer(card, sb, shapeRenderer, 0.5f, pos.getAngle());
        for (HeroCard card : cards)
            if(card.isHaveActivated() && !card.onTouched && (!cardChoosed || !cards.get(indexOfPickedCard).isHaveActivated() || cards.get(indexOfPickedCard).numberOfPlayer == card.numberOfPlayer))
                cardDrawer(card, sb, shapeRenderer, 0.7f, 0);


    if (cardChoosed) {
        sb.setColor(1, 1, 1, transparency / 100);
        sb.draw(backgroundRyab, 0, 0);
        sb.setColor(1, 1, 1, 1);

        for (HeroCard card : cards)
            if (!card.onTouched && card.numberOfPlayer != cards.get(indexOfPickedCard).numberOfPlayer && card.isHaveActivated() && cards.get(indexOfPickedCard).isHaveActivated())
                cardDrawer(card, sb, shapeRenderer, 0.8f, 0);

        cardDrawer(cards.get(indexOfPickedCard), sb, shapeRenderer, 0.9f, 0);
    }

        shapeRenderer.end();
        sb.end();
    }

    @Override
    public void dispose() {
        player1.getPlayerTexture().dispose();
        player2.getPlayerTexture().dispose();
        background.dispose();
        for (HeroCard card : cards) {
            card.getCardDefault().dispose();
        }
    }

    @Override
    public void resize(int w, int h) {
        camera.setToOrtho(true,w,h);
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
           }else {
               transparency = 0;
               velocityOfTransparency = 0;
               if (card.isHaveActivated()) {

                   double squareMax = 0;
                   int indexOfOver = -1;

                   for (int i = 0 ; i < cards.size() ; i++) {
                       if (card.getCardRect().overlaps(cards.get(i).getCardRect()) && card.numberOfPlayer != cards.get(i).numberOfPlayer && card.onTouched && cards.get(i).isHaveActivated() ) {
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


                   if ((card.getCardRect().overlaps(player1.getPlayerRect()) && card.numberOfPlayer == 2) || (card.getCardRect().overlaps(player2.getPlayerRect()) && card.numberOfPlayer ==1))
                   {
                      switch (card.numberOfPlayer){
                          case 1 :
                      }

                   }
                       card.getPosition().x = card.getOnFightRectPosition().x;
                       card.getPosition().y = card.getOnFightRectPosition().y;
               }                             // КОНЕЦ


                if (card.getCardRect().overlaps(fightZone) && !card.isHaveActivated() &&
                        (whoHaveStroke == 1 && player1.getMana()>=card.getManaForUse() && player1.countOfActiveCard < 4
                                || whoHaveStroke == 2 && player2.getMana()>=card.getManaForUse() && player2.countOfActiveCard < 4)) {

                    for (Positions pos : fightPositions){
                        if (pos.getSideNumber() == card.numberOfPlayer && !pos.isUsage()){
                            pos.setUsage(true);
                            card.setPosition(pos.getPosition());
                            switch (card.numberOfPlayer){
                                case 1 : player1.setMana(player1.getMana() - card.getManaForUse());
                                    player1.countOfActiveCard++;
                                    break;
                                case 2 : player2.setMana(player2.getMana() - card.getManaForUse());
                                    player2.countOfActiveCard++;
                                    break;
                             }
                            break;
                        }
                        else card.setPosition(card.getStartPos());
                    }
                    System.out.println();
                    card.setHaveUsed(true);
                    card.setHaveActivated(true);

                    card.getOnFightRectPosition().x = card.getPosition().x;
                    card.getOnFightRectPosition().y = card.getPosition().y;

                    System.out.println(card.getOnFightRectPosition()+" " +card.getPosition());
                } else
                    if (!card.isHaveActivated()) {
                        card.getPosition().x = card.getStartPos().x;
                        card.getPosition().y = card.getStartPos().y;
                    }else {
                        card.getPosition().x = card.getOnFightRectPosition().x;
                        card.getPosition().y = card.getOnFightRectPosition().y;
                    }
               card.onTouched = false;
               cardChoosed = false;

            }
            card.getCardRect().setPosition(card.getPosition().x, card.getPosition().y);

        }
    }

  /*  private void profileSwipeChecker(Player player){
        if(Gdx.input.isTouched()
                && Gdx.input.getX() > player.getPlayerPos().x &&  Gdx.input.getX() < player.getPlayerPos().x + player.getPlayerTexture().getWidth()
                && Gdx.input.getY() > player.getPlayerPos().y &&  Gdx.input.getY() < player.getPlayerPos().y + player.getPlayerTexture().getHeight())


    }*/

    private void skipStokeButtonChecker() {
        if  (Gdx.input.isTouched() && Gdx.input.getX() > positionOfSkipBtn.x && Gdx.input.getY() > positionOfSkipBtn.y && Gdx.input.getY() < positionOfSkipBtn.y + skipBtn.getHeight() * MyGame.ratioDeviceScreenToGameHeight && !cardChoosed)
            skipBtn = new Texture("skipBtn/skipBtnPressed.png");
            else skipBtn = new Texture("skipBtn/skipBtn.png");

     if (Gdx.input.justTouched() && Gdx.input.getX() > positionOfSkipBtn.x && Gdx.input.getY() > positionOfSkipBtn.y && Gdx.input.getY() < positionOfSkipBtn.y + skipBtn.getHeight() * MyGame.ratioDeviceScreenToGameHeight){
         swapOfStoke();
         skipBtn = new Texture("skipBtn/skipBtn.png");
     }
    }
    private void swapOfStoke(){
        timer.cancel();

        if (whoHaveStroke == 2) {
            whoHaveStroke = 1;
            numberOfStoke++;
            if (player1.getManaPool() < 12) {
                player1.setManaPool(player1.getManaPool() + 1);
            }
            if (player2.getManaPool() < 12) {
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
        timer.scheduleAtFixedRate(timerForOneStroke,0,1000);
    }

     private void adderOfMissingCards(){
         boolean isCardGiven1 = false;
         boolean isCardGiven2 = false;

        for (Positions pos : positions) {
            boolean busy = false;

            for (Card card : cards){
                if (pos.getPosition().x * MyGame.ratioDeviceScreenToGameWight == card.getPosition().x && pos.getPosition().y * MyGame.ratioDeviceScreenToGameHeight == card.getPosition().y)
                    busy = true;
            }
            if (!busy && (pos.getSideNumber() == 1 && !isCardGiven1 || pos.getSideNumber() == 2 && !isCardGiven2)) {
                cards.add(new HeroCard(pos.getPosition().x, pos.getPosition().y, pos.getSideNumber()));
                switch (pos.getSideNumber()){
                    case 1 : isCardGiven1 = true;
                        break;
                    case 2 : isCardGiven2 = true;
                        break;
                }
            }
        }
    }
    private void cardDrawer (HeroCard card, SpriteBatch sb, ShapeRenderer shapeRenderer, float scope, float angle){
        if (card.onTouched) {
            sb.setColor(1, 1, 1, transparency);
            sb.draw(card.getCardLightningTexture(), card.getPosition().x - (card.getCardLightningTexture().getWidth() / 2 - card.getCardDefault().getWidth()/2) * MyGame.ratioDeviceScreenToGameWight * scope, card.getPosition().y - (card.getCardLightningTexture().getHeight() / 2 - card.getCardDefault().getHeight()/2) * MyGame.ratioDeviceScreenToGameHeight *scope, card.getCardLightningTexture().getWidth() * scope, card.getCardLightningTexture().getHeight() * scope);
            sb.setColor(1, 1, 1, 1);
            sb.draw(card.getStatsBlock().getStatsBlockTexture(), card.getStatsBlock().getStartPosition().x + card.getStatsBlock().getPosition().x, card.getStatsBlock().getStartPosition().y, card.getStatsBlock().getStatsBlockTexture().getWidth() * scope, card.getStatsBlock().getStatsBlockTexture().getHeight() * scope);
        }

        if (card.numberOfPlayer == 1) {
            if (!card.isHaveActivated())

                sb.draw(card.getCardTexture(), card.getPosition().x - (card.getCardTexture().getWidth() - card.getCardDefault().getWidth()) / 2 * MyGame.ratioDeviceScreenToGameWight , card.getPosition().y - ( card.getCardTexture().getHeight() - card.getCharacterTexture().getHeight() ) * MyGame.ratioDeviceScreenToGameHeight  ,
                    card.getCardTexture().getWidth() / 2 , (card.getCardTexture().getHeight() - card.getCardDefault().getHeight()) , card.getCardTexture().getWidth(), card.getCardTexture().getHeight(), 1 * scope, 1 * scope, angle, 0, 0, card.getCardTexture().getWidth(), card.getCardTexture().getHeight(), false, false);

            sb.draw(card.getCardBackgroundTexture(), card.getPosition().x + (Card.CARD_WIGHT  - card.getCardBackgroundTexture().getWidth())/2 * MyGame.ratioDeviceScreenToGameWight , card.getPosition().y + (Card.CARD_HEIGHT  - card.getCardBackgroundTexture().getHeight()) / 2 * MyGame.ratioDeviceScreenToGameHeight ,
                    card.getCardBackgroundTexture().getWidth() / 2 , (card.getCardBackgroundTexture().getHeight() - card.getCardDefault().getHeight()) / 2, card.getCardBackgroundTexture().getWidth(), card.getCardBackgroundTexture().getHeight(), 1 * scope, 1 * scope, angle, 0, 0, card.getCardBackgroundTexture().getWidth(), card.getCardBackgroundTexture().getHeight(), false, false);

             if (card.onTouched || card.isHaveActivated()){
              sb.draw(healthPointBar, card.getPosition().x, card.getPosition().y - 12 * MyGame.ratioDeviceScreenToGameHeight  , healthPointBar.getWidth() / 2, healthPointBar.getHeight() , healthPointBar.getWidth(), healthPointBar.getHeight(),
                    1 * scope, 1 * scope, angle, 0 ,0 , healthPointBar.getWidth(), healthPointBar.getHeight(), false, false);

              shapeRenderer.rect((card.getPosition().x + 4 * MyGame.ratioDeviceScreenToGameWight * scope), (card.getPosition().y - 10 * MyGame.ratioDeviceScreenToGameHeight * scope), ((float)106 / card.getHealthPool()) * card.getHealthPoints() * scope * MyGame.ratioDeviceScreenToGameWight, 8 * scope * MyGame.ratioDeviceScreenToGameHeight);
          }

            sb.draw(card.getCharacterTexture(), card.getPosition().x, card.getPosition().y, card.getCardDefault().getWidth() / 2 , 0, card.getCharacterTexture().getWidth(), card.getCharacterTexture().getHeight() ,
                    1 * scope, 1 * scope, angle, 0, 0, card.getCharacterTexture().getWidth(), card.getCharacterTexture().getHeight(), false, false);

          if(card.onTouched || card.isHaveActivated()) {
              sb.draw(card.getTypeOfHeroSkillTexture(), card.getPosition().x , card.getPosition().y + card.getCardDefault().getHeight() * MyGame.ratioDeviceScreenToGameHeight * scope,0, 0,
                      card.getTypeOfHeroSkillTexture().getWidth(), card.getTypeOfHeroSkillTexture().getHeight(), 1 * scope, 1 * scope, angle, 0, 0, card.getTypeOfHeroSkillTexture().getWidth(), card.getTypeOfHeroSkillTexture().getHeight(), false, false);

              bitmapForTextOfFirst.draw(sb, " hp " + card.getHealthPoints() + " dm " + card.getDamage() + " mn " + card.getManaForUse(), card.getPosition().x  , card.getPosition().y + 12 * MyGame.ratioDeviceScreenToGameHeight );
          }
        }
        else {
            if (!card.isHaveActivated())
            sb.draw(card.getCardTexture(), card.getPosition().x + (Card.CARD_WIGHT  - card.getCardTexture().getWidth()) / 2 * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y,
                    card.getCardTexture().getWidth() / 2 , card.getCardDefault().getHeight(), card.getCardTexture().getWidth(), card.getCardTexture().getHeight(), 1 * scope, 1 * scope, angle, 0, 0, card.getCardTexture().getWidth(), card.getCardTexture().getHeight(), true, true);

              sb.draw(card.getCardBackgroundTexture(),card.getPosition().x + (Card.CARD_WIGHT  - card.getCardBackgroundTexture().getWidth())/2 * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y + (Card.CARD_HEIGHT  - card.getCardBackgroundTexture().getHeight()) / 2 * MyGame.ratioDeviceScreenToGameHeight,
                      card.getCardBackgroundTexture().getWidth()/2,(card.getCardBackgroundTexture().getHeight() - card.getCardDefault().getHeight())/2 +card.getCardDefault().getHeight(), card.getCardBackgroundTexture().getWidth(), card.getCardBackgroundTexture().getHeight(), 1 * scope, 1 * scope, angle, 0, 0, card.getCardBackgroundTexture().getWidth(), card.getCardBackgroundTexture().getHeight(), false, false);

             if (card.onTouched || card.isHaveActivated()) {
                 sb.draw(healthPointBar, card.getPosition().x, card.getPosition().y + Card.CARD_HEIGHT * MyGame.ratioDeviceScreenToGameHeight, healthPointBar.getWidth() / 2, 0, healthPointBar.getWidth(), healthPointBar.getHeight(),
                         1 * scope, 1 * scope, angle,0,0, healthPointBar.getWidth(), healthPointBar.getHeight(), false, false);

                 shapeRenderer.rect(card.getPosition().x + 4 * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y + (2 + Card.CARD_HEIGHT) * MyGame.ratioDeviceScreenToGameHeight, ((float) 106 / card.getHealthPool()) * card.getHealthPoints() * scope * MyGame.ratioDeviceScreenToGameWight, 8 * scope * MyGame.ratioDeviceScreenToGameHeight);
                 sb.draw(point,card.getPosition().x + 2 * MyGame.ratioDeviceScreenToGameWight, card.getPosition().y +(2 + Card.CARD_HEIGHT) * MyGame.ratioDeviceScreenToGameHeight);
             }
            sb.draw(card.getCharacterTexture(), card.getPosition().x, card.getPosition().y, card.getCharacterTexture().getWidth() / 2 , card.getCardDefault().getHeight(),
                    card.getCharacterTexture().getWidth(), card.getCharacterTexture().getHeight(), 1 * scope, 1 * scope, angle,
                    0, 0, card.getCharacterTexture().getWidth(), card.getCharacterTexture().getHeight() ,true, true);

            if (card.onTouched || card.isHaveActivated()) {
                sb.draw(card.getTypeOfHeroSkillTexture(), card.getPosition().x , card.getPosition().y - card.getTypeOfHeroSkillTexture().getHeight() * MyGame.ratioDeviceScreenToGameHeight, 0, 0,
                        card.getTypeOfHeroSkillTexture().getWidth(), card.getTypeOfHeroSkillTexture().getHeight(), 1 * scope, 1 * scope, angle, 0, 0, card.getTypeOfHeroSkillTexture().getWidth(), card.getTypeOfHeroSkillTexture().getHeight(), true, true);

                bitmapForTextOfSecond.draw(sb, " hp " + card.getHealthPoints() + " dm " + card.getDamage() + " mn " + card.getManaForUse(), card.getPosition().x, card.getPosition().y + (Card.CARD_HEIGHT - 12) * MyGame.ratioDeviceScreenToGameHeight);
            }
         //   sb.draw(card.getCardDefault(),card.getPosition().x, card.getPosition().y,card.getCardDefault().getWidth() * scope, card.getCardDefault().getHeight() * scope);

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
    private void timer(){
        time--;
    }
    public int getTime() {
        return time;
    }

}
}

