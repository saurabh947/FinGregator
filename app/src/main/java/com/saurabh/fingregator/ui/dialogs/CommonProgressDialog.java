package com.saurabh.fingregator.ui.dialogs;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

public class CommonProgressDialog {
    public static final String TAG = CommonProgressDialog.class.getSimpleName();

    private ProgressDialog mProgressDialog;

    public void showProgressDialog(@NonNull Context context, @StringRes int messageRes) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(context.getString(messageRes));

        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setIndeterminate(true);

        mProgressDialog.show();
    }

    public void dismissProgressDialog() {
        if (mProgressDialog == null || !mProgressDialog.isShowing()) {
            return;
        }
        mProgressDialog.dismiss();
    }
}
