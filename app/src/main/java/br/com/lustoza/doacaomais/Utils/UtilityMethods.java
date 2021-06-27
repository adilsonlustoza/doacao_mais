package br.com.lustoza.doacaomais.Utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import br.com.lustoza.doacaomais.Domain.ObjectValue.TipoCampanha;
import br.com.lustoza.doacaomais.Helper.TrackHelper;

/**
 * Created by Adilson on 30/12/2017.
 */

public class UtilityMethods extends Application {

    private static String tipoCampanha;
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static void SetGlobalContext(Context context) {
        if (UtilityMethods.context == null)
            UtilityMethods.context = context;
    }

    public static Context GetGlobalContext() {
        return context;
    }

    public static String ParseDateToString(Date date) {

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = null;

        try {
            dateString = formatter.format(date);
        } catch (Exception e) {
            TrackHelper.WriteError(UtilityMethods.class, "ParseDateToString ", e.getMessage());
        }
        return dateString;
    }

    public static String getTipoCampanha(int tipoCampanha) {

        switch (tipoCampanha) {

            case 1:
                UtilityMethods.tipoCampanha = TipoCampanha.Campanha.name();
                break;
            case 2:
                UtilityMethods.tipoCampanha = TipoCampanha.Evento.name();
                break;
            case 3:
                UtilityMethods.tipoCampanha = TipoCampanha.Projeto.name();
                break;
            case 4:
                UtilityMethods.tipoCampanha = TipoCampanha.Noticia.name();
                break;
            case 5:
                UtilityMethods.tipoCampanha = TipoCampanha.Depoimento.name();
                break;
            case 6:
                UtilityMethods.tipoCampanha = TipoCampanha.Parceiro.name();
                break;
            case 7:
                UtilityMethods.tipoCampanha = TipoCampanha.Voluntario.name();
                break;
            default:
                UtilityMethods.tipoCampanha = "Desconhecida";
                break;
        }
        return UtilityMethods.tipoCampanha;

    }

    public static String RemoveAccent(String str) {
        try {
            if (str != null && !TextUtils.isEmpty(str))
                return Normalizer.normalize(str, Normalizer.Form.NFD)
                        .replaceAll("[^\\p{ASCII}]", "")
                        .replaceAll(" ", "");
        } catch (Exception e) {
            TrackHelper.WriteError(UtilityMethods.class, "RemoveCaracteresEspeciais ", e.getMessage());
        }
        return str;
    }

    public static long DiferenceMinutes(long dataStart, long dataEnd) {
        try {
            long diff = dataStart - dataEnd;
            if (diff > 0)
                return diff / (60 * 1000) % 60;

        } catch (Exception e) {
            TrackHelper.WriteError(UtilityMethods.class, "DiferenceMinutes", e.getMessage());
        }

        return 0;
    }

    public static String GetProperties(String key) {
        try {
            context = GetGlobalContext();
            if (context != null) {
                Properties properties = new Properties();
                AssetManager assetManager = GetGlobalContext().getAssets();
                InputStream inputStream = assetManager.open("config.properties");
                properties.load(inputStream);
                return properties.getProperty(key);
            }

        } catch (IOException e) {
            TrackHelper.WriteError(UtilityMethods.class, "Io GetProperties ", e.getMessage());
        } catch (Exception e) {
            TrackHelper.WriteError(UtilityMethods.class, "Ex GetProperties ", e.getMessage());
        }
        return null;

    }

}






