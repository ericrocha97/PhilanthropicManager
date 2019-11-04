package cf.ericrocha.philanthropicmanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cf.ericrocha.philanthropicmanager.adapter.Adapter_members;
import cf.ericrocha.philanthropicmanager.helper.DBHelper;
import cf.ericrocha.philanthropicmanager.model.MembersModel;

public class members extends AppCompatActivity {

    DBHelper db;
    private AlertDialog alert;
    private RecyclerView recyclerView;

    private List<MembersModel> listMembersModel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Membros");
        db = new DBHelper(this);
        recyclerView = findViewById(R.id.member_view);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        this.createList();

        if(!listMembersModel.isEmpty()) {
            Adapter_members adapter = new Adapter_members(listMembersModel);

            RecyclerView.LayoutManager layoutManager = new
                    LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setHasFixedSize(true);

            recyclerView.addItemDecoration(new DividerItemDecoration(this,
                    LinearLayout.VERTICAL));
            recyclerView.setAdapter(adapter);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, Settings.class));
                finishAffinity();
                break;
            default:break;
        }
        return true;
    }


    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, Settings.class));
        finishAffinity();
    }

    public void createList() {
        listMembersModel = db.listMembers();
    }

    public void openNewMember(View view){
        Intent intent = new Intent(getApplicationContext(), new_user.class);
        startActivity(intent);


    }
}
