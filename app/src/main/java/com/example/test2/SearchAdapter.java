package com.example.test2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SearchAdapter extends ArrayAdapter {
    private final Context context;
    private final int layoutRes;
    private final List<Note> notes;
    private DatabaseHelper databaseHelper;

    public SearchAdapter(@NonNull Context context, int resource, @NonNull List<Note> notes) {
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

        TextView desc = view.findViewById(R.id.description_Search);
        desc.setText(notes.get(position).getDesc());
        TextView category = view.findViewById(R.id.cat1_search);
        category.setText(notes.get(position).getCategory());

        return view;

    }

}
