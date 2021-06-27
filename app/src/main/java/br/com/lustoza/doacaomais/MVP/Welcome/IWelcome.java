package br.com.lustoza.doacaomais.MVP.Welcome;

import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import br.com.lustoza.doacaomais.MVP.IGlobalPresenter;

/**
 * Created by Adilson on 24/06/2018.
 */

public interface IWelcome {

    interface IPresenterWelcome extends IGlobalPresenter {

        void setView(IViewWelcome iViewWelcome);

        void setModel(IModelWelcome iModelWelcome);

        void goToLoginEmail();

        void configGooglePlus();

        void configFacebook();

        void handleSignInResult(GoogleSignInResult result);

        void goToMainActivity();
    }

    interface IModelWelcome {
        void CallGoogleApi(GoogleSignInAccount googleSignInAccount);
    }

    interface IViewWelcome {

        void InitView();

        void GoogleWidgets();

        void CallbackFacebook(CallbackManager callbackManager);

        void CallbackGoogle(GoogleApiClient googleApiClient);

        void FacebookWidgetsEvents();

        void ConfigToEmail();

        void googleSignButtonShow(int visibilidade);

        void showProgressBar(int visibilidade);

    }

}
