package dev.cavemen.treetor;


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


/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderboardFragment extends Fragment {

    RecyclerView recyclerView;

    ArrayList<String> name,score,rank;
    LeaderboardFragmentAdapter adapter;


    public LeaderboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leaderboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        name=new ArrayList<>();
        rank=new ArrayList<>();
        score=new ArrayList<>();

        recyclerView=view.findViewById(R.id.leaderboardrecyclerview);

        name.add("Misty");
        name.add("Ria");
        name.add("Shakuntala");

        rank.add("1");
        rank.add("2");
        rank.add("3");



        score.add("1667xp");
        score.add("1667xp");
        score.add("1667xp");





        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new LeaderboardFragmentAdapter(getContext(),name,score,rank);
        recyclerView.setAdapter(adapter);
    }
}
