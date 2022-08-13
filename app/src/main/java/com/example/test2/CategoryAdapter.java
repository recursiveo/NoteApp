package com.example.test2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter {

    private Context context;
    private int layoutRes;
    private List<String> cats;

    public CategoryAdapter(@NonNull Context context, int resource,@NonNull List<String> cats) {
        super(context, resource, cats);
        this.context = context;
        this.layoutRes = resource;
        this.cats = cats;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;
        if (view == null) view = inflater.inflate(layoutRes, null);

        Button button = view.findViewById(R.id.categoryBtn);

        button.setText(cats.get(position));

        view.findViewById(R.id.categoryBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SecondActivity.class);
                intent.putExtra("Category", cats.get(position));
                context.startActivity(intent);
            }
        });

        return view;

    }
}
