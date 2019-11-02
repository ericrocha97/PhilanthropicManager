package cf.ericrocha.philanthropicmanager.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cf.ericrocha.philanthropicmanager.model.CalenderModel;


public class DBHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_APP";
    public static String TABELA_PESSOA = "pessoa";
    public static String TAB_trabalhos = "trabalhos";
    public static String TAB_filantropia = "filantropia";
    public static String TAB_documentos = "documentos";
    public static String TAB_doc_tipo = "doc_tipo";
    public static String TAB_financas = "financas";
    public static String TAB_membros = "membros";
    public static String TAB_usuarios = "usuarios";
    private List<CalenderModel> listCalenderModel = new ArrayList<>();

    public DBHelper(Context context) {
        super(context, NOME_DB, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql_pessoa = "CREATE TABLE IF NOT EXISTS " + TABELA_PESSOA
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " nome TEXT NOT NULL, " +
                " idade INT(3)); ";

        String sql_trabalhos = "CREATE TABLE IF NOT EXISTS " + TAB_trabalhos
                + " (cod_trabalho INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " titulo_trabalho TEXT NOT NULL, " +
                " desc_trabalho TEXT NOT NULL, " +
                " nome_membro TEXT NOT NULL, " +
                " dt_trab DATE NOT NULL); ";

        String sql_filantropia = "CREATE TABLE IF NOT EXISTS " + TAB_filantropia
                + " (cod_filantropia INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " titulo_filantropia TEXT NOT NULL, " +
                " local_filantropia TEXT NOT NULL, " +
                " desc_filantropia TEXT NOT NULL, " +
                " dt_filantropia DATE NOT NULL); ";

        String sql_documentos = "CREATE TABLE IF NOT EXISTS " + TAB_documentos
                + " (cod_doc INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " sumula TEXT NOT NULL, " +
                " file BLOB NOT NULL, " +
                " cod_doc_tipo INTEGER NOT NULL, " +
                " FOREIGN KEY(cod_doc_tipo) REFERENCES doc_tipo(cod_doc_tipo)); ";

        String sql_doc_tipo = "CREATE TABLE IF NOT EXISTS " + TAB_doc_tipo
                + " (cod_doc_tipo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " nome_tipo TEXT NOT NULL); ";

        String sql_financas = "CREATE TABLE IF NOT EXISTS " + TAB_financas
                + " (cod_lancamento INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " valor FLOAT NOT NULL, " +
                " tipo_lacamento CHARACTER(1) NOT NULL, " +
                " dt_lancamento DATE NOT NULL); ";

        String sql_membros = "CREATE TABLE IF NOT EXISTS " + TAB_membros
                + " (cod_membro INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " nome_membro VARCHAR(255) NOT NULL, " +
                " cid_membro VARCHAR(20) NOT NULL, " +
                " endereco VARCHAR(255) NOT NULL, " +
                " CEP VARCHAR(9) NOT NULL, " +
                " telefone VARCHAR(15) NOT NULL, " +
                " nivel INT(3)); ";

        String sql_usuarios = "CREATE TABLE IF NOT EXISTS " + TAB_usuarios
                + " (cod_usuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " username TEXT  NOT NULL, " +
                " password TEXT  NOT NULL, " +
                " membro INTEGER, " +
                " FOREIGN KEY(membro) REFERENCES membros(cod_membro)); ";

        String sql_insert_membro = "INSERT INTO " + TAB_membros + "(cod_membro," +
                "nome_membro," +
                "cid_membro," +
                "endereco," +
                "CEP," +
                "telefone," +
                "nivel) " +
                "VALUES( 1," + "'Admin'," + "'0000'," +
                "'nao tem'," +
                "'nao tem'," +
                "'nao tem'," +
                "1);";


        String sql_insert_user = "INSERT INTO " + TAB_usuarios + "(cod_usuario," +
                "username," +
                "password," +
                "membro) " +
                "VALUES( 1," + "'admin'," + "'admin123'," + "1);";


        try {
            db.execSQL( sql_membros );
            db.execSQL( sql_usuarios );
            db.execSQL( sql_pessoa );
            db.execSQL( sql_trabalhos );
            db.execSQL( sql_filantropia );
            db.execSQL( sql_doc_tipo );
            db.execSQL( sql_financas );
            db.execSQL( sql_documentos );
            db.execSQL( sql_insert_membro );
            db.execSQL( sql_insert_user );
            //addAdmin();


            Log.i("INFO DB", "Sucesso ao criar a tabela" );
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao criar a tabela" + e.getMessage() );
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql_pessoa = "DROP TABLE IF EXISTS " + TABELA_PESSOA + " ;" ;
        String sql_trabalhos = "DROP TABLE IF EXISTS " + TAB_trabalhos + " ;" ;
        String sql_filantropia = "DROP TABLE IF EXISTS " + TAB_filantropia + " ;" ;
        String sql_documentos = "DROP TABLE IF EXISTS " + TAB_documentos + " ;" ;
        String sql_doc_tipo = "DROP TABLE IF EXISTS " + TAB_doc_tipo + " ;" ;
        String sql_financas = "DROP TABLE IF EXISTS " + TAB_financas + " ;" ;
        String sql_membros = "DROP TABLE IF EXISTS " + TAB_membros + " ;" ;
        String sql_usuarios = "DROP TABLE IF EXISTS " + TAB_usuarios + " ;" ;


        try {
            db.execSQL( sql_pessoa );
            db.execSQL( sql_membros );
            db.execSQL( sql_usuarios );
            db.execSQL( sql_trabalhos );
            db.execSQL( sql_filantropia );
            db.execSQL( sql_doc_tipo );
            db.execSQL( sql_financas );
            db.execSQL( sql_documentos );

            onCreate(db);
            Log.i("INFO DB", "Sucesso ao atualizar App" );
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao atualizar App" + e.getMessage() );
        }

    }

    public void addAdmin(){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("cod_usuario",1);
        cv.put("username", "admin");
        cv.put("password","admin123");
        db.insert ("usuarios",null,cv);
    }

    public String nameUser(String username, String password){
        String retorno = "";
        SQLiteDatabase db = getReadableDatabase();
        String sql_consuta = "Select membros.nome_membro from membros inner join usuarios on usuarios.membro = membros.cod_membro where usuarios.username = ? and usuarios.password = ? ;";
        Cursor cursor = db.rawQuery(sql_consuta,new String[]{username,password});
        if(cursor.moveToFirst()){
            retorno = cursor.getString(0);
        }
        cursor.close();

        return retorno;

    }


    public boolean checkUser(String username, String password){
        String[] columns = { "cod_usuario" };
        SQLiteDatabase db = getReadableDatabase();
        String selection = "username" + "=?" + " and " + "password" + "=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TAB_usuarios,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return  true;
        else
            return  false;
    }

    public void addWork(String titulo_trabalho, String desc_trabalho, String nome_membro, String dt_trab){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("titulo_trabalho",titulo_trabalho);
        cv.put("desc_trabalho", desc_trabalho);
        cv.put("nome_membro", nome_membro);
        cv.put("dt_trab",dt_trab);
        db.insert ("trabalhos",null,cv);
    }


    public void addPhilanthropies(String titulo_filantropia, String local_filantropia, String desc_filantropia, String dt_filantropia){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("titulo_filantropia",titulo_filantropia);
        cv.put("local_filantropia", local_filantropia);
        cv.put("desc_filantropia", desc_filantropia);
        cv.put("dt_filantropia",dt_filantropia);
        db.insert ("filantropia",null,cv);
    }

    public List<CalenderModel> listevents(){
        SQLiteDatabase db = getReadableDatabase();
        String sql_consuta = "SELECT filantropia.titulo_filantropia, filantropia.local_filantropia, filantropia.desc_filantropia, filantropia.dt_filantropia FROM filantropia ORDER BY filantropia.dt_filantropia ASC;";
        Cursor cursor =  db.rawQuery(sql_consuta,null);
        cursor.moveToFirst();
        String TITULO = cursor.getString(0);
        String EXTRA = cursor.getString(1);
        String DESCRICAO = cursor.getString(2);
        String DATA_EVENTO = cursor.getString(3);
        String myFormat = "dd/MM/yyyy";
        DateFormat formataData = new SimpleDateFormat(myFormat, new Locale("pt","BR"));
        Date dta = null;
        try {
            dta = formataData.parse(DATA_EVENTO);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        CalenderModel calenderModel = new CalenderModel(TITULO,EXTRA,dta,DESCRICAO);
        listCalenderModel.add(calenderModel);

        while(cursor.moveToNext())
        {
             TITULO = cursor.getString(0);
             EXTRA = cursor.getString(1);
             DESCRICAO = cursor.getString(2);
             DATA_EVENTO = cursor.getString(3);
             dta = null;
            try {
                dta = formataData.parse(DATA_EVENTO);
            } catch (ParseException e) {
                e.printStackTrace();
            }
             calenderModel = new CalenderModel(TITULO,EXTRA,dta,DESCRICAO);
            listCalenderModel.add(calenderModel);
        }

        cursor.close();

        sql_consuta = "SELECT trabalhos.titulo_trabalho, trabalhos.nome_membro, trabalhos.desc_trabalho, trabalhos.dt_trab FROM trabalhos ORDER BY trabalhos.dt_trab ASC;";
        cursor =  db.rawQuery(sql_consuta,null);
        cursor.moveToFirst();

        TITULO = cursor.getString(0);
        EXTRA = cursor.getString(1);
        DESCRICAO = cursor.getString(2);
        DATA_EVENTO = cursor.getString(3);
        dta = null;
        try {
            dta = formataData.parse(DATA_EVENTO);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calenderModel = new CalenderModel(TITULO,EXTRA,dta,DESCRICAO);
        listCalenderModel.add(calenderModel);

        while(cursor.moveToNext())
        {
            TITULO = cursor.getString(0);
            EXTRA = cursor.getString(1);
            DESCRICAO = cursor.getString(2);
            DATA_EVENTO = cursor.getString(3);
            dta = null;
            try {
                dta = formataData.parse(DATA_EVENTO);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            calenderModel = new CalenderModel(TITULO,EXTRA,dta,DESCRICAO);
            listCalenderModel.add(calenderModel);
        }
        cursor.close();
        //Collections.sort(listCalenderModel, Comparator.comparing(calenderModel::getDate));
        //listCalenderModel.sort(calenderModel.date);
        //listCalenderModel.sort(Comparator.comparing(calenderModel.date));
        return listCalenderModel;
    }

}