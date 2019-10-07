package dev.cavemen.treetor;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RatingFragmentAdapter extends RecyclerView.Adapter<RatingFragmentAdapter.viewholder> {

    ArrayList<String> name,uid;
    FragmentManager f_manager;
    String iuid,batchuid,subjectid;
    Map map;
    Map present,absent;
    Context context;
    Float rate=0.0f;
    View view;
    int c;
    RequestQueue requestQueue;


    public RatingFragmentAdapter(Context context, ArrayList<String> name, ArrayList<String> uid, String iuid, String batchuid,String subjectid,FragmentManager f_manager){
        this.context = context;
        this.name=name;
        this.uid=uid;
        this.iuid=iuid;
        this.batchuid=batchuid;
        map=new HashMap();
        this.f_manager=f_manager;
        this.subjectid=subjectid;
    }

    public RatingFragmentAdapter() {

    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.customrating,parent,false);
        for(int i=0;i<uid.size();i++)
        {
            map.put(uid.get(i),"0");
        }
        return new viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, final int position) {


//        if(stuname.size()!=position)
//        {
//            absent=new HashMap();
//
//            present.put(uid.get(position),0);
//        }
        if(position==uid.size())
        {
            holder.attendance.setVisibility(View.VISIBLE);


            holder.attendance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Toast.makeText(context,""+rate,Toast.LENGTH_SHORT).show();


                    holder.dialog.setContentView(R.layout.curriculum_popup);
                    holder.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    holder.dialog.show();
                    Button submitbtn;
                    final EditText today,tomorrow;
                    submitbtn=holder.dialog.findViewById(R.id.curriculumsubmitbtn);
                    today=holder.dialog.findViewById(R.id.taughttoday);
                    tomorrow=holder.dialog.findViewById(R.id.taughttoday);

                    submitbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String taught=today.getText().toString();
                            holder.dialog.hide();
                            holder.dialog.setContentView(R.layout.curriculum_popup);
                            holder.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            holder.dialog.show();
                            Button submitbtn;
                            final EditText today,tomorrow;
                            TextView textView=holder.dialog.findViewById(R.id.textView13);
                            textView.setText("What will be taught \n" +
                                    "in the next class ?");
                            submitbtn=holder.dialog.findViewById(R.id.curriculumsubmitbtn);
                            today=holder.dialog.findViewById(R.id.taughttoday);
                            submitbtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String next=today.getText().toString();

                                    requestQueue= Volley.newRequestQueue(context);
                                    JSONObject json = new JSONObject(map);
                                    String jsonString=json.toString().replaceAll("\"","\\\"");

//                                    DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
//                                    Map map=new HashMap();
//                                    map.put("")

                                    String url ="https://treetor.in/set-rating/?data="+jsonString+"&"+"uid="+iuid+"&"+"batch="+batchuid+"&"+"teach="+taught+"&"+"next="+next+"&"+"subject="+subjectid;
                                    Log.d("HelloURL",url);
                                    jsonparse(url);
                                    holder.dialog.hide();
                                }
                            });




                        }
                    });



                    //sendPostRequest();
                }
            });
        }
        else {
//            holder.layout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Toast.makeText(context, "" + holder.c, Toast.LENGTH_SHORT).show();
////                    if (holder.c == 0) {
////                        holder.c = 1;
////                        holder.layout.setBackgroundResource(R.color.treetorgreen);
////                        map.put(uid.get(position), "1");
////                    } else {
////                        holder.c = 0;
////                        holder.layout.setBackgroundResource(R.color.treetor_white);
////                        map.put(uid.get(position), "0");
////                    }
//                    Toast.makeText(context, map.toString(), Toast.LENGTH_SHORT).show();
//
//                }
//            });
            holder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    rate=rating;
                    map.put(uid.get(position),rate.toString());
                    //Toast.makeText(context,rate.toString(),Toast.LENGTH_SHORT).show();
                }
            });
            map.put(uid.get(position),String.valueOf(holder.ratingBar.getRating()));
            holder.name.setText(String.valueOf(position + 1) + " " + name.get(position));
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return name.size()+1;
    }

    public  class viewholder extends RecyclerView.ViewHolder{
        TextView name;
        int c;
        Dialog dialog;
        Button attendance;
        View layout;
        RatingBar ratingBar;
        Map presentt=new HashMap();
        public viewholder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
             layout=itemView.findViewById(R.id.customattendance);
             attendance=(Button)itemView.findViewById(R.id.submitbtn);
             ratingBar=itemView.findViewById(R.id.ratingbar);
            c=0;
            dialog=new Dialog(context);







            // customer_pic=(ImageView) itemView.findViewById(R.id.doctor_pic);

            Log.d("TAAAAG","kk");

        }

    }
    public void jsonparse(final String url)
    {

        final ProgressDialog progressDialog = new ProgressDialog(context);
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
                   // Toast.makeText(context,"s1"+response.toString(),Toast.LENGTH_SHORT).show();



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
                    e.printStackTrace();
                    progressDialog.hide();
                    Intent i=new Intent(context,MainActivity.class);
                    context.startActivity(i);
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
                progressDialog.hide();
            }
        });
        requestQueue.add(request);
    }


}



