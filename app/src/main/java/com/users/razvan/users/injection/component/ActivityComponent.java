package com.users.razvan.users.injection.component;


import com.users.razvan.users.injection.PerActivity;
import com.users.razvan.users.injection.module.ActivityModule;
import com.users.razvan.users.ui.details.UserDetailsActivity;
import com.users.razvan.users.ui.main.MainActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);

    void inject(UserDetailsActivity userDetailsActivity);
}
