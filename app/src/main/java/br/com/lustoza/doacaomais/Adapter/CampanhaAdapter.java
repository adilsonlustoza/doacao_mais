package br.com.lustoza.doacaomais.Adapter;

import android.annotation.SuppressLint;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.lustoza.doacaomais.Domain.Campanha;
import br.com.lustoza.doacaomais.Helper.HtmlHelper;
import br.com.lustoza.doacaomais.R;
import br.com.lustoza.doacaomais.Utils.UtilityMethods;

/**
 * Created by Adilson on 26/03/2017.
 */

public class CampanhaAdapter extends RecyclerView.Adapter<CampanhaAdapter.CampanhaViewHolder> {
    private final List<Campanha> campanhaList;
    public CampanhaAdapter(List<Campanha> tabCampanhas) {
        this.campanhaList = tabCampanhas;
    }

    @NotNull
    @Override
    public CampanhaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_campanha, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(parent.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT));
        return new CampanhaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CampanhaViewHolder holder, int position) {
        Campanha campanha = campanhaList.get(position);
        holder.textViewTitle.setText(HtmlHelper.fromHtml(String.format("%s ", campanha.getNome())));
        holder.textViewTipoCampanha.setText(HtmlHelper.fromHtml(String.format(" %s ", UtilityMethods.getTipoCampanha(campanha.getTipoCampanha()))));
        holder.textViewContent.setText(campanha.getDescricao());
        String dataVigencia = "Vigência não informada.";
        if (campanha.getDataInicial() != null && campanha.getDataFinal() != null)
            dataVigencia = "Vigência de  : " + UtilityMethods.ParseDateToString(campanha.getDataInicial()) + "  a " + UtilityMethods.ParseDateToString(campanha.getDataFinal());
        else if (campanha.getDataInicial() == null && campanha.getDataFinal() != null)
            dataVigencia = "Vigência até : " + UtilityMethods.ParseDateToString(campanha.getDataInicial());
        else if (campanha.getDataInicial() != null && campanha.getDataFinal() == null)
            dataVigencia = "Vigência de  : " + UtilityMethods.ParseDateToString(campanha.getDataInicial()) + " a ...";

        holder.textViewData.setText(HtmlHelper.fromHtml(dataVigencia));

        Linkify.addLinks(holder.textViewContent, Linkify.WEB_URLS);

    }

    @Override
    public int getItemCount() {
        return (null != this.campanhaList ? this.campanhaList.size() : 0);
    }

    static class CampanhaViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;
        private final TextView textViewTipoCampanha;
        private final TextView textViewContent;
        private final TextView textViewData;

        public CampanhaViewHolder(View view) {
            super(view);
            this.textViewTitle = view.findViewById(R.id.txtTitle);
            this.textViewTipoCampanha = view.findViewById(R.id.txtTipoCampanha);
            this.textViewContent = view.findViewById(R.id.txtContent);
            this.textViewData = view.findViewById(R.id.txtData);

        }

    }

}
