package com.mygdx.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.game.GameSettings;

public class BulletObject extends GameObject {

    public boolean wasHit;

    public BulletObject(int x, int y, int width, int height,String texture, World world) {
        super(texture, x, y, width, height, GameSettings.BULLET_BIT, world);
        body.setLinearVelocity(new Vector2(0, GameSettings.BULLET_VELOCITY));
        body.setBullet(true);
    }
    public boolean hasToBeDestroyed (){
        return wasHit || (getY() - height / 2 > GameSettings.SCREEN_HEIGHT);
    }
    @Override
    public void hit() {
        wasHit = true;
    }

}
