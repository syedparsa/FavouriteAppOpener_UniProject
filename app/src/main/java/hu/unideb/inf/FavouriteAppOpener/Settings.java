package hu.unideb.inf.FavouriteAppOpener;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public final String MyPREFERENCES = "MyPref" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Spinner leftAppSpinner = findViewById(R.id.rightapp3);
        Spinner rightAppSpinner = findViewById(R.id.rightapp);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        final ArrayAdapter<Apps> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,MainActivity.finalApps);
       // ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, MainActivity.namesList);
        leftAppSpinner.setAdapter(adapter);
        rightAppSpinner.setAdapter(adapter);

        String appName= sharedpreferences.getString(getString(R.string.RightKeyApp),MainActivity.finalApps.get(0).getAppName());
        String pName = sharedpreferences.getString(getString(R.string.RightKey),MainActivity.finalApps.get(0).getPackageName());

        rightAppSpinner.setSelection(adapter.getPosition(new Apps(pName,appName)));
      //rightAppSpinner.setSelection(adapter.getPosition (new Apps(sharedpreferences.getString(getString(R.string.RightKey),MainActivity.finalApps.get(0).getPackageName()),sharedpreferences.getString(getString(R.string.RightKeyApp),MainActivity.finalApps.get(0).getAppName()))));

        appName= sharedpreferences.getString(getString(R.string.LeftKeyApp),MainActivity.finalApps.get(0).getAppName());
        pName = sharedpreferences.getString(getString(R.string.LeftKey),MainActivity.finalApps.get(0).getPackageName());

        leftAppSpinner.setSelection(adapter.getPosition(new Apps(pName,appName)));



        leftAppSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            Apps a = adapter.getItem(i);
            editor.putString(getString(R.string.LeftKey),a.getPackageName());
            editor.putString(getString(R.string.LeftKeyApp),a.getAppName());
            editor.apply();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    });

    rightAppSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            Apps a = adapter.getItem(i);
            editor.putString(getString(R.string.RightKey),a.getPackageName());
            editor.putString(getString(R.string.RightKeyApp),a.getAppName());
            editor.apply();

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    });



    }


}