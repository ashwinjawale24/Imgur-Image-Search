package com.ashwin.thoughtctl.model;

import java.util.List;



public class ImageDetails {
    private List<String> imageLink;
    private List<String> imageTitle;

    public List<String> getImagedataTimeList() {
        return imagedataTimeList;
    }

    public void setImagedataTimeList(List<String> imagedataTimeList) {
        this.imagedataTimeList = imagedataTimeList;
    }

    private List<String> imagedataTimeList;

    public ImageDetails(List<String> imageLink, List<String> imageTitle,List<String> imagedataTimeList) {
        this.imageLink = imageLink;
        this.imageTitle = imageTitle;
        this.imagedataTimeList = imagedataTimeList;
    }

    public List<String> getImageLink() {
        return imageLink;
    }

    public void setImageLink(List<String> imageLink) {
        this.imageLink = imageLink;
    }

    public List<String> getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(List<String> imageTitle) {
        this.imageTitle = imageTitle;
    }

}
