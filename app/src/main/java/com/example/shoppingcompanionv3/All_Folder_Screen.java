package com.example.shoppingcompanionv3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class All_Folder_Screen extends AppCompatActivity implements Image_Adapter.OnItemClickListener // Use interface created for options
{
    private RecyclerView mRecyclerView;
    private Image_Adapter mAdapter;

    private ProgressBar mProgressCircle; // Set default images before loading actual images

    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;

    private FirebaseStorage mStorage; // Used to get reference to images in FireBase storage
    private ValueEventListener mDBListener;

    Button addNewFolder;

    String userFirebaseID; // For parsing the current user's ID into the firebase to make sure we only retrieve THIS user's data

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_folder_screen);

        userFirebaseID = getIntent().getStringExtra("UserFirebaseID"); // Get the current user's ID

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true); // Increase performance of recycler view
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        addNewFolder = findViewById(R.id.btn_addNewFolder);

        mProgressCircle = findViewById(R.id.progress_circle); // For loading default images

        mUploads = new ArrayList<>(); // Initialize uploads as arraylist

        mAdapter = new Image_Adapter(All_Folder_Screen.this, mUploads);
        mRecyclerView.setAdapter(mAdapter); // Where the MAGIC happens
        mAdapter.setOnItemClickListener(All_Folder_Screen.this); // Method to parse the listener

        mStorage = FirebaseStorage.getInstance();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference(userFirebaseID + "/uploads");

        // Now need to get data out of uploads
        //mDatabaseRef.addValueEventListener(new ValueEventListener() - OLD: accidentally could create duplicate Database Refs
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                mUploads.clear(); // Remove duplicate uploads (error was happening where could duplicate upload attempts)

                // Get data out of node via snapshot
                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setKey(postSnapshot.getKey()); // Saves unique key to upload item so we can correctly delete it if need be
                    mUploads.add(upload); // Fills uploads list with upload nodes data
                }

                mAdapter.notifyDataSetChanged(); // Updates RecyclerView that data has changed
                mProgressCircle.setVisibility(View.INVISIBLE); // Hide default image loader
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                // Call if error -> eg no permission to read data from DataBase
                Toast.makeText(All_Folder_Screen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE); // Hide default image loader
            }
        });

        // AddNewFolder button on click
        addNewFolder.setOnClickListener(new View.OnClickListener() // Go back to ContentsActivity.java
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), Add_Folder_Screen.class);
                i.putExtra("UserFirebaseID", userFirebaseID); // Return this user's ID
                startActivity(i);
            }
        });
    }

    // For managing Adapter Clicks
    @Override
    public void onItemClick(int position)
    {
        Toast.makeText(this, "Open click at position: " + position, Toast.LENGTH_SHORT).show();

        // Start of Open Folder -> when clicking on image
        Upload selectedItem = mUploads.get(position); // Get upload item at click position
        String selectedKey = selectedItem.getKey();

        Intent i = new Intent(getApplicationContext(), In_Folder_Screen.class);
        i.putExtra("FolderImageUrl", selectedItem.getImageUrl()); // Send through the URL for the image we want to display
        i.putExtra("FolderName", selectedItem.getName()); // Send through the name for the image we want to display
        i.putExtra("FolderFirebaseKey", selectedItem.getKey());
        //i.putExtra("Value4", selectedItem.getGoal());
        i.putExtra("UserFirebaseID", userFirebaseID);

        startActivity(i);
        // End of Open Folder
    }

    @Override
    public void onWhatEverClick(int position)
    {
        Toast.makeText(this, "Statistics click at position: " + position, Toast.LENGTH_SHORT).show();

        // Open Stats for this folder //
        Upload selectedItem = mUploads.get(position);
        String selectedKey = selectedItem.getKey();

        Intent i = new Intent(getApplicationContext(), Statistic_Screen.class);
        i.putExtra("FolderImageUrl", selectedItem.getImageUrl());
        i.putExtra("FolderName", selectedItem.getName());
        i.putExtra("FolderFirebaseKey", selectedItem.getKey());
        i.putExtra("UserFirebaseID", userFirebaseID);

        startActivity(i);
        //-----------//
    }

    @Override
    public void onDeleteClick(int position)
    {
        Toast.makeText(this, "Delete click at position: " + position, Toast.LENGTH_SHORT).show();
        Upload selectedItem = mUploads.get(position); // Get upload item at click position
        String selectedKey = selectedItem.getKey();

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void aVoid)
            {
                // Only delete item from database if it was successfully deleted from storage
                // Otherwise there could be items in FireBase that don't exist in Android Storage
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(All_Folder_Screen.this, "Folder deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // MDBListener duplicates each time an image is made. This regulates the listener, ensuring only 1 exists at all times
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
}