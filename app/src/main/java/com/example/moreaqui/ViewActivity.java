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

    ListView lista_imoveis;
    Banco db;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

         lista_imoveis = (ListView) findViewById(R.id.lista_imoveis);

         db = new Banco(this);

        listarImoveis();

    }

    public void listarImoveis(){

        List<Imovel> imoveis = db.listaImoveis();

        arrayList = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(ViewActivity.this, android.R.layout.simple_list_item_1, arrayList);

        lista_imoveis.setAdapter(adapter);

        for(Imovel i : imoveis){

            Log.d(" Lista", "\n Telefone: "+ i.getPhone() + " Tipo: " + i.getType() + " Tamanho: " + i.getSize() + " Status: " + i.getBuilding() + " e " + i.getOccupy() + " Localização: " + i.getLatitude() + " // " + i.getLongitude());
            arrayList.add("Telefone: " + i.getPhone() + "\nTipo: " + i.getType() + "\nTamanho: " + i.getSize() + "\nStatus: " + i.getBuilding() + " e " + i.getOccupy() + "\nLocalização: " + i.getLatitude() + " // " + i.getLongitude());
            adapter.notifyDataSetChanged();

        }


    }
}