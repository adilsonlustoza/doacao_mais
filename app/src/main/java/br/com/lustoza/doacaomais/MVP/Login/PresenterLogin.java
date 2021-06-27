package br.com.lustoza.doacaomais.MVP.Login;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import br.com.lustoza.doacaomais.Helper.ConstantHelper;
import br.com.lustoza.doacaomais.Helper.PrefHelper;
import br.com.lustoza.doacaomais.Helper.TrackHelper;
import br.com.lustoza.doacaomais.MainActivity;

/**
 * Created by Adilson on 07/07/2018.
 */

public class PresenterLogin implements ILogin.IPresenterLogin {

    private ILogin.IViewLogin _iViewLogin;
    private Bundle _bundle;
    private Intent _intent;
    private UserLoginTask mAuthTask = null;
    private String _name;
    private String _email;
    private boolean _manterLogado;
    private AccountManager accountManager;
    private Account[] accounts;

    public PresenterLogin(Bundle bundle) {
        _bundle = bundle;
    }

    @Override
    public void setView(ILogin.IViewLogin iViewLogin) {
        _iViewLogin = iViewLogin;
        if (_iViewLogin != null)
            _iViewLogin.InitView();

    }

    @Override
    public Context getContext() {
        return (Context) this._iViewLogin;
    }

    @Override
    public Activity getActivity() {
        return (Activity) this._iViewLogin;
    }

    @Override
    public void ValidaLogin(String name, String email) {

        if (!this.isNameValid(name)) {
            _iViewLogin.setMessageLogin();

        } else if (!this.isEmailValid(email)) {
            _iViewLogin.setMessageEmail();
        } else {
            mAuthTask = new UserLoginTask(name, email);
            mAuthTask.execute((Void) null);
        }

    }

    public boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && email.contains("@") && email.contains(".");
    }

    public boolean isNameValid(String name) {
        return !TextUtils.isEmpty(name) && name.length() > 3;
    }

    public void goToMainActivity() {
        try {

            _bundle = new Bundle();
            _bundle.putString(PrefHelper.PreferenciaNome, _name);
            _bundle.putString(PrefHelper.PreferenciaEmail, _email);

            _intent = new Intent(getContext(), MainActivity.class);
            _intent.putExtras(_bundle);

            ConstantHelper.isLogado = true;
            ConstantHelper.isLogadoRedesSociais = false;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                getActivity().startActivity(_intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            else
                getActivity().startActivity(_intent);

            getActivity().finish();
        } catch (Exception e) {
            TrackHelper.WriteError(this, "GotoMainActivicty", e.getMessage());
        }
    }

    @Override
    public void itHasBeenAuthenticated() {
        try {

            _name = PrefHelper.getString(getContext(), PrefHelper.PreferenciaNome);
            _email = PrefHelper.getString(getContext(), PrefHelper.PreferenciaEmail);
            _manterLogado = PrefHelper.getBoolean(getContext(), PrefHelper.PreferenciaLogado);

            if (!TextUtils.isEmpty(_name))
                _iViewLogin.setLogin(_name);

            if (!TextUtils.isEmpty(_email))
                _iViewLogin.setEmail(_email);
            else {
                accountManager = AccountManager.get(getActivity());
                accounts = accountManager.getAccounts();
                if (accounts.length > 0)
                    for (Account account : accounts)
                        if (account.name.contains("@") && account.name.contains("."))
                            _iViewLogin.setEmail(account.name);
            }

            if (_manterLogado && !(TextUtils.isEmpty(_name) && TextUtils.isEmpty(_email)))
                goToMainActivity();
        } catch (Exception e) {
            TrackHelper.WriteError(this, "onStart", e.getMessage());
        }

    }

    private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        UserLoginTask(String name, String email) {
            _name = name;
            _email = email;
        }

        @Override
        protected void onPreExecute() {
            _iViewLogin.showProgressBar(View.VISIBLE);

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                PrefHelper.setString(getContext(), PrefHelper.PreferenciaNome, _name);
                PrefHelper.setString(getContext(), PrefHelper.PreferenciaEmail, _email);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            _iViewLogin.showProgressBar(View.GONE);
            mAuthTask = null;

            if (success) {
                goToMainActivity();
                getActivity().finish();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;

        }
    }

}
