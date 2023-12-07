package br.com.lustoza.doacaomais.Services.Job;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import br.com.lustoza.doacaomais.Domain.Dispositivo;
import br.com.lustoza.doacaomais.Domain.Notificacao;
import br.com.lustoza.doacaomais.Helper.ConstantHelper;
import br.com.lustoza.doacaomais.Helper.DeviceHelper;
import br.com.lustoza.doacaomais.Helper.HttpHelper;
import br.com.lustoza.doacaomais.Helper.NotificationHelper;
import br.com.lustoza.doacaomais.Helper.PrefHelper;
import br.com.lustoza.doacaomais.Helper.TipoRequisicaoHttp;
import br.com.lustoza.doacaomais.Helper.TrackHelper;
import br.com.lustoza.doacaomais.Utils.HandleFile;
import br.com.lustoza.doacaomais.Utils.UtilityJson;
import okhttp3.Headers;

/**
 * Created by Adilson on 08/01/2018.
 */

public class NotificationService extends Service {

    private String jsonNotificacaoWebApi;
    private String jsonNotificacaoStorage;
    private List<Notificacao> lstNotificacaoApi;
    private List<Notificacao> lstNotificacaoStorage;
    private boolean receberNotificacao;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TrackHelper.WriteInfo(this, "onStartCommand", String.format(" NotificationService - %s", new Date().toString()));
        return this.Execute();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private int Execute() {

        try {

            HandleFile handleFile = new HandleFile(getApplicationContext(), ConstantHelper.fileNotiticacaoRecebida);
            //Limpa todas as notificacoes e somente manterá a que ainda estão ativas
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    TrackHelper.WriteInfo(this, "Execute", String.format(" NotificationService - %s", new Date().toString()));

                    receberNotificacao = PrefHelper.getBoolean(getBaseContext(), PrefHelper.PreferenciaRecebeNotificacao);

                    if (!receberNotificacao)
                        return;

                    jsonNotificacaoWebApi = HttpHelper.makeOkHttpCall(ConstantHelper.urlWebApiListAllNotificacoes);

                    TrackHelper.WriteInfo(this, "Verificando novas notificacoes as : ", new Date().toString());

                    if (!TextUtils.isEmpty(jsonNotificacaoWebApi)) {
                        try {

                            jsonNotificacaoStorage = handleFile.ReadFile();

                            lstNotificacaoApi = new UtilityJson<Notificacao>().ParseJsonArrayToList(jsonNotificacaoWebApi, Notificacao.class);

                            if (!TextUtils.isEmpty(jsonNotificacaoStorage))
                                lstNotificacaoStorage = new UtilityJson<Notificacao>().ParseJsonArrayToList(jsonNotificacaoStorage, Notificacao.class);

                            if (lstNotificacaoApi != null && lstNotificacaoApi.size() > 0) {

                                int i = 0;

                                while (i < lstNotificacaoApi.size()) {

                                    Notificacao notificacao = lstNotificacaoApi.get(i);

                                    if (lstNotificacaoStorage != null && lstNotificacaoStorage.size() > 0) {
                                        for (Notificacao notificacaoStore : lstNotificacaoStorage)
                                            if (notificacaoStore.getNotificacaoId() == notificacao.getNotificacaoId()) {
                                                lstNotificacaoStorage.remove(notificacaoStore);
                                                break;
                                            }
                                    } else
                                        HandleSendDeviceMessage(i, notificacao);

                                    i++;
                                }

                            }

                            handleFile.WriteFile(jsonNotificacaoWebApi);

                        } catch (Exception e) {
                            TrackHelper.WriteError(this, "parseResult", e.getMessage());
                        }

                    }

                }

            });

            thread.start();

        } catch (Exception e) {
            TrackHelper.WriteError(this, "Error onStartCommand", e.getMessage());
        }

        return START_NOT_STICKY;
    }

    private boolean CheckDeviceNotifications(Notificacao notificacao) {

        try {

            if (notificacao != null) {

                Dispositivo dispositivo = DeviceHelper.GetDevice();
                String url = String.format("%s%s", ConstantHelper.urlWebApiNotificacaoPorDeviceId, dispositivo.getDeviceId());
                String jsonDispositivo = HttpHelper.makeOkHttpCall(url);

                if (!TextUtils.isEmpty(jsonDispositivo)) {

                    Dispositivo dispositivoEncontrado = new UtilityJson<Dispositivo>().ParseJsonToObj(jsonDispositivo, Dispositivo.class);

                    if (dispositivoEncontrado != null)
                        notificacao.getDispositivos().add(dispositivoEncontrado);

                }

                for (Dispositivo objDispositivo : notificacao.getDispositivos())
                    if (objDispositivo.getDeviceId().contains(dispositivo.getDeviceId()))
                        return true;
            }

        } catch (Exception ex) {
            TrackHelper.WriteError(this, "JsonParseNotification", ex.getMessage());
        }
        return false;
    }

    private void HttpSendDeviceData(Dispositivo dispositivo) {

        try {
            Map<String, String> map = new Hashtable<>();
            map.put("Email", "adilsonlustoza@gmail.com");
            map.put("Password", "AppPrivy$2021");
            Headers headers = Headers.of(map);

            String token = HttpHelper.makeOkHttpSend(
                    ConstantHelper.urlAuthorizationLogin,
                    null,
                    TipoRequisicaoHttp.Head,
                    headers);

            if (token != null && !token.isEmpty()) {
                map.clear();
                map.put("Authorization", token.trim());
                headers = Headers.of(map);

                HttpHelper.makeOkHttpSend(
                        ConstantHelper.urlWebApiSalvarDispositivo,
                        new UtilityJson<Dispositivo>().ParseObjToJson(dispositivo, Dispositivo.class),
                        TipoRequisicaoHttp.Post,
                        headers
                );
            }
        } catch (Exception ex) {
            TrackHelper.WriteError(this, "JsonParseNotification", ex.getMessage());

        }
    }

    private void HandleSendDeviceMessage(int i, Notificacao notificacao) {

        try {
            if (
                    Calendar.getInstance().getTime().after(notificacao.getDataInicial())
                            &&
                            Calendar.getInstance().getTime().before(notificacao.getDataFinal())
                            &&
                            notificacao.isAtiva()
            ) {

                new NotificationHelper(getApplicationContext())
                        .ShowNotification(
                                notificacao.getTitulo(),
                                notificacao.getDescricao(),
                                notificacao.getConteudo().split(",")
                        );
            }

            if (!CheckDeviceNotifications(lstNotificacaoApi.get(i)))
                HttpSendDeviceData(DeviceHelper.GetDevice());
        } catch (Exception e) {
            TrackHelper.WriteError(this, "HandleSendDeviceMessage", e.getMessage());
        }

    }

}
