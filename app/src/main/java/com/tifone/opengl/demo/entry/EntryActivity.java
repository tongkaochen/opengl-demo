package com.tifone.opengl.demo.entry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tifone.opengl.demo.R;
import com.tifone.opengl.demo.hockey.air2d.AirHockeyActivity;
import com.tifone.opengl.demo.texture.TextureDemoActivity;

import java.util.ArrayList;
import java.util.List;

public class EntryActivity extends Activity {

    private static final int FOCUS_INDEX = 1;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<DemoEntry> mList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_layout);
        mList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.entry_recycler_view);
        createAdapter();
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setAdapter(mAdapter);
    }

    private DemoEntry createEntryItem(String title, Class clazz) {
        return new DemoEntry(title, clazz);
    }
    private void createAndAddEntryToList(String title, Class clazz) {
        mList.add(createEntryItem(title, clazz));
    }
    private void createEntryData() {
        createAndAddEntryToList("Air Hockey", AirHockeyActivity.class);
        createAndAddEntryToList("Texture demo", TextureDemoActivity.class);
    }

    private void createAdapter() {
        createEntryData();
        mAdapter = new DemoAdapter();
    }

    class DemoAdapter extends RecyclerView.Adapter<DemoViewHolder> {

        @NonNull
        @Override
        public DemoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(EntryActivity.this);
            View view = inflater.inflate(R.layout.entry_item, null);
            return new DemoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DemoViewHolder holder, final int position) {
            holder.button.setText(mList.get(position).title);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(EntryActivity.this,
                            mList.get(position).targetClass);
                    startActivity(intent);
                }
            });
            if (FOCUS_INDEX == position) {
                holder.button.performClick();
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    class DemoViewHolder extends RecyclerView.ViewHolder {
        Button button;
        DemoViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.entry_btn);
        }
    }

    class DemoEntry {
        String title;
        Class targetClass;
        DemoEntry(String title, Class clazz) {
            this.title = title;
            targetClass = clazz;
        }
    }
}
