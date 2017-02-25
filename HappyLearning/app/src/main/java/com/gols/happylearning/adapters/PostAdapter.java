package com.gols.happylearning.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gols.happylearning.R;
import com.gols.happylearning.WebViewActivity;
import com.gols.happylearning.beans.WPPost;
import com.gols.happylearning.utils.AppUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 11/18/2016.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private static List<WPPost> wpPosts = new ArrayList<>();
    private static Context mContext;

    public PostAdapter(Context context){
        mContext = context;
    }

    public void setWpPosts(List<WPPost> wpPosts) {
        this.wpPosts = wpPosts;
        notifyDataSetChanged();
    }


    public void addWpPosts(List<WPPost> newWpPosts) {
        if(! this.wpPosts.containsAll(newWpPosts)) {
            this.wpPosts.addAll(newWpPosts);
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_post_layout, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textViewPostTitle.setText(wpPosts.get(position).getTitle());
        String imgUrl = wpPosts.get(position).getImageURL();
        try{
            imgUrl = imgUrl.substring(0,imgUrl.lastIndexOf(".png")+4);
            Log.d("image url",imgUrl);
            Picasso.with(mContext).load(imgUrl).into(holder.imageViewPostImage);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return wpPosts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textViewPostTitle;
        //,textViewFeedDescription;
        public ImageView imageViewPostImage;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            textViewPostTitle = (TextView) v.findViewById(R.id.txtPostTitle);
            //textViewFeedDescription = (TextView) v.findViewById(R.id.txtFeedDescription);
            imageViewPostImage = (ImageView) v.findViewById(R.id.imgPostImage);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra(AppUtil.KEY_POST_CONTENT, wpPosts.get(getAdapterPosition()).getContent());
            mContext.startActivity(intent);
        }
    }

}
