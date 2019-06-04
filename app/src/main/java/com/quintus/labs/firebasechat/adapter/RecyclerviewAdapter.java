package com.quintus.labs.firebasechat.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quintus.labs.firebasechat.R;
import com.quintus.labs.firebasechat.activity.ChatActivity;
import com.quintus.labs.firebasechat.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Quintus Labs on 3/6/2017.
 */

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.MyHolder> {

    List<User> listdata;
    Activity context;

    public RecyclerviewAdapter(Activity context, List<User> listdata) {
        this.context = context;
        this.listdata = listdata;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);

        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }


    public void onBindViewHolder(MyHolder holder, int position) {
        final User data = listdata.get(position);

        holder.vname.setText(data.getName());
        if (data.getProfileImage() != null) {
            Picasso.get().load(data.getProfileImage()).placeholder(R.drawable.user).into(holder.profile_image);
        }


        holder.chatUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("user_id", data.getuId());
                intent.putExtra("user_name", data.getName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {
        TextView vname;
        CircleImageView profile_image;
        LinearLayout chatUser;

        public MyHolder(View itemView) {
            super(itemView);
            vname = itemView.findViewById(R.id.vname);
            profile_image = itemView.findViewById(R.id.profile_image);
            chatUser = itemView.findViewById(R.id.chat_user);


        }
    }


}