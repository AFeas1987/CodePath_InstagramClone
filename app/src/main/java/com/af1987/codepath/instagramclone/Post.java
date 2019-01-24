package com.af1987.codepath.instagramclone;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {


    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";

    public String getDescription() {return getString(KEY_DESCRIPTION);}
    public ParseUser getUser() {return getParseUser(KEY_USER);}
    public ParseFile getImage() {return getParseFile(KEY_IMAGE);}


    public void setDescription(String desc) {put(KEY_DESCRIPTION, desc);}
    public void setImage(ParseFile img) {put(KEY_IMAGE, img);}
    public void setUser(ParseUser user) {put(KEY_USER, user);}
}
