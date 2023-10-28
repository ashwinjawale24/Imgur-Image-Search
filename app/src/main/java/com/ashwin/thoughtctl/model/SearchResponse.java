package com.ashwin.thoughtctl.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;



public class SearchResponse {

    @SerializedName("data")
    @Expose
    private List<DataModel> data = null;

    public List<DataModel> getData() {
        return data;
    }

    public void setData(List<DataModel> data) {
        this.data = data;
    }
}
