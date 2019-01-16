package com.shiyan.android.basemodule.permission;

public interface BasePermissionListener {

    //授权成功
    void granted();

    //授权失败
    void denied();

    void shouldShowRequestPermissionRationale();
}
