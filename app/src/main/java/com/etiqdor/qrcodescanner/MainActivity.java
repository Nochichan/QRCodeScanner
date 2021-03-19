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
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.etiqdor.qrcodescanner.sqlite.SQLiteHelper;
import com.etiqdor.qrcodescanner.sqlite.SQLiteUtil;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

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

        this.buttonList = findViewById(R.id.button_list);
        this.buttonSelectNum = findViewById(R.id.button_select_num);
        this.buttonReset = findViewById(R.id.button_reset);

        this.latitude = findViewById(R.id.latitude);
        this.longitude = findViewById(R.id.longitude);
        this.altitude= findViewById(R.id.altitude);
        this.speed = findViewById(R.id.vitesse);
        this.num = findViewById(R.id.current_num);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        ArrayList<LocationProvider> providers = new ArrayList<LocationProvider>();
        List<String> names = locationManager.getProviders(true);

        for (String name : names)
            providers.add(locationManager.getProvider(name));

        Criteria critere = new Criteria();

        // Pour indiquer la précision voulue
        // On peut mettre ACCURACY_FINE pour une haute précision ou ACCURACY_COARSE pour une moins bonne précision
        critere.setAccuracy(Criteria.ACCURACY_FINE);

        // Est-ce que le fournisseur doit être capable de donner une altitude ?
        critere.setAltitudeRequired(true);

        // Est-ce que le fournisseur doit être capable de donner une direction ?
        critere.setBearingRequired(true);

        // Est-ce que le fournisseur peut être payant ?
        critere.setCostAllowed(false);

        // Pour indiquer la consommation d'énergie demandée
        // Criteria.POWER_HIGH pour une haute consommation, Criteria.POWER_MEDIUM pour une consommation moyenne et Criteria.POWER_LOW pour une basse consommation
        critere.setPowerRequirement(Criteria.POWER_HIGH);

        // Est-ce que le fournisseur doit être capable de donner une vitesse ?
        critere.setSpeedRequired(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, /*60000*/ 100, 0, new LocationListener() {

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {

            }

            @Override
            public void onLocationChanged(Location location) {
                location2 = location;

                latitude.setText("Latitude : " + location.getLatitude());
                longitude.setText("Longitude : " + location.getLongitude());
                altitude.setText("Altitude : " + location.getAltitude());
                speed.setText("Speed : " + location.getSpeed());
//                if(TelephoneNum.currentNum != null) {
//                    System.out.println(TelephoneNum.currentNum.getName());
//                    num.setText("Numero : " + TelephoneNum.currentNum.getName());
//                }
                //System.out.println("Latitude " + location.getLatitude() + " et longitude " + location.getLongitude());
            }
        });

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        this.mCodeScanner = new CodeScanner(this, scannerView);
        this.mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //String content = line + ". Latitude " + location2.getLatitude() + " et longitude " + location2.getLongitude();
                        if (location2 != null){
                            SQLiteUtil.insertInto(helper, ""+location2.getLatitude(),""+location2.getLongitude(),""+location2.getAltitude(), result.getText());
                            //Toast.makeText(MainActivity.this, "Latitude " + location2.getLatitude() + " et longitude " + location2.getLongitude(), Toast.LENGTH_SHORT).show();

                            sendMessage(result);

//                            SmsManager.getDefault().sendTextMessage(TelephoneNum.GAYA.getNum(), null, msg, null, null);
//                            SmsManager.getDefault().sendTextMessage(TelephoneNum.MAXIME.getNum(), null, msg, null, null);
//                            SmsManager.getDefault().sendTextMessage(TelephoneNum.AXEL.getNum(), null, msg, null, null);
//                            SmsManager.getDefault().sendTextMessage(TelephoneNum.DORYAN.getNum(), null, msg, null, null);
//                            SmsManager.getDefault().sendTextMessage(TelephoneNum.KARIM.getNum(), null, msg, null, null);
//                            SmsManager.getDefault().sendTextMessage(TelephoneNum.LEO.getNum(), null, msg, null, null);
//                            SmsManager.getDefault().sendTextMessage(TelephoneNum.SEVAN.getNum(), null, msg, null, null);
//                            SmsManager.getDefault().sendTextMessage(TelephoneNum.RYAN.getNum(), null, msg, null, null);
//                            SmsManager.getDefault().sendTextMessage(TelephoneNum.UBALDO.getNum(), null, msg, null, null);
//                            SmsManager.getDefault().sendTextMessage(TelephoneNum.FLORIAN.getNum(), null, msg, null, null);
                        }
                       /*String url = result.getText();
                        Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);*/
                    }
                });
            }
        });
        scannerView.setOnClickListener(view -> mCodeScanner.startPreview());

        this.buttonList.setOnClickListener(v -> {
            Intent menuActivity = new Intent(MainActivity.this, LocalisationActivity.class);
            startActivity(menuActivity);
        });

        this.buttonSelectNum.setOnClickListener(v->{
            Intent menuActivity = new Intent(MainActivity.this, NumActivity.class);
            startActivity(menuActivity);
        });

        /*this.buttonAddLocation.setOnClickListener(v -> {
            if (location2 != null){

//                    SQLiteDatabase db = helper.getReadableDatabase();
//                    db.execSQL("DROP TABLE qr_code;");
//                    String sqlCreateDatatableQuiz = "CREATE TABLE qr_code(id_qr_code INTEGER PRIMARY KEY, latitude TEXT, longitude TEXT, altitude TEXT);";
//                    db.execSQL(sqlCreateDatatableQuiz);

                //LocationSave.saveLocation(location2.getLatitude(), location2.getLongitude(), location2.getAltitude(), location2.getSpeed());
                SQLiteUtil.insertInto(helper, ""+location2.getLatitude(),""+location2.getLongitude(),""+location2.getAltitude(), result.getT);
            }
        });*/

        this.buttonReset.setOnClickListener(v -> SQLiteUtil.resetDatabase(helper, MainActivity.this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(TelephoneNum.currentNum != null)
            this.num.setText("Numero : " + TelephoneNum.currentNum.getName());
        this.mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        this.mCodeScanner.releaseResources();
        super.onPause();
    }


    private void sendMessage(Result result){
        String msg = "Latitude : " + location2.getLatitude() + ", Longitude : " +  location2.getLongitude() + ", Altitude : " + location2.getAltitude() + ", Vitesse :" + location2.getSpeed() + ", Site Web : " + result.getText() ;

        if (TelephoneNum.currentNum != null){
            SmsManager.getDefault().sendTextMessage(TelephoneNum.currentNum.getNum(), null, msg, null, null);
        }
        else{
            Toast.makeText(MainActivity.this, "Aucun numéro séléctionné", Toast.LENGTH_SHORT).show();
        }
    }
}