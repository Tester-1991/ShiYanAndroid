package com.shiyan.android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.shiyan.android.R;
import com.shiyan.android.basemodule.util.RxBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxBusActivity extends AppCompatActivity {

    private Button btn_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bus);

        btn_send = findViewById(R.id.btn_send);

        btn_send.setOnClickListener(v -> {

            RxBus.getInstance().post("123456");
        });


        RxBus.getInstance().register(String.class, s -> {
                Log.e("RxBus",s);
        });
    }
}
