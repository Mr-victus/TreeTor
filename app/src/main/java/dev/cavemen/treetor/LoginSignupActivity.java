package dev.cavemen.treetor;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class LoginSignupActivity extends AppCompatActivity {

    int log=0;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        auth=FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null)
        {
            Intent i=new Intent(LoginSignupActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }
        final TextView login=findViewById(R.id.login);
        final TextView signup=findViewById(R.id.signup);
        login.setTextColor(Color.rgb(255,255,255));
        signup.setTextColor(Color.rgb(193,198,193));
        Fragment fragment2=new LoginFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.login_fragment_container,fragment2).commit();




        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(log==1)
                {
                    log=0;
                    login.setTextColor(Color.rgb(193,198,193));
                    signup.setTextColor(Color.rgb(255,255,255));

                    Fragment fragment=new SignupFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.login_fragment_container,fragment).commit();

                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(log==0)
                {
                    log=1;
                    login.setTextColor(Color.rgb(255,255,255));
                    signup.setTextColor(Color.rgb(193,198,193));

                    Fragment fragment1=new LoginFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.login_fragment_container,fragment1).commit();
                }

            }
        });



    }
}
