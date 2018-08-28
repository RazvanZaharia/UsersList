package com.users.razvan.users.ui.details;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

import com.users.razvan.users.base.Presenter;
import com.users.razvan.users.model.UserModel;
import com.users.razvan.users.utils.Utils;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class PresenterDetailsActivity implements Presenter<MvpDetailsActivity> {
    private static final String TAG = "PresenterDetailsActivit";

    private CompositeSubscription mSubscriptions;
    private MvpDetailsActivity viewDetails;

    private UserModel mUserModel;

    @Inject
    public PresenterDetailsActivity() {
        mSubscriptions = new CompositeSubscription();
    }

    public void init(UserModel userModel) {
        mUserModel = userModel;

        observeOnMapReady();
        observeOnWebsiteClick();
        observeOnPhoneNumberClick();

        setUserData();
    }

    @SuppressWarnings("unchecked")
    private void observeOnMapReady() {
        mSubscriptions.add(getView().onMapReadyPublisher()
                .compose(applyUISchedulers())
                .subscribe(action -> {
                    setUserLocation();
                }));
    }

    @SuppressWarnings("unchecked")
    private void observeOnWebsiteClick() {
        mSubscriptions.add(getView().onWebsiteClickPublisher()
                .compose(applyUISchedulers())
                .subscribe(action -> {
                    try {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mUserModel.getWebsite()));
                        getView().getViewContext().startActivity(browserIntent);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                }));
    }

    @SuppressWarnings("unchecked")
    private void observeOnPhoneNumberClick() {
        mSubscriptions.add(getView().onPhoneNumberClick()
                .compose(applyUISchedulers())
                .subscribe(action -> {
                    try {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:".concat(mUserModel.getPhone())));
                        getView().getViewContext().startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                }));
    }

    private void setUserLocation() {
        getView().setUserLocation(mUserModel.getAddress().getGeo(), mUserModel.getName());
    }

    private void setUserData() {
        getView().setUserInitials(Utils.getInitials(mUserModel.getName()));
        getView().setUserFullName(mUserModel.getName());
        getView().setUserEmail(mUserModel.getEmail());
        getView().setUserPhoneNumber(mUserModel.getPhone());
        getView().setUserAddress(
                mUserModel.getAddress().getSuite()
                        .concat(", ")
                        .concat(mUserModel.getAddress().getStreet())
                        .concat(", ")
                        .concat(mUserModel.getAddress().getCity()));
        getView().setUserWebsite(mUserModel.getWebsite());
    }

    @Override
    public void attachView(MvpDetailsActivity mvpView) {
        this.viewDetails = mvpView;
    }

    @Override
    public void detachView() {
        viewDetails = null;
        if (mSubscriptions.hasSubscriptions()) {
            mSubscriptions.clear();
        }
    }

    public MvpDetailsActivity getView() {
        return viewDetails;
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
