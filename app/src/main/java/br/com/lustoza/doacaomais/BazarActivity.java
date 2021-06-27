package br.com.lustoza.doacaomais;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import br.com.lustoza.doacaomais.Adapter.BazarAdapter;
import br.com.lustoza.doacaomais.Domain.Bazar;
import br.com.lustoza.doacaomais.Domain.Caccc;
import br.com.lustoza.doacaomais.Helper.ConstantHelper;
import br.com.lustoza.doacaomais.Helper.GenericParcelableHelper;
import br.com.lustoza.doacaomais.Helper.HttpHelper;
import br.com.lustoza.doacaomais.Helper.TrackHelper;
import br.com.lustoza.doacaomais.Interfaces.IOnLoadCallBack;

public class BazarActivity extends _SuperActivity implements IOnLoadCallBack {

    //region ***Variaveis e Propriedades***
    private List<Bazar> bazares;
    private GenericParcelableHelper<Bazar> bazarGenericParcelableHelper;
    private SwipeRefreshLayout swipeRefreshLayout;
    private GenericParcelableHelper<List<Bazar>> genericParcelableHelper;

    //endregion

    //region ***Metodos da Activity***

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bazar);
        this.ConfigureToolbar(ConstantHelper.ToolbarSubTitleBazares);
        this.ConfigureReturnToolbar();
        this.Init(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        searchView = new SearchView(this);
        searchView.setOnQueryTextListener(new ResearchMenu());

        MenuItem itemSearch = menu.add(0, 0, 0, "SearchView");
        itemSearch.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        itemSearch.setIcon(android.R.drawable.ic_menu_search);
        itemSearch.setActionView(searchView);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable(Bazar.TAG, new GenericParcelableHelper<>(bazares));
    }

    //endregion

    private void Init(Bundle savedInstanceState) {

        String url = ConstantHelper.urlWebApiListAllBazar;
        this.SwipeRefreshLayout();
        this.LoadBazar(savedInstanceState, url);

    }

    private void LoadBazar(Bundle savedInstanceState, String url) {

        this.progressBar = findViewById(R.id.progress_bar);
        this.progressBar.setVisibility(View.VISIBLE);

        if (savedInstanceState != null && savedInstanceState.getParcelable(Caccc.TAG) != null)
            genericParcelableHelper = savedInstanceState.getParcelable(Bazar.TAG);

        if (genericParcelableHelper != null && genericParcelableHelper.getValue() != null && genericParcelableHelper.getValue().size() > 0)
            this.Execute(genericParcelableHelper.getValue(), true);
        else
            new HttpHelper(superContext, ConstantHelper.fileListAllBazar).RestDownloadList(url, Bazar.class, this);
        // super.RestDownloadList(url, Bazar.class, this);
        this.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void Execute(List<?> list, boolean keep) {
        try {

            ImageView imageViewConsultaVazia = this.findViewById(R.id.imgConsultaVazia);
            RecyclerView recyclerView = this.findViewById(R.id.recycleViewBazar);
            List<Bazar> viewList;

            if (keep)
                bazares = viewList = (List<Bazar>) list;
            else
                viewList = (List<Bazar>) list;

            if (viewList != null && viewList.size() > 0) {

                BazarAdapter adapter = new BazarAdapter(viewList);

                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setNestedScrollingEnabled(true);
                recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
                recyclerView.setAdapter(adapter);
                imageViewConsultaVazia.setVisibility(View.GONE);

                adapter.setOnItemClickListener(item -> {

                    intent = new Intent(superContext, MapsActivity.class);
                    bazarGenericParcelableHelper = new GenericParcelableHelper<>(item);

                    bundle = new Bundle();
                    bundle.putParcelable(ConstantHelper.objBazar, bazarGenericParcelableHelper);
                    intent.putExtra(ConstantHelper.objBundle, bundle);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
                    else
                        startActivity(intent);
                });

            } else {
                recyclerView.setVisibility(View.GONE);
                imageViewConsultaVazia.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            TrackHelper.WriteError(this, "SwipeRefreshLayout", e.getMessage());
        }
    }

    private void SwipeRefreshLayout() {
        try {
            this.swipeRefreshLayout = findViewById(R.id.swipeToRefresh);
            this.swipeRefreshLayout.setOnRefreshListener(() -> new Handler().postDelayed(() -> swipeRefreshLayout.setRefreshing(false), 1000));
        } catch (Exception e) {
            TrackHelper.WriteError(this, "SwipeRefreshLayout", e.getMessage());
        }
    }

    private class ResearchMenu implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String s) {
            this.ExecuteQuery(s);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            this.ExecuteQuery(s);
            return false;
        }

        private void ExecuteQuery(String s) {
            int count = bazares.size();
            List<Bazar> bazarListKeep = new ArrayList<>();

            if (TextUtils.isEmpty(s))
                Execute(bazares, false);
            else {

                while (count > 0) {
                    Bazar bazar = bazares.get(count - 1);
                    if (bazar.getNome().toLowerCase().contains(s.toLowerCase()))
                        bazarListKeep.add(bazar);
                    count--;
                }

                Execute(bazarListKeep, false);
            }

        }
    }

}
