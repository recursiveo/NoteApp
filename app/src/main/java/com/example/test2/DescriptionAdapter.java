package com.example.test2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DescriptionAdapter extends ArrayAdapter {

    private Context context;
    private int layoutRes;
    private List<Note> notes;
    private DatabaseHelper databaseHelper;

    public DescriptionAdapter(@NonNull Context context, int resource, @NonNull List<Note> notes) {
        super(context, resource, notes);
        this.context = context;
        this.layoutRes = resource;
        this.notes = notes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;
        if (view == null) view = inflater.inflate(layoutRes, null);
        databaseHelper = new DatabaseHelper(getContext());

        //Button button = view.findViewById(R.id.categoryBtn);

       // button.setText(cats.get(position));

        TextView desc = view.findViewById(R.id.description);
        desc.setText(notes.get(position).getDesc());
        TextView category = view.findViewById(R.id.cat1);
        category.setText(notes.get(position).getCategory());

        view.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Delete Btn Clicked", Toast.LENGTH_SHORT).show();
                databaseHelper.deleteItem(notes.get(position).getId());
                notes.remove(notes.get(position));
                Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });

        view.findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.update_dialog);
                dialog.show();

                String noteText = notes.get(position).getDesc();
                TextView newNote = dialog.findViewById(R.id.newNote);
                newNote.setText(noteText);

                dialog.findViewById(R.id.updateNote).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String updatedNote = newNote.getText().toString().trim();
                        int rowCount= databaseHelper.updateNote(notes.get(position).getId(), updatedNote);
                        if(rowCount == 1){
                            notes.get(position).setDesc(updatedNote);
                            notifyDataSetChanged();
                            Toast.makeText(getContext(), "Note Updated", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else {
                            Toast.makeText(getContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                        
                    }
                });

            }

        });

        return view;
        
    }
}
