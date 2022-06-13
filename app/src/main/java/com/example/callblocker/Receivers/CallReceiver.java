package com.example.callblocker.Receivers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.callblocker.Dao.AppDatabase;
import com.example.callblocker.Models.AllowedNumbers;
import com.example.callblocker.Models.BlockedNumbers;
import com.example.callblocker.Models.SilentNumbers;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class CallReceiver extends BroadcastReceiver {
    SharedPreferences prefs;
    AppDatabase db;
    @Override
    public void onReceive(Context context, Intent intent) {
        prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        db = AppDatabase.getInstance(context);

        String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
        if(number != null) {
            AudioManager audio = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

            // When Call Answered
            if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {



            // When Call Ended
            } else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                int ringerMode = prefs.getInt("ringerMode", 2);
                audio.setRingerMode(ringerMode);


            // When Call Ringing
            } else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                List<AllowedNumbers> allowedNumbers = db.allowedNumbersDao().getAllByNumber(number);

                // When number is not allowed
                if(allowedNumbers.size() == 0){
                    boolean isBlockMode = prefs.getBoolean("isBlockMode", false);
                    boolean isSilentMode = prefs.getBoolean("isSilentMode", false);

                    if(isBlockMode){
//                        Toast.makeText(context, "Block Mode", Toast.LENGTH_SHORT).show();

                        endCall(context);
                        return;
                    }else if(isSilentMode){
//                        Toast.makeText(context, "Silent Mode", Toast.LENGTH_SHORT).show();

                        int ringerMode = audio.getRingerMode();
                        audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("ringerMode", ringerMode);
                        editor.commit();
                    }else {
                        List<BlockedNumbers> blockedNumbers = db.blockNumbersDao().getAllByNumber(number);
                        if(blockedNumbers.size() > 0){
//                            Toast.makeText(context, "Number Blocked", Toast.LENGTH_SHORT).show();

                            endCall(context);
                            return;
                        }

                        List<SilentNumbers> silentNumbers = db.silentNumbersDao().getAllByNumber(number);
                        if(silentNumbers.size() > 0){
//                            Toast.makeText(context, "Number Silent", Toast.LENGTH_SHORT).show();

                            int ringerMode = audio.getRingerMode();
                            audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt("ringerMode", ringerMode);
                            editor.commit();
                        }
                    }
                }
            }
        }

    }

    @SuppressLint("PrivateApi")
    public static boolean endCall(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            final TelecomManager telecomManager = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
            if (telecomManager != null && ContextCompat.checkSelfPermission(context, Manifest.permission.ANSWER_PHONE_CALLS) == PackageManager.PERMISSION_GRANTED) {
                telecomManager.endCall();
                return true;
            }
            return false;
        }

        //use unofficial API for older Android versions, as written here: https://stackoverflow.com/a/8380418/878126
        try {
            final Class<?> telephonyClass = Class.forName("com.android.internal.telephony.ITelephony");
            final Class<?> telephonyStubClass = telephonyClass.getClasses()[0];
            final Class<?> serviceManagerClass = Class.forName("android.os.ServiceManager");
            final Class<?> serviceManagerNativeClass = Class.forName("android.os.ServiceManagerNative");
            final Method getService = serviceManagerClass.getMethod("getService", String.class);
            final Method tempInterfaceMethod = serviceManagerNativeClass.getMethod("asInterface", IBinder.class);
            final Binder tmpBinder = new Binder();
            tmpBinder.attachInterface(null, "fake");
            final Object serviceManagerObject = tempInterfaceMethod.invoke(null, tmpBinder);
            final IBinder retbinder = (IBinder) getService.invoke(serviceManagerObject, "phone");
            final Method serviceMethod = telephonyStubClass.getMethod("asInterface", IBinder.class);
            final Object telephonyObject = serviceMethod.invoke(null, retbinder);
            final Method telephonyEndCall = telephonyClass.getMethod("endCall");
            telephonyEndCall.invoke(telephonyObject);
            return true;
        } catch (Exception e) {
            Toast.makeText(context, "Error = " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}
