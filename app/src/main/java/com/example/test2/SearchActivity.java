package com.example.test2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.test2.databinding.ActivitySearchBinding;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;
    private SQLiteDatabase sqLiteDatabase;
    private DatabaseHelper databaseHelper;
    private List<Note> noteList1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Search Result");

        databaseHelper = new DatabaseHelper(this);
        sqLiteDatabase = openOrCreateDatabase("NoteDB", MODE_PRIVATE, null);
        searchForNote(getIntent().getExtras().getString("searchParam"));

    }

    private void searchForNote(String searchString) {
            Cursor cursor = databaseHelper.getNoteByDesc(searchString);
                noteList1 = new ArrayList<>();
                if(cursor.moveToFirst()){
                    do{
                        noteList1.add(
                                new Note(cursor.getInt(0),
                                        cursor.getString(1),
                                        cursor.getString(2))

                        );
                    }while (cursor.moveToNext());
                }

                SearchAdapter adapter = new SearchAdapter(
                        getApplicationContext(),
                        R.layout.search_list,
                        noteList1
                );

                binding.searchList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.menu_square:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}