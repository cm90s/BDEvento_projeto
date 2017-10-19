package com.example.gatanhoto.bdevento_projeto.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.gatanhoto.bdevento_projeto.R;
import com.example.gatanhoto.bdevento_projeto.activity.EventoActivity;
import com.example.gatanhoto.bdevento_projeto.model.Evento;
import com.example.gatanhoto.bdevento_projeto.service.EventoServiceBD;

/**
 * Created by gatanhoto on 10/17/17.
 */

public class EventoEdicaoFragment extends BaseFragment {
    private Evento evento;
    private final String SAVE = "save";
    private final String DELETE = "delete";
    private EventoServiceBD eventoServiceBD;

    //Componente de UI, para mapeamento
    private RadioButton rdPago, rdGratis;
    private EditText edtNome, edtLocal, edtCidade;
    private EditText edtData, edtHora;
    private EditText edtDescricao;
    private EditText edtProdutor;
    private ImageView imageviewFoto, imgvhora,imgvdata;


    public  static  final String TAG = "EventoEdicaoFragment";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        Log.d(TAG, "title");

        eventoServiceBD = EventoServiceBD.getInstance(getContext());
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //infla o xml da UI e associa ao Fragment
        View view = inflater.inflate(R.layout.fragment_editar_evento, container, false);

        Log.d(TAG, "Dados do registro" + evento);

        //carregar imagem

        Log.d(TAG, "URL da foto= " + evento.url_foto);

        imageviewFoto = view.findViewById(R.id.imageView_card0_principal_fragmentEditarevento);
        if (evento.url_foto != null) {
            imageviewFoto.setImageURI(Uri.parse(evento.url_foto));
        }
        imageviewFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 0);
            }
        });


        //carregar data e hora
        Log.d(TAG, "Hora e data onClick");
        imgvdata = view.findViewById(R.id.imgv_calendario_card3_fragmenteditarevento);
        imgvhora = view.findViewById(R.id.imgv_hora_card3_fragmenteditarevento);
        imgvdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getContext(), "DATA CLICK, EM DESENVOLVIMENTO",Toast.LENGTH_LONG).show();
            }
        });

        imgvhora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getContext(), "Hora CLICK, EM DESENVOLVIMENTO",Toast.LENGTH_LONG).show();


            }
        });

        //Carega o tipo nos RadioButton

        Log.d(TAG, "Tipo de caro = " + evento.tipo);
        rdPago = view.findViewById(R.id.rb_pago_card1_fragmentEditarevento);
        rdGratis = view.findViewById(R.id.rb_gratis_card1_fragmentEditarevento);
        if (evento.tipo.equals(getResources().getString(R.string.tipo_pago_simples))){
            rdPago.setChecked(true);
        }else{
            rdGratis.setChecked(true);
        }

        //Carregar o nome , local e cidade
        Log.d(TAG, "Nome = " + evento.nome + "\nLocal= " + evento.local + "\nCidade= " + evento.cidade);
        edtNome = view.findViewById(R.id.edtNome_card2_fragmentEditarevento);
        edtLocal = view.findViewById(R.id.edtLocal_card2_fragmentEditarevento);
        edtCidade = view.findViewById(R.id.edtCidade_card2_fragmentEditarevento);
        edtNome.setText(evento.nome);
        edtLocal.setText(evento.local);
        edtCidade.setText(evento.cidade);

        //Carregar data e hora

        Log.d(TAG, "Data = " + evento.data + "\nHora= " + evento.hora);
        edtData = view.findViewById(R.id.edtDate_card3_fragmenteditarevento);
        edtHora = view.findViewById(R.id.edtHora_card2_fragmenteditarevento);
        edtData.setText(evento.data);
        edtHora.setText(evento.hora);

        //Carregar descricao
        Log.d(TAG, "Descricao = " + evento.descricao );
        edtDescricao = view.findViewById(R.id.edtDescricao_card4_fragmentEditarevento);
        edtDescricao.setText(evento.descricao);

        //Carreagr produtor
        Log.d(TAG, "Produtor= " + evento.produtor);
        edtProdutor = view.findViewById(R.id.edtProdutor_card4_fragmentEditarevento);
        edtProdutor.setText(evento.produtor);





        //um título para a janela
        ((EventoActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_edicaoevento);


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_fragment_edicaoevento, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuitem_salvar:
               // Toast.makeText(getContext(), "Salvar", Toast.LENGTH_LONG).show();

                evento.nome = edtNome.getText().toString();
                evento.local = edtLocal.getText().toString();
                evento.cidade = edtCidade.getText().toString();
                evento.data = edtData.getText().toString();
                evento.hora = edtHora.getText().toString();
                evento.descricao = edtDescricao.getText().toString();
                evento.produtor = edtProdutor.getText().toString();

                //falta imagem e Rdiobutton
                if(rdPago.isChecked()){
                    evento.tipo = getContext().getResources().getString(R.string.tipo_pago_simples);
                }else {
                    evento.tipo = getContext().getResources().getString(R.string.tipo_gratis_simples);
                }

                new Task().execute(SAVE);
                break;
            case R.id.menuitem_excluir:
                new Task().execute(DELETE); //executa a operação DELETE em uma thread AsyncTask
                break;

            case android.R.id.home:
                getActivity().finish();
                break;


            // replaceFragment(R.id.fragment_container, new EventoEdicaoFragment());
        }
        return true;
    }

    private class  Task extends AsyncTask<String, Void, Long>{


        @Override
        protected Long doInBackground(String... strings) {
            if (strings[0].equals(SAVE)){
                return eventoServiceBD.save(evento);
            }else if (strings[0].equals(DELETE)){
                return eventoServiceBD.delete(evento);
            }

            return 0L;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);

            if (aLong > 0 ){
                EventoEdicaoFragment.this.alertOk(R.string.alert_title_resultadodaoperacao , R.string.alert_message_realizadacomsucesso);
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == getActivity().RESULT_OK){
            Log.d(TAG, data.toString());
            Uri arquivoUri = data.getData(); //obtém o URI
            Log.d(TAG, "URI do arquivo: " + arquivoUri);
            if(arquivoUri.toString().contains("images")) {
                imageviewFoto.setImageURI(arquivoUri); //coloca a imagem no ImageView
                evento.url_foto = arquivoUri.toString(); //armazena o Uri da imagem no objeto do modelo
            }
        }
    }

}
