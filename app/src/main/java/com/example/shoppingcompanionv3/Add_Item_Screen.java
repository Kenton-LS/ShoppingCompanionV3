package com.example.shoppingcompanionv3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Calendar;

public class Add_Item_Screen extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    //------------------------------------References------------------------------------------//
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    String folderImageUrl;
    String folderName;
    String folderFirebaseKey;
    String userFirebaseID;
    String chosenDropdownTag;
    //----------------------------------------------------------------------------------------//

    private ImageView mImageFolder;
    private TextView mTextFolder;
    EditText itemName, itemQty, itemDate, itemDesc; // For adding a new item -> the name and quantity
    int index = 0; // For determining number of items in FireBase
    Boolean checked = true;

    Button push, back;
    String enteredItemName, enteredItemQty, enteredItemDate, enteredItemDesc, enteredDropdownTag;
    Contents contents; // For contents (and their variables) in the list

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_screen);

        //------------------------------------References------------------------------------------//
        folderImageUrl = getIntent().getStringExtra("FolderImageUrl"); // URL passed from ImageViewHolder
        folderName = getIntent().getStringExtra("FolderName"); // Name passed from ImageViewHolder
        folderFirebaseKey = getIntent().getStringExtra("FolderFirebaseKey"); // Key passed from ImageViewHolder
        userFirebaseID = getIntent().getStringExtra("UserFirebaseID"); // User ID
        myRef = FirebaseDatabase.getInstance().getReference(userFirebaseID + "/uploads");
        //----------------------------------------------------------------------------------------//

        // Declarations
        mImageFolder = findViewById(R.id.image_view_contents4);
        mTextFolder = findViewById(R.id.text_view_contents6);

        itemName = findViewById(R.id.et_itemName);
        itemQty = findViewById(R.id.et_itemQty);
        itemDate = findViewById(R.id.et_itemDate);
        itemDesc = findViewById(R.id.et_itemDesc);
        push = findViewById(R.id.btn_push);
        back = findViewById(R.id.btn_back);

        // For dropdown menu
        Spinner spinner = findViewById(R.id.sp_itemTag);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tags, android.R.layout.simple_spinner_item); // ArrayAdapter for dropdown spinner list (POE T3)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // For automatic calender in date acquired textbox
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        // Set image and text for folder header
        mTextFolder.setText(folderName);
        itemDate.setText(currentDate);
        Picasso.get().load(folderImageUrl).placeholder(R.mipmap.ic_launcher) // Mipmap creates default placeholder image while real images load
                .fit().centerCrop().into(mImageFolder);

        // ---------------------------------------------------------------------------------------------------------------------------------------//
        // For PUSH button

        myRef.child(folderFirebaseKey).child("contents").addValueEventListener(new ValueEventListener()
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

        // Save new item to Firebase
        push.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                enteredItemName = itemName.getText().toString().trim();
                enteredItemQty = itemQty.getText().toString().trim();
                enteredItemDate = itemDate.getText().toString().trim();
                enteredItemDesc = itemDesc.getText().toString().trim();
                enteredDropdownTag = chosenDropdownTag;

                contents = new Contents(enteredItemName, enteredItemQty, enteredItemDate, enteredItemDesc, enteredDropdownTag, checked);
                //myRef.child(String.valueOf(i)).setValue(contents); OLD

                Toast.makeText(Add_Item_Screen.this, "COUNT " + index, Toast.LENGTH_SHORT).show();
                myRef.child(folderFirebaseKey).child("contents").child(String.valueOf(index)).setValue(contents);

                itemName.setText(""); itemQty.setText(""); itemDate.setText(currentDate); itemDesc.setText("");
            }
        });

        back.setOnClickListener(new View.OnClickListener() // Go back to ContentsActivity.java
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), In_Folder_Screen.class);
                i.putExtra("FolderImageUrl", folderImageUrl); // Send through the URL for the image we want to display
                i.putExtra("FolderName", folderName); // Send through the name for the image we want to display
                i.putExtra("FolderFirebaseKey", folderFirebaseKey); // Key
                i.putExtra("UserFirebaseID", userFirebaseID); // Return this user's ID

                startActivity(i);
            }
        });
    }

    // Dropdown menu
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
    {
        chosenDropdownTag = adapterView.getItemAtPosition(position).toString(); // Take item at this pos, turn into string, save as text variable
        Toast.makeText(adapterView.getContext(), chosenDropdownTag, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {
    }
}