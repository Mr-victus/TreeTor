package dev.cavemen.treetor;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddQuestionsFragment extends Fragment {

    ArrayList<String> questionno,question,option1,option2,option3,option4;
    AddQuestionsFragmentAdapter adapter;


    Button addquestion,startquiz;
    Dialog dialog;
    FirebaseAuth auth;


    public AddQuestionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_questions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        addquestion=view.findViewById(R.id.addquestions);

        auth=FirebaseAuth.getInstance();

        startquiz=view.findViewById(R.id.startquizbtn);



        startquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment startQuizFragment=new StartQuizFragment();

                getFragmentManager().beginTransaction().replace(R.id.fragment_container,startQuizFragment).addToBackStack("ClassroomFragment").commit();


            }
        });



        question=new ArrayList<>();
        option1=new ArrayList<>();
        option2=new ArrayList<>();
        option3=new ArrayList<>();
        option4=new ArrayList<>();
        questionno=new ArrayList<>();
        final RecyclerView recyclerView=view.findViewById(R.id.addquestionsrecyclerview);

//        question.add("What is your name?");
//
//        option1.add("piyush");
//        option2.add("saswath");
//        option3.add("arpit");
//        option4.add("NOne of the above");

        addquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.addquestiondialog);
                final Spinner spinner;
                final EditText questionet,opt1,opt2,opt3,opt4;
                Button submit;
                questionet=dialog.findViewById(R.id.addquestion);
                opt1=dialog.findViewById(R.id.addoption1);
                opt2=dialog.findViewById(R.id.addoption2);
                opt3=dialog.findViewById(R.id.addoption3);
                opt4=dialog.findViewById(R.id.addoption4);
                spinner=dialog.findViewById(R.id.correctoptionselector);



                ArrayList<String> mnane=new ArrayList<>();

                mnane.add("A");
                mnane.add("B");
                mnane.add("C");
                mnane.add("D");


                ArrayAdapter<String> adaptere =
                        new ArrayAdapter<String>(getContext(),R.layout.customspinner, mnane);
                adaptere.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(adaptere);


                submit=dialog.findViewById(R.id.submitbtnaddquestion);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("users").child("teachers").child(auth.getCurrentUser().getUid()).child("quiz");
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()) {
                                    long questions=dataSnapshot.getChildrenCount()+1;
                                    Map map=new HashMap();

                                    map.put("question",questionet.getText().toString());
                                    map.put("A",opt1.getText().toString());
                                    map.put("B",opt2.getText().toString());
                                    map.put("C",opt3.getText().toString());
                                    map.put("D",opt4.getText().toString());
                                    map.put("correct",spinner.getSelectedItem().toString());

                                    reference.child(""+questions).updateChildren(map);
                                    dialog.hide();

                                }
                                else {
                                    Map map=new HashMap();

                                    map.put("question",questionet.getText().toString());
                                    map.put("A",opt1.getText().toString());
                                    map.put("B",opt2.getText().toString());
                                    map.put("C",opt3.getText().toString());
                                    map.put("D",opt4.getText().toString());
                                    map.put("correct",spinner.getSelectedItem().toString());

                                    reference.child("1").updateChildren(map);
                                    dialog.hide();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                });
            }
        });


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
                    question.add(snapshot.child("question").getValue().toString());
                    option1.add(snapshot.child("A").getValue().toString());
                    option2.add(snapshot.child("B").getValue().toString());
                    option3.add(snapshot.child("C").getValue().toString());
                    option4.add(snapshot.child("D").getValue().toString());


                }


                FragmentManager manager=getFragmentManager();

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new AddQuestionsFragmentAdapter(getContext(),questionno,question,option1,option2,option3,option4,manager,addquestion,1);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });










    }
}
