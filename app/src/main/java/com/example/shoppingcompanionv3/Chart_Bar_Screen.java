package com.example.shoppingcompanionv3;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Chart_Bar_Screen extends AppCompatActivity
{
    //------------------------------------References------------------------------------------//
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    String folderImageUrl;
    String folderName;
    String folderFirebaseKey;
    String userFirebaseID;

    Integer counterAlcohol = 0, counterCarbs = 0, counterDairy = 0, counterDrinks = 0, counterFruit = 0, counterGrains = 0,
            counterOils = 0, counterProtein = 0, counterSugary = 0, counterTakeaway = 0, counterVeg = 0, counterOther = 0;

    List<String> contentList;
    Contents contents; // For contents (and their variables) in the list
    //----------------------------------------------------------------------------------------//

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_bar);

        //------------------------------------References------------------------------------------//
        folderImageUrl = getIntent().getStringExtra("FolderImageUrl");
        folderName = getIntent().getStringExtra("FolderName");
        folderFirebaseKey = getIntent().getStringExtra("FolderFirebaseKey");
        userFirebaseID = getIntent().getStringExtra("UserFirebaseID");
        myRef = FirebaseDatabase.getInstance().getReference(userFirebaseID + "/uploads");

        counterAlcohol = getIntent().getIntExtra("counterAlcohol", 1);
        counterCarbs = getIntent().getIntExtra("counterCarbs", 1);
        counterDairy = getIntent().getIntExtra("counterDairy", 1);
        counterDrinks = getIntent().getIntExtra("counterDrinks", 1);
        counterFruit = getIntent().getIntExtra("counterFruit", 1);
        counterGrains = getIntent().getIntExtra("counterGrains", 1);
        counterOils = getIntent().getIntExtra("counterOils", 1);
        counterProtein = getIntent().getIntExtra("counterProtein", 1);
        counterSugary = getIntent().getIntExtra("counterSugary", 1);
        counterTakeaway = getIntent().getIntExtra("counterTakeaway", 1);
        counterVeg = getIntent().getIntExtra("counterVeg", 1);
        counterOther = getIntent().getIntExtra("counterOther",1);
        //----------------------------------------------------------------------------------------//

        BarChart barChart = findViewById(R.id.barChart);
        ArrayList<BarEntry> visitors = new ArrayList<BarEntry>();

        visitors.add(new BarEntry(2014, counterAlcohol));
        visitors.add(new BarEntry(2015, counterCarbs));
        visitors.add(new BarEntry(2016, counterDairy));
        visitors.add(new BarEntry(2017, counterDrinks));
        visitors.add(new BarEntry(2018, counterFruit));
        visitors.add(new BarEntry(2019, counterGrains));
        visitors.add(new BarEntry(2020, counterOils));
        visitors.add(new BarEntry(2021, counterProtein));
        visitors.add(new BarEntry(2022, counterSugary));
        visitors.add(new BarEntry(2023, counterTakeaway));
        visitors.add(new BarEntry(2024, counterVeg));
        visitors.add(new BarEntry(2025, counterOther));

        BarDataSet barDataSet = new BarDataSet(visitors, "Categories");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Categories Bar Chart");
        barChart.animateY(2000);
    }
}