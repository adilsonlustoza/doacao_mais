package br.com.lustoza.doacaomais.Utils;

import android.app.Application;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Adilson on 10/01/2018.
 */

public class UtilApplication<T> extends Application {
    private Collection<T> applicationList;

    @Override
    public void onCreate() {
        super.onCreate();

        if (applicationList == null)
            applicationList = new ArrayList<>();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        applicationList.clear();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    public Collection<T> getList() {
        return applicationList;
    }

    public void setList(Collection<T> _applicationList) {
        this.applicationList = _applicationList;
    }
}
