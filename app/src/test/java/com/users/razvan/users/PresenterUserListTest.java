package com.users.razvan.users;

import android.content.Context;
import android.util.Log;

import com.users.razvan.users.model.UserModel;
import com.users.razvan.users.retrofit.UsersService;
import com.users.razvan.users.ui.details.UserDetailsActivity;
import com.users.razvan.users.ui.main.MvpViewMain;
import com.users.razvan.users.ui.main.PresenterMain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import rx.subjects.PublishSubject;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UserDetailsActivity.class, Log.class})
public class PresenterUserListTest {

    @Rule
    public RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Mock
    MvpViewMain view;
    @Mock
    UsersService service;
    @Mock
    Context mContext;

    PresenterMain mPresenterMain;
    PublishSubject<UserModel> onUserClickedPublisher = PublishSubject.create();
    PublishSubject<List<UserModel>> usersListPublisher = PublishSubject.create();

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(UserDetailsActivity.class);
        PowerMockito.mockStatic(Log.class);

        PowerMockito.when(view.getClickedUserPublisher()).thenReturn(onUserClickedPublisher);
        PowerMockito.when(view.getViewContext()).thenReturn(mContext);
        PowerMockito.when(service.getUsers()).thenReturn(usersListPublisher);

        mPresenterMain = new PresenterMain(service);
        mPresenterMain.attachView(view);
        mPresenterMain.init();
    }

    @Test
    public void loadUsers() {
        List<UserModel> usersList = getUsersList();
        usersListPublisher.onNext(usersList);

        Mockito.verify(view).showData(Mockito.eq(usersList));
    }

    @Test
    public void showUserDetails() {
        UserModel clickedUser = getUser(5);
        onUserClickedPublisher.onNext(clickedUser);

        PowerMockito.verifyStatic();
        UserDetailsActivity.launch(Mockito.eq(mContext), Mockito.eq(clickedUser));
    }

    private UserModel getUser(int id) {
        UserModel user = new UserModel();
        user.setId(id);
        user.setName("Test Name" + id);
        user.setEmail("test@email.com");
        return user;
    }

    private List<UserModel> getUsersList() {
        List<UserModel> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            userList.add(getUser(i));
        }
        return userList;
    }

}
