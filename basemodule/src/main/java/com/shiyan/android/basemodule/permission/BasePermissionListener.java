package com.shiyan.android.basemodule.permission;

/**
 * 权限监听基类
 * shiyan
 * update 2019.01.17
 *
 */
public interface BasePermissionListener {

    //授权成功
    void granted();

    //授权失败
    void denied();

    void shouldShowRequestPermissionRationale();
}
