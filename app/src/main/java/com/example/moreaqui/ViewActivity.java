package com.example.moreaqui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity {

    ListView lista_imoveis = (ListView) findViewById(R.id.lista_imoveis);

    Banco db = new Banco(this);

    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        listarImoveis();

    }

    public void listarImoveis(){

        List<Imovel> imoveis = db.listaImoveis();

        arrayList = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(ViewActivity.this, android.R.layout.simple_list_item_1, arrayList);

        lista_imoveis.setAdapter(adapter);

        for(Imovel i : imoveis){

            Log.d("Lista", "\nID: " + i.getId() + "-" + i.getPhone() + "-" + i.getType() + "-" + i.getSize() + "-" + i.getBuilding());
            arrayList.add(i.getId() + "-" + i.getPhone() + "-" + i.getType() + "-" + i.getSize() + "-" + i.getBuilding());
            adapter.notifyDataSetChanged();
        }


    }
}