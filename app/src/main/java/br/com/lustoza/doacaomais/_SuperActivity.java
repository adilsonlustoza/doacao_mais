package br.com.lustoza.doacaomais;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginManager;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

import br.com.lustoza.doacaomais.Api.FacebookApi;
import br.com.lustoza.doacaomais.Api.GoogleApi;
import br.com.lustoza.doacaomais.Domain.Caccc;
import br.com.lustoza.doacaomais.Domain.FacebookUser;
import br.com.lustoza.doacaomais.Domain.GoogleUser;
import br.com.lustoza.doacaomais.Helper.ConstantHelper;
import br.com.lustoza.doacaomais.Helper.ImageHelper;
import br.com.lustoza.doacaomais.Helper.PrefHelper;
import br.com.lustoza.doacaomais.Helper.SimpleDialogFragmentHelper;
import br.com.lustoza.doacaomais.Helper.TrackHelper;
import br.com.lustoza.doacaomais.Interfaces.OnCustomDialogClickListener;
import br.com.lustoza.doacaomais.Services.Rede.NetWorkService;
import br.com.lustoza.doacaomais.Utils.EnumCommand;
import br.com.lustoza.doacaomais.Utils.HandleFile;
import br.com.lustoza.doacaomais.Utils.UtilApplication;
import br.com.lustoza.doacaomais.Utils.UtilityMethods;

/**
 * Created by ubuntu on 12/14/16.
 */

public class _SuperActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected static Menu menu;
    protected int permissionRequest;
    protected Toolbar toolbar;
    protected DrawerLayout drawer;
    protected Activity activity;
    protected MenuInflater menuInflater;
    protected Bitmap bitmap;
    protected SearchView searchView;
    protected ProgressBar progressBar;
    protected String jsonString;
    protected SimpleDialogFragmentHelper _simpleDialogFragmentHelper;
    protected FragmentManager fragmentManager;
    protected ActionBarDrawerToggle actionBarDrawerToggle;
    protected ImageView imageView;
    protected Intent intent;
    protected View globalView;
    protected FacebookUser facebookUser;
    protected GoogleUser googleUser;
    protected Context superContext;
    protected NavigationView navigationView;
    protected View header;
    protected TextView name;

    protected TextView email;

    protected Bundle bundle;

    protected UtilApplication<Caccc> cacccUtilApplication;

    protected HandleFile handleFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //Transition
            getWindow().requestFeature(android.view.Window.FEATURE_CONTENT_TRANSITIONS);
            Slide ts = new Slide(); //Explode();
            ts.setStartDelay(0);
            ts.setDuration(500);
            ts.setInterpolator(new AccelerateInterpolator(3));
            getWindow().setEnterTransition(ts);
        }

        setContentView(R.layout.activity_main);
        //Global Context
        UtilityMethods.SetGlobalContext(this);

        //Toolbar
        this.ConfigureToolbar();
        this.ConfigureNavegationDrawer();

        //Global Actitivity, Context, View
        activity = this;
        superContext = this;
        globalView = toolbar;
        cacccUtilApplication = (UtilApplication<Caccc>) getApplicationContext();

    }

    protected void ConfigureNavegationDrawer() {
        try {

            drawer = this.findViewById(R.id.drawer_layout);
            actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();
            actionBarDrawerToggle.isDrawerSlideAnimationEnabled();
            navigationView = this.findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        } catch (Exception e) {
            TrackHelper.WriteError(this, "ConfigureNavegationDrawer", e.getMessage());
        }
    }

    protected void ConfigureToolbarSuporte() {
        try {
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setTitle("");
        } catch (Exception e) {
            TrackHelper.WriteError(this, "ConfigureToolBar", e.getMessage());
        }

    }

    protected void ConfigureToolbar() {
        try {
            toolbar = this.findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setTitle(ConstantHelper.AppName);
            getSupportActionBar().setSubtitle(ConstantHelper.ToolbarSubTitleSuper);
        } catch (Exception e) {
            TrackHelper.WriteError(this, "ConfigureToolBar", e.getMessage());
        }

    }

    protected void ConfigureToolbar(String subTitle) {
        try {

            toolbar = this.findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setTitle(ConstantHelper.AppName);
            getSupportActionBar().setSubtitle(subTitle);
        } catch (Exception e) {
            TrackHelper.WriteError(this, "ConfigureToolBar", e.getMessage());
        }

    }

    protected void ConfigureReturnToolbar() {

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setOnClickListener(click -> activity.onBackPressed());
    }

    //-------------------------------------------region  Methods---------------------------------

    protected void GoToApresentacao() {
        try {
            intent = new Intent(this, ApresentacaoActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            TrackHelper.WriteError(this, "GoToWelcome", e.getMessage());
        }

    }

    protected void GoToWelcome() {
        try {
            intent = new Intent(this, WelcomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            TrackHelper.WriteError(this, "GoToWelcome", e.getMessage());
        }

    }

    protected void ExitApp() {
        LoginManager.getInstance().logOut();
        ConstantHelper.isLogado = false;
        ConstantHelper.isLogadoRedesSociais = false;
        finishAffinity();
        System.exit(0);

    }

    protected void ConfigureFacebook() {
        try {

            FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS);

            if (AccessToken.getCurrentAccessToken() != null) {

                facebookUser = new FacebookApi().GetProfilePicture();
                //Get Navegation View
                header = GetNavegationViewHeader();

                TextView name = this.header.findViewById(R.id.txtNomeNavHeader);
                TextView email = this.header.findViewById(R.id.txtEmailNavHeader);

                name.setText(facebookUser.getCompositeName());
                email.setText(facebookUser.getEmail());
                imageView = this.header.findViewById(R.id.imgLoginNavHeader);

                bitmap = ImageHelper.getCircularBitmap(
                        ImageHelper.getBitmap(facebookUser.getImageUrl())
                );

                imageView.setImageBitmap(bitmap);

                PrefHelper.setString(superContext, PrefHelper.PreferenciaNome, facebookUser.getCompositeName());
                PrefHelper.setString(superContext, PrefHelper.PreferenciaEmail, facebookUser.getEmail());
                PrefHelper.setString(superContext, PrefHelper.PreferenciaFoto, ImageHelper.EncodeTobase64(bitmap));
            }

        } catch (Exception e) {
            TrackHelper.WriteError(this, "ConfigureFacebook Main", e.getMessage());
        }
    }

    protected void ConfigureGoogle() {

        try {

            googleUser = GoogleApi.GetProfileGoogle();

            if (googleUser != null) {

                header = GetNavegationViewHeader();

                TextView name = header.findViewById(R.id.txtNomeNavHeader);
                TextView email = header.findViewById(R.id.txtEmailNavHeader);

                name.setText(googleUser.getNickName());
                email.setText(googleUser.getEmail());
                imageView = header.findViewById(R.id.imgLoginNavHeader);

                bitmap = ImageHelper.getCircularBitmap(
                        ImageHelper.getBitmap(googleUser.getUrlImage().toString())
                );
                imageView.setImageBitmap(bitmap);

                PrefHelper.setString(superContext, PrefHelper.PreferenciaNome, googleUser.getNickName());
                PrefHelper.setString(superContext, PrefHelper.PreferenciaEmail, googleUser.getEmail());
                PrefHelper.setString(superContext, PrefHelper.PreferenciaFoto, ImageHelper.EncodeTobase64(bitmap));

            }

        } catch (Exception e) {
            TrackHelper.WriteError(this, "ConfigureGoogle Main", e.getMessage());
        }

    }

    public View GetNavegationViewHeader() {
        try {
            navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            header = navigationView.getHeaderView(0);
            return header;
        } catch (Exception e) {
            TrackHelper.WriteError(this, "GetNavegationViewHeader", e.getMessage());

        }
        return null;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menuInflater = getMenuInflater();
        this.menuInflater.inflate(R.menu.main, menu);
        this.setMenuItemEnabled(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_politica) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ConstantHelper._urlPolitica)));
            return true;
        } else if (id == R.id.action_help) {
            startActivity(new Intent(this, AjudaActivity.class));
            return true;
        }
        //noinspection SimplifiableIfStatement
        else if (id == R.id.action_exit) {
            ExitApp();
            return true;
        } else if (id == android.R.id.home) {
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings({"ReturnInsideFinallyBlock", "finally"})
    //-------------------------------------------endregion----------------------------------------

    //-------------------------------------region  NavegationDrawer-------------------------------

    @Override
    public boolean onNavigationItemSelected(@NotNull MenuItem item) {

        try {
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            if (id == R.id.nav_map) {
                intent = new Intent(this, MapsActivity.class);

            } else if (id == R.id.nav_instituicoes) {
                intent = new Intent(this, CacccActivity.class);

            } else if (id == R.id.nav_newspaper) {
                intent = new Intent(this, NoticiasActivity.class);

            } else if (id == R.id.nav_eventos) {
                intent = new Intent(this, BazarActivity.class);
            } else if (id == R.id.nav_perfil) {
                intent = new Intent(this, PerfilActivity.class);

            } else if (id == R.id.nav_pref) {
                intent = new Intent(this, PreferenciaActivity.class);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            else
                startActivity(intent);

            DrawerLayout drawer = this.findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

        } catch (Exception e) {
            TrackHelper.WriteError(this, "onNavigationItemSelected", e.getMessage());
        } finally {
            return true;
        }

    }

    @Override
    public void onBackPressed() {

        try {
            DrawerLayout drawer = this.findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }

        } catch (Exception ex) {
            TrackHelper.WriteError(this, "onBackPressed", ex.getMessage());
        }

        super.onBackPressed();
        finish();

    }
    //-------------------------------------

    private void setMenuItemEnabled(Menu menu) {
        try {
            String className = this.activity.getComponentName().getClassName();

            for (int i = 0; i < menu.size(); i++) {
                if (className.contains(("Welcome")))
                    menu.getItem(i).setVisible(false);
                else if (className.contains(("Login")))
                    menu.getItem(i).setVisible(false);

            }

        } catch (Exception e) {
            TrackHelper.WriteError(superContext, "HideOptionMenu", e.getMessage());
        }

    }

//----------------------------Region Dialog---------------------------------------

    public void showSimpleDialog(String title, String question, EnumCommand enumCommand) {

        fragmentManager = this.getSupportFragmentManager();
        boolean _exitApp = true;

        OnCustomDialogClickListener onCustomDialogClickListener = command -> {

            if (command == EnumCommand.AllConfig) {
                intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Settings.ACTION_SETTINGS);
                startActivity(intent);

            } else if (command == EnumCommand.NetWorkEnableWiFi) {
                intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intent);
            } else if (command == EnumCommand.NetWorkEnabledMobile) {
                intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Settings.ACTION_DATA_ROAMING_SETTINGS);
                startActivity(intent);

            } else if (command == EnumCommand.Localization) {
                intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.putExtra("enabled", true);
                startActivity(intent);
            } else if (command == EnumCommand.ExitApp) {
                ExitApp();
            } else if (command == EnumCommand.CloseWindow) {
                finish();
            }

        };

        _simpleDialogFragmentHelper = SimpleDialogFragmentHelper.newInstance(title, question, onCustomDialogClickListener);
        if (enumCommand == EnumCommand.Localization)
            _exitApp = false;

        _simpleDialogFragmentHelper.setCommand(enumCommand, _exitApp);
        _simpleDialogFragmentHelper.show(fragmentManager, "fragment_dialog_simple");

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!this.getLocalClassName().contains("Apresentacao"))
            this.ChecaRede();
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }

    private void ChecaRede() {
        try {

            if (!NetWorkService.instance().isEnabledNetWork(superContext))
                showSimpleDialog("Usar rede ?", "Este app necessita de conexão com a rede para continuar.", EnumCommand.NetWorkEnabledMobile);

        } catch (Exception e) {
            TrackHelper.WriteError(this, "ChecaInternet", e.getMessage());
        }
    }

    //------------------------------------Common Task -------------------------------------

    protected void CheckPermissions(String[] perms) {
        try {
            if (
                    ContextCompat.checkSelfPermission(superContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(superContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(superContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
            ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    requestPermissions(perms, permissionRequest);

            }
        } catch (Exception ex) {
            TrackHelper.WriteError(superContext, "CheckPermissions", ex.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {

        if (
                (Arrays.asList(permissions).contains("android.permission.FINE_LOCATION") && grantResults[0] != PackageManager.PERMISSION_GRANTED) &&
                        (Arrays.asList(permissions).contains("android.permission.ACCESS_COARSE_LOCATION") && grantResults[1] != PackageManager.PERMISSION_GRANTED)
        )
            Toast.makeText(this, "Seu local não será demonstrado em localização.", Toast.LENGTH_LONG).show();
        else
            intent = new Intent(this, MapsActivity.class);

        if (Arrays.asList(permissions).contains("android.permission.CAMERA") && grantResults[0] != PackageManager.PERMISSION_GRANTED)
            Toast.makeText(this, "Você não poderá atualizar sua foto no perfil.", Toast.LENGTH_LONG).show();

    }

}
