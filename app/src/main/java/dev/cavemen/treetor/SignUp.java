package dev.cavemen.treetor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class SignUp extends AppCompatActivity {
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText name,email,password,confirmpassword,phone;
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        confirmpassword=findViewById(R.id.confirmpassword);
        phone=findViewById(R.id.mobilenumber);

        Button register;

        register=findViewById(R.id.registerbtn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pass,conpass,emai,nam,phon;
                final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                progressDialog.setTitle("Signing Up");
                progressDialog.setMessage("please wait..");
                progressDialog.show();

                pass=""+password.getText().toString();
                conpass=""+confirmpassword.getText().toString();
                emai=""+email.getText().toString();
                nam=""+name.getText().toString();
                phon=""+phone.getText().toString();

                if(pass.equals(conpass) && !emai.equals("") && !nam.equals("") && !pass.equals(""))
                {
                    auth.createUserWithEmailAndPassword(emai,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("users").child("teachers").child(auth.getCurrentUser().getUid());
                                Map map=new HashMap();
                                map.put("email",emai);
                                map.put("name",nam);
                                map.put("gender","Not Updated");
                                map.put("dob","Not Updated");
                                map.put("languages","Not Updated");
                                map.put("phone",phon);
                                map.put("address","Not Updated");
                                map.put("facebook","Not Updated");
                                map.put("Qualification","Not Updated");
                                map.put("Experience","Not Updated");
                                map.put("Treetor institutes","Not Updated");
                                map.put("old tuition","Not Updated");
                                map.put("score","N/A");
                                map.put("rating","N/A");
                                map.put("rank","N/A");
                                map.put("password",pass);


                                reference.updateChildren(map);

                                Toast.makeText(SignUp.this,"Signed Up Succesfully",Toast.LENGTH_SHORT).show();

                                Intent i=new Intent(SignUp.this,MainActivity.class);
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
