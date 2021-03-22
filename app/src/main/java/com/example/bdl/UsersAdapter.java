package com.example.bdl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserAdapterVH> {

    private List<UserResponse> userResponseList;
    private Context context;
    private ClickedItem clickedItem;

    public UsersAdapter(ClickedItem clickedItem) {
        this.clickedItem = clickedItem;
    }
    public void setData(List<UserResponse> userResponseList) {
        this.userResponseList = userResponseList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new UsersAdapter.UserAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_users,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapterVH holder, int position) {
        UserResponse userResponse = userResponseList.get(position);
        String username = userResponse.getUSER_NAME();
        String prefix = userResponse.getPREFIX();
        String bloobgroup = userResponse.getBLOOD_GROUP();
        holder.prefix.setText(prefix);
        holder.username.setText(username);
        holder.bloodGroup.setText(bloobgroup);
        holder.imagemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedItem.ClickedUser(userResponse);
            }
        });

    }

    public interface ClickedItem{
        public void ClickedUser(UserResponse userResponse);
    }
    @Override
    public int getItemCount() {
        return userResponseList.size();
    }

    public class UserAdapterVH extends RecyclerView.ViewHolder {
        TextView username;
        TextView prefix;
        TextView bloodGroup;
        ImageView imagemore;

        public UserAdapterVH(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.userName);
            prefix = itemView.findViewById(R.id.prefix);
            imagemore = itemView.findViewById(R.id.ImageMore);
            bloodGroup = itemView.findViewById(R.id.bloodGroup);
        }
    }
}
