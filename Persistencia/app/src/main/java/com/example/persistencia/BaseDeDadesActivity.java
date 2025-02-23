package com.example.persistencia;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BaseDeDadesActivity  extends AppCompatActivity {
    private SqlLiteHelper db;
    private EditText editText1, editText2, editText3;
    private Button buttonInsert, buttonList, buttonUpdate, buttonDeleteFromList, buttonDeleteById;
    private ListView listView;
    private String selectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Igual que al MainActivity, necessitem la funció OnCreate i el setContentView,
        // però assignant el Layout de la vista que us correspongui, en lloc de l'activity_main.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bade_de_dades);

        //Instanciam la base de dades, de la clase que hem creat nosoltres
        db = new SqlLiteHelper(this);

        // Assigna els components de la interfície
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        buttonInsert = findViewById(R.id.buttonInsert);
        buttonList = findViewById(R.id.buttonList);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDeleteFromList = findViewById(R.id.buttonDeleteFromList);
        buttonDeleteById = findViewById(R.id.buttonDeleteById);
        listView = findViewById(R.id.listView);

        // Configura els listeners de cada botó amb la seva funció corresponent.
        buttonInsert.setOnClickListener(v -> insertData());
        buttonList.setOnClickListener(v -> listData());
        buttonUpdate.setOnClickListener(v -> updateDate());
        buttonDeleteFromList.setOnClickListener(v -> deleteFromList());
        buttonDeleteById.setOnClickListener(v -> deleteById());

        //Configura el listener de la llista quan es selecciona una de les opcions
        listView.setOnItemClickListener((parent,view,position,id)->
        {
            String item = (String) parent.getItemAtPosition(position);
            String[] parts = item.split(" - ");
            selectedId = parts[0];
            editText1.setText(parts[1]);
            editText2.setText(parts[2]);
        });

    }

    private void insertData() {
        if (!editText1.getText().toString().isEmpty() && !editText2.getText().toString().isEmpty()) {
            boolean inserted = db.insertData(editText1.getText().toString(), editText2.getText().toString());
            Toast.makeText(this, inserted ? "Insertat correctament" : "Error al inserir", Toast.LENGTH_SHORT).show();
            editText1.setText("");
            editText2.setText("");
        } else {
            Toast.makeText(this, "No es pot insertar. S'han de completar el dos camps.", Toast.LENGTH_SHORT).show();
        }
    }

    private void listData() {
        Cursor cursor = db.getAllData();
        ArrayList<String> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            list.add(cursor.getString(0) + " - " + cursor.getString(1) + " - " + cursor.getString(2));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        selectedId = null;
    }

    private void updateDate() {
        if (selectedId != null) {
            new AlertDialog.Builder(this)
                    .setMessage("Estàs segur que vols modificar aquest registre?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        boolean updated = db.updateData(selectedId, editText1.getText().toString(), editText2.getText().toString());
                        Toast.makeText(this, updated ? "Modificat correctament" : "Error al modificar", Toast.LENGTH_SHORT).show();
                        listData();
                    })
                    .setNegativeButton("No", null)
                    .show();
        } else {
            Toast.makeText(this, "Selecciona un registre primer", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteFromList() {
        if (selectedId != null) {
            new AlertDialog.Builder(this)
                    .setMessage("Estàs segur que vols esborrar aquest registre?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        int deleted = db.deleteData(selectedId);
                        Toast.makeText(this, deleted > 0 ? "Esborrat correctament" : "Error al esborrar", Toast.LENGTH_SHORT).show();
                        listData();
                    })
                    .setNegativeButton("No", null)
                    .show();
        } else {
            Toast.makeText(this, "Selecciona un registre primer", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteById (){
        if (!editText3.getText().toString().isEmpty()) {
            new AlertDialog.Builder(this)
                    .setMessage("Estàs segur que vols esborrar aquest registre?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        int deleted = db.deleteData(editText3.getText().toString());
                        Toast.makeText(this, deleted > 0 ? "Esborrat correctament" : "Error al esborrar", Toast.LENGTH_SHORT).show();
                        listData();
                    })
                    .setNegativeButton("No", null)
                    .show();
        } else {
            Toast.makeText(this, "Indica un ID al camp 'Id per esborrar'.", Toast.LENGTH_SHORT).show();
        }
    }


}
