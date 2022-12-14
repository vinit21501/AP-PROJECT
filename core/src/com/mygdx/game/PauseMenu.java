package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PauseMenu implements Screen {
    private ButtonCreator buttonCreator;
    private MyGdxGame myGame;
    private TextureRegion backGround;
    PauseMenu(MyGdxGame game) {
        myGame = game;
        backGround = new TextureRegion(new Texture("BACKGROUND/bg1.png"));
        buttonCreator = myGame.getButtonCreator();
    }

    @Override
    public void show() {
        buttonCreator.addMainMenuButton();
        buttonCreator.addResumeButton();
        buttonCreator.addLoadButton();
        buttonCreator.addExitButton();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        myGame.getBatch().setProjectionMatrix(myGame.getGamCam().combined);
        myGame.getGamCam().update();
        myGame.getBatch().begin();
        myGame.getBatch().draw(backGround, -Utils.getWidth() / 2f, -Utils.getHeight() / 2f, Utils.getWidth(), Utils.getHeight());
        myGame.getBatch().end();
        buttonCreator.render(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        myGame.getBatch().dispose();
        buttonCreator.dispose();
    }
}
