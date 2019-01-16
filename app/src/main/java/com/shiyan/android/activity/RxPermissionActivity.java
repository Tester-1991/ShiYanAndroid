package com.shiyan.android.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.shiyan.android.R;
import com.shiyan.android.basemodule.permission.PermissionListener;
import com.shiyan.android.basemodule.permission.Permissions;

public class RxPermissionActivity extends AppCompatActivity {

    private Button btn_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_permission);


        btn_click = findViewById(R.id.btn_click);

        btn_click.setOnClickListener(v -> {

            new Permissions(RxPermissionActivity.this).request(new PermissionListener() {
                @Override
                public void granted() {

                }
            },Manifest.permission.WRITE_EXTERNAL_STORAGE);
        });
    }
}
