package dev.cavemen.treetor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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

public class DashboardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_dashboard,container,false);


        //Button rankbtn=rootview.findViewById(R.id.rankbtn);


       // Button starstudent=rootview.findViewById(R.id.starstudentbtn);

        //Button suggestion=rootview.findViewById(R.id.allstudent);






        return inflater.inflate(R.layout.fragment_dashboard,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Button institutebtn=view.findViewById(R.id.institutebtn);
//
        Button allstudent=view.findViewById(R.id.allstudent);

        final TextView n=view.findViewById(R.id.name);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("users").child("teachers").child(auth.getCurrentUser().getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                n.setText("Welcome, "+dataSnapshot.child("name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        Button rankbtn=view.findViewById(R.id.rankbtn);
//        Button ideabtn=view.findViewById(R.id.ideaboxbtn);
        Button topstudent=view.findViewById(R.id.diary);


        topstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                //bundle.putString("intent","allstudent");
                bundle.putString("intent","diary");
                Fragment allBatchFragment=new AllBatchFragment();
                allBatchFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,allBatchFragment).addToBackStack("DashboardFragment").commit();

            }
        });
//
//
//        ideabtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment ideaFragment=new IdeaFragment();
//
//                getFragmentManager().beginTransaction().replace(R.id.fragment_container,ideaFragment).addToBackStack("DashboardFragment").commit();
//
//            }
//        });
//
//        rankbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment leaderboardFragment=new LeaderboardFragment();
//
//                getFragmentManager().beginTransaction().replace(R.id.fragment_container,leaderboardFragment).addToBackStack("DashboardFragment").commit();
//
//            }
//        });
//
//        institutebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Fragment allinstitutefragment=new AllInstituteFragment();
//
//                getFragmentManager().beginTransaction().replace(R.id.fragment_container,allinstitutefragment).addToBackStack("DashboardFragment").commit();
//
//            }
//        });
        allstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle=new Bundle();
                bundle.putString("intent","allstudent");
                //bundle.putString("intent","diary");
                Fragment allBatchFragment=new AllBatchFragment();
                allBatchFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,allBatchFragment).addToBackStack("DashboardFragment").commit();

            }
        });


    }
}
