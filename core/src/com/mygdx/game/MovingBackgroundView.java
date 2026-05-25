package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameSettings;
import com.mygdx.game.View;

public class MovingBackgroundView  extends View {

    Texture texture;

    int texture1Y;
    int texture2Y;
    int speed = 2;

    public MovingBackgroundView(String pathToTexture) {
        super(0, 0);
        texture1Y = 0;
        texture2Y = GameSettings.SCREEN_HEIGHT;
        texture = new Texture(pathToTexture);
    }
    public void move() {
        texture1Y -= speed;
        texture2Y -= speed;
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture, 0, texture1Y, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
        batch.draw(texture, 0, texture2Y, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);

    }
}
