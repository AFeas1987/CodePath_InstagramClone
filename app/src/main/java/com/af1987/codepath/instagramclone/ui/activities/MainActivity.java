package com.af1987.codepath.instagramclone.ui.activities;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.af1987.codepath.instagramclone.R;
import com.af1987.codepath.instagramclone.ui.fragments.ComposeFragment;
import com.af1987.codepath.instagramclone.ui.fragments.DetailFragment;
import com.af1987.codepath.instagramclone.ui.fragments.HomeFeedFragment;
import com.af1987.codepath.instagramclone.ui.fragments.UserFragment;
import com.af1987.codepath.instagramclone.ui.recyclerView.PostAdapter;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navView = findViewById(R.id.bottom_navigation);

        final FragmentManager fragMgr = getSupportFragmentManager();

        navView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.action_compose:
                    Toast.makeText(this, "Compose", Toast.LENGTH_LONG).show();
                    fragment = new ComposeFragment();
                    break;
                case R.id.action_home:
                    Toast.makeText(this, "Home", Toast.LENGTH_LONG).show();
                    fragment = new HomeFeedFragment();
                    break;
                case R.id.action_profile:
                    Toast.makeText(this, "Profile", Toast.LENGTH_LONG).show();
                    fragment = UserFragment.newInstance(ParseUser.getCurrentUser());
//                    fragMgr.beginTransaction().add(new DetailFragment(), "DetailFragment").commit();
                    break;
            }
            if (fragment != null) {
                fragMgr.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
            return false;
        });

        navView.setSelectedItemId(R.id.action_home);
    }


    public PostAdapter.UserClickListener userClickListener = user -> {
        Toast.makeText(this, "Received user click: " + user.getUsername(), Toast.LENGTH_LONG).show();
        getSupportFragmentManager();
    };
}
