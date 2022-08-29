package com.yh.parkingpartner.api;

import com.yh.parkingpartner.model.ReviewListRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiMypageActivity {

    @GET("/review")
    Call<ReviewListRes> getReviewList(@Header("Authorization") String token, @Query("order") String order, @Query("offset") int offset, @Query("limit") int limit);

}
