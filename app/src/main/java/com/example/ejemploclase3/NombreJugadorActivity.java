package com.example.ejemploclase3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NombreJugadorActivity extends AppCompatActivity {

    private EditText etNombreJugador;
    private Button btnSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nombre_jugador);

        etNombreJugador = findViewById(R.id.etNombreJugador1);
        btnSiguiente = findViewById(R.id.btnSiguiente);

        btnSiguiente.setOnClickListener(v -> {
            String nombre = etNombreJugador.getText().toString().trim();
            if (nombre.isEmpty()) nombre = "Extraño";

            Intent intent = new Intent(NombreJugadorActivity.this, FichaActivity.class);
            intent.putExtra("nombreJugador", nombre);
            startActivity(intent);
        });
    }
}
