package com.users.razvan.users.ui.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jakewharton.rxbinding.view.RxView;
import com.users.razvan.users.R;
import com.users.razvan.users.UsersApplication;
import com.users.razvan.users.injection.component.ActivityComponent;
import com.users.razvan.users.injection.component.ConfigPersistentComponent;
import com.users.razvan.users.injection.component.DaggerConfigPersistentComponent;
import com.users.razvan.users.injection.module.ActivityModule;
import com.users.razvan.users.model.UserGeoPosition;
import com.users.razvan.users.model.UserModel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.subjects.PublishSubject;

public class UserDetailsActivity extends AppCompatActivity implements MvpDetailsActivity, OnMapReadyCallback {

    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final String EXTRA_USER = "extraUser";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final Map<Long, ConfigPersistentComponent> sComponentsMap = new HashMap<>();

    private long activityId;
    private ActivityComponent activityComponent;

    @Inject
    PresenterDetailsActivity mPresenter;

    @BindView(R.id.tv_user_initials)
    TextView mTvUserInitials;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_user_email)
    TextView mTvEmail;
    @BindView(R.id.tv_phone_number)
    TextView mTvPhoneNumber;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_website)
    TextView mTvWebsite;

    private GoogleMap mMap;
    private PublishSubject mMapReadyPublisher = PublishSubject.create();

    public static void launch(@NonNull Context context, @NonNull UserModel userModel) {
        Intent intent = new Intent(context, UserDetailsActivity.class);
        intent.putExtra(EXTRA_USER, userModel);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupDependencies(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        mPresenter.attachView(this);
        mPresenter.init((UserModel) getIntent().getSerializableExtra(EXTRA_USER));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setupDependencies(Bundle savedInstanceState) {
        activityId = savedInstanceState != null ? savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();
        ConfigPersistentComponent configPersistentComponent;
        if (!sComponentsMap.containsKey(activityId)) {
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .applicationComponent(((UsersApplication) getApplicationContext()).getComponent())
                    .build();
            sComponentsMap.put(activityId, configPersistentComponent);
        } else {
            configPersistentComponent = sComponentsMap.get(activityId);
        }
        activityComponent = configPersistentComponent.activityComponent(new ActivityModule(this));
        activityComponent.inject(this);
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMapReadyPublisher.onNext(null);
    }

    @Override
    public Observable onMapReadyPublisher() {
        return mMapReadyPublisher;
    }

    @Override
    public Observable onWebsiteClickPublisher() {
        return RxView.clicks(mTvWebsite);
    }

    @Override
    public Observable onPhoneNumberClick() {
        return RxView.clicks(mTvPhoneNumber);
    }

    @Override
    public void setUserLocation(UserGeoPosition userPosition, String userName) {
        LatLng userLocation = new LatLng(Double.parseDouble(userPosition.getLat()), Double.parseDouble(userPosition.getLng()));
        MarkerOptions userMarkerOptions = new MarkerOptions().position(userLocation).title(userName);
        Marker userMarker = mMap.addMarker(userMarkerOptions);
        userMarker.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
    }

    @Override
    public void setUserFullName(String userFullName) {
        mTvUserName.setText(userFullName);
    }

    @Override
    public void setUserEmail(String userEmail) {
        mTvEmail.setText(userEmail);
    }

    @Override
    public void setUserInitials(String userInitials) {
        mTvUserInitials.setText(userInitials);
    }

    @Override
    public void setUserPhoneNumber(String phoneNumber) {
        final SpannableString spannableString = new SpannableString(phoneNumber);
        spannableString.setSpan(new URLSpan(""), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvPhoneNumber.setText(spannableString, TextView.BufferType.SPANNABLE);
    }

    @Override
    public void setUserAddress(String userAddress) {
        mTvAddress.setText(userAddress);
    }

    @Override
    public void setUserWebsite(String userWebsite) {
        final SpannableString spannableString = new SpannableString(userWebsite);
        spannableString.setSpan(new URLSpan(""), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvWebsite.setText(spannableString, TextView.BufferType.SPANNABLE);
    }
}
