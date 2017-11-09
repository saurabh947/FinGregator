package com.saurabh.fingregator.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import com.saurabh.fingregator.R;

public class LoginActivity extends BaseActivity {

    private static final int MAX_PIN_LENGTH = 5;

    private ImageView mImageFingerprint;
    private EditText mTxtLoginPin;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mImageFingerprint = (ImageView)findViewById(R.id.image_fingerprint);
        mImageFingerprint.setImageResource(R.drawable.fingerprint_lock);

        mTxtLoginPin = (EditText)findViewById(R.id.text_login_pin);
        mTxtLoginPin.addTextChangedListener(new PinTextWatcher());

        syncUi();
    }

    private void syncUi() {

    }

    private class PinTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String pin = s.toString();

            if (pin.length() >= MAX_PIN_LENGTH) {
                hideSoftKeyboard();
                showProgressDialog(R.string.common_loading);
                mTxtLoginPin.setText(null);

                // TODO: Shared Prefs? Customizable PIN?
                if (pin.equals("11111")) {
                    dismissProgressDialog();
                    // TODO: Move to next screen
                    startActivity(new Intent(LoginActivity.this, LinkAccountsActivity.class));
                } else {
                    dismissProgressDialog();
                    showErrorDialog(getString(R.string.error_incorrect_pin));
                }
            }
        }
    }
}
