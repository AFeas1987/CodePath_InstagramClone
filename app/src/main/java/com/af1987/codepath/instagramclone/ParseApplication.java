package com.af1987.codepath.instagramclone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Post.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("codepath-instagram-clone") // should correspond to APP_ID env variable
                .clientKey("2YtiTjLZCNQF8YaF")  // set explicitly unless clientKey is explicitly configured on Parse server
                .server("http://codepath-instagram-clone.herokuapp.com/parse/").build());
    }
}
