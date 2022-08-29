package com.yh.parkingpartner.model;

import java.io.Serializable;
import java.util.List;

public class DataListRes implements Serializable {

    private String result;
    private int count;
    private List<Data> items;

    String img_prk;                      //주차사진URL
    String  DetectedText;           //AWS 텍스트감지 결과 텍스트
    double Confidence;              //AWS 텍스트감지 결과 텍스트 확률

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Data> getItems() {
        return items;
    }

    public void setItems(List<Data> items) {
        this.items = items;
    }

    public String getImg_prk() {
        return img_prk;
    }

    public void setImg_prk(String img_prk) {
        this.img_prk = img_prk;
    }

    public String getDetectedText() {
        return DetectedText;
    }

    public void setDetectedText(String detectedText) {
        DetectedText = detectedText;
    }

    public double getConfidence() {
        return Confidence;
    }

    public void setConfidence(double confidence) {
        Confidence = confidence;
    }
}
