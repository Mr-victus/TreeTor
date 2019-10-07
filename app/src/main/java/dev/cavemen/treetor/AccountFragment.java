package dev.cavemen.treetor;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.crashlytics.android.Crashlytics;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {

    FirebaseAuth auth;
    StorageReference ref;
    DatePickerDialog.OnDateSetListener currentDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth=FirebaseAuth.getInstance();

        final TextView name,score,rank,xp,gendere,tick,cross,dobe,lange,phonee,emaile,addresse,facebooke,qualificatione,experiencee,treetorinstitutee,gnamee,gemaile,gphonee,gdobe,grelatione;
        final EditText gender,dob,lang,phone,email,address,facebook,qualification,experience,treetorinstitute,gname,gemail,gphone,gdob,grelation;
        Button logout;

        logout=view.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent i=new Intent(getContext(),LogIn.class);
                startActivity(i);
                getActivity().finish();
            }
        });
//        Button crash=view.findViewById(R.id.crash);
//        crash.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Crashlytics.getInstance().crash();
//            }
//        });


        CircleImageView circleImageView=view.findViewById(R.id.circleImageView);
        ref= FirebaseStorage.getInstance().getReference().child("users").child("teachers").child(auth.getCurrentUser().getUid()).child(auth.getCurrentUser().getUid());
        if(!ref.equals(null))
            Glide.with(getContext()).using(new FirebaseImageLoader()).load(ref).placeholder(R.drawable.propic).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true).into(circleImageView);

        name=view.findViewById(R.id.nameaccount);
        score=view.findViewById(R.id.scoreaccount);
        rank=view.findViewById(R.id.rankaccount);
        xp=view.findViewById(R.id.ratingaccount);
        gendere=view.findViewById(R.id.gendereditaccount);
        gender=view.findViewById(R.id.genderaccount);
        dob=view.findViewById(R.id.dobaccount);
        lang=view.findViewById(R.id.languageaccount);
        phone=view.findViewById(R.id.phoneaccount);
        email=view.findViewById(R.id.emailaccount);
        address=view.findViewById(R.id.addressaccount);

        qualification=view.findViewById(R.id.qualificationaccount);
        experience=view.findViewById(R.id.experienceaccount);
        treetorinstitute=view.findViewById(R.id.treetorinstituteaccount);

        dobe=view.findViewById(R.id.dobeditaccount);
        lange=view.findViewById(R.id.languageeditaccount);
        phonee=view.findViewById(R.id.phoneeditaccount);
        emaile=view.findViewById(R.id.emaileditaccount);
        addresse=view.findViewById(R.id.addresseditaccount);

        qualificatione=view.findViewById(R.id.qualificationeditaccount);
        experiencee=view.findViewById(R.id.experienceeditaccount);
        treetorinstitutee=view.findViewById(R.id.treetorinstituteeditaccount);

        tick=view.findViewById(R.id.tick);
        cross=view.findViewById(R.id.cross);

        tick.setVisibility(View.INVISIBLE);
        cross.setVisibility(View.INVISIBLE);


        gendere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                gender.setCursorVisible(true);
//                gender.setFocusableInTouchMode(true);
//                gender.setInputType(InputType.TYPE_CLASS_TEXT);
//                gender.requestFocus();

                final Dialog dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.gender_popup);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                TextView male,female,others;
                male=dialog.findViewById(R.id.male);
                female=dialog.findViewById(R.id.female);
                others=dialog.findViewById(R.id.others);

                male.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gender.setText("Male");
                        dialog.hide();
                    }
                });
                female.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gender.setText("Female");
                        dialog.hide();
                    }
                });
                others.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gender.setText("Others");
                        dialog.hide();
                    }
                });



                tick.setVisibility(View.VISIBLE);
                cross.setVisibility(View.VISIBLE);



            }
        });

        dobe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                dob.setCursorVisible(true);
//                dob.setFocusableInTouchMode(true);
//                dob.setInputType(InputType.TYPE_CLASS_TEXT);
//                dob.requestFocus();

                        Calendar c = Calendar.getInstance();
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dialog = new DatePickerDialog(getContext(),
                                currentDate, year, month, day
                        );

                        dialog.show();

                currentDate = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        dob.setText(new StringBuilder().append(i).append("-").append(i1+1).append("-").append(i2));
                    }
                };

                tick.setVisibility(View.VISIBLE);
                cross.setVisibility(View.VISIBLE);

            }
        });
        lange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lang.setText("");
                lang.setHint("Type here");
                lang.setCursorVisible(true);
                lang.setFocusableInTouchMode(true);
                lang.setInputType(InputType.TYPE_CLASS_TEXT);
                lang.requestFocus();


                tick.setVisibility(View.VISIBLE);
                cross.setVisibility(View.VISIBLE);

            }
        });

        phonee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phone.setText("");
                phone.setHint("Type here");
                phone.setCursorVisible(true);
                phone.setFocusableInTouchMode(true);
                phone.setInputType(InputType.TYPE_CLASS_PHONE);
                phone.requestFocus();


                tick.setVisibility(View.VISIBLE);
                cross.setVisibility(View.VISIBLE);

            }
        });

        addresse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                address.setText("");
                address.setHint("Type here");
                address.setCursorVisible(true);
                address.setFocusableInTouchMode(true);
                address.setInputType(InputType.TYPE_CLASS_TEXT);
                address.requestFocus();


                tick.setVisibility(View.VISIBLE);
                cross.setVisibility(View.VISIBLE);

            }
        });

        qualificatione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                qualification.setText("");
                qualification.setHint("Type here");
                qualification.setCursorVisible(true);
                qualification.setFocusableInTouchMode(true);
                qualification.setInputType(InputType.TYPE_CLASS_TEXT);
                qualification.requestFocus();


                tick.setVisibility(View.VISIBLE);
                cross.setVisibility(View.VISIBLE);

            }
        });
        experiencee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                experience.setCursorVisible(true);
//                experience.setFocusableInTouchMode(true);
//                experience.setInputType(InputType.TYPE_CLASS_TEXT);
//                experience.requestFocus();

                final Dialog dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.experience_popup);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                Button submit=dialog.findViewById(R.id.experiencesubmit);
                final Spinner spinner=dialog.findViewById(R.id.yearsspinner);
                Integer[] items=new Integer[100];
                for(int i=0;i<100;i++)items[i]=i;
                ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getContext(),android.R.layout.simple_spinner_item, items);
                spinner.setAdapter(adapter);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.hide();
                        experience.setText(""+spinner.getSelectedItem().toString());
                    }
                });

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        experience.setText(""+position+" Years");

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                tick.setVisibility(View.VISIBLE);
                cross.setVisibility(View.VISIBLE);

            }
        });
        treetorinstitutee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                treetorinstitute.setCursorVisible(true);
//                treetorinstitute.setFocusableInTouchMode(true);
//                treetorinstitute.setInputType(InputType.TYPE_CLASS_TEXT);
//                treetorinstitute.requestFocus();


                tick.setVisibility(View.VISIBLE);
                cross.setVisibility(View.VISIBLE);

            }
        });
        tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("users").child("teachers").child(auth.getCurrentUser().getUid());
                Map map=new HashMap();
                //map.put("email",emai);
                //map.put("name",nam);
                map.put("gender",""+gender.getText().toString());
                map.put("dob",""+dob.getText().toString());
                map.put("languages",""+lang.getText().toString());
                map.put("phone",""+phone.getText().toString());
                map.put("address",""+address.getText().toString());
                map.put("Qualification",""+qualification.getText().toString());
                map.put("Experience",""+experience.getText().toString());

                /*map.put("guardian occupation",""+gender.getText().toString());
                map.put("guardian qualification",""+gender.getText().toString());
                map.put("class","Not Updated");
                map.put("school","Not Updated");
                map.put("treetor center","Not Updated");
                map.put("board","Not Updated");
                map.put("percentage","Not Updated");
                map.put("subjects","Not Updated");
                map.put("best at","Not Updated");
                map.put("weak at","Not Updated");
                map.put("old tuition","Not Updated");*/



                reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Fragment s=new AccountFragment();
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,s).addToBackStack("some").commit();
                    }
                });






            }
        });

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment s=new AccountFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,s).commit();

            }
        });


        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("users").child("teachers").child(auth.getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.child("institutes").getChildren())
                {
                    DatabaseReference reference1=FirebaseDatabase.getInstance().getReference().child("users").child("institutes").child(snapshot.getKey());
                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            treetorinstitute.setText(""+dataSnapshot.child("name").getValue().toString());

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                name.setText(""+dataSnapshot.child("name").getValue().toString());
//                Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(),  "fonts/dkcoolcrayon.ttf");
//                name.setTypeface(custom_font);
                gender.setText(""+dataSnapshot.child("gender").getValue().toString());
                dob.setText(""+dataSnapshot.child("dob").getValue().toString());
                lang.setText(""+dataSnapshot.child("languages").getValue().toString());
                phone.setText(""+dataSnapshot.child("phone").getValue().toString());
                email.setText(""+dataSnapshot.child("email").getValue().toString());
                address.setText(""+dataSnapshot.child("address").getValue().toString());
                qualification.setText(""+dataSnapshot.child("Qualification").getValue().toString());
                experience.setText(""+dataSnapshot.child("Experience").getValue().toString());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
}
