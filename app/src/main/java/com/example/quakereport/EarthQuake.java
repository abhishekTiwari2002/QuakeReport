package com.example.quakereport;

public class EarthQuake {
    String location;
    double magnitude;
    String date;
    String url;
   EarthQuake(Double magnitude,String quakeName,String date,String url){
        this.location=quakeName;
        this.date=date;
        this.magnitude=magnitude;
        this.url=url;
    }
    public String getQuakeName(){return location;}
    public double getMagnitude(){return magnitude;}
    public String getDate(){return date;}
    public String getUrl(){return url;}
}
