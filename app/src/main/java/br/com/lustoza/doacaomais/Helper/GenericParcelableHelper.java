package br.com.lustoza.doacaomais.Helper;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Adilson on 28/10/2017.
 */

public class GenericParcelableHelper<T> implements Parcelable {

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator<GenericParcelableHelper> CREATOR = new Parcelable.Creator<GenericParcelableHelper>() {

        public GenericParcelableHelper createFromParcel(Parcel in) {
            return new GenericParcelableHelper(in);
        }

        public GenericParcelableHelper[] newArray(int size) {
            return new GenericParcelableHelper[size];
        }
    };
    private static ClassLoader mClassLoader;
    private T mValue;

    public GenericParcelableHelper(T value) {
        this.mValue = value;
        if (this.mValue != null)
            GenericParcelableHelper.mClassLoader = value.getClass().getClassLoader();
    }

    private GenericParcelableHelper(Parcel parcelIn) {
        try {
            //reading the passed value
            mValue = (T) parcelIn.readValue(GenericParcelableHelper.mClassLoader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcelOut, int flags) {
        parcelOut.writeValue(mValue);
    }

    //generic method of obtaining the parcel value
    public T getValue() {
        return mValue;
    }
}
