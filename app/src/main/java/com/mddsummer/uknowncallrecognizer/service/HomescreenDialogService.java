package com.mddsummer.uknowncallrecognizer.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.mddsummer.uknowncallrecognizer.activity.DialogActivity;

/**
 * Service to start activity that displays dialog in homescreen
 */
public class HomescreenDialogService extends Service {

    private static final String TAG = HomescreenDialogService.class.getSimpleName();
    public static final String MSISDN = "msisdn";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent dialogIntent = new Intent(getApplicationContext(), DialogActivity.class);
        dialogIntent.putExtra(MSISDN, intent.getStringExtra(MSISDN));

        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(dialogIntent);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}