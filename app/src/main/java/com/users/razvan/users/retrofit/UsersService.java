package com.users.razvan.users.retrofit;


import com.users.razvan.users.model.UserModel;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface UsersService {
    @GET("users")
    Observable<List<UserModel>> getUsers();
}
