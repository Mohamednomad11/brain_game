package com.nomad.mybrainmemory;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.google.firebase.FirebaseApp;
import com.nomad.mybrainmemory.game.PopulateScore;
import com.nomad.mybrainmemory.game.ScoreDB;
import com.nomad.mybrainmemory.jigsawpuzzle.adapter.StorePreference;
import com.nomad.mybrainmemory.model.ScoreModel;
import com.nomad.mybrainmemory.play.CongratsScreenActivity;
import com.nomad.mybrainmemory.util.ScoreModelComparator;
import com.nomad.mybrainmemory.util.StaticConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;
public class PerformanceReport extends AppCompatActivity {


    ImageView backBtn;

    ScoreModel scoreModel;

    TextView tvAccuracy, tvReactionTime, tvTotalTime;
    HalfGauge halfGauge;

    private LineChart chart;

    List<ScoreModel> scoreModelList =  new ArrayList<>();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.performance);

        Intent intent = getIntent();

        scoreModel = intent.getParcelableExtra(StaticConstants.KEY_SCORE_REPORT);


        backBtn = findViewById(R.id.back_btn);
        tvAccuracy = findViewById(R.id.tvAccuracy);
        tvReactionTime = findViewById(R.id.tvReactionTime);
        tvTotalTime = findViewById(R.id.tvTotalTime);

        halfGauge = findViewById(R.id.halfGauge);

        Log.e("PerformanceReport", scoreModel.toString());

        int percentage = (int)(scoreModel.getAccuracy() * 100);

        String totalTimeFormatted = convertSecondsToMinuteSecond(Long.valueOf(scoreModel.getTime()));
        String reactionTimeFormatted = formatMillisecondsToSeconds(scoreModel.getAvgReactionTime());


        tvAccuracy.setText(String.format("%d%%", percentage));
        tvTotalTime.setText(totalTimeFormatted);
        tvReactionTime.setText(reactionTimeFormatted);

        halfGauge.enableAnimation(true);
        halfGauge.setMinValue(0.0f);
        halfGauge.setFitsSystemWindows(true);
        halfGauge.setMaxValue(scoreModel.getScore());
        halfGauge.setValue(scoreModel.getScore());


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(PerformanceReport.this, DifficultyLevelScreen.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                startActivity(i);
                finish();
            }
        });


        // setting chart


        {   // // Chart Style // //
            chart = findViewById(R.id.chart1);

            // background color
            chart.setBackgroundColor(getColor(R.color.light_pink));

            // disable description text
            chart.getDescription().setEnabled(false);

            // enable touch gestures
            chart.setTouchEnabled(true);

            // set listeners
//            chart.setOnChartValueSelectedListener(this);
            chart.setDrawGridBackground(false);

            // create marker to display box when values are selected
//            MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

            // Set the marker to the chart
//            mv.setChartView(chart);
//            chart.setMarker(mv);

            // enable scaling and dragging
            chart.setDragEnabled(true);
            chart.setScaleEnabled(true);
            // chart.setScaleXEnabled(true);
            // chart.setScaleYEnabled(true);

            // force pinch zoom along both axis
            chart.setPinchZoom(true);
        }

        XAxis xAxis;
        {   // // X-Axis Style // //
            xAxis = chart.getXAxis();

            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f);
        }

        YAxis yAxis;
        {   // // Y-Axis Style // //
            yAxis = chart.getAxisLeft();

            // disable dual axis (only use LEFT axis)
            chart.getAxisRight().setEnabled(false);

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f);

            // axis range
            yAxis.setAxisMaximum(320f);
            yAxis.setAxisMinimum(0f);
        }


        {   // // Create Limit Lines // //
            LimitLine llXAxis = new LimitLine(9f, "Index 10");
            llXAxis.setLineWidth(4f);
            llXAxis.enableDashedLine(10f, 10f, 0f);
            llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
            llXAxis.setTextSize(10f);
//            llXAxis.setTypeface(tfRegular);

//            LimitLine ll1 = new LimitLine(300f, "Upper Limit");
//            ll1.setLineWidth(4f);
//            ll1.enableDashedLine(10f, 10f, 0f);
//            ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
//            ll1.setTextSize(10f);
////            ll1.setTypeface(tfRegular);
//
//            LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
//            ll2.setLineWidth(4f);
//            ll2.enableDashedLine(10f, 10f, 0f);
//            ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//            ll2.setTextSize(10f);
//            ll2.setTypeface(tfRegular);

            // draw limit lines behind data instead of on top
            yAxis.setDrawLimitLinesBehindData(true);
            xAxis.setDrawLimitLinesBehindData(true);

            // add limit lines
//            yAxis.addLimitLine(ll1);
//            yAxis.addLimitLine(ll2);
            //xAxis.addLimitLine(llXAxis);


            // add data

            StorePreference storePreference = new StorePreference(this);

            String userType =  storePreference.getStringValue(StaticConstants.KEY_USER_TYPE);

            if(userType.equals(StaticConstants.VAL_USER_TYPE_USER)){

                PopulateScore populateScore = new PopulateScore(this);

                scoreModelList = populateScore.populateScore();

            }else{
                scoreModelList = StaticConstants.userScoreMap.getOrDefault(scoreModel.getUid(),null);
            }






            int count = 0;
            if(scoreModelList != null){
                count = scoreModelList.size();
                Collections.sort(scoreModelList, new ScoreModelComparator());
            }



            setData(count, 270);

            // draw points over time
            chart.animateX(1500);

            // get the legend (only possible after setting data)
            Legend l = chart.getLegend();

            // draw legend entries as lines
            l.setForm(Legend.LegendForm.LINE);
        }
    }


    private void setData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < count; i++) {
                ScoreModel scoreModel1 = scoreModelList.get(i);
//            float val = (float) (Math.random() * range) - 30;
            float val = (float) scoreModel1.getScore();
            Log.e("Performance Report"," score --- " + val);
            values.add(new Entry(i, val, getResources().getDrawable(R.drawable.star)));
        }

        LineDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setDrawIcons(false);

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set1.setColor(Color.WHITE);
            set1.setCircleColor(Color.WHITE);

            // line thickness and point size
            set1.setLineWidth(1f);
            set1.setCircleRadius(5f);


            // draw points as solid circles
            set1.setDrawCircleHole(false);

            // customize legend entry
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            // text size of values
            set1.setValueTextSize(9f);

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            chart.setData(data);
        }
    }

    public static String convertSecondsToMinuteSecond(long totalSeconds) {
        int minutes = (int) (totalSeconds / 60);
        int seconds = (int) (totalSeconds % 60);

        return String.format("%02dm:%02ds", minutes, seconds);
    }

    public static String formatMillisecondsToSeconds(long milliseconds) {
        double seconds = (double) milliseconds / 1000.0;
        return String.format("%.1f sec", seconds);
    }


}
