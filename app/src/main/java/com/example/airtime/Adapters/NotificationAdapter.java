package com.example.airtime.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airtime.R;
import com.example.airtime.TaskModel;
import com.example.airtime.ViewDetailsActivity;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    Context context;
    ArrayList<TaskModel> list;

    public NotificationAdapter(Context context, ArrayList<TaskModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationAdapter.ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.tasknotfdesgn, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskModel model = this.list.get(position);
        holder.mname.setText(model.getTitle());
        holder.mdesc.setText(model.getS_desc());
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
    }


    public int getItemCount() {
        return this.list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mdesc;
        TextView mname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mname = (TextView) itemView.findViewById(R.id.text_titles);
            this.mdesc = (TextView) itemView.findViewById(R.id.text_descriptions);
        }
    }
}
