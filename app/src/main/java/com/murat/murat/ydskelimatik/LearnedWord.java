package com.murat.murat.ydskelimatik;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;

import com.murat.murat.ydskelimatik.database.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LearnedWord extends AppCompatActivity {


    ListView lv;
    ArrayAdapter adapter;
    Database kelimeler;
    SQLiteDatabase db;
    String[] dizi = null;
    ArrayList<HashMap<String, String>> keyValue = null;

    ArrayList<kelimeogrenStructer> klm = new ArrayList<>();
    List<dataStructure> listeKelime = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ogrenilenler);
        kelimeler = new Database(this);
        keyValue = kelimeler.kelimeler();//kelime listesini alıyoruz
        if (keyValue.size() == 0) {//kelime listesi boşsa
            Toast.makeText(getApplicationContext(), "Henüz kelime Eklenmemiş.", Toast.LENGTH_LONG).show();
        } else {
            dizi = new String[keyValue.size()];
            listeKelime = new ArrayList<>();
            for (int i = 0; i < keyValue.size(); i++) {
                dataStructure dst = new dataStructure(keyValue.get(i).get("datakelime").toString(), keyValue.get(i).get("dataanlami").toString());
                listeKelime.add(dst);
            }
            ListView listemiz = (ListView) findViewById(R.id.lvogrenilenler);

            listemiz.setAdapter(new kelimeAdapter(this,listeKelime));

        }
    }

    public void onResume() {
        super.onResume();

    }

    public void start_test(View view) {
        Intent i = new Intent(ogrenilenler.this, kendiniDene.class);
        startActivity(i);
    }

    public class WordAdapter extends BaseAdapter {

        LayoutInflater layoutInflater;
        List<dataStructure> kelimelerList;

        public WordAdapter(Activity activity, List<dataStructure> kelimeler) {

            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.kelimelerList = kelimeler;
        }

        @Override
        public int getCount() {
            return kelimelerList.size();
        }

        @Override
        public Object getItem(int position) {
            return getItemId(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View satirgorunum;
            satirgorunum = layoutInflater.inflate(R.layout.listitem,null);
            TextView kelime = (TextView)satirgorunum.findViewById(R.id.tvkelime);
            TextView anlami = (TextView)satirgorunum.findViewById(R.id.tvanlami);
            dataStructure dst =   kelimelerList.get(position);
            kelime.setText(dst.getKey());
            anlami.setText(dst.getValue());
            return  satirgorunum;
        }
    }
}
