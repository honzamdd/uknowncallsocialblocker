package com.mddsummer.uknowncallrecognizer.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.mddsummer.uknowncallrecognizer.activity.DialogActivity;

public class HomePopupDataService extends Service {

    private static final String TAG = HomePopupDataService.class.getSimpleName();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG, "Start service");

        Intent dialogIntent = new Intent(getApplicationContext(),
                DialogActivity.class);

        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(dialogIntent);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        Log.v(TAG, "OnBind");
        return null;
    }
}