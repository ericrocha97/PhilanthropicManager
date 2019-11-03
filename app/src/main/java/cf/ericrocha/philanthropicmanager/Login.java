package cf.ericrocha.philanthropicmanager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

        /*Intent intent = new Intent(this, Main.class);
        startActivity(intent);*/
    }

    public void exit(View view){
        finishAffinity();
    }
}
