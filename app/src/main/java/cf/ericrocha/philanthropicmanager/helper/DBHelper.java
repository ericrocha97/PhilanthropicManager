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

import cf.ericrocha.philanthropicmanager.model.CalenderModel;
import cf.ericrocha.philanthropicmanager.model.MembersModel;


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
    private List<MembersModel> listMembersModel = new ArrayList<>();

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
                " dt_trab DATETIME NOT NULL, " +
                " cor_trab INTEGER DEFAULT 0); ";

        String sql_filantropia = "CREATE TABLE IF NOT EXISTS " + TAB_filantropia
                + " (cod_filantropia INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " titulo_filantropia TEXT NOT NULL, " +
                " local_filantropia TEXT NOT NULL, " +
                " desc_filantropia TEXT NOT NULL, " +
                " dt_filantropia DATETIME NOT NULL, " +
                " cor_fila INTEGER DEFAULT 1); ";

        String sql_documentos = "CREATE TABLE IF NOT EXISTS " + TAB_documentos
                + " (cod_doc INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " sumula TEXT NOT NULL, " +
                " desc_doc TEXT NOT NULL, " +
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
                " dt_nascimento DATE NOT NULL, " +
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
                "dt_nascimento," +
                "nivel) " +
                "VALUES( 1," + "'Admin'," + "'0000'," +
                "'nao tem'," +
                "'nao tem'," +
                "'nao tem'," +
                "'1900/01/01'," +
                "1);";


        String sql_insert_user = "INSERT INTO " + TAB_usuarios + "(cod_usuario," +
                "username," +
                "password," +
                "membro) " +
                "VALUES( 1," + "'admin'," + "'admin123'," + "1);";

        String sql_insert_doc_tipos = "INSERT INTO " + TAB_doc_tipo + "(cod_doc_tipo," +
                "nome_tipo) " +
                "VALUES ( 1, 'Atas')," +
                "       (2, 'Protocolo');";


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
            db.execSQL( sql_insert_doc_tipos );
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

    public void addOrEditWork(Integer ID ,String titulo_trabalho, String desc_trabalho, String nome_membro, Date dt){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        String dt_trab = (sdf.format(dt.getTime()));
        if(ID == 0){
            //insert
            String sql = "INSERT INTO trabalhos (titulo_trabalho,desc_trabalho,nome_membro,dt_trab)" +
                    "VALUES('"+titulo_trabalho+"','"+desc_trabalho+"','"+nome_membro+"','"+dt_trab+"')";
            db.execSQL(sql);
        }else{
            //edit
            String sql = "UPDATE trabalhos SET " +
                    "titulo_trabalho = '"+titulo_trabalho+"'," +
                    "desc_trabalho = '"+desc_trabalho+"'," +
                    "nome_membro = '"+nome_membro+"'," +
                    "dt_trab = '"+dt_trab+"'" +
                    " WHERE " +
                    "cod_trabalho = "+ ID;
            db.execSQL(sql);

        }




    }


    public void addOrEditPhilanthropies(Integer ID, String titulo_filantropia, String local_filantropia, String desc_filantropia, Date dt){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        String dt_fila = (sdf.format(dt.getTime()));
        if(ID == 0){
            //insert
            /*cv.put("titulo_filantropia",titulo_filantropia);
            cv.put("local_filantropia", local_filantropia);
            cv.put("desc_filantropia", desc_filantropia);
            //cv.put("dt_filantropia",dt_filantropia);
            db.insert ("filantropia",null,cv);*/

            String sql = "INSERT INTO filantropia (titulo_filantropia,local_filantropia,desc_filantropia,dt_filantropia)" +
                    "VALUES('"+titulo_filantropia+"','"+local_filantropia+"','"+desc_filantropia+"','"+dt_fila+"')";
            db.execSQL(sql);
        }else{
            //edit
            /*cv.put("cod_filantropia",ID);
            cv.put("titulo_filantropia",titulo_filantropia);
            cv.put("local_filantropia", local_filantropia);
            cv.put("desc_filantropia", desc_filantropia);
            //cv.put("dt_filantropia",dt_filantropia);
            db.update ("filantropia",cv,"cod_filantropia = "+ ID,null);*/
            String sql = "UPDATE filantropia SET " +
                    "titulo_filantropia = '"+titulo_filantropia+"'," +
                    "local_filantropia = '"+local_filantropia+"'," +
                    "desc_filantropia = '"+desc_filantropia+"'," +
                    "dt_filantropia = '"+dt_fila+"'" +
                    " WHERE " +
                    "cod_filantropia = "+ ID;
            db.execSQL(sql);
        }
    }



    /*public void addDoc(String desc_doc, String sumula, Bitmap file, INTEGER cod_doc_tipo){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("desc_doc",desc_doc);
        cv.put("sumula",sumula);
        cv.put("file", file);
        cv.put("cod_doc_tipo", cod_doc_tipo);
        db.insert ("usuarios",null,cv);
    }*/

    public List<CalenderModel> listevents(){
        SQLiteDatabase db = getReadableDatabase();
        //String sql_consuta = "SELECT filantropia.titulo_filantropia, filantropia.local_filantropia, filantropia.desc_filantropia, filantropia.dt_filantropia FROM filantropia ORDER BY filantropia.dt_filantropia ASC;";
        String sql_consuta = "SELECT " +
                "trabalhos.cod_trabalho AS ID, " +
                "trabalhos.titulo_trabalho AS TITULO, " +
                "trabalhos.nome_membro AS EXTRA, " +
                "trabalhos.desc_trabalho AS DESCRICAO, " +
                "trabalhos.dt_trab AS DATA_EVENTO," +
                "trabalhos.cor_trab AS COR  " +
                "FROM trabalhos " +
               // "ORDER BY DATA_EVENTO ASC" +
                "UNION ALL " +
                "SELECT " +
                "filantropia.cod_filantropia AS ID, " +
                "filantropia.titulo_filantropia AS TITULO, " +
                "filantropia.local_filantropia AS EXTRA, " +
                "filantropia.desc_filantropia AS DESCRICAO, " +
                "filantropia.dt_filantropia AS DATA_EVENTO, " +
                "filantropia.cor_fila AS COR " +
                "FROM filantropia " +
                "ORDER BY DATA_EVENTO ASC;";
        Cursor cursor =  db.rawQuery(sql_consuta,null);
        if(cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount() != 0) {
                Integer ID = cursor.getInt(0);
                String TITULO = cursor.getString(1);
                String EXTRA = cursor.getString(2);
                String DESCRICAO = cursor.getString(3);
                String DATA_EVENTO = cursor.getString(4);
                Integer COR = cursor.getInt(5);
                /*String myFormat = "dd/MM/yyyy";
                DateFormat formataData = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));*/
                DateFormat formatUS = new SimpleDateFormat("yyyy/MM/dd");
                Date date = null;
                try {
                    date = formatUS.parse(DATA_EVENTO);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //Depois formata data
                DateFormat formatBR = new SimpleDateFormat("dd/mm/yyyy");
                String dateFormated = formatBR.format(date);

                /*Date dta = null;
                try {
                    dta = formataData.parse(DATA_EVENTO);
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/
                CalenderModel calenderModel = new CalenderModel(ID, TITULO, EXTRA, date, DESCRICAO, COR);
                listCalenderModel.add(calenderModel);

                while (cursor.moveToNext()) {
                    ID = cursor.getInt(0);
                    TITULO = cursor.getString(1);
                    EXTRA = cursor.getString(2);
                    DESCRICAO = cursor.getString(3);
                    DATA_EVENTO = cursor.getString(4);
                    COR = cursor.getInt(5);
                    date = null;
                    try {
                        date = formatUS.parse(DATA_EVENTO);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    /*dta = null;
                    try {
                        dta = formataData.parse(DATA_EVENTO);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }*/
                    calenderModel = new CalenderModel(ID, TITULO, EXTRA, date, DESCRICAO, COR);
                    listCalenderModel.add(calenderModel);
                }

                cursor.close();
            }
        }

        return listCalenderModel;
    }
    public void deleteWork(Integer ID){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("trabalhos","cod_trabalho = "+ ID,null);
    }
    public void deletePhilanthropies(Integer ID){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("filantropia","cod_filantropia = "+ ID,null);
    }


    public List<String> getAllDocTypes(){
        List<String> list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TAB_doc_tipo;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));//adding 2nd column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return list;
    }


    public List<MembersModel> listMembers(){
        SQLiteDatabase db = getReadableDatabase();
        String sql_consuta = "SELECT * FROM membros WHERE cod_membro != 1 ;";

        Cursor cursor =  db.rawQuery(sql_consuta,null);
        if(cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount() != 0) {
                Integer ID = cursor.getInt(0);
                String NOME = cursor.getString(1);
                String CID = cursor.getString(2);
                String ENDERECO = cursor.getString(3);
                String CEP = cursor.getString(4);
                String TELEFONE = cursor.getString(5);
                String DATE_NASCIMENTO = cursor.getString(6);
                Integer NIVEL = cursor.getInt(7);
                DateFormat formatUS = new SimpleDateFormat("yyyy/MM/dd");
                Date date = null;
                try {
                    date = formatUS.parse(DATE_NASCIMENTO);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                MembersModel membersModel = new MembersModel(ID,NOME,CID,date,ENDERECO,CEP,TELEFONE,NIVEL);
                listMembersModel.add(membersModel);

                while (cursor.moveToNext()) {
                    ID = cursor.getInt(0);
                    NOME = cursor.getString(1);
                    CID = cursor.getString(2);
                    ENDERECO = cursor.getString(3);
                    CEP = cursor.getString(4);
                    TELEFONE = cursor.getString(5);
                    DATE_NASCIMENTO = cursor.getString(6);
                    NIVEL = cursor.getInt(7);
                    date = null;
                    try {
                        date = formatUS.parse(DATE_NASCIMENTO);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    membersModel = new MembersModel(ID,NOME,CID,date,ENDERECO,CEP,TELEFONE,NIVEL);
                    listMembersModel.add(membersModel);
                }

                cursor.close();
            }
        }

        return listMembersModel;
    }
    public void addOrEditMember(Integer ID, String nome_membro, String cid_membro, String endereco, String CEP, String telefone, Date dt, Integer nivel, String pwd){
        SQLiteDatabase escreve = getWritableDatabase();
        SQLiteDatabase le = getReadableDatabase();
        ContentValues cv = new ContentValues();
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        String dt_nascimento = (sdf.format(dt.getTime()));
        /*
        *
        * tring sql_membros = "CREATE TABLE IF NOT EXISTS " + TAB_membros
                + " (cod_membro INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " nome_membro VARCHAR(255) NOT NULL, " +
                " cid_membro VARCHAR(20) NOT NULL, " +
                " endereco VARCHAR(255) NOT NULL, " +
                " CEP VARCHAR(9) NOT NULL, " +
                " telefone VARCHAR(15) NOT NULL, " +
                " dt_nascimento DATE NOT NULL, " +
                " nivel INT(3)); ";
        *
        * */
        if(ID == 0){
            //insert

            /*String sql = "INSERT INTO filantropia (titulo_filantropia,local_filantropia,desc_filantropia,dt_filantropia)" +
                    "VALUES('"+titulo_filantropia+"','"+local_filantropia+"','"+desc_filantropia+"','"+dt_fila+"')";
            db.execSQL(sql);*/
            String sql_insert_membro = "INSERT INTO " + TAB_membros + "(" +
                    "nome_membro," +
                    "cid_membro," +
                    "endereco," +
                    "CEP," +
                    "telefone," +
                    "dt_nascimento," +
                    "nivel) " +
                    "VALUES( '"+nome_membro+"'," +
                    "'"+cid_membro+"'," +
                    "'"+endereco+"'," +
                    "'"+CEP+"'," +
                    "'"+telefone+"'," +
                    "'"+dt_nascimento+"'," +
                    ""+nivel+");";
            escreve.execSQL(sql_insert_membro);

            String sql_consuta = "SELECT cod_membro FROM membros WHERE cid_membro == "+cid_membro+" ;";
            Cursor cursor = le.rawQuery(sql_consuta,null);
            Integer cod_membro = 0;
            if(cursor != null) {
                cursor.moveToFirst();
                if (cursor.getCount() != 0) {
                    cod_membro = cursor.getInt(0);
                }
            }

            String sql_insert_user = "INSERT INTO " + TAB_usuarios + "(" +
                    "username," +
                    "password," +
                    "membro) " +
                    "VALUES('"+cid_membro+"'," + "'"+pwd+"'," + ""+cod_membro+");";
            escreve.execSQL(sql_insert_user);
        }else{
            //edit
            String sql_update_member = "UPDATE membros SET " +
                    "nome_membro = '"+nome_membro+"'," +
                    "cid_membro = '"+cid_membro+"'," +
                    "endereco = '"+endereco+"'," +
                    "CEP = '"+CEP+"'," +
                    "telefone = '"+telefone+"'," +
                    "dt_nascimento = '"+dt_nascimento+"'," +
                    "nivel = '"+nivel+"'" +
                    " WHERE " +
                    "cod_membro = "+ ID;

            escreve.execSQL(sql_update_member);


            String sql_consuta = "SELECT cod_usuario FROM usuarios WHERE membro == "+ID+" ;";
            Cursor cursor = le.rawQuery(sql_consuta,null);
            Integer cod_usuario = 0;
            if(cursor != null) {
                cursor.moveToFirst();
                if (cursor.getCount() != 0) {
                    cod_usuario = cursor.getInt(0);
                }
            }
            String sql_update_user = "UPDATE usuarios SET " +
                    "username = '"+cid_membro+"', " +
                    "password = '"+pwd+"', " +
                    "membro = "+ID+" " +
                    " WHERE " +
                    "cod_usuario = "+ cod_usuario;

            escreve.execSQL(sql_update_user);
        }
    }

}

