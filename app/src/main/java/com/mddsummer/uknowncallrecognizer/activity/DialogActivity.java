package com.mddsummer.uknowncallrecognizer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.mddsummer.uknowncallrecognizer.R;

public class DialogActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog);

        Button closeButton = (Button) findViewById(R.id.close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//
//        Dialog dialog = new Dialog(this);
//
//        dialog.setTitle("What to do with this number");
//        dialog.setContentView(R.layout.dialog);
//        dialog.show();

    }
}