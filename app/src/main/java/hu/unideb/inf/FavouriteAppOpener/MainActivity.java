package hu.unideb.inf.FavouriteAppOpener;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedpreferences;
    public final String MyPREFERENCES = "MyPref";
    private SensorManager sensorManager;
    private ShakeSensor shakeListner = new ShakeSensor();
    private Sensor shakeSensor;

   // public static List<String> namesList = new ArrayList<>();
    private  List<PackageInfo> apps = new ArrayList<>();

    public static   List<Apps> finalApps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        getApps();


        TextView tv = findViewById(R.id.name);
        tv.setMovementMethod(new ScrollingMovementMethod());
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        shakeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(shakeListner, shakeSensor, SensorManager.SENSOR_DELAY_GAME);
        shakeListner.setTv(tv);
        tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals(getString(R.string.rightmove))) {

                    launchApp(sharedpreferences.getString(getString(R.string.RightKey), finalApps.get(0).getPackageName()));

                } else if (charSequence.toString().equals(getString(R.string.leftmove))) {
                    launchApp(sharedpreferences.getString(getString(R.string.LeftKey), finalApps.get(0).getPackageName()));

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(shakeListner, shakeSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(shakeListner);
    }


    public void SettingOpen(View view) {
        Intent intent = new Intent(this, Settings.class);

        getApps();
        startActivity(intent);
    }

    private void getApps() {
        apps = getPackageManager().getInstalledPackages(0);
        finalApps = new ArrayList<>();
        for (int i = 0; i < apps.size(); i++) {
            PackageInfo packInfo = apps.get(i);
            if (this.getPackageManager().getLaunchIntentForPackage(packInfo.packageName) != null) {
                //namesList.add(packInfo.packageName);
                String appName = packInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                finalApps.add(new Apps(packInfo.packageName,appName));
            }

        }
    }

    protected void launchApp(String packageName) {


        Intent mIntent = getPackageManager().getLaunchIntentForPackage(packageName);

        if (mIntent != null) {
            Log.d("n", packageName);
            try {
                startActivity(mIntent);
            } catch (ActivityNotFoundException err) {
                Toast t = Toast.makeText(getApplicationContext(), R.string.app_not_found, Toast.LENGTH_SHORT);
                t.show();
            }

        } else {
            Toast t = Toast.makeText(getApplicationContext(),
                    R.string.app_not_found, Toast.LENGTH_SHORT);
            t.show();
        }

    }

}