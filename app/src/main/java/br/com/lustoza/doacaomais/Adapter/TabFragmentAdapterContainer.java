package br.com.lustoza.doacaomais.Adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import br.com.lustoza.doacaomais.Fragments.CacccFragment;
import br.com.lustoza.doacaomais.Fragments.CampanhaFragment;
import br.com.lustoza.doacaomais.Fragments.DoacaoFragment;

public class TabFragmentAdapterContainer extends FragmentStatePagerAdapter {

    private final int mNumOfTabs;
    private final Bundle bundle;

    public TabFragmentAdapterContainer(FragmentManager fm, int NumOfTabs, @Nullable Bundle bundle) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.bundle = bundle;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new CacccFragment();
                fragment.setArguments(bundle);
                break;
            case 1:
                fragment = new CampanhaFragment();
                fragment.setArguments(bundle);
                break;
            case 2:
                fragment = new DoacaoFragment();
                fragment.setArguments(bundle);
                break;

            default:
                fragment = null;
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}