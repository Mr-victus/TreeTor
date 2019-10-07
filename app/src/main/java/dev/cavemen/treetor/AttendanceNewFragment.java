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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceNewFragment extends Fragment {
    RecyclerView recyclerView;
    AttendanceNewFragmentAdapter adapter;
    ArrayList<String> time,subject,venue,teach,batchuid,iuid,rating,teachername,attendancepercent,subjectid;
    RequestQueue requestQueue;
    String dt;
    FirebaseAuth auth;
    public AttendanceNewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attendance_new, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.attendancerecyclerview);
        time=new ArrayList<>();
        subject=new ArrayList<>();
        teach=new ArrayList<>();
        venue=new ArrayList<>();
        batchuid=new ArrayList<>();
        iuid=new ArrayList<>();
        rating=new ArrayList<>();
        subjectid=new ArrayList<>();
        attendancepercent=new ArrayList<>();
        teachername=new ArrayList<>();
        final TextView n=view.findViewById(R.id.name);
        final FirebaseAuth auth=FirebaseAuth.getInstance();
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

//        time.add("10:00AM");
//        time.add("10:00AM");
//        time.add("10:00AM");
//        time.add("10:00AM");
//
//        subject.add("Maths");
//        subject.add("Maths");
//        subject.add("Maths");
//        subject.add("Maths");
//
//        teach.add("trignometry");
//        teach.add("trignometry");
//        teach.add("trignometry");
//        teach.add("trignometry");
//
//        venue.add("TAT");
//        venue.add("TAT");
//        venue.add("TAT");
//        venue.add("TAT");
        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        final HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(getActivity(), R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();
        horizontalCalendar.setElevation(9);
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                //do something
                //Toast.makeText(getContext(),String.valueOf(date.get(Calendar.YEAR))+"/"+String.valueOf(date.get(Calendar.MONTH)+1)+"/"+String.valueOf(date.get(Calendar.DATE)),Toast.LENGTH_SHORT).show();
                dt=String.valueOf(date.get(Calendar.YEAR))+"/"+String.valueOf(date.get(Calendar.MONTH)+1)+"/"+String.valueOf(date.get(Calendar.DATE));
                requestQueue= Volley.newRequestQueue(getContext());
                String url ="https://treetor.in/get-batches/?uid="+auth.getCurrentUser().getUid()+"&"+"date="+dt;
                Log.d("HelloURL",url);
                jsonparse(url);
            }
        });
        horizontalCalendar.goToday(true);
//        horizontalCalendar.refresh();






    }

    public void jsonparse(final String url)
    {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("please wait..");
        progressDialog.show();
        final JSONObject[] jsonObject = {null};
        HttpsTrustManager.allowAllSSL();

        recyclerView.removeAllViews();
        time.clear();
        subject.clear();
        venue.clear();
        teach.clear();
        iuid.clear();
        batchuid.clear();
        rating.clear();
        teachername.clear();
        attendancepercent.clear();
        subjectid.clear();
        recyclerView.removeAllViews();
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
               // Toast.makeText(getContext(),"s1"+response.toString(),Toast.LENGTH_SHORT).show();
                try {
                    //Toast.makeText(getContext(),"s1"+response.toString(),Toast.LENGTH_SHORT).show();
                    for(int i=0;i<response.length();i++)
                    {
                        JSONObject list=response.getJSONObject(i);
                        Log.d("HelloHO",list.get("time").toString());
                        time.add(list.get("time").toString());
                        subject.add(list.get("subject").toString());
                        venue.add(list.get("venue").toString());
                        teach.add(list.get("next").toString());
                        iuid.add(list.get("uid").toString());
                        batchuid.add(list.get("batch").toString());
                        subjectid.add(list.get("subject_id").toString());
                    }
                    FragmentManager f_manager;
                    f_manager = getFragmentManager();
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new AttendanceNewFragmentAdapter(getContext(),time,subject,venue,teach,iuid,batchuid,subjectid,f_manager);
                    recyclerView.setAdapter(adapter);
                    progressDialog.hide();
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
                Log.d("HelloBAHar",""+error);
                error.printStackTrace();
                if(error.toString().equals("com.android.volley.NoConnectionError: javax.net.ssl.SSLHandshakeException: Handshake failed"))
                {
                    jsonparse(url);
                    Log.d("HelloBAHar",""+error);
                    error.printStackTrace();
                }
                progressDialog.hide();

            }
        });
        requestQueue.add(request);
    }

}
