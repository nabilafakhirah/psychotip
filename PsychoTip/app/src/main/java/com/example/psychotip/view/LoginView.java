package com.example.psychotip.view;

public interface LoginView {
    void showErrorMessageForUsernamePassword();

    void showErrorMessageIfEmpty();

    void showErrorMessageIfUsernameEmpty();

    void showErrorMessageIfPasswordEmpty();
}
