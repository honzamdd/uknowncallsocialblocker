package com.mddsummer.uknowncallrecognizer.receiver;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;
import com.mddsummer.uknowncallrecognizer.service.HomescreenDialogService;

import java.lang.reflect.Method;
import java.util.Date;

public class CallReceiver extends PhoneCallReceiver {

    private static final String TAG = CallReceiver.class.getSimpleName();

    String sMsisdnToDecide = null;

    @Override
    protected void onIncomingCallReceived(Context context, String msisdn, Date start) {

        // Reset
        sMsisdnToDecide = null;

        Log.v(TAG, "onIncomingCallReceived: " + msisdn);

        /*
         * Check if incoming msisdn exists in a users' contacts.
         * If yes continue the incoming call process, if no check if the msisdn is flagged as blocked in db.
         * If msisdn is blocked, end the call otherwise continue call process.
         */
        if (!contactExists(context, msisdn)) {

            // Set msisdn if the homescreen dialog must be shown after the call
            sMsisdnToDecide = msisdn;

            // End the call
            try {

                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

                Class clazz = Class.forName(telephonyManager.getClass().getName());

                Method method = clazz.getDeclaredMethod("getITelephony");
                method.setAccessible(true);

                ITelephony telephonyService = (ITelephony) method.invoke(telephonyManager);
                telephonyService.silenceRinger();
                telephonyService.endCall();

                Log.v(TAG, "Called hidden from msisdn: " + msisdn);

            } catch (Exception e) {
                e.printStackTrace();
                // Some problem occurred while accessing private API
                // TODO: do whatever error handling you want here

            }
        }
    }

    @Override
    protected void onIncomingCallAnswered(Context context, String msisdn, Date start) {
        Log.v(TAG, "onIncomingCallAnswered: " + msisdn);
    }

    @Override
    protected void onIncomingCallEnded(Context context, String msisdn, Date start, Date end) {
        Log.v(TAG, "onIncomingCallEnded: " + msisdn);

        if (sMsisdnToDecide != null) {
            Intent dialogIntent = new Intent(context, HomescreenDialogService.class);
            dialogIntent.putExtra(HomescreenDialogService.MSISDN, msisdn);
            context.startService(dialogIntent);
        }
    }

    @Override
    protected void onOutgoingCallStarted(Context context, String msisdn, Date start) {
        Log.v(TAG, "onOutgoingCallStarted: " + msisdn);
    }

    @Override
    protected void onOutgoingCallEnded(Context context, String msisdn, Date start, Date end) {
        Log.v(TAG, "onOutgoingCallEnded: " + msisdn);
    }

    @Override
    protected void onMissedCall(Context context, String msisdn, Date start) {
        Log.v(TAG, "onMissedCall: " + msisdn);

    }

    /**
     * Check if msisdn exists in
     *
     * @param context
     * @param msisdn
     * @return
     */
    private static boolean contactExists(Context context, String msisdn) {

        Uri lookupUri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(msisdn));

        String[] mPhoneNumberProjection = {ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME};
        Cursor cur = context.getContentResolver().query(lookupUri, mPhoneNumberProjection, null, null, null);
        try {
            if (cur.moveToFirst()) {
                return true;
            }
        } finally {
            if (cur != null)
                cur.close();
        }
        return false;
    }
}