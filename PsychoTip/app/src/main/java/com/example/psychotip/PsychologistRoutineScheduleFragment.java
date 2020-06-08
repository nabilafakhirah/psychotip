package com.example.psychotip;

import android.app.TimePickerDialog;
import android.content.Context;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PsychologistHomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PsychologistHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PsychologistRoutineScheduleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String param1;
    private String param2;

    private OnFragmentInteractionListener interactionListener;

    private Spinner chooseDay;
    private EditText timeView;
    private EditText chooseStartTime;
    private EditText chooseEndTime;
    private String timePeriod;

    private Api createSchedule;

    private GetUserResponse user;
    private String usernamePsikolog;

    private String chosenDay;
    private String chosenStartTime;
    private String chosenEndTime;

    private int currSchedule;
    private int startMinute;
    private int endMinute;

    SessionManager session;
    Api userApi;

    public PsychologistRoutineScheduleFragment() {
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
    public static PsychologistRoutineScheduleFragment newInstance(String param1, String param2) {
        PsychologistRoutineScheduleFragment fragment = new PsychologistRoutineScheduleFragment();
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

        View view = inflater.inflate(R.layout.fragment_psychologist_set_routine_schedule,
            container, false);

        chooseDay = (Spinner) view.findViewById(R.id.set_routine_day);
        chooseStartTime = view.findViewById(R.id.set_routine_start_time);
        chooseEndTime = view.findViewById(R.id.set_routine_end_time);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);*/

        /*TextView title = (TextView)findViewById(R.id.toolbar_title);
        title.setText("Jadwal Praktek");
        title.setTextColor(getResources().getColor(R.color.colorBlack));*/

        String[] days = new String[]{
            "Hari Praktek", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"};

        createSpinner(view, R.id.set_routine_day, days);

        chooseStartTime.setInputType(InputType.TYPE_NULL);
        chooseStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeView = chooseStartTime;
                createTime();
            }
        });

        chooseEndTime.setInputType(InputType.TYPE_NULL);
        chooseEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeView = chooseEndTime;
                createTime();
            }
        });

        session = new SessionManager(getActivity().getApplicationContext());
        final String loggedInUser = session.getUserDetails().get("username");

        userApi = UtilsApi.getApiService();
        Call<GetUserResponse> call = userApi.getUser("api/user_view/" + loggedInUser);
        call.enqueue(new Callback<GetUserResponse>() {
            @Override
            public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                user = response.body();
                usernamePsikolog = user.username;
                System.out.println(usernamePsikolog);
            }

            @Override
            public void onFailure(Call<GetUserResponse> call, Throwable t) {
                call.cancel();
            }
        });

        Button sendButton = (Button) view.findViewById(R.id.set_routine_send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chosenDay = chooseDay.getSelectedItem().toString();
                chosenStartTime = chooseStartTime.getText().toString();
                chosenEndTime = chooseEndTime.getText().toString();

                if (!validateField(chooseDay.getSelectedItemPosition(),
                        chosenStartTime, chosenEndTime)) {
                    showErrorMessageIfValueEmpty();
                } else {
                    if (!validateTime(chosenStartTime, chosenEndTime)) {
                        showErrorMessageIfTimeInvalid();
                    } else {
                        Collection<Map<String, String>> maps = new ArrayList<Map<String, String>>();
                        for (int n = 1; n <= currSchedule; n++) {
                            int minute = 90;
                            int currStartMinute = startMinute;
                            int currEndMinute = startMinute + minute;
                            startMinute = currEndMinute;

                            System.out.println(n + " adalah " + currStartMinute
                                    + " sampai " + currEndMinute);

                            chosenStartTime = convertIntegertoTimeString(currStartMinute);
                            chosenEndTime = convertIntegertoTimeString(currEndMinute);

                            Map<String, String> fields = new HashMap<>();
                            fields.put("psikolog", usernamePsikolog);
                            fields.put("hari", chosenDay);
                            fields.put("jam_kosong_start", chosenStartTime);
                            fields.put("jam_kosong_end", chosenEndTime);

                            System.out.println(fields);

                            maps.add(fields);
                        }
                        createSchedule = UtilsApi.getApiService();
                        for (int i = 0; i < maps.size(); i++) {
                            Call<CreateScheduleResponse> call = createSchedule
                                    .createPsychologistSchedule(
                                            ((ArrayList<Map<String, String>>) maps).get(i));
                            System.out.println("masukkk sini");
                            call.enqueue(new Callback<CreateScheduleResponse>() {
                                @Override
                                public void onResponse(Call<CreateScheduleResponse> call,
                                                       Response<CreateScheduleResponse> response) {
                                    System.out.println("masukkk lagii lagii");
                                    if (response.isSuccessful()) {
                                        System.out.println("masukkk lagii lagii lagii");
                                        Toast.makeText(getActivity().getApplicationContext(),
                                                "Jadwal berhasil dibuat", Toast.LENGTH_SHORT)
                                                .show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<CreateScheduleResponse> call,
                                                      Throwable t) {
                                    Log.d("ERROR", "the post process failed");
                                    call.cancel();
                                }
                            });
                        }
                        Fragment fragment = new PsychologistHomeFragment();
                        replaceFragment(fragment);
                    }
                }
            }
        });
        return view;
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_psychologist, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (interactionListener != null) {
            interactionListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            interactionListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
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

    public void createTime() {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                    timeView.setText(String.format("%02d:%02d", hourOfDay, minutes));
                }
            }, currentHour, currentMinute, false);

        timePickerDialog.show();
    }

    public void createSpinner(View view, int resource, String[] spinnerItem) {
        // Get reference of widgets from XML layout
        final Spinner spinner = (Spinner) view.findViewById(resource);

        // Initializing a String Array
        final List<String> spinnerItemList = new ArrayList<>(Arrays.asList(spinnerItem));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getActivity(), R.layout.spinner_item_color_primary, spinnerItemList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);

                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item_color_primary);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {
                    // Notify the selected item text
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Selected : " + selectedItemText,
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public boolean validateTime(String startTime, String endTime) {

        int[] start = convertTimeStringtoInteger(startTime);
        int[] end = convertTimeStringtoInteger(endTime);

        startMinute = convertTimeIntegertoMinute(start);
        endMinute = convertTimeIntegertoMinute(end);
        int duration = endMinute - startMinute;

        if (duration < 90) {
            return false;
        } else {
            currSchedule = duration / 90;
            return true;
        }
    }

    public int convertTimeIntegertoMinute(int[] time) {
        int minute = time[0] * 60 + time[1];
        return minute;
    }

    public boolean validateField(int chooseDay, String chosenStartTime, String chosenEndTime) {
        if (chooseDay == 0) {
            return false;
        } else if (chosenStartTime.isEmpty()) {
            return false;
        } else if (chosenEndTime.isEmpty()) {
            return false;
        }
        return true;
    }

    public int[] convertTimeStringtoInteger(String time) {
        String[] timeNumberSplit = time.split(":");

        int hour = Integer.parseInt(timeNumberSplit[0]);
        int minute = Integer.parseInt(timeNumberSplit[1]);

        int[] timeArray = {hour, minute};

        return timeArray;
    }

    public String convertIntegertoTimeString(int minute) {
        int hourTime = minute / 60;
        int minuteTime = minute % 60;
        if (minuteTime == 0) {
            return hourTime + ":00";
        }
        return hourTime + ":" + minuteTime;
    }

    public void showErrorMessageIfValueEmpty() {
        Toast.makeText(getActivity().getApplicationContext(), "Tolong isi seluruh form!",
                Toast.LENGTH_SHORT).show();
    }

    public void showErrorMessageIfTimeInvalid() {
        Toast.makeText(getActivity().getApplicationContext(), "Rentang waktu kurang dari 90 menit!",
                Toast.LENGTH_SHORT).show();
    }
}
