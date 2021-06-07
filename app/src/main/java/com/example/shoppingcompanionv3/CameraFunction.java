package com.example.shoppingcompanionv3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class CameraFunction extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }

        final Button camButton = findViewById(R.id.cameraButton);
        camButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(imageCapture, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final ImageView imageView = findViewById(R.id.image_view);
        if (resultCode != Activity.RESULT_OK && data != null) {
            return;
        }
        if (requestCode == 100) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            if (isStoragePermissionGranted()) {
                SaveImage(bitmap);
            }
        }
    }

    private void SaveImage(Bitmap finalBitmap) {

        String root =
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).toString(); //Create string for path of save
        File myDir = new File(root + "/saved_images"); //Create the file with root path
        Log.d(root, "Save Location for images."); //Log the call
        if (!myDir.exists()){
            myDir.mkdirs(); //Make the directory if it doesn't exist
        }
        Random generator = new Random(); // Create a new random generator
        int n = 10000;
        n = generator.nextInt(n); //Name of the image
        String imgName = "Image-" + n + ".jpg"; //Image name is: Image-(random number).jpg
        File file = new File(myDir, imgName);//Making the image with the path and image name
        if (file.exists()) file.delete(); //If it already exists, delete and replace it
        try { // Attempt to output the file and compress it
            final FileOutputStream out = new FileOutputStream(file);
            final BufferedOutputStream bos = new BufferedOutputStream(out, 8192);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos);
            bos.flush();
            bos.close();
            Log.d(bos.toString(), " | File saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) { // Check build of android to see if it automatically grants permissions
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        } else { // Permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }
}
