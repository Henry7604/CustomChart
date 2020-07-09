package com.henry.chart;

/**
 *
 * @author Henry
 */
public class ChartModel {
    private int data;

    public ChartModel(int score) {
        this.data = score;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
