package com.example.psychotip;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PsychologistEditProfile extends AppCompatActivity {

    public static final int IMAGE_GALLERY_REQUEST = 20;
    private EditText name;
    private TextView username;
    private TextView birthdate;
    private TextView gender;
    private EditText address;
    private ImageView profilePicture;
    private GetUserResponse user;

    Api userApi;
    Api updateProfileApi;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychologist_edit_profile);

        name = (EditText) findViewById(R.id.name);
        username = findViewById(R.id.username);
        birthdate = findViewById(R.id.birthdate);
        gender = findViewById(R.id.gender);
        address = (EditText) findViewById(R.id.address);
        profilePicture = (ImageView) findViewById(R.id.profile_picture);

        session = new SessionManager(getApplicationContext());
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
            }

            @Override
            public void onFailure(Call<GetUserResponse> call, Throwable t) {
                call.cancel();
            }
        });

        updateProfileApi = UtilsApi.getApiService();

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageGalleryClicked();
            }
        });

        Button confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> fields = new HashMap<>();
                fields.put("username", loggedInUser);

                String newName = name.getText().toString();
                String newAddress = address.getText().toString();
                fields.put("name", newName);
                fields.put("address", newAddress);

                fields.put("email", user.email);
                fields.put("birthday", user.birthday);
                fields.put("gender", user.gender);

                Call<User> call = updateProfileApi.updateProfile(fields);

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        openConfirmPage();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        call.cancel();
                    }
                });
            }
        });
    }

    public void openConfirmPage() {

        Intent intent = new Intent(this, PsychologistDashboard.class);

        startActivity(intent);
    }

    public void onImageGalleryClicked() {
        //invoke the image gallery using implicit intent
        Intent photoSelectorIntent = new Intent(Intent.ACTION_PICK);

        //directory to find the data/file
        File picDirectory = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        String picDirectoryPath = picDirectory.getPath();

        //get a URI representation
        Uri data = Uri.parse(picDirectoryPath);

        photoSelectorIntent.setDataAndType(data, "image/*");

        //we will invoke this activity and get something back from it
        startActivityForResult(photoSelectorIntent, IMAGE_GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            //if we are here, everything processed successfully
            if (requestCode == IMAGE_GALLERY_REQUEST) {
                //if we are here, we are hearing back from the image gallery

                //the address of the image on the SD card
                Uri imageUri = data.getData();

                //declare a stream to read the image from the SD card
                InputStream inputStream;
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);

                    //get a bitmap from the stream
                    Bitmap image = BitmapFactory.decodeStream(inputStream);

                    //show the image to the user
                    profilePicture.setImageBitmap(image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    //show message to user, if image unavailable
                    Toast.makeText(this, "Gambar gagal dibuka", Toast.LENGTH_LONG).show();

                }
            }
        }
    }
}
