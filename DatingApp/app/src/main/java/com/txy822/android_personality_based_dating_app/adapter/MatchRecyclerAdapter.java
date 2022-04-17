package com.txy822.android_personality_based_dating_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.txy822.android_personality_based_dating_app.ChatActivity;
import com.txy822.android_personality_based_dating_app.R;
import com.txy822.android_personality_based_dating_app.utils.Match;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Match list display class
 */
public class MatchRecyclerAdapter extends RecyclerView.Adapter<MatchRecyclerAdapter.ViewHolder> {
    Context context;
    List<Match> mMatchList;
    public MatchRecyclerAdapter(Context context, List<Match> mMatchList) {
        this.context = context;
        this.mMatchList = mMatchList;
    }

    /**
     * Create single match view holder
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_match_user_item,parent,false);
        return new ViewHolder(view);
    }

    /**Called by match RecyclerView to display the match at the specified position
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.mName.setText(mMatchList.get(position).getFullName());
        Glide.with(context).load(mMatchList.get(position).getImg_url()).into(holder.mImg);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ChatActivity.class);
                intent.putExtra("doc_id",mMatchList.get(position).getUser_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMatchList.size();
    }

    /**
     * ViewHolder to hold the match name and image on the match list
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mName;
        CircleImageView mImg;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            mName = itemView.findViewById(R.id.match_name);
            mImg = itemView.findViewById(R.id.match_img);
        }
    }
}
