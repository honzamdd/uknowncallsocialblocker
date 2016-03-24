package com.mddsummer.uknowncallrecognizer.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mddsummer.uknowncallrecognizer.R;
import com.mddsummer.uknowncallrecognizer.database.dao.DaoMsisdnHelper;
import com.mddsummer.uknowncallrecognizer.database.model.Msisdn;
import com.mddsummer.uknowncallrecognizer.service.HomescreenDialogService;

/**
 * This activity creates dialog on home screen.
 *
 * @author {@link "mailto:honzamusil@honzamusil.info" "Honza Musil"} on 24/3/2016
 */
public class DialogActivity extends Activity {

    private static final String TAG = DialogActivity.class.getSimpleName();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);

        final String msisdn = getIntent().getStringExtra(HomescreenDialogService.MSISDN);

        setTitle(getString(R.string.dialog_title) + " " + msisdn);

        // Block number
        TextView blockButton = (TextView) findViewById(R.id.block);
        blockButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Msisdn msisdnObj = new Msisdn(msisdn, true);
                new DaoMsisdnHelper(DialogActivity.this).createMsisdn(msisdnObj);
                finish();
            }
        });

        // Close dialog
        TextView closeButton = (TextView) findViewById(R.id.close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // Close activity once dialog is closed
    protected void onPause() {
        super.onPause();
        finish();
    }
}