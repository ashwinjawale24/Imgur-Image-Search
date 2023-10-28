package com.ashwin.thoughtctl.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.ashwin.thoughtctl.model.ImageDetails;
import com.ashwin.thoughtctl.network.API_RequestConnection;

import java.util.Map;



public class Response_ViewModel extends ViewModel {
    API_RequestConnection connection;
    LiveData<Map<String, ImageDetails>> result;
    MutableLiveData<String> search_query = new MutableLiveData<>();


    public Response_ViewModel() {

        search_query.setValue("Top images");
        connection = new API_RequestConnection();

        result = Transformations.switchMap(search_query,
                new_searchQuery -> {
                    return connection.getApiInterface(new_searchQuery);
                });
    }

    /**
     * MutableLiveData return type stated getter function.
     *
     * @return MutableLiveData<Map < String, ImageDetails>>
     */
    public LiveData<Map<String, ImageDetails>> getResult() {
        return result;
    }

    public void setQuery(String query) {
        this.search_query.setValue(query);
    }


}
