package com.example.arinspect.network;

import com.example.arinspect.database.entity.RowResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author spatel
 * @since 16-10-2019
 */
public interface ApiInterface {

    @GET(ApiConstants.GET_ROW_LIST)
    public Call<RowResponse> getRowList();
}
