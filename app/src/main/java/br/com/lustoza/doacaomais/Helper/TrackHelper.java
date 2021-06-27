package br.com.lustoza.doacaomais.Helper;

import android.util.Log;

/**
 * Created by ubuntu on 12/1/16.
 */
public class TrackHelper {

    public static void WriteError(Object object, String method, String error) {
        String errorTrack = "Ocorreu um erro no objeto %s no metodo %s  - Mensagem do Compilador : %s";
        Log.e("Error App Solidario", String.format(errorTrack, object.toString(), method, error));
    }

    public static void WriteInfo(Object object, String method, String info) {
        String infoTrack = "Informativo objeto %s no metodo %s  - Mensagem do Programador : %s";
        Log.i("Info App Solidario : ", String.format(infoTrack, object.toString(), method, info));
    }
}
