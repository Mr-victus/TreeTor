package dev.cavemen.treetor;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
public class OngoingTestFragment extends Fragment {


    Button nextongoingquestion;
    Bundle bundle;
    FirebaseAuth auth;
    ArrayList<String> question,option1,option2,option3,option4;


    TextView ques,a,b,c,d;

    int count=0;

    public OngoingTestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bundle=getArguments();
        return inflater.inflate(R.layout.fragment_ongoing_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth=FirebaseAuth.getInstance();

        nextongoingquestion=view.findViewById(R.id.nextongoingquestion);

        ques=view.findViewById(R.id.questionongoing);
        a=view.findViewById(R.id.option1ongoing);
        b=view.findViewById(R.id.option2ongoing);
        c=view.findViewById(R.id.option3ongoing);
        d=view.findViewById(R.id.option4ongoing);

        question=new ArrayList<>();
        option1=new ArrayList<>();
        option2=new ArrayList<>();
        option3=new ArrayList<>();
        option4=new ArrayList<>();

        for(final String key:bundle.keySet())
        {
            DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("users").child("teachers").child(auth.getCurrentUser().getUid()).child("quiz");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        if(key.equals(snapshot.getKey()))
                        {
                            question.add(snapshot.child("question").getValue().toString());
                            option1.add(snapshot.child("A").getValue().toString());
                            option2.add(snapshot.child("B").getValue().toString());
                            option3.add(snapshot.child("C").getValue().toString());
                            option4.add(snapshot.child("D").getValue().toString());
                        }
                    }
                    nextongoingquestion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DatabaseReference reference1=FirebaseDatabase.getInstance().getReference().child("users").child("teachers").child(auth.getCurrentUser().getUid());
                            Map map=new HashMap();
                            if(count<question.size())
                            {


                                map.put("ongoing",count+1);
                                reference1.updateChildren(map);
                                ques.setText("Q. "+question.get(count));
                                a.setText("A. "+option1.get(count));
                                b.setText("B. "+option2.get(count));
                                c.setText("C. "+option3.get(count));
                                d.setText("D. "+option4.get(count));
                                count=count+1;
                            }
                            else
                            {
                                map.put("ongoing","over");
                                reference1.updateChildren(map);
                                Toast.makeText(getContext(),"Test Over",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }





    }
}
