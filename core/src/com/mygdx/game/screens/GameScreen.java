package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.ContactManager;
import com.mygdx.game.GameSession;
import com.mygdx.game.GameSettings;
import com.mygdx.game.ImageView;
import com.mygdx.game.LiveView;
import com.mygdx.game.MovingBackgroundView;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.objects.BulletObject;
import com.mygdx.game.objects.ShipObject;
import com.mygdx.game.GameResources;
import com.mygdx.game.objects.TrashObject;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    ShipObject shipObject;

    GameSession gameSession;

    ImageView topBlackoutView;

    ArrayList<TrashObject> trashArray;
    ArrayList<BulletObject> bulletArray;

    MovingBackgroundView backgroundView;
    LiveView liveView;



    private void updateTrash() {
        for (int i = 0; i < trashArray.size(); i++) {
            if (!trashArray.get(i).isInFrame() || !trashArray.get(i).isAlive()) {
                myGdxGame.world.destroyBody(trashArray.get(i).body);
                trashArray.remove(i--);
            }
        }
    }

    private void updateBullet(){
        for (int i = 0; i < bulletArray.size(); i++){
            if (bulletArray.get(i).hasToBeDestroyed()) {
                myGdxGame.world.destroyBody(bulletArray.get(i).body);
                bulletArray.remove(i--);
            }
        }
    }
    ContactManager contactManager;




    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        gameSession = new GameSession();
        liveView = new LiveView(305, 1215);

        contactManager = new ContactManager(myGdxGame.world);

        trashArray = new ArrayList<>();
        bulletArray = new ArrayList<>();

        topBlackoutView = new ImageView(0, 1180, GameResources.BACKGROUND_IMG_PATH);

        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);



         shipObject = new ShipObject(
                GameSettings.SCREEN_WIDTH / 2, 150,
                GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
                GameResources.SHIP_IMG_PATH,
                myGdxGame.world
        );
    }

    @Override
    public void show() {
        gameSession.startGame();
    }

    @Override
    public void render(float delta){
        myGdxGame.stepWorld();
        backgroundView.move();
        handleInput();
        liveView.setLeftLives(shipObject.getLiveLeft());
        if (gameSession.shouldSpawnTrash()) {
            TrashObject trashObject = new TrashObject(
                    GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT,
                    GameResources.TRASH_IMG_PATH,
                    myGdxGame.world
            );
            trashArray.add(trashObject);
        }


        if (shipObject.needToShoot()){
            BulletObject laserBullet = new BulletObject(
                    shipObject.getX(), shipObject.getY() + shipObject.height / 2,
                    GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT,
                    GameResources.BULLET_IMG_PATH,
                    myGdxGame.world
            );
            bulletArray.add(laserBullet);
        }
        updateTrash();
        updateBullet();
        draw();
        if (!shipObject.isAlive()) {
            System.out.println("Game over!");
        }
    }


    private void handleInput() {
        if (Gdx.input.isTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            shipObject.move(myGdxGame.touch);
        }
    }

    private void draw() {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();
        backgroundView.draw(myGdxGame.batch);
        shipObject.draw(myGdxGame.batch);

        for (TrashObject trash : trashArray) trash.draw(myGdxGame.batch);
        for (BulletObject bulletObject : bulletArray) bulletObject.draw(myGdxGame.batch);


        topBlackoutView.draw(myGdxGame.batch);
        liveView.draw(myGdxGame.batch);
        myGdxGame.batch.end();
    }


}

