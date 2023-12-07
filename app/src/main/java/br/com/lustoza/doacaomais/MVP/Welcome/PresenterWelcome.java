package br.com.lustoza.doacaomais.MVP.Welcome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import br.com.lustoza.doacaomais.Helper.ConstantHelper;
import br.com.lustoza.doacaomais.Helper.TrackHelper;
import br.com.lustoza.doacaomais.LoginActivity;
import br.com.lustoza.doacaomais.MainActivity;

/**
 * Created by Adilson on 24/06/2018.
 */

public class PresenterWelcome implements IWelcome.IPresenterWelcome {

    private IWelcome.IViewWelcome _iViewWelcome;
    private IWelcome.IModelWelcome _iModelWelcome;
    private Bundle _savedInstanceState;
    private Intent _intent;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions googleSignInOptions;
    private GoogleSignInAccount googleSignInAccount;
    private CallbackManager callbackManager;

    public PresenterWelcome(Bundle savedInstanceState) {
        this._savedInstanceState = savedInstanceState;
        this.setModel(new ModelWelcome(this));
    }

    @Override
    public void setView(IWelcome.IViewWelcome iViewWelcome) {
        try {
            this._iViewWelcome = iViewWelcome;
            if (_iViewWelcome != null) {
                _iViewWelcome.InitView();
                _iViewWelcome.ConfigToEmail();
                this.configFacebook();
                this.configGooglePlus();
            }
        } catch (Exception e) {
            TrackHelper.WriteError(this, "PresenterWelcome", e.getMessage());
        }
    }

    public void setModel(IWelcome.IModelWelcome iModelWelcome) {
        try {
            this._iModelWelcome = iModelWelcome;

        } catch (Exception e) {
            TrackHelper.WriteError(this, "PresenterWelcome", e.getMessage());
        }
    }

    @Override
    public Context getContext() {
        return (Context) this._iViewWelcome;
    }

    @Override
    public Activity getActivity() {
        return (Activity) this._iViewWelcome;
    }

    @Override
    public void goToLoginEmail() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _iViewWelcome.showProgressBar(View.VISIBLE);
            }
        });

        try {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    _intent = new Intent(getContext(), LoginActivity.class);

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    getActivity().startActivity(_intent);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            _iViewWelcome.showProgressBar(View.GONE);
                        }
                    });

                }
            }).start();

        } catch (Exception e) {
            TrackHelper.WriteError(this.getClass().getEnclosingMethod(), "GoToLogin", e.getMessage());
        }
    }

    @Override
    public void configGooglePlus() {

        try {

            googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .enableAutoManage((FragmentActivity) getActivity(), connectionResult -> {

                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                    .build();

            _iViewWelcome.CallbackGoogle(mGoogleApiClient);
            _iViewWelcome.GoogleWidgets();

        } catch (Exception e) {
            TrackHelper.WriteError(getContext(), "ConfigGooglePlus", e.getMessage());
        }
    }

    @Override
    public void configFacebook() {

        if (AccessToken.getCurrentAccessToken() != null)
            LoginManager.getInstance().logOut();
        if (callbackManager == null)
            callbackManager = CallbackManager.Factory.create();
        _iViewWelcome.CallbackFacebook(callbackManager);
        _iViewWelcome.FacebookWidgetsEvents();
    }

    @Override
    public void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            googleSignInAccount = result.getSignInAccount();
            updateUI(true, googleSignInAccount);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false, null);
        }
    }

    private void updateUI(boolean signedIn, GoogleSignInAccount googleSignInAccount) {
        if (signedIn) {

            try {
                ConstantHelper.isLogadoRedesSociais = true;
                ConstantHelper.foiTrocadaImagemPerfil = false;
                _iModelWelcome.CallGoogleApi(googleSignInAccount);
                goToMainActivity();
            } catch (Exception e) {
                TrackHelper.WriteError(this.getClass().getEnclosingMethod(), "updateUI", e.getMessage());
            }

        } else {

            _iViewWelcome.googleSignButtonShow(View.VISIBLE);
        }
    }

    @Override
    public void goToMainActivity() {

        if (ConstantHelper.isLogado || ConstantHelper.isLogadoRedesSociais) {
            _intent = new Intent(getContext(), MainActivity.class);
            _intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(_intent);
            getActivity().finish();
        }
        getActivity().finish();
    }
}

