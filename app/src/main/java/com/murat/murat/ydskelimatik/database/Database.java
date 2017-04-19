package com.murat.murat.ydskelimatik.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Database extends SQLiteOpenHelper {
    SQLiteDatabase db;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ogrenilenkelimeler";

    private static final String TABLE_ADI = "ogrenilenler";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = " CREATE TABLE " + TABLE_ADI + " (datakelime   TEXT , dataanlami TEXT , cevapBir TEXT, cevapIki TEXT, cevapUc TEXT );";
        db.execSQL(CREATE_TABLE);

    }

    public void kelime_ekle(String dbkelime, String dbanlami, String cevap1, String cevap2, String cevap3) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("datakelime", dbkelime);
        values.put("dataanlami", dbanlami);
        values.put("cevapBir", cevap1);
        values.put("cevapIki", cevap2);
        values.put("cevapUc", cevap3);
        db.insert("ogrenilenler", null, values);
        Log.d("kelime eklendi", "sad");
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST ogrenilenler");
        onCreate(db);
    }


    public ArrayList<HashMap<String, String>> kelimeler() {

        //Bu methodda ise tablodaki tüm değerleri alıyoruz
        //ArrayList adı üstünde Array lerin listelendiği bir Array.Burda hashmapleri listeleyeceğiz
        //Herbir satırı değer ve value ile hashmap a atıyoruz. Her bir satır 1 tane hashmap arrayı demek.
        //olusturdugumuz tüm hashmapleri ArrayList e atıp geri dönüyoruz(return).

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM  ogrenilenler";
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> ogrenilenkelimelerList = new ArrayList<HashMap<String, String>>();
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }
                ogrenilenkelimelerList.add(map);
            } while (cursor.moveToNext());
        }
        db.close();

        return ogrenilenkelimelerList;
    }

    public int getRowCount() {
        // Bu method bu uygulamada kullanılmıyor ama her zaman lazım olabilir.Tablodaki row sayısını geri döner.
        String countQuery = "SELECT  * FROM ogrenilenler ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        // return row count
        return rowCount;
    }

    public void resetTables() {
        //Bunuda uygulamada kullanmıyoruz. Tüm verileri siler. tabloyu resetler.
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete("ogrenilenler", null, null);
        db.close();
    }


/*
    public void kelimeleri(String kelime, String anlami) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Bu methodda ise var olan veriyi güncelliyoruz(update)
        ContentValues values = new ContentValues();
        values.put(KITAP_ADI, kitap_adi);
        values.put(KITAP_YAZARI, kitap_yazari);


        // updating row
        db.update(TABLE_NAME, values, KITAP_ID + " = ?",
                new String[] { String.valueOf(id) });
    }*/


   /* public HashMap<String,String> kelimelerDetay(){
        //keyi belli olan değeri çekmek için kullanılmaktadır.
        HashMap<String,String> kelimelerim =  new HashMap<>();
        String selectQuery ="SELECT*FROM TABLE_NAME";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            kelimelerim.put(KELIME, cursor.getString(1));
            kelimelerim.put(ANLAMI, cursor.getString(2));
        }
        cursor.close();
        db.close();
        return kelimelerim;
    }*/

}
