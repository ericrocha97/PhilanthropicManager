package cf.ericrocha.philanthropicmanager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class documents extends AppCompatActivity {

    Intent doc_file;
    Intent cam_file;
    EditText doc_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);
        doc_name = findViewById(R.id.doc_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.btn_documents));
    }

    public void fileinput(View v){

        doc_file = new Intent(Intent.ACTION_GET_CONTENT);
        doc_file.setType("*/*");
        startActivityForResult(doc_file,10);
    }

    public void opencam(View v){
        cam_file = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cam_file,0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case 10:
                if (resultCode == RESULT_OK) {
                    String name = data.getData().getPath();
                    doc_name.setText(name);
                }
                break;

            case 0:
                if (resultCode == RESULT_OK) {
                    Bitmap image = (Bitmap) data.getExtras().get("data");

                    doc_name.setText(R.string.cam_image_text);
                }
                break;
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
}

