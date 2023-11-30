package br.com.lustoza.doacaomais.Helper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import br.com.lustoza.doacaomais.Interfaces.OnCustomDialogClickListener;
import br.com.lustoza.doacaomais.Utils.EnumCommand;

/**
 * Created by ubuntu on 4/21/17.
 */

public class SimpleDialogFragmentHelper extends DialogFragment {

    private static OnCustomDialogClickListener onCustomDialogClickListener;

    private EnumCommand enumCommand;
    private boolean exitApp;

    public SimpleDialogFragmentHelper() {
        // Empty constructor required for DialogFragment
    }

    public static SimpleDialogFragmentHelper newInstance(String titulo, String message, OnCustomDialogClickListener onCustomDialogClickListener) {

        SimpleDialogFragmentHelper simpleDialogFragmentHelper = new SimpleDialogFragmentHelper();
        Bundle bundle = new Bundle();

        bundle.putString("title", titulo);
        bundle.putString("message", message);

        simpleDialogFragmentHelper.setArguments(bundle);
        SimpleDialogFragmentHelper.onCustomDialogClickListener = onCustomDialogClickListener;
        return simpleDialogFragmentHelper;

        // Empty constructor required for DialogFragment
    }

    public void setCommand(EnumCommand enumCommand, boolean exitApp) {
        this.enumCommand = enumCommand;
        this.exitApp = exitApp;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String title = requireArguments().getString("title");
        String message = getArguments().getString("message");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("Não", (dialog, which) -> {
            if (dialog != null) {
                if (exitApp)
                    onCustomDialogClickListener.onItemClick(EnumCommand.ExitApp);
                else
                    onCustomDialogClickListener.onItemClick(EnumCommand.CloseWindow);
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    onCustomDialogClickListener.onItemClick(enumCommand);
                } catch (Exception e) {
                    TrackHelper.WriteError(this, "onCreateDialog", e.getMessage());
                }
            }
        });

        return builder.create();

    }

}

