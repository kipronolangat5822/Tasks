package com.example.airtime;

import android.content.Context;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserDashboardAdapter extends RecyclerView.Adapter<UserDashboardAdapter.MyViewHolder> {
    Context context;
    ArrayList<TaskModel> list;
    FirebaseAuth mAuth;
    String subjects;



    public UserDashboardAdapter(Context context2, ArrayList<TaskModel> list2) {
        this.context = context2;
        this.list = list2;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(this.context).inflate(R.layout.tasklist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TaskModel model = this.list.get(position);
        holder.mname.setText(model.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context.getApplicationContext(), ViewDetailsActivity.class);
                intent.putExtra("id",model.getTaskId());
                intent.putExtra("dep",model.getDepartment());
                intent.putExtra("desc",model.getDescription());
                intent.putExtra("name",model.getTitle());
                intent.putExtra("start",model.getStartdate());
                intent.putExtra("end",model.getEnddate());
                intent.putExtra("taskid",model.getTaskId());
                context.startActivity(intent);
            }
        });
     /*   holder.mdesc.setText(model.getDescription());
        holder.mstart.setText(model.getStartdate());
        holder.mend.setText(" - "+model.getEnddate());
        mAuth=FirebaseAuth.getInstance();
        DatabaseReference referencesfgf =  FirebaseDatabase.getInstance().getReference("usertask").child(mAuth.getCurrentUser().getUid());
        referencesfgf.child(model.getTaskId()).child("status").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                subjects=snapshot.getValue(String.class);
                if (subjects.equalsIgnoreCase("accepted")){
                    holder.btn.setText("Complete");
                }
                else if(subjects.equalsIgnoreCase("Assigned")){
                    holder.btn.setText("Accept");
                }
                else if(subjects.equalsIgnoreCase("Completed")){
                    holder.btn.setText("Completed");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

      */

    }


    public int getItemCount() {
        return this.list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mname;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.mname = (TextView) itemView.findViewById(R.id.text_title);
        }
    }
}
