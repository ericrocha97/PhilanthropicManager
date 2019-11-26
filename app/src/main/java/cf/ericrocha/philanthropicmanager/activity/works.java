package cf.ericrocha.philanthropicmanager.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cf.ericrocha.philanthropicmanager.R;
import cf.ericrocha.philanthropicmanager.helper.DBHelper;

public class works extends AppCompatActivity {

    DBHelper db;
    EditText ed_dt_work;
    EditText ed_work;
    EditText ed_desc_work;
    EditText ed_member_work;
    Integer ID;
    Spinner tt;
    List<String> labels;

    TextInputLayout test;

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

        ed_dt_work.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_works);
        Intent it = getIntent();
        ID = it.getIntExtra("ID",0);
        String TITULO = it.getStringExtra("TITULO");
        String DESC = it.getStringExtra("DESC");
        String EXTRA = it.getStringExtra("EXTRA");
        String DATA = it.getStringExtra("DATA");

        db = new DBHelper(this);
        test = findViewById(R.id.work_date_l);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(works.this, date, myCalendar
                        .get(java.util.Calendar.YEAR), myCalendar.get(java.util.Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        ed_dt_work = findViewById(R.id.work_date_ed);
        ed_dt_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(works.this, date, myCalendar
                        .get(java.util.Calendar.YEAR), myCalendar.get(java.util.Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        ed_work = findViewById(R.id.work_title_ed);
        ed_desc_work = findViewById(R.id.work_desc_ed);
        //ed_member_work = findViewById(R.id.work_member_ed);
        tt = findViewById(R.id.teste_t);
        loadSpinnerData();
        if(ID != 0){
            ed_work.setText(TITULO);
            ed_desc_work.setText(DESC);
            Integer tamanho = labels.size();
            for(Integer i = 0; i < tamanho; i++){
                if(labels.get(i).equals(EXTRA)){
                    tt.setSelection(i);
                }
            }
            //ed_member_work.setText(EXTRA);
            ed_dt_work.setText(DATA);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.btn_works));
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(ID != 0){
                    startActivity(new Intent(this, cf.ericrocha.philanthropicmanager.activity.Calendar.class));
                    finishAffinity();
                }else {
                    startActivity(new Intent(this, Main.class));
                    finishAffinity();
                }
                break;
            default:break;
        }
        return true;
    }


    @Override
    public void onBackPressed(){
        if(ID != 0){
            startActivity(new Intent(this, cf.ericrocha.philanthropicmanager.activity.Calendar.class));
            finishAffinity();
        }else {
            startActivity(new Intent(this, Main.class));
            finishAffinity();
        }
    }
    public void clear(){
        ed_work.setText("");
        ed_desc_work.setText("");
        ed_dt_work.setText("");
        tt.setSelection(0);
        //ed_member_work.setText("");
    }

    public void work_Confirm(View view){

        String titulo = ed_work.getText().toString().trim();
        String desc = ed_desc_work.getText().toString().trim();
        String dt = ed_dt_work.getText().toString().trim();
        //String membro = "TESTE";//ed_member_work.getText().toString().trim();
        String membro = tt.getSelectedItem().toString();
        String j = "jj";

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        //SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));
        SimpleDateFormat formato = new SimpleDateFormat(myFormat,new Locale("pt","BR"));
        Date dataFormatada = null;
        try {
            dataFormatada = formato.parse(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Integer SpinnerSelect = tt.getSelectedItemPosition();
        if(titulo.equals("") || desc.equals("") || dt.equals("") || membro.equals("") || SpinnerSelect.equals(0)){
            Toast.makeText(this,"Favor preencher todos os campos!", Toast.LENGTH_SHORT).show();

        }else{
            if(ID != 0){
                db.addOrEditWork(ID,titulo,desc,membro,dataFormatada);
                Toast.makeText(this,"Cadastro alterado com sucesso!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, cf.ericrocha.philanthropicmanager.activity.Calendar.class));
                finishAffinity();

            }else{
                db.addOrEditWork(0,titulo,desc,membro,dataFormatada);
                Toast.makeText(this,"Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                clear();
            }

        }



    }

    public void work_Cancel(View view){
        if(ID != 0){
            startActivity(new Intent(this, cf.ericrocha.philanthropicmanager.activity.Calendar.class));
            finishAffinity();
        }else{
            Toast.makeText(this,"Cadastro cancelado!", Toast.LENGTH_SHORT).show();
            clear();
        }

    }

    private void loadSpinnerData() {
        //DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        labels = db.getAllMembers();
        labels.add(0,"Selecione um membro");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        tt.setAdapter(dataAdapter);
    }
}
