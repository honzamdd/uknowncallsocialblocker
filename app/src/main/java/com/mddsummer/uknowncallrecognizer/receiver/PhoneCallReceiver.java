package com.mddsummer.uknowncallrecognizer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import java.util.Date;

public abstract class PhoneCallReceiver extends BroadcastReceiver {

    //The receiver will be recreated whenever android feels like it.  We need a static variable to remember data between instantiations

    private static int sLastState = TelephonyManager.CALL_STATE_IDLE;
    private static Date sCallStartTime;
    private static boolean sIsIncoming;
    private static String sSavedNumber;  //because the passed incoming is only valid in ringing


    @Override
    public void onReceive(Context context, Intent intent) {

        //We listen to two intents.  The new outgoing call only tells us of an outgoing call.  We use it to get the number.
        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
            sSavedNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
        } else {
            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            int state = 0;
            if (stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                state = TelephonyManager.CALL_STATE_IDLE;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                state = TelephonyManager.CALL_STATE_OFFHOOK;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                state = TelephonyManager.CALL_STATE_RINGING;
            }

            onCallStateChanged(context, state, number);
        }
    }


    //Deals with actual events

    //Incoming call-  goes from IDLE to RINGING when it rings, to OFFHOOK when it's answered, to IDLE when its hung up
    //Outgoing call-  goes from IDLE to OFFHOOK when it dials out, to IDLE when hung up
    public void onCallStateChanged(Context context, int state, String number) {
        if (sLastState == state) {
            //No change, debounce extras
            return;
        }
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                sIsIncoming = true;
                sCallStartTime = new Date();
                sSavedNumber = number;
                onIncomingCallReceived(context, number, sCallStartTime);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                //Transition of ringing->offhook are pickups of incoming calls.  Nothing done on them
                if (sLastState != TelephonyManager.CALL_STATE_RINGING) {
                    sIsIncoming = false;
                    sCallStartTime = new Date();
                    onOutgoingCallStarted(context, sSavedNumber, sCallStartTime);
                } else {
                    sIsIncoming = true;
                    sCallStartTime = new Date();
                    onIncomingCallAnswered(context, sSavedNumber, sCallStartTime);
                }

                break;
            case TelephonyManager.CALL_STATE_IDLE:
                //Went to idle-  this is the end of a call.  What type depends on previous state(s)
                if (sLastState == TelephonyManager.CALL_STATE_RINGING) {
                    //Ring but no pickup-  a miss
                    onMissedCall(context, sSavedNumber, sCallStartTime);
                } else if (sIsIncoming) {
                    onIncomingCallEnded(context, sSavedNumber, sCallStartTime, new Date());
                } else {
                    onOutgoingCallEnded(context, sSavedNumber, sCallStartTime, new Date());
                }
                break;
        }
        sLastState = state;
    }

    //Derived classes should override these to respond to specific events of interest
    protected abstract void onIncomingCallReceived(Context ctx, String number, Date start);

    protected abstract void onIncomingCallAnswered(Context ctx, String number, Date start);

    protected abstract void onIncomingCallEnded(Context ctx, String number, Date start, Date end);

    protected abstract void onOutgoingCallStarted(Context ctx, String number, Date start);

    protected abstract void onOutgoingCallEnded(Context ctx, String number, Date start, Date end);

    protected abstract void onMissedCall(Context ctx, String number, Date start);

}