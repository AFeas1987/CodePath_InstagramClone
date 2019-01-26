package com.af1987.codepath.instagramclone.ui.recyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.af1987.codepath.instagramclone.Post;
import com.af1987.codepath.instagramclone.R;
import com.bumptech.glide.Glide;
import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;
    private UserClickListener userClickListener;

    public interface UserClickListener {
        void onUserClick(ParseUser user);
    }

    public PostAdapter(Context context, List<Post> posts, UserClickListener userClickListener) {
        this.context = context;
        this.posts = posts;
        this.userClickListener = userClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.rv_item_post, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Post post = posts.get(i);
        viewHolder.bind(post);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUser;
        private TextView tvPost;
        private ImageView ivPost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUser = itemView.findViewById(R.id.tvUser);
            tvPost = itemView.findViewById(R.id.tvPost);
            ivPost = itemView.findViewById(R.id.ivPost);
        }

        public void bind(Post post) {
            tvUser.setText(post.getUser().getUsername());
            tvPost.setText(post.getDescription());
            ParseFile image = post.getImage();
            if (image != null)
                Glide.with(context).load(image.getUrl()).into(ivPost);
            else
                ivPost.setVisibility(View.GONE);

            tvUser.setOnClickListener(v ->
                    userClickListener.onUserClick(post.getUser()));
        }
    }
}
