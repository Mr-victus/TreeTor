package dev.cavemen.treetor;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {

    FirebaseAuth auth;



    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        auth=FirebaseAuth.getInstance();
        final EditText name,email,password,confirmpassword;
        Button singup;
        singup=view.findViewById(R.id.singupbtn);
        name=view.findViewById(R.id.namesignup);
        email=view.findViewById(R.id.emailsignup);
        password=view.findViewById(R.id.passwordsignup);
        confirmpassword=view.findViewById(R.id.confirmpasswordsignup);


        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pass,conpass,emai,nam;
                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setTitle("Signing Up");
                progressDialog.setMessage("please wait..");
                progressDialog.show();

                pass=""+password.getText().toString();
                conpass=""+confirmpassword.getText().toString();
                emai=""+email.getText().toString();
                nam=""+name.getText().toString();

                if(pass.equals(conpass) && !emai.equals("") && !nam.equals("") && !pass.equals(""))
                {
                    auth.createUserWithEmailAndPassword(emai,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("users").child("teachers").child(auth.getCurrentUser().getUid());
                                Map map=new HashMap();
                                map.put("email",emai);
                                map.put("name",nam);
                                map.put("gender","Not Updated");
                                map.put("dob","Not Updated");
                                map.put("languages","Not Updated");
                                map.put("phone","Not Updated");
                                map.put("address","Not Updated");
                                map.put("facebook","Not Updated");
                                map.put("Qualification","Not Updated");
                                map.put("Experience","Not Updated");
                                map.put("Treetor institutes","Not Updated");
                                map.put("old tuition","Not Updated");
                                map.put("score","N/A");
                                map.put("rating","N/A");
                                map.put("rank","N/A");


                                reference.updateChildren(map);

                                Toast.makeText(getContext(),"Signed Up Succesfully",Toast.LENGTH_SHORT).show();

                                Intent i=new Intent(getActivity(),MainActivity.class);
                                startActivity(i);
                                progressDialog.hide();

                            }
                        }

                    });


                }
            }
        });


    }
}
