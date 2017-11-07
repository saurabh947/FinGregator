package com.saurabh.fingregator.ui.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

public class CommonAlertDialog {
    public static final String TAG = CommonAlertDialog.class.getSimpleName();

    private AlertDialog mAlertDialog;

    public void show(@NonNull Context context,
            @StringRes int dialogTitle, @NonNull String dialogMessage,
            @StringRes int neutralText, @Nullable final DialogInterface.OnClickListener neutralListener) {

        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }

        mAlertDialog = new AlertDialog.Builder(context).create();
        mAlertDialog.setTitle(dialogTitle);
        mAlertDialog.setMessage(dialogMessage);

        mAlertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,
                context.getString(neutralText),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        if (neutralListener != null) {
                            neutralListener.onClick(dialog, which);
                        }
                    }
                });
        mAlertDialog.show();
    }

    public void show(@NonNull Context context,
            @StringRes int dialogTitle, @NonNull String dialogMessage,
            @StringRes int negativeText, @Nullable final DialogInterface.OnClickListener negativeListener,
            @StringRes int positiveText, @Nullable final DialogInterface.OnClickListener positiveListener) {

        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }

        mAlertDialog = new AlertDialog.Builder(context).create();
        mAlertDialog.setTitle(dialogTitle);
        mAlertDialog.setMessage(dialogMessage);

        mAlertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                context.getString(negativeText),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        if (negativeListener != null) {
                            negativeListener.onClick(dialog, which);
                        }
                    }
                });
        mAlertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                context.getString(positiveText),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        if (positiveListener != null) {
                            positiveListener.onClick(dialog, which);
                        }
                    }
                });
        mAlertDialog.show();
    }
}
