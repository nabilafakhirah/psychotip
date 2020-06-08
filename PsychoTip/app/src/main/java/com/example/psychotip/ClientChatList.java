package com.example.psychotip;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ClientChatList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_chat_list);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        //Floating action button new counseling
        FloatingActionButton fab = findViewById(R.id.new_counseling);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCounselingCategory();
            }
        });

        ChatListData[] myListData = new ChatListData[] {
            new ChatListData("Sarah Sechan", "Semoga anda cepat membaik", R.drawable.ic_avatar),
            new ChatListData("Barry Allen", "Terima kasih kembali.", R.drawable.ic_avatar),
            new ChatListData("Robin Hood", "Tetap semangat!", R.drawable.ic_avatar),
        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ChatListAdapter adapter = new ChatListAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id ==  R.id.action_favorite) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openCounselingCategory() {
        Intent intent = new Intent(this, ClientNewCounselingSchedule.class);

        startActivity(intent);
    }
}
