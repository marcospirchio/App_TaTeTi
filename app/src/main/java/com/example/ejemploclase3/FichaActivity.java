    package com.example.ejemploclase3;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.RadioButton;
    import android.widget.TextView;

    import androidx.appcompat.app.AppCompatActivity;

    public class FichaActivity extends AppCompatActivity {

        private RadioButton rbX, rbO;
        private Button btnComenzar;
        private TextView txtMensaje;

        private String nombreJugador;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ficha);

            rbX = findViewById(R.id.rbX);
            rbO = findViewById(R.id.rbO);
            btnComenzar = findViewById(R.id.btnComenzar);
            txtMensaje = findViewById(R.id.txtMensaje);

            nombreJugador = getIntent().getStringExtra("nombreJugador");
            txtMensaje.setText("Elegí tu ficha:");

            btnComenzar.setOnClickListener(v -> {
                String fichaElegida = rbX.isChecked() ? "X" : "O";

                Intent intent = new Intent(FichaActivity.this, JuegoActivity.class);
                intent.putExtra("nombreJugador", nombreJugador);
                intent.putExtra("fichaJugador", fichaElegida);
                intent.putExtra("modo", 1);
                startActivity(intent);
            });
        }
    }