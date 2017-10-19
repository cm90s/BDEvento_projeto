package com.example.gatanhoto.bdevento_projeto.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gatanhoto.bdevento_projeto.R;
import com.example.gatanhoto.bdevento_projeto.model.Evento;

import java.util.List;

/**
 * Created by gatanhoto on 10/17/17.
 */

public class EventosAdapter extends RecyclerView.Adapter<EventosAdapter.EventosViewHolder> {
    protected static final String TAG = "EventosAdapter";
    private final List<Evento> eventos;
    private final Context context;

    private EventoOnClickListener eventoOnClickListener;

    public EventosAdapter(Context context, List<Evento> eventos, EventoOnClickListener eventoOnClickListener) {
        this.context = context;
        this.eventos = eventos;
        this.eventoOnClickListener = eventoOnClickListener;
    }

    @Override
    public int getItemCount() {
        return this.eventos != null ? this.eventos.size() : 0;
    }

    @Override
    public EventosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Infla a view do layout
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_eventos, viewGroup, false);

        // Cria o ViewHolder
        EventosViewHolder holder = new EventosViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final EventosViewHolder holder, final int position) {
        // Atualiza a view
        Evento c = eventos.get(position);
        Log.d(TAG, "Evento no Adapter da RecyclerView: " + c.toString());

        Log.d(TAG, c.toString());

        holder.tNome.setText(c.nome);
        holder.local.setText(c.local);
        holder.cidade.setText(c.cidade);
        holder.hora.setText(c.hora+"h");
        holder.progress.setVisibility(View.VISIBLE);
        if(c.url_foto != null){
            holder.img.setImageURI(Uri.parse(c.url_foto));
        }else{
            holder.img.setImageResource(R.drawable.drama);
        }

        // Click
        if (eventoOnClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventoOnClickListener.onClickEvento(holder.itemView, position); // A variável position é final
                }
            });
        }

        holder.progress.setVisibility(View.INVISIBLE);
    }

    public interface EventoOnClickListener {
        public void onClickEvento(View view, int idx);
    }

    // ViewHolder com as views
    public static class EventosViewHolder extends RecyclerView.ViewHolder {
        public TextView tNome;
        public TextView local;
        public TextView cidade ;
        public TextView hora ;
        public TextView mes;
        public  TextView data;
        ImageView img;
        ProgressBar progress;


        public EventosViewHolder(View view) {
            super(view);
            // Cria as views para salvar no ViewHolder
            tNome = (TextView) view.findViewById(R.id.text_titulo_adapter);
            local = (TextView) view.findViewById(R.id.text_local_adapter);
            cidade = (TextView) view.findViewById(R.id.text_cidade_adapter);
            img = (ImageView) view.findViewById(R.id.imageView_principal_adapter);
            progress = (ProgressBar) view.findViewById(R.id.progressBar_adapter);
            hora = (TextView) view.findViewById(R.id.text_hora_adapter);
            mes = (TextView) view.findViewById(R.id.text_mes_adapter);
            data = (TextView) view.findViewById(R.id.text_data_adapter);

        }
    }

}
