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

import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cf.ericrocha.philanthropicmanager.helper.DBHelper;

public class new_user extends AppCompatActivity {

    DBHelper db;

    EditText ed_dt_nasc;
    EditText ed_nome;
    EditText ed_cid;
    EditText ed_endereco;
    EditText ed_cep;
    EditText ed_telefone;
    Spinner ed_nivel;
    EditText ed_pwd;


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

        ed_dt_nasc.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        db = new DBHelper(this);
        test = findViewById(R.id.user_date_l);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(new_user.this, date, myCalendar
                        .get(java.util.Calendar.YEAR), myCalendar.get(java.util.Calendar.MONTH),
                        myCalendar.get(java.util.Calendar.DAY_OF_MONTH)).show();
            }
        });
        ed_dt_nasc = findViewById(R.id.user_date_ed);
        ed_dt_nasc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(new_user.this, date, myCalendar
                        .get(java.util.Calendar.YEAR), myCalendar.get(java.util.Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        ed_nome = findViewById(R.id.user_name_ed);
        ed_cid = findViewById(R.id.user_cid_ed);
        ed_endereco = findViewById(R.id.user_address_ed);
        ed_cep = findViewById(R.id.user_cep_ed);
        ed_telefone = findViewById(R.id.user_tel_ed);
        ed_nivel = findViewById(R.id.level);
        ed_pwd = findViewById(R.id.user_pwd_ed);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Cadastro de membros");
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, members.class));
                finishAffinity();
                break;
            default:break;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, members.class));
        finishAffinity();
    }

    public void confirmNewMember(View view){
        String nome = ed_nome.getText().toString().trim();
        String cid = ed_cid.getText().toString().trim();
        String endereco = ed_endereco.getText().toString().trim();
        String cep = ed_cep.getText().toString().trim();
        String telefone = ed_telefone.getText().toString().trim();
        Integer nivel = ed_nivel.getSelectedItemPosition()+1;
        String senha = ed_pwd.getText().toString().trim();
        String dt_nasc = ed_dt_nasc.getText().toString().trim();
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        //SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));
        SimpleDateFormat formato = new SimpleDateFormat(myFormat,new Locale("pt","BR"));
        Date dataFormatada = null;
        try {
            dataFormatada = formato.parse(dt_nasc);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(nome.equals("") || cid.equals("") || endereco.equals("") || cep.equals("") || telefone.equals("") || senha.equals("") || dt_nasc.equals("")){
            Toast.makeText(this,"Favor preencher todos os campos!", Toast.LENGTH_SHORT).show();
        }else{
            db.addOrEditMember(0,nome,cid,endereco,cep,telefone,dataFormatada,nivel,senha);
            Toast.makeText(this,"Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
            clear();
        }



    }

    public void Member_Cancel(View view){
        Toast.makeText(this,"Cadastro cancelado!", Toast.LENGTH_SHORT).show();
        clear();
    }

    public void clear(){
        ed_nome.setText("");
        ed_cid.setText("");
        ed_endereco.setText("");
        ed_cep.setText("");
        ed_telefone.setText("");
        ed_pwd.setText("");
        ed_dt_nasc.setText("");
    }
}
