package com.nopalsoft.invaders.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.nopalsoft.invaders.Assets;
import com.nopalsoft.invaders.MainInvaders;
import com.nopalsoft.invaders.Settings;
import com.nopalsoft.invaders.game.GameScreen;

public class MainMenuScreen extends Screens {

    TextButton btPlay, btSettings;

    Label lbHighestScore;

    ImageButton btSonido, btMusica;
    Image elipseIzq;

    public MainMenuScreen(final MainInvaders game) {
        super(game);

        Table tituloTable = new Table();
        tituloTable.setBackground(Assets.tituloMenuRecuadro);
        Label titulo = new Label(Assets.idiomas.get("titulo_app"), new LabelStyle(Assets.font60, Color.GREEN));
        titulo.setAlignment(Align.center);
        tituloTable.setSize(265, 100);
        tituloTable.setPosition((SCREEN_WIDTH - 265) / 2f, SCREEN_HEIGHT - 110);
        tituloTable.add(titulo).expand().center();

        // El texto se lo pongo en el update
        lbHighestScore = new Label("", new LabelStyle(Assets.font10, Color.GREEN));
        lbHighestScore.setWidth(SCREEN_WIDTH);
        lbHighestScore.setAlignment(Align.center);
        lbHighestScore.setPosition(0, SCREEN_HEIGHT - 120);

        btPlay = new TextButton(Assets.idiomas.get("play"), Assets.styleTextButtonMenu);
        btPlay.setSize(250, 80);
        btPlay.setPosition(0, 250);
        btPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Assets.playSound(Assets.clickSound);
                game.setScreen(new GameScreen(game));

            }
        });

        btSettings = new TextButton(Assets.idiomas.get("settings"), Assets.styleTextButtonMenu);
        btSettings.setSize(300, 80);
        btSettings.setPosition(0, 150);
        btSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Assets.playSound(Assets.clickSound);
                game.setScreen(new SettingsScreen(game));
            }
        });


        btSonido = new ImageButton(Assets.botonSonidoOn, Assets.botonSonidoOff, Assets.botonSonidoOff);
        btSonido.setSize(40, 40);
        btSonido.setPosition(2, 2);
        if (!Settings.soundEnabled)
            btSonido.setChecked(true);
        btSonido.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                Settings.soundEnabled = !Settings.soundEnabled;
                Assets.playSound(Assets.clickSound);
                btSonido.setChecked(!Settings.soundEnabled);
            }
        });

        btMusica = new ImageButton(Assets.botonMusicaOn, Assets.botonMusicaOff, Assets.botonMusicaOff);
        btMusica.setSize(40, 40);
        btMusica.setPosition(44, 2);
        if (!Settings.musicEnabled)
            btMusica.setChecked(true);
        btMusica.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                Settings.musicEnabled = !Settings.musicEnabled;
                Assets.playSound(Assets.clickSound);
                if (!Settings.musicEnabled) {
                    btMusica.setChecked(true);
                    Assets.music.pause();
                } else {
                    btMusica.setChecked(false);
                    Assets.music.play();
                }
            }
        });

        // Las medidas se sacaron con una formual de 3 si 480 / 960 x 585 donde 585 es el tamano,
        // 960 es el tamano para lo que se hicieron y 480 es el tamano de la camara
        elipseIzq = new Image(Assets.elipseMenuIzq);
        elipseIzq.setSize(18.5f, 292.5f);
        elipseIzq.setPosition(0, 60);

        stage.addActor(tituloTable);
        stage.addActor(lbHighestScore);

        stage.addActor(btPlay);
        stage.addActor(btSettings);


        stage.addActor(elipseIzq);
        stage.addActor(btSonido);
        stage.addActor(btMusica);

        if (Settings.numeroDeVecesQueSeHaJugado == 0) {
            game.dialogs.showDialogSignIn();

        }
    }

    @Override
    public void update(float delta) {
        lbHighestScore.setText(Assets.idiomas.format("local_highest_score", String.valueOf(Settings.highScores[0])));
    }

    @Override
    public void draw(float delta) {
        oCam.update();
        batcher.setProjectionMatrix(oCam.combined);

        batcher.disableBlending();
        Assets.parallaxFondo.render(delta);
    }

    @Override
    public boolean keyDown(int tecleada) {
        if (tecleada == Keys.BACK || tecleada == Keys.ESCAPE) {
            Assets.playSound(Assets.clickSound);
            if (game.dialogs.isDialogShown()) {
                game.dialogs.dismissAll();
            } else {

                Gdx.app.exit();
            }
            return true;
        }
        return false;
    }
}
