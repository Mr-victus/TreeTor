package dev.cavemen.treetor;


import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceFragment extends Fragment {
    ArrayList<String> name,sid;
    RecyclerView recyclerView;
    AttendanceFragmentAdapter adapter;
    FirebaseAuth auth;
    StorageReference ref;

    public AttendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attendance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();

        CircleImageView imageView=view.findViewById(R.id.circleImageView);
        name=new ArrayList<>();
        sid=new ArrayList<>();
        final TextView nametv=view.findViewById(R.id.nameattendance);
        auth=FirebaseAuth.getInstance();
        DatabaseReference reference2=FirebaseDatabase.getInstance().getReference().child("users").child("teachers").child(auth.getCurrentUser().getUid());
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nametv.setText(dataSnapshot.child("name").getValue().toString());
                Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(),  "fonts/dkcoolcrayon.ttf");

                nametv.setTypeface(custom_font);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ref= FirebaseStorage.getInstance().getReference().child("users").child("teachers").child(auth.getCurrentUser().getUid()).child(auth.getCurrentUser().getUid());
        if(!ref.equals(null))
            Glide.with(getContext()).using(new FirebaseImageLoader()).load(ref).placeholder(R.drawable.propic).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true).into(imageView);

//        name.add("Riya");
//        name.add("Astha Sethi");
//        name.add("Asus Rog");
//        name.add("Sardar ji");
//        name.add("Sardar ji");
//        name.add("Sardar ji");
        recyclerView=view.findViewById(R.id.attendancerecyclerview);
        final String batchuid=getArguments().getString("batchuid");
        final String instituteuid=getArguments().getString("instituteuid");



        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("users").child("institutes").child(instituteuid).child("batches").child(batchuid).child("students");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    sid.add(snapshot.getKey());

                }
                for(int i=0;i<sid.size();i++)
                {
                    recyclerView.removeAllViews();
                    DatabaseReference reference1=FirebaseDatabase.getInstance().getReference().child("users").child("students").child(sid.get(i));
                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            name.add(""+dataSnapshot.child("name").getValue().toString());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            adapter = new AttendanceFragmentAdapter(getContext(),name,sid,instituteuid,batchuid);
                            recyclerView.setAdapter(adapter);
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








    }
}
