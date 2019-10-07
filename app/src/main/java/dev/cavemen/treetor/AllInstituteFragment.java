package dev.cavemen.treetor;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class AllInstituteFragment extends Fragment {
    ArrayList<String> institutename,instituterating;
    RecyclerView recyclerView;
    AllInsituteFragmentAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_all_institute, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        institutename=new ArrayList<>();
        instituterating=new ArrayList<>();
        recyclerView=view.findViewById(R.id.allinstituterecyclerview);


        institutename.add("Treetor Coaching");
        institutename.add("Institute of Treetor");
        institutename.add("Treetor Academy Of Technology");
        institutename.add("Trident Institute Of Technology");
        institutename.add("Trident school Of Technology");

        instituterating.add("4.5");
        instituterating.add("4.5");
        instituterating.add("4.5");
        instituterating.add("4.5");
        instituterating.add("4.5");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AllInsituteFragmentAdapter(getContext(),institutename,instituterating);
        recyclerView.setAdapter(adapter);





    }
}
