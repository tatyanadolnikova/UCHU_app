package com.example.android.uchu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserCardAdaptor extends RecyclerView.Adapter<UserCardAdaptor.UserCardViewHolder> {

    private Context context;
    private ArrayList<UserCard> userList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public UserCardAdaptor(Context context, ArrayList<UserCard> userList) {
        this.context = context;
        this.userList = userList;
    }

    public class UserCardViewHolder extends RecyclerView.ViewHolder {

        public ImageView uPhoto;
        public TextView uName;

        public UserCardViewHolder(@NonNull View itemView) {
            super(itemView);
            uPhoto = itemView.findViewById(R.id.image_rec_view);
            uName = itemView.findViewById(R.id.name_rec_view);
        }
    }

    @NonNull
    @Override
    public UserCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.user_card, parent, false);
        return new UserCardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserCardViewHolder holder, int position) {
        UserCard userCard = userList.get(position);
        String imageUrl = userCard.getImageUrl();
        String uName = userCard.getuName();
        holder.uName.setText(uName);
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.uPhoto);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
