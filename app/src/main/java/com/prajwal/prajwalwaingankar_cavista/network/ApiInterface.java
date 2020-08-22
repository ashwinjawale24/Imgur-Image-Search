package com.prajwal.prajwalwaingankar_cavista.network;

import com.prajwal.prajwalwaingankar_cavista.model.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Prajwal Waingankar
 * on 22-Aug-20.
 * Github: prajwalmw
 */


public interface ApiInterface {

    @GET
    Call<SearchResponse> getSearchImages(@Query("q") String search);

}
