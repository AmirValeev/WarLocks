package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.MyGame;
import com.mygdx.game.sprites.Button;


/**
 * Created by Валеев on 13.02.2017.
 */

public class MenuState extends State {

    private Texture background;
    private Texture backgroundLoading;
    private Button playBtn;
    private boolean buttonPressed = false;
    private Music fonMusic;

    private float velocityOfTransparency = 0;
    private float transparency = 0;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("menuAssets/background.png");
        backgroundLoading = new Texture("menuAssets/backgroundLoading.png");
        playBtn = new Button((MyGame.WIGHT - Button.WIGHT ) / 2, (MyGame.HEIGHT - Button.HEIGHT) / 2, new Texture("menuAssets/playBtn/playBtnBeforePressed.png"),new Texture("menuAssets/playBtn/playBtnAfterPressed.png"));
        fonMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/menuBackgroundMusic.mp3"));
        fonMusic.setLooping(true);
        fonMusic.setVolume(0);
        fonMusic.play();
    }

    @Override
    protected void handleInput(float dt) {
        if (Gdx.input.isTouched()){
        if (Gdx.input.isTouched() && Gdx.input.getX() > playBtn.getPosition().x && Gdx.input.getX() < playBtn.getPosition().x + playBtn.getTextureBeforeTouched().getWidth() * MyGame.ratioDeviceScreenToGameWight &&
                Gdx.input.getY() > playBtn.getPosition().y && Gdx.input.getY() < playBtn.getPosition().y + playBtn.getTextureBeforeTouched().getHeight() * MyGame.ratioDeviceScreenToGameHeight && !buttonPressed){
           buttonPressed = true;
           playBtn.pressed = true;
           playBtn.onTouched();
          }
        } else
            if (buttonPressed ) {
                buttonPressed = false;
                playBtn.pressed = false;
                playBtn.onTouched();
                fonMusic.stop();
                gsm.set(new PlayState(gsm));
            }
    }

    @Override
    public void update(float dt) {

        if (transparency < 100) {
            float accelerationOfTransparency = 1;
            velocityOfTransparency = velocityOfTransparency + accelerationOfTransparency;
            velocityOfTransparency = velocityOfTransparency * dt;
            if (transparency + velocityOfTransparency < 100)
                transparency = transparency + velocityOfTransparency;
            else
                transparency = 100;
            velocityOfTransparency = velocityOfTransparency * (1 / dt);

        } else transparency = 100;
        fonMusic.setVolume(transparency / 100);

     handleInput(dt);

    }

    @Override
    public void render(SpriteBatchWithRatio sb, ShapeRenderer shapeRenderer) {
        sb.begin();
        sb.setColor(1, 1, 1, 1);
        if (transparency < 100)
sb.draw(backgroundLoading, 0, 0);
        sb.setColor(1, 1, 1, transparency / 100);
        sb.draw(background, 0, 0);
        sb.draw(playBtn.getTexture(), playBtn.getPosition().x, playBtn.getPosition().y);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        backgroundLoading.dispose();
        playBtn.dispose();
        fonMusic.dispose();

    }

    @Override
    public void resize(int wight, int height) {
        camera.setToOrtho(true, wight, height);
    }
}
