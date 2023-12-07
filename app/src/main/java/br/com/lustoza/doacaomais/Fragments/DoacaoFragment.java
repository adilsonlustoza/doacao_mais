package br.com.lustoza.doacaomais.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import br.com.lustoza.doacaomais.Domain.Caccc;
import br.com.lustoza.doacaomais.Domain.ContaBancaria;
import br.com.lustoza.doacaomais.Domain.ObjectValue.TpDoacao;
import br.com.lustoza.doacaomais.Helper.ConstantHelper;
import br.com.lustoza.doacaomais.Helper.GenericParcelableHelper;
import br.com.lustoza.doacaomais.Helper.HtmlHelper;
import br.com.lustoza.doacaomais.Helper.HttpHelper;
import br.com.lustoza.doacaomais.Helper.TrackHelper;
import br.com.lustoza.doacaomais.R;
import br.com.lustoza.doacaomais.WebViewActivity;

public class DoacaoFragment extends _SuperFragment {

    private GenericParcelableHelper<Caccc> cacccGenericParcelableHelper;
    private Caccc caccc = null;
    private NestedScrollView nestedScrollViewNoticias;

    public DoacaoFragment() {
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
        return inflater.inflate(R.layout.fragment_doacao, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        this.OnInit();
    }

    private void OnInit() {

        (requireActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        bundleArguments = this.getArguments();
        this.nestedScrollViewNoticias = requireView().findViewById(R.id.nestedScrollViewNoticias);
        this.nestedScrollViewNoticias.setOnClickListener(view -> onDonationPosition());
        if (bundleArguments != null)
            cacccGenericParcelableHelper = bundleArguments.getParcelable(ConstantHelper.objCaccc);

        try {

            if (getArguments() != null) {

                idCentro = cacccGenericParcelableHelper.getValue().getCacccId();
                eMailCentro = cacccGenericParcelableHelper.getValue().getEmail();
                caccc = cacccGenericParcelableHelper.getValue();

                @SuppressLint("DefaultLocale")
                String url = String.format("%s%d", ConstantHelper.urlWebApiConteudoContasPorCaccc, idCentro);

                if (bundleArguments != null && bundleArguments.getParcelable(ConstantHelper.objCaccc) != null)
                    cacccGenericParcelableHelper = bundleArguments.getParcelable(ConstantHelper.objCaccc);

                this.DoacaoLoad(url);

            }
        } catch (Exception e) {
            TrackHelper.WriteError(this, "OnInit", e.getMessage());
        }
    }

    private void DoacaoLoad(String url) {
        try {
            this.progressBar = requireView().findViewById(R.id.progress_bar);
            this.progressBar.setVisibility(View.VISIBLE);
            if (cacccGenericParcelableHelper.getValue() != null && (cacccGenericParcelableHelper.getValue().getConteudos() != null && cacccGenericParcelableHelper.getValue().getConteudos().size() > 0))
                FillBankData(cacccGenericParcelableHelper.getValue());
            else
                BankDataLoad(url);

            this.progressBar.setVisibility(View.GONE);
        } catch (Exception e) {
            TrackHelper.WriteError(this, "LoadData", e.getMessage());
        }
    }

    private void onDonationPosition() {
        try {

            nestedScrollViewNoticias.post(() -> nestedScrollViewNoticias.fullScroll(View.FOCUS_DOWN));
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

    private void FillBankData(Caccc caccc) {
        try {

            TextView lblDadosBancarios = requireView().findViewById(R.id.lblDadosBancarios);
            ImageView imageViewPagSeguro = requireView().findViewById(R.id.imgPagSeguro);
            ImageView imageViewPayPal = requireView().findViewById(R.id.imgPayPay);
            CardView cardViewIntegracao = requireView().findViewById(R.id.cardIntegracao);

            if (caccc != null) {

                StringBuilder sbContaBancaria = new StringBuilder();

                for (ContaBancaria itemConta : caccc.getContasBancarias()) {
                    sbContaBancaria.append(String.format("<font size='14' color='gray' face='verdana'>Banco :</font> %s", itemConta.getNomeBanco()));
                    sbContaBancaria.append("<br/>");
                    sbContaBancaria.append(String.format("<font size='14' color='gray' face='verdana'>Agencia :</font> %s", itemConta.getAgencia()));
                    sbContaBancaria.append("<br/>");
                    sbContaBancaria.append(String.format("<font size='14' color='gray' face='verdana'>Conta :</font> %s", itemConta.getConta()));
                    sbContaBancaria.append("<br/>");
                    sbContaBancaria.append(String.format("<font size='14' color='gray' face='verdana'>Beneficiário :</font> %s", itemConta.getBeneficiario()));
                    sbContaBancaria.append("<br/><br/>");
                }

                lblDadosBancarios.setText(HtmlHelper.fromHtml(sbContaBancaria.toString()));

            }

            if (Objects.requireNonNull(caccc).getEnumTipoDoacao() == TpDoacao.PagSeguro) {
                imageViewPagSeguro.setVisibility(View.VISIBLE);
                imageViewPayPal.setVisibility(View.GONE);
                cardViewIntegracao.setVisibility(View.VISIBLE);
            } else if (Objects.requireNonNull(caccc).getEnumTipoDoacao() == TpDoacao.PayPal) {
                imageViewPayPal.setVisibility(View.VISIBLE);
                imageViewPagSeguro.setVisibility(View.GONE);
                cardViewIntegracao.setVisibility(View.VISIBLE);
            } else if (Objects.requireNonNull(caccc).getEnumTipoDoacao() == TpDoacao.PagSeguro_PayPal) {
                imageViewPagSeguro.setVisibility(View.VISIBLE);
                imageViewPayPal.setVisibility(View.VISIBLE);
                cardViewIntegracao.setVisibility(View.VISIBLE);
            } else {
                cardViewIntegracao.setVisibility(View.GONE);
            }

            imageViewPagSeguro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        intent = new Intent(getActivity(), WebViewActivity.class);
                        bundleArguments = new Bundle();
                        caccc.setEnumTipoDoacao(TpDoacao.PagSeguro.valor);
                        bundleArguments.putParcelable(ConstantHelper.objCaccc, new GenericParcelableHelper<>(caccc));
                        intent.putExtra(ConstantHelper.objBundle, bundleArguments);
                        startActivity(intent);
                    } catch (Exception e) {
                        TrackHelper.WriteError(this, "onActivityCreated", e.getMessage());
                    }
                }
            });

            imageViewPayPal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        intent = new Intent(getActivity(), WebViewActivity.class);
                        bundleArguments = new Bundle();
                        caccc.setEnumTipoDoacao(TpDoacao.PayPal.valor);
                        bundleArguments.putParcelable(ConstantHelper.objCaccc, new GenericParcelableHelper<>(caccc));
                        intent.putExtra(ConstantHelper.objBundle, bundleArguments);
                        startActivity(intent);
                    } catch (Exception e) {
                        TrackHelper.WriteError(this, "onActivityCreated", e.getMessage());
                    }
                }
            });

        } catch (Exception e) {
            TrackHelper.WriteError(this, "onControlsValuesAndActions", e.getMessage());

        }

    }

    private void BankDataLoad(String url) {
        try {
            Caccc caccc = (Caccc) new HttpHelper(getContext(), ConstantHelper.fileListarConteudoContasPorCaccc).RestDownloadObj(url, Caccc.class); //super.RestDownloadObj(url, Caccc.class,false);
            if (caccc != null)
                FillBankData(caccc);
        } catch (Exception e) {
            TrackHelper.WriteError(this, "onDonation", e.getMessage());

        }
    }



}



