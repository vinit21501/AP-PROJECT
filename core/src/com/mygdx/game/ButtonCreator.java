package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ButtonCreator {
    private final TextButton.TextButtonStyle buttonStyle, pauseButtonStyle, loadButtonStyle;
    private TextureRegionDrawable up, down, pause;
    private MyGdxGame mygame;
    private Stage stage;
    private Table table;
    private boolean setter, resize;
    private TextButton newGame, exitButton, loadButton, resumeButton, mainMenuButton, fireButton, loadingButton, playerTank, leftButton, rightButton;
    private Button pauseButton;
    ButtonCreator(MyGdxGame mygame) {
        this.mygame = mygame;
        up = new TextureRegionDrawable(new TextureRegion(new Texture("BUTTON/up.png")));
        down = new TextureRegionDrawable(new TextureRegion(new Texture("BUTTON/down.png")));
        pause = new TextureRegionDrawable(new TextureRegion(new Texture("BUTTON/pausebutton.png")));
        table = new Table();
        setter = false;
        resize = false;
        stage = new Stage(new ScreenViewport());
        stage.addActor(table);
        buttonStyle = new TextButton.TextButtonStyle();
        pauseButtonStyle = new TextButton.TextButtonStyle();
        loadButtonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = mygame.getFont();
        buttonStyle.up = up;
        buttonStyle.down = down;
        loadButtonStyle.font = mygame.getFont();
        loadButtonStyle.up = loadButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture("BUTTON/up.png")));
        pauseButtonStyle.down = pauseButtonStyle.up = pause;
        mygame.getMultiplexer().addProcessor(stage);
    }
    public void addPauseButton() {
        pauseButton = new Button(pauseButtonStyle);
        pauseButton.setSize(Gdx.graphics.getWidth() / 100f * 4f, Gdx.graphics.getHeight() / 100f * 6f);
        pauseButton.setPosition(0, Gdx.graphics.getHeight() / 100f * 90f);
        setter = true;
        stage.clear();
        stage.addActor(pauseButton);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fun();
                mygame.getGameScreen().write();
                Utils.writes(mygame.getGameScreen(), Utils.getLoadedNum());
                mygame.getGameScreen().destroyObject();
                if (mygame.getPauseMenu() == null) mygame.setPauseMenu(new PauseMenu(mygame));
                mygame.setScreen(mygame.getPauseMenu());
            }
        });
    }
    public void fun() {
        stage.clear();
        stage.addActor(table);
        setter = false;
        resize = true;
    }
    public TextButton addFireButton() {
        fireButton = new TextButton("FIRE", buttonStyle);
        fireButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.setFired(true);
            }
        });
        return fireButton;
    }
    public void addNewGameButton() {
        newGame = new TextButton("NEW GAME", buttonStyle);
        newGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.reset();
                resize = true;
                mygame.setScreen(mygame.getTankSelectionScreen());
            }
        });
        table.add(newGame).space(Utils.getButtonPadding()).row();
    }
    public void addMainMenuButton() {
        mainMenuButton = new TextButton("MAIN MENU", buttonStyle);
        table.add(mainMenuButton).space(Utils.getButtonPadding()).row();
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.reset();
                resize = true;
                if (mygame.getMainScreen() == null) mygame.setMainScreen(new MainScreen(mygame));
                mygame.setScreen(mygame.getMainScreen());
            }
        });
    }
    public void addLoadButton() {
        if (Utils.getTotalLoaded() > 0) {
            loadButton = new TextButton("LOAD GAME", buttonStyle);
            table.add(loadButton).space(Utils.getButtonPadding()).row();
            loadButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    table.reset();
                    resize = true;
                    if (mygame.getLoadScreen() == null) mygame.setLoadScreen(new LoadScreen(mygame));
                    mygame.setScreen(mygame.getLoadScreen());
                }
            });
        }
    }
    public void addExitButton() {
        exitButton = new TextButton("QUIT", buttonStyle);
        table.add(exitButton).space(Utils.getButtonPadding()).row();
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }
    public void loadMenu() {
        for (int i = 1; i <= Utils.getTotalLoaded(); ++i) {
            loadingButton = new TextButton("Loaded " + i, loadButtonStyle);
            final int finalI = i;
            loadingButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    table.reset();
                    resize = true;
                    Utils.setLoadedNum(finalI);
                    mygame.setGameScreen(Utils.reads(finalI));
                    mygame.getGameScreen().read(mygame);
                    mygame.setScreen(mygame.getGameScreen());
                }
            });
            table.add(loadingButton).space(Utils.getButtonPadding()).row();
        }
    }
    public void addResumeButton() {
        if (Utils.getLoadedNum() > 0) {
            resumeButton = new TextButton("RESUME GAME", buttonStyle);
            table.add(resumeButton).space(Utils.getButtonPadding()).row();
            resumeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    table.reset();
                    resize = true;
                    mygame.setGameScreen(Utils.reads(Utils.getLoadedNum()));
                    mygame.getGameScreen().read(mygame);
                    mygame.setScreen(mygame.getGameScreen());
                }
            });
        }
    }
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        if (setter) {
            pauseButton.setSize(width / 100f * 4f, height / 100f * 6f);
            pauseButton.setPosition(0, height / 100f * 90);
        }
        else {
            table.setSize(width, height);
            for(Cell t: table.getCells()) {
                t.size(width / 100f * Utils.getButtonWidth(), height / 100f * Utils.getButtonHeight());
            }
        }
    }

    public void render(float delta) {
        if (resize) {
            stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
            table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            for(Cell t: table.getCells()) {
                t.size(Gdx.graphics.getWidth() / 100f * Utils.getButtonWidth(), Gdx.graphics.getHeight() / 100f * Utils.getButtonHeight());
            } resize = false;
        }
        stage.act(delta);
        stage.draw();
    }
    public void dispose() {
        stage.dispose();
    }
}
