package cf.ericrocha.philanthropicmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cf.ericrocha.philanthropicmanager.activity.RecyclerItemClickListener;
import cf.ericrocha.philanthropicmanager.adapter.Adapter;
import cf.ericrocha.philanthropicmanager.helper.DBHelper;
import cf.ericrocha.philanthropicmanager.model.CalenderModel;

public class Calendar extends AppCompatActivity {

    DBHelper db;
    private AlertDialog alert;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        this.createCalendar();

        if(!listCalenderModel.isEmpty()){
            Adapter adapter = new Adapter( listCalenderModel );

            RecyclerView.LayoutManager layoutManager = new
                    LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setHasFixedSize(true);

            recyclerView.addItemDecoration( new DividerItemDecoration(this,
                    LinearLayout.VERTICAL));
            recyclerView.setAdapter( adapter );

            recyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(
                            getApplicationContext(),

                            recyclerView,
                            new RecyclerItemClickListener.OnItemClickListener() {

                                @Override

                                public void onItemClick(View view, int position) {
                                    CalenderModel calenderModel = listCalenderModel.get( position );
                                    builder.setPositiveButton("Deletar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            if(calenderModel.getCor().equals(0)){
                                                db.deleteWork(calenderModel.getId());
                                                listCalenderModel.remove(position);
                                                Adapter adapter = new Adapter( listCalenderModel );
                                                recyclerView.setAdapter( adapter );

                                            }else{
                                                db.deletePhilanthropies(calenderModel.getId());
                                                listCalenderModel.remove(position);
                                                Adapter adapter = new Adapter( listCalenderModel );
                                                recyclerView.setAdapter( adapter );
                                            }

                                            Toast.makeText( getApplicationContext(),"Evento deletado",Toast.LENGTH_LONG).show();
                                            if(listCalenderModel.isEmpty()){
                                                Toast.makeText( getApplicationContext(),"Calendario vazio",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                                    builder.setNeutralButton("OK",new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                        }
                                    });


                                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                        @Override
                                        public void onDismiss(DialogInterface dialog) {

                                        }
                                    });
                                    String titulo = "";
                                    String mensagem = "";
                                    String myFormat = "dd/MM/yyyy"; //In which you need put here
                                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));


                                    if(calenderModel.getCor().equals(0)){
                                        titulo = "Trabalho";
                                        mensagem = "Na data " +  sdf.format(calenderModel.getDate()) +
                                                " será realizado " + calenderModel.getTitle() + " que será apresentado pelo membro " +
                                                calenderModel.getExtra() + "\n" +
                                                "Descrição: " + calenderModel.getDesc();

                                    }else{
                                        titulo = "Filantropia";
                                        mensagem = "Na data " +  sdf.format(calenderModel.getDate()) +
                                                " será realizado " + calenderModel.getTitle() + " que ira ocorrer em  " +
                                                calenderModel.getExtra() + "\n" +
                                                "Descrição: " + calenderModel.getDesc();
                                    }



                                    builder.setTitle(titulo);
                                    builder.setMessage(mensagem);


                                    alert = builder.create();
                                    alert.show();

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
        } else{
            Toast.makeText( getApplicationContext(),"Calendario vazio",Toast.LENGTH_LONG).show();
        }

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
