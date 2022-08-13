package com.example.test2;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;


import com.example.test2.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final Context TAG = MainActivity.this;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private SQLiteDatabase sqLiteDatabase;
    private DatabaseHelper databaseHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        databaseHelper = new DatabaseHelper(this);
        sqLiteDatabase = openOrCreateDatabase("NoteDB", MODE_PRIVATE, null);

        binding.floatingActionButton.setOnClickListener(v -> alertDialog());
        showNoteList();

    }

    @Override
    protected void onResume() {
        super.onResume();
        showNoteList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_square:
                Toast.makeText(this, "Home button clicked", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void alertDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();



        Button createNote = dialog.findViewById(R.id.createNote);
        createNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();

                EditText noteBox = dialog.findViewById(R.id.note);
                Spinner categoryBox = dialog.findViewById(R.id.noteCategory);
                String note = noteBox.getText().toString().trim();
                String category = categoryBox.getSelectedItem().toString();

                if(note.isEmpty()){
                    noteBox.setError("Note is required");
                    noteBox.requestFocus();
                    return;
                }

                Note noteObj = new Note(category, note);
                if(databaseHelper.insertNewNote(noteObj)){
                    Toast.makeText(TAG, "Note Created", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(TAG, "Error Creating Note", Toast.LENGTH_SHORT).show();
                }

//                LinearLayout layout = binding.linearLayout ;
//                Button btn = new Button(getApplicationContext());
//                btn.setText(category);
//                layout.addView(btn);
                showNoteList();
                dialog.dismiss();



            }
        });
    }

    private void showNoteList() {
        List<String> cats = new ArrayList<>();
        Cursor cursor = databaseHelper.getUniqueCat();
        if(cursor.moveToFirst()){
            do{
                cats.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        CategoryAdapter categoryAdapter = new CategoryAdapter(
                this,
                R.layout.category_layout,
                cats
        );

        binding.catList.setAdapter(categoryAdapter);
    }

}