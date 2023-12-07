package br.com.lustoza.doacaomais;

import android.os.Bundle;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;

import br.com.lustoza.doacaomais.Domain.Bazar;
import br.com.lustoza.doacaomais.Helper.ConstantHelper;
import br.com.lustoza.doacaomais.Helper.GenericParcelableHelper;
import br.com.lustoza.doacaomais.Helper.TrackHelper;

public class VisaoRuaActivity extends _SuperActivity implements OnStreetViewPanoramaReadyCallback {

    private static final String TAG = "VisaoRuaActivity";
    private StreetViewPanoramaFragment streetViewPanoramaFragment;
    private Bundle savedInstanceRecover = null;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_view);
        this.ConfigureToolbar(ConstantHelper.ToolbarSubTitleVisaoDeRua);
        this.ConfigureReturnToolbar();
        if (savedInstanceState != null)
            savedInstanceRecover = savedInstanceState.getBundle(TAG);
        this.Init(savedInstanceRecover);
    }

    private void Init(Bundle savedInstanceState) {
        try {

            bundle = getIntent().getExtras().getBundle(ConstantHelper.objBundle);

            if (bundle != null) {
                double _latitude = ((GenericParcelableHelper<Bazar>) bundle.getParcelable(ConstantHelper.objBazar)).getValue().getEndereco().getLatitude();
                double _longitude = ((GenericParcelableHelper<Bazar>) bundle.getParcelable(ConstantHelper.objBazar)).getValue().getEndereco().getLongitude();
                latLng = new LatLng(_latitude, _longitude);

            } else if (savedInstanceState != null && savedInstanceState.getParcelable(Bazar.TAG) != null) {
                GenericParcelableHelper<Bazar> bazarGenericParcelableHelper = savedInstanceState.getParcelable(Bazar.TAG);
                if (bazarGenericParcelableHelper.getValue() != null)
                    latLng = new LatLng(
                            bazarGenericParcelableHelper.getValue().getEndereco().getLatitude(),
                            bazarGenericParcelableHelper.getValue().getEndereco().getLongitude()
                    );

                streetViewPanoramaFragment.onCreate(savedInstanceState);
            }

            streetViewPanoramaFragment = (StreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.map_street_view);
            streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);

        } catch (Exception e) {
            TrackHelper.WriteError(this, "Init Street View Activity", e.getMessage());
        }
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        try {
            streetViewPanorama.setPosition(latLng);
            streetViewPanorama.setPanningGesturesEnabled(true);
            streetViewPanorama.setUserNavigationEnabled(true);
            streetViewPanorama.setStreetNamesEnabled(true);

            streetViewPanorama.animateTo(
                    new StreetViewPanoramaCamera.Builder().
                            orientation(new StreetViewPanoramaOrientation(20, 20))
                            .zoom(streetViewPanorama.getPanoramaCamera().zoom)
                            .build(), 2000);
        } catch (Exception e) {
            TrackHelper.WriteError(this, "onStreetViewPanoramaReady", e.getMessage());
        }

    }

    @Override
    public void onResume() {
        streetViewPanoramaFragment.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        streetViewPanoramaFragment.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mStreetViewBundle = outState.getBundle(TAG);
        if (mStreetViewBundle == null) {
            mStreetViewBundle = new Bundle();
            outState.putBundle(TAG, mStreetViewBundle);
        }
        streetViewPanoramaFragment.onSaveInstanceState(mStreetViewBundle);
    }
}
