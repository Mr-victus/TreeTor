package dev.cavemen.treetor;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AttendanceStudentFragmentAdapter extends RecyclerView.Adapter<AttendanceStudentFragmentAdapter.viewholder> {

    ArrayList<String> name,uid,p,n;
   String iuid,batchuid,subjectid;
    Map<String,String> map,nam;
    Map present,absent;
    Context context;
    View view;
    FragmentManager f_manager;
    FirebaseAuth auth;
    int c;
    RequestQueue requestQueue;

    public AttendanceStudentFragmentAdapter(Context context, ArrayList<String> name,ArrayList<String> uid,String iuid,String batchuid,String subjectid,FragmentManager f_manager){
        this.context = context;
        this.name=name;
        this.uid=uid;
        this.iuid=iuid;
        this.batchuid=batchuid;
        this.f_manager=f_manager;
        this.map=new HashMap<String, String>();
        this.nam=new HashMap<String, String>();
        this.p=new ArrayList<>();
        this.n=new ArrayList<>();
        this.subjectid=subjectid;
        for(int i=0;i<uid.size();i++)
        {
            map.put(uid.get(i),"0");
            nam.put(name.get(i),"0");
        }

    }

    public AttendanceStudentFragmentAdapter() {

    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.customattendance,parent,false);

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

                    requestQueue= Volley.newRequestQueue(context);
                    JSONObject json = new JSONObject(map);
                    Log.d("HelloAfter",map.toString());
                    String jsonString=json.toString().replaceAll("\"","\\\"");
                    auth=FirebaseAuth.getInstance();
                    String url ="https://treetor.in/set-attendance/?data="+jsonString+"&"+"uid="+iuid+"&"+"batch="+batchuid+"&"+"teacher="+auth.getCurrentUser().getUid()+"&"+"subject_id="+subjectid;
                    Log.d("HelloURL",url);
                    jsonparse(url);
                    Fragment selectedfragment=new RatingFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("iuid",iuid);
                    bundle.putString("batchuid",batchuid);
                    bundle.putString("subject_id",subjectid);

                    for(String key :map.keySet())
                    {
                        if(map.get(key).equals("1"))
                        {
                            p.add(key);

                        }
                    }
                    for(String key :nam.keySet())
                    {
                        if(nam.get(key).equals("1"))
                        {
                            n.add(key);

                        }
                    }
                    bundle.putStringArrayList("uid",uid);
                    bundle.putStringArrayList("name",name);
                    selectedfragment.setArguments(bundle);
                    f_manager.beginTransaction().replace(R.id.fragment_container,selectedfragment).commit();
                    //sendPostRequest();
                }
            });
        }
        else {
            holder.c=0;
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Toast.makeText(context, "" + holder.c, Toast.LENGTH_SHORT).show();
                    if (holder.c == 0) {
                        holder.c = 1;
                        holder.layout.setBackgroundResource(R.color.treetorgreen);
                        map.put(uid.get(position), "1");
                        nam.put(name.get(position),"1");

                    } else {
                        holder.c = 0;
                        holder.layout.setBackgroundResource(R.color.treetor_white);
                        map.put(uid.get(position), "0");
                        nam.put(name.get(position),"0");
                    }
                    //Toast.makeText(context, map.toString(), Toast.LENGTH_SHORT).show();

                }
            });
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
        Map presentt=new HashMap();
        public viewholder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
             layout=itemView.findViewById(R.id.customattendance);
             attendance=(Button)itemView.findViewById(R.id.submitbtn);
            c=0;
            dialog=new Dialog(context);







            // customer_pic=(ImageView) itemView.findViewById(R.id.doctor_pic);

            Log.d("TAAAAG","kk");

        }

    }
    public void jsonparse(final String url)
    {

        final JSONObject[] jsonObject = {null};
        HttpsTrustManager.allowAllSSL();
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // Toast.makeText(getContext(),"s1"+response.toString(),Toast.LENGTH_SHORT).show();
                try {
                    Toast.makeText(context,"s1"+response.toString(),Toast.LENGTH_SHORT).show();



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
    private class SendDeviceDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String data = "";

            HttpURLConnection httpURLConnection = null;
            try {

                httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes("PostData=" + params[1]);
                wr.flush();
                wr.close();

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data += current;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
        }
    }

}



