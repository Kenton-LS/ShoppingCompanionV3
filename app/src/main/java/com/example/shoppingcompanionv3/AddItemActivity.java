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

import java.util.List;

public class AddItemActivity extends AppCompatActivity
{
    FirebaseDatabase database = FirebaseDatabase.getInstance(); // FireBase Reference
    //DatabaseReference myRef = database.getReference("message");
    private DatabaseReference myRef;

    private ImageView mImageFolder;
    private TextView mTextFolder;
    EditText itemName, itemQty, itemDate, itemDesc; // For adding a new item -> the name and quantity
    int index = 0; // For determining number of items in FireBase

    Button push, back;
    String enteredItemName, enteredItemQty, enteredItemDate, enteredItemDesc;

    Contents contents; // For contents (and their variables) in the list

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        String value1 = getIntent().getStringExtra("Value1"); // URL passed from ImageViewHolder
        String value2 = getIntent().getStringExtra("Value2"); // Name passed from ImageViewHolder
        String value3 = getIntent().getStringExtra("Value3"); // Key passed from ImageViewHolder
        //int value4 = getIntent().getIntExtra("Value4", 30); // Goal passed from ImageViewHolder
        String valueUID = getIntent().getStringExtra("ValueUID"); // User ID

        myRef = FirebaseDatabase.getInstance().getReference(valueUID + "/uploads");

        // Declarations
        mImageFolder = findViewById(R.id.image_view_contents4);
        mTextFolder = findViewById(R.id.text_view_contents6);

        itemName = findViewById(R.id.et_itemName);
        itemQty = findViewById(R.id.et_itemQty);
        itemDate = findViewById(R.id.et_itemDate);
        itemDesc = findViewById(R.id.et_itemDesc);
        push = findViewById(R.id.btn_push);
        back = findViewById(R.id.btn_back);

        // Set image and text for folder header
        mTextFolder.setText(value2);
        Picasso.get().load(value1).placeholder(R.mipmap.ic_launcher) // Mipmap creates default placeholder image while real images load
                .fit().centerCrop().into(mImageFolder);

        // ---------------------------------------------------------------------------------------------------------------------------------------//
        // For PUSH button

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

        push.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                enteredItemName = itemName.getText().toString().trim();
                enteredItemQty = itemQty.getText().toString().trim();
                enteredItemDate = itemDate.getText().toString().trim();
                enteredItemDesc = itemDesc.getText().toString().trim();

                contents = new Contents(enteredItemName, enteredItemQty, enteredItemDate, enteredItemDesc);
                //myRef.child(String.valueOf(i)).setValue(contents); OLD

                Toast.makeText(AddItemActivity.this, "COUNT " + index, Toast.LENGTH_SHORT).show();
                myRef.child(value3).child("contents").child(String.valueOf(index)).setValue(contents);

                itemName.setText(""); itemQty.setText(""); itemDate.setText(""); itemDesc.setText("");
            }
        });

        back.setOnClickListener(new View.OnClickListener() // Go back to ContentsActivity.java
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), ContentsActivity.class);
                i.putExtra("Value1", value1); // Send through the URL for the image we want to display
                i.putExtra("Value2", value2); // Send through the name for the image we want to display
                i.putExtra("Value3", value3); // Key
                //i.putExtra("Value4", value4); // Goal
                i.putExtra("ValueUID", valueUID); // Return this user's ID

                startActivity(i);
            }
        });
    }
}