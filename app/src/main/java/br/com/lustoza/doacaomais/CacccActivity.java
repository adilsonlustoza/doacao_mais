package br.com.lustoza.doacaomais;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import br.com.lustoza.doacaomais.Adapter.CacccAdapter;
import br.com.lustoza.doacaomais.Domain.Caccc;
import br.com.lustoza.doacaomais.Helper.ConstantHelper;
import br.com.lustoza.doacaomais.Helper.GenericParcelableHelper;
import br.com.lustoza.doacaomais.Helper.HttpHelper;
import br.com.lustoza.doacaomais.Helper.TrackHelper;
import br.com.lustoza.doacaomais.Interfaces.IOnLoadCallBack;

public class CacccActivity extends _SuperActivity implements IOnLoadCallBack {

    private static final String TAG = "CacccActivity";
    private GenericParcelableHelper<Caccc> cacccGenericParcelableHelper;
    private List<Caccc> cacccList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private GenericParcelableHelper<List<Caccc>> genericParcelableHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instituicao);
        this.ConfigureToolbar(ConstantHelper.ToolbarSubTitleCaccc);
        this.ConfigureReturnToolbar();
        this.Init(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        searchView = new SearchView(this);
        searchView.setOnQueryTextListener(new ResearchMenu());

        MenuItem itemSearch = menu.add(0, 0, 0, R.string.SearchView);
        itemSearch.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        itemSearch.setActionView(searchView);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable(Caccc.TAG, new GenericParcelableHelper<>(cacccList));
    }

    private void Init(Bundle savedInstanceState) {

        String url = ConstantHelper.urlWebApiListAllCaccc;
        this.SwipeRefreshLayout();
        this.LoadCaccc(savedInstanceState, url);

    }

    private void LoadCaccc(Bundle savedInstanceState, String url) {

        try {

            this.progressBar = findViewById(R.id.progress_bar);
            this.progressBar.setVisibility(View.VISIBLE);

            if (savedInstanceState != null && savedInstanceState.getParcelable(Caccc.TAG) != null)
                genericParcelableHelper = savedInstanceState.getParcelable(Caccc.TAG);

            if (genericParcelableHelper != null && genericParcelableHelper.getValue() != null && genericParcelableHelper.getValue().size() > 0)
                this.Execute(genericParcelableHelper.getValue(), true);
            else
                new HttpHelper(superContext, ConstantHelper.fileListAllCaccc).RestDownloadList(url, Caccc.class, this);// super.RestDownloadList(url, Caccc.class, this);

            this.progressBar.setVisibility(View.GONE);
        } catch (Exception e) {
            TrackHelper.WriteError(this, "LoadData", e.getMessage());
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

    public void Execute(List<?> list, boolean keep) {
        try {

            ImageView imageViewConsultaVazia = this.findViewById(R.id.imgConsultaVazia);
            RecyclerView recyclerView = this.findViewById(R.id.recycleInstituicao);
            List<Caccc> viewList;

            if (keep)
                cacccList = viewList = (List<Caccc>) list;
            else
                viewList = (List<Caccc>) list;

            if (viewList != null && viewList.size() > 0) {

                CacccAdapter adapter = new CacccAdapter(viewList);

                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setNestedScrollingEnabled(true);
                recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
                recyclerView.setAdapter(adapter);
                imageViewConsultaVazia.setVisibility(View.GONE);

                adapter.setOnItemClickListener(item -> {

                    intent = new Intent(superContext, TabsCacccActivity.class);
                    cacccGenericParcelableHelper = new GenericParcelableHelper<>(item);

                    bundle = new Bundle();
                    bundle.putParcelable(ConstantHelper.objCaccc, cacccGenericParcelableHelper);
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

            int count = cacccList.size();
            List<Caccc> cacccListKeep = new ArrayList<>();

            if (TextUtils.isEmpty(s))
                Execute(cacccList, false);
            else {

                while (count > 0) {
                    Caccc caccc = cacccList.get(count - 1);
                    if (caccc.getNome().toLowerCase().contains(s.toLowerCase()))
                        cacccListKeep.add(caccc);
                    count--;
                }

                Execute(cacccListKeep, false);
            }

        }
    }

}
