package com.af1987.codepath.instagramclone.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.af1987.codepath.instagramclone.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends DialogFragment {


    public DetailFragment() {} // Required empty public constructor


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setShowsDialog(true);
        return inflater.inflate(R.layout.rv_item_post, container, false);
    }



}
