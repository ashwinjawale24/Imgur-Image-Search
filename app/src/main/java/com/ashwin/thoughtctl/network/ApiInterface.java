package com.ashwin.thoughtctl.network;


import com.ashwin.thoughtctl.model.SearchResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;


public interface ApiInterface {

    /**
     *
     * @param search The search query that is passed as Query string to the api
     * @param key The authorization key that has to be provided with this api
     * @return Observable<SearchResponse> the observable type of Rxjava
     */
    @GET("1")
    Observable<SearchResponse> getSearchImages(@Query("q") String search, @Header("Authorization") String key);

}
