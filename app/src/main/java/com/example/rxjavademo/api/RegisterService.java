package com.example.rxjavademo.api;

import com.example.rxjavademo.result.RegisterResult;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface RegisterService {

    @POST("/user/register")
    Observable<RegisterResult> register(@Query("username") String username, @Query("passwd")String passwd, @Query("code") String code);


}
