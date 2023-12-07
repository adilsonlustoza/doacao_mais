package br.com.lustoza.doacaomais.Helper;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

/**
 * Created by ubuntu on 4/21/17.
 */

public class SimpleChoiceDialogFragmentHelper extends DialogFragment {

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String title = requireArguments().getString("title");
        String message = getArguments().getString("message");
        String[] itens = getArguments().getStringArray("itens");

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setSingleChoiceItems(itens, -1, (dialogInterface, i) -> {

        });

        return builder.create();
    }

}

