package com.example.timecube;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.litepal.crud.DataSupport;

import java.util.Calendar;
import java.util.List;

import Tool.LongClickListener;
import Tool.RecycleAdapter;


public class HomeFragment extends Fragment implements LongClickListener{
    private ImageView imageView;
   private RecyclerView recyclerView;
   private RecycleAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onResume() {
        adapter=new RecycleAdapter(DataSupport.findAll(Note.class),this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        super.onResume();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView=(ImageView)getActivity().findViewById(R.id.btn_add);

        recyclerView=(RecyclerView)getActivity().findViewById(R.id.recycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter=new RecycleAdapter(DataSupport.findAll(Note.class),this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),NoteActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void OnLongClick(View v, int position) {
        DataSupport.deleteAll(Note.class,"deadline=?",""+DataSupport.findAll(Note.class).get(position).getDeadline());
        adapter=new RecycleAdapter(DataSupport.findAll(Note.class),this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}