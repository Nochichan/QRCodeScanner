package com.etiqdor.qrcodescanner;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.etiqdor.qrcodescanner.sqlite.SQLiteHelper;
import com.etiqdor.qrcodescanner.sqlite.SQLiteUtil;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * @author etiqdor
 */
public class MainActivity extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    private Location location2;

    private Button buttonList;
    private Button buttonSelectNum;
    private Button buttonReset;

    private TextView latitude;
    private TextView longitude;
    private TextView altitude;
    private TextView speed;
    private TextView num;

    protected static SQLiteHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.helper = new SQLiteHelper(this);

        // Assignement de tous les élements graphiques aux variables
        this.buttonList = findViewById(R.id.button_list);
        this.buttonSelectNum = findViewById(R.id.button_select_num);
        this.buttonReset = findViewById(R.id.button_reset);

        this.latitude = findViewById(R.id.latitude);
        this.longitude = findViewById(R.id.longitude);
        this.altitude= findViewById(R.id.altitude);
        this.speed = findViewById(R.id.vitesse);
        this.num = findViewById(R.id.current_num);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        ArrayList<LocationProvider> providers = new ArrayList<>();
        List<String> names = locationManager.getProviders(true);

        for (String name : names)
            providers.add(locationManager.getProvider(name));

        // Création des critères du fournisseur
        Criteria critere = new Criteria();
        critere.setAccuracy(Criteria.ACCURACY_FINE);
        critere.setAltitudeRequired(true); // Altitude
        critere.setBearingRequired(true); // Direction
        critere.setCostAllowed(false); // Payant
        critere.setPowerRequirement(Criteria.POWER_HIGH); // Consommation d'énergie
        critere.setSpeedRequired(true); // Vitesse

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // Lorsqu'on demande de mettre à jour la position
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, /*60000*/ 100, 0, new LocationListener() {

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}

            @Override
            public void onLocationChanged(Location location) {
                location2 = location;
                latitude.setText(("Latitude : " + location.getLatitude()));
                longitude.setText(("Longitude : " + location.getLongitude()));
                altitude.setText(("Altitude : " + location.getAltitude()));
                speed.setText(("Speed : " + location.getSpeed()));
            }
        });

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        this.mCodeScanner = new CodeScanner(this, scannerView);
        this.mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> {
            if (location2 != null){
                SQLiteUtil.insertInto(helper, ""+location2.getLatitude(),""+location2.getLongitude(),""+location2.getAltitude(), result.getText());
                sendMessage(result);
            }
//            // Ouvre un naviguateur avec le lien obtenu
//            String url = result.getText();
//            Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse(url));
//            startActivity(intent);
        }));
        scannerView.setOnClickListener(view -> mCodeScanner.startPreview());

        // Lorsqu'on appui sur le bouton Open, ouverture de la listes contenant touts les localisations enregsitrées
        this.buttonList.setOnClickListener(v -> {
            Intent menuActivity = new Intent(MainActivity.this, LocalisationActivity.class);
            startActivity(menuActivity);
        });

        // Lorsqu'on appui sur le bouton Num, ouverture de la listes contenant tous les numéros de téléphone enregsitrés
        this.buttonSelectNum.setOnClickListener(v->{
            Intent menuActivity = new Intent(MainActivity.this, NumActivity.class);
            startActivity(menuActivity);
        });

        // Lorsqu'on appui sur le bouton Reset, réinitialisaiton de la base de données
        this.buttonReset.setOnClickListener(v -> SQLiteUtil.resetDatabase(helper, MainActivity.this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(TelephoneNum.currentNum != null) {
            this.num.setText(("Numero : " + TelephoneNum.currentNum.getName()));
        }
        this.mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        this.mCodeScanner.releaseResources();
        super.onPause();
    }

    /**
     * Méthode permettant d'envoyer un message depuis le téléphone
     * @param result Le résultat du QRCode
     */
    private void sendMessage(Result result) {
        // Le message à envoyer
        String msg = "Latitude : " + location2.getLatitude() + ", Longitude : " + location2.getLongitude() + ", Altitude : " + location2.getAltitude() + ", Vitesse :" + location2.getSpeed() + ", Site Web : " + result.getText();

        // Vérifie si un numéro est sélectionné
        if (TelephoneNum.currentNum != null){ // Envoie du message
            SmsManager.getDefault().sendTextMessage(TelephoneNum.currentNum.getNum(), null, msg, null, null);
        }
        else // Affichage d'une erreur
            Toast.makeText(MainActivity.this, "Aucun numéro séléctionné", Toast.LENGTH_SHORT).show();
    }
}