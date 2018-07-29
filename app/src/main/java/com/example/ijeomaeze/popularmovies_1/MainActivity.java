package com.example.ijeomaeze.popularmovies_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnItemClickListener{
    //Instantiate Object need display content for moviesAPI
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private ArrayList<Movie> mMovieList;

    //Instantiate Values as keys to use for Movie Detail
    public static final String EXTRA_MOVIE_IMAGE_URL = "imageUrl";
    public static final String EXTRA_MOVIE_TITLE = "movieTitle";
    public static final String EXTRA_MOVIE_SUMMARY = "movieSummary";
    public static final String EXTRA_MOVIE_RELEASE_DATE = "releaseDate";
    public static final String EXTRA_MOVIE_RATING = "movieRating";


    //Instantiate Request Queue needed to make calls with volley
    private RequestQueue mRequestQueue;

    //Construct Movie URL
    private static final String movieAPIKey = BuildConfig.MDB_API_KEY;
    private static final String movieURL = "http://api.themoviedb.org/3/movie/popular?api_key=" + movieAPIKey;
    private static final String movieAPIKeyParam = "api_key=" + movieAPIKey;
    public static final String baseImageURL = "http://image.tmdb.org/t/p/w185";

    private String baseMovieURL =  "http://api.themoviedb.org/3/movie/";
    private String popularMovieURL = baseMovieURL;
    private String highestRatedURL = baseMovieURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        mMovieList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        fetchMovies(movieURL);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.sortby_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.most_popular):
                fetchMovies(movieURL);
                break;
            case (R.id.highest_rating):
                fetchMovies(movieURL);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchMovies(String requestURL) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, requestURL, null,onMoviesLoaded, onMoviesError);
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
                    String moviePlotSummary = movieResult.getString("overview");
                    String movieReleaseDate = formatDate(movieResult.getString("release_date"));
                    String movieRating = String.valueOf(movieResult.getDouble("vote_average"));

                    //Add values to movieArrayList
                    mMovieList.add(new Movie(movieTitle,movieImageUrl, moviePlotSummary, movieRating, movieReleaseDate));
                }

                //Bind Movie Date using Adapter
                mMovieAdapter = new MovieAdapter(MainActivity.this, mMovieList);
                mRecyclerView.setAdapter(mMovieAdapter);
                mMovieAdapter.setOnItemClickListener(MainActivity.this);
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


    @Override
    public void onItemClick(int position) {
        Intent movieDetailIntent = new Intent(this, DetailActivity.class);
        Movie selectedMovie = mMovieList.get(position);

        movieDetailIntent.putExtra(EXTRA_MOVIE_TITLE, selectedMovie.getTitle());
        movieDetailIntent.putExtra(EXTRA_MOVIE_IMAGE_URL, selectedMovie.getPosterImageURL());
        movieDetailIntent.putExtra(EXTRA_MOVIE_SUMMARY, selectedMovie.getPlotSynopsis());
        movieDetailIntent.putExtra(EXTRA_MOVIE_RATING, selectedMovie.getRating());
        movieDetailIntent.putExtra(EXTRA_MOVIE_RELEASE_DATE, selectedMovie.getReleaseDate());

        startActivity(movieDetailIntent);

    }

    private String formatDate(String date){
        SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate;

        try {
            Date currentDate = dateformatter.parse(date);
            dateformatter.applyPattern("MMMM dd, yyyy");
            formattedDate = dateformatter.format(currentDate);

        } catch (ParseException e) {
            formattedDate = "";
            e.printStackTrace();
        }


        return formattedDate;
    }
}



