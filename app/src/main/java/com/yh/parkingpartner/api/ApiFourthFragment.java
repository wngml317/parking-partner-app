package com.yh.parkingpartner.api;

import com.yh.parkingpartner.model.DataListRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ApiFourthFragment {


    @GET("/parkingend/{prk_id}")
    Call<DataListRes> getPay(@Path("prk_id") int prk_id);

}
