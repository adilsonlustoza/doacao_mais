package br.com.lustoza.doacaomais.Services.Arquivo;

import android.content.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.lustoza.doacaomais.Helper.ConstantHelper;
import br.com.lustoza.doacaomais.Helper.TrackHelper;

/**
 * Created by Adilson on 30/04/2018.
 */

public class StoredHandleFileTask extends Thread {

    private final Context context;

    public StoredHandleFileTask(Context context) {
        this.context = context;
    }

    @Override
    public void run() {

        try {

            TrackHelper.WriteInfo(this, " StoredHandleFileTask em : ", new Date().toString());

            Map<String, String> dictionary = new HashMap<>();
            dictionary.put(ConstantHelper.fileListAllCaccc, ConstantHelper.urlWebApiListAllCaccc);
            dictionary.put(ConstantHelper.fileListAllCacccBazar, ConstantHelper.urlWebApiListAllCacccBazar);
            dictionary.put(ConstantHelper.fileListAllBazar, ConstantHelper.urlWebApiListAllBazar);
            dictionary.put(ConstantHelper.fileListAllNoticia, ConstantHelper.urlWebApiListAllNoticia);
            dictionary.put(ConstantHelper.fileListAllCampanhas, ConstantHelper.urlWebApiListAllCampanhas);
            dictionary.put(ConstantHelper.fileListarConteudoContasPorCaccc, ConstantHelper.urlWebApiListarConteudoContasPorCaccc);
            dictionary.put(ConstantHelper.fileListarEstatisticoPorTipo, ConstantHelper.urlWebApiListarEstatisticoPorTipo);

            for (String key : dictionary.keySet()) {
                String url = dictionary.get(key);
                FileTaskService fileTaskService = new FileTaskService(context, key, url);
                fileTaskService.start();
            }

        } catch (Exception e) {
            TrackHelper.WriteError(this, "StoredHandleFileTask.Run", e.getMessage());
        }

    }

}
