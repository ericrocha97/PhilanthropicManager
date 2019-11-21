package cf.ericrocha.philanthropicmanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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

        Float cc = db.vlProv();
        Float dd = db.vlDeb();
        Float st = db.vlSaldo();

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        tx_prov.setText("R$ "+ df.format(cc));
        tx_desc.setText("R$ "+ df.format(dd));
        tx_saldo.setText("R$ "+ df.format(st));




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


    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, Main.class));
        finishAffinity();
    }

}
