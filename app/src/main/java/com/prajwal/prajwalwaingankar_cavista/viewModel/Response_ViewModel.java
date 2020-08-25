package com.prajwal.prajwalwaingankar_cavista.viewModel;

import androidx.lifecycle.MutableLiveData;
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
    MutableLiveData<Map<String, ImageDetails>> result;

    public Response_ViewModel() {
        connection = new API_RequestConnection();
        result = connection.getApiInterface();
    }

    /**
     * MutableLiveData return type stated getter function.
     * @return MutableLiveData<Map<String, ImageDetails>>
     */
    public MutableLiveData<Map<String, ImageDetails>> getResult() {
        return result;
    }


}
