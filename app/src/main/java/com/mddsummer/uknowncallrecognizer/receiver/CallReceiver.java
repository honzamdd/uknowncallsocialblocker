package com.mddsummer.uknowncallrecognizer.receiver;

import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.Date;
import com.android.internal.telephony.ITelephony;
import com.mddsummer.uknowncallrecognizer.activity.MainActivity;
import com.mddsummer.uknowncallrecognizer.service.HomePopupDataService;

public class CallReceiver extends PhoneCallReceiver {

    private static final String TAG = CallReceiver.class.getSimpleName();

    @Override
    protected void onIncomingCallReceived(Context context, String number, Date start) {

        Log.v(TAG, "onIncomingCallReceived: " + number);

        if(!MainActivity.contactExists(context, number)) {

            try {

                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

                Class clazz = Class.forName(telephonyManager.getClass().getName());

                Method method = clazz.getDeclaredMethod("getITelephony");
                method.setAccessible(true);

                ITelephony telephonyService = (ITelephony) method.invoke(telephonyManager);
                telephonyService.silenceRinger();
                telephonyService.endCall();

                Log.v(TAG, "Called hidden from number: " + number);

            } catch (Exception e) {
                e.printStackTrace();
                // Some problem occurred while accessing private API
                // TODO: do whatever error handling you want here

            }
        }
    }

    @Override
    protected void onIncomingCallAnswered(Context ctx, String number, Date start) {
        Log.v(TAG, "onIncomingCallAnswered: " + number);
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        Log.v(TAG, "onIncomingCallEnded: " + number);

        //TODO open dialog "create new contact", "block"
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
        Log.v(TAG, "onOutgoingCallStarted: " + number);
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        Log.v(TAG, "onOutgoingCallEnded: " + number);
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {
        Log.v(TAG, "onMissedCall: " + number);

        ctx.startService(new Intent(ctx, HomePopupDataService.class));
    }
}

//Missed call
//03-23 19:30:24.070 20078-20078/com.mddsummer.uknowncallrecognizer I/art: Can not find class: Lcom/mddsummer/uknowncallrecognizer/CallReceiver;
//        03-23 19:30:24.080 20078-20078/com.mddsummer.uknowncallrecognizer V/CallReceiver: onIncomingCallReceived: +420732194453
//        03-23 19:30:24.900 20078-20078/com.mddsummer.uknowncallrecognizer I/art: Can not find class: Lcom/mddsummer/uknowncallrecognizer/CallReceiver;
//        03-23 19:30:32.790 20078-20078/com.mddsummer.uknowncallrecognizer I/art: Can not find class: Lcom/mddsummer/uknowncallrecognizer/CallReceiver;
//        03-23 19:30:32.790 20078-20078/com.mddsummer.uknowncallrecognizer V/CallReceiver: onMissedCall: +420732194453
//        03-23 19:30:32.810 20078-20078/com.mddsummer.uknowncallrecognizer I/art: Can not find class: Lcom/mddsummer/uknowncallrecognizer/CallReceiver;
//        03-23 19:30:43.440 20078-20078/com.mddsummer.uknowncallrecognizer I/art: Can not find class: Lcom/mddsummer/uknowncallrecognizer/CallReceiver;


//Answered call
//        03-23 19:30:43.440 20078-20078/com.mddsummer.uknowncallrecognizer V/CallReceiver: onIncomingCallReceived: +420732194453
//        03-23 19:30:44.230 20078-20078/com.mddsummer.uknowncallrecognizer I/art: Can not find class: Lcom/mddsummer/uknowncallrecognizer/CallReceiver;
//        03-23 19:30:52.000 20078-20078/com.mddsummer.uknowncallrecognizer I/art: Can not find class: Lcom/mddsummer/uknowncallrecognizer/CallReceiver;
//        03-23 19:30:52.000 20078-20078/com.mddsummer.uknowncallrecognizer V/CallReceiver: onIncomingCallAnswered: +420732194453
//        03-23 19:30:52.230 20078-20078/com.mddsummer.uknowncallrecognizer I/art: Can not find class: Lcom/mddsummer/uknowncallrecognizer/CallReceiver;
//
//        [ 03-23 19:30:52.230  3909: 3909 D/         ]
//static CNfcConfig& CNfcConfig::GetInstance() NFC real_config_name[21]: libnfc-nxp-alice.conf
//        03-23 19:30:55.310 20078-20078/com.mddsummer.uknowncallrecognizer I/art: Can not find class: Lcom/mddsummer/uknowncallrecognizer/CallReceiver;
//        03-23 19:30:55.310 20078-20078/com.mddsummer.uknowncallrecognizer V/CallReceiver: onIncomingCallEnded: +420732194453
//        03-23 19:30:55.330 20078-20078/com.mddsummer.uknowncallrecognizer I/art: Can not find class: Lcom/mddsummer/uknowncallrecognizer/CallReceiver;