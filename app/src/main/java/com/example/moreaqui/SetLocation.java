package com.example.moreaqui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moreaqui.R;
import com.google.android.material.snackbar.Snackbar;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.INTERNET;

public class SetLocation extends AppCompatActivity implements LocationListener {

    // declara constante com a tag que será mostrada no Log
    private static final String TAG = "SetLocation";

    // declara as variaveis que farão o link com os elementos do XML (layout)
    private TextView latitudeText;
    private TextView longitudeText;
    // declara o objeto LocationManager para recuperar a localização do usuário
    private LocationManager locationManager;
    // declara um string para determinar o tipo de provedor de localização
    private String provider;

    // declara uma view que será atualizada com os dados de localização
    private View view;
    // declara uma constante que guarda as permissões necessárias para acessar
    // a internet o GPS
    private static final String[] PERMISSIONS = {"android.permission.INTERNET",
            "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"};

    // declara uma constante que informa o valor do tipo de permissão que o app precisa
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);

        // associa a view ao layout que está na activity_main.xml
        view = findViewById(R.id.main_layout);

        // associa os textviews da classe com os textviews do xml
        latitudeText = (TextView) findViewById(R.id.latitude_text);
        longitudeText = (TextView) findViewById(R.id.longitude_text);

        // recupera o serviço de localização
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // declara um critério de atualização da localização
        Criteria criteria = new Criteria();
        // inicializa provider com o provedor diponibilizado pelo locationManager
        provider = locationManager.getBestProvider(criteria, false);

        // verifica se tem permissão para acessar internet e o GPS
        if (checkPermission()) {
            // caso a permissão já tenha sido concedida, atualiza a localização
            Location location = locationManager.getLastKnownLocation(provider);

            // caso o localização não seja nula
            if (location != null) {
                Log.d(TAG, "Provider " + provider + "foi selecionado.");
                // chama o listener para atualizar a localização
                onLocationChanged(location);
            } else {
                // caso contrário, informar que localização não está disponível
                latitudeText.setText(R.string.location_not_available);
                longitudeText.setText(R.string.location_not_available);
            }
        } else {
            // caso não tenha permissão, solicita a permissão ao usuário
            requestPermission();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        // verifica as permissões concedidas
        if (checkPermission()) {
            // para de solicitar a atualização da localização
            locationManager.removeUpdates(this);
            // solita a atualização da localização
            locationManager.requestLocationUpdates(provider, 400, 1, this);
        } else {
            // caso contrário, solicita novamente a permissão de acesso para o usuário
            requestPermission();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        // para de solicitar a atualização da localização
        locationManager.removeUpdates(this);
    }

    /***
     * Método do listener responsável por informar a atualização de localização
     * @param location Location
     */
    @Override
    public void onLocationChanged(@NonNull Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        latitudeText.setText(getString(R.string.point_label, lat));
        longitudeText.setText(getString(R.string.point_label, lng));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        Toast.makeText(this, "Novo provider " + provider, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Toast.makeText(this, "Provider desabilitado", Toast.LENGTH_SHORT).show();
    }

    /***
     * Método que verifica as permissão de acesso a Internet e ao GPS
     * @return boolean
     */
    private boolean checkPermission() {
        int internetResult = ContextCompat.checkSelfPermission(getApplicationContext(), INTERNET);
        int fineLocationResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int coarseLocationResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);

        // retorna true caso tenha as três permissões ou false caso uma das permissões tenha
        // sido negada
        return internetResult == PackageManager.PERMISSION_GRANTED &&
                fineLocationResult == PackageManager.PERMISSION_GRANTED &&
                coarseLocationResult == PackageManager.PERMISSION_GRANTED;
    }

    /***
     * Método que pede autorização de acesso ao usuário para Internet e GPS
     */
    private void requestPermission() {

        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);
        // recria a MainActivity com as permissões concedidas
        this.recreate();

    }

    /***
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        // verifca se as permissões solicitadas foram concedidas pelo usuário
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length > 0) {

            boolean internetAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean fineLocationAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            boolean coarseLocationAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;

            if (internetAccepted && fineLocationAccepted && coarseLocationAccepted) {
                Snackbar.make(view,
                        "Permissões garantidas.",
                        Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(view, "Permissões negadas.", Snackbar.LENGTH_LONG).show();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                        showMessageOKCancel("Você precisa autorizar todas as permissões.",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(PERMISSIONS,
                                                    PERMISSION_REQUEST_CODE);
                                        }
                                    }
                                });
                        return;
                    }
                }

            }
        }
    }


    /***
     * Método responsável por mostrar ao usuário uma caixa de dialogo solicitando a permissão
     * de uso da Internet e do GPS
     * @param message
     * @param okListener
     */
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(SetLocation.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancelar", null)
                .create()
                .show();
    }

}