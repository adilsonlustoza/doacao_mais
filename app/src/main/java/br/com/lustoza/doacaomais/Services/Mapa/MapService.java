package br.com.lustoza.doacaomais.Services.Mapa;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Geocoder;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import br.com.lustoza.doacaomais.Domain.ObjectValue.Endereco;
import br.com.lustoza.doacaomais.Helper.TrackHelper;
import br.com.lustoza.doacaomais.R;

/**
 * Created by Adilson on 02/01/2016.
 */
public class MapService {

    GoogleMap map;
    Marker marker;
    MarkerOptions markerOptions;
    CameraUpdate cameraUpdate;
    CameraPosition cameraPosition;
    double varDistance;
    Context context;
    Polyline polyline = null;
    Geocoder geocoder;

    public MapService(Context context) {
        this.context = context;
    }

    public MapService(GoogleMap googleMap, Context context) {
        this.map = googleMap;
        this.context = context;
    }

    public void CustomAddMarkerCaccc(LatLng latLng, String title, String snippet, String tag, Bitmap pinCaccc) {
        try {

            marker = null;
            markerOptions = new MarkerOptions();
            markerOptions.position(latLng).title(title).snippet(snippet).draggable(true);
            if (pinCaccc.getByteCount() == 0)
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_inst_32));
            else
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(pinCaccc));
            marker = map.addMarker(markerOptions);
            marker.setTag(tag);
        } catch (Exception ex) {
            Log.e("LogError", "Add Marker : " + ex.getMessage());
        }

    }

    public void CustomAddMarkerStore(LatLng latLng, String title, String snippet, String tag) {
        try {
            marker = null;
            markerOptions = new MarkerOptions();
            markerOptions.position(latLng).title(title).snippet(snippet).draggable(true);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_venda_rounded_24));
            marker = this.map.addMarker(markerOptions);
            marker.setTag(tag);

        } catch (Exception ex) {
            Log.e("LogError", "Add Marker : " + ex.getMessage());
        }

    }

    public void CustomAddHome(LatLng latLng, String title, String snippet, String tag) {
        try {
            marker = null;
            markerOptions = new MarkerOptions();
            markerOptions.position(latLng).title(title).snippet(snippet).draggable(false);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_house_point_32));
            marker = this.map.addMarker(markerOptions);
            marker.setTag(tag);

        } catch (Exception ex) {
            Log.e("LogError", "Add Marker : " + ex.getMessage());
        }

    }

    public void ClearRoute() {
        try {
            if (this.polyline != null)
                this.polyline.remove();
        } catch (Exception e) {
            Log.e("ClearRoute", "ClearRoute Error" + e.getMessage());
        }
    }

    public void DrawRoute(List<LatLng> listLatLng) {

        try {

            polyline = null;
            PolylineOptions polylineOptions;

            polylineOptions = new PolylineOptions();

            for (int i = 0; i < listLatLng.size(); i++)
                polylineOptions.add(listLatLng.get(i))
                        .width(2)
                        .color(Color.GREEN)
                        .geodesic(true);

            polylineOptions.color(R.color.colorPrimary).width(4);
            polyline = this.map.addPolyline(polylineOptions);
            polyline.setGeodesic(true);
            polyline.setVisible(true);

        } catch (Exception e) {
            Log.e("DrawRoute", "DrawRoute Error : " + e.getMessage());
        }
    }

    public void ConfigCameraPosition(LatLng latLng) {

        //Posiciona a camera
        cameraPosition = new CameraPosition
                .Builder()
                .target(latLng)
                .zoom(14)
                .bearing(90)
                .tilt(30)
                .build();

        //Cria a Camera
        cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);

        map.animateCamera(cameraUpdate, 2000, null);
    }

    public void SetDeviceLocation(LatLng latLng) {
        try {

            if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);

        } catch (Exception ex) {
            Log.e("SetDeviceLocation", "SetDeviceLocation : " + ex.getMessage());
        }
    }

    public List<LatLng> BuildJSONRoute(String json) throws JSONException {

        JSONObject result = new JSONObject(json);

        JSONArray routes = result.getJSONArray("routes");

        varDistance = routes.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("distance").getInt("value");

        JSONArray steps = routes.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps");

        List<LatLng> lines = new ArrayList<>();

        for (int i = 0; i < steps.length(); i++) {

            String polyline = steps.getJSONObject(i).getJSONObject("polyline").getString("points");
            lines.addAll(DecodePolyline(polyline));
        }

        return (lines);
    }

    //Methodos Obtidos Na Web para Auxiliar
    @SuppressWarnings("rawtypes")
    public List<LatLng> DecodePolyline(String encoded) {

        List<LatLng> listPoints = new ArrayList();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)), (((double) lng / 1E5)));
            //  Log.i("Script", "POL: LAT: " + p.latitude + " | LNG: " + p.longitude);
            listPoints.add(p);
        }
        return listPoints;
    }

    public List<Endereco> ConfirmarEndereco(final Endereco enderecoUser) {

        geocoder = new Geocoder(context, new Locale("pt", "BR"));

        String seachAdress = enderecoUser.toString();

        try {

            List<android.location.Address> addressList = null;
            List<Endereco> enderecoUsersList = null;

            try {
                addressList = geocoder.getFromLocationName(seachAdress, 10);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (Objects.requireNonNull(addressList).size() > 0) {
                enderecoUsersList = new ArrayList<>();

                for (int i = 0; i < addressList.size(); i++) {

                    Endereco user = new Endereco();

                    user.setEstado(addressList.get(i).getAdminArea());
                    user.setCidade(addressList.get(i).getSubAdminArea());
                    user.setBairro(addressList.get(i).getSubLocality());
                    user.setNumero(addressList.get(i).getSubThoroughfare());

                    if (TextUtils.isEmpty(addressList.get(i).getSubThoroughfare()))
                        user.setLogradouro(addressList.get(i).getThoroughfare());
                    else
                        user.setLogradouro(addressList.get(i).getThoroughfare() + ", " + addressList.get(i).getSubThoroughfare());

                    if (!TextUtils.isEmpty(addressList.get(i).getPostalCode()))
                        user.setCep(addressList.get(i).getPostalCode());

                    user.setLongitude(addressList.get(i).getLongitude());
                    user.setLatitude(addressList.get(i).getLatitude());
                    user.setPais(addressList.get(i).getCountryName());
                    user.setPaisSigla(addressList.get(i).getCountryCode());

                    enderecoUsersList.add(user);
                }

            }

            return enderecoUsersList;

        } catch (Exception e) {
            TrackHelper.WriteError(this, "GetLocation", e.getMessage());

        }
        return null;

    }

}
