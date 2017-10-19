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
import android.widget.TextView;
import android.widget.Toast;

import com.example.gatanhoto.bdevento_projeto.R;
import com.example.gatanhoto.bdevento_projeto.activity.EventoActivity;
import com.example.gatanhoto.bdevento_projeto.activity.EventosActivity;
import com.example.gatanhoto.bdevento_projeto.model.Evento;
import com.example.gatanhoto.bdevento_projeto.service.EventoServiceBD;

/**
 * Created by gatanhoto on 10/17/17.
 */

public class EventoNovoFragment extends BaseFragment {
    private Evento evento; //uma instância da classe Evento com escopo global para utilização em membros da classe
    //componentes <-> objeto evento
    //Componente de UI, para mapeamento
    private RadioButton rdPago, rdGratis;
    private EditText edtNome, edtLocal, edtCidade;
    private EditText edtData, edtHora;
    private EditText edtDescricao;
    private EditText edtProdutor;
    private ImageView imageviewFoto;

    public static final String TAG = "EventoNovoFragment";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true); //informa ao sistema que o fragment irá adicionar botões na ActionBar

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //infla o xml da UI e associa ao Fragment
        View view = inflater.inflate(R.layout.fragment_novo_evento, container, false);

        //um título para a janela
        ((EventoActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_novoevento);

        //Cria uma instancia da classe de modelo
        evento = new Evento();

        //mapeia e inicializa os componentes da UI
        //Card0
        imageviewFoto = (ImageView) view.findViewById(R.id.img_card0_principal_fragmentnovoevento);
        imageviewFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cria uma Intent
                //primeiro argumento: ação ACTION_PICK "escolha um item a partir dos dados e retorne o seu URI"
                //segundo argumento: refina a ação para arquivos de imagem, retornando um URI
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //inicializa uma Activity. Neste caso, uma que forneca acesso a galeria de imagens do dispositivo.
                startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 0);
            }
        });

        //Carega o tipo nos RadioButton

        Log.d(TAG, "Tipo de caro = " + evento.tipo);
        rdPago = view.findViewById(R.id.rb_pago_card1_fragmentnovoevento);
        rdGratis = view.findViewById(R.id.rb_gratis_card1_fragmentnovoevento);
        rdPago.setChecked(true);


        //Carregar o nome , local e cidade
        Log.d(TAG, "Nome = " + evento.nome + "\nLocal= " + evento.local + "\nCidade= " + evento.cidade);
        edtNome = view.findViewById(R.id.edtNome_card2_fragmentnovoevento);
        edtLocal = view.findViewById(R.id.edtLocal_card2_fragmentnovoevento);
        edtCidade = view.findViewById(R.id.edtCidade_card2_fragmentnovoevento);


        //Carregar data e hora

        Log.d(TAG, "Data = " + evento.data + "\nHora= " + evento.hora);
        edtData = view.findViewById(R.id.edtDate_card3_fragmentnovoevento);
        edtHora = view.findViewById(R.id.edtHora_card3_fragmentnovoevento);


        //Carregar descricao
        Log.d(TAG, "Descricao = " + evento.descricao );
        edtDescricao = view.findViewById(R.id.edtDescricao_card4_fragmentnovoevento);

        //Carreagr produtor
        Log.d(TAG, "Produtor= " + evento.produtor);
        edtProdutor = view.findViewById(R.id.edtProdutor_card4_fragmentnovoevento);
        return view;
    }

    /*
        Infla o menu.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_novoevento, menu);
    }

    /*
        Trata eventos dos itens de menu.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuitem_salvar:{
                //carrega os dados do formulário no objeto do modelo
                //carrega os dados do formulário no objeto do modelo
                if(!edtNome.getText().toString().isEmpty()) {
                    evento.nome = edtNome.getText().toString();
                    evento.local = edtLocal.getText().toString();
                    evento.cidade = edtCidade.getText().toString();
                    evento.data = edtData.getText().toString();
                    evento.hora = edtHora.getText().toString();
                    evento.descricao = edtDescricao.getText().toString();
                    evento.produtor = edtProdutor.getText().toString();



                    if(rdPago.isChecked()){
                        evento.tipo = getContext().getResources().getString(R.string.tipo_pago_simples);
                    }else {
                        evento.tipo = getContext().getResources().getString(R.string.tipo_gratis_simples);
                    }
                    new EventosTask().execute(); //executa a operação CREATE em uma thread AsyncTask
                }else{
                    toast(getContext().getResources().getString(R.string.val_dadosinputs));
                }
                break;

            }
            case android.R.id.home:
                getActivity().finish();
                break;
        }

        return false;
    }

    /**
     * Método que recebe o retorno da Activity de galeria de imagens.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == getActivity().RESULT_OK){
            Log.d(TAG, data.toString());
            Uri arquivoUri = data.getData(); //obtém o URI da imagem
            Log.d(TAG, "URI do arquivo: " + arquivoUri);
            if(arquivoUri.toString().contains("images")) {
                imageviewFoto.setImageURI(arquivoUri); //coloca a imagem no ImageView
                evento.url_foto = arquivoUri.toString(); //armazena o Uri para salvar a imagem no objeto imagem
            }
        }
    }

    /*
        Classe interna que extende uma AsyncTask.
        Lembrando: A AsyncTask gerência a thread que realiza CRUD no banco de dados.
    */
    private class EventosTask extends AsyncTask<Void, Void, Long> {

        @Override
        protected Long doInBackground(Void... voids) {
            return EventoServiceBD.getInstance(getContext()).save(evento);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //emite uma caixa processando
            alertWait(R.string.alert_title_wait, R.string.alert_message_processando);
        }


        @Override
        protected void onPostExecute(Long cont) {
            super.onPostExecute(cont);
            alertWaitDismiss();
            if(cont > 0){
                //faz aparecer uma caixa de diálogo confirmando a operação
                alertOk(R.string.alert_title_resultadodaoperacao, R.string.alert_message_realizadacomsucesso);
            }else{
                //faz aparecer uma caixa de diálogo confirmando a operação
                alertOk(R.string.alert_title_resultadodaoperacao, R.string.alert_message_erroaorealizaroperacao);
            }
        }
    }


}
