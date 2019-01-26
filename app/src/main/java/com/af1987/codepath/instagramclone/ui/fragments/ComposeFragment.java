package com.af1987.codepath.instagramclone.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.af1987.codepath.instagramclone.Post;
import com.af1987.codepath.instagramclone.R;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.File;

public class ComposeFragment extends Fragment {

    private EditText etDescription;
    private Button btnPicture;
    private Button btnSubmit;
    private ImageView ivImage;
    private final String APP_TAG = "_AF";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    private File photoFile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compose, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etDescription = view.findViewById(R.id.etDescription);
        btnPicture = view.findViewById(R.id.btnPicture);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        ivImage = view.findViewById(R.id.ivImage);

        btnPicture.setOnClickListener(v -> launchCamera());

        btnSubmit.setOnClickListener(v -> {
            if (photoFile == null || ivImage.getDrawable() == null) {
                Log.e(APP_TAG, "On Submit:  No valid image found");
            }
            else {
                String desc = etDescription.getText().toString();
                ParseUser user = ParseUser.getCurrentUser();
                submitPost(desc, user, new ParseFile(photoFile));
            }
        });
    }

    private void submitPost(String desc, ParseUser user, ParseFile image) {
        Post post = new Post();
        post.setDescription(desc);
        post.setUser(user);
        post.setImage(image);
        post.saveInBackground(e -> {
            if (e != null) {
                Log.e(APP_TAG, "row creation failed: " + e.getMessage(), e);
            }
            else {
                clearActivity();
                Snackbar.make(getView(), "POST SUBMITTED SUCCESSFULLY", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void clearActivity() {
        etDescription.setText("");
        ivImage.setImageResource(0);
        ivImage.setVisibility(View.GONE);
    }

    private void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename

        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                ivImage.setVisibility(View.VISIBLE);

                ivImage.setImageBitmap(takenImage);

            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
