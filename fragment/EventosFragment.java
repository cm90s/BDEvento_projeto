package com.example.gatanhoto.bdevento_projeto.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gatanhoto.bdevento_projeto.R;
import com.example.gatanhoto.bdevento_projeto.activity.EventoActivity;
import com.example.gatanhoto.bdevento_projeto.activity.EventosActivity;
import com.example.gatanhoto.bdevento_projeto.adapter.EventosAdapter;
import com.example.gatanhoto.bdevento_projeto.model.Evento;
import com.example.gatanhoto.bdevento_projeto.service.EventoServiceBD;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gatanhoto on 10/16/17.
 */

public class EventosFragment extends  BaseFragment implements SearchView.OnQueryTextListener{
     RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Evento> eventos;
    private String tipo; //o tipo de evento que é recebido como argumento na construção do fragmento
    private EventoServiceBD eventoServiceBD;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static final String TAG = "eventosFragment";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setHasOptionsMenu(true);

        ((EventosActivity) getActivity()).getSupportActionBar().setTitle(R.string.titulo_fragmenteventos);

        Log.d(TAG,"Tipo de control = " + getArguments().getString("tipo"));
        if (getArguments() != null) {
            this.tipo = getArguments().getString("tipo");
        }


        eventoServiceBD = EventoServiceBD.getInstance(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();

        new Task().execute();
        Log.d(TAG,"Tread carregada");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_eventos, container, false);

       recyclerView=(RecyclerView) view.findViewById(R.id.recyclerview_fragmenteventos);
       linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        //configura o swipeRefreshLayout

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout_fragment_eventos);
        swipeRefreshLayout.setOnRefreshListener(OnRefreshListener());
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1, R.color.refresh_progress_2, R.color.refresh_progress_3);




      //  eventos = EventoServiceTeste.getEventos(getResources().getString(R.string.tipo_pago));

        EventosAdapter adapter= new EventosAdapter(getContext(), eventos, onClickEvento());
        recyclerView.setAdapter(adapter);

        return view;

    }

    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               new Task().execute();
            }
        };
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_eventos, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menuitem_pesquisar).getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getString(R.string.hint_pesquisar));

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<Evento> eventoList = new ArrayList<>();

        for(Evento evento:eventos){
            if(evento.nome.contains(newText)){
                eventoList.add(evento);
            }
        }

        recyclerView.setAdapter(new EventosAdapter(getContext(), eventoList, onClickEvento()));

        return true;
    }

    protected EventosAdapter.EventoOnClickListener onClickEvento() {
        return new EventosAdapter.EventoOnClickListener(){
            @Override
            public void onClickEvento(View view, int idx) {
                //Toast.makeText(getContext(), "clicou", Toast.LENGTH_SHORT).show();

                Evento evento = eventos.get(idx);
                Intent intent = new Intent(getContext(),EventoActivity.class);
                intent.putExtra("qualFragmentAbrir", "EventoDetalheFragment");
                intent.putExtra("evento", evento);
                //chama outra Activity
                startActivity(intent);
            }
        };
    }

    private class Task extends AsyncTask<Void, Void, List<Evento>>{


        @Override
        protected List<Evento> doInBackground(Void... voids) {

            if (EventosFragment.this.tipo.equals(getString(R.string.tabs_pagos))){
                return eventoServiceBD.getByTipo("pago");
            }else {
                if (EventosFragment.this.tipo.equals(getString(R.string.tabs_gratis))){
                    return eventoServiceBD.getByTipo("gratis");

                }else {
                    if (EventosFragment.this.tipo.equals(getString(R.string.tabs_todos))){
                        return eventoServiceBD.getAll();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Evento> eventos) {
            super.onPostExecute(eventos);

            EventosFragment.this.eventos = eventos;
            //atualiza a view na UIThread
            recyclerView.setAdapter(new EventosAdapter(getContext(), eventos, onClickEvento()));

            swipeRefreshLayout.setRefreshing(false);
        }
    }

}
