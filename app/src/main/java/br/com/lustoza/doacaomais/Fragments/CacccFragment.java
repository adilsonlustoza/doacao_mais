package br.com.lustoza.doacaomais.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import br.com.lustoza.doacaomais.Domain.Caccc;
import br.com.lustoza.doacaomais.Domain.Conteudo;
import br.com.lustoza.doacaomais.Helper.ConstantHelper;
import br.com.lustoza.doacaomais.Helper.GenericParcelableHelper;
import br.com.lustoza.doacaomais.Helper.HtmlHelper;
import br.com.lustoza.doacaomais.Helper.HttpHelper;
import br.com.lustoza.doacaomais.Helper.TrackHelper;
import br.com.lustoza.doacaomais.R;

public class CacccFragment extends _SuperFragment {

    private GenericParcelableHelper<Caccc> cacccGenericParcelableHelper;
    private Caccc caccc;
    private NestedScrollView nestedScrollView;

    public CacccFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_caccc, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        this.OnInit();
    }

    private void OnInit() {

        (Objects.requireNonNull(getActivity())).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        bundleArguments = this.getArguments();
        this.nestedScrollView = Objects.requireNonNull(getView()).findViewById(R.id.nestedScrollViewNoticias);
        this.nestedScrollView.setOnClickListener(view -> onDonationPosition());

        if (bundleArguments != null && bundleArguments.getParcelable(ConstantHelper.objCaccc) != null)
            cacccGenericParcelableHelper = bundleArguments.getParcelable(ConstantHelper.objCaccc);

        try {

            if (getArguments() != null) {

                idCentro = cacccGenericParcelableHelper.getValue().getCacccId();
                eMailCentro = cacccGenericParcelableHelper.getValue().getEmail();

                @SuppressLint("DefaultLocale")
                String url = String.format("%s%d", ConstantHelper.urlWebApiConteudoContasPorCaccc, idCentro);

                if (cacccGenericParcelableHelper.getValue() != null && (cacccGenericParcelableHelper.getValue().getConteudos() != null && cacccGenericParcelableHelper.getValue().getConteudos().size() > 0))
                    FillView(cacccGenericParcelableHelper.getValue());
                else
                    CacccLoad(url);
            }
        } catch (Exception e) {
            TrackHelper.WriteError(this, "onActivityCreated", e.getMessage());
        }
    }

    private void CacccLoad(String url) {
        try {
            this.progressBar = Objects.requireNonNull(getView()).findViewById(R.id.progress_bar);
            this.progressBar.setVisibility(View.VISIBLE);

            caccc = (Caccc) new HttpHelper(getContext(), ConstantHelper.fileListarConteudoContasPorCaccc).RestDownloadObj(url, Caccc.class);//super.RestDownloadObj(url, Caccc.class, false);
            cacccGenericParcelableHelper = new GenericParcelableHelper<>(caccc);
            Bundle bundle = new Bundle();
            bundle.putParcelable(ConstantHelper.objCaccc, cacccGenericParcelableHelper);

            if (caccc != null)
                FillView(caccc);
            this.progressBar.setVisibility(View.GONE);
        } catch (Exception e) {
            TrackHelper.WriteError(this, "onDonation", e.getMessage());

        }
    }

    private void onDonationPosition() {
        try {
            nestedScrollView.post(() -> nestedScrollView.fullScroll(View.FOCUS_DOWN));
        } catch (Exception e) {
            TrackHelper.WriteError(this, "onDonation", e.getMessage());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Caccc.TAG, new GenericParcelableHelper<>(caccc));
    }

    private void FillView(Caccc caccc) {
        try {

            TextView lblNomeCentro = Objects.requireNonNull(getView()).findViewById(R.id.lblNomeCentro);
            TextView lblTelefoneCentro = getView().findViewById(R.id.lblTelefoneCentro);
            TextView lblEmailCentro = getView().findViewById(R.id.lblEmailCentro);
            TextView lblEnderecoCentro = getView().findViewById(R.id.lblEnderecoCentro);
            TextView lblHistorico = getView().findViewById(R.id.lblHistorico);
            CardView cardViewBanco = getView().findViewById(R.id.cardViewBanco);

            if (caccc != null) {

                StringBuilder sbCentro = new StringBuilder();
                sbCentro.append(String.format("<font size='14' color='gray' face='verdana'>Nome :</font> %s", caccc.getNome()));
                lblNomeCentro.setText(HtmlHelper.fromHtml(sbCentro.toString()));

                sbCentro = new StringBuilder();
                sbCentro.append(String.format("<font size='14' color='gray' face='verdana'>Telefone :</font> %s ", caccc.getTelefone()));
                lblTelefoneCentro.setText(HtmlHelper.fromHtml(sbCentro.toString()));
                Linkify.addLinks(lblTelefoneCentro, Linkify.PHONE_NUMBERS);

                sbCentro = new StringBuilder();
                sbCentro.append(String.format("<font size='14' color='gray' face='verdana'>E-mail :</font>  %s ", caccc.getEmail()));
                lblEmailCentro.setText(HtmlHelper.fromHtml(sbCentro.toString()));
                Linkify.addLinks(lblEmailCentro, Linkify.EMAIL_ADDRESSES);

                sbCentro = new StringBuilder();
                sbCentro.append("<hr>");
                sbCentro.append("<br/>");
                sbCentro.append(String.format("<font size='14' color='#a9a9a9' face='verdana'>%s</font>", "Endereço"));
                sbCentro.append("<br/><br/>");
                sbCentro.append(String.format("<font size='14' color='gray' face='verdana'>Logradouro :</font> %s,%s", caccc.getEndereco().getLogradouro(), caccc.getEndereco().getNumero()));
                sbCentro.append("<br/>");
                sbCentro.append(String.format("<font size='14' color='gray' face='verdana'>Bairro :</font> %s", caccc.getEndereco().getBairro()));
                sbCentro.append("<br/>");
                sbCentro.append(String.format("<font size='14' color='gray' face='verdana'>Cidade :</font> %s", caccc.getEndereco().getCidade()));
                sbCentro.append("<br/>");
                sbCentro.append(String.format("<font size='14' color='gray' face='verdana'>Estado :</font> %s", caccc.getEndereco().getEstado()));
                sbCentro.append("<br/>");
                sbCentro.append(String.format("<font size='14' color='gray' face='verdana'>Cep :</font> %s", caccc.getEndereco().getCep()));
                sbCentro.append("<br/>");
                lblEnderecoCentro.setText(HtmlHelper.fromHtml(sbCentro.toString()));

                StringBuilder sbConteudo = new StringBuilder();

                for (Conteudo itemConteudo : caccc.getConteudos()) {
                    sbConteudo.append(String.format("<font size='medium' color='gray'>%s</font>", itemConteudo.getTitulo()));
                    sbConteudo.append("<br/><br/>");
                    sbConteudo.append(String.format("%s", itemConteudo.getColuna()));
                    sbConteudo.append("<br/><br/>");

                }

                lblHistorico.setText(HtmlHelper.fromHtml(sbConteudo.toString()));
                Linkify.addLinks(lblHistorico, Linkify.ALL);
                cardViewBanco.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            TrackHelper.WriteError(this, "onControlsValuesAndActions", e.getMessage());

        }

    }

}
