package com.wicoding.injaz.babycare;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DataActivity extends AppCompatActivity {

    LineChart mChart;
    Spinner mSpinner;
    LineDataSet lineDataSet;
    WebView textDescriptionWeb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        textDescriptionWeb = (WebView) findViewById(R.id.section_label);
        textDescriptionWeb.getSettings().setJavaScriptEnabled(true);
        textDescriptionWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        Bundle b = getIntent().getExtras();
        switch (b.getString("type"))
        {
            case "co2":
                setTitle("Graphics CO2");
                textDescriptionWeb.loadData(getResources().getString(R.string.data_co2), "text/html;charset=utf-8", null);
                break;
            case "heath":
                setTitle("Graphics Heath");
                textDescriptionWeb.loadData(getResources().getString(R.string.data_heath), "text/html;charset=utf-8", null);
                break;
            case "sleep":
                setTitle("Graphics Sleep");
                textDescriptionWeb.loadData(getResources().getString(R.string.data_sleep), "text/html;charset=utf-8", null);
                break;
            case "temp":
                setTitle("Graphics Temperature");
                textDescriptionWeb.loadData(getResources().getString(R.string.data_temp), "text/html;charset=utf-8", null);
                break;
        }

        mChart = (LineChart) findViewById(R.id.chart);
        mChart.getViewPortHandler().setMaximumScaleX(5f);
        mChart.getViewPortHandler().setMaximumScaleY(5f);
        mChart.getXAxis().setAxisMinimum(1f);
        mChart.getAxisRight().setEnabled(false);

        lineDataSet = new LineDataSet(new ArrayList<Entry>(),"DataSet 1");
        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_primary_color);
            lineDataSet.setFillDrawable(drawable);
        }
        else {
            lineDataSet.setFillColor(Color.BLACK);
        }

        // set the line to be drawn like this "- - - - - -"
        lineDataSet.setLineWidth(1.75f);
        lineDataSet.setCircleRadius(5f);
        lineDataSet.setCircleHoleRadius(2.5f);
        lineDataSet.setColor(ContextCompat.getColor(this,R.color.colorAccent));
        lineDataSet.setCircleColor(ContextCompat.getColor(this,R.color.colorAccent));
        lineDataSet.setHighLightColor(ContextCompat.getColor(this,R.color.colorAccent));

        lineDataSet.enableDashedLine(10f, 5f, 0f);
        lineDataSet.enableDashedHighlightLine(10f, 5f, 0f);

        lineDataSet.setDrawFilled(true);
        lineDataSet.setDrawCircleHole(false);

        mSpinner = (Spinner) findViewById(R.id.spinner_timegraph);
        mSpinner.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                showGraphByDate(position,null);
                //mChart.animateXY(2500, 3000);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.timegraph_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);


        showGraphByDate(mSpinner.getSelectedItemPosition(),null);
        //mChart.animateXY(2500, 3000);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void showGraphByDate(int type,List<Date> dateList)
    {
        ArrayList<Entry> yVals = new ArrayList<Entry>();

        switch (type)
        {
            case 0:
                yVals.add(new Entry(1, 37, "first"));
                yVals.add(new Entry(2, 36, "first"));
                yVals.add(new Entry(3, 35, "first"));
                yVals.add(new Entry(4, 36, "first"));
                yVals.add(new Entry(5, 37, "first"));
                yVals.add(new Entry(6, 36, "first"));
                yVals.add(new Entry(7, 37, "first"));
                yVals.add(new Entry(8, 36, "first"));
                yVals.add(new Entry(9, 36, "first"));
                yVals.add(new Entry(10, 36, "first"));
                yVals.add(new Entry(11, 37.2f, "first"));
                yVals.add(new Entry(12, 37.7f, "first"));
                yVals.add(new Entry(13, 36, "first"));
                yVals.add(new Entry(14, 36, "first"));
                yVals.add(new Entry(15, 37, "first"));
                yVals.add(new Entry(16, 36, "first"));
                yVals.add(new Entry(17, 37, "first"));
                yVals.add(new Entry(18, 36, "first"));
                yVals.add(new Entry(19, 37, "first"));
                yVals.add(new Entry(20, 37, "first"));
                break;
            case 1:
                yVals.add(new Entry(2, 33, "second"));
                yVals.add(new Entry(3, 34, "third"));
                break;
            case 2:
                yVals.add(new Entry(2, 33, "second"));
                yVals.add(new Entry(3, 34, "third"));
                yVals.add(new Entry(3, 34, "third"));
                break;
            case 3:
                break;
            default:
                break;
        }

        lineDataSet.setValues(yVals);

        //set1.setFillAlpha(250);

        //set1.setValueTextSize(9f);
        /*
        final ArrayList<String> xLabels = new ArrayList<String>();
        xLabels.add("Jan");
        xLabels.add("Feb");
        xLabels.add("Mar");
        xLabels.add("Apr");
        xLabels.add("May");
        xLabels.add("Jun");

        lineDataSet.setDrawValues(false);
        mChart.getXAxis().setValueFormatter(new IAxisValueFormatter(){
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLabels.get((int)value);
            }

        });
         */
        //set1.setFillColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(lineDataSet); // add the datasets
        LineData lineData = new LineData(dataSets);


        //chart.setBackgroundColor(Color.WHITE);
        mChart.setData(lineData);
        mChart.invalidate();
    }
}
