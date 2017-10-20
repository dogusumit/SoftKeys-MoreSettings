package com.dogusumit.softkeys_moresettings;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;

public class Servis extends AccessibilityService {

    private final static int ATOMIC_ID = new AtomicInteger(0).incrementAndGet();
    WindowManager windowManager;
    LinearLayout linearLayout;
    ImageButton back, home, recent;
    WindowManager.LayoutParams params;
    boolean isEnabled = false;
    boolean isAdded = false;
    boolean isVibr = false;
    SharedPreferences settings;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            String s = intent.getAction();
            if (s != null && s.equals("update") && isEnabled)
                konumAyarla();
        } catch (Exception e) {
            Log.d(e.getLocalizedMessage(), e.getLocalizedMessage());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onServiceConnected() {
        try {
            windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            linearLayout = (LinearLayout) inflater.inflate(R.layout.servis_layout, null);
            isEnabled = true;
            settings = getApplicationContext().getSharedPreferences("dogusumit.dogusumit.softkeys_moresettings", 0);

            back = linearLayout.findViewById(R.id.back);
            home = linearLayout.findViewById(R.id.home);
            recent = linearLayout.findViewById(R.id.recent);

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                        if (isVibr)
                            titresim();
                    } catch (Exception e) {
                        toastla(e.getMessage());
                    }
                }
            });

            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
                        if (isVibr)
                            titresim();
                    } catch (Exception e) {
                        toastla(e.getMessage());
                    }
                }
            });

            recent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS);
                        if (isVibr)
                            titresim();
                    } catch (Exception e) {
                        toastla(e.getMessage());
                    }
                }


            });

            konumAyarla();
        } catch (Exception e) {
            toastla(e.getMessage());
        }
        super.onServiceConnected();
    }

    void toastla(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    void konumAyarla() {
        try {
            if (isAdded)
                windowManager.removeViewImmediate(linearLayout);

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
            String bFile = settings.getString("bFile", "");
            String hFile = settings.getString("hFile", "");
            String rFile = settings.getString("rFile", "");
            String bAppName = settings.getString("bAppName", "");
            String hAppName = settings.getString("hAppName", "");
            String rAppName = settings.getString("rAppName", "");

            if (orientation == 0)
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            else
                linearLayout.setOrientation(LinearLayout.VERTICAL);

            isVibr = (vibration == 1);

            if (backOnLeft == 1) {
                linearLayout.removeAllViews();
                linearLayout.addView(back);
                linearLayout.addView(home);
                linearLayout.addView(recent);
            } else {
                linearLayout.removeAllViews();
                linearLayout.addView(recent);
                linearLayout.addView(home);
                linearLayout.addView(back);
            }

            if (Build.VERSION.SDK_INT >= 22)
                params = new WindowManager.LayoutParams(barWidth, barHeight, locationX, locationY,
                        WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, PixelFormat.TRANSLUCENT);
            else
                params = new WindowManager.LayoutParams(barWidth, barHeight, locationX, locationY,
                        WindowManager.LayoutParams.TYPE_PHONE,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, PixelFormat.TRANSLUCENT);

            if(keyHeight > 0) {
                back.getLayoutParams().height = keyHeight;
                home.getLayoutParams().height = keyHeight;
                recent.getLayoutParams().height = keyHeight;
            } else {
                back.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
                home.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
                recent.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
            }
            if(keyWidth > 0) {
                back.getLayoutParams().width = keyWidth;
                home.getLayoutParams().width = keyWidth;
                recent.getLayoutParams().width = keyWidth;
            } else {
                back.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
                home.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
                recent.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
            }

            switch (backgroundColor) {
                case 0:
                    linearLayout.setBackgroundColor(Color.BLACK);
                    break;
                case 1:
                    linearLayout.setBackgroundColor(Color.BLUE);
                    break;
                case 2:
                    linearLayout.setBackgroundColor(Color.CYAN);
                    break;
                case 3:
                    linearLayout.setBackgroundColor(Color.GRAY);
                    break;
                case 4:
                    linearLayout.setBackgroundColor(Color.GREEN);
                    break;
                case 5:
                    linearLayout.setBackgroundColor(Color.MAGENTA);
                    break;
                case 6:
                    linearLayout.setBackgroundColor(Color.RED);
                    break;
                case 7:
                    linearLayout.setBackgroundColor(Color.WHITE);
                    break;
                case 8:
                    linearLayout.setBackgroundColor(Color.YELLOW);
                    break;
            }

            linearLayout.getBackground().setAlpha((255 - transparency) > 0 ? (255 - transparency) : 0);

            back.setOnLongClickListener(getLongListener(bLongPress, bAppName));
            home.setOnLongClickListener(getLongListener(hLongPress, hAppName));
            recent.setOnLongClickListener(getLongListener(rLongPress, rAppName));

            switch (bIcon) {
                default:
                    back.setImageResource(R.mipmap.ic_back);
                    break;
                case 1:
                    back.setImageResource(R.mipmap.ic_ucgen);
                    break;
                case 2:
                    back.setImageResource(R.mipmap.ic_ucgen2);
                    break;
                case 3:
                    try {
                        back.setImageURI(Uri.parse(bFile));
                    } catch (Exception e) {
                        toastla(e.getLocalizedMessage());
                    }
                    break;
            }
            switch (hIcon) {
                default:
                    home.setImageResource(R.mipmap.ic_home);
                    break;
                case 1:
                    home.setImageResource(R.mipmap.ic_yuvarlak);
                    break;
                case 2:
                    home.setImageResource(R.mipmap.ic_yuvarlak2);
                    break;
                case 3:
                    try {
                        home.setImageURI(Uri.parse(hFile));
                    } catch (Exception e) {
                        toastla(e.getLocalizedMessage());
                    }
                    break;
            }
            switch (rIcon) {
                default:
                    recent.setImageResource(R.mipmap.ic_recent);
                    break;
                case 1:
                    recent.setImageResource(R.mipmap.ic_kare);
                    break;
                case 2:
                    recent.setImageResource(R.mipmap.ic_kare2);
                    break;
                case 3:
                    try {
                        recent.setImageURI(Uri.parse(rFile));
                    } catch (Exception e) {
                        toastla(e.getLocalizedMessage());
                    }
                    break;
            }

            windowManager.addView(linearLayout, params);
            isAdded = true;
        } catch (Exception e) {
            toastla(e.getMessage());
        }
    }

    void hideKeys() {
        try {
            if (isAdded)
                windowManager.removeViewImmediate(linearLayout);
            isAdded = false;
            Intent notificationIntent = new Intent(this, Servis.class);
            notificationIntent.setAction("update");
            PendingIntent pendingIntent = PendingIntent.getService(this, 0,
                    notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                String CHANNEL_ID = getString(R.string.app_name);
                Notification notification = new Notification.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(getString(R.string.str21))
                        .setContentIntent(pendingIntent).build();
                notification.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_AUTO_CANCEL;
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                mNotificationManager.notify(ATOMIC_ID, notification);
                CharSequence name = getString(R.string.app_name);
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                mNotificationManager.createNotificationChannel(mChannel);
            } else {

                Notification notification = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(getString(R.string.str21))
                        .setContentIntent(pendingIntent).build();
                notification.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_AUTO_CANCEL;
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                mNotificationManager.notify(ATOMIC_ID, notification);
            }
        } catch (Exception e) {
            toastla(e.getLocalizedMessage());
        }
    }

    void titresim() {
        try {
            if (Build.VERSION.SDK_INT >= 26) {
                ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(100);
            }
        } catch (Exception e) {
            toastla(e.getLocalizedMessage());
        }
    }

    View.OnLongClickListener getLongListener(int i, final String s) {
        switch (i) {
            default:
                return null;
            case 1:
                return new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        openApp(s);
                        return true;
                    }
                };
            case 2:
                return new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        hideKeys();
                        return true;
                    }
                };
            case 3:
                return new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        mutePhone();
                        return true;
                    }
                };
        }
    }

    void openApp(String s) {
        try {
            Intent i = getPackageManager().getLaunchIntentForPackage(s);
            //if (i != null)
            startActivity(i);
        } catch (Exception e) {
            toastla(e.getLocalizedMessage());
        }
    }

    void mutePhone() {
        try {
            AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL)
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            else
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        } catch (Exception e) {
            toastla(e.getLocalizedMessage());
        }
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
    }

    @Override
    public void onInterrupt() {
        try {
            if (isAdded) {
                windowManager.removeViewImmediate(linearLayout);
                isAdded = false;
                isEnabled = false;
            }
        } catch (Exception e) {
            toastla(e.getLocalizedMessage());
        }
    }

    @Override
    public void onDestroy() {
        try {
            if (isAdded) {
                windowManager.removeViewImmediate(linearLayout);
                isAdded = false;
                isEnabled = false;
            }
        } catch (Exception e) {
            toastla(e.getLocalizedMessage());
        }
        super.onDestroy();
    }
}