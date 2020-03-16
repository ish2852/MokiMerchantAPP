package com.example.orderspot_merchant.service;

import android.graphics.Color;
import android.util.Log;

import com.example.orderspot_merchant.Util.Util;
import com.example.orderspot_merchant.domain.SalesHistoryVO;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChartService {
    private ArrayList<Object> VoDatas;
    private List<ILineDataSet> ILineDataSet;

    public ChartService() {
    }

    public void lineChartDrawingByLineChartAndJsonString(LineChart lineChart, String jsonData) {

        makeVoDataListByJsonData(jsonData);

        if(VoDatas != null)
            makeILineDataSetList();

        drawLineChartByLineChart(lineChart);
    }

    public void makeVoDataListByJsonData(String jsonData) {
        VoDatas = new ArrayList<>();

        salesHistoryVODataList(jsonData);
    }

    public void salesHistoryVODataList(String jsonData){
        try {
            SalesHistoryVO salesHistoryVO = new SalesHistoryVO();
            JSONArray jsonArray = new JSONArray(jsonData);
            JSONObject jsonObject = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                salesHistoryVO.addDate(jsonObject.getString("DATE"));
                salesHistoryVO.addSales(Integer.parseInt(jsonObject.getString("SALES")));

            }
            VoDatas.add(salesHistoryVO);
        } catch (Exception e) {
            Log.e("chartService", e.toString());
            VoDatas = null;
            makeEmptyVoData();
        }
    }

    public void makeEmptyVoData(){
        ILineDataSet = new ArrayList<>();
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 0));
        addLineDateSet(entries, "기록이 없습니다.");
    }

    public void makeILineDataSetList() {
        ILineDataSet = new ArrayList<>();
        List<Entry> entries = null;
        SalesHistoryVO salesHistoryVO = (SalesHistoryVO) VoDatas.get(0);
        entries = makeEntryList(salesHistoryVO.getSales(), salesHistoryVO.getDate());
        addLineDateSet(entries, "매출 금액");
    }


    public List<Entry> makeEntryList(ArrayList<Integer> valueList, ArrayList<String> dateList) {

        List<Entry> entries = new ArrayList<>();

        String date = null;
        int count = 0;
        for (int i = 0; i < dateList.size(); i++) {
            date = dateList.get(i);
            date = date.substring(date.length() - 2);
            count = valueList.get(i);
            entries.add(new Entry(Integer.parseInt(date), count));
        }

        return entries;
    }

    public void addLineDateSet(List<Entry> entries, String labelName) {
        int RColor = Util.getRandomInt(256);
        int GColor = Util.getRandomInt(256);
        int BColor = Util.getRandomInt(256);

        LineDataSet lineDataSet = new LineDataSet(entries, labelName);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        lineDataSet.setLineWidth(2);    // 선굵기
        lineDataSet.setCircleRadius(6); //곡률
        lineDataSet.setCircleColor(Color.argb(255, RColor, GColor, BColor));
        lineDataSet.setCircleColorHole(Color.argb(255, RColor, GColor, BColor));
        lineDataSet.setColor(Color.argb(255, RColor, GColor, BColor));
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setDrawValues(false);


        ILineDataSet.add(lineDataSet);
    }


    public void drawLineChartByLineChart(LineChart lineChart) {
        LineData data = new LineData(ILineDataSet);

        lineChart.setData(data);
        lineChart.invalidate();

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.enableGridDashedLine(8, 24, 0);

        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1.0f);
        xAxis.setLabelCount(15);

        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);

        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        Description description = new Description();
        description.setText("");

        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription(description);
        lineChart.animateY(2000, Easing.EasingOption.EaseInCubic);
        lineChart.invalidate();
    }
}
