package cf.ericrocha.philanthropicmanager.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


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

    public DBHelper(Context context) {
        super(context, NOME_DB, null, VERSION);
    }
    //public DatabaseHelper(Context context) {
    //    super(context, DATABASE_NAME, null, 1);
    //}

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql_pessoa = "CREATE TABLE IF NOT EXISTS " + TABELA_PESSOA
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " nome TEXT NOT NULL, " +
                " idade INT(3)); ";

        String sql_trabalhos = "CREATE TABLE IF NOT EXISTS " + TAB_trabalhos
                + " (cod_trabalho INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " desc_trabalho TEXT NOT NULL, " +
                " nome_membro TEXT NOT NULL, " +
                " dt_trab DATE NOT NULL); ";

        String sql_filantropia = "CREATE TABLE IF NOT EXISTS " + TAB_filantropia
                + " (cod_filantropia INTEGER PRIMARY KEY AUTOINCREMENT, " +
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


        try {
            db.execSQL( sql_membros );
            db.execSQL( sql_usuarios );
            db.execSQL( sql_pessoa );
            db.execSQL( sql_trabalhos );
            db.execSQL( sql_filantropia );
            db.execSQL( sql_doc_tipo );
            db.execSQL( sql_financas );
            db.execSQL( sql_documentos );



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

}