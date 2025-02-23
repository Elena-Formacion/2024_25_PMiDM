package com.example.persistencia;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class InternalStorageActivity extends AppCompatActivity {
    private EditText editText;
    private TextView textView;
    //Definim el nom d l'arxiu intern, que es guardarà a la ruta
    // Es guardarà a la ruta /data/data/com.example.persistencia/files/arxiu_intern.txt>
    // Es pot veure al Device Explorer.
    private static final String FILE_NAME = "arxiu_intern.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Igual que al MainActivity, necessitem la funció OnCreate i el setContentView,
        // però assignant el Layout de la vista que us correspongui, en lloc de l'activity_main.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_storage);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        Button btnGuardar = findViewById(R.id.btnGuardar);
        Button btnRecuperar = findViewById(R.id.btnRecuperar);

        //Listener del botó "Guardar"
        btnGuardar.setOnClickListener(v -> {
            String texto = editText.getText().toString();
            try (FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
                fos.write(texto.getBytes());
            } catch (IOException e) {
                Log.d("InternalStorageActivi: ", "Error guardant el fitxer" + e);
            }
        });

        //Listener del botó "Recuperar"
        btnRecuperar.setOnClickListener(v -> {
            try (FileInputStream fis = openFileInput(FILE_NAME)) {
                byte[] bytes = new byte[fis.available()];
                fis.read(bytes);
                String texto = new String(bytes);
                textView.setText(texto);
            } catch (IOException e) {
                Log.d("InternalStorageActivi: ", "Error llegint el fitxer: " + e);
            }
        });
    }
}
