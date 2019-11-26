package cf.ericrocha.philanthropicmanager.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cf.ericrocha.philanthropicmanager.R;
import cf.ericrocha.philanthropicmanager.helper.DBHelper;
import cf.ericrocha.philanthropicmanager.model.session;

public class Login extends AppCompatActivity {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;
    EditText username;
    EditText password;
    DBHelper db;
    public session Session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.edt_login);
        password = findViewById(R.id.edt_password);
        db = new DBHelper(this);
        Session = Session.getInstance();
    }


    public void login(View view){

        String user = username.getText().toString().trim();
        String pwd = password.getText().toString().trim();
        if(!user.isEmpty() && !pwd.isEmpty()){
            Boolean res = db.checkUser(user, pwd);
            if(res)
            {
                Session.setLogin(user);
                Session.setUsername(db.nameUser(user,pwd));
                Intent intent = new Intent(this, Main.class);
                intent.putExtra("Username", Session.getUsername());
                // intent.putExtra("class", );
                startActivity(intent);
            }
            else
            {
                Toast.makeText(Login.this,"Usuario ou senha incorretos!", Toast.LENGTH_SHORT).show();
            }
        }else{
            if(user.isEmpty()){
                username.setError("Digite o usuário");
                username.requestFocus();
            }else{
                if(pwd.isEmpty()){
                    password.setError("Digite a senha",null);
                    password.requestFocus();
                }
            }

        }




        /*Intent intent = new Intent(this, Main.class);
        startActivity(intent);*/
    }

    public void exit(View view){
        finishAffinity();
    }
}
