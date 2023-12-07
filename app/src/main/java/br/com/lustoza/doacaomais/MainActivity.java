package br.com.lustoza.doacaomais;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.fragment.app.FragmentTransaction;

import com.facebook.AccessToken;

import br.com.lustoza.doacaomais.Api.FacebookApi;
import br.com.lustoza.doacaomais.Api.GoogleApi;
import br.com.lustoza.doacaomais.Fragments._SuperFragment;
import br.com.lustoza.doacaomais.Helper.ConstantHelper;
import br.com.lustoza.doacaomais.Helper.ImageHelper;
import br.com.lustoza.doacaomais.Helper.PrefHelper;
import br.com.lustoza.doacaomais.Helper.TrackHelper;
import br.com.lustoza.doacaomais.Services.Job.JobServiceUpdateFilesBroadcast;
import br.com.lustoza.doacaomais.Services.Job.NotificationBroadcast;
import br.com.lustoza.doacaomais.Services.Rede.NetWorkService;

public class MainActivity extends _SuperActivity implements View.OnClickListener {

    FragmentTransaction transaction;
    NotificationBroadcast notificationBroadcast;
    JobServiceUpdateFilesBroadcast jobServiceUpdateFilesBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new _SuperFragment());
        transaction.addToBackStack(null);
        transaction.commit();
        this.BroadCastRegister();
    }

    private void BroadCastRegister() {
        try {
            if (NetWorkService.instance().isEnabledNetWork(superContext) && !ConstantHelper.isRegistradoServicos) {

                if (this.getClass().getSuperclass().getSimpleName().contains("_SuperActivity")) {
                    ConstantHelper.isRegistradoServicos = true;
                    notificationBroadcast = new NotificationBroadcast();
                    jobServiceUpdateFilesBroadcast = new JobServiceUpdateFilesBroadcast();

                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction(ConstantHelper.IntentStartAllServices);
                    intentFilter.addCategory(Intent.CATEGORY_DEFAULT);

                    registerReceiver(notificationBroadcast, intentFilter);
                    registerReceiver(jobServiceUpdateFilesBroadcast, intentFilter);
                }
            }

        } catch (Exception e) {
            TrackHelper.WriteError(this, "BroadCastRegister", MapsActivity.class.getSimpleName());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            this.CheckWhoIsLogado();
        } catch (Exception e) {
            TrackHelper.WriteError(this, "OnCreate", e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConstantHelper.login) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                String value = data.getStringExtra(PrefHelper.PreferenciaEmail);
                if (value != null && !value.isEmpty())
                    TrackHelper.WriteInfo(this, "onActivityResult", value);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        try {

            if (NetWorkService.instance().isEnabledNetWork(superContext) && ConstantHelper.isRegistradoServicos) {
                intent = new Intent(ConstantHelper.IntentStartAllServices);
                sendBroadcast(intent);
            }

        } catch (Exception e) {
            TrackHelper.WriteError(this, "onStart", e.getMessage());
        }
    }

    @Override
    public void onDestroy() {

        try {
            if (ConstantHelper.isRegistradoServicos && this.getClass().getSuperclass().getSimpleName().contains("_SuperActivity")) {
                ConstantHelper.isRegistradoServicos = false;
                unregisterReceiver(notificationBroadcast);
                unregisterReceiver(jobServiceUpdateFilesBroadcast);
            }
        } catch (Exception e) {
            TrackHelper.WriteError(this, "onDestroy", e.getMessage());
        }
        super.onDestroy();

    }

    public void CheckWhoIsLogado() {

        try {

            if (!ConstantHelper.foiTrocadaImagemPerfil) {
                this.ConfigureFacebook();
                this.ConfigureGoogle();
            }
            //Login Facebook
            if (AccessToken.getCurrentAccessToken() != null && ConstantHelper.isLogadoRedesSociais) {
                new GoogleApi().SetProfileGoogle(null);

            }
            //Login google
            if (GoogleApi.GetProfileGoogle() != null && ConstantHelper.isLogadoRedesSociais) {
                new FacebookApi().setNullProfileFacebook();

            }
            //Login Normal
            if (ConstantHelper.isLogado || ConstantHelper.foiTrocadaImagemPerfil) {
                ConstantHelper.foiTrocadaImagemPerfil = false;

                intent = getIntent();

                bundle = intent.getExtras();
                header = GetNavegationViewHeader();

                name = header.findViewById(R.id.txtNomeNavHeader);
                email = header.findViewById(R.id.txtEmailNavHeader);

                String setNome = bundle.getString(PrefHelper.PreferenciaNome);
                String setEmail = bundle.getString(PrefHelper.PreferenciaEmail);

                if (!TextUtils.isEmpty(setNome))
                    name.setText(setNome);

                if (!TextUtils.isEmpty(setEmail))
                    email.setText(setEmail);

                imageView = header.findViewById(R.id.imgLoginNavHeader);
                imageView.setOnClickListener(this);
                String image = PrefHelper.getString(superContext, PrefHelper.PreferenciaFoto);

                if (image != null && !TextUtils.isEmpty(image)) {
                    bitmap = ImageHelper.DecodeBase64(image);
                    imageView.setImageBitmap(ImageHelper.getCircularBitmap(bitmap));
                } else
                    imageView.setImageResource(R.drawable.user_boy);

            }

            if (!ConstantHelper.isLogado && !ConstantHelper.isLogadoRedesSociais && !ConstantHelper.foiTrocadaImagemPerfil)
                this.GoToApresentacao();

        } catch (Exception e) {
            TrackHelper.WriteError(this, "CheckWhoIsLogado", e.getMessage());
        }

    }

    @Override
    public void onClick(View v) {
        try {

            if (v.getId() == R.id.imgLoginNavHeader)
                intent = new Intent(this, PerfilActivity.class);
            startActivity(intent);
            finish();

        } catch (Exception e) {
            TrackHelper.WriteError(this, "onClick MainActivity", e.getMessage());
        }
    }

}
