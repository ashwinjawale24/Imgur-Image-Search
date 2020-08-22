package com.prajwal.prajwalwaingankar_cavista.firstScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;

import com.prajwal.prajwalwaingankar_cavista.R;
import com.prajwal.prajwalwaingankar_cavista.model.SearchResponse;
import com.prajwal.prajwalwaingankar_cavista.network.ApiClient;
import com.prajwal.prajwalwaingankar_cavista.network.ApiInterface;
import com.prajwal.prajwalwaingankar_cavista.secondScreen.KotlinSecondScreen;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    static GridView gridView;
    SearchView searchView;
    static ApiInterface apiInterface;
    static List<String> imageUrlsList;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);
        searchView = findViewById(R.id.searchView);
        context = MainActivity.this;

        getApiInterface();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getApplicationContext(), KotlinSecondScreen.class);
                i.putExtra("id", position);
                startActivity(i);
            }
        });
    }

    public static ApiInterface getApiInterface()
    {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        final Observable<SearchResponse> observable =
                apiInterface.getSearchImages("fruit", "Client-ID 137cda6b5008a7c");
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<SearchResponse>() {
                    @Override
                    public void onNext(SearchResponse searchResponse) {
                        Log.d("praj",searchResponse.getData().get(0).getTitle());

                        imageUrlsList = new ArrayList<>();
                        for (int i = 0; i < searchResponse.getData().size(); i++) {
                            for (int j = 0; j < searchResponse.getData().get(i).getImages().size(); j++) {
                                imageUrlsList
                                        .add(i, searchResponse.getData().get(i).getImages().get(j).getLink());

                            }

                        }

                        gridView.setAdapter(new ImageAdapter(context, imageUrlsList));
                        Log.d("praj", String.valueOf(imageUrlsList.size()));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return apiInterface;
    }

}