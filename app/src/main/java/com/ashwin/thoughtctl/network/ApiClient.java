package com.ashwin.thoughtctl.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    private static Retrofit retrofit = null;
    static OkHttpClient.Builder client = new OkHttpClient.Builder();

    /**
     * This function creates the Retrofit instance & states the baseUrl
     * @return Retrofit
     */
    public static Retrofit getApiClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(loggingInterceptor);
        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.imgur.com/3/gallery/search/") //baseurl for imgur
                    .client(client.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
