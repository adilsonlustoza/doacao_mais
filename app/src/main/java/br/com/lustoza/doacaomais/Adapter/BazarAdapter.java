package br.com.lustoza.doacaomais.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.lustoza.doacaomais.Domain.Bazar;
import br.com.lustoza.doacaomais.Helper.HtmlHelper;
import br.com.lustoza.doacaomais.Helper.TrackHelper;
import br.com.lustoza.doacaomais.Interfaces.OnDoacaoItemClickListener;
import br.com.lustoza.doacaomais.R;

/**
 * Created by Adilson on 26/03/2017.
 */

public class BazarAdapter extends RecyclerView.Adapter<BazarAdapter.DoacaoViewHolder> {

    private final List<Bazar> bazarList;
    private OnDoacaoItemClickListener onItemClickListener;

    public BazarAdapter(List<Bazar> bazarList) {
        this.bazarList = bazarList;

    }

    @NotNull
    @Override
    public DoacaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_bazar, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(parent.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT));
        return new DoacaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull final DoacaoViewHolder holder, int position) {
        try {

            Context context = holder.imageView.getContext();
            Bazar bazar = bazarList.get(position);

            if (!TextUtils.isEmpty(bazar.getUrlImagem()))
                Picasso.with(context).load(bazar.getUrlImagem()).placeholder(R.drawable.progress_animation).error(R.drawable.arquivo_nao_encontrado).fit().into(holder.imageView);

            holder.textViewTitle.setText(HtmlHelper.fromHtml(bazar.getNome()));
            holder.textBairro.setText(HtmlHelper.fromHtml(bazar.getEndereco().getBairro()));
            holder.textViewDescription.setText(HtmlHelper.fromHtml(bazar.getInformacao()));

            View.OnClickListener listener = v -> onItemClickListener.onItemClick(bazarList.get(holder.getAdapterPosition()));

            holder.cardViewBazar.setOnClickListener(listener);

        } catch (Exception e) {
            TrackHelper.WriteError(this, "onBindViewHolder", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return (null != this.bazarList ? this.bazarList.size() : 0);
    }

    public void setOnItemClickListener(OnDoacaoItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, Bazar data) {
        bazarList.add(position, data);
        notifyItemInserted(position);
    }

    class DoacaoViewHolder extends RecyclerView.ViewHolder {

        final ImageView imageView;
        final TextView textViewTitle;
        final TextView textBairro;
        final TextView textViewDescription;
        final CardView cardViewBazar;
        final ImageView imageViewAction;

        public DoacaoViewHolder(View view) {
            super(view);

            imageView = view.findViewById(R.id.thumbnail);
            textViewTitle = view.findViewById(R.id.title);
            textBairro = view.findViewById(R.id.txtBairro);
            textViewDescription = view.findViewById(R.id.description);
            imageViewAction = view.findViewById(R.id.imgPin);
            cardViewBazar = view.findViewById(R.id.cardViewBazar);

        }
    }

}
