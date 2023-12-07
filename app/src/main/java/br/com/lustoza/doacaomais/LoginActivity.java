package br.com.lustoza.doacaomais;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import br.com.lustoza.doacaomais.Helper.ConstantHelper;
import br.com.lustoza.doacaomais.MVP.Login.ILogin;
import br.com.lustoza.doacaomais.MVP.Login.PresenterLogin;

public class LoginActivity extends _SuperActivity implements ILogin.IViewLogin {

    private EditText mNameView;
    private AutoCompleteTextView mEmailView;
    private Button mEmailSignInButton;
    private ProgressBar progressBar;
    private ILogin.IPresenterLogin presenterLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.ConfigureToolbar(ConstantHelper.ToolbarSubTitleLogin);
        if (presenterLogin == null)
            presenterLogin = new PresenterLogin(savedInstanceState);
        presenterLogin.setView(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        presenterLogin.itHasBeenAuthenticated();

    }

    private void setViewEvents() {

        mNameView.setOnClickListener(v -> {

            mNameView.setFocusableInTouchMode(true);
            mNameView.requestFocus();
        });

        mEmailView.setOnClickListener(v -> {
            mEmailView.setFocusableInTouchMode(true);
            mEmailView.requestFocus();
        });

        mNameView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.id.name || id == EditorInfo.IME_NULL) {
                tryLogin();
                return true;
            }
            return false;
        });

        //Botao
        mEmailSignInButton = findViewById(R.id.email_sign_in_button);

        mEmailSignInButton.setOnClickListener(view -> tryLogin());

    }

    @Override
    public void setLogin(String login) {
        mNameView.setText(login);
    }

    @Override
    public void setEmail(String email) {
        mEmailView.setText(email);
    }

    @Override
    public void setMessageLogin() {
        mNameView.setError(getString(R.string.error_invalid_name));
        mNameView.requestFocus();
    }

    @Override
    public void setMessageEmail() {
        mEmailView.setError(getString(R.string.error_invalid_email));
        mEmailView.requestFocus();
    }

    private void tryLogin() {
        presenterLogin.ValidaLogin(mNameView.getText().toString(), mEmailView.getText().toString());
    }

    @Override
    public void InitView() {
        this.mNameView = findViewById(R.id.name);
        this.mEmailView = findViewById(R.id.email);
        this.mEmailSignInButton = this.findViewById(R.id.email_sign_in_button);
        this.progressBar = findViewById(R.id.progress_bar);
        this.mNameView.setError(null);
        this.mEmailView.setError(null);
        this.setViewEvents();
    }

    @Override
    public void showProgressBar(int visibilidade) {
        this.progressBar.setVisibility(visibilidade);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}

