package com.example.persistencia;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creem un botó per a cadascun dels tipus de persistència
        Button btnPreferencias = findViewById(R.id.btnPreferencias);
        Button btnInterna = findViewById(R.id.btnInterna);
        Button btnNomesLectura = findViewById(R.id.btnNomesLectura);
        Button btnTargeta = findViewById(R.id.btnTargeta);
        Button btnBaseDeDades = findViewById(R.id.btnBaseDeDades);

        //En prémer el botó , obrim la vista que li correspongui
        //Listener del botó i cridada a la vista de l' activitat 7.1. Shared Preferences.
        btnPreferencias.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SharedPreferencesActivity.class);
            startActivity(intent);
        });

        //Listener del botó i cridada a la vista de l' activitat 7.2. Memòria Interna.
        btnInterna.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, InternalStorageActivity.class);
            startActivity(intent);
        });

        //Listener del botó i cridada a la vista de l' activitat 7.3. Lectura del fitxer
        btnNomesLectura.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ReadOnlyActivity.class);
            startActivity(intent);
        });

        //Listener del botó i cridada a la vista de l' activitat 7.4. Emmagatzematge extern
        btnTargeta.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TargetaActivity.class);
            startActivity(intent);
        });

        //Listener del botó i cridada a la vista de l' activitat 7.5 Base de dades SQLite
        btnBaseDeDades.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BaseDeDadesActivity.class);
            startActivity(intent);
        });
    }
}
