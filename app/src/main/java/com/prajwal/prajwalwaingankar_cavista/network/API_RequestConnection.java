package com.prajwal.prajwalwaingankar_cavista.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.GridView;

import androidx.lifecycle.MutableLiveData;

import com.prajwal.prajwalwaingankar_cavista.firstScreen.ImageAdapter;
import com.prajwal.prajwalwaingankar_cavista.model.ImageDetails;
import com.prajwal.prajwalwaingankar_cavista.model.SearchResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Prajwal Waingankar
 * on 23-Aug-20.
 * Github: prajwalmw
 */


public class API_RequestConnection {
    MutableLiveData<Map<String, ImageDetails>> mutableLiveData;
    ApiInterface apiInterface;
    List<String> imageUrlsList, imagetitleList;
    ImageDetails details;
    Map<String, ImageDetails> stringMap;
    String mquery = "shapes";  //default value for first launch as shown in assignment.

    public MutableLiveData<Map<String, ImageDetails>> getApiInterface() {

        if(mutableLiveData == null) {

            mutableLiveData = new MutableLiveData<>();

            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            final Observable<SearchResponse> observable =
                    apiInterface.getSearchImages(getMquery(), "Client-ID 137cda6b5008a7c");
            observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<SearchResponse>() {
                        @Override
                        public void onNext(SearchResponse searchResponse) {
//                            Log.d("praj", searchResponse.getData().get(0).getTitle());
                            imageUrlsList = new ArrayList<>();
                            imagetitleList = new ArrayList<>();
                            stringMap = new HashMap<>();
                            details = new ImageDetails(imageUrlsList, imagetitleList);

                            for (int i = 0; i < searchResponse.getData().size(); i++) {
                                if ((searchResponse.getData().get(i).getImages() != null)) {
                                    for (int j = 0; j < searchResponse.getData().get(i).getImages().size(); j++)
                                    {
                                        if ((searchResponse.getData().get(i).getImages()
                                                .get(j).getLink().contains(".jpg")
                                                || searchResponse.getData()
                                                .get(i).getImages().get(j).getLink().contains(".png")))
                                        {



                                            if(searchResponse.getData().get(i).getTitle() != null)
                                            {
                                                imageUrlsList.add(i, searchResponse.getData().get(i)
                                                        .getImages().get(j).getLink());
                                                imagetitleList.add(i, searchResponse.getData().get(i)
                                                        .getTitle());
                                            }
                                            else
                                            {
                                                imageUrlsList.add(i, searchResponse.getData().get(i)
                                                        .getImages().get(j).getLink());
                                                imagetitleList.add(i, "No title");
                                            }

                                        } else {
                                            imageUrlsList.add(i, "empty");
                                            imagetitleList.add(i, "empty");
                                        }
                                    }
                                } else {
                                    imageUrlsList.add(i, "empty");
                                    imagetitleList.add(i, "empty");
                                }


                            }



                            imageUrlsList.removeAll(Collections.singleton("empty"));
                            imagetitleList.removeAll(Collections.singleton("empty"));
                            stringMap.put(getMquery(), new ImageDetails(imageUrlsList, imagetitleList));

                            mutableLiveData.setValue(stringMap);

                            Log.d("praj31", String.valueOf(imageUrlsList.size()));
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

            return mutableLiveData;

        }
        return mutableLiveData;
    }

    public String getMquery() {
        return mquery;
    }

    public void setMquery(String mquery) {
        this.mquery = mquery;
    }




}
