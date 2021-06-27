package br.com.lustoza.doacaomais;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.nio.charset.StandardCharsets;

import br.com.lustoza.doacaomais.Domain.Caccc;
import br.com.lustoza.doacaomais.Domain.ObjectValue.TpDoacao;
import br.com.lustoza.doacaomais.Helper.ConstantHelper;
import br.com.lustoza.doacaomais.Helper.GenericParcelableHelper;
import br.com.lustoza.doacaomais.Helper.TrackHelper;

public class WebViewActivity extends _SuperActivity {

    private WebView webView;
    private ProgressBar progressBar;

    private TpDoacao tpDoacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        this.ConfigureToolbar(ConstantHelper.ToolbarSubTitlePagSeguro);
        this.ConfigureReturnToolbar();
        this.Init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void Init() {
        String emailPagSeguro;
        String emailPayPal;
        String centro;

        Bundle objBundle = getIntent().getExtras().getBundle(ConstantHelper.objBundle);
        if (objBundle != null) {

            GenericParcelableHelper<Caccc> cacccGenericParcelableHelper = objBundle.getParcelable(ConstantHelper.objCaccc);

            if (cacccGenericParcelableHelper != null) {
                emailPagSeguro = cacccGenericParcelableHelper.getValue().getEmailPagSeguro();
                emailPayPal = cacccGenericParcelableHelper.getValue().getEmailPayPal();
                centro = cacccGenericParcelableHelper.getValue().getNome();

                if (cacccGenericParcelableHelper.getValue().getEnumTipoDoacao() == TpDoacao.PagSeguro)
                    tpDoacao = TpDoacao.PagSeguro;
                else if (cacccGenericParcelableHelper.getValue().getEnumTipoDoacao() == TpDoacao.PayPal)
                    tpDoacao = TpDoacao.PayPal;
                else if (cacccGenericParcelableHelper.getValue().getEnumTipoDoacao() == TpDoacao.PagSeguro_PayPal)
                    tpDoacao = TpDoacao.PagSeguro_PayPal;

                try {

                    if (tpDoacao == TpDoacao.PagSeguro)
                        this.ConfigureToolbar(getString(R.string.IntegracaoPagSeguro));
                    else if (tpDoacao == TpDoacao.PayPal)
                        this.ConfigureToolbar(getString(R.string.IntegracaoPayPal));

                    webView = this.findViewById(R.id.wv_pagseguro);
                    progressBar = this.findViewById(R.id.progress_bar);

                    WebSettings webSettings = webView.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    webSettings.setDefaultTextEncodingName(StandardCharsets.UTF_8.name());

                    webSettings.setMinimumFontSize(2);
                    webSettings.setLoadWithOverviewMode(true);
                    webSettings.setUseWideViewPort(true);
                    webSettings.setBuiltInZoomControls(true);
                    webSettings.setDisplayZoomControls(false);

                    webSettings.setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
                    webSettings.setAppCacheEnabled(true);

                    webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
                    webView.setPadding(0, 0, 0, 0);
                    webView.clearHistory();

                    webView.setWebViewClient(new WebViewClient() {

                        @Override
                        public void onPageStarted(WebView view, String url, Bitmap favicon) {
                            super.onPageStarted(view, url, favicon);
                            progressBar.setVisibility(ProgressBar.VISIBLE);
                            webView.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onPageCommitVisible(WebView view, String url) {
                            super.onPageCommitVisible(view, url);
                            progressBar.setVisibility(ProgressBar.GONE);
                            webView.setVisibility(View.VISIBLE);

                        }

                        @Override
                        public void onPageFinished(WebView webView, String url) {
                            super.onPageFinished(webView, url);
                            progressBar.setVisibility(ProgressBar.GONE);
                            webView.setVisibility(View.VISIBLE);
                            webView.loadUrl("javascript:resize(document.body.getBoundingClientRect().height)");

                        }

                    });

                    String postData;
                    if (tpDoacao == TpDoacao.PagSeguro) {
                        postData = "currency='BRL'&receiverEmail='" + emailPagSeguro + "' ";
                        webView.postUrl(ConstantHelper._urlPagSeguro, postData.getBytes());
                    } else if (tpDoacao == TpDoacao.PayPal) {
                        postData = "cmd=_donations&business=" + emailPayPal + "&lc=BR&item_name=" + centro + "&currency_code=BRL&bn=PP-DonationsBF:btn_donateCC_LG.gif:NonHosted";
                        webView.postUrl(ConstantHelper._urlPayPal, postData.getBytes());
                    }

                } catch (Exception e) {
                    TrackHelper.WriteError(this, "WebViewActivity", e.getMessage());
                }

            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}

