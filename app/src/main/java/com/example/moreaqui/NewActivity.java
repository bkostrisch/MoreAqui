package com.example.moreaqui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

public class NewActivity extends AppCompatActivity {

    String tipo;
    String tamanho;
    Banco db = new Banco(this);
    Imovel im = new Imovel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        RadioButton casa = (RadioButton) findViewById(R.id.btn_casa);
        RadioButton apt = (RadioButton) findViewById(R.id.btn_apartamento);
        RadioButton loja = (RadioButton) findViewById(R.id.btn_loja);
        RadioButton ns = (RadioButton) findViewById(R.id.btn_naosei);
        RadioButton peq = (RadioButton) findViewById(R.id.btn_pequeno);
        RadioButton med = (RadioButton) findViewById(R.id.btn_medio);
        RadioButton gnd = (RadioButton) findViewById(R.id.btn_grande);
        RadioButton ns2 = (RadioButton) findViewById(R.id.btn_naosei2);

        CheckBox build = (CheckBox) findViewById(R.id.btn_emconstrucao);
        CheckBox occupy = (CheckBox) findViewById(R.id.btn_ocupado);

        EditText telefone = (EditText) findViewById(R.id.phone_input);
        ImageButton pronto = (ImageButton) findViewById(R.id.btn_pronto);



        /* Botão Pronto */

        pronto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    im.setPhone(telefone.getText().toString());
                    im.setType(tipo);
                    im.setSize(tamanho);

                    if (build.isChecked()){
                        im.setBuilding("Em Construção");
                    }   else {
                        im.setBuilding("Pronto");
                    }

                    if(occupy.isChecked()){
                        im.setOccupy("Ocupado");
                    } else {
                        im.setOccupy("Sem Moradores");
                    }

                    if (telefone.length() == 11 && tipo != null && tamanho != null ){

                        db.addImovel(im);

                        Log.v("Imóvel Cadastrado", "Id: " + im.getId() + "Telefone:" + im.getPhone() + " Tipo: " + im.getType() + " Tamanho: " + im.getSize() + " Status: " + im.getBuilding() + " e " + im.getOccupy() );
                        finish();
                        Toast.makeText(getApplicationContext(), "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show();


                    } else if (telefone.length() != 11){

                        Toast.makeText(NewActivity.this, "Verifique o número de telefone e tente novamente!", Toast.LENGTH_SHORT).show();

                    } else if (telefone.length() == 11 && (tipo == null || tamanho == null)){

                        Toast.makeText(NewActivity.this, "Dados incompletos!", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {

                    Toast.makeText(NewActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });


        /* Checkbox */

        casa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                apt.setChecked(false);
                loja.setChecked(false);
                ns.setChecked(false);

                tipo = "Casa";


            }
        });

        apt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                casa.setChecked(false);
                loja.setChecked(false);
                ns.setChecked(false);

                tipo = "Apartamento";

            }
        });

        loja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                casa.setChecked(false);
                apt.setChecked(false);
                ns.setChecked(false);

                tipo = "Loja";

            }
        });

        ns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                casa.setChecked(false);
                apt.setChecked(false);
                loja.setChecked(false);

                tipo = "Não Sei";

            }
        });

        peq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                med.setChecked(false);
                gnd.setChecked(false);
                ns2.setChecked(false);

                tamanho = "Pequeno";

            }
        });

        med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                peq.setChecked(false);
                gnd.setChecked(false);
                ns2.setChecked(false);

                tamanho = "Médio";

            }
        });

        gnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                peq.setChecked(false);
                med.setChecked(false);
                ns2.setChecked(false);

                tamanho = "Grande";

            }
        });

        ns2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                peq.setChecked(false);
                med.setChecked(false);
                gnd.setChecked(false);

                tamanho = "Não Sei";

            }
        });

    }
}