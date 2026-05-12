package com.mygdx.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameSettings;

public class BulletObject extends GameObject {

    BulletObject(int x, int y, int width, int height, World world) {
        super("/bullet.png", x, y, width, height, world);
        body.setLinearVelocity(new Vector2(0, GameSettings.BULLET_VELOCITY));
    }
    public boolean hasToBeDestroyed (){
        return true;
    }

}
