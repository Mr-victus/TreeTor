package dev.cavemen.treetor;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AttendanceFragmentAdapter extends RecyclerView.Adapter<AttendanceFragmentAdapter.viewholder> {

    ArrayList<String> stuname,uid,subject,institute;
    String instituteuid,batchuid;
    Map present,absent;
    Context context;
    View view;
    int c;

    public AttendanceFragmentAdapter(Context context, ArrayList<String> stuname,ArrayList<String>uid,String instituteuid,String batchuid){
        this.context = context;
        this.stuname=stuname;
        this.instituteuid=instituteuid;
        this.batchuid=batchuid;
        this.uid=uid;
        present=new HashMap();
        for(int i=0;i<uid.size();i++)
            present.put(uid.get(i),0);

    }

    public AttendanceFragmentAdapter() {

    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.customattendancestudent,parent,false);



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

        if(stuname.size()==position)
        {
            holder.submit.setVisibility(View.VISIBLE);
            holder.submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   //Toast.makeText(context,""+present.get(uid.get(17)),Toast.LENGTH_SHORT).show();
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
                            String timestamp=""+System.currentTimeMillis();
                            Iterator it = present.entrySet().iterator();
                            while (it.hasNext())
                            {
                                Map.Entry pair = (Map.Entry)it.next();
                                DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("users").child("institutes").child(instituteuid).child("batches").child(batchuid).child("dailyreport").child(timestamp).child(pair.getKey().toString());
                                Map map=new HashMap();
                                map.put("attendance",pair.getValue().toString());
                                map.put("rating","4.5");
                                map.put("review","Good");
                                map.put("today",""+today.getText().toString());
                                map.put("tomorrow",""+tomorrow.getText().toString());
                                reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        holder.dialog.hide();
                                    }
                                });

                            }
                        }
                    });

                   }
            });

        }
        else {


            holder.stunamee.setText("" + stuname.get(position));
            holder.c = 0;


            holder.attendance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Drawable d = context.getResources().getDrawable(R.drawable.attendanceunchecked);

                    if (holder.c == 0) {
                        holder.attendance.setBackgroundResource(R.drawable.attendancechecked);

                        holder.c = 1;

                        present.put(uid.get(position), 1);
//                        Toast.makeText(context,""+present.get(uid.get(position)),Toast.LENGTH_SHORT).show();
//                    if(absent.get(position)!=null){
//                        absent.remove(position);


                    } else {
                        holder.attendance.setBackgroundResource(R.drawable.attendanceunchecked);

                        holder.c = 0;
                        present.put(uid.get(position), 0);
//                        Toast.makeText(context,""+present.get(uid.get(position)),Toast.LENGTH_SHORT).show();
//                    if(!present.get(position).equals(null)){
//                        present.remove(position);
//                    }


                    }
                    Log.d("checcckk", present.toString());
                }
            });


            // holder.customer_pic.setImageResource(customerpic.get(position));


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (holder.c == 0) {
                        holder.attendance.setBackgroundResource(R.drawable.attendancechecked);
                        holder.c = 1;
                        present.put(uid.get(position), 1);
//                        Toast.makeText(context,""+present.get(uid.get(position)),Toast.LENGTH_SHORT).show();
//                    if(absent.get(position)!=null){
//                        absent.remove(position);
//                    }


                    } else {
                        holder.attendance.setBackgroundResource(R.drawable.attendanceunchecked);
                        holder.c = 0;
                        present.put(uid.get(position), 0);
//                        Toast.makeText(context,""+present.get(uid.get(position)),Toast.LENGTH_SHORT).show();
//                    if(!present.get(position).equals(null)){
//                        present.remove(position);
//                    }


                    }

                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return stuname.size()+1;
    }

    public  class viewholder extends RecyclerView.ViewHolder{
        TextView stunamee;
        int c;
        Dialog dialog;
        Button attendance,submit;
        Map presentt=new HashMap();
        public viewholder(View itemView) {
            super(itemView);
            stunamee=(TextView)itemView.findViewById(R.id.allstudentname);
            attendance=itemView.findViewById(R.id.attendance);
            c=0;
            //submit=itemView.findViewById(R.id.submit);
            dialog=new Dialog(context);







            // customer_pic=(ImageView) itemView.findViewById(R.id.doctor_pic);

            Log.d("TAAAAG","kk");

        }

    }

}

