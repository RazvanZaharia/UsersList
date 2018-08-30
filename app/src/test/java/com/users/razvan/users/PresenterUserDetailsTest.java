package com.users.razvan.users;

import android.content.Context;
import android.util.Log;

import com.users.razvan.users.model.AddressModel;
import com.users.razvan.users.model.UserGeoPosition;
import com.users.razvan.users.model.UserModel;
import com.users.razvan.users.ui.details.MvpDetailsActivity;
import com.users.razvan.users.ui.details.PresenterDetailsActivity;
import com.users.razvan.users.utils.Utils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import rx.subjects.PublishSubject;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class, Utils.class})
public class PresenterUserDetailsTest {

    @Rule
    public RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Mock
    MvpDetailsActivity view;
    @Mock
    Context mContext;

    PresenterDetailsActivity mPresenter;

    UserModel testUser;

    PublishSubject onMapReadyPublisher = PublishSubject.create();
    PublishSubject onWebsiteClickPublisher = PublishSubject.create();
    PublishSubject onPhoneNumberPublisher = PublishSubject.create();

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(Log.class);
        PowerMockito.mockStatic(Utils.class);

        PowerMockito.when(view.getViewContext()).thenReturn(mContext);
        PowerMockito.when(view.onMapReadyPublisher()).thenReturn(onMapReadyPublisher);
        PowerMockito.when(view.onWebsiteClickPublisher()).thenReturn(onWebsiteClickPublisher);
        PowerMockito.when(view.onPhoneNumberClick()).thenReturn(onPhoneNumberPublisher);

        testUser = getUser(1);

        mPresenter = new PresenterDetailsActivity();
        mPresenter.attachView(view);
        mPresenter.init(testUser);
    }

    @Test
    public void setUserData() {
        Mockito.verify(view).setUserFullName(Mockito.eq(testUser.getName()));
        Mockito.verify(view).setUserEmail(Mockito.eq(testUser.getEmail()));
        Mockito.verify(view).setUserPhoneNumber(Mockito.eq(testUser.getPhone()));
        Mockito.verify(view).setUserAddress(Mockito.eq("Suite, Street, City"));
        Mockito.verify(view).setUserWebsite(Mockito.eq("http://website.com"));
    }

    @Test
    public void setUserLocation() {
        onMapReadyPublisher.onNext(null);

        Mockito.verify(view).setUserLocation(Mockito.eq(testUser.getAddress().getGeo()), Mockito.eq(testUser.getName()));
    }

    private UserModel getUser(int id) {
        UserGeoPosition geoPosition = new UserGeoPosition();
        geoPosition.setLat("51.517183");
        geoPosition.setLng("-0.078595");

        AddressModel addressModel = new AddressModel();
        addressModel.setSuite("Suite");
        addressModel.setStreet("Street");
        addressModel.setCity("City");
        addressModel.setGeo(geoPosition);

        UserModel user = new UserModel();
        user.setId(id);
        user.setName("Test Name" + id);
        user.setEmail("test@email.com");
        user.setPhone("077859283754" + id);
        user.setAddress(addressModel);
        user.setWebsite("http://website.com");

        return user;
    }
}
