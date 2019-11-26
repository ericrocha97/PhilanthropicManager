package cf.ericrocha.philanthropicmanager.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import cf.ericrocha.philanthropicmanager.R;
import cf.ericrocha.philanthropicmanager.helper.DBHelper;

public class user_preference extends AppCompatActivity {

    DBHelper db;
    List<String> labels;
    List<String> dados;

    Spinner lider1;
    Spinner lider2;
    Spinner lider3;
    Spinner tesoureiro;
    Spinner escrivao;
    Spinner pre_trab;
    Spinner pre_fila;
    Spinner adm;
    Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preference);
        res = getResources();
        db = new DBHelper(this);
        adm = findViewById(R.id.administration);
        lider1 = findViewById(R.id.leader_one_s);
        lider2 = findViewById(R.id.leader_two_s);
        lider3 = findViewById(R.id.leader_three_s);
        tesoureiro = findViewById(R.id.treasurer_s);
        escrivao = findViewById(R.id.registrar_s);
        pre_trab = findViewById(R.id.president_works_s);
        pre_fila = findViewById(R.id.philanthropy_president_s);
        loadSpinnerData();
        loadTela();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.user_preference));
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

    public void confirmUserPreference(View view){
        String var1 =  adm.getSelectedItem().toString();
        String var2 = lider1.getSelectedItem().toString();
        String var3 = lider2.getSelectedItem().toString();
        String var4 = lider3.getSelectedItem().toString();
        String var5 = tesoureiro.getSelectedItem().toString();
        String var6 = escrivao.getSelectedItem().toString();
        String var7 = pre_trab.getSelectedItem().toString();
        String var8 = pre_fila.getSelectedItem().toString();
        db.AddOrEditUserPreference(var1,var2,var3,var4,var5,var6,var7,var8);
        Toast.makeText(this,"Alterações Salvas!", Toast.LENGTH_SHORT).show();


    }

    public void loadTela(){
        dados = db.getUserPreference();
        Integer tamanho = labels.size();
        Integer tamDados = dados.size();
        if(tamDados!=0){
            String[] campo = res.getStringArray(R.array.Administration);

            //campo0

            for(Integer i = 0; i < tamanho; i++){
                if(campo[i].equals(dados.get(0))){
                    adm.setSelection(i);
                    i = tamanho+1;

                }
            }

            //campo1
            for(Integer i = 0; i < tamanho; i++){
                if(labels.get(i).equals(dados.get(1))){
                    lider1.setSelection(i);
                    i = tamanho+1;

                }
            }
            //campo2
            for(Integer i = 0; i < tamanho; i++){
                if(labels.get(i).equals(dados.get(2))){
                    lider2.setSelection(i);
                    i = tamanho+1;

                }
            }
            //campo3
            for(Integer i = 0; i < tamanho; i++){
                if(labels.get(i).equals(dados.get(3))){
                    lider3.setSelection(i);
                    i = tamanho+1;

                }
            }
            //campo4
            for(Integer i = 0; i < tamanho; i++){
                if(labels.get(i).equals(dados.get(4))){
                    tesoureiro.setSelection(i);
                    i = tamanho+1;

                }
            }
            //campo5
            for(Integer i = 0; i < tamanho; i++){
                if(labels.get(i).equals(dados.get(5))){
                    escrivao.setSelection(i);
                    i = tamanho+1;

                }
            }
            //campo6
            for(Integer i = 0; i < tamanho; i++){
                if(labels.get(i).equals(dados.get(6))){
                    pre_trab.setSelection(i);
                    i = tamanho+1;

                }
            }
            //campo7
            for(Integer i = 0; i < tamanho; i++){
                if(labels.get(i).equals(dados.get(7))){
                    pre_fila.setSelection(i);
                    i = tamanho+1;

                }
            }

        }


        String t = "tes";
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
        lider1.setAdapter(dataAdapter);
        lider2.setAdapter(dataAdapter);
        lider3.setAdapter(dataAdapter);
        tesoureiro.setAdapter(dataAdapter);
        escrivao.setAdapter(dataAdapter);
        pre_trab.setAdapter(dataAdapter);
        pre_fila.setAdapter(dataAdapter);
        //tt.setAdapter(dataAdapter);
    }
}
