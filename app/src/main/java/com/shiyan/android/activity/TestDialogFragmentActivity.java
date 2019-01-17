package com.shiyan.android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.shiyan.android.R;
import com.shiyan.android.view.BottomDialgFragment;

public class TestDialogFragmentActivity extends AppCompatActivity {

    private Button btn_show;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dialog_fragment);

        btn_show = findViewById(R.id.btn_show);

        btn_show.setOnClickListener(v -> {

            BottomDialgFragment dialgFragment = new BottomDialgFragment();

            dialgFragment.setGravity(Gravity.CENTER);

            dialgFragment.setOutCancel(true);

            dialgFragment.setShowBottom(false);

            dialgFragment.show(getSupportFragmentManager());

        });

    }
}
