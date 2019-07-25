package com.example.myapplication2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication2.entities.AppGroup;

public class ListAppGroup extends RecyclerView.Adapter<ListAppGroup.ViewHolder>{

    private AppGroup[] listdata;

    public ListAppGroup(AppGroup[] listData){
        this.listdata = listData;
    }

    @Override
    public ListAppGroup.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.group_full_detail_element, parent, false);
        ListAppGroup.ViewHolder viewHolder = new ListAppGroup.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListAppGroup.ViewHolder holder, int position) {
        final AppGroup myListData = listdata[position];
        holder.textView.setText(listdata[position].getGroupName());
        //holder.imageView.setImageResource(listdata[position].getImgId());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(myListData.getGroupName());
                System.out.println("Gruppo:" + myListData);
                Toast.makeText(view.getContext(),"click on item: "+myListData.getDescription(),Toast.LENGTH_LONG).show();

                System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA: " + myListData.getDescription());
                Intent intent = new Intent(view.getContext(), GroupInfoActivity.class);
                intent.putExtra("groupId",Long.toString(myListData.getGroupId()));
                intent.putExtra("groupName",myListData.getGroupName());
                intent.putExtra("groupDescription",myListData.getDescription());
                intent.putExtra("groupCreator",myListData.getCreator());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            /*this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.textView = (TextView) itemView.findViewById(R.id.textView2);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
            */
        }
    }
}
