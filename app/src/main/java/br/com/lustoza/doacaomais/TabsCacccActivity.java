package br.com.lustoza.doacaomais;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import br.com.lustoza.doacaomais.Adapter.TabFragmentAdapterContainer;
import br.com.lustoza.doacaomais.Api.CircleTransformPicasso;
import br.com.lustoza.doacaomais.Domain.Caccc;
import br.com.lustoza.doacaomais.Helper.ConstantHelper;
import br.com.lustoza.doacaomais.Helper.GenericParcelableHelper;
import br.com.lustoza.doacaomais.Helper.HtmlHelper;
import br.com.lustoza.doacaomais.Helper.TrackHelper;

public class TabsCacccActivity extends _SuperActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private ViewPager2 viewPager;
    private Bundle bundle;
    private ImageView imageViewCentro;
    private String titulo;
    private String urlThumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        try {
            bundle = getIntent().getExtras().getBundle(ConstantHelper.objBundle);

            if (bundle != null) {
                titulo = ((GenericParcelableHelper<Caccc>) bundle.getParcelable(ConstantHelper.objCaccc)).getValue().getNome();
                urlThumbnail = ((GenericParcelableHelper<Caccc>) bundle.getParcelable(ConstantHelper.objCaccc)).getValue().getUrlImagem();

                this.ConfigureToolbarSuporte();
                this.ConfigureReturnToolbar();
                this.ConfigureAppBarLayout(titulo);
                this.ConfigTabs();
            }

        } catch (Exception e) {
            TrackHelper.WriteError(this, "onCreate", e.getMessage());
        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void ConfigTabs() {
        try {
            TabLayout tabLayout = findViewById(R.id.tab_layout);

            tabLayout.removeAllTabs();
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.sobre).setText(ConstantHelper.TabSobre));
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.campanha).setText(ConstantHelper.TabCampanha));
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.chat).setText(ConstantHelper.TabDonation));

            tabLayout.setTabTextColors(R.color.colorPreto, R.color.colorAccent);
            tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            viewPager = this.findViewById(R.id.pager);
            TabFragmentAdapterContainer tabCacccAdapter = new TabFragmentAdapterContainer(getSupportFragmentManager(), tabLayout.getTabCount(), bundle);
            viewPager.setAdapter(tabCacccAdapter);
            viewPager.addOnLayoutChangeListener((View.OnLayoutChangeListener) new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

        } catch (Exception e) {
            TrackHelper.WriteError(this, "ConfigTabs", e.getMessage());
        }

    }

    private void ConfigureAppBarLayout(final String title) {
        try {

            final Handler handler = new Handler();
            Runnable run = () -> {

                appBarLayout = findViewById(R.id.appBar);
                // appBarLayout.setExpanded(false, true);
                collapsingToolbarLayout = appBarLayout.findViewById(R.id.collapsing);
                collapsingToolbarLayout.setTitle(HtmlHelper.fromHtml("<font color='#FFFFFF'>" + title + "</font>"));
                collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorPreto));
                collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.START);
                collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedAppBar);
                collapsingToolbarLayout.setScrimAnimationDuration(ConstantHelper.OneSecond);

                imageViewCentro = collapsingToolbarLayout.findViewById(R.id.imgCentro);
                if (imageViewCentro != null) {
                    imageViewCentro.setVisibility(View.VISIBLE);
                    imageViewCentro.setAlpha(0f);

                    Picasso.with(superContext).load(urlThumbnail)
                            .fit().transform(new CircleTransformPicasso())
                            .into(imageViewCentro, new Callback() {
                                @Override
                                public void onSuccess() {
                                    imageViewCentro.animate().setDuration(ConstantHelper.OneSecond).alpha(1.0f).start();
                                }

                                @Override
                                public void onError() {

                                }
                            });

                }

                appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {

                });

            };

            handler.postDelayed(run, 1500);
        } catch (Exception e) {
            TrackHelper.WriteError(this, "CollapsingToolbarLayoutClose", e.getMessage());
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            TrackHelper.WriteInfo(this, "onConfigurationChanged", "ORIENTATION_PORTRAIT");
            this.ConfigTabs();

        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TrackHelper.WriteInfo(this, "onConfigurationChanged", "ORIENTATION_LANDSCAPE");
            this.ConfigTabs();

        }

    }

}
