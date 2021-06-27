package br.com.lustoza.doacaomais.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

import br.com.lustoza.doacaomais.Domain.Noticia;
import br.com.lustoza.doacaomais.Helper.HtmlHelper;
import br.com.lustoza.doacaomais.Interfaces.OnNoticiaItemClickListener;
import br.com.lustoza.doacaomais.R;
import br.com.lustoza.doacaomais.Utils.UtilityMethods;

/**
 * Created by Adilson on 26/03/2017.
 */

public class NoticiaAdapter extends RecyclerView.Adapter<NoticiaAdapter.NoticiaViewHolder> {

    private final List<Noticia> noticiaList;
    private Noticia noticia;
    private OnNoticiaItemClickListener onItemClickListener;
    private Context context;

    public NoticiaAdapter(Context context, List<Noticia> noticias) {
        this.noticiaList = noticias;
        this.context = context;
    }

    @NotNull
    @Override
    public NoticiaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_noticia, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(parent.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT));
        return new NoticiaViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final NoticiaViewHolder holder, int position) {

        context = holder.imageView.getContext();
        noticia = noticiaList.get(position);
        //Render image using Picasso library
        if (!TextUtils.isEmpty(noticia.getUrlImagem()))
            Picasso.with(context).load(noticia.getUrlImagem()).placeholder(R.drawable.progress_animation).error(R.drawable.arquivo_nao_encontrado).fit().into(holder.imageView);
        //Setting text view title
        holder.textViewTitle.setText(HtmlHelper.fromHtml(noticia.getTitulo()));

        String date = UtilityMethods.ParseDateToString(noticia.getDataPublicacao());
        if (date != null)
            holder.textViewData.setText(date);
        else
            holder.textViewData.setText(HtmlHelper.fromHtml("Data não divulgada"));

        holder.textViewDescription.setHtml(noticia.getConteudo(), new HtmlResImageGetter(holder.textViewDescription));
        Linkify.addLinks(holder.textViewDescription, Linkify.ALL);

        View.OnClickListener listener = v -> onItemClickListener.onItemClick(noticia);

        int color;
        if (position % 2 == 0)
            color = Color.WHITE;
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                color = ContextCompat.getColor(context, R.color.colorGreenWhite);
            else
                color = context.getResources().getColor(R.color.colorGreenWhite);

        }

        holder.imageView.setOnClickListener(listener);
        holder.textViewTitle.setOnClickListener(listener);
        holder.textViewDescription.setOnClickListener(listener);

        holder.textViewTitle.setBackgroundColor(color);
        holder.textViewDescription.setBackgroundColor(color);
        holder.cardView.setCardBackgroundColor(color);
        holder.cardViewContent.setCardBackgroundColor(color);
        holder.textViewData.setBackgroundColor(color);
        EstablishCardSize(holder);

    }

    private void EstablishCardSize(NoticiaViewHolder holder) {
        WindowManager wm = (WindowManager) this.context.getSystemService(Context.WINDOW_SERVICE);
        Display screenSize = wm.getDefaultDisplay();
        Point size = new Point();
        screenSize.getSize(size);
        if (holder.cardViewContent.getHeight() <= size.y)
            holder.cardViewContent.setMinimumHeight(size.y);
    }

    @Override
    public int getItemCount() {
        return (null != this.noticiaList ? this.noticiaList.size() : 0);
    }

    public void setOnItemClickListener(OnNoticiaItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    static class NoticiaViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView textViewTitle;
        private final TextView textViewData;
        private final HtmlTextView textViewDescription;
        private final CardView cardView;
        private final CardView cardViewContent;

        public NoticiaViewHolder(View view) {
            super(view);

            this.imageView = view.findViewById(R.id.thumbnail);
            this.textViewTitle = view.findViewById(R.id.txtTitle);
            this.textViewDescription = view.findViewById(R.id.txtContent);
            this.textViewData = view.findViewById(R.id.txtData);
            this.cardView = view.findViewById(R.id.cardViewNoticias);
            this.cardViewContent = view.findViewById(R.id.cardViewContent);

        }

    }
}
