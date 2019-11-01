package cf.ericrocha.philanthropicmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cf.ericrocha.philanthropicmanager.helper.DBHelper;

public class Login extends AppCompatActivity {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;
    EditText username;
    EditText password;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.edt_login);
        password = findViewById(R.id.edt_password);
        db = new DBHelper(this);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();

        try{
            //Inserir dados
            ContentValues cv = new ContentValues();
            cv.put("cod_usuario",1);
            cv.put("username", "admin");
            cv.put("password","admin123");
            escreve.insert ("usuarios",null,cv);
            Toast.makeText(Login.this,"SHOW",Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(Login.this,"ERROR",Toast.LENGTH_SHORT).show();
            //Toast.makeText(MainActivity.this,personName + " " + personEmail,Toast.LENGTH_SHORT).show();
            Log.i("Info","Erro");
            e.printStackTrace();
        }
    }

    /*
    * String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                Boolean res = db.checkUser(user, pwd);
                if(res)
                {
                    Intent HomePage = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(HomePage);
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Login Error", Toast.LENGTH_SHORT).show();
                }
    *
    *
    *
    *
    *
    *
    * */

    public void login(View view){

        String user = username.getText().toString().trim();
        String pwd = password.getText().toString().trim();
        Boolean res = db.checkUser(user, pwd);
        if(res)
        {
            Intent intent = new Intent(this, Main.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(Login.this,"Usuario ou senha incorretos!", Toast.LENGTH_SHORT).show();
        }

        /*Intent intent = new Intent(this, Main.class);
        startActivity(intent);*/
    }

    public void exit(View view){
        finishAffinity();
    }
}
