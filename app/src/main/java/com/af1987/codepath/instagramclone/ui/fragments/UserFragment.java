package com.af1987.codepath.instagramclone.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.af1987.codepath.instagramclone.Post;
import com.af1987.codepath.instagramclone.R;
import com.af1987.codepath.instagramclone.ui.activities.MainActivity;
import com.af1987.codepath.instagramclone.ui.recyclerView.PostAdapter;
import com.parse.Parse;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {

    private RecyclerView rvUserFeed;
    private List<Post> postList;
    private PostAdapter adapter;
    private ParseUser user;

    public UserFragment () {}

    public static UserFragment newInstance(ParseUser user) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putParcelable("user", user);
        fragment.user = user;
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = getArguments().getParcelable("user");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvUserFeed = view.findViewById(R.id.rvHomeFeed);
        postList = new ArrayList<>();
        adapter = new PostAdapter(getContext(), postList,
                ((MainActivity)getActivity()).userClickListener);
        rvUserFeed.setAdapter(adapter);
        rvUserFeed.setLayoutManager(new LinearLayoutManager(getContext()));
        rvUserFeed.addItemDecoration(new DividerItemDecoration(rvUserFeed.getContext(), DividerItemDecoration.VERTICAL));
        getUserPosts(user);
    }

    public void getUserPosts(ParseUser user) {
        ParseQuery<Post> query = new ParseQuery<>(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, user);
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground((posts, e) -> {
            if (e != null) {
                Log.e("_AF", "parse query failed: " + e.getMessage(), e);
            }
            else {
                Log.d("_AF", "allUserPosts: " + posts.toString());
                for (Post p : posts)
                    Log.d("_AF", "getUserPosts: " + p.toString());
                postList.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }

}
