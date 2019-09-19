package cf.ericrocha.philanthropicmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void settings(View view){
        Intent intent = new Intent(this, settings.class);
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
