package dev.cavemen.treetor;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartQuizFragment extends Fragment {


    ArrayList<String> question,option1,option2,option3,option4,questionno;
    AddQuestionsFragmentAdapter adapter;

    FirebaseAuth auth;

    Button startquiz;



    public StartQuizFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start_quiz, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        question=new ArrayList<>();
        option1=new ArrayList<>();
        option2=new ArrayList<>();
        option3=new ArrayList<>();
        option4=new ArrayList<>();
        questionno=new ArrayList<>();

        auth=FirebaseAuth.getInstance();


        startquiz=view.findViewById(R.id.startquiz);


        final RecyclerView recyclerView=view.findViewById(R.id.addquestionsrecyclerview);



        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("users").child("teachers").child(auth.getCurrentUser().getUid()).child("quiz");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                question.clear();
                option1.clear();
                option2.clear();
                option3.clear();
                option4.clear();

                recyclerView.removeAllViews();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    questionno.add(snapshot.getKey());
                    question.add(snapshot.child("question").getValue().toString());
                    option1.add(snapshot.child("A").getValue().toString());
                    option2.add(snapshot.child("B").getValue().toString());
                    option3.add(snapshot.child("C").getValue().toString());
                    option4.add(snapshot.child("D").getValue().toString());


                }


                FragmentManager manager=getFragmentManager();

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new AddQuestionsFragmentAdapter(getContext(),questionno,question,option1,option2,option3,option4,manager,startquiz,2);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
