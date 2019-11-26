package cf.ericrocha.philanthropicmanager.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cf.ericrocha.philanthropicmanager.R;
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

            recyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(
                            getApplicationContext(),

                            recyclerView,
                            new RecyclerItemClickListener.OnItemClickListener() {

                                @Override
                                public void onItemClick(View view, int position) {
                                    MembersModel membersModel = listMembersModel.get( position );
                                    String myFormat = "dd/MM/yyyy"; //In which you need put here
                                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));
                                    Intent intent = new Intent(getApplicationContext(), new_user.class);
                                    Integer ID = membersModel.getId();
                                    intent.putExtra("ID", ID);
                                    intent.putExtra("nome_membro", membersModel.getNome() );
                                    intent.putExtra("cid_membro", membersModel.getCid() );
                                    intent.putExtra("endereco", membersModel.getEndereco() );
                                    intent.putExtra("CEP", membersModel.getCep());
                                    intent.putExtra("telefone", membersModel.getTelefone() );
                                    intent.putExtra("nivel", membersModel.getNivel());
                                    //intent.putExtra("pwd", membersModel.get());
                                    Date data = membersModel.getDt_nasc();
                                    intent.putExtra("dtnasc", sdf.format(membersModel.getDt_nasc()));
                                    //intent.putExtra("DATA", sdf.format(calenderModel.getDate()));

                                    startActivity(intent);


                                }
                                @Override
                                public void onLongItemClick(View view, int position)

                                {

                                }
                                @Override
                                public void onItemClick(AdapterView<?> adapterView,

                                                        View view, int i, long l) {
                                }
                            }
                    )
            );
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
