package dev.cavemen.treetor;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ClassroomFragmentAdapter extends RecyclerView.Adapter<ClassroomFragmentAdapter.viewholder> {

    ArrayList<String> subjectuid,instituteuid,batchuid;
    FragmentManager f_manager;

    Context context;
    View view;

    public ClassroomFragmentAdapter(Context context, ArrayList<String> subjectuid,ArrayList<String>instituteuid,ArrayList<String>batchuid,FragmentManager f_manager
    ){
        this.context = context;
        this.subjectuid=subjectuid;
        this.instituteuid=instituteuid;
        this.batchuid=batchuid;
        this.f_manager=f_manager;

    }

    public ClassroomFragmentAdapter() {

    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.customclassroominstitute,parent,false);
        return new viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull viewholder holder, final int position) {

        holder.institute_name.setText(subjectuid.get(position));


       // String ti="<font color=#EFA017>"+nextclass.get(position)+"</font>";
       // String at="<font color=#EFA017>"+attendance.get(position)+"</font>";

       // holder.time.append(Html.fromHtml(ti));
       // holder.attendance.append(Html.fromHtml(at));

        // holder.customer_pic.setImageResource(customerpic.get(position));
        holder.attendance.setVisibility(View.INVISIBLE);





        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment attendanceFragment=new AttendanceFragment();

                Bundle bundle = new Bundle();
                bundle.putString("instituteuid", instituteuid.get(position));
                bundle.putString("batchuid",batchuid.get(position));


                attendanceFragment.setArguments(bundle);
                f_manager.beginTransaction().replace(R.id.fragment_container,attendanceFragment).addToBackStack("ClassroomFragment").commit();






            }
        });
    }


    @Override
    public int getItemCount() {
        return instituteuid.size();
    }

    public  class viewholder extends RecyclerView.ViewHolder{
        TextView institute_name,time,attendance;
        ImageView customer_pic;
        public viewholder(View itemView) {
            super(itemView);
            institute_name=(TextView)itemView.findViewById(R.id.institutenametextview);
            time=(TextView)itemView.findViewById(R.id.nextclasstextview);
            attendance=(TextView)itemView.findViewById(R.id.attendancetextview);

            // customer_pic=(ImageView) itemView.findViewById(R.id.doctor_pic);

            Log.d("TAAAAG","kk");

        }

    }

}

