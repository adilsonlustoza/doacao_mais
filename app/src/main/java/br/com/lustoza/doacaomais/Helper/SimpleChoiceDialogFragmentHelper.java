package br.com.lustoza.doacaomais.Helper;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Created by ubuntu on 4/21/17.
 */

public class SimpleChoiceDialogFragmentHelper extends DialogFragment {

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String title = Objects.requireNonNull(getArguments()).getString("title");
        String message = getArguments().getString("message");
        String[] itens = getArguments().getStringArray("itens");

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setSingleChoiceItems(itens, -1, (dialogInterface, i) -> {

        });

        return builder.create();
    }

}

