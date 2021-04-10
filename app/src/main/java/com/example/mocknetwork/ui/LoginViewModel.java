package com.example.mocknetwork.ui;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mocknetwork.R;
import com.example.mocknetwork.model.UserInfo;
import com.example.mocknetwork.model.UserInfoResponse;
import com.example.mocknetwork.service.MockLoginRepository;
import com.example.mocknetwork.util.ValidationUtil;

public class LoginViewModel extends AndroidViewModel {
    private final MockLoginRepository mockLoginRepository;
    private int userNameErrorMessageId;
    private int passwordErrorMessageId;
    private final MutableLiveData<Boolean> isPasswordValidObservable = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isUserNameValidObservable = new MutableLiveData<>();


    public LoginViewModel(@NonNull Application application) {
        super(application);
        mockLoginRepository = MockLoginRepository.Companion.getInstance(application);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        public Factory(@NonNull Application application) {
            this.application = application;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new LoginViewModel(application);
        }
    }

    public MutableLiveData<Boolean> getIsUserNameValidObservable() {
        return isUserNameValidObservable;
    }

    public MutableLiveData<Boolean> getIsPasswordValidObservable() {
        return isPasswordValidObservable;
    }

    public MutableLiveData<UserInfoResponse> getUserResponseObservable() {
        return mockLoginRepository.getUserInfoLiveData();
    }

    public int getUserNameErrorMessageId() {
        return userNameErrorMessageId;
    }

    public int getPasswordErrorMessageId() {
        return passwordErrorMessageId;
    }

    public void initObservableVariables() {
        isUserNameValidObservable.setValue(true);
        isPasswordValidObservable.setValue(true);
    }

    public void validateLoginUsername(String input) {
        final int resourceId;
        final boolean isValid;
        if (input.length() == 0) {
            resourceId = R.string.accountLogin_username_errorEmptyUnselected;
            isValid = false;
        } else {
            isValid = ValidationUtil.isValidUsernameAndPassword(input.toLowerCase());
            resourceId = isValid ? 0 : R.string.accountLogin_username_errorFormatUnselected;
        }
        updateLoginUserIdValidation(resourceId, isValid);
    }

    public void validatePassword(String password) {
        final boolean isValid = ValidationUtil.isValidUsernameAndPassword(password);
        passwordErrorMessageId = isValid ? 0 : R.string.accountLogin_password_errorEmptyUnselected;
        isPasswordValidObservable.setValue(isValid);
    }

    private void updateLoginUserIdValidation(int resourceId, boolean isValid) {
        userNameErrorMessageId = resourceId;
        isUserNameValidObservable.setValue(isValid);
    }

    public void performLogin(Context context, UserInfo userInfo) {
        mockLoginRepository.fetchUserInfo(context, userInfo);
    }
}
