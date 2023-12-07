package br.com.lustoza.doacaomais.Services.Rede;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.fragment.app.FragmentActivity;

import br.com.lustoza.doacaomais.Helper.TrackHelper;

/**
 * Created by ubuntu on 4/19/17.
 */

public class NetWorkService extends FragmentActivity {

    private static NetWorkService netWorkService;

    private NetWorkService() {
    }

    public static NetWorkService instance() {
        if (netWorkService == null)
            netWorkService = new NetWorkService();
        return netWorkService;
    }

    public boolean isEnabledNetWork(Context context) {
        try {

            boolean ret = false;

            if (context != null) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isAvailable()) {
                    if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                        ret = true;
                    else
                        ret = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;

                }

                return ret;
            }
        } catch (Exception e) {
            TrackHelper.WriteError(this, "EnabledNetWork", e.getMessage());
        }

        return false;
    }

    public boolean isEnabledLocation(Context context) {
        try {
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            TrackHelper.WriteError(this, "isEnabledLocation", e.getMessage());
        }

        return false;
    }

}
