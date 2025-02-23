package com.example.persistencia;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SharedPreferencesActivity extends AppCompatActivity {
    private EditText editText1, editText2;
    private TextView textView1, textView2;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Igual que al MainActivity, necessitem la funció OnCreate i el setContentView,
        // però assignant el Layout de la vista que us correspongui, en lloc de l'activity_main.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        Button btnGuardar = findViewById(R.id.btnGuardar);
        Button btnRecuperar = findViewById(R.id.btnRecuperar);

        //Creem la instància de la classe i donem el nom al fitxer
        //on s'estarà guardant les preferències. Ho posem en mode privat.
        // Aquest fitxer es pot veure al Device Explorer, a la ruta
        // /data/data/com.example.persistencia/shared_prefs/MisPreferencias.xml>
        sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        // Quan polsem el botó desar, utilitzarem la funció edit de la classeSharedPreferences
        // i el putString, recollint els valors inserits als editText.
        btnGuardar.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("texto1", editText1.getText().toString());
            editor.putString("texto2", editText2.getText().toString());
            editor.apply();
        });

        //I per llegir utilitzarem simplement el getString de la classeSharedPreferences
        // I el mostrarem als TextView
        btnRecuperar.setOnClickListener(v -> {
            String texto1 = sharedPreferences.getString("texto1", "No guardado");
            String texto2 = sharedPreferences.getString("texto2", "No guardado");
            textView1.setText(texto1);
            textView2.setText(texto2);
        });
    }
}
