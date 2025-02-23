package com.example.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Cream una clase que estén ("extiende") de SQLiteOpenHelper
// Per tenir les diferent accions contra la base de dades
public class SqlLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "databaseProves";
    private static final String TABLE_NAME = "my_table";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TEXT1 = "DNI";
    private static final String COLUMN_TEXT2 = "Nom";

    //Constructor de la clase que extends de SQLiteOpenHelper
    public SqlLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TEXT1 + " TEXT, " +
                COLUMN_TEXT2 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS Usuaris");
        onCreate(db);
    }

    public boolean insertData(String text1, String text2) {
       //Mètode que permet l'escriptura en la base de dades
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues NouRegistre = new ContentValues();
        NouRegistre.put(COLUMN_TEXT1, text1);
        NouRegistre.put(COLUMN_TEXT2, text2);
        long result = db.insert(TABLE_NAME, null, NouRegistre);
        return result != -1;
    }

    public Cursor getAllData() {
        //Mètode que permet la lectura de la base de dades
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    /*
    public Cursor getData() {
        //Mètode que permet la lectura de la base de dades
        SQLiteDatabase db = this.getReadableDatabase();

        String[] args = new String[] {"5"};
        Cursor myCursor = db.rawQuery("SELECT dni, nom FROM Usuaris WHERE dni=? ", args);
        if(myCursor.moveToFirst()){
            do{
                String dni = myCursor.getString(0);
                String nom = myCursor.getString(1);

            } while (myCursor.moveToNext());
        }
        myCursor.close();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
    */

    public boolean updateData(String id, String text1, String text2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TEXT1, text1);
        contentValues.put(COLUMN_TEXT2, text2);
        int rowsUpdated = db.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[]{id});
        return rowsUpdated > 0;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{id});
    }
}
