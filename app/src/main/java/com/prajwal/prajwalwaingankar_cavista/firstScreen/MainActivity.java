package com.prajwal.prajwalwaingankar_cavista.firstScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import com.prajwal.prajwalwaingankar_cavista.R;
import com.prajwal.prajwalwaingankar_cavista.model.SearchResponse;
import com.prajwal.prajwalwaingankar_cavista.network.ApiClient;
import com.prajwal.prajwalwaingankar_cavista.network.ApiInterface;
import com.prajwal.prajwalwaingankar_cavista.secondScreen.KotlinSecondScreen;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    SearchView searchView;
    ApiInterface apiInterface;
    List<String> imageUrlsList;
    Context context;
    String mquery="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);
        searchView = findViewById(R.id.searchView);
        context = MainActivity.this;

       /* searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query = searchView.getQuery().toString();

            }
        });*/
       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               mquery = query;
               getApiInterface(mquery);
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               return false;
           }
       });

        if(!mquery.isEmpty())
            getApiInterface(mquery);
        else
           Toast.makeText(context, "Enter search field", Toast.LENGTH_SHORT).show();


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getApplicationContext(), KotlinSecondScreen.class);
                i.putExtra("url", imageUrlsList.get(position));
                startActivity(i);
            }
        });
    }

    public ApiInterface getApiInterface(String query)
    {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        final Observable<SearchResponse> observable =
                apiInterface.getSearchImages(query, "Client-ID 137cda6b5008a7c");
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<SearchResponse>() {
                    @Override
                    public void onNext(SearchResponse searchResponse) {
                        Log.d("praj",searchResponse.getData().get(0).getTitle());
                        imageUrlsList = new ArrayList<>();

                        for (int i = 0; i < searchResponse.getData().size(); i++) {
                            if((searchResponse.getData().get(i).getImages() != null)) {
                                for (int j = 0; j < searchResponse.getData().get(i).getImages().size(); j++) {
                                    if(searchResponse.getData().get(i).getImages().get(j).getLink()
                                            .contains(".jpg") || searchResponse.getData()
                                            .get(i).getImages().get(j).getLink().contains(".png"))

                            {
                                imageUrlsList.add(i, searchResponse.getData().get(i)
                                        .getImages().get(j).getLink());
                            }
                            else
                            {
                                imageUrlsList.add(i, "empty");
                            }
                             }
                            }
                            else
                            {
                                imageUrlsList.add(i, "empty");
                            }
                        }

                        gridView.setAdapter(new ImageAdapter(context, imageUrlsList));
                        Log.d("praj31", String.valueOf(imageUrlsList.size()));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
dispose();
                    }
                });

        return apiInterface;
    }

}