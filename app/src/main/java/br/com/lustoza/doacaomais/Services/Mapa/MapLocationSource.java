package br.com.lustoza.doacaomais.Services.Mapa;

import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Adilson Frasnelli on 26/12/2015.
 */
public class MapLocationSource implements LocationSource {

    private OnLocationChangedListener listener;

    public void setLocation(LatLng latLng) {
        Location location = new Location(LocationManager.GPS_PROVIDER);
        location.setLatitude(latLng.latitude);
        location.setLongitude(latLng.longitude);
        if (this.listener != null)
            this.listener.onLocationChanged(location);
    }

    @Override
    public void activate(OnLocationChangedListener listener) {

        this.listener = listener;
    }

    @Override
    public void deactivate() {
        this.listener = null;
    }
}