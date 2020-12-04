package com.example.recipedcode.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.recipedcode.Post;
import com.example.recipedcode.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

public class UploadPostFragment extends Fragment{
    public static final String TAG = "CreatePostFragment";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    private static final int PICK_PHOTO_FOR_AVATAR = 0;
    private EditText etCaption;
    private EditText etDescription;
    private Button btnReuploadImage;
    private ImageView ivPostImage;
    private Button btnSubmit;
    private File photoFile;
    private Button btnCancel;
    public String photoFileName = "photo.jpg";

    public UploadPostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        pickImage();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload_post_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        etCaption = view.findViewById(R.id.etCaption);
        etDescription = view.findViewById(R.id.etDescription);
        btnReuploadImage = view.findViewById(R.id.btnReuploadImage);
        ivPostImage = view.findViewById(R.id.ivPostImage);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnCancel = view.findViewById(R.id.btnCancel);
        super.onViewCreated(view, savedInstanceState);

        btnReuploadImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                pickImage();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new CreateFragment();

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String caption = etCaption.getText().toString();
                if (caption.isEmpty()) {
                    Toast.makeText(getContext(), "Caption cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                String description = etDescription.getText().toString();
                if (description.isEmpty()) {
                    Toast.makeText(getContext(), "Description cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (photoFile == null || ivPostImage.getDrawable() == null) {
                    Toast.makeText(getContext(), "There is no image!", Toast.LENGTH_SHORT).show();
                    return;

                }
                System.out.println("***************setting up parseuser****************");
                System.out.println("***************setting up savingpost****************");
                ParseUser currentUser = ParseUser.getCurrentUser();
                System.out.println("***************called parseuser****************");
                savePost(caption, description, currentUser, photoFile);
                System.out.println("***************called savingpost****************");

                Fragment fragment = new HomeFragment();

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();

            }
        });
    }

    private void savePost(String caption, String description, ParseUser currentUser, File photoFile) {
        System.out.println("********************** about to save attributes into post*********************");
        Post post = new Post();
        System.out.println("********************** created postt*********************");
        post.setCaption(caption);
        post.setDescription(description);
        post.setImage(new ParseFile(photoFile));
        post.setUser(currentUser);
        System.out.println("***************saved all attributes into post****************");
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), "Errors while saving!", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Post save was successful");
                etCaption.setText("");
                etDescription.setText("");
                ivPostImage.setImageResource(0);
            }
        });
    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ivPostImage.setImageBitmap(selectedImage);
                photoFile = getPhotoFileUri(photoFileName);
//                InputStream inputStream = getContext().getContentResolver().openInputStream(data.getData());
//                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
//                ivPostImage.setImageBitmap(takenImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
    }

    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);

    }

}
