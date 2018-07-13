package com.example.ijeomaeze.popularmovies_1.model;

import java.util.Date;

public class Movie {

    private int mId;
    private String mTitle;
    private String mPosterImageURL;
    private String mPlotSynopsis;
    private Double mRating;
    private Date mReleaseDate;

    //Model for Movie in List
    public Movie(String title, String posterImageURL) {
        mTitle = title;
        mPosterImageURL = posterImageURL;
    }

    //Model for Movie Detail
    public Movie(int id, String title, String posterImageURL, String plotSynopsis, Double rating, Date releaseDate ){
        mId = id;
        mTitle = title;
        mPosterImageURL = posterImageURL;
        mPlotSynopsis = plotSynopsis;
        mRating = rating;
        mReleaseDate = releaseDate;


    }


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getPosterImageURL() {
        return mPosterImageURL;
    }

    public void setPosterImageURL(String posterImageURL) {
        mPosterImageURL = posterImageURL;
    }

    public String getPlotSynopsis() {
        return mPlotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        mPlotSynopsis = plotSynopsis;
    }

    public Double getRating() {
        return mRating;
    }

    public void setRating(Double rating) {
        mRating = rating;
    }

    public Date getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        mReleaseDate = releaseDate;
    }

}

