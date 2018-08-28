package com.users.razvan.users.injection.module;

import android.content.Context;

import com.users.razvan.users.injection.ActivityContext;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private Context context;

    public ActivityModule(Context context) {
        this.context = context;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return context;
    }

}
