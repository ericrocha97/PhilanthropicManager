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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cf.ericrocha.philanthropicmanager.helper.DBHelper;

public class works extends AppCompatActivity {

    DBHelper db;
    EditText ed_dt_work;
    EditText ed_work;
    EditText ed_desc_work;
    EditText ed_member_work;

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
        ed_member_work = findViewById(R.id.work_member_ed);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.btn_works));
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
        ed_work.setText("");
        ed_desc_work.setText("");
        ed_dt_work.setText("");
        ed_member_work.setText("");
    }

    public void work_Confirm(View view){
        String titulo = ed_work.getText().toString().trim();
        String desc = ed_desc_work.getText().toString().trim();
        String dt = ed_dt_work.getText().toString().trim();
        String membro = ed_member_work.getText().toString().trim();

        if(titulo.equals("") || desc.equals("") || dt.equals("") || membro.equals("")){
            Toast.makeText(this,"Favor preencher todos os campos!", Toast.LENGTH_SHORT).show();
        }else{
            db.addWork(titulo,desc,membro,dt);
            Toast.makeText(this,"Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
            clear();
        }



    }

    public void work_Cancel(View view){
        Toast.makeText(this,"Cadastro cancelado!", Toast.LENGTH_SHORT).show();
        clear();
    }
}
