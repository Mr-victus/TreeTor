package dev.cavemen.treetor;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinner;
import com.androidbuts.multispinnerfilter.MultiSpinnerListener;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SingleSpinner;
import com.androidbuts.multispinnerfilter.SpinnerListener;
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
import java.util.List;

import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;

import static io.fabric.sdk.android.Fabric.TAG;


public class DiaryFragment extends Fragment {
    ArrayList<String> name,standard,uid,subject,institute,recpient,mailTo;
    ArrayList<String> batch,iuid,institute_name;
    AlertDialog.Builder builder;
    RecyclerView recyclerView;
    AllStudentFragmentAdapter adapter;
    MultiSpinnerSearch spinner;
    FirebaseAuth auth;
    Bundle bundle;

    RequestQueue requestQueue;

    public DiaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bundle=getArguments();
        return inflater.inflate(R.layout.fragment_diary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView n=view.findViewById(R.id.name);
        final FirebaseAuth auth=FirebaseAuth.getInstance();
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("please wait..");
        progressDialog.show();
        uid=new ArrayList<>();
        name=new ArrayList<>();
        recpient=new ArrayList<>();
        final EditText msg=view.findViewById(R.id.msg);
        TextView sendbtn=view.findViewById(R.id.sendbtn);
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

        final List<String> list= new ArrayList<>();

        List<String> subject=new ArrayList<>();
        subject.add("Notice");

        final List<KeyPairBoolData> listArray1 = new ArrayList<>();

        for (int i = 0; i < subject.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(subject.get(i));
            h.setSelected(false);
            listArray1.add(h);
        }
        spinner =(MultiSpinnerSearch)  view.findViewById(R.id.SearchableSpinner);



        SingleSpinner spinner1=view.findViewById(R.id.singleSpinner);
        spinner1.setItems(listArray1, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });


        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ms=msg.getText().toString();
               // Log.d("HelloMsg",ms+recpient.toString());
                requestQueue= Volley.newRequestQueue(getContext());
                if(!recpient.isEmpty())
                {
                    String to=""+recpient.get(0);
                    for(int i=1;i<recpient.size();i++)
                    {
                        to=to+","+recpient.get(i);
                    }
                    String url ="https://treetor.in/diary-input/?to="+to+"&subject=notice"+"&message="+ms+"&tid="+auth.getCurrentUser().getUid();
                    Log.d("HelloURL",url);
                    jsonparse2(url);
                }
                else
                {
                    Toast.makeText(getContext(),"Please Fill All The Details!",Toast.LENGTH_SHORT).show();
                }


            }
        });

        requestQueue= Volley.newRequestQueue(getContext());
        String url ="https://treetor.in/batch-students/?batch="+bundle.get("batchuid").toString()+"&uid="+bundle.get("iuid").toString();
        Log.d("HelloURL",url);
        jsonparse(url);





        // Creating ArrayAdapter using the string array and default spinner layout
//        ArrayAdapter<String> adapter =new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_dropdown_item, name);
//        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
//
//        // Specify layout to be used when list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // Applying the adapter to our spinner
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

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

//        requestQueue= Volley.newRequestQueue(getContext());
//        String url ="https://treetor.in/batch-students/?batch="+bundle.get("batchuid").toString()+"&uid="+bundle.get("iuid").toString();
//        Log.d("HelloURL",url);
//        jsonparse(url);

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
                        uid.add(list.get("id").toString());

                    }
                    final List<KeyPairBoolData> listArray0 = new ArrayList<>();
                    for (int i = 0; i < name.size(); i++) {
                        KeyPairBoolData h = new KeyPairBoolData();
                        h.setId(i + 1);
                        h.setName(name.get(i));
                        h.setSelected(false);
                        listArray0.add(h);
                    }
                    spinner.setItems(listArray0, -1, new SpinnerListener() {

                        @Override
                        public void onItemsSelected(List<KeyPairBoolData> items) {

                            for (int i = 0; i < items.size(); i++) {
                                if (items.get(i).isSelected()) {
                                    Log.i("HelloHogaya", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                    recpient.add(uid.get(i));
                                }
                                else
                                {
                                    recpient.remove(uid.get(i));
                                }
                            }
                            Log.d("HelloHogaya",recpient.toString());
                        }
                    });
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
    public void jsonparse2(final String url)
    {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("please wait..");
        progressDialog.show();

        final JSONObject[] jsonObject = {null};
        HttpsTrustManager.allowAllSSL();
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // Toast.makeText(getContext(),"s1"+response.toString(),Toast.LENGTH_SHORT).show();
                try {
                    Toast.makeText(getContext(),"s1"+response.toString(),Toast.LENGTH_SHORT).show();



                    for(int i=0;i<response.length();i++)
                    {
                        JSONObject list=response.getJSONObject(i);
//                        Log.d("HelloHO",list.get("name").toString());
//                        name.add(list.get("name").toString());
//                        uid.add(list.get("id").toString());


                    }

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
                    progressDialog.hide();
                    Intent intent=new Intent(getContext(),MainActivity.class);
                    startActivity(intent);
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
            }
        });
        requestQueue.add(request);
    }
}
