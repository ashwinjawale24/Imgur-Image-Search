package com.prajwal.prajwalwaingankar_cavista.viewModel;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.prajwal.prajwalwaingankar_cavista.model.ImageDetails;
import com.prajwal.prajwalwaingankar_cavista.network.API_RequestConnection;

import java.util.List;
import java.util.Map;


/**
 * Created by Prajwal Waingankar
 * on 23-Aug-20.
 * Github: prajwalmw
 */


public class Response_ViewModel extends ViewModel {
    API_RequestConnection connection;
    LiveData<Map<String, ImageDetails>> result;
    MutableLiveData<String> search_query = new MutableLiveData<>();


    public Response_ViewModel() {
       // connection
        //whenever search_query value will be set to new value (new_searchQuery variable contains this new
        // value; connection.getApiInterface(query) will be called).
        search_query.setValue("cats");
        connection = new API_RequestConnection();

         result = Transformations.switchMap(search_query,
                new_searchQuery -> {
                    return connection.getApiInterface(new_searchQuery);
                });
    }

    /**
     * MutableLiveData return type stated getter function.
     * @return MutableLiveData<Map<String, ImageDetails>>
     */
    public LiveData<Map<String, ImageDetails>> getResult() {
        return result;
    }

    public void setQuery(String query) {
        this.search_query.setValue(query);
    }


}
