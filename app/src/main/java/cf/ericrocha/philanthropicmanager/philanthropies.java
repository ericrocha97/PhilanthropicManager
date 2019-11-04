package cf.ericrocha.philanthropicmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cf.ericrocha.philanthropicmanager.helper.DBHelper;


public class philanthropies extends AppCompatActivity {


    DBHelper db;
    EditText ed_dt_phil;
    EditText ed_phil;
    EditText ed_desc_phil;
    EditText ed_place_phil;
    TextInputLayout test;
    Integer ID;

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

        ed_dt_phil.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_philanthropies);
        Intent it = getIntent();
        ID = it.getIntExtra("ID",0);
        String TITULO = it.getStringExtra("TITULO");
        String DESC = it.getStringExtra("DESC");
        String EXTRA = it.getStringExtra("EXTRA");
        String DATA = it.getStringExtra("DATA");
        db = new DBHelper(this);
        test = findViewById(R.id.philanthropy_date_l);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(philanthropies.this, date, myCalendar
                        .get(java.util.Calendar.YEAR), myCalendar.get(java.util.Calendar.MONTH),
                        myCalendar.get(java.util.Calendar.DAY_OF_MONTH)).show();
            }
        });
        ed_dt_phil = findViewById(R.id.philanthropy_date_ed);
        ed_dt_phil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(philanthropies.this, date, myCalendar
                        .get(java.util.Calendar.YEAR), myCalendar.get(java.util.Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        ed_phil = findViewById(R.id.philanthropy_title_ed);
        ed_desc_phil = findViewById(R.id.philanthropy_desc_ed);
        ed_place_phil = findViewById(R.id.philanthropy_place_ed);

        if(ID != 0){
            ed_phil.setText(TITULO);
            ed_desc_phil.setText(DESC);
            ed_place_phil.setText(EXTRA);
            ed_dt_phil.setText(DATA);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.btn_philanthropies));
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


    public void clear(){
        ed_phil.setText("");
        ed_desc_phil.setText("");
        ed_dt_phil.setText("");
        ed_place_phil.setText("");
    }

    public void philanthropies_Confirm(View view){
        String titulo = ed_phil.getText().toString().trim();
        String desc = ed_desc_phil.getText().toString().trim();
        String dt = ed_dt_phil.getText().toString().trim();
        String lugar = ed_place_phil.getText().toString().trim();
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        //SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));
        SimpleDateFormat formato = new SimpleDateFormat(myFormat,new Locale("pt","BR"));
        Date dataFormatada = null;
        try {
            dataFormatada = formato.parse(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(titulo.equals("") || desc.equals("") || dt.equals("") || lugar.equals("")){
            Toast.makeText(this,"Favor preencher todos os campos!", Toast.LENGTH_SHORT).show();
        }else{
            if(ID != 0){
                db.addOrEditPhilanthropies(ID,titulo,lugar, desc,dataFormatada);
                Toast.makeText(this,"Cadastro alterado com sucesso!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, cf.ericrocha.philanthropicmanager.Calendar.class));
                finishAffinity();

            }else{
                db.addOrEditPhilanthropies(0,titulo,lugar, desc,dataFormatada);
                Toast.makeText(this,"Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                clear();
            }

        }



    }

    public void philanthropies_Cancel(View view){
        Toast.makeText(this,"Cadastro cancelado!", Toast.LENGTH_SHORT).show();
        clear();
    }
}
