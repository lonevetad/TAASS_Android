package com.example.myapplication2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication2.payloadsResponses.GroupFullDetail;

public class ListGroupFullDetail extends RecyclerView.Adapter<ListGroupFullDetail.LGFD_ViewHolder> {

    private GroupFullDetail[] listdata;

    public ListGroupFullDetail(GroupFullDetail[] listData) {
        this.listdata = listData;
    }

    @NonNull
    @Override
    public LGFD_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.group_full_detail_element, parent, false);
        ListGroupFullDetail.LGFD_ViewHolder viewHolder = new ListGroupFullDetail.LGFD_ViewHolder(parent.getContext(), listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LGFD_ViewHolder lgfd_viewHolder, int position) {
        final GroupFullDetail myListData = listdata[position];
        lgfd_viewHolder.setData(listdata[position]);

        //holder.imageView.setImageResource(listdata[position].getImgId());
        // al click, mostra il dettaglio del gruppo
        lgfd_viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(myListData.getGroupName());
                System.out.println("GroupFullDetail:" + myListData);
                Toast.makeText(view.getContext(), "click on item: " + myListData.getDescription(), Toast.LENGTH_LONG).show();

                System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA: " + myListData.getDescription());
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


    /**Rappresentazione del singolo elemento della lista di gruppi, ossia il
     * singolo gruppo con tutti i dettagli*/
    public static class LGFD_ViewHolder extends RecyclerView.ViewHolder {
        protected Context context;
        public ImageView imageView;
        public TextView name, date, location, creator, memberCount;
        public ListView tagsContainer;
        //public ListView membersContainer;
        public RelativeLayout relativeLayout;
        GroupFullDetail gfd;

        public LGFD_ViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            this.relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayoutGFDE);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.name = (TextView) itemView.findViewById(R.id.nameGroup);
            this.date = (TextView) itemView.findViewById(R.id.dateGroup);
            this.location = (TextView) itemView.findViewById(R.id.locationGroup);
            this.creator = (TextView) itemView.findViewById(R.id.creatorGroup);
            //this.memberCount = (TextView) itemView.findViewById(R.id.memberCountrGroup);
            //this.tagsContainer = (ListView) itemView.findViewById(R.id.tagsContainerGroup);
            //this.membersContainer = (RecyclerView) itemView.findViewById(R.id.membersContainerGroup);
        }

        public void setData(GroupFullDetail g){
            //ArrayAdapter adapter;
            this.gfd = g;
            this.name.setText(g.getGroupName());
            this.date.setText(g.getGroupDate().toString());
            this.location.setText(g.getLocation().getName());
            this.creator.setText(g.getCreator());
            //this.memberCount.setText("Members: " + g.getMembers().size());
            //adapter = new ArrayAdapter(context, R.id.tagsLayoutPanelGroup, g.getMembers());
            //this.tagsContainer.setAdapter(adapter);
        }
    }
}
