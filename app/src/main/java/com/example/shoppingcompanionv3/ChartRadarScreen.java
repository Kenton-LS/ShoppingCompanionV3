package com.example.shoppingcompanionv3;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class ChartRadarScreen extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chartradar);

        RadarChart radarChart = findViewById(R.id.radarChart);

        ArrayList<RadarEntry> visitorsForFirstWebsite = new ArrayList<>();
        visitorsForFirstWebsite.add(new RadarEntry(420));
        visitorsForFirstWebsite.add(new RadarEntry(475));
        visitorsForFirstWebsite.add(new RadarEntry(500));
        visitorsForFirstWebsite.add(new RadarEntry(660));
        visitorsForFirstWebsite.add(new RadarEntry(550));
        visitorsForFirstWebsite.add(new RadarEntry(630));
        visitorsForFirstWebsite.add(new RadarEntry(470));
        visitorsForFirstWebsite.add(new RadarEntry(560));
        visitorsForFirstWebsite.add(new RadarEntry(700));
        visitorsForFirstWebsite.add(new RadarEntry(410));

        RadarDataSet radarDataSetForFirstWebsite = new RadarDataSet(visitorsForFirstWebsite, "Visitors");
        radarDataSetForFirstWebsite.setColor(Color.RED);
        radarDataSetForFirstWebsite.setLineWidth(2f);
        radarDataSetForFirstWebsite.setValueTextColor(Color.RED);
        radarDataSetForFirstWebsite.setValueTextSize(14f);

        ArrayList<RadarEntry> visitorsForSecondWebsite = new ArrayList<>();
        visitorsForSecondWebsite.add(new RadarEntry(310));
        visitorsForSecondWebsite.add(new RadarEntry(420));
        visitorsForSecondWebsite.add(new RadarEntry(685));
        visitorsForSecondWebsite.add(new RadarEntry(820));
        visitorsForSecondWebsite.add(new RadarEntry(490));
        visitorsForSecondWebsite.add(new RadarEntry(730));
        visitorsForSecondWebsite.add(new RadarEntry(200));
        visitorsForSecondWebsite.add(new RadarEntry(350));
        visitorsForSecondWebsite.add(new RadarEntry(650));
        visitorsForSecondWebsite.add(new RadarEntry(740));

        RadarDataSet radarDataSetForSecondWebsite = new RadarDataSet(visitorsForSecondWebsite, "Website 2");
        radarDataSetForSecondWebsite.setColor(Color.BLUE);
        radarDataSetForSecondWebsite.setLineWidth(2f);
        radarDataSetForSecondWebsite.setValueTextColor(Color.BLUE);
        radarDataSetForSecondWebsite.setValueTextSize(14f);

        RadarData radarData = new RadarData();
        radarData.addDataSet(radarDataSetForFirstWebsite);
        radarData.addDataSet(radarDataSetForSecondWebsite);

        String[] labels = {"2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023"};

        XAxis xAxis = radarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        radarChart.getDescription().setText("Radar Chart Example");
        radarChart.setData(radarData);
    }
}