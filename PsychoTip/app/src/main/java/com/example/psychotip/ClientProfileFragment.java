package com.example.psychotip;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClientProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClientProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientProfileFragment extends Fragment {
    private Button logoutButton;
    private ImageButton settingButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String param1;
    private String param2;

    private GetUserResponse user;
    private Api userApi;

    SessionManager session;

    private TextView username;
    private TextView name;
    private TextView birthdate;
    private TextView gender;
    private TextView address;
    private TextView email;

    private OnFragmentInteractionListener listener;

    public ClientProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClientProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientProfileFragment newInstance(String param1, String param2) {
        ClientProfileFragment fragment = new ClientProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            param1 = getArguments().getString(ARG_PARAM1);
            param2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        session = new SessionManager(getActivity().getApplicationContext());
        Toast.makeText(getActivity().getApplicationContext(),"User Login Status: "
                + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_client_profile, container, false);

        Button editProfileButton = view.findViewById(R.id.editProfileButton);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ClientEditProfile.class);
                startActivity(intent);
            }
        });

        logoutButton = view.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //flush session
                session.logoutUser();

                Intent intent = new Intent(getActivity(), LandingPage.class);
                startActivity(intent);
            }
        });

        settingButton = view.findViewById(R.id.setting_button);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        username = getView().findViewById(R.id.username);
        name = getView().findViewById(R.id.name);
        birthdate = getView().findViewById(R.id.birthdate);
        gender = getView().findViewById(R.id.gender);
        address = getView().findViewById(R.id.address);
        email = getView().findViewById(R.id.email);

        session = new SessionManager(getActivity().getApplicationContext());
        final String loggedInUser = session.getUserDetails().get("username");

        userApi = UtilsApi.getApiService();

        Call<GetUserResponse> call = userApi.getUser("api/user_view/" + loggedInUser);

        call.enqueue(new Callback<GetUserResponse>() {
            @Override
            public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                user = response.body();

                name.setText(user.name);
                username.setText(user.username);
                birthdate.setText(user.birthday);
                gender.setText(user.gender);
                address.setText(user.address);
                email.setText(user.email);


            }

            @Override
            public void onFailure(Call<GetUserResponse> call, Throwable t) {
                call.cancel();
            }
        });

    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (listener != null) {
            listener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
