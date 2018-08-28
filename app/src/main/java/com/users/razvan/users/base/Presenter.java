package com.users.razvan.users.base;

public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}