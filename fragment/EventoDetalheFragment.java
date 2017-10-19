package com.example.gatanhoto.bdevento_projeto.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.gatanhoto.bdevento_projeto.R;
import com.example.gatanhoto.bdevento_projeto.activity.EventoActivity;
import com.example.gatanhoto.bdevento_projeto.model.Evento;

/**
 * Created by gatanhoto on 10/17/17.
 */

public class EventoDetalheFragment extends BaseFragment {

    private Evento evento;

    //Componente de UI, para mapeamento
    private RadioButton rdPago, rdGratis;
    private TextView tvNome, tvLocal, tvCidade;
    private TextView tvData, tvHora;
    private TextView tvDescricao;
    private TextView tvProdutor;
    private ImageView imageviewFoto;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        Log.d(TAG, "title");
    }

    public void setEvento(Evento evento){
        this.evento = evento;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //infla o xml da UI e associa ao Fragment
        View view = inflater.inflate(R.layout.fragment_detalhes_evento, container, false);


        //carregar imagem

        Log.d(TAG, "URL da foto= " + evento.url_foto);

        imageviewFoto = view.findViewById(R.id.img_card0_principal_fragmentdetalhes);
        if (evento.url_foto != null) {
            imageviewFoto.setImageURI(Uri.parse(evento.url_foto));
        }


        //Carega o tipo nos RadioButton

        Log.d(TAG, "Tipo de caro = " + evento.tipo);
        rdPago = view.findViewById(R.id.rb_pago_card1_fragmentdetalhes);
        rdGratis = view.findViewById(R.id.rb_gratis_card1_fragmentdetalhes);
        if (evento.tipo.equals(getResources().getString(R.string.tipo_pago_simples))){
            rdPago.setChecked(true);
        }else{
            rdGratis.setChecked(true);
        }

        //Carregar o nome , local e cidade
        Log.d(TAG, "Nome = " + evento.nome + "\nLocal= " + evento.local + "\nCidade= " + evento.cidade);
        tvNome = view.findViewById(R.id.txt_nome_fragmentdetalhe);
        tvLocal = view.findViewById(R.id.txt_local_fragmentdetalhe);
        tvCidade = view.findViewById(R.id.txt_cidade_fragmentdetalhe);
        tvNome.setText(evento.nome);
        tvLocal.setText(evento.local);
        tvCidade.setText(evento.cidade);

        //Carregar data e hora

        Log.d(TAG, "Data = " + evento.data + "\nHora= " + evento.hora);
        tvData = view.findViewById(R.id.txt_data_fragmentdetalhe);
        tvHora = view.findViewById(R.id.txt_hora_fragmentdetalhe);
        tvData.setText(evento.data);
        tvHora.setText("Ás "+ evento.hora + "h");

        //Carregar descricao
        Log.d(TAG, "Descricao = " + evento.descricao );
        tvDescricao = view.findViewById(R.id.txt_descricao_fragmentdetalhe);
        tvDescricao.setText(evento.descricao);

        //Carreagr produtor
        Log.d(TAG, "Produtor= " + evento.produtor);
        tvProdutor = view.findViewById(R.id.txt_produtor_fragmentdetalhe);
        tvProdutor.setText(evento.produtor);

        //um título para a janela
        ((EventoActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_detalheevento);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_fragment_detalheevento, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuitem_editar:
                //Toast.makeText(getContext(),"Editar",Toast.LENGTH_LONG ).show();
                EventoEdicaoFragment eventoEdicaoFragment = new EventoEdicaoFragment();
                eventoEdicaoFragment.setEvento(evento);
                replaceFragment(R.id.fragment_container, eventoEdicaoFragment);
                break;
            case android.R.id.home:
                getActivity().finish();
                break;

        }
        return true;
    }
}
