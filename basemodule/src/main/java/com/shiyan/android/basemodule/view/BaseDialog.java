package com.shiyan.android.basemodule.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.shiyan.android.basemodule.R;

public abstract class BaseDialog extends DialogFragment {

    private Dialog dialog;

    protected View rootView;

    private boolean cancle = false;

    private int theme = R.style.BaseDialogFragment;

    private float dimAmount = 0.3f;

    private int gravity = Gravity.CENTER;

    //是否从底部弹出
    private boolean showBottom = false;

    //动画
    private int animStyle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, theme);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        rootView = LayoutInflater.from(getActivity()).inflate(getLayoutId(), null);

        dialog.setContentView(rootView);

        dialog.setCanceledOnTouchOutside(cancle);

        Window window = dialog.getWindow();

        //设置背景颜色透明
        window.setBackgroundDrawableResource(android.R.color.transparent);

        WindowManager.LayoutParams layoutParams = window.getAttributes();

        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;

        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        layoutParams.dimAmount = dimAmount;

        layoutParams.gravity = gravity;

        //是否在底部显示
        if (showBottom) {

            layoutParams.gravity = Gravity.BOTTOM;

            if (animStyle == 0) {

                animStyle = R.style.BaseDialogFragmentBottomAnimation;

            }
        }

        //设置dialog进入、退出的动画
        window.setWindowAnimations(animStyle);

        window.setAttributes(layoutParams);

        initView();

        return dialog;
    }

    public void setOutCancel(boolean outCancel) {

        this.cancle = outCancel;
    }

    public void setDimAmount(float dimAmount) {

        this.dimAmount = dimAmount;
    }

    public void setGravity(int gravity) {

        this.gravity = gravity;
    }

    public void setShowBottom(boolean showBottom) {

        this.showBottom = showBottom;
    }

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 获取布局文件
     */
    protected abstract int getLayoutId();

    /**
     * 显示
     * @param manager
     */
    public BaseDialog show(FragmentManager manager) {

        FragmentTransaction ft = manager.beginTransaction();

        if (this.isAdded()) {

            ft.remove(this).commit();

        }

        ft.add(this, String.valueOf(System.currentTimeMillis()));

        ft.commitAllowingStateLoss();

        return this;
    }
}
