package com.example.psychotip;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PsychologistDailyScheduleActivity extends AppCompatActivity {
    private ArrayList<PsychologistScheduleData> exampleList;

    private RecyclerView recyclerView;
    private PsychologistScheduleAdapter psychologistScheduleAdapter;
    private RecyclerView.LayoutManager layoutManager;

    Intent intent;
    TextView dateText;
    String dateStr;

    SessionManager session;
    String loggedInUser;

    Api deleteApi;
    private Api psikologApi;

    private List<GetPsychologistSchedule> psikologResponse;
    private String scheduleTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychologist_daily_schedule);

        intent = getIntent();
        dateText = findViewById(R.id.psychologist_daily_schedule_date_name);
        dateStr = getDateFromCalendar(intent);
        dateText.setText(dateStr);

        session = new SessionManager(getApplicationContext());
        loggedInUser = session.getUserDetails().get("username");

        if (exampleList != null) {
            exampleList.clear();
        } else {
            exampleList = new ArrayList<>();
        }

        showRoutineSchedule();
        //buildRecyclerView();

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPsychAddSchedule(dateStr);
            }
        });
    }

    public void removeItem(final int position) {
        String status = exampleList.get(position).getScheduleStatus();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (status.equalsIgnoreCase("NOT BOOKED")) {
            builder.setTitle("Hapus jadwal");
            builder.setMessage("Kamu akan menghapus jadwalmu. Apakah kamu yakin?");
            builder.setCancelable(false);
            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Map<String, String> fields = new HashMap<>();
                    fields.put("psikolog", loggedInUser);

                    String dayStr = dateStr.split(", ")[0];
                    fields.put("hari", dayStr);
                    Log.d("HARI JADWAL", dayStr);

                    String[] splitTimeStr = scheduleTime.split(
                            " "); //08:00 AM - 19:30 PM
                    fields.put("jam_kosong_start", splitTimeStr[0]);
                    fields.put("jam_kosong_end", splitTimeStr[2]);
                    Log.d("HARI JADWAL", splitTimeStr[0]);
                    Log.d("HARI JADWAL", splitTimeStr[2]);

                    deleteApi = UtilsApi.getApiService();
                    Call<CreateScheduleResponse> call =
                            deleteApi.deletePsychologistSchedule(fields);
                    call.enqueue(new Callback<CreateScheduleResponse>() {
                        @Override
                        public void onResponse(Call<CreateScheduleResponse> call,
                                               Response<CreateScheduleResponse> response) {
                            exampleList.remove(position);
                            psychologistScheduleAdapter.notifyItemRemoved(position);
                            Toast.makeText(getApplicationContext(),
                                    "Jadwal berhasil dihapus", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<CreateScheduleResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),
                                    "Jadwal gagal dihapus", Toast.LENGTH_SHORT).show();
                            call.cancel();
                        }
                    });
                }
            });

            builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(),
                            "Hapus jadwal dibatalkan", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            builder.setTitle("Tidak dapat menghapus jadwal");
            builder.setMessage("Jadwal gagal dihapus karena sudah terdaftar");
            builder.setCancelable(true);
            builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(),
                            "Tidak dapat menghapus jadwal", Toast.LENGTH_SHORT).show();
                }
            });
        }
        builder.show();
    }

    public void changeItem(int position, String text) {
        exampleList.get(position).changeScheduleStatus(text);
        psychologistScheduleAdapter.notifyItemChanged(position);
    }

    public void showRoutineSchedule(){
        String[] hariTanggal = dateStr.split(",");
        final String hari = hariTanggal[0];
        System.out.println("print hari: " + hari);

        psikologApi = UtilsApi.getApiService();

        Call<List<GetPsychologistSchedule>> call = psikologApi.getSchedule(
                "api/view_schedule/" + loggedInUser);
        call.enqueue(new Callback<List<GetPsychologistSchedule>>() {
            @Override
            public void onResponse(Call<List<GetPsychologistSchedule>> call,
                                   Response<List<GetPsychologistSchedule>> response) {
                psikologResponse = response.body();

                for (int i = psikologResponse.size() - 1; i >= 0; i--) {
                    GetPsychologistSchedule psikologData = psikologResponse.get(i);
                    if (hari.equalsIgnoreCase(psikologData.getHari())) {
                        scheduleTime = psikologData.getJam_kosong_start() + " - "
                                + psikologData.getJam_kosong_end();
                        System.out.println("hasil get schedule : " + psikologData.getHari()
                                + " " + scheduleTime);
                        exampleList.add(new PsychologistScheduleData("NOT BOOKED", scheduleTime));
                    }
                }
                buildRecyclerView();
            }

            @Override
            public void onFailure(Call<List<GetPsychologistSchedule>> call, Throwable t) {
                t.printStackTrace();
                System.out.println("hasil get schedule : GAGAL !!!");
            }
        });
    }

    public void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        psychologistScheduleAdapter = new PsychologistScheduleAdapter(exampleList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(psychologistScheduleAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
                if (dy < 0 && !fab.isShown()) {
                    fab.show();
                } else if (dy > 0 && fab.isShown()) {
                    fab.hide();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });


        psychologistScheduleAdapter.setOnItemClickListener(
                new PsychologistScheduleAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(getApplicationContext(),
                            "Ini schedule anda", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onDeleteClick(int position) {
                    removeItem(position);
                }
            });
    }

    private String getDateFromCalendar(Intent intent) {
        int dayOfMonth = intent.getIntExtra("dayOfMonthText", 0);
        int month = intent.getIntExtra("monthText", 0);
        int year = intent.getIntExtra("yearText", 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        String dayStr = new SimpleDateFormat("EEEE").format(calendar.getTime());
        String monthStr = new SimpleDateFormat("MMMM").format(calendar.getTime());
        String yearStr = new SimpleDateFormat("yyyy").format(calendar.getTime());

        dayStr = dayTranslatorEngToBahasa(dayStr);
        monthStr = monthTranslatorEngToBahasa(monthStr);

        String dayMonthYear = dayStr + ", " + dayOfMonth + " " + monthStr + " " + yearStr;

        Resources res = getResources();
        String result = String.format(res.getString(
                R.string.psychologist_daily_schedule_date_text), dayMonthYear);
        return result;
    }

    public String dayTranslatorEngToBahasa(String dayStr) {
        if (dayStr.equalsIgnoreCase("Monday")) {
            dayStr = "Senin";
        } else if (dayStr.equalsIgnoreCase("Tuesday")) {
            dayStr = "Selasa";
        } else if (dayStr.equalsIgnoreCase("Wednesday")) {
            dayStr = "Rabu";
        } else if (dayStr.equalsIgnoreCase("Thursday")) {
            dayStr = "Kamis";
        } else if (dayStr.equalsIgnoreCase("Friday")) {
            dayStr = "Jumat";
        } else if (dayStr.equalsIgnoreCase("Saturday")) {
            dayStr = "Sabtu";
        } else {
            dayStr = "Minggu";
        }
        return dayStr;
    }

    public String monthTranslatorEngToBahasa(String monthStr) {
        if (monthStr.equalsIgnoreCase("January")) {
            monthStr = "Januari";
        } else if (monthStr.equalsIgnoreCase("February")) {
            monthStr = "Februari";
        } else if (monthStr.equalsIgnoreCase("March")) {
            monthStr = "Maret";
        } else if (monthStr.equalsIgnoreCase("May")) {
            monthStr = "Mei";
        } else if (monthStr.equalsIgnoreCase("June")) {
            monthStr = "Juni";
        } else if (monthStr.equalsIgnoreCase("July")) {
            monthStr = "Juli";
        } else if (monthStr.equalsIgnoreCase("August")) {
            monthStr = "Agustus";
        } else if (monthStr.equalsIgnoreCase("October")) {
            monthStr = "Oktober";
        } else if (monthStr.equalsIgnoreCase("December")) {
            monthStr = "Desember";
        }
        return monthStr;
    }

    public void openPsychAddSchedule(String dateStr) {
        Intent intent = new Intent(this, PsychologistAddSchedule.class);
        intent.putExtra("date", dateStr);
        startActivity(intent);
    }
}