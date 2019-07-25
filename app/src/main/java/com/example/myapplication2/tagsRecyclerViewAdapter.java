package com.example.myapplication2;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class tagsRecyclerViewAdapter extends RecyclerView.Adapter<tagsRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "tagsRecyclerViewAdapter";
    private ArrayList<String> mName = new ArrayList<>();
    //private ArrayList<String> tags_image_url= new ArrayList<>();
    private Context mContext;


    public tagsRecyclerViewAdapter( Context mContext,ArrayList<String> tags_name, ArrayList<String> tags_image_url) {
        this.mName = tags_name;
        //this.tags_image_url = tags_image_url;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        /*
        Immagine

        Glide.with(mContext)
                .asBitmap()
                .load(tags_image_url.get(position))
                .into(holder.tag_image);*/



        holder.tag_name.setText(mName.get(position));
        holder.tag_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(EsploraActivity.selectedTags.contains(holder.tag_name.getText().toString())){
                    holder.cardView.setCardBackgroundColor(Color.WHITE);
                    EsploraActivity.selectedTags.remove(holder.tag_name.getText().toString());
                    System.out.println(EsploraActivity.selectedTags.size());
                }else {
                    EsploraActivity.selectedTags.add(holder.tag_name.getText().toString());
                    holder.cardView.setCardBackgroundColor(Color.TRANSPARENT);
                    System.out.println(EsploraActivity.selectedTags.size());
                }

                Log.d(TAG,"onClick: clicked on an image: " + mName.get(position));
                Toast.makeText(mContext,mName.get(position),Toast.LENGTH_SHORT).show();
            }
        });

        /*holder.tag_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG,"onClick: clicked on an image: " + mName.get(position));
                Toast.makeText(mContext,mName.get(position),Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView tag_image;
        TextView tag_name;
        public CardView cardView;

        public ViewHolder(View itemView){
            super(itemView);
            //tag_image = itemView.findViewById(R.id.tag_image);
            tag_name = itemView.findViewById(R.id.tag_text);
            cardView = (CardView) itemView.findViewById(R.id.tag_card);
        }
    }
}
