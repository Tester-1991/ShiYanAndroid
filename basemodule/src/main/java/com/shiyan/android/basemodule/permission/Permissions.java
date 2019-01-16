package com.shiyan.android.basemodule.permission;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * 权限管理类
 */
public class Permissions {

    private FragmentActivity context;

    public Permissions(FragmentActivity context) {

        this.context = context;

    }


    /**
     * 请求权限
     * @param listener
     * @param manifest
     */
    @SuppressLint("CheckResult")
    public void request(PermissionListener listener,String... manifest) {

        if(listener == null) return;

        new RxPermissions(context).requestEachCombined(manifest)
                .subscribe(permission -> {

                    if (permission.granted) {
                        // All permissions are granted
                        listener.granted();

                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // At least one denied permission without ask never again
                        listener.shouldShowRequestPermissionRationale();
                    } else {
                        // At least one denied permission with ask never again
                        // Need to go to the settings
                        listener.denied();
                    }
                });
    }
}
