package br.com.lustoza.doacaomais;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import br.com.lustoza.doacaomais.Domain.Bazar;
import br.com.lustoza.doacaomais.Domain.Caccc;
import br.com.lustoza.doacaomais.Helper.ConstantHelper;
import br.com.lustoza.doacaomais.Helper.GenericParcelableHelper;
import br.com.lustoza.doacaomais.Helper.HtmlHelper;
import br.com.lustoza.doacaomais.Helper.HttpHelper;
import br.com.lustoza.doacaomais.Helper.ImageHelper;
import br.com.lustoza.doacaomais.Helper.PrefHelper;
import br.com.lustoza.doacaomais.Helper.TrackHelper;
import br.com.lustoza.doacaomais.Services.Mapa.MapService;
import br.com.lustoza.doacaomais.Services.Rede.NetWorkService;
import br.com.lustoza.doacaomais.Utils.EnumCommand;
import br.com.lustoza.doacaomais.Utils.HandleFile;

public class MapsActivity extends _SuperActivity
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private int permissionRequest ;
    //Google Objects
    protected Location globalLocation;
    //Criar o objeto mapa
    protected GoogleMap map;
    protected GoogleApiClient googleApiClient;
    protected MapService mapService;
    //Seta os pontos no mapa com Imagem
    //  protected MarkerOptions markerOptions;
    //   protected Marker marker;
    //Latitude e Longitude(Usado para tudo)
    protected LatLng latLng;
    protected List<LatLng> listLatLng;
    protected LocationManager locationManager;
    protected LocationRequest mLocationRequest;
    protected FusedLocationProviderClient fusedLocationProviderClient;

    private Marker markerChange;

    private GenericParcelableHelper<Caccc> cacccGenericParcelableHelper;
    private GenericParcelableHelper<Bazar> bazarGenericParcelableHelper;
    private Collection<Caccc> cacccList;

    private Caccc caccc;
    private Bazar bazar;
    private Bundle bundle;
    private TextView txtViewWindowInfo;
    private LinearLayout ll;
    private String url;
    private GenericParcelableHelper<List<Caccc>> genericParcelableHelper;

    public MapsActivity(int permissionRequest) {
        this.permissionRequest = permissionRequest;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        this.ConfigureToolbar(getString(R.string.localizacao));
        this.ConfigureReturnToolbar();
        this.url = ConstantHelper.urlWebApiListAllCacccBazar;

        try {

            if (getIntent().getExtras() != null)
                bundle = getIntent().getExtras().getBundle(ConstantHelper.objBundle);
            // private FragmentTransaction transaction;
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            Objects.requireNonNull(mapFragment).getMapAsync(this);

            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

            if (savedInstanceState != null && savedInstanceState.getParcelable(Caccc.TAG) != null) {
                genericParcelableHelper = savedInstanceState.getParcelable(Caccc.TAG);
            }

        } catch (Exception e) {
            TrackHelper.WriteError(this, "onCreate", e.getMessage());
        }

    }

    private void LoadMapa(String url) {

        try {
            this.progressBar = findViewById(R.id.progress_bar);
            this.progressBar.setVisibility(View.VISIBLE);
            cacccList = (List<Caccc>) new HttpHelper(superContext, ConstantHelper.fileListAllCacccBazar).RestDownloadList(url, Caccc.class, null); //super.RestDownloadList(url, Caccc.class, null);
            PutInMap(cacccList);
            this.progressBar.setVisibility(View.GONE);
        } catch (Exception e) {
            TrackHelper.WriteError(this, "LoadMapa", e.getMessage());

        }
    }

    private void ChecaLocalizacao() {
        if (!NetWorkService.instance().isEnabledLocation(superContext))
            super.showSimpleDialog("Usar localização? ", "É necessário ativar a localização para continuar", EnumCommand.Localization);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.ChecaLocalizacao();
        if (!googleApiClient.isConnected())
            googleApiClient.connect();

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {

            this.map = googleMap;

            if (this.map != null) {
                this.ConfigMap();
                this.MapEvents();

                if (genericParcelableHelper != null && genericParcelableHelper.getValue() != null && genericParcelableHelper.getValue().size() > 0)
                    PutInMap(genericParcelableHelper.getValue());
                else
                    LoadMapa(url);

            }

        } catch (Exception e) {
            TrackHelper.WriteError(this, "onMapReady", e.getMessage());
        }

    }

    @SuppressWarnings("rawtypes")
    private void ConfigMap() {
        try {
            //Tipo do Mapa
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            if (bundle == null) {
                globalLocation = this.ProviderGlobalLocation();
                latLng = new LatLng(globalLocation.getLatitude(),globalLocation.getLongitude());            }
            else if (bundle.getParcelable(ConstantHelper.objCaccc) != null) {
                caccc = ((GenericParcelableHelper<Caccc>) bundle.getParcelable(ConstantHelper.objCaccc)).getValue();
                latLng = new LatLng(caccc.getEndereco().getLatitude(), caccc.getEndereco().getLongitude());
            } else if (bundle.getParcelable(ConstantHelper.objBazar) != null) {
                bazar = ((GenericParcelableHelper<Bazar>) bundle.getParcelable(ConstantHelper.objBazar)).getValue();
                latLng = new LatLng(bazar.getEndereco().getLatitude(), bazar.getEndereco().getLongitude());
            } else if (bundle.getString(ConstantHelper.objActivity) != null) {
                if (bundle.getString(ConstantHelper.objActivity).contains("Perfil")) {
                    double latitude = Double.parseDouble(PrefHelper.getString(superContext, PrefHelper.PreferenciaLatitude));
                    double longitude = Double.parseDouble(PrefHelper.getString(superContext, PrefHelper.PreferenciaLongitude));
                    latLng = new LatLng(latitude, longitude);
                }

            }

            //Posiciona a camera/*
            this.ConfigMapService(map);
            //Adiciona a casa
            this.AddMapHome();

            map.getUiSettings().setCompassEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(true);
            map.getUiSettings().setIndoorLevelPickerEnabled(true);
            //Gestures
            map.getUiSettings().setAllGesturesEnabled(true);
            map.getUiSettings().setMapToolbarEnabled(true);
            map.getUiSettings().setScrollGesturesEnabled(true);
            map.getUiSettings().setTiltGesturesEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);

        } catch (Exception e) {
            TrackHelper.WriteError(this, "ConfigMap", e.getMessage());
        }

    }

    private void ConfigMapService(GoogleMap map) {
        try {

            mapService = new MapService(map, getApplicationContext());

            if (bundle == null) {
                mapService.ConfigCameraPosition(latLng);
                mapService.SetDeviceLocation(latLng);
            } else {
                globalLocation = this.ProviderGlobalLocation();
                mapService.ConfigCameraPosition(latLng);

                if (globalLocation != null) {
                    latLng = new LatLng(globalLocation.getLatitude(), globalLocation.getLongitude());
                    mapService.SetDeviceLocation(latLng);
                }
            }
        } catch (Exception e) {
            TrackHelper.WriteError(this, "ConfigMapService : ", e.getMessage());
        }
    }

    private void AddMapServiceCaccc(Caccc caccc) {

        double Latitude = caccc.getEndereco().getLatitude();
        double Longitute = caccc.getEndereco().getLongitude();

        String complemento = caccc.getTelefone().equals("") ? caccc.getCelular() : caccc.getTelefone();

        if (caccc.getDescription() != null && !caccc.getDescription().isEmpty())
            complemento += " - " + caccc.getDescription();

        mapService.CustomAddMarkerCaccc(
                new LatLng(Latitude, Longitute),
                caccc.getNome(),
                String.format("%s", complemento),
                "Centro",
                DealPinPath(caccc.getUrlImagemPin())
        );

    }

    private Bitmap DealPinPath(String url) {
        try {

            String image = url.substring(url.lastIndexOf("/") + 1);
            HandleFile handleFile = new HandleFile(this, image);
            handleFile.ApagarArquivo();
            Bitmap bitmapStored = handleFile.ReadBitMapFile();
            Bitmap bitmapDownload;

            if (bitmapStored != null && bitmapStored.getByteCount() > 0)
                return bitmapStored;
            else {
                bitmapDownload = ImageHelper.getBitmap(url);
                if (bitmapDownload != null)
                    handleFile.WriteBitMapFile(bitmapDownload);
                bitmapStored = handleFile.ReadBitMapFile();
            }

            return (bitmapStored != null && bitmapStored.getByteCount() > 0) ? bitmapStored : bitmapDownload;
            //return bitmapDownload;

        } catch (Exception ex) {
            TrackHelper.WriteError(this, "SaveImagePath", ex.getMessage());
        }
        return null;
    }

    private void AddMapServiceStore(Bazar bazar) {
        if (bazar.getEndereco() != null) {
            double Latitude = Objects.requireNonNull(bazar.getEndereco().getLatitude());
            double Longitute = Objects.requireNonNull(bazar.getEndereco().getLongitude());
            String complemento = !TextUtils.isEmpty(bazar.getEndereco().getBairro()) ? bazar.getEndereco().getBairro() : bazar.getNome();

            if (Latitude != 0 && Longitute != 0) {
                mapService.CustomAddMarkerStore(
                        new LatLng(Latitude, Longitute),
                        bazar.getNome(),
                        complemento,
                        "Bazar"
                );
            }
        }

    }

    private void AddMapHome() {
        try {
            if (PrefHelper.getString(superContext, "pref_latitude") != null && PrefHelper.getString(superContext, "pref_longitude") != null) {

                double latHome = Double.parseDouble(PrefHelper.getString(superContext, "pref_latitude"));
                double lonHome = Double.parseDouble(PrefHelper.getString(superContext, "pref_longitude"));

                if (latHome != 0 && lonHome != 0) {
                    mapService.CustomAddHome(
                            new LatLng(latHome, lonHome),
                            "Lar, docê lar!",
                            "Sua residência",
                            "Lar"
                    );
                }
            }
        } catch (Exception e) {
            TrackHelper.WriteError(this, "setOnMapClickListener", e.getMessage());
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable(Caccc.TAG, new GenericParcelableHelper<>(cacccList));
    }

    private void MapEvents() {
        try {
            //Nao vamos trabalhar com este metodo
            map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    String infoText;

                    String tag = String.valueOf(marker.getTag());

                    if (tag.equals("Centro")) {
                        infoText = "<b><font color='#1B5E20' size='2'>" + marker.getTitle() + "</font></b> " +
                                "<br/> " +
                                "<font color='#E65100' size='1'> " + marker.getSnippet() + " </font>" +
                                "<br/> " +
                                "<b><i><font color='#9E9E9E' size='1'>Clique para saber mais!</font></i><b/>";
                    } else if (tag.equals("Bazar")) {

                        infoText = "<b><font color='#1B5E20' size='2'>" + marker.getTitle() + "</font></b> " +
                                "<br/> " +
                                "<font color='#E65100' size='1'> " + marker.getSnippet() + " </font>" +
                                "<br/> " +
                                "<b><i><font color='#9E9E9E' size='1'>Clique para conhecer as proximidades ao bazar!</font></i><b/>";
                    } else {

                        infoText = "<b><font color='#1B5E20' size='2'>" + marker.getTitle() + "</font></b> " +
                                "<br /> " +
                                "<font color='#E65100' size='1'> " + marker.getSnippet() + " </font>" +
                                "<br/> ";

                    }

                    ll = new LinearLayout(MapsActivity.this);
                    ll.setPadding(10, 10, 10, 10);
                    ll.setBackgroundColor(Color.WHITE);

                    txtViewWindowInfo = new TextView(getBaseContext());
                    txtViewWindowInfo.setTextSize(10);
                    txtViewWindowInfo.setText(HtmlHelper.fromHtml(infoText));

                    Linkify.addLinks(txtViewWindowInfo, Linkify.PHONE_NUMBERS);
                    ll.addView(txtViewWindowInfo);
                    return ll;
                }
            });

            map.setOnCameraMoveStartedListener((i -> {

            }));

            map.setOnCameraIdleListener(() -> {

            });

            map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {

                    try {

                        TrackHelper.WriteInfo(this, "setOnMapClickListener", latLng.latitude + " - " + latLng.longitude);

                    } catch (Exception e) {

                        TrackHelper.WriteError(this, "setOnMapClickListener", e.getMessage());

                    }

                }
            });

            //Aqui capturar a posição para a localizaçao da instituiçao ou bazar
            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @SuppressWarnings("finally")
                @Override
                public boolean onMarkerClick(Marker marker) {

                    try {
                        markerChange = marker;
                        ChangeRoute(markerChange);
                    } catch (Exception e) {
                        TrackHelper.WriteError(this, "setOnMarkerClickListener", e.getMessage());
                    } finally {
                        return false;
                    }
                }
            });

            map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    TrackHelper.WriteInfo(this, "onInfoWindowClick", marker.getSnippet());
                    caccc = null;
                    bazar = null;
                    String tag = String.valueOf(marker.getTag());

                    if (cacccList.size() > 0) {

                        for (Caccc itemCaccc : cacccList) {

                            String market1 = marker.getTitle().trim();
                            String nome = itemCaccc.getNome();

                            if (tag.equals("Centro") && itemCaccc.getNome().contains(marker.getTitle().trim()))
                                caccc = itemCaccc;

                            if (tag.equals("Bazar")) {

                                for (Bazar itemBazar : itemCaccc.getBazares()) {
                                    String market2 = marker.getSnippet().trim();
                                    String logradouro = itemBazar.getEndereco().getBairro().trim();

                                    if (itemBazar.getEndereco().getBairro().contains(marker.getSnippet().trim()))
                                        bazar = itemBazar;
                                }
                            }
                        }
                    }

                    if (caccc != null && tag.equals("Centro")) {
                        intent = new Intent(superContext, TabsCacccActivity.class);
                        cacccGenericParcelableHelper = new GenericParcelableHelper<>(caccc);
                        bundle = new Bundle();
                        bundle.putParcelable(ConstantHelper.objCaccc, cacccGenericParcelableHelper);
                        intent.putExtra(ConstantHelper.objBundle, bundle);
                        startActivity(intent);
                    } else if (bazar != null && tag.equals("Bazar")) {
                        intent = new Intent(superContext, VisaoRuaActivity.class);
                        bazarGenericParcelableHelper = new GenericParcelableHelper<>(bazar);
                        bundle = new Bundle();
                        bundle.putParcelable(ConstantHelper.objBazar, bazarGenericParcelableHelper);
                        intent.putExtra(ConstantHelper.objBundle, bundle);
                        startActivity(intent);
                    }

                }
            });

        } catch (Exception e) {
            TrackHelper.WriteError(this, "setOnMapClickListener", e.getMessage());
        }
    }

    //***********************************************************************************************

    private void ChangeRoute(Marker marker) {
        try {
            LatLng origin;
            if (marker != null) {
                if (globalLocation != null)
                    origin = new LatLng(globalLocation.getLatitude(), globalLocation.getLongitude());
                else
                    origin = latLng;

                LatLng destination = marker.getPosition();
                mapService.ClearRoute();
                GoogleRoute(origin, destination);
            }
        } catch (Exception e) {
            TrackHelper.WriteError(this, "ChangeRoute", e.getMessage());
        }

    }

    public void GoogleRoute(final LatLng origin, final LatLng destination) {

        new Thread() {
            public void run() {

                String url = String.format(ConstantHelper.urlMapsApi, origin.latitude, origin.longitude, destination.latitude, destination.longitude, ConstantHelper.API_KEY_GOOOGLE);

                try {

                    final String answer = HttpHelper.makeOkHttpCall(url);

                    runOnUiThread(new Runnable() {
                        public void run() {

                            try {

                                listLatLng = mapService.BuildJSONRoute(answer);
                                mapService.DrawRoute(listLatLng);

                            } catch (JSONException e) {
                                TrackHelper.WriteError(this, "setOnMapClickListener", e.getMessage());
                            }
                        }
                    });

                } catch (Exception e) {
                    TrackHelper.WriteError(this, "GoogleRoute", e.getMessage());
                }
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        if (googleApiClient.isConnected())
            googleApiClient.disconnect();
        super.onDestroy();
        this.finish();

    }

    //**************************************LocationListener***************************************

    protected void startLocationUpdates() {

        try {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                return;

            if (!googleApiClient.isConnected())
                return;

            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(ConstantHelper.OneSecond * 10);
            mLocationRequest.setFastestInterval(ConstantHelper.OneSecond);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            globalLocation = this.ProviderGlobalLocation();

        } catch (Exception e) {
            TrackHelper.WriteInfo(this, "startLocationUpdates", String.valueOf(googleApiClient.isConnected()));
        }

    }

    protected void stopLocationUpdates() {
        if (googleApiClient.isConnected())
            fusedLocationProviderClient.removeLocationUpdates(new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    globalLocation = locationResult.getLastLocation();
                }
            });
        else
            TrackHelper.WriteInfo(this, "stopLocationUpdates", String.valueOf(googleApiClient.isConnected()));

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onStart() {
        super.onStart();
        if (!googleApiClient.isConnected())
            googleApiClient.connect();

    }

    @Override
    public void onStop() {
        super.onStop();
        if (googleApiClient.isConnected())
            googleApiClient.disconnect();
    }

    @SuppressWarnings("finally")
    @Nullable
    private Location ProviderGlobalLocation() {

        try {
            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                this.CheckPermissions();

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

            fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    for (Location location : locationResult.getLocations()) {
                        globalLocation = location;
                    }
                }

            }, null);

            if (globalLocation == null) {
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                globalLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, new InternalLocationListener());
            }

            if (globalLocation == null) {
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                globalLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, new InternalLocationListener());
            }

            mapService.SetDeviceLocation(new LatLng(globalLocation.getLatitude(),globalLocation.getLongitude()));

        } catch (Exception e) {
            TrackHelper.WriteError(this, "parseResult", e.getMessage());
        } finally {
            return globalLocation;
        }
    }

    private void CheckPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {

            mapService.SetDeviceLocation(new LatLng(globalLocation.getLatitude(),globalLocation.getLongitude()));

        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    permissionRequest);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
      super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        if (
                (Arrays.asList(permissions).contains("android.permission.FINE_LOCATION") && grantResults[0] != PackageManager.PERMISSION_GRANTED) &&
                        (Arrays.asList(permissions).contains("android.permission.ACCESS_COARSE_LOCATION") && grantResults[1] != PackageManager.PERMISSION_GRANTED)
        )
            Toast.makeText(getApplicationContext(), "Seu local não será demonstrado em localização.", Toast.LENGTH_LONG).show();
        else {
             finish();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                startActivity(getIntent(), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            else
                startActivity(getIntent());
        }

        if (Arrays.asList(permissions).contains("android.permission.CAMERA") && grantResults[0] != PackageManager.PERMISSION_GRANTED)
            Toast.makeText(getApplicationContext(), "Você não poderá atualizar sua foto no perfil.", Toast.LENGTH_LONG).show();

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        TrackHelper.WriteInfo(this, "onConnected", "Connectado ao google");
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        TrackHelper.WriteInfo(this, "onConnectionSuspended", "Conexao google suspensa");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        TrackHelper.WriteInfo(this, "onConnectionFailed", "Conexao falhou");
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    //---------------------------------Async---------------------------------------------------

    private void PutInMap(Collection<Caccc> listCaccc) {
        try {

            for (Caccc caccc : listCaccc) {

                this.AddMapServiceCaccc(caccc);

                if (caccc.getBazares() != null) {
                    for (Bazar bazar : caccc.getBazares())
                        this.AddMapServiceStore(bazar);
                }

            }

        } catch (Exception e) {
            TrackHelper.WriteError(this, "PutInMap", e.getMessage());
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            TrackHelper.WriteInfo(this, "onConfigurationChanged", "ORIENTATION_PORTRAIT");

        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TrackHelper.WriteInfo(this, "onConfigurationChanged", "ORIENTATION_LANDSCAPE");

        }

    }

    private class InternalLocationListener implements android.location.LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                globalLocation = location;
                mapService.SetDeviceLocation(new LatLng(globalLocation.getLatitude(), globalLocation.getLongitude()));
                ChangeRoute(markerChange);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

}
