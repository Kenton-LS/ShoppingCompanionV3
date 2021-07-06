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
    //------------------------------------References------------------------------------------//
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    String folderImageUrl;
    String folderName;
    String folderFirebaseKey;
    String userFirebaseID;
    //----------------------------------------------------------------------------------------//

    Button btnBarChart;
    Button btnPieChart;
    Button btnRadarChart;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statisticscreen);

        //------------------------------------References------------------------------------------//
        folderImageUrl = getIntent().getStringExtra("FolderImageUrl");
        folderName = getIntent().getStringExtra("FolderName");
        folderFirebaseKey = getIntent().getStringExtra("FolderFirebaseKey");
        userFirebaseID = getIntent().getStringExtra("UserFirebaseID");
        myRef = FirebaseDatabase.getInstance().getReference(userFirebaseID + "/uploads");
        //----------------------------------------------------------------------------------------//

        btnBarChart = findViewById(R.id.ButtonBarChart);
        btnPieChart = findViewById(R.id.ButtonPieChart);
        btnRadarChart = findViewById(R.id.ButtonRadarChart);
        btnBack = findViewById(R.id.ButtonBack);

        btnBarChart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //startActivity(new Intent(getApplicationContext(), ChartBarScreen.class));
                Intent i = new Intent(getApplicationContext(), ChartBarScreen.class);
                sendDetails(i);
                startActivity(i);
            }
        });

        btnPieChart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //startActivity(new Intent(getApplicationContext(), ChartPieScreen.class));
                Intent i = new Intent(getApplicationContext(), ChartPieScreen.class);
                sendDetails(i);
                startActivity(i);
            }
        });

        btnRadarChart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //startActivity(new Intent(getApplicationContext(), ChartRadarScreen.class));
                Intent i = new Intent(getApplicationContext(), ChartRadarScreen.class);
                sendDetails(i);
                startActivity(i);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //startActivity(new Intent(getApplicationContext(), ChartRadarScreen.class));
                Intent i = new Intent(getApplicationContext(), AllFolderScreen.class);
                sendDetails(i);
                startActivity(i);
            }
        });
    }

    protected void sendDetails(Intent i)
    {
        i.putExtra("FolderImageUrl", folderImageUrl);
        i.putExtra("FolderName", folderName);
        i.putExtra("FolderFirebaseKey", folderFirebaseKey);
        i.putExtra("UserFirebaseID", userFirebaseID);
    }
}