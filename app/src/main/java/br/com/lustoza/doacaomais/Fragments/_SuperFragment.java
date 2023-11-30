package br.com.lustoza.doacaomais.Fragments;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import br.com.lustoza.doacaomais.Domain.Estatistico;
import br.com.lustoza.doacaomais.Domain.Noticia;
import br.com.lustoza.doacaomais.Helper.ConstantHelper;
import br.com.lustoza.doacaomais.Helper.GenericParcelableHelper;
import br.com.lustoza.doacaomais.Helper.HttpHelper;
import br.com.lustoza.doacaomais.Helper.TrackHelper;
import br.com.lustoza.doacaomais.NoticiasActivity;
import br.com.lustoza.doacaomais.R;
import br.com.lustoza.doacaomais.Services.Rede.NetWorkService;

public class _SuperFragment extends Fragment {

    protected Bundle bundleArguments;
    protected int idCentro;
    protected String eMailCentro;
    protected Intent intent;
    protected ProgressBar progressBar;
    private GenericParcelableHelper<Noticia> noticiaGenericParcelableHelper;
    private Bundle bundle;
    private Noticia noticia;
    private PieEntry pieEntry;
    private GenericParcelableHelper<List<Estatistico>> estatisticoGenericParcelableHelper;

    public _SuperFragment() {
        // Required empty public constructor
    }

    //region ***Ciclo de Vida***

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        setRetainInstance(true);



        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final AtomicReference atomicReferenceFinal = new AtomicReference();
        final Handler handler = new Handler();

        try {

            executor.execute(() -> {

                while (!NetWorkService.instance().isEnabledNetWork(getContext())) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        InitPieChart();
                    }
                }, 1);

            });

            try {
                executor.shutdown();
                if (executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.NANOSECONDS))
                    executor.shutdownNow();
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }

        } catch (Exception e) {
            TrackHelper.WriteError(this, "", e.getMessage());
        }

    }

     @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    //endregion

    //region ***Metodos***
    private void InitPieChart() {

        try {

            @SuppressLint("DefaultLocale")
            String url = String.format("%s%d", ConstantHelper.urlWebApiListarEstatisticoPorTipo, 2);
            List<Estatistico> lstEstatistico;

            if (estatisticoGenericParcelableHelper == null || estatisticoGenericParcelableHelper.getValue().size() == 0) {
                lstEstatistico = (List<Estatistico>) new HttpHelper(getContext(), ConstantHelper.fileListarEstatisticoPorTipo).RestDownloadList(url, Estatistico.class, null);// this.RestDownloadList(url, Estatistico.class, null);
                estatisticoGenericParcelableHelper = new GenericParcelableHelper<>(lstEstatistico);
            } else
                lstEstatistico = estatisticoGenericParcelableHelper.getValue();

            PieChart pieChart = requireView().findViewById(R.id.pieChart);

            pieChart.setExtraOffsets(5, 10, 5, 15);
            pieChart.setDragDecelerationFrictionCoef(0.85f);
            pieChart.setCenterText("Epidemologia");
            pieChart.setDrawHoleEnabled(true);
            pieChart.setHoleColor(Color.TRANSPARENT);
            pieChart.setTransparentCircleRadius(15f);
            pieChart.getLegend().setEnabled(false);

            List<PieEntry> yValues = new ArrayList<>();

            for (Estatistico estatistico : lstEstatistico) {
                yValues.add(new PieEntry(estatistico.getValor(), estatistico.getNome()));
            }

            pieChart.animateY(4000, Easing.EasingOption.EaseInOutCubic);

            PieDataSet pieDataSet = new PieDataSet(yValues, "");

            pieDataSet.setSliceSpace(3f);
            pieDataSet.setSelectionShift(10f);
            pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

            Description description = new Description();
            description.setText("Principais tipos de câncer infanto juvenil.");
            description.setTextSize(12.5f);
            pieChart.setDescription(description);

            PieData pieData = new PieData(pieDataSet);
            pieData.setValueTextSize(15f);
            pieData.setValueTextColor(Color.WHITE);
            pieChart.setData(pieData);
            pieChart.setUsePercentValues(true);
            pieChart.setOnChartValueSelectedListener(new InteractionChart());

        } catch (Exception e) {
            TrackHelper.WriteError(this, "", e.getMessage());
        }

    }

    private class InteractionChart implements OnChartValueSelectedListener {

        @Override
        public void onValueSelected(Entry e, Highlight h) {

            if (e instanceof PieEntry) {
                try {
                    pieEntry = (PieEntry) e;

                    new Handler().postDelayed(() -> {
                        intent = new Intent(getContext(), NoticiasActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        noticia = new Noticia();
                        noticia.setTitulo(pieEntry.getLabel());
                        noticiaGenericParcelableHelper = new GenericParcelableHelper<>(noticia);
                        bundle = new Bundle();
                        bundle.putParcelable(ConstantHelper.objNoticia, noticiaGenericParcelableHelper);
                        intent.putExtra(ConstantHelper.objBundle, bundle);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                        else
                            startActivity(intent);

                    }, ConstantHelper.OneSecond);
                } catch (Exception ex) {
                    TrackHelper.WriteError(this, "InteractionChart", ex.getMessage());
                }

            }

        }

        @Override
        public void onNothingSelected() {

        }
    }
    //endregion

}
