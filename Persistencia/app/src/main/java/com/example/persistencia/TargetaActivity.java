package com.example.persistencia;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TargetaActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textView;

    private static final String FILE_NAME = "dades.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Igual que al MainActivity, necessitem la funció OnCreate i el setContentView,
        // però assignant el Layout de la vista que us correspongui, en lloc de l'activity_main.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_targeta);

        editText = findViewById(R.id.editText);
        Button btnSave = findViewById(R.id.buttonDesar);
        Button btnRetrieve = findViewById(R.id.buttonRecuperar);
        textView = findViewById(R.id.textView);

        // Comprova si tenim permís per accedir a l’emmagatzematge
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            Log.d("TarjetaActivity: ", "Tenemos permisos para acceder");
        }

        //Listener del botó "Guardar"
        btnSave.setOnClickListener(v -> saveToFile(editText.getText().toString()));

        //Listener del botó "Recuperar"
        btnRetrieve.setOnClickListener(v -> retrieveFromFile());
    }

    //Método per guardar a la tarjeta SD
    private void saveToFile(String data) {
        //  Comprobam si es por escriure a la tarjeta SD isExternalStorageWritable
        if (! (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))) {
            Toast.makeText(this, "No es pot escriure a la tarjeta SD", Toast.LENGTH_SHORT).show();
            Log.d("TarjetaActivity: ", "No es pot escriure a la tarjeta SD");
            return;
        }

        File file = new File(getExternalFilesDir(null), FILE_NAME);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data.getBytes());
            Toast.makeText(this, "Dades desades a: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            Log.d("TarjetaActivity: ", "Datos guardados en: " + file.getAbsolutePath());
        } catch (IOException e) {
            Log.e("TarjetaActivity", "Error escrivint al fitxer", e);
        }
    }

    //Funció per llegir desde la tarjeta SD
    private void retrieveFromFile() {
        //Comprobam si podemo llegir de la tarjeta SD.
        if (! (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ||
                Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY)) ) {
            Toast.makeText(this, "No es pot llegir la tarjeta SD", Toast.LENGTH_SHORT).show();
            Log.d("TarjetaActivity", "No es pot llegir la tarjeta SD");
            return;
        }

        File file = new File(getExternalFilesDir(null), FILE_NAME);
        if (!file.exists()) {
            Toast.makeText(this, "El fitxer no existeix", Toast.LENGTH_SHORT).show();
            return;
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[(int) fis.available()];
            fis.read(buffer);
            textView.setText(new String(buffer));
        } catch (IOException e) {
            Log.e("TarjetaActivity", "Error llegint del fitxer", e);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this, "Permís necessari per accedir a la tarjeta SD", Toast.LENGTH_SHORT).show();
            }
        }
    }
}