package dev.cavemen.treetor;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RatingFragment extends Fragment {
    RecyclerView recyclerView;
    RatingFragmentAdapter adapter;
    ArrayList<String> name,uid;
    Bundle bundle;
    RequestQueue requestQueue;

    public RatingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bundle=getArguments();
        return inflater.inflate(R.layout.fragment_rating, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView=view.findViewById(R.id.ratingrecyclerview);

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
        name=new ArrayList<>();
        uid=new ArrayList<>();
        name=bundle.getStringArrayList("name");
        uid=bundle.getStringArrayList("uid");
        FragmentManager f_manager;
        f_manager = getFragmentManager();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RatingFragmentAdapter(getContext(),name,uid,bundle.get("iuid").toString(),bundle.get("batchuid").toString(),bundle.get("subject_id").toString(),f_manager);
        recyclerView.setAdapter(adapter);
//        requestQueue= Volley.newRequestQueue(getContext());
//        String url ="https://treetor.in/get-batch-students/?uid="+bundle.get("iuid")+"&"+"batch="+bundle.get("batchuid");
//        Log.d("HelloURL",url);
//        jsonparse(url);
    }

//    public void jsonparse(String url)
//    {
//
//        final JSONObject[] jsonObject = {null};
//        HttpsTrustManager.allowAllSSL();
//        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//
//            @Override
//            public void onResponse(JSONArray response) {
//                // Toast.makeText(getContext(),"s1"+response.toString(),Toast.LENGTH_SHORT).show();
//                try {
//                    Toast.makeText(getContext(),"s1"+response.toString(),Toast.LENGTH_SHORT).show();
//
//
//
//                    for(int i=0;i<response.length();i++)
//                    {
//                        JSONObject list=response.getJSONObject(i);
//                        Log.d("HelloHO",list.get("name").toString());
//                        name.add(list.get("name").toString());
//                        uid.add(list.get("id").toString());
//
//
//                    }
//                    FragmentManager f_manager;
//                    f_manager = getFragmentManager();
//                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//                    adapter = new RatingFragmentAdapter(getContext(),name,uid,bundle.get("iuid").toString(),bundle.get("batchuid").toString(),f_manager);
//                    recyclerView.setAdapter(adapter);
//                    //jsonObject[0] = new JSONObject(response);
//
////                    Toast.makeText(getContext(),"s"+array.toString(),Toast.LENGTH_SHORT).show();
////                    for(int i=0;i<array.length();i++)
////                    {
////                        JSONObject list=array.getJSONObject(i);
////
////
////
////                        //Toast.makeText(getContext(),"s"+list.toString(),Toast.LENGTH_SHORT).show();
////
////
////
////
////
////
////
////
////                    }
//                } catch (JSONException e) {
//                    Log.d("HelloTRY",""+e);
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //Toast.makeText(getContext(),"e"+error,Toast.LENGTH_SHORT).show();
//                Log.d("HelloBAHar",""+error);
//                error.printStackTrace();
//            }
//        });
//        requestQueue.add(request);
//    }
}
