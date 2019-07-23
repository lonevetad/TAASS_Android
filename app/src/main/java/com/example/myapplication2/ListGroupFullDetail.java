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
import com.example.myapplication2.entities.GroupFullDetail;

public class ListGroupFullDetail extends RecyclerView.Adapter<ListGroupFullDetail.LGFD_ViewHolder> {

    private GroupFullDetail[] listdata;

    public ListGroupFullDetail(GroupFullDetail[] listData) {
        this.listdata = listData;
    }

    @NonNull
    @Override
    public LGFD_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        ListGroupFullDetail.LGFD_ViewHolder viewHolder = new ListGroupFullDetail.LGFD_ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LGFD_ViewHolder lgfd_viewHolder, int position) {
        final GroupFullDetail myListData = listdata[position];
        lgfd_viewHolder.textView.setText(listdata[position].getGroupName());
        //holder.imageView.setImageResource(listdata[position].getImgId());
        lgfd_viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(myListData.getGroupName());
                System.out.println("GroupFullDetail:" + myListData);
                Toast.makeText(view.getContext(), "click on item: " + myListData.getDescription(), Toast.LENGTH_LONG).show();

                System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA: " + myListData.getGroupId());
                Intent intent = new Intent(view.getContext(), GroupInfoActivity.class);
                intent.putExtra("groupId", Long.toString(myListData.getGroupId()));
                intent.putExtra("groupName", myListData.getGroupName());
                intent.putExtra("groupDescription", myListData.getDescription());
                intent.putExtra("groupCreator", myListData.getCreator());
                intent.putExtra("groupFullDetail", myListData); // NOTA: passo un Serializable
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata != null ? listdata.length : 0;
    }

    public static class LGFD_ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public RelativeLayout relativeLayout;

        public LGFD_ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }
    }
}
