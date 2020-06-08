package com.example.psychotip;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClientHomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClientHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientHomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String param1;
    private String param2;

    private OnFragmentInteractionListener listener;

    SessionManager session;

    private GetQuoteOfTheDayResponse quote;
    private Api qotdApi;
    private TextView kontenQuote;
    private TextView authorQuote;
    private TextView clientName;


    public ClientHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClientHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientHomeFragment newInstance(String param1, String param2) {
        ClientHomeFragment fragment = new ClientHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("THIS IS HOME FRAGMENT");
        if (getArguments() != null) {
            param1 = getArguments().getString(ARG_PARAM1);
            param2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_client_home, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        //Toolbar toolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
        //activity.setSupportActionBar(toolbar);

        //ActionBar ab = activity.getSupportActionBar();
        //ab.setDisplayShowTitleEnabled(false);

        session = new SessionManager(getActivity().getApplicationContext());
        Toast.makeText(getActivity().getApplicationContext(),"User Login Status: "
                + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        session.checkLogin();

        HashMap<String, String> user = session.getUserDetails();
        String username = user.get(SessionManager.key_username);

        clientName = (TextView) view.findViewById(R.id.client_name);
        clientName.setText(username);

        kontenQuote = (TextView) view.findViewById(R.id.quote);
        authorQuote = (TextView) view.findViewById(R.id.author);

        qotdApi = ApiClass.getApi(UtilsApi.BASE_URL_API).create(Api.class);

        Call<GetQuoteOfTheDayResponse> call = qotdApi.getQotd();
        call.enqueue(new Callback<GetQuoteOfTheDayResponse>() {
            @Override
            public void onResponse(Call<GetQuoteOfTheDayResponse> call,
                                   Response<GetQuoteOfTheDayResponse> response) {
                quote = response.body();

                kontenQuote.setText(quote.konten);
                authorQuote.setText(quote.author);
                System.out.println(quote.konten);
            }

            @Override
            public void onFailure(Call<GetQuoteOfTheDayResponse> call, Throwable t) {
                call.cancel();
            }
        });

        /*Button openRoutine = view.findViewById(R.id.client_home_open_set_routine_button);
        openRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PsychologistSetRoutineSchedule.class);
                startActivity(intent);
            }
        });*/


        return view;
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
