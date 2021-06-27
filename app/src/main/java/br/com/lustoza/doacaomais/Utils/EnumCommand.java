package br.com.lustoza.doacaomais.Utils;

/**
 * Created by ubuntu on 4/18/17.
 */

public enum EnumCommand {

    NetWorkEnabledMobile(1), NetWorkEnableWiFi(2), AllConfig(3), CloseWindow(5), Localization(6), ExitApp(7);
    private final int value;

    EnumCommand(int valorOpcao) {
        value = valorOpcao;
    }

    public int getValue() {
        return value;
    }
}
