package br.com.lustoza.doacaomais.Helper;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import br.com.lustoza.doacaomais.Interfaces.IOnLoadCallBack;
import br.com.lustoza.doacaomais.Utils.HandleFile;
import br.com.lustoza.doacaomais.Utils.UtilityJson;
import kotlin.Pair;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ubuntu on 2/5/17.
 */
public class HttpHelper {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final String TAG = HttpHelper.class.getSimpleName();
    private Context context = null;
    private HandleFile handleFile = null;
    private String fileDownloadList = null;

    public HttpHelper() {
    }

    public HttpHelper(Context context) {
        this.context = context;
    }

    public HttpHelper(Context context, String fileName) {
        this.context = context;
        this.fileDownloadList = fileName;
    }

    @Deprecated()
    public static String makeServiceCall(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);

        } catch (Exception e) {
            TrackHelper.WriteError(HttpHelper.class, "DownloadTask doInBackground", e.getMessage());
        }

        return response;
    }

    @Deprecated()
    public static int makeServiceSend(String reqUrl, JSONObject jsonObject) {
        int HttpResult = -3;

        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestMethod("POST");
            // read the response

            String json = jsonObject.toString();
            OutputStream outputStream = conn.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            outputStreamWriter.write(json);
            outputStreamWriter.flush();
            outputStreamWriter.close();

            HttpResult = conn.getResponseCode();

            return Math.max(HttpResult, -1);

        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }

        return HttpResult;
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static String makeOkHttpCall(String url) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful())
                    return Objects.requireNonNull(response.body()).string();
                return null;
            }
        } catch (Exception e) {
            TrackHelper.WriteError(HttpHelper.class, "makeHttpCall", e.getMessage());
        }

        return null;
    }

    public static String makeOkHttpSend(String url, String json, TipoRequisicaoHttp tipoRequisicaoHttp, Headers headers) {
        try {

            OkHttpClient client = new OkHttpClient();
            RequestBody body = null;
            Request request;

            switch (tipoRequisicaoHttp) {
                case Post:
                    if (json != null && !json.isEmpty()) {
                        body = RequestBody.create(json, JSON);
                        request = new Request.Builder()
                                .url(url)
                                .headers(headers)
                                .post(body)
                                .build();

                        try (Response response = client.newCall(request).execute()) {
                            if (response.isSuccessful())
                                return Objects.requireNonNull(response.body()).string();
                            return null;
                        }

                    }
                    break;
                case Put:
                    if (json != null && !json.isEmpty()) {
                        body = RequestBody.create(json, JSON);
                        request = new Request.Builder()
                                .url(url)
                                .headers(headers)
                                .put(body)
                                .build();

                        try (Response response = client.newCall(request).execute()) {
                            if (response.isSuccessful())
                                return Objects.requireNonNull(response.body()).string();
                            return null;
                        }

                    }
                    break;
                case Get:

                    request = new Request.Builder()
                            .url(url)
                            .headers(headers)
                            .get()
                            .build();

                    try (Response response = client.newCall(request).execute()) {
                        if (response.isSuccessful())
                            return Objects.requireNonNull(response.body()).string();

                    }

                    break;

                case Delete:
                    if (json != null && !json.isEmpty())
                        body = RequestBody.create(json, JSON);

                    request = new Request.Builder()
                            .url(url)
                            .headers(headers)
                            .delete(body)
                            .build();

                    try (Response response = client.newCall(request).execute()) {
                        if (response.isSuccessful())
                            return Objects.requireNonNull(response.body()).string();
                    }

                    break;
                case Head:
                    if (json == null || json.isEmpty()) {
                        request = new Request.Builder()
                                .url(url)
                                .headers(headers)
                                .head()
                                .build();

                        String key, value, result = null;

                        try (Response response = client.newCall(request).execute()) {
                            if (response.isSuccessful()) {

                                Headers lstHeaders = response.headers();

                                if (lstHeaders.size() > 0) {
                                    for (Pair<? extends String, ? extends String> head : lstHeaders) {
                                        key = head.component1();
                                        value = head.component2();

                                        if (key.contains("Token"))
                                            result = value;

                                    }
                                }

                            }
                            return result;
                        }

                    }
                    break;
            }

        } catch (Exception e) {
            TrackHelper.WriteError(HttpHelper.class, "makeHttpSend", e.getMessage());
        }

        return null;
    }

    public Object RestDownloadList(final String url, final Class<?> clazz, final IOnLoadCallBack iOnLoadCallBack) {

        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());
        final AtomicReference atomicReferenceFinal = new AtomicReference();

        try {

            executor.execute(() -> {

                if (handleFile == null)
                    handleFile = new HandleFile(context, fileDownloadList);

                String fileJson = handleFile.ReadFile();

                if (TextUtils.isEmpty(fileJson) || fileJson.length() < 10) {
                    String jsonString = HttpHelper.makeOkHttpCall(url);
                    if (jsonString != null && jsonString.length() > 0) {
                        handleFile.WriteFile(jsonString);
                        fileJson = jsonString;
                    }
                }

                final List<?> list = new UtilityJson<List<?>>().ParseJsonArrayToList(fileJson, clazz);

                if (list != null && list.size() > 0)
                    atomicReferenceFinal.set(list);

                handler.post(() -> {
                    if (iOnLoadCallBack != null)
                        iOnLoadCallBack.Execute(list, true);
                });
            });

            try {
                executor.shutdown();
                if (executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.NANOSECONDS))
                    executor.shutdownNow();
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }

        } catch (Exception ex) {
            TrackHelper.WriteError(this, "", ex.getMessage());
        }
        return atomicReferenceFinal.get();
    }

    public Object RestDownloadObj(final String url, final Class<?> clazz) {

        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final AtomicReference atomicReferenceFinal = new AtomicReference();

        try {

            executor.execute(() -> {
                try {

                    if (handleFile == null)
                        handleFile = new HandleFile(context, fileDownloadList);

                    String fileJson = handleFile.ReadFile();

                    if (TextUtils.isEmpty(fileJson) || fileJson.length() < 10) {

                        String jsonString = HttpHelper.makeOkHttpCall(url);

                        if (jsonString != null && jsonString.length() > 0) {
                            handleFile.WriteFile(jsonString);
                            fileJson = jsonString;
                        }

                    }

                    Object objClazz = new UtilityJson<>().ParseJsonToObj(fileJson, clazz);
                    atomicReferenceFinal.set(objClazz);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

            try {
                executor.shutdown();
                if (executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.NANOSECONDS))
                    executor.shutdownNow();
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }

        } catch (Exception e) {
            TrackHelper.WriteError(this, "", e.getMessage());
        }
        return atomicReferenceFinal.get();
    }

}

