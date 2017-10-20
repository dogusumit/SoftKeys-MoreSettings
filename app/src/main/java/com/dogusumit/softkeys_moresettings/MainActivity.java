package com.dogusumit.softkeys_moresettings;

import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            izinleriKontrolEt();
            final Button buton1 = (Button) findViewById(R.id.buton1);
            final Button buton3 = (Button) findViewById(R.id.buton3);
            final Button buton4 = (Button) findViewById(R.id.buton4);
            final Button buton5 = (Button) findViewById(R.id.buton5);
            final Button buton6 = (Button) findViewById(R.id.buton6);
            final Button buton7 = (Button) findViewById(R.id.buton7);
            final Button buton8 = (Button) findViewById(R.id.buton8);
            final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
            final CheckBox checkbox1 = (CheckBox) findViewById(R.id.checkbox1);
            final CheckBox checkbox2 = (CheckBox) findViewById(R.id.checkbox2);
            final EditText editText1 = (EditText) findViewById(R.id.edittext1);
            final EditText editText2 = (EditText) findViewById(R.id.edittext2);
            final EditText editText3 = (EditText) findViewById(R.id.edittext3);
            final EditText editText4 = (EditText) findViewById(R.id.edittext4);
            final EditText editText5 = (EditText) findViewById(R.id.edittext5);
            final EditText editText6 = (EditText) findViewById(R.id.edittext6);
            final EditText editText7 = (EditText) findViewById(R.id.edittext7);
            final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
            final Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
            final Spinner spinner4 = (Spinner) findViewById(R.id.spinner4);
            final Spinner spinner5 = (Spinner) findViewById(R.id.spinner5);
            final Spinner spinner6 = (Spinner) findViewById(R.id.spinner6);
            final Spinner spinner7 = (Spinner) findViewById(R.id.spinner7);
            final Spinner spinner8 = (Spinner) findViewById(R.id.spinner8);

            final SharedPreferences settings = getApplicationContext().getSharedPreferences("dogusumit.dogusumit.softkeys_moresettings", 0);
            final SharedPreferences.Editor editor = settings.edit();
            int orientation = settings.getInt("orientation", 0);
            int vibration = settings.getInt("vibration", 0);
            int backOnLeft = settings.getInt("backOnLeft", 0);
            int barHeight = settings.getInt("barHeight", 0);
            int barWidth = settings.getInt("barWidth", 0);
            int locationX = settings.getInt("locationX", 0);
            int locationY = settings.getInt("locationY", 0);
            int transparency = settings.getInt("transparency", 0);
            int keyHeight = settings.getInt("keyHeight", 0);
            int keyWidth = settings.getInt("keyWidth", 0);
            int backgroundColor = settings.getInt("backgroundColor", 0);
            int bLongPress = settings.getInt("bLongPress", 0);
            int hLongPress = settings.getInt("hLongPress", 0);
            int rLongPress = settings.getInt("rLongPress", 0);
            int bIcon = settings.getInt("bIcon", 0);
            int hIcon = settings.getInt("hIcon", 0);
            int rIcon = settings.getInt("rIcon", 0);

            spinner1.setSelection(orientation);
            checkbox1.setChecked((vibration == 1));
            checkbox2.setChecked((backOnLeft == 1));
            editText1.setText(String.valueOf(barHeight));
            editText2.setText(String.valueOf(barWidth));
            editText3.setText(String.valueOf(locationX));
            editText4.setText(String.valueOf(locationY));
            editText5.setText(String.valueOf(transparency));
            editText6.setText(String.valueOf(keyHeight));
            editText7.setText(String.valueOf(keyWidth));
            spinner2.setSelection(backgroundColor);
            spinner3.setSelection(bLongPress);
            spinner4.setSelection(hLongPress);
            spinner5.setSelection(rLongPress);
            spinner6.setSelection(bIcon);
            spinner7.setSelection(hIcon);
            spinner8.setSelection(rIcon);

            buton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    startActivity(intent);
                }
            });
            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    editor.putInt("orientation", i).apply();
                    if (isAccessibilityEnabled())
                        servisGuncelle();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    editor.putInt("vibration", (isChecked ? 1 : 0)).apply();
                    if (isAccessibilityEnabled())
                        servisGuncelle();
                }
            });
            checkbox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    editor.putInt("backOnLeft", (isChecked ? 1 : 0)).apply();
                    if (isAccessibilityEnabled())
                        servisGuncelle();
                }
            });
            editText1.addTextChangedListener(getEditTextWatcher(editor, "barHeight"));
            editText2.addTextChangedListener(getEditTextWatcher(editor, "barWidth"));
            editText3.addTextChangedListener(getEditTextWatcher(editor, "locationX"));
            editText4.addTextChangedListener(getEditTextWatcher(editor, "locationY"));
            editText5.addTextChangedListener(getEditTextWatcher(editor, "transparency"));
            editText6.addTextChangedListener(getEditTextWatcher(editor, "keyHeight"));
            editText7.addTextChangedListener(getEditTextWatcher(editor, "keyWidth"));
            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    editor.putInt("backgroundColor", i).apply();
                    if (isAccessibilityEnabled())
                        servisGuncelle();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    editor.putInt("bLongPress", i).apply();
                    if (i == 1) {
                        buton3.setVisibility(View.VISIBLE);
                    } else {
                        buton3.setVisibility(View.GONE);
                        if (isAccessibilityEnabled())
                            servisGuncelle();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            buton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(getBaseContext(), ActivityPicker.class);
                        intent.putExtra("extra", "bAppName");
                        startActivity(intent);
                    } catch (Exception e) {
                        toastla(e.getLocalizedMessage());
                    }
                }
            });

            spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    editor.putInt("hLongPress", i).apply();
                    if (i == 1) {
                        buton4.setVisibility(View.VISIBLE);
                    } else {
                        buton4.setVisibility(View.GONE);
                        if (isAccessibilityEnabled())
                            servisGuncelle();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            buton4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(getBaseContext(), ActivityPicker.class);
                        intent.putExtra("extra", "hAppName");
                        startActivity(intent);
                    } catch (Exception e) {
                        toastla(e.getLocalizedMessage());
                    }
                }
            });

            spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    editor.putInt("rLongPress", i).apply();
                    if (i == 1) {
                        buton5.setVisibility(View.VISIBLE);
                    } else {
                        buton5.setVisibility(View.GONE);
                        if (isAccessibilityEnabled())
                            servisGuncelle();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            buton5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(getBaseContext(), ActivityPicker.class);
                        intent.putExtra("extra", "rAppName");
                        startActivity(intent);
                    } catch (Exception e) {
                        toastla(e.getLocalizedMessage());
                    }
                }
            });

            spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    editor.putInt("bIcon", i).apply();
                    if (i == 3)
                        buton6.setVisibility(View.VISIBLE);
                    else {
                        buton6.setVisibility(View.GONE);
                        if (isAccessibilityEnabled())
                            servisGuncelle();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            buton6.setOnClickListener(imagePickerClick("bFile"));
            spinner7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    editor.putInt("hIcon", i).apply();
                    if (i == 3)
                        buton7.setVisibility(View.VISIBLE);
                    else {
                        buton7.setVisibility(View.GONE);
                        if (isAccessibilityEnabled())
                            servisGuncelle();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            buton7.setOnClickListener(imagePickerClick("hFile"));
            spinner8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    editor.putInt("rIcon", i).apply();
                    if (i == 3)
                        buton8.setVisibility(View.VISIBLE);
                    else {
                        buton8.setVisibility(View.GONE);
                        if (isAccessibilityEnabled())
                            servisGuncelle();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            buton8.setOnClickListener(imagePickerClick("rFile"));

            AdView mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        } catch (Exception e) {
            toastla(e.getLocalizedMessage());
        }
    }


    private void uygulamayiOyla() {
        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.dogusumit/store/apps/details?id=" + getApplicationContext().getPackageName())));
            } catch (Exception ane) {
                toastla(e.getMessage());
            }
        }
    }

    private void marketiAc() {
        try {
            Uri uri = Uri.parse("market://developer?id=" + getString(R.string.play_store_id));
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.dogusumit/store/apps/developer?id=" + getString(R.string.play_store_id))));
            } catch (Exception ane) {
                toastla(e.getMessage());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.oyla:
                uygulamayiOyla();
                return true;
            case R.id.market:
                marketiAc();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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

    void izinleriKontrolEt() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                }
            }
            NotificationManager notificationManager =
                    (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !notificationManager.isNotificationPolicyAccessGranted()) {
                Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                startActivity(intent);
            }
        } catch (Exception e) {
            toastla(e.getLocalizedMessage());
        }
    }

    TextWatcher getEditTextWatcher(final SharedPreferences.Editor spedit, final String key) {
        try {
            return new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    try {
                        int h = Integer.parseInt(editable.toString());
                        spedit.putInt(key, h).apply();
                        if (isAccessibilityEnabled())
                            servisGuncelle();
                    } catch (Exception e) {
                        toastla(e.getLocalizedMessage());
                    }
                }
            };
        } catch (Exception e) {
            toastla(e.getLocalizedMessage());
            return null;
        }
    }

    View.OnClickListener imagePickerClick(final String key) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                char c = key.charAt(0);
                int ascii = (int) c;
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, ascii);
            }
        };
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        SharedPreferences settings = getApplicationContext().getSharedPreferences("dogusumit.dogusumit.softkeys_moresettings", 0);
        SharedPreferences.Editor editor = settings.edit();
        switch (requestCode) {
            case (int) 'b':
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    editor.putString("bFile", selectedImage.toString()).apply();
                    if (isAccessibilityEnabled())
                        servisGuncelle();
                }
                break;
            case (int) 'h':
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    editor.putString("hFile", selectedImage.toString()).apply();
                    if (isAccessibilityEnabled())
                        servisGuncelle();
                }
                break;
            case (int) 'r':
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    editor.putString("rFile", selectedImage.toString()).apply();
                    if (isAccessibilityEnabled())
                        servisGuncelle();
                }
                break;
        }
    }
}