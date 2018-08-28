package com.users.razvan.users.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.users.razvan.users.R;
import com.users.razvan.users.model.UserModel;
import com.users.razvan.users.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.subjects.PublishSubject;

public class RvAdapterUsers extends RecyclerView.Adapter<RvAdapterUsers.UserViewHolder> {

    private Context mContext;
    private List<UserModel> mDataSet;
    private PublishSubject<UserModel> mUserClickedPublisher;

    public RvAdapterUsers(Context context,
                          @NonNull PublishSubject<UserModel> userClickedPublisher) {
        mContext = context;
        mUserClickedPublisher = userClickedPublisher;
        mDataSet = new ArrayList<>();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_user, parent, false),
                mUserClickedPublisher);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(mDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataSet != null ? mDataSet.size() : 0;
    }

    public void setDataSet(@NonNull List<UserModel> dataSet) {
        mDataSet.clear();
        mDataSet.addAll(dataSet);
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "UserViewHolder";

        @BindView(R.id.tv_user_initials)
        TextView mTvInitials;

        @BindView(R.id.tv_user_name)
        TextView mTvUserName;

        @BindView(R.id.tv_user_email)
        TextView mTvUserEmail;

        private PublishSubject<UserModel> mUserClickedPublisher;

        UserViewHolder(View itemView,
                       @NonNull PublishSubject<UserModel> userClickedPublisher) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mUserClickedPublisher = userClickedPublisher;
        }

        void bind(final UserModel userModel) {
            itemView.setOnClickListener(view -> {
                if (mUserClickedPublisher != null) {
                    mUserClickedPublisher.onNext(userModel);
                }
            });

            mTvInitials.setText(Utils.getInitials(userModel.getName()));
            mTvUserName.setText(userModel.getName());
            mTvUserEmail.setText(userModel.getEmail());
        }
    }
}
