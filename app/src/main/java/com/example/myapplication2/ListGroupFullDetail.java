package com.example.myapplication2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication2.entities.GroupFullDetail;

public class ListGroupFullDetail extends RecyclerView.Adapter<ListGroupFullDetail.LGFD_ViewHolder>{

    private GroupFullDetail[] listdata;

    public ListGroupFullDetail(GroupFullDetail[] listData){
        this.listdata = listData;
    }

    @NonNull
    @Override
    public LGFD_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ListGroupFullDetail.LGFD_ViewHolder viewHolder = new ListGroupFullDetail.LGFD_ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LGFD_ViewHolder lgfd_viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class LGFD_ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public RelativeLayout relativeLayout;
        public LGFD_ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }
}
