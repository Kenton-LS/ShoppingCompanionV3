package com.example.shoppingcompanionv3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
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

    ImageView back;

    Integer counterAlcohol = 0, counterCarbs = 0, counterDairy = 0, counterDrinks = 0, counterFruit = 0, counterGrains = 0,
            counterOils = 0, counterProtein = 0, counterSugary = 0, counterTakeaway = 0, counterVeg = 0, counterOther = 0;

    ArrayList<String> labelName;
    ArrayList<Tag_Data> tagDataArrayList;
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
        back = findViewById(R.id.img_backToSet);
        //----------------------------------------------------------------------------------------//

        BarChart barChart = findViewById(R.id.barChart);
        ArrayList<BarEntry> categories = new ArrayList<BarEntry>();

        labelName  = new ArrayList<>();
        tagDataArrayList = new ArrayList<>();

        fillTagDataArrayList();

        for (int i =0; i < tagDataArrayList.size();i++)
        {
            String tag = tagDataArrayList.get(i).getTag();
            int amount = tagDataArrayList.get(i).getAmount();
            categories.add(new BarEntry(i,amount));
            labelName.add(tag);
        }

        BarDataSet barDataSet = new BarDataSet(categories, "Amount");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        // Extra addition
        Description description = new Description();
        description.setText("Tags");
        barChart.setDescription(description);
        //

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);

        // Extra addition
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labelName));
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setDrawAxisLine(false);
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setLabelCount(labelName.size());
        barChart.getXAxis().setLabelRotationAngle(270);
        //

        barChart.getDescription().setText("Categories Bar Chart");
        barChart.animateY(2000);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Statistic_Screen.class));
            }
        });
    }

    private void fillTagDataArrayList()
    {
        tagDataArrayList.clear();
        tagDataArrayList.add(new Tag_Data("Alcohol",counterAlcohol));
        tagDataArrayList.add(new Tag_Data("Carbs",counterCarbs));
        tagDataArrayList.add(new Tag_Data("Dairy",counterDairy));
        tagDataArrayList.add(new Tag_Data("Drinks",counterDrinks));
        tagDataArrayList.add(new Tag_Data("Fruit",counterFruit));
        tagDataArrayList.add(new Tag_Data("Grains",counterGrains));
        tagDataArrayList.add(new Tag_Data("Oils",counterOils));
        tagDataArrayList.add(new Tag_Data("Protein",counterProtein));
        tagDataArrayList.add(new Tag_Data("Sugary",counterSugary));
        tagDataArrayList.add(new Tag_Data("Takeaway",counterTakeaway));
        tagDataArrayList.add(new Tag_Data("Veg",counterVeg));
        tagDataArrayList.add(new Tag_Data("Other",counterOther));
    }


}