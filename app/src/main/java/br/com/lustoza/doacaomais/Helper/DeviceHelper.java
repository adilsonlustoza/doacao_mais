package br.com.lustoza.doacaomais.Helper;

import android.annotation.SuppressLint;
import android.os.Build;

import br.com.lustoza.doacaomais.Domain.Dispositivo;

public class DeviceHelper {

    private static final Dispositivo dispositivo = new Dispositivo();

    @SuppressLint("HardwareIds")
    public static Dispositivo GetDevice() {

        try {
            dispositivo.setRadioVersion(Build.getRadioVersion());
            dispositivo.setIncremental(Build.VERSION.INCREMENTAL);
            dispositivo.setRelease(Build.VERSION.RELEASE);
            dispositivo.setSerial(Build.SERIAL);
            dispositivo.setBoard(Build.BOARD);
            dispositivo.setBrand(Build.BRAND);
            dispositivo.setBootLoader(Build.BOOTLOADER);
            dispositivo.setDevice(Build.DEVICE);
            dispositivo.setDisplay(Build.DISPLAY);
            dispositivo.setFingerPrint(Build.FINGERPRINT);
            dispositivo.setHost(Build.HOST);
            dispositivo.setDeviceId(Build.ID);
            dispositivo.setModel(Build.MODEL);
            dispositivo.setManufacture(Build.MANUFACTURER);
            dispositivo.setProduct(Build.PRODUCT);
            dispositivo.setUser(Build.USER);
            dispositivo.setVersaoSDK(String.valueOf(Build.VERSION.SDK_INT));
            dispositivo.setCode(Build.VERSION.CODENAME);

        } catch (Exception e) {
            TrackHelper.WriteError(DeviceHelper.class, "GetDevice", e.getMessage());
        }
        return dispositivo;

    }

}
