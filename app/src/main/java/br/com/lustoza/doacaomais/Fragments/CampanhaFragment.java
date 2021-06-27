package br.com.lustoza.doacaomais.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.lustoza.doacaomais.Adapter.CampanhaAdapter;
import br.com.lustoza.doacaomais.Domain.Caccc;
import br.com.lustoza.doacaomais.Domain.Campanha;
import br.com.lustoza.doacaomais.Helper.ConstantHelper;
import br.com.lustoza.doacaomais.Helper.GenericParcelableHelper;
import br.com.lustoza.doacaomais.Helper.HttpHelper;
import br.com.lustoza.doacaomais.Helper.TrackHelper;
import br.com.lustoza.doacaomais.Interfaces.IOnLoadCallBack;
import br.com.lustoza.doacaomais.R;

public class CampanhaFragment extends _SuperFragment implements IOnLoadCallBack {

    private GenericParcelableHelper<Caccc> cacccGenericParcelableHelper;
    private List<Campanha> campanhas;
    private SwipeRefreshLayout _swipeRefreshLayout;
    private GenericParcelableHelper<List<Campanha>> genericParcelableHelper;

    public CampanhaFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_campanha, container, false);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.Init(bundle);
    }

    private void Init(Bundle bundle) {
        try {

            (Objects.requireNonNull(getActivity())).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

            this.SwipeRefreshLayout();

            bundleArguments = this.getArguments();
            if (bundleArguments != null)
                cacccGenericParcelableHelper = bundleArguments.getParcelable(ConstantHelper.objCaccc);

            if (getArguments() != null) {

                idCentro = cacccGenericParcelableHelper.getValue().getCacccId();
                eMailCentro = cacccGenericParcelableHelper.getValue().getEmail();
                String url = String.format("%s", ConstantHelper.urlWebApiListAllCampanhas);

                if (bundle != null && bundle.getParcelable(Campanha.TAG) != null)
                    genericParcelableHelper = bundle.getParcelable(Campanha.TAG);

                this.CampanhasLoad(url);

            }
        } catch (Exception e) {
            TrackHelper.WriteError(this, "OnInit", e.getMessage());
        }

    }

    private void CampanhasLoad(String url) {

        this.progressBar = Objects.requireNonNull(getView()).findViewById(R.id.progress_bar);
        this.progressBar.setVisibility(View.VISIBLE);

        if (genericParcelableHelper != null && genericParcelableHelper.getValue() != null && (genericParcelableHelper.getValue()).size() > 0)
            this.Execute(genericParcelableHelper.getValue(), true);
        else
            new HttpHelper(getContext(), ConstantHelper.fileListAllCampanhas).RestDownloadList(url, Campanha.class, this);
        //super.RestDownloadList(url, Campanha.class, this);

        this.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void SwipeRefreshLayout() {
        try {
            this._swipeRefreshLayout = Objects.requireNonNull(getView()).findViewById(R.id.swipeToRefresh);
            this._swipeRefreshLayout.setOnRefreshListener(() -> new Handler().postDelayed(() -> _swipeRefreshLayout.setRefreshing(false), 1000));
        } catch (Exception e) {
            TrackHelper.WriteError(this, "SwipeRefreshLayout", e.getMessage());
        }
    }

    @Override
    public void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Campanha.TAG, new GenericParcelableHelper<>(campanhas));
    }

    @Override
    public void Execute(List<?> list, boolean keep) {

        ImageView imageViewConsultaVazia = Objects.requireNonNull(getView()).findViewById(R.id.imgConsultaVazia);
        RecyclerView recyclerView = getView().findViewById(R.id.recycleCampanha);
        List<Campanha> viewList;

        if (keep)
            campanhas = viewList = (List<Campanha>) list;
        else
            viewList = (List<Campanha>) list;

        if (viewList != null && viewList.size() > 0) {

            List<Campanha> filterCampanhas = this.CampanhaFilter(idCentro);

            imageViewConsultaVazia.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            CampanhaAdapter adapter = new CampanhaAdapter(filterCampanhas);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);

        } else {
            recyclerView.setVisibility(View.GONE);
            imageViewConsultaVazia.setVisibility(View.VISIBLE);
        }
    }

    private List<Campanha> CampanhaFilter(int idCentro) {
        List<Campanha> filterList = null;
        if (campanhas.size() > 1) {
            filterList = new ArrayList<>();

            for (Campanha campanha : campanhas) {
                if (campanha.getCacccId() == idCentro)
                    filterList.add(campanha);
            }
        }
        return filterList;
    }

}



