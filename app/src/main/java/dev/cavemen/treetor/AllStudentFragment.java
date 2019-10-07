package dev.cavemen.treetor;


import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import devs.mulham.horizontalcalendar.utils.Utils;


public class AllStudentFragment extends Fragment {
    ArrayList<String> name,standard,subject,institute;
    ArrayList<String> batch,iuid,institute_name;
    RecyclerView recyclerView;
    AllStudentFragmentAdapter adapter;
    FirebaseAuth auth;
    Bundle bundle;
    RequestQueue requestQueue;

    public AllStudentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        bundle=getArguments();
        return inflater.inflate(R.layout.fragment_all_student, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name=new ArrayList<>();
        standard=new ArrayList<>();
        subject=new ArrayList<>();
        institute=new ArrayList<>();
        final TextView n=view.findViewById(R.id.name);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("please wait..");
        progressDialog.show();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("users").child("teachers").child(auth.getCurrentUser().getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                n.setText("Welcome, "+dataSnapshot.child("name").getValue().toString());
                progressDialog.hide();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recyclerView=view.findViewById(R.id.allstudentrecyclerview);


//        name.add("Riya");
//        name.add("Astha Sethi");
//        name.add("Asus Rog");
//        name.add("Sardar ji");
//        name.add("Sardar ji");name.add("Sardar ji");
//
//        standard.add("VII");
//        standard.add("XI");
//        standard.add("X");
//        standard.add("VI");
//        standard.add("VI");
//        standard.add("VI");
//
//        subject.add("History,maths");
//        subject.add("History,maths");
//        subject.add("History,maths");
//        subject.add("History,maths");
//        subject.add("History,maths");
//        subject.add("History,maths");
//
//        institute.add("Treetor school");
//        institute.add("Treetor school");
//        institute.add("Treetor school");
//        institute.add("Treetor school");
//        institute.add("Treetor school");
//        institute.add("Treetor school");

        requestQueue= Volley.newRequestQueue(getContext());
        String url ="https://treetor.in/batch-students/?batch="+bundle.get("batchuid").toString()+"&uid="+bundle.get("iuid").toString();
        Log.d("HelloURL",url);
        jsonparse(url);

//        recyclerView.setHasFixedSize(true);
//        int columns = resources.getConfiguration()
//                .orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;
//
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), columns);
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(columns,
//                Utils.dpToPx(resources, 3), true));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setLayoutManager(mLayoutManager);

    }

    public void jsonparse(final String url)
    {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("please wait..");
        progressDialog.show();

        recyclerView.removeAllViews();
        name.clear();
        subject.clear();
        standard.clear();
        institute.clear();


        final JSONObject[] jsonObject = {null};
        HttpsTrustManager.allowAllSSL();
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // Toast.makeText(getContext(),"s1"+response.toString(),Toast.LENGTH_SHORT).show();
                try {
                    //Toast.makeText(getContext(),"s1"+response.toString(),Toast.LENGTH_SHORT).show();
                    for(int i=0;i<response.length();i++)
                    {
                        JSONObject list=response.getJSONObject(i);

                        Log.d("HelloHO",list.get("name").toString());
                        name.add(list.get("name").toString());
                        subject.add(list.get("phone").toString());
                        standard.add(list.get("class").toString());
                        institute.add(list.get("rating").toString());

                    }
                    FragmentManager f_manager;
                    f_manager = getFragmentManager();
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);

                    recyclerView.setLayoutManager(mLayoutManager);

                    adapter = new AllStudentFragmentAdapter(getContext(),name,standard,subject,institute,f_manager);
                    recyclerView.setAdapter(adapter);
                    progressDialog.hide();
                    //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    //adapter = new AttendanceNewFragmentAdapter(getContext(),time,subject,venue,teach,iuid,batchuid,subjectid,f_manager);
                    //recyclerView.setAdapter(adapter);
                    //jsonObject[0] = new JSONObject(response);

//                    Toast.makeText(getContext(),"s"+array.toString(),Toast.LENGTH_SHORT).show();
//                    for(int i=0;i<array.length();i++)
//                    {
//                        JSONObject list=array.getJSONObject(i);
//
//
//
//                        //Toast.makeText(getContext(),"s"+list.toString(),Toast.LENGTH_SHORT).show();
//
//
//
//
//
//
//
//
//                    }
                } catch (JSONException e) {
                    Log.d("HelloTRY",""+e);
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getContext(),"e"+error,Toast.LENGTH_SHORT).show();
                if(error.toString().equals("com.android.volley.NoConnectionError: javax.net.ssl.SSLHandshakeException: Handshake failed"))
                {
                    jsonparse(url);
                    Log.d("HelloBAHar",""+error);
                    error.printStackTrace();
                }
                Log.d("HelloBAHar",""+error);
                error.printStackTrace();
                progressDialog.hide();
            }
        });
        requestQueue.add(request);
    }
}
