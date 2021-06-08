package com.example.shoppingcompanionv3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ContentsActivity extends AppCompatActivity
{
    FirebaseDatabase database = FirebaseDatabase.getInstance(); // FireBase Reference
    //DatabaseReference myRef = database.getReference("message");
    private DatabaseReference myRef;

    private ImageView mImageFolder;
    private TextView mTextFolder;
    int index = 0;

    Button push;

    List<String> contentList; // For FireBase list of contents
    ArrayAdapter adapter;

    Contents contents; // For contents (and their variables) in the list
    ListView contentsListView; // For list

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents);

        String value1 = getIntent().getStringExtra("Value1"); // URL passed from ImageViewHolder
        String value2 = getIntent().getStringExtra("Value2"); // Name passed from ImageViewHolder
        String value3 = getIntent().getStringExtra("Value3"); // Name passed from ImageViewHolder
        Toast.makeText(ContentsActivity.this,  "Name: " + value2 + "\nKey: " + value3 + "\nLink: " + value1, Toast.LENGTH_SHORT).show();

        myRef = FirebaseDatabase.getInstance().getReference("uploads");

        // Declarations
        mImageFolder = findViewById(R.id.image_view_contents);
        mTextFolder = findViewById(R.id.text_view_contents);
        contentsListView = findViewById(R.id.lv_contents);

        push = findViewById(R.id.btn_push);

        // Set image and text for folder header
        mTextFolder.setText(value2);
        Picasso.get().load(value1).placeholder(R.mipmap.ic_launcher) // Mipmap creates default placeholder image while real images load
                .fit().centerCrop().into(mImageFolder);

        myRef.child(value3).child("contents").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                int i = 0;

                for (DataSnapshot contentsFromFirebase : snapshot.getChildren())
                {
                    i++;
                    index = i;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        // ---------------------------------------------------------------------------------------------------------------------------------------//
        // For PUSH button
        // Open the AddItemActivity.java class
        push.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), AddItemActivity.class);
                i.putExtra("Value1", value1); // Send through the URL for the image we want to display
                i.putExtra("Value2", value2); // Send through the name for the image we want to display
                i.putExtra("Value3", value3);

                startActivity(i);
            }
        });

        // ---------------------------------------------------------------------------------------------------------------------------------------//
        // Fills list with contents for this folder
        // For finding and assigning content values. Check Contents.java class for more info
        myRef.child(value3).child("contents").addValueEventListener(new ValueEventListener() { // Was prev just myRef.addValue... etc
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contents = new Contents();
                contentList = new ArrayList<String>();

                for (DataSnapshot contentsFromFirebase : snapshot.getChildren()) {
                    // For each child, find the values and map them to the Contents class...
                    // ...and then store them in contents (Instantiation of Contents)
                    contents = contentsFromFirebase.getValue(Contents.class);
                    contentList.add(contents.ToString());
                }

                // Pass in current context, layout, and orderList
                adapter = new ArrayAdapter(ContentsActivity.this, android.R.layout.simple_list_item_1, contentList);
                contentsListView.setAdapter(adapter); // Takes all the data and displays it into the list
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(ContentsActivity.this, error.getMessage(), Toast.LENGTH_SHORT);
            }
        });
        // ---------------------------------------------------------------------------------------------------------------------------------------//
    }
}