package com.saurabh.fingregator.ui.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.saurabh.fingregator.R;
import com.saurabh.fingregator.ui.dialogs.CommonAlertDialog;
import com.saurabh.fingregator.ui.dialogs.CommonProgressDialog;

public class BaseActivity extends AppCompatActivity {

    private CommonAlertDialog mCommonAlertDialog;
    private CommonProgressDialog mCommonProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCommonAlertDialog = new CommonAlertDialog();
        mCommonProgressDialog = new CommonProgressDialog();
    }

    //region Progress Dialog

    public void showProgressDialog(@StringRes int message) {
        if (mCommonProgressDialog == null) {
            return;
        }

        Log.i(getClass().getSimpleName(), "showProgressDialog() :: Showing progress dialog");
        mCommonProgressDialog.showProgressDialog(this, message);
    }

    public void dismissProgressDialog() {
        if (mCommonProgressDialog == null) {
            return;
        }

        Log.i(getClass().getSimpleName(), "dismissProgressDialog() :: Dismissing progress dialog");
        mCommonProgressDialog.dismissProgressDialog();
    }

    //endregion

    //region Alert Dialog

    public void showAlertDialog(@StringRes int dialogTitle, @NonNull String dialogMessage,
            @StringRes int neutralText, @Nullable final DialogInterface.OnClickListener neutralListener) {
        if (mCommonAlertDialog == null) {
            return;
        }
        mCommonAlertDialog.show(this, dialogTitle, dialogMessage, neutralText, neutralListener);
    }

    protected void showAlertDialog(@StringRes int dialogTitle, @NonNull String dialogMessage,
            @StringRes int negativeText, @Nullable final DialogInterface.OnClickListener negativeListener,
            @StringRes int positiveText, @Nullable final DialogInterface.OnClickListener positiveListener) {
        if (mCommonAlertDialog == null) {
            return;
        }
        mCommonAlertDialog.show(this, dialogTitle, dialogMessage,
                negativeText, negativeListener, positiveText, positiveListener);
    }

    protected void showErrorDialog(@NonNull String message) {
        if (mCommonAlertDialog == null) {
            return;
        }
        mCommonAlertDialog.show(this, R.string.label_error, message, android.R.string.ok, null);
    }

    //endregion
}
