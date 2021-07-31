package com.example.deadnote;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends Fragment {
    FloatingActionButton fab_add;
    RecycleAdapter recycleAdapter;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    ArrayList<note> listnote;
    RecyclerView rv_view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home,container,false);
        fab_add = (FloatingActionButton)view.findViewById(R.id.fab_add);
        rv_view = (RecyclerView) view.findViewById(R.id.rv_view);

        RecyclerView.LayoutManager mLayout = new LinearLayoutManager(getContext());
        rv_view.setLayoutManager(mLayout);
        rv_view.setItemAnimator(new DefaultItemAnimator());

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputActivity inputActivity = new InputActivity("","","","","Tambah");
                inputActivity.show(getFragmentManager(),"form");
            }
        });
        showData();
        return view;
    }
    private void  showData(){

        database.child("test").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listnote = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()) {
                    note not = item.getValue(note.class);
                    not.setKey(item.getKey());
                    listnote.add(not);
                }
                recycleAdapter = new RecycleAdapter(listnote,getActivity());
                rv_view.setAdapter(recycleAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }
}
