package com.example.airtime;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class OtpTextWatcher implements TextWatcher {
    private EditText currentEditText;
    private EditText nextEditText;

    public OtpTextWatcher(EditText currentEditText, EditText nextEditText) {
        this.currentEditText = currentEditText;
        this.nextEditText = nextEditText;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (count == 1 && nextEditText != null) {
            nextEditText.requestFocus();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Not needed
    }

    @Override
    public void afterTextChanged(Editable s) {
        // Not needed
    }
}
