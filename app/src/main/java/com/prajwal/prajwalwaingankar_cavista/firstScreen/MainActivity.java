package com.prajwal.prajwalwaingankar_cavista.firstScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import com.prajwal.prajwalwaingankar_cavista.network.API_RequestConnection;
import com.prajwal.prajwalwaingankar_cavista.network.ApiClient;
import com.prajwal.prajwalwaingankar_cavista.network.ApiInterface;
import com.prajwal.prajwalwaingankar_cavista.secondScreen.KotlinSecondScreen;
import com.prajwal.prajwalwaingankar_cavista.viewModel.Response_ViewModel;

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
    Map<String, List<String>> stringMap;
    Context context;
    String mquery="";
    API_RequestConnection connection;
    Response_ViewModel response_viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);
        searchView = findViewById(R.id.searchView);
        context = MainActivity.this;

        connection = new API_RequestConnection();

        response_viewModel = new ViewModelProvider(MainActivity.this).get(Response_ViewModel.class);

     /*   if(response_viewModel.getResult() != null) {
            response_viewModel.getResult().observe(MainActivity.this, new Observer<List<String>>() {
                @Override
                public void onChanged(List<String> strings) {
                    gridView.setAdapter(new ImageAdapter(context, strings));
                    imageUrlsList = strings;
                }
            });*/

            response_viewModel.getResult().observe(MainActivity.this, new Observer<Map<String, List<String>>>() {
                @Override
                public void onChanged(Map<String, List<String>> stringListMap) {

                    for(String key : stringListMap.keySet())
                        mquery = key;

                    gridView.setAdapter(new ImageAdapter(context, stringListMap.get(mquery)));
//                    imageUrlsList = strings;
                }
            });


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
               new_request(query);
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               return false;
           }
       });

//        if(!mquery.isEmpty())
//        {
//            connection.getApiInterface();
//        }

//        else
//           Toast.makeText(context, "Enter search field", Toast.LENGTH_SHORT).show();


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getApplicationContext(), KotlinSecondScreen.class);
                i.putExtra("url", imageUrlsList.get(position));
                startActivity(i);
            }
        });
    }

    public void new_request(final String vquery)
    {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        final Observable<SearchResponse> observable =
                apiInterface.getSearchImages(vquery, "Client-ID 137cda6b5008a7c");
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<SearchResponse>() {
                    @Override
                    public void onNext(SearchResponse searchResponse) {
//                            Log.d("praj", searchResponse.getData().get(0).getTitle());
                        imageUrlsList = new ArrayList<>();
                        stringMap = new HashMap<>();

                        for (int i = 0; i < searchResponse.getData().size(); i++) {
                            if ((searchResponse.getData().get(i).getImages() != null)) {
                                for (int j = 0; j < searchResponse.getData().get(i).getImages().size(); j++) {
                                    if (searchResponse.getData().get(i).getImages().get(j).getLink()
                                            .contains(".jpg") || searchResponse.getData()
                                            .get(i).getImages().get(j).getLink().contains(".png")) {

                                        imageUrlsList.add(i, searchResponse.getData().get(i)
                                                .getImages().get(j).getLink());
                                    } else {
                                        imageUrlsList.add(i, "empty");
                                    }
                                }
                            } else {
                                imageUrlsList.add(i, "empty");
                            }


                        }

                        imageUrlsList.remove("empty");
                        stringMap.put(vquery, imageUrlsList);
                        response_viewModel.getResult().setValue(stringMap);

                        Log.d("praj31", String.valueOf(imageUrlsList.size()));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

}