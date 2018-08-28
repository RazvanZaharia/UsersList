package com.users.razvan.users.injection.component;


import com.users.razvan.users.injection.ConfigPersistent;
import com.users.razvan.users.injection.module.ActivityModule;

import dagger.Component;

@ConfigPersistent
@Component(dependencies = ApplicationComponent.class)
public interface ConfigPersistentComponent {
    ActivityComponent activityComponent(ActivityModule activityModule);
}