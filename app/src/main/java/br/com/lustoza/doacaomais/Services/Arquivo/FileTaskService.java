package br.com.lustoza.doacaomais.Services.Arquivo;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.Date;
import java.util.Objects;

import br.com.lustoza.doacaomais.Helper.ConstantHelper;
import br.com.lustoza.doacaomais.Helper.HttpHelper;
import br.com.lustoza.doacaomais.Helper.PrefHelper;
import br.com.lustoza.doacaomais.Utils.HandleFile;
import br.com.lustoza.doacaomais.Utils.UtilityMethods;

/**
 * Created by Adilson on 17/02/2018.
 */
public class FileTaskService extends Thread {

    private final Context context;
    private final String url;
    private final String file;

    public FileTaskService(Context context, String file, String url) {
        this.context = context;
        this.url = url;
        this.file = file;
    }

    @Override
    public void run() {

        try {

            String intervaloString = PrefHelper.getString(context, ConstantHelper.pref_atualizar);

            long intervalo;
            if (TextUtils.isEmpty(intervaloString))
                intervalo = 15;
            else
                intervalo = Integer.getInteger(intervaloString, 0);

            HandleFile handleFile = new HandleFile(context, file);
            String fileJson = handleFile.ReadFile();

            long dataLong = new Date().getTime();
            long dataFile = handleFile.LastChangeFile();

            if (!TextUtils.isEmpty(fileJson) || fileJson == null) {

                long difMinutes = UtilityMethods.DiferenceMinutes(dataLong, dataFile);

                if (difMinutes > intervalo) {
                    handleFile.ApagarArquivo();

                    String jsonString = HttpHelper.makeOkHttpCall(url);

                    if (jsonString != null && jsonString.length() > 0)
                        handleFile.WriteFile(jsonString);
                }
            }

        } catch (Exception e) {
            Log.d("", Objects.requireNonNull(e.getLocalizedMessage()));
        }

    }

}
