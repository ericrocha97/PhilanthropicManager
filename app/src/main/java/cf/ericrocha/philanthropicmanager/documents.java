package cf.ericrocha.philanthropicmanager;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import cf.ericrocha.philanthropicmanager.helper.DBHelper;

public class documents extends AppCompatActivity {

    DBHelper db;
    Intent doc_file;
    Intent cam_file;
    EditText doc_name;
    Spinner doc_tipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);
        doc_name = findViewById(R.id.doc_name);
        doc_tipes = findViewById(R.id.doc_type);
        db = new DBHelper(this);
        loadSpinnerData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.btn_documents));
    }


    private void loadSpinnerData() {
        //DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        List<String> labels = db.getAllDocTypes();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        doc_tipes.setAdapter(dataAdapter);
    }

    public void fileinput(View v){

        //doc_file = new Intent(Intent.ACTION_GET_CONTENT);
        //Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        doc_file = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        doc_file.addCategory(Intent.CATEGORY_OPENABLE);
        doc_file.setType("*/*");
        startActivityForResult(doc_file,42);
    }

    public void opencam(View v){
        cam_file = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cam_file,0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case 42:
                if (resultCode == RESULT_OK) {
                    String name = data.getData().getPath();
                    Uri uri = null;
                    if (data != null) {
                        uri = data.getData();
                        //Log.i(TAG, "Uri: " + uri.toString());
                        Cursor cursor = this.getContentResolver()
                                .query(uri, null, null, null, null, null);

                        try {
                            // moveToFirst() returns false if the cursor has 0 rows.  Very handy for
                            // "if there's anything to look at, look at it" conditionals.
                            if (cursor != null && cursor.moveToFirst()) {

                                // Note it's called "Display Name".  This is
                                // provider-specific, and might not necessarily be the file name.
                                String displayName = cursor.getString(
                                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                doc_name.setText(displayName);
                                //Log.i(TAG, "Display Name: " + displayName);

                                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                                // If the size is unknown, the value stored is null.  But since an
                                // int can't be null in Java, the behavior is implementation-specific,
                                // which is just a fancy term for "unpredictable".  So as
                                // a rule, check if it's null before assigning to an int.  This will
                                // happen often:  The storage API allows for remote files, whose
                                // size might not be locally known.
                                String size = null;
                                if (!cursor.isNull(sizeIndex)) {
                                    // Technically the column stores an int, but cursor.getString()
                                    // will do the conversion automatically.
                                    size = cursor.getString(sizeIndex);
                                } else {
                                    size = "Unknown";
                                }
                                //Log.i(TAG, "Size: " + size);
                            }
                        } finally {
                            cursor.close();
                        }

                    }
                }
                break;

            case 0:
                if (resultCode == RESULT_OK) {
                    //Bitmap image = (Bitmap) data.getExtras().get("data");

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

