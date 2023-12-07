package br.com.lustoza.doacaomais;

import android.os.Bundle;

import br.com.lustoza.doacaomais.Helper.ConstantHelper;

public class AjudaActivity extends _SuperActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuda);
        this.ConfigureToolbar(ConstantHelper.ToolbarSubTitleAjuda);
        this.ConfigureReturnToolbar();
    }

}
