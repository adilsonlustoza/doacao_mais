package br.com.lustoza.doacaomais;

import android.app.Activity;
import android.content.ComponentName;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import br.com.lustoza.doacaomais.Adapter.NoticiaAdapter;
import br.com.lustoza.doacaomais.Domain.Noticia;
import br.com.lustoza.doacaomais.Helper.ConstantHelper;
import br.com.lustoza.doacaomais.Helper.GenericParcelableHelper;
import br.com.lustoza.doacaomais.Helper.HttpHelper;
import br.com.lustoza.doacaomais.Helper.PrefHelper;
import br.com.lustoza.doacaomais.Helper.TrackHelper;
import br.com.lustoza.doacaomais.Interfaces.IOnLoadCallBack;
import br.com.lustoza.doacaomais.Interfaces.OnNoticiaItemClickListener;
import br.com.lustoza.doacaomais.Utils.UtilityMethods;

public class NoticiasActivity extends _SuperActivity implements View.OnCreateContextMenuListener, IOnLoadCallBack {

    private GenericParcelableHelper<Noticia> noticiaGenericParcelableHelper;
    private GenericParcelableHelper<List<Noticia>> listNoticiaGenericParcelableHelper;
    private List<Noticia> noticiaList;
    private Noticia noticia;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    //region ***Ciclo de Vida***
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia);
        this.ConfigureToolbar(ConstantHelper.ToolbarSubTitleNoticias);
        this.ConfigureReturnToolbar();
        this.Init(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        searchView = new SearchView(this);
        searchView.setOnQueryTextListener(new ResearchMenu());

        if (noticiaGenericParcelableHelper != null) {
            searchView.setQuery(noticiaGenericParcelableHelper.getValue().getTitulo(), false);
            searchView.clearFocus();
        }

        MenuItem itemSearch = menu.add(0, 0, 0, "SearchView");
        itemSearch.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        itemSearch.setIcon(android.R.drawable.ic_menu_search);
        itemSearch.setActionView(searchView);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable(Noticia.TAG, new GenericParcelableHelper<>(noticiaList));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    //endregion

    //region ***Metodos***

    private void Init(Bundle savedInstanceState) {

        try {
            String url = ConstantHelper.urlWebApiListAllNoticia;
            this.SwipeRefreshLayout();
            this.LoadNoticias(savedInstanceState, url);
        } catch (Exception e) {
            TrackHelper.WriteError(this, "onInit", e.getMessage());
        }
    }

    private void LoadNoticias(Bundle savedInstanceState, String url) {

        try {

            this.progressBar = findViewById(R.id.progress_bar);
            this.progressBar.setVisibility(View.VISIBLE);
            bundle = getIntent().getBundleExtra(ConstantHelper.objBundle);


            if (savedInstanceState != null && savedInstanceState.getParcelable(Noticia.TAG) != null)
                listNoticiaGenericParcelableHelper = savedInstanceState.getParcelable(Noticia.TAG);

            if (listNoticiaGenericParcelableHelper != null && listNoticiaGenericParcelableHelper.getValue() != null && listNoticiaGenericParcelableHelper.getValue().size() > 0)
                this.Execute(listNoticiaGenericParcelableHelper.getValue(), true);
            else {
                if (bundle == null && noticiaGenericParcelableHelper == null)
                    new HttpHelper(superContext, ConstantHelper.fileListAllNoticia).RestDownloadList(url, Noticia.class, this); //super.RestDownloadList(url, Noticia.class, this);
                else
                    noticiaList = (List<Noticia>) new HttpHelper(superContext, ConstantHelper.fileListAllNoticia).RestDownloadList(url, Noticia.class, this);
            }

            if (bundle != null && noticiaGenericParcelableHelper == null) {
                noticiaGenericParcelableHelper = bundle.getParcelable(ConstantHelper.objNoticia);
                List<Noticia> noticiaDashboard = new ArrayList<>();

                for (Noticia noticia : noticiaList) {
                    if (noticia.getTitulo().trim().contains(noticiaGenericParcelableHelper.getValue().getTitulo())) {
                        noticiaDashboard.add(noticia);
                    }
                }
                noticiaList = noticiaDashboard;

                this.Execute(noticiaList, true);
            }
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

    @Override
    public void Execute(List<?> list, boolean keep) {
        try {

            ImageView imageViewConsultaVazia = this.findViewById(R.id.imgConsultaVazia);
            recyclerView = this.findViewById(R.id.recycleNoticia);
            List<Noticia> viewList;
            bundle = getIntent().getBundleExtra(ConstantHelper.objBundle);

            if (keep)
                noticiaList = viewList = (List<Noticia>) list;
            else
                viewList = (List<Noticia>) list;

            if (viewList != null && viewList.size() > 0) {

                NoticiaAdapter adapter = new NoticiaAdapter(NoticiasActivity.this, viewList);

                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setNestedScrollingEnabled(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                recyclerView.setAdapter(adapter);
                imageViewConsultaVazia.setVisibility(View.GONE);

                adapter.setOnItemClickListener(new OnNoticiaItemClickListener() {
                    @Override
                    public void onItemClick(Noticia item) {
                        try {
                            noticia = item;
                        } catch (Exception e) {
                            TrackHelper.WriteError(this, "onItemClick", e.getMessage());
                        }

                    }
                });

                String showGesture = PrefHelper.getString(superContext, PrefHelper.PreferenciaNoticia);

                if (bundle==null  && (showGesture==null || showGesture.isEmpty()) )
                      ShowGestureIcon();
                else if(bundle!=null && !bundle.getString("Origin").contains("Chart"))
                     ShowGestureIcon();

            } else {
                recyclerView.setVisibility(View.GONE);
                imageViewConsultaVazia.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            TrackHelper.WriteError(this, "Execute Noticias", e.getMessage());
        }
    }

    private void ShowGestureIcon(){
        try{
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(" ").setSpan(new ImageSpan(this, R.drawable.swipe), builder.length()-1 , builder.length(), 0);
            builder.append("  ").append("Deslize a tela esquerda!");
            Snackbar.make(recyclerView, builder, Snackbar.LENGTH_LONG)
            .setAction("Ocultar", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PrefHelper.setString(superContext, PrefHelper.PreferenciaNoticia,"S");
                }
            }).show();

        } catch (Exception e) {
            TrackHelper.WriteError(this, "Execute Noticias", e.getMessage());
        }
    }

    //endregion

    //region ***Funcoes Especiais***

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

        private void ExecuteQuery(String query) {

            try {

                query = UtilityMethods.RemoveAccent(query.trim().toLowerCase());
                int count = noticiaList.size();
                List<Noticia> noticiasKeep = new ArrayList<>();

                if (!TextUtils.isEmpty(query)) {
                    while (count > 0) {
                        noticia = noticiaList.get(count - 1);
                        if (UtilityMethods.RemoveAccent(noticia.getTitulo().toLowerCase().trim()).contains(query))
                            noticiasKeep.add(noticia);
                        count--;
                    }

                    Execute(noticiasKeep, false);
                } else
                    Execute(noticiaList, true);

            } catch (Exception e) {
                TrackHelper.WriteInfo(this, "ExecuteQuery", e.getMessage());
            }

        }
    }

    //endregion

}



