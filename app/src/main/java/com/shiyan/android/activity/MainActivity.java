package com.shiyan.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.shiyan.android.R;
import com.shiyan.android.ndk.NdkUtil;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, RxPermissionActivity.class));
    }

    public void test2() {

        new NdkUtil().string_java("我是java");
    }

    public void test3() {

        int[] array = new int[10];

        for (int i = 0; i < 10; i++) {
            array[i] = i;
        }

        new NdkUtil().array_java(array);
    }

    public void test4() {

        int[] array = new int[10];

        for (int i = 0; i < 10; i++) {
            array[i] = i;
        }

        new NdkUtil().array2_java(array);
    }
}
