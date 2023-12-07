package br.com.lustoza.doacaomais;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import br.com.lustoza.doacaomais.Helper.ConstantHelper;


public class PreferenciaActivity extends _SuperActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencia);
        this.ConfigureToolbar(ConstantHelper.ToolbarSubTitlePreferencia);
        this.ConfigureReturnToolbar();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_Preference, new SettingsFragment())
                .commit();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @SuppressWarnings("deprecation")
    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }

    }
}


