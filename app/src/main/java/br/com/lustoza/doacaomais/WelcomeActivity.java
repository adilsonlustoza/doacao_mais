package br.com.lustoza.doacaomais;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Collections;

import br.com.lustoza.doacaomais.Helper.ConstantHelper;
import br.com.lustoza.doacaomais.Helper.TrackHelper;
import br.com.lustoza.doacaomais.MVP.Welcome.IWelcome;
import br.com.lustoza.doacaomais.MVP.Welcome.PresenterWelcome;

public class WelcomeActivity extends _SuperActivity implements View.OnClickListener, IWelcome.IViewWelcome {

    //region ***Variaveis***

    private static final int RC_SIGN_IN = 9001;
    //Login Facebook
    private LoginButton loginFaceButton;
    private CallbackManager _callbackManager;
    //Login Google
    private GoogleApiClient _mGoogleApiClient;
    private SignInButton signInButton;

    // private GoogleApi googleApi;
    //Components Commons
    private Button loginEmailButton;
    private ProgressBar progressBar;

    private IWelcome.IPresenterWelcome iPresenterWelcome;

    //endregion

    //region ***Metodos da Activity***

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        this.ConfigureToolbar(ConstantHelper.ToolbarSubTitleBemVindo);
        if (iPresenterWelcome == null)
            iPresenterWelcome = new PresenterWelcome(savedInstanceState);
        iPresenterWelcome.setView(this);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Google Result
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            iPresenterWelcome.handleSignInResult(result);
        }

        //Facebook Callback
        _callbackManager.onActivityResult(requestCode, resultCode, data);
        if (AccessToken.getCurrentAccessToken() != null)
            iPresenterWelcome.goToMainActivity();
    }

    //endregion

    //region    ***Metodos do Google***
    @Override
    public void GoogleWidgets() {
        signInButton.setSize(SignInButton.SIZE_WIDE);
        TextView textView = (TextView) signInButton.getChildAt(0);
        textView.setText(R.string.google_button_text);
        signInButton.setOnClickListener((View.OnClickListener) superContext);
    }

    @Override
    public void CallbackGoogle(GoogleApiClient callbackManager) {
        _mGoogleApiClient = callbackManager;
    }

    private void signIn() {
        try {
            Intent intent = Auth.GoogleSignInApi.getSignInIntent(_mGoogleApiClient);
            startActivityForResult(intent, RC_SIGN_IN);
        } catch (Exception e) {
            TrackHelper.WriteError(superContext, "signIn", e.getMessage());
        }
    }

    @Override
    public void googleSignButtonShow(int visibilidade) {
        this.findViewById(R.id.sign_in_button).setVisibility(visibilidade);
    }

    //endregion

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sign_in_button) {
            signIn();
        }
    }

    @Override
    public void InitView() {
        try {
            this.progressBar = this.findViewById(R.id.progress_bar);
            this.loginEmailButton = this.findViewById(R.id.loginEmail);
            this.loginFaceButton = this.findViewById(R.id.loginFacebook);
            this.signInButton = this.findViewById(R.id.sign_in_button);
            // this.ExitApp();
        } catch (Exception e) {
            TrackHelper.WriteError(this, "initView", e.getMessage());
        }

    }


    @Override
    public void showProgressBar(int visibilidade) {
        this.progressBar.setVisibility(visibilidade);
    }

    @Override
    public void ConfigToEmail() {

        loginEmailButton.setOnClickListener(v -> {
            ConstantHelper.isLogado = false;
            iPresenterWelcome.goToLoginEmail();

        });
    }

    //region ***Metodos do Facebook***
    @Override
    public void CallbackFacebook(CallbackManager callbackManager) {
        _callbackManager = callbackManager;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void FacebookWidgetsEvents() {

        loginFaceButton.setReadPermissions(Collections.singletonList("public_profile,email"));
        // Callback registration
        loginFaceButton.registerCallback(_callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                if (AccessToken.getCurrentAccessToken() != null) {
                    ConstantHelper.isLogadoRedesSociais = true;
                    ConstantHelper.foiTrocadaImagemPerfil = false;
                }
            }

            @Override
            public void onCancel() {
                LoginManager.getInstance().logOut();
                // App code
            }

            @Override
            public void onError(@NonNull FacebookException exception) {
                // App code
            }
        });
    }

    //endregion

}


