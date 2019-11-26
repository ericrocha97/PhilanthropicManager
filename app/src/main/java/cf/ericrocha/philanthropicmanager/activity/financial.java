package cf.ericrocha.philanthropicmanager.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cf.ericrocha.philanthropicmanager.R;
import cf.ericrocha.philanthropicmanager.adapter.Adapter_financial;
import cf.ericrocha.philanthropicmanager.helper.DBHelper;
import cf.ericrocha.philanthropicmanager.model.FinancialModel;


public class financial extends AppCompatActivity {

    DBHelper db;
    private AlertDialog alert;
    private RecyclerView recyclerView;
    TextView tx_prov;
    TextView tx_desc;
    TextView tx_saldo;

    private List<FinancialModel> listFinancialModel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.btn_financial));
        db = new DBHelper(this);
        recyclerView = findViewById(R.id.view_financial);
        //AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        this.createFinancial();


        if(!listFinancialModel.isEmpty()) {
            Adapter_financial adapter = new Adapter_financial(listFinancialModel);

            RecyclerView.LayoutManager layoutManager = new
                    LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setHasFixedSize(true);

            recyclerView.addItemDecoration(new DividerItemDecoration(this,
                    LinearLayout.VERTICAL));
            recyclerView.setAdapter(adapter);
        }

        tx_prov = findViewById(R.id.tx_vl_prov);
        tx_desc = findViewById(R.id.tx_vl_desc);
        tx_saldo = findViewById(R.id.tx_vl_saldo);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        this.attSaldos();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),

                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {

                            @Override
                            public void onItemClick(View view, int position) {
                                FinancialModel financialModel = listFinancialModel.get( position );
                                String myFormat = "dd/MM/yyyy"; //In which you need put here
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));



                                builder.setNegativeButton("Deletar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // funcao deletar
                                        db.deleteFinancial(financialModel.getId());
                                        listFinancialModel.remove(position);
                                        Adapter_financial adapter = new Adapter_financial( listFinancialModel );
                                        recyclerView.setAdapter( adapter );

                                        attSaldos();


                                        Toast.makeText( getApplicationContext(),"Lançamento deletado",Toast.LENGTH_LONG).show();
                                        if(listFinancialModel.isEmpty()){
                                            Toast.makeText( getApplicationContext(),"Sem lançamentos!",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });


                                builder.setNeutralButton("Editar",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(getApplicationContext(), new_financial.class);
                                        Integer ID = financialModel.getId();
                                        intent.putExtra("ID", ID);
                                        intent.putExtra("valor", financialModel.getValor() );
                                        intent.putExtra("tipo_lanc", financialModel.getTipo_lanc() );
                                        intent.putExtra("desc_lanc", financialModel.getDesc_lanc() );
                                        //intent.putExtra("pwd", membersModel.get());
                                        Date data = financialModel.getDate_lanc();
                                        intent.putExtra("dtnasc", sdf.format(financialModel.getDate_lanc()));
                                        //intent.putExtra("DATA", sdf.format(calenderModel.getDate()));

                                        startActivity(intent);


                                    }
                                });


                                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {

                                    }
                                });
                                String titulo = "";
                                String mensagem = "";


                                Date today = new Date();
                                if(financialModel.getTipo_lanc().equals("C")){
                                    titulo = "Lançamento de Crédito";

                                }else{
                                    titulo = "Lançamento de Débito";
                                }
                                mensagem = "Descrição: "+financialModel.getDesc_lanc() + "\n" +
                                        "Valor: R$ " + df.format(financialModel.getValor());






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




    }

    private void createFinancial() {
        listFinancialModel = db.listFinanc();
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

    public void attSaldos(){
        Float cc = db.vlProv();
        Float dd = db.vlDeb();
        Float st = db.vlSaldo();

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        if(cc == 0){
            tx_prov.setText("R$ 0,00");
        }else{
            tx_prov.setText("R$ "+ df.format(cc));
        }
        if(dd == 0){
            tx_desc.setText("R$ 0,00");
        }else{
            tx_desc.setText("R$ "+ df.format(dd));
        }
        if(st == 0){
            tx_saldo.setText("R$ 0,00");
        }else{
            tx_saldo.setText("R$ "+ df.format(st));
        }
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, Main.class));
        finishAffinity();
    }

    public void new_fin(View v){
        startActivity(new Intent(this, new_financial.class));
        finishAffinity();
    }

}
