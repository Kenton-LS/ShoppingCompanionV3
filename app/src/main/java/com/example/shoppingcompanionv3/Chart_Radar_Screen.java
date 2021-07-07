package com.example.shoppingcompanionv3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class Chart_Radar_Screen extends AppCompatActivity
{
    //------------------------------------References------------------------------------------//
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    String folderImageUrl;
    String folderName;
    String folderFirebaseKey;
    String userFirebaseID;

    ImageView back;

    Integer counterAlcohol = 0, counterCarbs = 0, counterDairy = 0, counterDrinks = 0, counterFruit = 0, counterGrains = 0,
            counterOils = 0, counterProtein = 0, counterSugary = 0, counterTakeaway = 0, counterVeg = 0, counterOther = 0;
    //----------------------------------------------------------------------------------------//

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_radar);

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

        back = findViewById(R.id.img_back3);
        //----------------------------------------------------------------------------------------//

        RadarChart radarChart = findViewById(R.id.radarChart);

        /*ArrayList<RadarEntry> visitorsForFirstWebsite = new ArrayList<>();
        visitorsForFirstWebsite.add(new RadarEntry(counterAlcohol));
        visitorsForFirstWebsite.add(new RadarEntry(counterCarbs));
        visitorsForFirstWebsite.add(new RadarEntry(counterDairy));
        visitorsForFirstWebsite.add(new RadarEntry(counterDrinks));
        visitorsForFirstWebsite.add(new RadarEntry(counterFruit));
        visitorsForFirstWebsite.add(new RadarEntry(counterGrains));

        RadarDataSet radarDataSetForFirstWebsite = new RadarDataSet(visitorsForFirstWebsite, "Categories 1");
        radarDataSetForFirstWebsite.setColor(Color.RED);
        radarDataSetForFirstWebsite.setLineWidth(2f);
        radarDataSetForFirstWebsite.setValueTextColor(Color.RED);
        radarDataSetForFirstWebsite.setValueTextSize(14f);*/

        ArrayList<RadarEntry> visitorsForSecondWebsite = new ArrayList<>();
        visitorsForSecondWebsite.add(new RadarEntry(counterAlcohol));
        visitorsForSecondWebsite.add(new RadarEntry(counterCarbs));
        visitorsForSecondWebsite.add(new RadarEntry(counterDairy));
        visitorsForSecondWebsite.add(new RadarEntry(counterDrinks));
        visitorsForSecondWebsite.add(new RadarEntry(counterFruit));
        visitorsForSecondWebsite.add(new RadarEntry(counterGrains));

        visitorsForSecondWebsite.add(new RadarEntry(counterOils));
        visitorsForSecondWebsite.add(new RadarEntry(counterProtein));
        visitorsForSecondWebsite.add(new RadarEntry(counterSugary));
        visitorsForSecondWebsite.add(new RadarEntry(counterTakeaway));
        visitorsForSecondWebsite.add(new RadarEntry(counterVeg));
        visitorsForSecondWebsite.add(new RadarEntry(counterOther));

        RadarDataSet radarDataSetForSecondWebsite = new RadarDataSet(visitorsForSecondWebsite, "Categories");
        radarDataSetForSecondWebsite.setColor(Color.BLUE);
        radarDataSetForSecondWebsite.setLineWidth(2f);
        radarDataSetForSecondWebsite.setColors(ColorTemplate.COLORFUL_COLORS);
        radarDataSetForSecondWebsite.setValueTextSize(14f);

        RadarData radarData = new RadarData();
        //radarData.addDataSet(radarDataSetForFirstWebsite);
        radarData.addDataSet(radarDataSetForSecondWebsite);

        String[] labels = {"Alcohol", "Carbs", "Dairy", "Drinks", "Fruit", "Grains", "Oils", "Protein", "Sugary", "Takeaway", "Veg", "Other"};

        XAxis xAxis = radarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        radarChart.getDescription().setText("Categories Radar Chart");
        radarChart.setData(radarData);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Statistic_Screen.class));
            }
        });
    }
}