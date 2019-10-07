package dev.cavemen.treetor;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AllStudentFragmentAdapter extends RecyclerView.Adapter<AllStudentFragmentAdapter.viewholder> {

    ArrayList<String> stuname,std,subject,institute;
    FragmentManager f_manager;

    Context context;
    View view;

    public AllStudentFragmentAdapter(Context context, ArrayList<String> stuname,
                                     ArrayList<String> std, ArrayList<String> subject, ArrayList<String> institute, FragmentManager f_manager){
        this.context = context;
        this.stuname=stuname;
        this.std=std;
        this.subject=subject;
        this.institute=institute;
        this.f_manager=f_manager;



    }

    public AllStudentFragmentAdapter() {

    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.customallstudent,parent,false);



        return new viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, final int position) {


//        if(institute.get(position).length()>16)
//        {
//            holder.stuinstitute.setText(institute.get(position).substring(0,16)+"...");
//        }





        holder.stuinstitute.setText(""+institute.get(position).substring(0,3));
        holder.stunamee.setText(""+stuname.get(position));
        holder.stustd.setText("Std: "+std.get(position));
        holder.stusubject.setText("Phone: "+subject.get(position));











        // holder.customer_pic.setImageResource(customerpic.get(position));


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });
    }


    @Override
    public int getItemCount() {
        return stuname.size();
    }

    public  class viewholder extends RecyclerView.ViewHolder{
        TextView stunamee,stustd,stusubject,stuinstitute;
        public viewholder(View itemView) {
            super(itemView);
            stunamee=(TextView)itemView.findViewById(R.id.allstudentname);
            stustd=(TextView)itemView.findViewById(R.id.allstudentstd);
            stusubject=itemView.findViewById(R.id.allstudentsubject);
            stuinstitute=itemView.findViewById(R.id.allstudentinstitute);

            // customer_pic=(ImageView) itemView.findViewById(R.id.doctor_pic);

            Log.d("TAAAAG","kk");

        }

    }

}

