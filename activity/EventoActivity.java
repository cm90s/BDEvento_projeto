package com.example.gatanhoto.bdevento_projeto.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.gatanhoto.bdevento_projeto.R;
import com.example.gatanhoto.bdevento_projeto.fragment.EventoDetalheFragment;
import com.example.gatanhoto.bdevento_projeto.fragment.EventoNovoFragment;
import com.example.gatanhoto.bdevento_projeto.model.Evento;

/**
 * Created by gatanhoto on 10/16/17.
 */

public class EventoActivity extends BaseActivity {

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);

        //obtém do extras da intent recebida o fragmento que deve abrir
        String msg = (String) getIntent().getCharSequenceExtra("qualFragmentAbrir");
        if(msg.equals("EventoNovoFragment")){
            replaceFragment(R.id.fragment_container, new EventoNovoFragment());
        }else if(msg.equals("EventoDetalheFragment")){
            EventoDetalheFragment eventoDetalheFragment = new EventoDetalheFragment();
            replaceFragment(R.id.fragment_container,  eventoDetalheFragment);

            Evento evento = (Evento) getIntent().getSerializableExtra("evento");
            Log.d(TAG, "Obejto evento recebido." + evento.toString());
            eventoDetalheFragment.setEvento(evento);
        }
    }
}
