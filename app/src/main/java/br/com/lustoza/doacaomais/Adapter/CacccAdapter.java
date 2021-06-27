package br.com.lustoza.doacaomais.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.lustoza.doacaomais.Api.CircleTransformPicasso;
import br.com.lustoza.doacaomais.Domain.Caccc;
import br.com.lustoza.doacaomais.Helper.HtmlHelper;
import br.com.lustoza.doacaomais.Interfaces.OnInstituicaoItemClickListener;
import br.com.lustoza.doacaomais.R;

/**
 * Created by Adilson on 26/03/2017.
 */

public class CacccAdapter extends RecyclerView.Adapter<CacccAdapter.InstituicaoViewHolder> {

    private final List<Caccc> cacccList;
    private Caccc caccc;
    private OnInstituicaoItemClickListener onItemClickListener;

    public CacccAdapter(List<Caccc> instituicoes) {
        this.cacccList = instituicoes;
    }

    @NotNull
    @Override
    public CacccAdapter.InstituicaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_instituicao, null);
        return new InstituicaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final InstituicaoViewHolder holder, int position) {
        Context context = holder.imageView.getContext();
        caccc = cacccList.get(position);

        if (!TextUtils.isEmpty(caccc.getUrlImagem()))
            Picasso.with(context).load(caccc.getUrlImagem()).placeholder(R.drawable.progress_animation).error(R.drawable.arquivo_nao_encontrado).fit().transform(new CircleTransformPicasso()).into(holder.imageView);

        holder.textView.setText(HtmlHelper.fromHtml(caccc.getNome()));
        holder.textViewData.setText(HtmlHelper.fromHtml(String.valueOf(caccc.getEmail())));
        Linkify.addLinks(holder.textViewData, Linkify.EMAIL_ADDRESSES);

        View.OnClickListener listener = v -> {
            caccc = cacccList.get(holder.getAdapterPosition());
            onItemClickListener.onItemClick(caccc);
        };

        holder.cardView.setOnClickListener(listener);

    }

    @Override
    public int getItemCount() {
        return (null != this.cacccList ? this.cacccList.size() : 0);
    }

    public void setOnItemClickListener(OnInstituicaoItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    static class InstituicaoViewHolder extends RecyclerView.ViewHolder {

        protected ImageView imageView;
        protected TextView textView;
        protected TextView textViewData;
        protected ImageView imageViewAction;
        protected CardView cardView;

        public InstituicaoViewHolder(View view) {
            super(view);

            this.imageView = view.findViewById(R.id.thumbnail);
            this.textView = view.findViewById(R.id.title);
            this.textViewData = view.findViewById(R.id.txtData);
            this.imageViewAction = view.findViewById(R.id.imgGoTo);
            this.cardView = view.findViewById(R.id.cardInstituicao);

        }
    }
}
