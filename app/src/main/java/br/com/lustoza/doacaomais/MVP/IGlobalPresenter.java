package br.com.lustoza.doacaomais.MVP;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Adilson on 07/07/2018.
 */

public interface IGlobalPresenter {
    Context getContext();

    Activity getActivity();
}
