package com.ceotic.clubtrack.model;

public class FreeSample {

    private String idSample;

    private String nameSample;
    private String imageSample;
    private String descriptionSample;

    public FreeSample(String nameSample, String imageSample, String descriptionSample) {
        this.nameSample = nameSample;
        this.imageSample = imageSample;
        this.descriptionSample = descriptionSample;
    }

    public FreeSample() {
    }

    public String getNameSample() {
        return nameSample;
    }

    public void setNameSample(String nameSample) {
        this.nameSample = nameSample;
    }

    public String getImageSample() {
        return imageSample;
    }

    public void setImageSample(String imageSample) {
        this.imageSample = imageSample;
    }

    public String getDescriptionSample() {
        return descriptionSample;
    }

    public void setDescriptionSample(String descriptionSample) {
        this.descriptionSample = descriptionSample;
    }
}
