package dev.cavemen.treetor;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AttendanceNewFragmentAdapter extends RecyclerView.Adapter<AttendanceNewFragmentAdapter.viewholder> {

    ArrayList<String> time,subject,venue,teach,iuid,batchuid,rating,teachername,attendancepercent,subjectid;
   // String instituteuid,batchuid;
    Map present,absent;
    FragmentManager f_manager;
    Context context;
    View view;
    int c;
    //ArrayList<String> rating,ArrayList<String> attendancepercent,ArrayList<String> teachername

    public AttendanceNewFragmentAdapter(Context context, ArrayList<String> time, ArrayList<String>subject, ArrayList<String> venue, ArrayList<String> teach, ArrayList<String> iuid, ArrayList<String> batchuid,ArrayList<String>subjectid, FragmentManager f_manager){
        this.context = context;
        this.time=time;
        this.subject=subject;
        this.venue=venue;
        this.teach=teach;
        this.iuid=iuid;
        this.batchuid=batchuid;
        this.f_manager=f_manager;
        this.subjectid=subjectid;
//        this.rating=rating;
//        this.teachername=teachername;
//        this.attendancepercent=attendancepercent;

    }

    public AttendanceNewFragmentAdapter() {

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

        holder.time.setText(time.get(position));
        holder.subject.setText("Subject : "+subject.get(position));
        if(venue.get(position).length()<=14)
        {
            holder.venue.setText("venue : "+venue.get(position));
        }
        else
        {
            holder.venue.setText("venue : "+venue.get(position).substring(0,13)+"...");
        }

        holder.teach.setText("teach : "+teach.get(position));
//        holder.teachername.setText("Teacher : "+teachername.get(position));
//        holder.rating.setText("Rating : "+rating.get(position));
//        holder.attendancepercent.setText("Attendance percent : "+attendancepercent.get(position));

        holder.attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedfragment=new AttendanceStudentFragment();
                Bundle bundle=new Bundle();
                bundle.putString("iuid",iuid.get(position));
                bundle.putString("batchuid",batchuid.get(position));
                bundle.putString("subject_id",subjectid.get(position));
                selectedfragment.setArguments(bundle);
               f_manager.beginTransaction().replace(R.id.fragment_container,selectedfragment).commit();
            }
        });


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return time.size();
    }

    public  class viewholder extends RecyclerView.ViewHolder{
        TextView time,subject,venue,teach,rating,attendancepercent,teachername;
        int c;
        Dialog dialog;
        Button attendance;
        Map presentt=new HashMap();
        public viewholder(View itemView) {
            super(itemView);
            time=(TextView)itemView.findViewById(R.id.time);
            subject=(TextView)itemView.findViewById(R.id.subject);
            venue=(TextView)itemView.findViewById(R.id.venue);
            teach=(TextView)itemView.findViewById(R.id.teach);
//            rating=(TextView)itemView.findViewById(R.id.rating);
//            attendancepercent=(TextView)itemView.findViewById(R.id.attendancepercent);
//            teachername=(TextView)itemView.findViewById(R.id.teachername);

            attendance=itemView.findViewById(R.id.attendance);
            c=0;
            dialog=new Dialog(context);







            // customer_pic=(ImageView) itemView.findViewById(R.id.doctor_pic);

            Log.d("TAAAAG","kk");

        }

    }

}

