package com.example.test2;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test2.databinding.ActivitySecondBinding;

import java.util.ArrayList;
import java.util.List;


public class SecondActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    private List<Note> noteList;

    private ActivitySecondBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySecondBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        //setContentView(R.layout.activity_second);
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        databaseHelper = new DatabaseHelper(this);
        sqLiteDatabase = openOrCreateDatabase("NoteDB", MODE_PRIVATE, null);

        actionBar.setTitle(getIntent().getExtras().getString("Category"));
        showDescriptionList(getIntent().getExtras().getString("Category"));
        binding.deleteFloating.setOnClickListener(v -> deleteDialog(getIntent().getExtras().getString("Category")));
    }

    private void deleteDialog(String category) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.confirm_delete);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        Button deleteCategory = dialog.findViewById(R.id.deleteCategory);
        deleteCategory.setOnClickListener(v -> {
            dialog.dismiss();
            int rowCount = databaseHelper.deleteCategory(category);
            if (rowCount >= 1) {
                finish();
            } else {
                Toast.makeText(SecondActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDescriptionList(String category) {
        Cursor cursor = databaseHelper.getAllNotesFromCategory(category);
        noteList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                noteList.add(
                        new Note(cursor.getInt(2),
                                cursor.getString(0),
                                cursor.getString(1))

                );
            } while (cursor.moveToNext());
        }


        DescriptionAdapter adapter = new DescriptionAdapter(
                this,
                R.layout.desc_layout,
                noteList
        );

        binding.noteList.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_square:
                Toast.makeText(this, "Home button clicked", Toast.LENGTH_SHORT).show();

            case android.R.id.home:
                //For handling the back button in the toolbar
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}