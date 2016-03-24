package com.mddsummer.uknowncallrecognizer.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mddsummer.uknowncallrecognizer.R;
import com.mddsummer.uknowncallrecognizer.database.dao.DaoMsisdnHelper;
import com.mddsummer.uknowncallrecognizer.database.model.Msisdn;
import com.mddsummer.uknowncallrecognizer.service.HomescreenDialogService;

public class DialogActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);

        final String msisdn = getIntent().getStringExtra(HomescreenDialogService.MSISDN);

        //TODO move title value to string.xml
        setTitle("What to do with this msisdn: " + msisdn);

        // Block number
        TextView blockButton = (TextView) findViewById(R.id.block);
        blockButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Msisdn msisdnObj = new Msisdn(msisdn, true);
                new DaoMsisdnHelper(DialogActivity.this).createMsisdn(msisdnObj);
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

        // Close activity once dialog is closed
        Dialog dialog = (Dialog) closeButton.getParent();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                finish();
            }
        });
    }
}