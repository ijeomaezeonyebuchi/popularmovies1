package com.example.ijeomaeze.popularmovies_1.network;

import android.net.Uri;

import com.example.ijeomaeze.popularmovies_1.BuildConfig;

public class QueryUtils {

    private static final String movieAPIKey = BuildConfig.MDB_API_KEY;

    public String queryByDefault() {
        Uri.Builder movieUriBuilder = new Uri.Builder();
        String movieURL;
        movieUriBuilder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("discover")
                .appendPath("movie")
                .appendQueryParameter("api_key",movieAPIKey);

        movieURL = movieUriBuilder.build().toString();

        return movieURL;
    }

    public String queryByPopularMovies(){
        Uri.Builder movieUriBuilder = new Uri.Builder();
        String movieURL;
        movieUriBuilder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("popular")
                .appendQueryParameter("api_key",movieAPIKey);

        movieURL = movieUriBuilder.build().toString();
        return movieURL;
    }

    public String queryByTopRatedMovies() {
        Uri.Builder movieUriBuilder = new Uri.Builder();
        String movieURL;
        movieUriBuilder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("top_rated")
                .appendQueryParameter("api_key",movieAPIKey);

        movieURL = movieUriBuilder.build().toString();
        return movieURL;
    }
}
