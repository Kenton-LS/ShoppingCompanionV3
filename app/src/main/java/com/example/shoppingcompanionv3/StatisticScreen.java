package com.example.shoppingcompanionv3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatisticScreen extends AppCompatActivity
{
    //-----Firebase refs as usual---------//
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    //-----------------------------------//

    Button btnBarChart;
    Button btnPieChart;
    Button btnRadarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statisticscreen);

        //-----References as usual-------//
        String folderImageUrl = getIntent().getStringExtra("FolderImageUrl");
        String folderName = getIntent().getStringExtra("FolderName");
        String folderFirebaseKey = getIntent().getStringExtra("FolderFirebaseKey");
        String userFirebaseID = getIntent().getStringExtra("UserFirebaseID");
        Toast.makeText(StatisticScreen.this,  "Name: " + folderName + "\nKey: " + folderFirebaseKey + "\nLink: " + folderImageUrl, Toast.LENGTH_SHORT).show();
        myRef = FirebaseDatabase.getInstance().getReference(userFirebaseID + "/uploads");
        //------------------------------//

        btnBarChart = findViewById(R.id.ButtonBarChart);
        btnPieChart = findViewById(R.id.ButtonPieChart);
        btnRadarChart = findViewById(R.id.ButtonRadarChart);

        btnBarChart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), ChartBarScreen.class));
            }
        });

        btnPieChart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), ChartPieScreen.class));
            }
        });

        btnRadarChart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), ChartRadarScreen.class));
            }
        });
    }
}