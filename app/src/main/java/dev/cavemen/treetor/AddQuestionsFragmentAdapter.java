package dev.cavemen.treetor;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddQuestionsFragmentAdapter extends RecyclerView.Adapter<AddQuestionsFragmentAdapter.viewholder> {

    ArrayList<String> questionno, question,option1,option2,option3,option4;
    FragmentManager f_manager;
    int flag;
    FirebaseAuth auth;
    Button startquiz;
    ArrayList<String> selectedquestion=new ArrayList<>();
    Context context;
    View view;

    public AddQuestionsFragmentAdapter(Context context, ArrayList<String> questionno, ArrayList<String> question,
                                       ArrayList<String> option1, ArrayList<String> option2, ArrayList<String> option3, ArrayList<String> option4, FragmentManager f_manager, Button startquiz,int  flag){
        this.context = context;
        this.questionno=questionno;
        this.question=question;
        this.option1=option1;
        this.option2=option2;
        this.option3=option3;
        this.option4=option4;
        this.f_manager=f_manager;
        this.startquiz=startquiz;
        this.flag=flag;

    }

    public AddQuestionsFragmentAdapter() {

    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.customaddquestion,parent,false);



        return new viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull viewholder holder, final int position) {

        holder.ques.setText("Q. "+question.get(position));

        auth=FirebaseAuth.getInstance();


//        String ti="<font color=#EFA017>"+nextclass.get(position)+"</font>";
//        String at="<font color=#EFA017>"+attendance.get(position)+"</font>";

//        holder.opt4.append(Html.fromHtml(ti));
//        holder.attendance.append(Html.fromHtml(at));

        holder.opt1.setText("A. "+option1.get(position));
        holder.opt2.setText("B. "+option2.get(position));
        holder.opt3.setText("C. "+option3.get(position));
        holder.opt4.setText("D. "+option4.get(position));

        // holder.customer_pic.setImageResource(customerpic.get(position));

        if(flag==1)
        {
            holder.box.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.box.setVisibility(View.VISIBLE);
        }


        holder.box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked)
               {
                   selectedquestion.add(questionno.get(position));

               }
               else {
                   selectedquestion.remove(questionno.get(position));
               }
            }
        });

        if(flag==2){
            startquiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"Here we go",Toast.LENGTH_SHORT).show();


                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("users").child("teachers").child(auth.getCurrentUser().getUid()).child("myquizes").push();
                    Map map=new HashMap();
                    Bundle bundle=new Bundle();
                    for(int i=0;i<selectedquestion.size();i++)
                    {
                        map.put(selectedquestion.get(i),"0");
                        bundle.putString(selectedquestion.get(i),"0");
                    }
                    reference.updateChildren(map);

                    Fragment ongoingTestFragment=new OngoingTestFragment();

                    ongoingTestFragment.setArguments(bundle);


                    f_manager.beginTransaction().replace(R.id.fragment_container,ongoingTestFragment).addToBackStack("ClassroomFragment").commit();







                }
            });
        }


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Fragment attendanceFragment=new AttendanceFragment();
//
//                f_manager.beginTransaction().replace(R.id.fragment_container,attendanceFragment).addToBackStack("ClassroomFragment").commit();

                Toast.makeText(context,""+selectedquestion,Toast.LENGTH_SHORT).show();






            }
        });
    }


    @Override
    public int getItemCount() {
        return question.size();
    }

    public  class viewholder extends RecyclerView.ViewHolder{
        TextView ques,opt1,opt2,opt3,opt4;
        ImageView customer_pic;
        CheckBox box;
        public viewholder(View itemView) {
            super(itemView);
            ques=(TextView)itemView.findViewById(R.id.question);
            opt1=(TextView)itemView.findViewById(R.id.option1);
            opt2=(TextView)itemView.findViewById(R.id.option2);
            opt3=(TextView)itemView.findViewById(R.id.option3);
            opt4=(TextView)itemView.findViewById(R.id.option4);
            box=itemView.findViewById(R.id.checkBox);

            // customer_pic=(ImageView) itemView.findViewById(R.id.doctor_pic);

            Log.d("TAAAAG","kk");

        }

    }

}

