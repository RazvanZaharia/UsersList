package com.users.razvan.users.ui.details;

import com.users.razvan.users.base.MvpView;
import com.users.razvan.users.model.UserGeoPosition;

import rx.Observable;

public interface MvpDetailsActivity extends MvpView {

    Observable onMapReadyPublisher();

    Observable onWebsiteClickPublisher();

    Observable onPhoneNumberClick();

    void setUserLocation(UserGeoPosition userPosition, String userName);

    void setUserInitials(String userInitials);

    void setUserFullName(String userFullName);

    void setUserEmail(String userEmail);

    void setUserPhoneNumber(String phoneNumber);

    void setUserAddress(String userAddress);

    void setUserWebsite(String userWebsite);
}
