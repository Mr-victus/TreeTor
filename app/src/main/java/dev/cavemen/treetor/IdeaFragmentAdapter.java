package dev.cavemen.treetor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class IdeaFragmentAdapter extends RecyclerView.Adapter<IdeaFragmentAdapter.viewholder> {

    ArrayList<String> titlename,time,date;
    FragmentManager f_manager;

    Context context;
    View view;

    public IdeaFragmentAdapter(Context context, ArrayList<String> titlename, ArrayList<String> time, ArrayList<String> date){
        this.context = context;
        this.titlename=titlename;

        this.time=time;
        this.date=date;




    }

    public IdeaFragmentAdapter() {

    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.customideabox,parent,false);



        return new viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, final int position) {









        holder.titletextvie.setText(""+titlename.get(position));
        holder.timetext.setText(""+time.get(position));
        holder.datetext.setText(""+date.get(position));











        // holder.customer_pic.setImageResource(customerpic.get(position));


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });
    }


    @Override
    public int getItemCount() {
        return titlename.size();
    }

    public  class viewholder extends RecyclerView.ViewHolder{
        TextView titletextvie,timetext,datetext;
        public viewholder(View itemView) {
            super(itemView);
            titletextvie=(TextView)itemView.findViewById(R.id.titleideabox);
            timetext=itemView.findViewById(R.id.timestamp);
            datetext=itemView.findViewById(R.id.datestamp);





            // customer_pic=(ImageView) itemView.findViewById(R.id.doctor_pic);

            Log.d("TAAAAG","kk");

        }

    }

}

