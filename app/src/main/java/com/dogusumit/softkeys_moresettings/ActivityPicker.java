package com.dogusumit.softkeys_moresettings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ActivityPicker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_activity_picker);

        try {

            final String newString;
            if (savedInstanceState == null) {
                Bundle extras = getIntent().getExtras();
                if (extras == null) {
                    newString = null;
                } else {
                    newString = extras.getString("extra");
                }
            } else {
                newString = (String) savedInstanceState.getSerializable("extra");
            }

            final ListView listView1 = (ListView) findViewById(R.id.listview1);
            Button button2 = (Button) findViewById(R.id.pickerbuton2);

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            final List<ResolveInfo> apps = getPackageManager().queryIntentActivities(intent,0);

            final ArrayList<String> liste = new ArrayList<>();
            for (int i = 0; i < apps.size(); i++) {
                ResolveInfo p = apps.get(i);
                liste.add(p.activityInfo.packageName);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, liste);
            listView1.setAdapter(adapter);

            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        final SharedPreferences settings = getApplicationContext().getSharedPreferences("dogusumit.dogusumit.softkeys_moresettings", 0);
                        final SharedPreferences.Editor editor = settings.edit();
                        editor.putString(newString, liste.get(position)).apply();
                        if (isAccessibilityEnabled())
                            servisGuncelle();
                        finish();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    void toastla(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    boolean isAccessibilityEnabled() {
        int accessibilityEnabled = 0;
        final String ACCESSIBILITY_SERVICE_NAME = getPackageName() + "/" + Servis.class.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(this.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            toastla(e.getMessage());
        }

        TextUtils.SimpleStringSplitter splitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {

            String settingValue = Settings.Secure.getString(getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                splitter.setString(settingValue);
                while (splitter.hasNext()) {
                    String accessabilityService = splitter.next();
                    if (accessabilityService.equalsIgnoreCase(ACCESSIBILITY_SERVICE_NAME)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    void servisGuncelle() {
        try {
            Intent intent = new Intent(getApplicationContext(), Servis.class);
            intent.setAction("update");
            startService(intent);
            stopService(intent);
        } catch (Exception e) {
            toastla(e.getLocalizedMessage());
        }
    }

}