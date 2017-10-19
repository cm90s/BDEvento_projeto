package com.example.gatanhoto.bdevento_projeto.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import com.example.gatanhoto.bdevento_projeto.model.Evento;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gatanhoto on 10/17/17.
 */

public class EventoServiceBD extends SQLiteOpenHelper {


    private static String TAG = "bdevento";
    private static String NAME = "evento2.sqlite";
    private static int VERSION = 1;
    private static EventoServiceBD eventoServiceBD = null;


    /*
        Construtor
     */
    private EventoServiceBD(Context context) {
        super(context, NAME, null, VERSION);
        getWritableDatabase(); //abre a conexão com o bd, utilizado pelo onCreate()
    }

    //Singleton
    public static EventoServiceBD getInstance(Context context){
        if(eventoServiceBD == null){
            return eventoServiceBD = new EventoServiceBD(context);
        }

        return eventoServiceBD;
    }


    /*
        Métodos do ciclo de vida.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //cria a tabela
        String sql = "create table if not exists evento " +
                "(_id integer primary key autoincrement, " +
                "nome text, " +
                "local text, " +
                "cidade text," +
                "data text," +
                "hora text, " +
                "url_foto text, " +
                "desc text, " +
                "tipo text, " +
                "produtor text);";
        Log.d(TAG, "Criando a tabela evetno2. Aguarde ...");
        sqLiteDatabase.execSQL(sql);
        Log.d(TAG, "Tabela evento2 criada com sucesso.");
        new Task().execute(); //popula o bd
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /*

    MÉDOTOS CRUD

   */
    //READ
    public List<Evento> getAll(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        try{

            return toList(sqLiteDatabase.rawQuery("select * from evento",null));

        }finally {
            sqLiteDatabase.close();
        }

    }
    //READ
    public List<Evento> getByTipo(String tipo){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        try{

            return toList(sqLiteDatabase.rawQuery("select * from evento where tipo='" +tipo + "'",null));

        }finally {
            sqLiteDatabase.close();
        }
    }

    private List<Evento> toList(Cursor cursor){

        List<Evento> eventos = new ArrayList<>();

        if(cursor.moveToFirst()){

            do {

                Evento evento = new Evento();

                //recupera os atributos do cursor para o evento
                evento._id = cursor.getLong(cursor.getColumnIndex("_id"));
                evento.nome = cursor.getString(cursor.getColumnIndex("nome"));
                evento.local = cursor.getString(cursor.getColumnIndex("local"));
                evento.cidade = cursor.getString(cursor.getColumnIndex("cidade"));
                evento.data = cursor.getString(cursor.getColumnIndex("data"));
                evento.hora = cursor.getString(cursor.getColumnIndex("hora"));
                evento.url_foto = cursor.getString(cursor.getColumnIndex("url_foto"));
                evento.descricao = cursor.getString(cursor.getColumnIndex("desc"));
                evento.tipo = cursor.getString(cursor.getColumnIndex("tipo"));
                evento.produtor = cursor.getString(cursor.getColumnIndex("produtor"));

                eventos.add(evento);



            }while (cursor.moveToNext());
        }
        return eventos;
    }

    //Salvar (CREATE, UPDATE

    public long save(Evento evento){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        try{

            ContentValues values = new ContentValues();
            values.put("nome", evento.nome);
            values.put("local",evento.local);
            values.put("cidade", evento.cidade);
            values.put("data",evento.data);
            values.put("hora", evento.hora);
            values.put("url_foto",evento.url_foto);
            values.put("desc", evento.descricao);
            values.put("tipo",evento.tipo);
            values.put("produtor", evento.produtor);


            //realizar operacoes

            if (evento._id == null){
                //insere no naco de dados
                return sqLiteDatabase.insert("evento", null, values);
            }else{
                //alterar no banco de dados
                values.put("_id",evento._id);
                return  sqLiteDatabase.update("evento", values, "_id=" + evento._id, null);
            }
        }finally {
            sqLiteDatabase.close();
        }

    }

    //Excluir

    public long delete(Evento evento){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        try {
            return sqLiteDatabase.delete("evento", "_id=?", new String[]{String.valueOf(evento._id)});

        }finally {
            sqLiteDatabase.close();
        }



    }

    //Thread para executar a inserção de dados no bd.
    //Utilizada apenas na criação do bd
    private class Task extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            return popularTabelaCarro();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                Log.d(TAG, "Tabela evento2 populada com sucesso.");
            }
        }

        //popula a tabela evento
        private boolean popularTabelaCarro() {
            //abre a conexão com o bd
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            try {
                //registro 1
                ContentValues values = new ContentValues();
                values.put("nome", "Tucker 1948");
                values.put("local", "Descrição Tucker 1948");
                values.put("cidade", "Clássicos");
                values.put("data", "-31.3322593");
                values.put("hora", "54.0718532");
                values.put("desc", "54.0718532");
                values.put("tipo", "pago");

                sqLiteDatabase.insert("evento", null, values);

                //registro 2
                values = new ContentValues();
                values.put("nome", "Tucker 1948");
                values.put("local", "Descrição Tucker 1948");
                values.put("cidade", "Clássicos");
                values.put("data", "-31.3322593");
                values.put("hora", "54.0718532");
                values.put("desc", "54.0718532");
                values.put("tipo", "pago");
                sqLiteDatabase.insert("evento", null, values);

                //registro 3
                values = new ContentValues();
                values.put("nome", "Tucker 1948");
                values.put("local", "Descrição Tucker 1948");
                values.put("cidade", "Clássicos");
                values.put("data", "-31.3322593");
                values.put("hora", "54.0718532");
                values.put("desc", "54.0718532");
                values.put("tipo", "pago");
                sqLiteDatabase.insert("evento", null, values);

                //registro 4
                values = new ContentValues();
                values.put("nome", "Tucker 1948");
                values.put("local", "Descrição Tucker 1948");
                values.put("cidade", "Clássicos");
                values.put("data", "-31.3322593");
                values.put("hora", "54.0718532");
                values.put("desc", "54.0718532");
                values.put("tipo", "pago");
                sqLiteDatabase.insert("evento", null, values);

                //registro 5
                values = new ContentValues();
                values.put("nome", "Tucker 1948");
                values.put("local", "Descrição Tucker 1948");
                values.put("cidade", "Clássicos");
                values.put("data", "-31.3322593");
                values.put("hora", "54.0718532");
                values.put("desc", "54.0718532");
                values.put("tipo", "pago");
                sqLiteDatabase.insert("evento", null, values);

                //registro 6
                values = new ContentValues();
                values.put("nome", "Tucker 1948");
                values.put("local", "Descrição Tucker 1948");
                values.put("cidade", "Clássicos");
                values.put("data", "-31.3322593");
                values.put("hora", "54.0718532");
                values.put("desc", "54.0718532");
                values.put("tipo", "pago");
                sqLiteDatabase.insert("evento", null, values);

                //registro 7
                values = new ContentValues();
                values.put("nome", "Ferrari 250 GTO");
                values.put("local", "Descrição Tucker 1948");
                values.put("cidade", "Clássicos");
                values.put("data", "-31.3322593");
                values.put("hora", "54.0718532");
                values.put("desc", "54.0718532");
                values.put("tipo", "pago");
                sqLiteDatabase.insert("evento", null, values);

                //registro 8
                values = new ContentValues();
                values.put("nome", "Dodge Challenger");
                values.put("local", "Descrição Tucker 1948");
                values.put("cidade", "Clássicos");
                values.put("data", "-31.3322593");
                values.put("hora", "54.0718532");
                values.put("desc", "54.0718532");
                values.put("tipo", "pago");
                sqLiteDatabase.insert("evento", null, values);

                //registro 9
                values = new ContentValues();
                values.put("nome", "Camaro SS 1969");
                values.put("local", "Descrição Tucker 1948");
                values.put("cidade", "Clássicos");
                values.put("data", "-31.3322593");
                values.put("hora", "54.0718532");
                values.put("desc", "54.0718532");
                values.put("tipo", "pago");
                sqLiteDatabase.insert("evento", null, values);

                //registro 10
                values = new ContentValues();
                values.put("nome", "Ford Mustang 1976");
                values.put("local", "Descrição Tucker 1948");
                values.put("cidade", "Clássicos");
                values.put("data", "-31.3322593");
                values.put("hora", "54.0718532");
                values.put("desc", "54.0718532");
                values.put("tipo", "pago");
                sqLiteDatabase.insert("evento", null, values);

                //registro 11
                values = new ContentValues();
                values.put("nome", "Ferrari FF");
                values.put("local", "Descrição Tucker 1948");
                values.put("cidade", "Clássicos");
                values.put("data", "-31.3322593");
                values.put("hora", "54.0718532");
                values.put("desc", "54.0718532");
                values.put("tipo", "gratis");
                sqLiteDatabase.insert("evento", null, values);

                //registro 12
                values = new ContentValues();
                values.put("nome", "AUDI GT Spyder");
                values.put("local", "Descrição Tucker 1948");
                values.put("cidade", "Clássicos");
                values.put("data", "-31.3322593");
                values.put("hora", "54.0718532");
                values.put("desc", "54.0718532");
                values.put("tipo", "gratis");
                sqLiteDatabase.insert("evento", null, values);

                //registro 13
                values = new ContentValues();
                values.put("nome", "Porsche Panamera");
                values.put("local", "Descrição Tucker 1948");
                values.put("cidade", "Clássicos");
                values.put("data", "-31.3322593");
                values.put("hora", "54.0718532");
                values.put("desc", "54.0718532");
                values.put("tipo", "gratis");
                sqLiteDatabase.insert("evento", null, values);

                //registro 14
                values = new ContentValues();
                values.put("nome", "Lamborghini Aventador");
                values.put("local", "Descrição Tucker 1948");
                values.put("cidade", "Clássicos");
                values.put("data", "-31.3322593");
                values.put("hora", "54.0718532");
                values.put("desc", "54.0718532");
                values.put("tipo", "gratis");
                sqLiteDatabase.insert("evento", null, values);

                //registro 15
                values = new ContentValues();
                values.put("nome", "Chevrolet Corvette Z06");
                values.put("local", "Descrição Tucker 1948");
                values.put("cidade", "Clássicos");
                values.put("data", "-31.3322593");
                values.put("hora", "54.0718532");
                values.put("desc", "54.0718532");
                values.put("tipo", "gratis");
                sqLiteDatabase.insert("evento", null, values);

                //registro 16
                values = new ContentValues();
                values.put("nome", "BMW M5");
                values.put("local", "Descrição Tucker 1948");
                values.put("cidade", "Clássicos");
                values.put("data", "-31.3322593");
                values.put("hora", "54.0718532");
                values.put("desc", "54.0718532");
                values.put("tipo", "gratis");
                sqLiteDatabase.insert("evento", null, values);

                //registro 17
                values = new ContentValues();
                values.put("nome", "Renault Megane RS Trophy");
                values.put("local", "Descrição Tucker 1948");
                values.put("cidade", "Clássicos");
                values.put("data", "-31.3322593");
                values.put("hora", "54.0718532");
                values.put("desc", "54.0718532");
                values.put("tipo", "gratis");
                sqLiteDatabase.insert("evento", null, values);

                //registro 18
                values = new ContentValues();
                values.put("nome", "Maserati Grancabrio Sport");
                values.put("local", "Descrição Tucker 1948");
                values.put("cidade", "Clássicos");
                values.put("data", "-31.3322593");
                values.put("hora", "54.0718532");
                values.put("desc", "54.0718532");
                values.put("tipo", "gratis");;
                sqLiteDatabase.insert("evento", null, values);

                //registro 19
                values = new ContentValues();
                values.put("nome", "McLAREN MP4-12C");
                values.put("local", "Descrição Tucker 1948");
                values.put("cidade", "Clássicos");
                values.put("data", "-31.3322593");
                values.put("hora", "54.0718532");
                values.put("desc", "54.0718532");
                values.put("tipo", "gratis");
                sqLiteDatabase.insert("evento", null, values);

                //registro 20
                values = new ContentValues();
                values.put("nome", "MERCEDES-BENZ C63 AMG");values.put("local", "Descrição Tucker 1948");
                values.put("cidade", "Clássicos");
                values.put("data", "-31.3322593");
                values.put("hora", "54.0718532");
                values.put("desc", "54.0718532");
                values.put("tipo", "gratis");
                sqLiteDatabase.insert("evento", null, values);


            }catch (SQLException e){
                e.printStackTrace();
                return false;
            }
            finally {
                sqLiteDatabase.close(); //libera o recurso
            }

            return true;
        }
    }
}
