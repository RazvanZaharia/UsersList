package com.users.razvan.users.injection.component;

import android.app.Application;
import android.content.Context;

import com.users.razvan.users.UsersApplication;
import com.users.razvan.users.injection.ApplicationContext;
import com.users.razvan.users.injection.module.ApplicationModule;
import com.users.razvan.users.injection.module.NetworkModule;
import com.users.razvan.users.injection.module.ServiceModule;
import com.users.razvan.users.retrofit.UsersService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class,
        ServiceModule.class
})
public interface ApplicationComponent {

    void inject(UsersApplication usersApplication);

    @ApplicationContext
    Context context();

    Application application();

    UsersService retrofitService();
}
