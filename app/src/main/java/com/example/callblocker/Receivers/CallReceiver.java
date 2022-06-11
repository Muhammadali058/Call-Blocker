package com.example.callblocker.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Called", Toast.LENGTH_SHORT).show();
        if (intent.hasExtra(Intent.EXTRA_PROCESS_TEXT)) {
            String text = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString();
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }
    }
}
