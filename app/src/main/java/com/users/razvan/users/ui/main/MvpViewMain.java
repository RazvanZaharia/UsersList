package com.users.razvan.users.ui.main;

import android.support.annotation.NonNull;

import com.users.razvan.users.base.MvpView;
import com.users.razvan.users.model.UserModel;

import java.util.List;

import rx.Observable;

public interface MvpViewMain extends MvpView {
    void showData(@NonNull List<UserModel> users);

    Observable<UserModel> getClickedUserPublisher();
}
