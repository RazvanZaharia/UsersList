package com.users.razvan.users.ui.main;

import android.util.Log;

import com.users.razvan.users.base.Presenter;
import com.users.razvan.users.retrofit.UsersService;
import com.users.razvan.users.ui.details.UserDetailsActivity;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class PresenterMain implements Presenter<MvpViewMain> {
    private static final String TAG = "PresenterMain";

    private UsersService mService;
    private CompositeSubscription mSubscriptions;
    private MvpViewMain viewMain;

    @Inject
    public PresenterMain(UsersService service) {
        mService = service;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void attachView(MvpViewMain mvpView) {
        this.viewMain = mvpView;
    }

    @Override
    public void detachView() {
        viewMain = null;
        if (mSubscriptions.hasSubscriptions()) {
            mSubscriptions.clear();
        }
    }

    public void init() {
        mSubscriptions.add(viewMain.getClickedUserPublisher()
                .compose(applyUISchedulers())
                .subscribe(userModel -> UserDetailsActivity.launch(getView().getViewContext(), userModel)));

        mSubscriptions.add(mService.getUsers()
                .compose(applySchedulers())
                .subscribe(response -> {
                            Log.d(TAG, "response: " + response.toString());
                            getView().showData(response);
                        },
                        throwable -> {
                            Log.e(TAG, "request: ", throwable);
                        }));
    }

    public MvpViewMain getView() {
        return viewMain;
    }

    private <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable<T> observable) -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private <T> Observable.Transformer<T, T> applyIOSchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    private <T> Observable.Transformer<T, T> applyUISchedulers() {
        return observable -> observable.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
