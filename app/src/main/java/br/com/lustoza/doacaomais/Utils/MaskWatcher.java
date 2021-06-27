package br.com.lustoza.doacaomais.Utils;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Adilson on 31/03/2018.
 */

public class MaskWatcher implements TextWatcher {

    private final String mask;
    private boolean isRunning = false;
    private boolean isDeleting = false;

    public MaskWatcher(String mask) {
        this.mask = mask;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        isDeleting = count > after;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        if (isRunning || isDeleting) {
            return;
        }
        isRunning = true;

        int editableLength = editable.length();

        if (editableLength < mask.length() && editableLength > 0) {
            if (mask.charAt(editableLength) != '#') {
                editable.append(mask.charAt(editableLength));
            } else if (mask.charAt(editableLength - 1) != '#') {
                editable.insert(editableLength - 1, mask, editableLength - 1, editableLength);
            }
        }

        isRunning = false;

    }

}
