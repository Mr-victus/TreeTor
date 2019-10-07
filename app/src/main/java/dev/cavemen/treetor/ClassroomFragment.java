package dev.cavemen.treetor;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClassroomFragment extends Fragment {

    ArrayList<String> institutename,nextclass,attendance,instituteuid,batchuid,instituteuidd,subjectuid,subjectname,subjectstd;
    RecyclerView recyclerView;
    ClassroomFragmentAdapter adapter;
    FirebaseAuth auth;
    StorageReference ref;
    int dayOfWeek;
    String time,endtime;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_classroom,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView=view.findViewById(R.id.classroomrecyclerview);
        auth=FirebaseAuth.getInstance();

        final TextView nameclassroom=view.findViewById(R.id.nameclassroom);

        institutename=new ArrayList<>();
        nextclass=new ArrayList<>();
        attendance=new ArrayList<>();
        instituteuid=new ArrayList<>();
        instituteuidd=new ArrayList<>();
        batchuid=new ArrayList<>();
        subjectuid=new ArrayList<>();
        subjectname=new ArrayList<>();
        subjectstd=new ArrayList<>();
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("please wait..");
       progressDialog.show();
        CircleImageView circleImageView=view.findViewById(R.id.circleImageView);
        circleImageView.setImageResource(R.drawable.propic);
        ref= FirebaseStorage.getInstance().getReference().child("users").child("teachers").child(auth.getCurrentUser().getUid()).child(auth.getCurrentUser().getUid()+".jpeg");
        if(!ref.equals(null))
            Glide.with(getContext()).using(new FirebaseImageLoader()).load(ref).placeholder(R.drawable.propic).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true).into(circleImageView);


//        institutename.add("Treetor Coaching");
//        institutename.add("Institute of Treetor");
//        institutename.add("Treetor Academy Of Technology");
//        institutename.add("Trident Institute Of Technology");
//        institutename.add("Trident school Of Technology");
//
//        nextclass.add("8:30AM");
//        nextclass.add("5:30AM");
//        nextclass.add("6:30AM");
//        nextclass.add("9:30AM");
//        nextclass.add("1:30PM");
//
//        attendance.add("97%");
//        attendance.add("97%");
//        attendance.add("97%");
//        attendance.add("97%");
//        attendance.add("97%");

        DatabaseReference reference2=FirebaseDatabase.getInstance().getReference().child("users").child("teachers").child(auth.getCurrentUser().getUid());
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nameclassroom.setText(dataSnapshot.child("name").getValue().toString());


                Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(),  "fonts/dkcoolcrayon.ttf");

                nameclassroom.setTypeface(custom_font);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference referencetry=FirebaseDatabase.getInstance().getReference().child("users").child("teachers").child(auth.getCurrentUser().getUid()).child("institutes");
        referencetry.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    instituteuid.add(snapshot.getKey());

                }
                for(int i=0;i<instituteuid.size();i++)
                {
                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("users").child("institutes").child(instituteuid.get(i)).child("batches");
                    final String institutesuid=instituteuid.get(i);
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshotbatches:dataSnapshot.getChildren())
                            {
                                //batchuid.add(snapshotbatches.getKey());
                                recyclerView.removeAllViews();
                                //instituteuid.clear();

                                    for (DataSnapshot snapshotsub : snapshotbatches.child("subjects").getChildren()) {
//                                        if(snapshotsub.child("teacher").getValue().toString().equals(auth.getCurrentUser().getUid())) {
//                                            subjectuid.add(snapshotsub.getKey());
//                                            batchuid.add(snapshotbatches.getKey());
//
//                                        }
                                        if (snapshotsub.child("teacher").getValue().equals(auth.getCurrentUser().getUid())) {

                                            batchuid.add(snapshotbatches.getKey());
                                            subjectuid.add(snapshotsub.getKey());
                                            instituteuidd.add(institutesuid);
                                            Toast.makeText(getContext(),""+batchuid+subjectuid+instituteuidd,Toast.LENGTH_SHORT).show();
                                            FragmentManager f_manager;
                                            f_manager = getFragmentManager();
                                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                            adapter = new ClassroomFragmentAdapter(getContext(), subjectuid, instituteuidd, batchuid, f_manager);
                                            recyclerView.setAdapter(adapter);
                                            progressDialog.hide();

//
//
////                                    nextclass.add(snapshot.child("time").getValue().toString());
//
//
//                                            ArrayList<String> temp = new ArrayList<>();
//                                            ArrayList<Integer> temp2 = new ArrayList<>();
//                                            ArrayList<String> time1 = new ArrayList<>();
//
//
//                                            for (DataSnapshot snapshot3 : snapshotsub.child("timings").getChildren()) {
//
//
////                                              Toast.makeText(getContext(),""+dayOfWeek,Toast.LENGTH_SHORT).show();
//                                                String[] weekdays = {"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};
//                                                String weekGot = snapshot3.getKey();
//
//
//                                                for (DataSnapshot snapshot1 : snapshot3.getChildren()) {
////                                                    Toast.makeText(getContext(), snapshot1.getKey(), Toast.LENGTH_SHORT).show();
////                                                    Toast.makeText(getContext(), snapshot1.getValue().toString(), Toast.LENGTH_SHORT).show();
//                                                    endtime = snapshot1.getValue().toString();
//                                                    time = snapshot1.getKey();
//                                                }
//
//
//                                                for (int i = 0; i < weekdays.length; i++) {
//                                                    if (weekGot.toLowerCase().equals(weekdays[i])) {
//                                                        temp.add(weekGot);
//                                                        temp2.add(i);
//                                                        time1.add(time);
//                                                    }
//                                                }
//                                            }
//
//                                            ArrayList<Integer> temp3 = new ArrayList<>();
//                                            Calendar calendar = Calendar.getInstance();
//                                            int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
//
//                                            dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
//
//                                            for (int i = 0; i < temp2.size(); i++) {
//
//                                                int dif = hourOfDay - Integer.parseInt(time1.get(i).toString().substring(0, 2));
//                                                if (dayOfWeek <= temp2.get(i) && dif != 2) {
////                                            Toast.makeText(getContext(),""+temp.get(i)+""+temp2.get(i),Toast.LENGTH_SHORT).show();
//                                                    nextclass.add("" + temp.get(i) + "@" + time1.get(i));
//                                                    //institutename.add(snapshot.child("course").getValue().toString());
//                                                    subjectuid.add(snapshotsub.getKey());
//                                                    attendance.add("97%");
//                                                    instituteuidd.add(institutesuid);
//                                                    temp3.clear();
//                                                    break;
//                                                } else {
//                                                    temp3.add(temp2.get(i));
//                                                }
//                                            }
//
//
//                                            if (!temp3.isEmpty()) {
////                                        Toast.makeText(getContext(),""+temp.get(0)+""+temp2.get(0),Toast.LENGTH_SHORT).show();
//                                                nextclass.add("" + temp.get(0) + "@" + time1.get(0));
//                                                subjectuid.add(snapshotsub.getKey());
//                                                attendance.add("97%");
//                                                instituteuidd.add(institutesuid);
//                                            }




                                        } else {
                                            Toast toast = Toast.makeText(getContext(), "NO batches", Toast.LENGTH_LONG);
                                            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                                            toast.show();
                                            progressDialog.hide();

                                        }

                                    }


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



//        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("users").child("teachers").child(auth.getCurrentUser().getUid()).child("institutes");
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot snapshot:dataSnapshot.getChildren())
//                {
//                    instituteuid.add(snapshot.getKey());
//                }
//                for(int i=0;i<instituteuid.size();i++)
//                {
//                    DatabaseReference reference1=FirebaseDatabase.getInstance().getReference().child("users").child("institutes").child(instituteuid.get(i)).child("batches");
//
//                    final String instittuteuid=instituteuid.get(i);
//                    reference1.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
//                                recyclerView.removeAllViews();
//                                instituteuid.clear();
//
//
//                                //attendance taken or not by getting the date from daily reports epoch
//
//                                for(DataSnapshot snapshotsub:snapshot.child("subjects").getChildren()) {
//                                    subjectuid.add(snapshotsub.getKey());
//
//
//                                    if (snapshotsub.child("teacher").getValue().equals(auth.getCurrentUser().getUid())) {
//
//                                        batchuid.add(snapshot.getKey());
//
//
////                                    nextclass.add(snapshot.child("time").getValue().toString());
//
//
//                                        ArrayList<String> temp = new ArrayList<>();
//                                        ArrayList<Integer> temp2 = new ArrayList<>();
//                                        ArrayList<String> time1 = new ArrayList<>();
//
//
//                                        for (DataSnapshot snapshot3 : snapshot.child("timings").getChildren()) {
//
//
////                                              Toast.makeText(getContext(),""+dayOfWeek,Toast.LENGTH_SHORT).show();
//                                            String[] weekdays = {"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};
//                                            String weekGot = snapshot3.getKey();
//
//
//                                            for (DataSnapshot snapshot1 : snapshot3.getChildren()) {
////                                                    Toast.makeText(getContext(), snapshot1.getKey(), Toast.LENGTH_SHORT).show();
////                                                    Toast.makeText(getContext(), snapshot1.getValue().toString(), Toast.LENGTH_SHORT).show();
//                                                endtime = snapshot1.getValue().toString();
//                                                time = snapshot1.getKey();
//                                            }
//
//
//                                            for (int i = 0; i < weekdays.length; i++) {
//                                                if (weekGot.toLowerCase().equals(weekdays[i])) {
//                                                    temp.add(weekGot);
//                                                    temp2.add(i);
//                                                    time1.add(time);
//                                                }
//                                            }
//                                        }
//
//                                        ArrayList<Integer> temp3 = new ArrayList<>();
//                                        Calendar calendar = Calendar.getInstance();
//                                        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
//
//                                        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
//
//                                        for (int i = 0; i < temp2.size(); i++) {
//
//                                            int dif = hourOfDay - Integer.parseInt(time1.get(i).toString().substring(0, 2));
//                                            if (dayOfWeek <= temp2.get(i) && dif != 2) {
////                                            Toast.makeText(getContext(),""+temp.get(i)+""+temp2.get(i),Toast.LENGTH_SHORT).show();
//                                                nextclass.add("" + temp.get(i) + "@" + time1.get(i));
//                                                institutename.add(snapshot.child("course").getValue().toString());
//                                                attendance.add("97%");
//                                                instituteuidd.add(instittuteuid);
//                                                temp3.clear();
//                                                break;
//                                            } else {
//                                                temp3.add(temp2.get(i));
//                                            }
//                                        }
//
//
//                                        if (!temp3.isEmpty()) {
////                                        Toast.makeText(getContext(),""+temp.get(0)+""+temp2.get(0),Toast.LENGTH_SHORT).show();
//                                            nextclass.add("" + temp.get(0) + "@" + time1.get(0));
//                                            institutename.add(snapshot.child("course").getValue().toString());
//                                            attendance.add("97%");
//                                            instituteuidd.add(instittuteuid);
//                                        }
//
//
//                                        FragmentManager f_manager;
//                                        f_manager = getFragmentManager();
//                                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//                                        adapter = new ClassroomFragmentAdapter(getContext(), institutename, nextclass, attendance, instituteuidd, batchuid, f_manager);
//                                        recyclerView.setAdapter(adapter);
//                                        progressDialog.hide();
//
//                                    } else {
//                                        Toast toast = Toast.makeText(getContext(), "NO batches", Toast.LENGTH_LONG);
//                                        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
//                                        toast.show();
//                                        progressDialog.hide();
//
//                                    }
//                                }
//                                }
//                            }
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }
}
//for(int i=0;i<weekdays.length;i++)
//        {
//        if(snapshot1.getKey().equals(weekdays[i]))
//        {
//        if(i<=dayOfWeek)
//        {
//        c=c+1;
//        temp[]
//        Toast.makeText(getContext(),""+snapshot1.getKey()+""+snapshot1.getValue().toString(),Toast.LENGTH_SHORT).show();
//        break;
//        }
//        else if(i<dayOfWeek)
//        {
//        if(c==dayOfWeek)
//        }
//
//        }
//        }