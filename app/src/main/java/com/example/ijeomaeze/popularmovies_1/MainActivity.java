package com.example.ijeomaeze.popularmovies_1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ijeomaeze.popularmovies_1.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Instantiate Object need display content for moviesAPI
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private ArrayList<Movie> mMovieList;

    //Instantiate Request Queue needed to make calls with volley
    private RequestQueue mRequestQueue;

    //Construct Movie URL
    private static final String movieAPIKey = BuildConfig.MDB_API_KEY;
    private static final String movieURL = "http://api.themoviedb.org/3/movie/popular?api_key=" + movieAPIKey;
    private static final String baseImageURL = "http://image.tmdb.org/t/p/w185";


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

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, movieURL, null,onMoviesLoaded, onMoviesError);
        mRequestQueue.add(request);

    }

    private final Response.Listener<JSONObject> onMoviesLoaded = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            //Parse results
            try {
                JSONArray resultsArray = response.getJSONArray("results");

                //Loop through results in array
                for(int i= 0; i < resultsArray.length(); i++){
                    JSONObject movieResult = resultsArray.getJSONObject(i);

                    //Parse values for main screen
                    String movieTitle = movieResult.getString("title");
                    String movieImageUrl =  baseImageURL.concat((movieResult.getString("poster_path")));

                    //Add values to movieArrayList
                    mMovieList.add(new Movie(movieTitle, movieImageUrl));
                }

                //Bind Movie Date using Adapter
                mMovieAdapter = new MovieAdapter(MainActivity.this, mMovieList);
                mRecyclerView.setAdapter(mMovieAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    private final Response.ErrorListener onMoviesError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
        }
    };


}



