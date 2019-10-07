package dev.cavemen.treetor;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AllInsituteFragmentAdapter extends RecyclerView.Adapter<AllInsituteFragmentAdapter.viewholder> {

    ArrayList<String> institutename,instituterating;

    Context context;
    View view;

    public AllInsituteFragmentAdapter(Context context, ArrayList<String> institutename,
                                      ArrayList<String> instituterating){
        this.context = context;
        this.institutename=institutename;
        this.instituterating=instituterating;


    }

    public AllInsituteFragmentAdapter() {

    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.customallinstitute,parent,false);



        return new viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull viewholder holder, final int position) {


        if(institutename.get(position).length()>16)
        {
            holder.institute_name.setText(institutename.get(position).substring(0,16)+"...");
        }




        holder.letterbtn.setText(institutename.get(position).substring(0,1));

        holder.rating.setText(instituterating.get(position));

        String[] color={"#5C9045","#4B6254","#1A6B33"};
        int[] btncolor={R.drawable.lettericonlightcolor,R.drawable.lettericonmeduimcolor,R.drawable.lettericondeepcolor};
        int index=position%3;

        holder.institute_name.setTextColor(Color.parseColor(color[index]));
        holder.letterbtn.setBackgroundResource(btncolor[index]);
        holder.rating.setTextColor(Color.parseColor(color[index]));









        // holder.customer_pic.setImageResource(customerpic.get(position));


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,""+institutename.get(position),Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public int getItemCount() {
        return institutename.size();
    }

    public  class viewholder extends RecyclerView.ViewHolder{
        TextView institute_name,rating,attendance;
        Button letterbtn;
        public viewholder(View itemView) {
            super(itemView);
            institute_name=(TextView)itemView.findViewById(R.id.allinstitutename);
            rating=(TextView)itemView.findViewById(R.id.ratingallinstitute);
            letterbtn=itemView.findViewById(R.id.letterbtn);


            // customer_pic=(ImageView) itemView.findViewById(R.id.doctor_pic);

            Log.d("TAAAAG","kk");

        }

    }

}

