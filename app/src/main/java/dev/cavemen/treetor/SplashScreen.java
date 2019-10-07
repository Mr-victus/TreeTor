package dev.cavemen.treetor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {


    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        auth=FirebaseAuth.getInstance();
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if(auth.getCurrentUser()==null)
                    {
                        Intent i = new Intent(SplashScreen.this, LogIn.class);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        Intent i = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }


                }
            }
        };
        timer.start();
    }
}
