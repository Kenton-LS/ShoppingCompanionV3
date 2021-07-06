package com.example.shoppingcompanionv3;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class Chart_Pie_Screen extends AppCompatActivity
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
    //----------------------------------------------------------------------------------------//

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_pie);

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

        PieChart pieChart = findViewById(R.id.pieChart);

        ArrayList<PieEntry> visitors = new ArrayList<PieEntry>();
        visitors.add(new PieEntry(counterAlcohol, 2016));
        visitors.add(new PieEntry(counterCarbs, 2017));
        visitors.add(new PieEntry(counterDairy, 2018));
        visitors.add(new PieEntry(counterDrinks, 2019));
        visitors.add(new PieEntry(counterFruit, 2020));
        visitors.add(new PieEntry(counterGrains, 2021));
        visitors.add(new PieEntry(counterOils, 2022));
        visitors.add(new PieEntry(counterProtein, 2023));
        visitors.add(new PieEntry(counterSugary, 2024));
        visitors.add(new PieEntry(counterTakeaway, 2025));
        visitors.add(new PieEntry(counterVeg, 2026));
        visitors.add(new PieEntry(counterOther, 2027));

        PieDataSet pieDataSet = new PieDataSet(visitors, "Categories");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Categories Pie Chart");
        pieChart.animate();
    }
}