package com.example.moreaqui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    Banco db = new Banco(this);
    private Object ContextWrapper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* Referenciamento de Botões */

        ImageButton btn_novo = (ImageButton) findViewById(R.id.btn_novo);
        btn_novo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), NewActivity.class));
            }
        });

        ImageButton btn_visualizar = (ImageButton) findViewById(R.id.btn_visualizar);
        btn_visualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ViewActivity.class));
            }
        });

        ImageButton btn_mapa = (ImageButton) findViewById(R.id.btn_mapa);
        btn_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), MapActivity.class));
            }
        });

        ImageButton btn_gravar = (ImageButton) findViewById(R.id.btn_gravar);
        btn_gravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), RecordActivity.class));
            }
        });

        Imovel imovel = db.selecionarImovel(1);

        Log.d("Imovel Selecionado", " Codigo: " + imovel.getId() + " Phone: " + imovel.getPhone() + " Tipo: " + imovel.getType() + " Tam: " + imovel.getSize()+ " Build: " + imovel.getBuilding());

    }


}