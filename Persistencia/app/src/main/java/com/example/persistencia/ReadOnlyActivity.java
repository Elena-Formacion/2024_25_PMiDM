package com.example.persistencia;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadOnlyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Igual que al MainActivity, necessitem la funció OnCreate i el setContentView,
        // però assignant el Layout de la vista que us correspongui, en lloc de l'activity_main.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_only);

        Button btnLlegir = findViewById(R.id.btnLlegir);
        TextView textView = findViewById(R.id.textView);

        // Listener del botó
        btnLlegir.setOnClickListener(v -> {
                // Llegeix el fitxer de la carpeta raw, que ha d'estar creat previament.
                String text = llegirFitxerRaw(R.raw.dades);
                textView.setText(text);
        });
    }

    // Mètode per llegir un fitxer de la carpeta raw
    private String llegirFitxerRaw(int resourceId) {
        StringBuilder resultat = new StringBuilder();
        try {
            InputStream inputStream = getResources().openRawResource(resourceId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String linia;
            while ((linia = reader.readLine()) != null) {
                resultat.append(linia).append("\n");
            }
            reader.close();
        } catch (Exception e) {
            Log.d("ReadOnlyActivity: ", "Error llegint el fitxer" + e);
            return "Error llegint el fitxer.";
        }
        return resultat.toString();
    }
}
