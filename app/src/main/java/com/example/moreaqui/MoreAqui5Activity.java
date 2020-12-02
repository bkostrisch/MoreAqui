package com.example.moreaqui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.List;

public class MoreAqui5Activity extends AppCompatActivity {

    /*** Variável que liga a classe ao banco, utilizada aqui para teste no select. */
    // Banco db = new Banco(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        /*** Botão que redireciona o app para página de cadastrar imóveis. */
        ImageButton btn_novo = (ImageButton) findViewById(R.id.btn_novo);
        btn_novo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), NewActivity.class));
            }
        });

        /*** Botão que redireciona o app para página de visualizar imóveis cadastrados. */
        ImageButton btn_visualizar = (ImageButton) findViewById(R.id.btn_visualizar);
        btn_visualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ViewActivity.class));
            }
        });

        /*** Botão que redireciona o app para página do Mapa. */
        ImageButton btn_mapa = (ImageButton) findViewById(R.id.btn_mapa);
        btn_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), MapActivity.class));
            }
        });

        /*** Botão que grava os dados cadastrados no servidor remoto. */
        ImageButton btn_gravar = (ImageButton) findViewById(R.id.btn_gravar);
        btn_gravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Banco estateData = Banco.getInstance(MoreAqui5Activity.this);

                List<LocationEstate> estates = estateData.getAllEstates();

                new RecordData().execute(estates);

            }
        });


        /*** Utiliza o select criado no banco para conferir, atraés do id escolhido (1), se há cadastro no banco */
        //Imovel imovel = db.selecionarImovel(1);
        /*** Imprime no logcat os valores respectivos do banco para conferência. */
        //Log.d("Imovel Selecionado", " Codigo: " + imovel.getId() + " Phone: " + imovel.getPhone() + " Tipo: " + imovel.getType() + " Tam: " + imovel.getSize() + " Build: " + imovel.getBuilding() + "Localização: " + imovel.getLatitude() + " // " + imovel.getLongitude());

    }


}