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

public class LeaderboardFragmentAdapter extends RecyclerView.Adapter<LeaderboardFragmentAdapter.viewholder> {

    ArrayList<String> stuname,score,rank;
    FragmentManager f_manager;

    Context context;
    View view;

    public LeaderboardFragmentAdapter(Context context, ArrayList<String> stuname,ArrayList<String> score,ArrayList<String> rank){
        this.context = context;
        this.stuname=stuname;

        this.score=score;
        this.rank=rank;




    }

    public LeaderboardFragmentAdapter() {

    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.customleaderboard,parent,false);



        return new viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, final int position) {









        holder.stunamee.setText(""+stuname.get(position));
        holder.sturank.setText(""+rank.get(position));
        holder.stuscore.setText(""+score.get(position));











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
        TextView stunamee,stuscore,sturank;
        public viewholder(View itemView) {
            super(itemView);
            stunamee=(TextView)itemView.findViewById(R.id.allstudentname);
            stuscore=itemView.findViewById(R.id.scoreleaderboard);
            sturank=itemView.findViewById(R.id.leaderboardrank);





            // customer_pic=(ImageView) itemView.findViewById(R.id.doctor_pic);

            Log.d("TAAAAG","kk");

        }

    }

}

