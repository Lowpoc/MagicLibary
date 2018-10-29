package com.org.magiclibary.magiclibary;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity implements View.OnClickListener {

    private MediaPlayer Song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        tocarMusica();
    }

    @Override
    public void onClick(View v) {
        abriTelaInicial();
    }

    private void abriTelaInicial() {
        startActivity(new Intent(getBaseContext(), TelaInicial.class));
        Song.stop();
    }

    private void tocarMusica() {
        this.Song = MediaPlayer.create(this, R.raw.games_of_thrones);
        Song.start();
        ImageView img = findViewById(R.id.imageView);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abriTelaInicial();
            }
        });
        img.animate().rotationBy(360).setDuration(1900).setInterpolator(new LinearInterpolator()).start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                abriTelaInicial();
            }
        }, 2000);
    }

}
