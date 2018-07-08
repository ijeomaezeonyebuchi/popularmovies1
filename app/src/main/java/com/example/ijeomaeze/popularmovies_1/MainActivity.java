package com.example.ijeomaeze.popularmovies_1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    TextView mTextView;

    //Construct Movie URL
    String movieAPIKey = BuildConfig.MDB_API_KEY;
    String movieURL = "http://api.themoviedb.org/3/movie/popular?api_key=" + movieAPIKey;

    //Instantiate Request Queue
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.tv_testresponse);

        mRequestQueue = Volley.newRequestQueue(this);
        fetchMovies();

    }

    private void fetchMovies() {
        StringRequest movieStringRequest = new StringRequest(Request.Method.GET, movieURL, onMoviesLoaded, onMoviesError);
        mRequestQueue.add(movieStringRequest);
    }

    private final Response.Listener<String> onMoviesLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("MainMovie",response.substring(0,500));
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



