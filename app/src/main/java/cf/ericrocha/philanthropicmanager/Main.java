package cf.ericrocha.philanthropicmanager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import cf.ericrocha.philanthropicmanager.helper.DBHelper;

public class Main extends AppCompatActivity {

    TextView wellcome;
    DBHelper db;
    private SQLiteDatabase le;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBHelper(this);
        le = db.getReadableDatabase();
        wellcome = findViewById(R.id.tx_welcome);


    }

    public void financial(View view){
        Intent intent = new Intent(this, financial.class);
        startActivity(intent);
        finishAffinity();
    }

    public void works(View view){
        Intent intent = new Intent(this, works.class);
        startActivity(intent);
    }

    public void philanthropies(View view){
        Intent intent = new Intent(this, philanthropies.class);
        startActivity(intent);
    }

    public void documents(View view){
        Intent intent = new Intent(this, documents.class);
        startActivity(intent);
    }

    public void settings(View view){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void calendar(View view){
        Intent intent = new Intent(this, Calendar.class);
        startActivity(intent);
    }

    public void exit(View view){
        startActivity(new Intent(this, Login.class));
        finishAffinity();
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, Main.class));
        finishAffinity();
    }
}
