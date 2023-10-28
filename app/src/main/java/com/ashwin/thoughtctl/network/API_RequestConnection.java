package com.ashwin.thoughtctl.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.ashwin.thoughtctl.model.ImageDetails;
import com.ashwin.thoughtctl.model.SearchResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class API_RequestConnection {
    MutableLiveData<Map<String, ImageDetails>> mutableLiveData;
    ApiInterface apiInterface;
    List<String> imageUrlsList, imagetitleList, imagedataTimeList;
    ImageDetails details;
    Map<String, ImageDetails> stringMap;

    /**
     * The api calling is done with this function where the query is passed
     * as the argument and the api-key is passed as authentication.
     *
     * @return MutableLiveData<Map < String, ImageDetails>>
     */
    public MutableLiveData<Map<String, ImageDetails>> getApiInterface(String query) {

        mutableLiveData = new MutableLiveData<>();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        final Observable<SearchResponse> observable =
                apiInterface.getSearchImages(query, "Client-ID c6b083c91056f1a");
        observable
                .debounce(250, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<SearchResponse>() {
                    @Override
                    public void onNext(SearchResponse searchResponse) {
                            //Log.d("logdatetime", String.valueOf(searchResponse));
                        imageUrlsList = new ArrayList<>();
                        imagetitleList = new ArrayList<>();
                        imagedataTimeList = new ArrayList<>();

                        stringMap = new HashMap<>();

                        details = new ImageDetails(imageUrlsList, imagetitleList,imagedataTimeList);

                        for (int i = 0; i < searchResponse.getData().size(); i++) {
                            if ((searchResponse.getData().get(i).getImages() != null)) {
                                for (int j = 0; j < searchResponse.getData().get(i).getImages().size(); j++) {
                                    if ((searchResponse.getData().get(i).getImages()
                                            .get(j).getLink().contains(".jpg")
                                            || searchResponse.getData()
                                            .get(i).getImages().get(j).getLink().contains(".png"))) {

                                        if (searchResponse.getData().get(i).getTitle() != null) {
                                            imageUrlsList.add(i, searchResponse.getData().get(i)
                                                    .getImages().get(j).getLink());
                                            imagetitleList.add(i, searchResponse.getData().get(i)
                                                    .getTitle());
                                            imagedataTimeList.add(i, String.valueOf(searchResponse.getData().get(i)
                                                    .getDatetime()));
                                            
                                        } else {
                                            imageUrlsList.add(i, searchResponse.getData().get(i)
                                                    .getImages().get(j).getLink());
                                            imagetitleList.add(i, "No title");
                                            imagedataTimeList.add(i, "No Data");
                                        }

                                    } else {
                                        imageUrlsList.add(i, "empty");
                                        imagetitleList.add(i, "empty");
                                        imagedataTimeList.add(i, "empty");
                                    }
                                }
                            } else {
                                imageUrlsList.add(i, "empty");
                                imagetitleList.add(i, "empty");
                                imagedataTimeList.add(i, "empty");
                            }

                        }


                        imageUrlsList.removeAll(Collections.singleton("empty"));
                        imagetitleList.removeAll(Collections.singleton("empty"));
                        imagedataTimeList.removeAll(Collections.singleton("empty"));
                        stringMap.put(query, new ImageDetails(imageUrlsList, imagetitleList,imagedataTimeList));

                        mutableLiveData.setValue(stringMap);

                        Log.d("lgSize", String.valueOf(imageUrlsList.size()));
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

}
