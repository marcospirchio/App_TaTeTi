package com.example.ejemploclase3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class JuegoActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private Button[][] botones = new Button[3][3];
    private String fichaJugador, fichaMaquina;
    private String nombreJugador;
    private boolean turnoJugador = true;
    private int jugadas = 0;

    private TextView txtTurno;
    private Button btnReiniciar, btnMenu;

    private SharedPreferences prefs;
    private final String PREFS_NAME = "tateti_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        fichaJugador = getIntent().getStringExtra("fichaJugador");
        nombreJugador = getIntent().getStringExtra("nombreJugador");
        fichaMaquina = fichaJugador.equals("X") ? "O" : "X";

        txtTurno = findViewById(R.id.txtTurno);
        gridLayout = findViewById(R.id.gridLayout);
        btnReiniciar = findViewById(R.id.btnReiniciar);
        btnMenu = findViewById(R.id.btnMenu);

        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int index = i * 3 + j;
                Button btn = (Button) gridLayout.getChildAt(index);
                botones[i][j] = btn;

                int finalI = i;
                int finalJ = j;

                btn.setOnClickListener(v -> {
                    if (btn.getText().toString().isEmpty() && turnoJugador) {
                        jugarTurno(finalI, finalJ);
                    }
                });
            }
        }

        actualizarTurno();

        btnReiniciar.setOnClickListener(v -> reiniciarPartida());

        btnMenu.setOnClickListener(v -> {
            Intent intent = new Intent(JuegoActivity.this, BienvenidaActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void jugarTurno(int i, int j) {
        botones[i][j].setText(fichaJugador);
        jugadas++;

        if (verificarGanador(fichaJugador)) {
            mostrarResultado(nombreJugador + " gana!");
            guardarResultado(true);
            return;
        }

        if (jugadas == 9) {
            mostrarResultado("Empate");
            return;
        }

        turnoJugador = false;
        actualizarTurno();

        new Handler().postDelayed(this::turnoMaquina, 500);
    }

    private void turnoMaquina() {
        Random random = new Random();
        while (true) {
            int i = random.nextInt(3);
            int j = random.nextInt(3);

            if (botones[i][j].getText().toString().isEmpty()) {
                botones[i][j].setText(fichaMaquina);
                jugadas++;
                break;
            }
        }

        if (verificarGanador(fichaMaquina)) {
            mostrarResultado("La máquina gana!");
            guardarResultado(false);
            return;
        }

        if (jugadas == 9) {
            mostrarResultado("Empate");
            return;
        }

        turnoJugador = true;
        actualizarTurno();
    }

    private void actualizarTurno() {
        txtTurno.setText("Turno de " + (turnoJugador ? nombreJugador : "Máquina"));
    }

    private boolean verificarGanador(String ficha) {
        for (int i = 0; i < 3; i++) {
            if (botones[i][0].getText().toString().equals(ficha) &&
                    botones[i][1].getText().toString().equals(ficha) &&
                    botones[i][2].getText().toString().equals(ficha)) return true;

            if (botones[0][i].getText().toString().equals(ficha) &&
                    botones[1][i].getText().toString().equals(ficha) &&
                    botones[2][i].getText().toString().equals(ficha)) return true;
        }

        if (botones[0][0].getText().toString().equals(ficha) &&
                botones[1][1].getText().toString().equals(ficha) &&
                botones[2][2].getText().toString().equals(ficha)) return true;

        if (botones[0][2].getText().toString().equals(ficha) &&
                botones[1][1].getText().toString().equals(ficha) &&
                botones[2][0].getText().toString().equals(ficha)) return true;

        return false;
    }

    private void mostrarResultado(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
        txtTurno.setText(mensaje);
        for (Button[] fila : botones) {
            for (Button btn : fila) {
                btn.setEnabled(false);
            }
        }
    }

    private void reiniciarPartida() {
        for (Button[] fila : botones) {
            for (Button btn : fila) {
                btn.setText("");
                btn.setEnabled(true);
            }
        }
        jugadas = 0;
        turnoJugador = true;
        actualizarTurno();
    }

    private void guardarResultado(boolean ganoJugador) {
        SharedPreferences.Editor editor = prefs.edit();
        String claveGanadas = nombreJugador + "_victorias";
        String clavePerdidas = nombreJugador + "_derrotas";

        if (ganoJugador) {
            int ganadas = prefs.getInt(claveGanadas, 0);
            editor.putInt(claveGanadas, ganadas + 1);
        } else {
            int perdidas = prefs.getInt(clavePerdidas, 0);
            editor.putInt(clavePerdidas, perdidas + 1);
        }

        editor.apply();
    }
}