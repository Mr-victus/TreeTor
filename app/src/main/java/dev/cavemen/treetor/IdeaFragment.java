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


public class IdeaFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<String> title,time,date;
    IdeaFragmentAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_idea, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView=view.findViewById(R.id.ideaboxrecyclerview);
        title=new ArrayList<>();
        time=new ArrayList<>();
        date=new ArrayList<>();


        title.add("Nice Topic");
        title.add("Nice Topic");
        title.add("Nice Topic");
        title.add("Nice Topic");

        time.add("5:30");
        time.add("5:30");
        time.add("5:30");
        time.add("5:30");


        date.add("23/12/18");
        date.add("23/12/18");
        date.add("23/12/18");
        date.add("23/12/18");


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new IdeaFragmentAdapter(getContext(),title,time,date);
        recyclerView.setAdapter(adapter);






    }
}
