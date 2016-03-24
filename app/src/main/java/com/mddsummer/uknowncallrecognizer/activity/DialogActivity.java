package com.mddsummer.uknowncallrecognizer.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mddsummer.uknowncallrecognizer.R;
import com.mddsummer.uknowncallrecognizer.service.HomescreenDialogService;

public class DialogActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String msisdn = getIntent().getStringExtra(HomescreenDialogService.MSISDN);


        setContentView(R.layout.dialog);
        setTitle("What to do with this msisdn: " + msisdn);

        Button closeButton = (Button) findViewById(R.id.close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Dialog dialog = (Dialog) closeButton.getParent();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                finish();
            }
        });
    }
}