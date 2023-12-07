package br.com.lustoza.doacaomais.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import br.com.lustoza.doacaomais.Fragments.CacccFragment;
import br.com.lustoza.doacaomais.Fragments.CampanhaFragment;
import br.com.lustoza.doacaomais.Fragments.DoacaoFragment;

public class TabFragmentAdapterContainer extends FragmentStateAdapter {
    private final int mNumOfTabs;
    private final Bundle bundle;
    public TabFragmentAdapterContainer(FragmentManager fm, int NumOfTabs, @Nullable Bundle bundle) {
        super(fm.getPrimaryNavigationFragment());
        this.mNumOfTabs = NumOfTabs;
        this.bundle = bundle;
    }
/*
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

   /* @Override
    public int getCount() {
        return mNumOfTabs;
    }*/

    @NonNull
    @Override
    public Fragment createFragment(int position) {
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
    public int getItemCount() {
        return mNumOfTabs;
    }
}