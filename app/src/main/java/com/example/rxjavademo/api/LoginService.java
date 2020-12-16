package com.example.rxjavademo.api;


import com.example.rxjavademo.result.LoginResult;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface LoginService {
     @POST("/user/login")
     Observable<LoginResult> login(@Query("username")String username, @Query("passwd")String passwd);
}
