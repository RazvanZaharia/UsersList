package com.users.razvan.users.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.users.razvan.users.R;
import com.users.razvan.users.UsersApplication;
import com.users.razvan.users.adapter.RvAdapterUsers;
import com.users.razvan.users.injection.component.ActivityComponent;
import com.users.razvan.users.injection.component.ConfigPersistentComponent;
import com.users.razvan.users.injection.component.DaggerConfigPersistentComponent;
import com.users.razvan.users.injection.module.ActivityModule;
import com.users.razvan.users.model.UserModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity implements MvpViewMain {

    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final Map<Long, ConfigPersistentComponent> sComponentsMap = new HashMap<>();

    private long activityId;
    private ActivityComponent activityComponent;

    @Inject
    PresenterMain mPresenter;

    private RvAdapterUsers mRvAdapterUsers;
    @BindView(R.id.rv_users)
    RecyclerView mRvUsers;

    private PublishSubject<UserModel> mUserClickedPublisher = PublishSubject.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupDependencies(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupRecyclerView();
        init();
    }

    private void init() {
        mPresenter.attachView(this);
        mPresenter.init();
    }

    // Setup

    private void setupRecyclerView() {
        mRvAdapterUsers = new RvAdapterUsers(this, mUserClickedPublisher);
        mRvUsers.setLayoutManager(new LinearLayoutManager(this));
        mRvUsers.setAdapter(mRvAdapterUsers);
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

    // MvpView
    @Override
    public void showData(@NonNull List<UserModel> userModels) {
        mRvAdapterUsers.setDataSet(userModels);
        mRvAdapterUsers.notifyDataSetChanged();
    }

    @Override
    public Observable<UserModel> getClickedUserPublisher() {
        return mUserClickedPublisher;
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
