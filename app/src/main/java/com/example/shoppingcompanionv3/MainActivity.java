package com.example.shoppingcompanionv3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
{
    private static final int PICK_IMAGE_REQUEST = 1; // Constant used to identify image request

    private Button mButtonChooseImage; // For image chooser button
    private Button mButtonUpload; // For uploading button
    private TextView mTextViewShowUploads; // Text view (which also works as button)
    private EditText mEditTextFileName; // For editing image name
    private android.widget.ImageView mImageView; // Display image in view
    private ProgressBar mProgressBar;

    private Uri mImageUri; // For User Interface -> allows showing image in image view & uploading in FireBase

    private StorageReference mStorageRef; // For storing data to FireBase
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask; // Testing if GitHub works

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assigning variables
        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mButtonUpload = findViewById(R.id.button_upload);
        mTextViewShowUploads = findViewById(R.id.text_view_show_uploads);
        mEditTextFileName = findViewById(R.id.edit_text_file_name);
        mImageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        // Set OnClick Listeners on buttons and textview (which also functions as an additional button)
        mButtonChooseImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Check if currently uploading something -> make sure user cant spam uploads
                if (mUploadTask != null && mUploadTask.isInProgress())
                {
                    // Don't upload, rather show toast message
                    Toast.makeText(MainActivity.this, "Currently uploading something else", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // Upload file
                    uploadFile(); // To FireBase
                }
            }
        });

        mTextViewShowUploads.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Opens Activity_Images , which displays all folders as Image_Items
                openImagesActivity();
            }
        });
    }

    private void openFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*"); // Only see images in file chooser
        intent.setAction(Intent.ACTION_GET_CONTENT); // Access intent class and take a constant of it
        startActivityForResult(intent, PICK_IMAGE_REQUEST); // Parse in pick image request
    }

    // Called when we pick our file / image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        final ImageView imageViewThing = findViewById(R.id.image_view);

        super.onActivityResult(requestCode, resultCode, data);

        // If this is the result we want to handle right now, and the user actually picked something
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
        && data != null && data.getData() != null)
        {
            // Check for image request, if image was successfully picked, return something back
            mImageUri = data.getData(); // Now Uri contains image UI, will be used later for sending to FireBase
            Picasso.get().load(mImageUri).into(mImageView); // For loading Image View
        }
    }

    private String getFileExtension(Uri uri) // Returns the extension of the file -> e.g. for Jpeg images it will be jpg
    {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile()
    {
        if (mImageUri != null) // Check that an image has actually been picked
        {
            // Give every image a unique name -> uses time passing to create unique number id
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
            + "." + getFileExtension(mImageUri)); // Creates a big number .jpg
            //e.g. "uploads/678237647892.jpg"

            mUploadTask = fileReference.putFile(mImageUri) // mUploadTask is new -> used to check if upload currently running
                    // ^^^ (stops user from spamming multiple uploads too quickly)

                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                    {
                        // When upload is successful
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            // Create a delayed progress bar that gives user the idea their image is being uploaded
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500); // Delay of progress bar reset for a half second (shows 100% for 0.5 secs)

                            /*
                            Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),
                                    taskSnapshot.getMetadata().getReference().getDownloadUrl().toString()); // Name and image url parameters

                            // Make entry in database
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload); // Take unique id and set data to uploadfile (which contains upload name and url)*/

                            Toast.makeText(MainActivity.this, "Upload success", Toast.LENGTH_LONG).show();
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult();
                            Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),downloadUrl.toString());

                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener()
                    {
                        // When upload is failed
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            // Show error as toast message
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>()
                    {
                        // When upload is progressing
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot)
                        {
                            // Update progressbar with current progression by extracting from taskSnapshot
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress); // Cast to int otherwise not accepted
                        }
                    });
        }
        else
        {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void openImagesActivity() // On clicking ShowUploads button, displays all folders in Images_View class
    {
        Intent intent = new Intent(this, ImageViewHolder.class);
        startActivity(intent);
    }
}