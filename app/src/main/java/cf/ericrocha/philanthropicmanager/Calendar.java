package cf.ericrocha.philanthropicmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cf.ericrocha.philanthropicmanager.adapter.Adapter;
import cf.ericrocha.philanthropicmanager.helper.DBHelper;
import cf.ericrocha.philanthropicmanager.model.CalenderModel;

public class Calendar extends AppCompatActivity {

    DBHelper db;

    private RecyclerView recyclerView;

    private List<CalenderModel> listCalenderModel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.btn_calendar));
        db = new DBHelper(this);
        recyclerView = findViewById(R.id.recyclerView);

        this.createCalendar();

        Adapter adapter = new Adapter( listCalenderModel );

        RecyclerView.LayoutManager layoutManager = new
                LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration( new DividerItemDecoration(this,
                LinearLayout.VERTICAL));
        recyclerView.setAdapter( adapter );



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, Main.class));
                finishAffinity();
                break;
            default:break;
        }
        return true;
    }


    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, Main.class));
        finishAffinity();
    }

    public void createCalendar() {
        listCalenderModel = db.listevents();
    }
}
