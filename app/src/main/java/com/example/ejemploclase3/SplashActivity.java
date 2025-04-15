package com.example.ejemploclase3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int DURACION_SPLASH = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logo = findViewById(R.id.logoImage);

        // Animación: fade in + scale up
        logo.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(1200)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();

        // Ir a la pantalla de bienvenida después del splash
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, BienvenidaActivity.class);
            startActivity(intent);
            finish();
        }, DURACION_SPLASH);
    }
}
