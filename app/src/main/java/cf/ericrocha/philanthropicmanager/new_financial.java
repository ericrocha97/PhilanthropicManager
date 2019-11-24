package cf.ericrocha.philanthropicmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cf.ericrocha.philanthropicmanager.helper.DBHelper;

public class new_financial extends AppCompatActivity {

    DBHelper db;

    EditText fin_valor;
    EditText fin_desc;
    EditText fin_date;
    Spinner fin_tipo;
    //TODO: RECEBER OS DADOS PARA UPDATE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_financial);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Lançamentos");
        db = new DBHelper(this);
        fin_valor = findViewById(R.id.fin_valor_ed);
        fin_valor.addTextChangedListener(new NumberTextWatcher(fin_valor, "#,###"));

        fin_date = findViewById(R.id.fin_data_ed);
        fin_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(new_financial.this, date, myCalendar
                        .get(java.util.Calendar.YEAR), myCalendar.get(java.util.Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        fin_desc = findViewById(R.id.fin_desc_ed);

        fin_tipo = findViewById(R.id.fin_tipo);


    }

    java.util.Calendar myCalendar = java.util.Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(java.util.Calendar.YEAR, year);
            myCalendar.set(java.util.Calendar.MONTH, monthOfYear);
            myCalendar.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };



    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        fin_date.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, financial.class));
                finishAffinity();
                break;
            default:break;
        }
        return true;
    }


    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, financial.class));
        finishAffinity();
    }

    public void confirmNewFinancial(View v){


        String descr = fin_desc.getText().toString().trim();
        String val = fin_valor.getText().toString().trim();
        String date_fin = fin_date.getText().toString().trim();
        Integer tipo = fin_tipo.getSelectedItemPosition();
        String tipo_c = "";

        if(descr.equals("") || val.equals("") || date_fin.equals("") || tipo == 0  ){

            Toast.makeText(this, "Favor preencher todos os campos!", Toast.LENGTH_SHORT).show();

        }else{
            String val_number = val.substring(3);
            Float val_real =  Float.parseFloat(val_number.replace(",","."));
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            //SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));
            SimpleDateFormat formato = new SimpleDateFormat(myFormat,new Locale("pt","BR"));
            Date dataFormatada = null;
            try {
                dataFormatada = formato.parse(date_fin);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(tipo==1){
                tipo_c = "C";
            }
            if(tipo==2){
                tipo_c = "D";
            }
            db.addorEditFinancial(0,val_real,tipo_c,descr,dataFormatada);
            Clear();
            Toast.makeText(this, "Lançamento realizado com sucesso!", Toast.LENGTH_SHORT).show();

        }



    }

    public void Clear(){
        fin_desc.setText("");
        fin_date.setText("");
        fin_valor.setText("");
        fin_tipo.setSelection(0);
    }
}
