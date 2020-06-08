package com.example.psychotip;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClientChatFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClientChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientCounselingCategoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String param1;
    private String param2;

    private OnFragmentInteractionListener listener;

    public ClientCounselingCategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientCounselingCategoryFragment newInstance(String param1, String param2) {
        ClientCounselingCategoryFragment fragment = new ClientCounselingCategoryFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_client_counseling_category, container, false);
        final Bundle bundle = getArguments();

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll);
        CardView card1 = (CardView) view.findViewById(R.id.psychologicalProblemsId1);
        CardView card2 = (CardView) view.findViewById(R.id.psychologicalProblemsId2);
        CardView card3 = (CardView) view.findViewById(R.id.psychologicalProblemsId3);
        CardView card4 = (CardView) view.findViewById(R.id.psychologicalProblemsId4);
        CardView card5 = (CardView) view.findViewById(R.id.psychologicalProblemsId5);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bundle == null) {
                    Fragment fragment = new ClientChatFragment();
                    replaceFragment(fragment);
                } else {
                    Intent intent = new Intent(getActivity(), ClientNewCounselingSchedule.class);
                    intent.putExtra("category", "Psikologi Klinis");
                    startActivity(intent);
                }


            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bundle == null) {
                    Fragment fragment = new ClientChatFragment();
                    replaceFragment(fragment);
                } else {
                    Intent intent = new Intent(getActivity(), ClientNewCounselingSchedule.class);
                    intent.putExtra("category", "Psikologi Pendidikan");
                    startActivity(intent);
                }


            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bundle == null) {
                    Fragment fragment = new ClientChatFragment();
                    replaceFragment(fragment);
                } else {
                    Intent intent = new Intent(getActivity(), ClientNewCounselingSchedule.class);
                    intent.putExtra("category", "Psikologi Industri & Organisasi");
                    startActivity(intent);
                }


            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bundle == null) {
                    Fragment fragment = new ClientChatFragment();
                    replaceFragment(fragment);
                } else {
                    Intent intent = new Intent(getActivity(), ClientNewCounselingSchedule.class);
                    intent.putExtra("category", "Psikologi Sosial");
                    startActivity(intent);
                }


            }
        });

        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bundle == null) {
                    Fragment fragment = new ClientChatFragment();
                    replaceFragment(fragment);
                } else {
                    Intent intent = new Intent(getActivity(), ClientNewCounselingSchedule.class);
                    intent.putExtra("category", "Psikologi Perkembangan");
                    startActivity(intent);
                }


            }
        });


        return view;
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_client, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
