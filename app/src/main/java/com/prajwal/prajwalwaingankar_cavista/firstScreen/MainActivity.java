package com.prajwal.prajwalwaingankar_cavista.firstScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.prajwal.prajwalwaingankar_cavista.R;
import com.prajwal.prajwalwaingankar_cavista.model.ImageDetails;
import com.prajwal.prajwalwaingankar_cavista.model.SearchResponse;
import com.prajwal.prajwalwaingankar_cavista.network.API_RequestConnection;
import com.prajwal.prajwalwaingankar_cavista.network.ApiClient;
import com.prajwal.prajwalwaingankar_cavista.network.ApiInterface;
import com.prajwal.prajwalwaingankar_cavista.network.NetworkConnection;
import com.prajwal.prajwalwaingankar_cavista.secondScreen.KotlinSecondScreen;
import com.prajwal.prajwalwaingankar_cavista.viewModel.Response_ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    SearchView searchView;
    ApiInterface apiInterface;
    List<String> imageUrlsList, imageTitleList;
    Map<String, ImageDetails> stringMap;
    Context context;
    String mquery = "", image_id;
    API_RequestConnection connection;
    Response_ViewModel response_viewModel;

    /**
     * The onCreate() is the first function that is called
     * when you launch your app.
     *
     * @param savedInstanceState savedInstanceState of the onCreate()
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);
       // searchView = findViewById(R.id.searchView);
        context = MainActivity.this;
        imageUrlsList = new ArrayList<>();
        imageTitleList = new ArrayList<>();

        connection = new API_RequestConnection();

        if (!NetworkConnection.isOnline(context) || !NetworkConnection.isConnecting(context)) {
            internet_check();
        }

        response_viewModel = new ViewModelProvider(MainActivity.this).get(Response_ViewModel.class);
        response_viewModel.getResult().observe(MainActivity.this,
                new Observer<Map<String, ImageDetails>>() {

                    @Override
                    public void onChanged(Map<String, ImageDetails> stringImageDetailsMap) {
                        for (String key : stringImageDetailsMap.keySet())
                            mquery = key;

                        imageUrlsList = Objects.requireNonNull(stringImageDetailsMap.get(mquery)).getImageLink();

                        //image Titles
                        imageTitleList = Objects.requireNonNull(stringImageDetailsMap.get(mquery)).getImageTitle();

                        if (!stringImageDetailsMap.get(mquery).getImageLink().isEmpty()) {
                            //Image Links
                            gridView.setAdapter(new ImageAdapter(context, Objects.requireNonNull
                                    (stringImageDetailsMap.get(mquery)).getImageLink()));
                        } else {
                            Toast.makeText(context, "No images found! The query is either invalid or doesn't contains images", Toast.LENGTH_LONG).show();
                        }

                    }
                });




        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getApplicationContext(), KotlinSecondScreen.class);
                i.putExtra("url", imageUrlsList.get(position));
                i.putExtra("title", imageTitleList.get(position));
                Log.d("Title", imageTitleList.get(position));
                startActivity(i);
            }
        });
    }


    /**
     * This function is used when making a new request
     * to the api for new search query
     *
     * @param vquery the search query that is the endpoint of the api.
     */

    /**
     * This function checks for the internet connectivity function
     * to avoid any crashes...
     *
     * @return void
     */
    public void internet_check() {

        AlertDialog.Builder alerBuilder = new AlertDialog.Builder(context)
                .setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again later.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        moveTaskToBack(true);
                        System.exit(1);   //non-zero states that the JVM has to be killed.
                    }
                });
        AlertDialog alertDialog = alerBuilder.create();
        alertDialog.show();
        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_main, menu);
        MenuItem menuItem = menu.findItem(R.id.search_item);
        searchView = (SearchView) menuItem.getActionView();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_item:
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        mquery = query;
                        response_viewModel.setQuery(query);
                        searchView.clearFocus();  //disables the keyboard show up on rotation.
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}