package com.example.psychotip.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.psychotip.Api;
import com.example.psychotip.ClientDashboard;
import com.example.psychotip.GetUserResponse;
import com.example.psychotip.LoginResponse;
import com.example.psychotip.PsychologistDashboard;
import com.example.psychotip.R;
import com.example.psychotip.SessionManager;
import com.example.psychotip.User;
import com.example.psychotip.UtilsApi;
import com.example.psychotip.presenter.LoginPresenter;
import com.example.psychotip.view.LoginView;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity implements LoginView {
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnMasuk;
    private ProgressDialog loading;
    private LoginPresenter loginPresenter;

    SessionManager session;

    private Context context;
    private Api loginApi;
    private Api userApi;
    private LoginResponse loginResponse;

    private final String[] status = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        // Session Manager
        session = new SessionManager(getApplicationContext());

        Toast.makeText(getApplicationContext(), "User Login Status: "
                + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        context = this;
        loginApi = UtilsApi.getApiService();

        initializePresenter();
        initializeViews();
    }


    private void initializePresenter() {
        loginPresenter = new LoginPresenter(this,
                this);
    }

    private void initializeViews() {
        // Username, Password input text
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        loginPresenter.edtUsername = edtUsername;
        loginPresenter.edtPassword = edtPassword;

        // Login button
        btnMasuk = (Button) findViewById(R.id.btnMasuk);

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = edtUsername.getText().toString();
                final String password = edtPassword.getText().toString();

                // loginApi = APIClass.getAPI().create(Api.class);

                Map<String, String> fields = new HashMap<>();
                fields.put("username", username);
                fields.put("password", password);

                if (loginPresenter.requestLogin(username, password)) {

                    loading = ProgressDialog.show(context, null,
                            "Harap tunggu...", true, false);

                    Call<LoginResponse> call = loginApi.userLogin(fields);

                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call,
                                               Response<LoginResponse> response) {
                            loginResponse = response.body();

                            System.out.println(loginResponse);

                            status[0] = loginResponse.validation;
                            System.out.println("ini status " + status[0]);

                            if ("True".equals(status[0])) {

                                session.createLoginSession(username, password);

                                userApi = UtilsApi.getApiService();
                                String loggedInUser = session.getUserDetails().get("username");

                                final Call<GetUserResponse> retrieveUser = userApi.getUser(
                                        "api/user_view/" + loggedInUser);

                                retrieveUser.enqueue(new Callback<GetUserResponse>() {
                                    @Override
                                    public void onResponse(Call<GetUserResponse> call,
                                                           Response<GetUserResponse> response) {
                                        GetUserResponse user = response.body();

                                        if (user.role.equalsIgnoreCase("klien")) {
                                            Intent i = new Intent(getApplicationContext(),
                                                    ClientDashboard.class);
                                            startActivity(i);
                                        } else {
                                            Intent i = new Intent(getApplicationContext(),
                                                    PsychologistDashboard.class);
                                            startActivity(i);
                                        }
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Call<GetUserResponse> call, Throwable t) {
                                        retrieveUser.cancel();
                                    }
                                });

                                loading.dismiss();
                            } else {
                                showErrorMessageForUsernamePassword();
                                loading.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            call.cancel();
                        }
                    });

                }
                /*else{
                    showErrorMessageIfEmpty();
                }*/
            }
        });
    }

    @Override
    public void showErrorMessageForUsernamePassword() {
        Snackbar.make(btnMasuk, "Pengguna tidak dikenali. "
                        + "Silakan periksa kembali username dan password anda.",
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showErrorMessageIfEmpty() {
        edtUsername.setError("Username tidak boleh kosong");
        edtPassword.setError("Password tidak boleh kosong");
    }

    @Override
    public void showErrorMessageIfUsernameEmpty() {
        edtUsername.setError("Masukkan username anda");
        edtUsername.requestFocus();
    }

    @Override
    public void showErrorMessageIfPasswordEmpty() {
        edtPassword.setError("Masukkan password anda");
        edtPassword.requestFocus();
    }

}
