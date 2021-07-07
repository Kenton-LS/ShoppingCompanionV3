package com.example.shoppingcompanionv3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Statistic_Screen extends AppCompatActivity
{
    //------------------------------------References------------------------------------------//
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private DatabaseReference myRef1;
    String folderImageUrl;
    String folderName;
    String folderFirebaseKey;
    String userFirebaseID;

    Integer counterAlcohol = 0, counterCarbs = 0, counterDairy = 0, counterDrinks = 0, counterFruit = 0, counterGrains = 0,
            counterOils = 0, counterProtein = 0, counterSugary = 0, counterTakeaway = 0, counterVeg = 0, counterOther = 0;
    //----------------------------------------------------------------------------------------//

    Button btnBarChart;
    Button btnPieChart;
    Button btnRadarChart;
    ImageView btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_screen);

        //------------------------------------References------------------------------------------//
        folderImageUrl = getIntent().getStringExtra("FolderImageUrl");
        folderName = getIntent().getStringExtra("FolderName");
        folderFirebaseKey = getIntent().getStringExtra("FolderFirebaseKey");
        userFirebaseID = getIntent().getStringExtra("UserFirebaseID");
        myRef = FirebaseDatabase.getInstance().getReference(userFirebaseID + "/uploads");
        myRef1 = myRef.child(folderFirebaseKey).child("contents");
        //----------------------------------------------------------------------------------------//

        btnBarChart = findViewById(R.id.ButtonBarChart);
        btnPieChart = findViewById(R.id.ButtonPieChart);
        btnRadarChart = findViewById(R.id.ButtonRadarChart);
        btnBack = findViewById(R.id.img_backBtn);

        btnBarChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), ChartBarScreen.class));
                Intent i = new Intent(getApplicationContext(), Chart_Bar_Screen.class);
                sendDetails(i);
                startActivity(i);
            }
        });

        btnPieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), ChartPieScreen.class));
                Intent i = new Intent(getApplicationContext(), Chart_Pie_Screen.class);
                sendDetails(i);
                startActivity(i);
            }
        });

        btnRadarChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), ChartRadarScreen.class));
                Intent i = new Intent(getApplicationContext(), Chart_Radar_Screen.class);
                sendDetails(i);
                startActivity(i);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), ChartRadarScreen.class));
                Intent i = new Intent(getApplicationContext(), All_Folder_Screen.class);
                sendDetails(i);
                startActivity(i);
            }
        });

        //-------------------------------------------------------------------------------------------------------------------//

        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot zoneSnapshot: dataSnapshot.getChildren()) {
                    String currentTag = zoneSnapshot.child("tag").getValue(String.class);
                    CheckTag(currentTag);
                }
                //Log.d("message" , "nommer van veggies " + counterVeg);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Statistic_Screen.this, "ERROR in StatScreen.java", Toast.LENGTH_SHORT);
            }
        });

        //-------------------------------------------------------------------------------------------------------------------//
    }
    public void CheckTag(String tag)
    {
        //Log.d("message", "tag 1 check " + tag);
        if (tag.equals("Alcohol")) { counterAlcohol++; }
        else if (tag.equals("Carbs")) { counterCarbs++; }
        else if (tag.equals("Dairy")) { counterDairy++; }
        else if (tag.equals("Drinks")) { counterDrinks++; }
        else if (tag.equals("Fruit")) { counterFruit++; }
        else if (tag.equals("Grains")) { counterGrains++; }
        else if (tag.equals("Oils")) { counterOils++; }
        else if (tag.equals("Protein")) { counterProtein++; }
        else if (tag.equals("Sugary")) { counterSugary++; }
        else if (tag.equals("Takeaway")) { counterTakeaway++; }
        else if (tag.equals("Veg")) { counterVeg++; }
        else { counterOther++; }
    }

    protected void sendDetails(Intent i)
    {
        i.putExtra("FolderImageUrl", folderImageUrl);
        i.putExtra("FolderName", folderName);
        i.putExtra("FolderFirebaseKey", folderFirebaseKey);
        i.putExtra("UserFirebaseID", userFirebaseID);

        i.putExtra("counterAlcohol", counterAlcohol);
        i.putExtra("counterCarbs", counterCarbs);
        i.putExtra("counterDairy", counterDairy);
        i.putExtra("counterDrinks", counterDrinks);
        i.putExtra("counterFruit", counterFruit);
        i.putExtra("counterGrains", counterGrains);
        i.putExtra("counterOils", counterOils);
        i.putExtra("counterProtein", counterProtein);
        i.putExtra("counterSugary", counterSugary);
        i.putExtra("counterTakeaway", counterTakeaway);
        i.putExtra("counterVeg", counterVeg);
        i.putExtra("counterOther", counterOther);
    }
}