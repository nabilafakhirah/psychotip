package com.example.psychotip.presenter;

import android.widget.EditText;

import com.example.psychotip.model.LoginPage;
import com.example.psychotip.view.LoginView;

public class LoginPresenter {
    private final LoginPage loginPage;
    private final LoginView loginView;

    public EditText edtPassword;
    public EditText edtUsername;

    public LoginPresenter(LoginView loginView, LoginPage loginPage) {
        this.loginPage = loginPage;
        this.loginView = loginView;
    }

    public boolean requestLogin(String username, String password) {

        if ("".equals(username) && "".equals(password)) {
            loginView.showErrorMessageIfEmpty();
            return false;
        } else if ("".equals(password)) {
            loginView.showErrorMessageIfPasswordEmpty();
            return false;
        } else if ("".equals(username)) {
            loginView.showErrorMessageIfUsernameEmpty();
            return false;
        }
        return true;
    }
}
