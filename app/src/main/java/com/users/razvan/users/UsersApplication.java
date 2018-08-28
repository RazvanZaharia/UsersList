package com.users.razvan.users;

import android.support.multidex.MultiDexApplication;

import com.users.razvan.users.injection.component.ApplicationComponent;
import com.users.razvan.users.injection.component.DaggerApplicationComponent;
import com.users.razvan.users.injection.module.ApplicationModule;
import com.users.razvan.users.injection.module.NetworkModule;
import com.users.razvan.users.injection.module.ServiceModule;

public class UsersApplication extends MultiDexApplication {
    protected ApplicationComponent applicationComponent;

    public ApplicationComponent getComponent() {
        if (applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .networkModule(new NetworkModule())
                    .serviceModule(new ServiceModule())
                    .build();
        }
        return applicationComponent;
    }
}
