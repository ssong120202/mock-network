package com.example.mocknetwork.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mocknetwork.R;
import com.example.mocknetwork.accessibility.AccessibilityTextInputLayout;
import com.example.mocknetwork.custom.CustomEditTextWatcher;
import com.example.mocknetwork.model.UserInfo;
import com.example.mocknetwork.util.NavigationUtil;
import com.example.mocknetwork.util.WebUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Optional;

import static com.example.mocknetwork.custom.CustomEditTextWatcher.LOGIN_USER_NAME_FIELD;
import static com.example.mocknetwork.custom.CustomEditTextWatcher.LOGIN_PASSWORD_FIELD;

public class LoginFragment extends Fragment implements View.OnFocusChangeListener {
    private LoginViewModel viewModel;
    private TextInputEditText loginUerNameEditText, loginPasswordEditText;
    private Button loginButton;
    private ImageButton loginClearButton;
    private ConstraintLayout loginButtonLoadingView;
    private AccessibilityTextInputLayout loginUserNameLayout, loginPasswordLayout;
    private ImageView loginUerNameErrorImageView, loginPasswordErrorImageView;
    private View.OnClickListener loginButtonListener = view -> validateAndPerformLogin();
    private UserInfo credentials = new UserInfo("", "");

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    private final TextWatcher loginUserNameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
            setLoginClearButtonVisibility(charSequence.length() > 0);
        }
    };

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        final TextInputEditText editText = (TextInputEditText) v;
        final Editable editableText = editText.getEditableText();
        final int length = editableText.length();
        if (v.getId() == R.id.account_login_user_name_edit_text)
            setLoginClearButtonVisibility(hasFocus && length > 0);
        if (hasFocus) {
            editText.setSelection(length);
        } else if (length != 0) {
            switch (v.getId()) {
                case R.id.account_login_user_name_edit_text:
                    viewModel.validateLoginUsername(loginUerNameEditText.getEditableText().toString().trim());
                    break;
                case R.id.account_login_password_edit_text:
                    viewModel.validatePassword(loginPasswordEditText.getEditableText().toString().trim());
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final FragmentActivity activity = getActivity();
        if (activity != null) {
            viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUiForLoginPage(view);
        observeViewModel();
    }

    @Override
    public void onResume() {
        super.onResume();
        hideKeyboard();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideKeyboard();
        viewModel.initObservableVariables();
    }

    void setupUiForLoginPage(View view) {
        loginButton = view.findViewById(R.id.account_login_login_button);
        loginClearButton = view.findViewById(R.id.account_login_clear_button);
        loginButtonLoadingView = view.findViewById(R.id.account_login_button_loading_view);
        loginPasswordEditText = view.findViewById(R.id.account_login_password_edit_text);
        loginUserNameLayout = view.findViewById(R.id.account_login_user_name_layout);
        loginPasswordLayout = view.findViewById(R.id.account_login_password_layout);
        loginUerNameEditText = view.findViewById(R.id.account_login_user_name_edit_text);
        loginUerNameErrorImageView = view.findViewById(R.id.account_login_user_name_error_image_view);
        loginPasswordErrorImageView = view.findViewById(R.id.account_login_password_error_image_view);
        TextView createAccount = view.findViewById(R.id.account_login_create_account_text_view);

        final ImageView loginLoadingImageView = loginButtonLoadingView.findViewById(R.id.account_login_button_loading_view_progress_bar);
        final TextView forgotPassword = view.findViewById(R.id.account_login_forgot_password_view);

        loginUerNameEditText.setText(credentials.getUserName());
        loginUerNameEditText.setOnFocusChangeListener(this);
        CustomEditTextWatcher userNameWatcher = new CustomEditTextWatcher(viewModel,
                loginUerNameEditText,
                viewModel.getIsUserNameValidObservable(),
                LOGIN_USER_NAME_FIELD);
        loginUerNameEditText.addTextChangedListener(userNameWatcher);
        loginUerNameEditText.addTextChangedListener(loginUserNameTextWatcher);
        CustomEditTextWatcher passwordWatcher = new CustomEditTextWatcher(viewModel,
                loginPasswordEditText,
                viewModel.getIsPasswordValidObservable(),
                LOGIN_PASSWORD_FIELD);
        loginPasswordEditText.addTextChangedListener(passwordWatcher);
        loginPasswordEditText.setText(credentials.getPassword());
        loginPasswordEditText.setOnFocusChangeListener(this);

        loginClearButton.setOnClickListener(v -> loginUerNameEditText.setText(""));
        loginButton.setOnClickListener(loginButtonListener);
        createAccount.setOnClickListener(v -> openCreateAccountLink());
        forgotPassword.setOnClickListener(v -> openForgotOrResetPasswordWebView());
        loginLoadingImageView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.image_spinner));
    }

    private void observeViewModel() {
        viewModel.getIsUserNameValidObservable().observe(getViewLifecycleOwner(), isValid ->
                toggleInputFieldErrorLoginLandingPage(loginUserNameLayout,
                        isValid,
                        viewModel.getUserNameErrorMessageId(),
                        loginUerNameErrorImageView));
        viewModel.getIsPasswordValidObservable().observe(getViewLifecycleOwner(), isValid ->
                toggleInputFieldErrorLoginLandingPage(loginPasswordLayout,
                        isValid,
                        viewModel.getPasswordErrorMessageId(),
                        loginPasswordErrorImageView));

        viewModel.getUserResponseObservable().observe(getViewLifecycleOwner(), userInfoResponse -> {
                    stopLoginButtonLoadingAnimation();
                    NavigationUtil.getInstance().pushOrPopFragment(AccountFragment.Companion.newInstance(userInfoResponse.getUserName()), 0, true);
                }
        );
    }


    private void validateAndPerformLogin() {
        // Validating login fields
        final String userName = loginUerNameEditText.getEditableText().toString().trim();
        final String password = loginPasswordEditText.getEditableText().toString().trim();
        viewModel.validateLoginUsername(userName);
        viewModel.validatePassword(password);
        final boolean validUserName = Optional.ofNullable(viewModel.getIsUserNameValidObservable().getValue()).orElse(false);
        final boolean validPassword = Optional.ofNullable(viewModel.getIsPasswordValidObservable().getValue()).orElse(false);

        if (!validUserName) {
            loginUerNameEditText.requestFocus();
            showKeyboard(loginUerNameEditText);
        } else if (!validPassword) {
            loginPasswordEditText.requestFocus();
            showKeyboard(loginPasswordEditText);
        }

        if (validUserName && validPassword) {
            startLoginButtonLoadingAnimation();
            credentials = new UserInfo(userName, password);
            viewModel.performLogin(getContext(), credentials);
        }
    }

    private void toggleInputFieldErrorLoginLandingPage(TextInputLayout layout, Boolean isValid, int errorMessageId, ImageView errorIcon) {
        if (isValid == null) {
            return;
        }

        final EditText editText = layout.getEditText();
        final Context context = getContext();

        errorIcon.setVisibility(isValid ? View.INVISIBLE : View.VISIBLE);
        layout.setErrorEnabled(!isValid);
        layout.setError(isValid ? null : getResources().getText(errorMessageId));

        if (isValid && editText != null && context != null) {
            editText.setTextColor(context.getColor(R.color.subTextLabels));
        }
    }

    private void startLoginButtonLoadingAnimation() {
        loginButton.setVisibility(View.GONE);
        loginButtonLoadingView.setVisibility(View.VISIBLE);
        loginButton.setEnabled(false);
    }

    private void stopLoginButtonLoadingAnimation() {
        loginButtonLoadingView.setVisibility(View.GONE);
        loginButton.setVisibility(View.VISIBLE);
        loginButton.setEnabled(true);
    }

    private void setLoginClearButtonVisibility(boolean isVisible) {
        loginClearButton.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
    }

    private void showKeyboard(TextInputEditText inputEditText) {
        Optional.ofNullable(this.getActivity()).flatMap(
                activity -> Optional.ofNullable((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)))
                .ifPresent(systemService -> systemService.showSoftInput(inputEditText, InputMethodManager.SHOW_FORCED));
    }

    private void hideKeyboard() {
        if (getActivity() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null && getActivity().getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    //Mock urls
    private void openCreateAccountLink() {
        final String url = "https://www.google.com";
        WebUtil.INSTANCE.openUrlInApp(getContext(), url);
    }

    private void openForgotOrResetPasswordWebView() {
        final String url = "https://www.google.com";
        WebUtil.INSTANCE.openUrlInApp(getContext(), url);
    }
}
