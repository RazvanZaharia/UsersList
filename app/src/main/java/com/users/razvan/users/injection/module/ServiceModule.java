package com.users.razvan.users.injection.module;

import com.users.razvan.users.retrofit.UsersService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = ApplicationModule.class)
public class ServiceModule {

    @Singleton
    @Provides
    UsersService providesRetrofitService(Retrofit retrofit) {
        return retrofit.create(UsersService.class);
    }

}
