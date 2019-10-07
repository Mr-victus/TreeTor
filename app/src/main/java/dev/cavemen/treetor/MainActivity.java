package dev.cavemen.treetor;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        Fragment fragment=new ClassroomFragment();
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();



        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomnavbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);




    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedfragment=null;
            switch (menuItem.getItemId()) {
                case R.id.account:

                    selectedfragment = new AccountFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedfragment).commit();
                    break;
                case R.id.classroom:
                    selectedfragment = new AttendanceNewFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedfragment).commit();
                    break;
                case R.id.dashboard:
                    selectedfragment=new DashboardFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedfragment).commit();
                    break;



            }

            return true;
        }

    };
}
