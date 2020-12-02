package com.example.moreaqui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class NewActivity extends AppCompatActivity {

    /*** Declaração de variáveis de acesso ao banco, seus dados e as informações de localização. */
    String tipo;
    String tamanho;
    Banco db = new Banco(this);
    Imovel im = new Imovel();
    private static final int REQUEST_CODE = 101;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        /*** Faz o reconhecimento da estrutura do front para classe do back */
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

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();


        /* Botão Pronto */
        /*** Define o que será feito ao clicar no botão Pronto, na tela de cadastro. */
        pronto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    /*** Faz com que os valores colocados nas inputs da tela de cadastro sejam redirecionadas para o banco. */
                    im.setPhone(telefone.getText().toString());
                    im.setType(tipo);
                    im.setSize(tamanho);

                    /*** Se a checkbox "Em construção" for selecionada, o valor da variável build será Em Construição, caso contrário, será Pronto */
                    if (build.isChecked()){
                        im.setBuilding("Em Construção");
                    }   else {
                        im.setBuilding("Pronto");
                    }

                    /*** Se a checkbox "Ocupado" for selecionada, o valor da variável occupy será Ocupado, caso contrário, será Sem Moradores. */
                    if(occupy.isChecked()){
                        im.setOccupy("Ocupado");
                    } else {
                        im.setOccupy("Sem Moradores");
                    }

                    /*** Faz com que se o telefone assumir os requisitos corretos e os valores das radiobox não estiverem vazios, realize o cadastro de um novo imóvel e imprima no logcat os valores inseridos. */
                    if (telefone.length() == 11 && tipo != null && tamanho != null ){

                        db.addImovel(im);

                        Log.v("Imóvel Cadastrado", "Id: " + im.getId() + "Telefone:" + im.getPhone() + " Tipo: " + im.getType() + " Tamanho: " + im.getSize() + " Status: " + im.getBuilding() + " e " + im.getOccupy() + " Localização: " + im.getLatitude() + " // " + im.getLongitude());
                        finish();
                        Toast.makeText(getApplicationContext(), "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show();

                        /*** Caso o número de telefona seja diferente de 11, exibe uma mensagem na tela para verificação de telefone. */
                    } else if (telefone.length() != 11){

                        Toast.makeText(NewActivity.this, "Verifique o número de telefone e tente novamente!", Toast.LENGTH_SHORT).show();

                        /*** Caso o número de telefone esteja correto mas as radiobox estejam vazias, exibe mensagem explicando que há dados incompletos. */
                    } else if (telefone.length() == 11 && (tipo == null || tamanho == null)){

                        Toast.makeText(NewActivity.this, "Dados incompletos!", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {

                    Toast.makeText(NewActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });


        /* Checkbox */
        /*** Sistema criado para que as checkbox fiquem em linear layout sem poderem ser marcadas simultãneamente. */
        casa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*** Caso "casa" seja selecionada, Apartamento, loja e Não Sei não podem ser selecionados e a variável tipo assume valor de "Casa" para ser armazenado ao banco. */
                apt.setChecked(false);
                loja.setChecked(false);
                ns.setChecked(false);

                tipo = "Casa";


            }
        });

        apt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*** Caso "apartamento" seja selecionado, Casa, loja e Não Sei não podem ser selecionados e a variável tipo assume valor de "Apartamento" para ser armazenado ao banco. */
                casa.setChecked(false);
                loja.setChecked(false);
                ns.setChecked(false);

                tipo = "Apartamento";

            }
        });

        loja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*** Caso "loja" seja selecionada, Casa, Apartamento e Não Sei não podem ser selecionados e a variável tipo assume valor de "Loja" para ser armazenado ao banco. */
                casa.setChecked(false);
                apt.setChecked(false);
                ns.setChecked(false);

                tipo = "Loja";

            }
        });

        ns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*** Caso "não sei" seja selecionado, Casa, loja e Apartamento não podem ser selecionados e a variável tipo assume valor de "Não Sei" para ser armazenado ao banco. */
                casa.setChecked(false);
                apt.setChecked(false);
                loja.setChecked(false);

                tipo = "Não Sei";

            }
        });

        peq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*** Caso "pequeno" seja selecionado, Médio, Grande e Não Sei não podem ser selecionados e a variável tamanho assume valor de "Pequeno" para ser armazenado ao banco. */
                med.setChecked(false);
                gnd.setChecked(false);
                ns2.setChecked(false);

                tamanho = "Pequeno";

            }
        });

        med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*** Caso "médio" seja selecionado, Pequeno, Grande e Não Sei não podem ser selecionados e a variável tamanho assume valor de "Médio" para ser armazenado ao banco. */
                peq.setChecked(false);
                gnd.setChecked(false);
                ns2.setChecked(false);

                tamanho = "Médio";

            }
        });

        gnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*** Caso "grande" seja selecionado, Médio, Pequeno e Não Sei não podem ser selecionados e a variável tamanho assume valor de "Grande" para ser armazenado ao banco. */
                peq.setChecked(false);
                med.setChecked(false);
                ns2.setChecked(false);

                tamanho = "Grande";

            }
        });

        ns2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*** Caso "não sei" seja selecionado, Médio, Grande e Pequeno não podem ser selecionados e a variável tamanho assume valor de "Não Sei" para ser armazenado ao banco. */
                peq.setChecked(false);
                med.setChecked(false);
                gnd.setChecked(false);

                tamanho = "Não Sei";

            }
        });

    }

    /*** Acessa a localização atual do usuário após a permissão concedida. Se não conedida, ele pergunta se o usuário gostaria de conceder. */
    private void fetchLastLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);

            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if(location != null){

                    currentLocation = location;

                    im.setLatitude(currentLocation.getLatitude());
                    im.setLongitude(currentLocation.getLongitude());

                }

            }

        });

    }

    /*** Pede a permissão do usuário para saber sua localização atual. */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case REQUEST_CODE:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    fetchLastLocation();

                }

                break;
        }
    }
}