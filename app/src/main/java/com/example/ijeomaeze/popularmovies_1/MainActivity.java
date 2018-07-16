package com.example.ijeomaeze.popularmovies_1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ijeomaeze.popularmovies_1.model.Movie;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Instantiate Object need display content for moviesAPI
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private ArrayList<Movie> mMovieList;

    //Instantiate Request Queue needed to make calls with volley
    private RequestQueue mRequestQueue;

    //Construct Movie URL
    String movieAPIKey = BuildConfig.MDB_API_KEY;
    String movieURL = "http://api.themoviedb.org/3/movie/popular?api_key=" + movieAPIKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        mMovieList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        fetchMovies();

    }

    private void fetchMovies() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, movieURL, null,onMoviesLoaded, onMoviesError);


    }

    private final Response.Listener<JsonObjectRequest> onMoviesLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("MainMovie",response);
            }
    };

    private final Response.ErrorListener onMoviesError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("MainMovie", error.toString());
        }
    };


    @Override
    protected void onStop() {
        super.onStop();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll("MainMovie");
        }
    }
}



