package com.yh.parkingpartner.model;

import java.io.Serializable;
import java.util.List;

public class DataListRes implements Serializable {

    private String result;
    private int count;
    private List<Data> items;

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
}
