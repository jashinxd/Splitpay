package com.example.jashin.splitpay;

import java.io.File;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.content.Intent;
import android.provider.MediaStore;


public class mainScreen extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;

    public static Context context;
    private String photoPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        context = getApplicationContext();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            System.out.println(photoPath);
            displayItems();
        }
    }

    public void takePhoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoUtils pU = new photoUtils(context);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            photoPath = "";
            try {
                pU.createImageFile();
                photoFile = pU.getPic();
                photoPath = pU.getMCurrentPhotoPath();
            } catch (Exception ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            Uri photoURI = FileProvider.getUriForFile(context,
                    "com.example.android.fileprovider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            //Log.d("Photo Path", photoPath);
            // Process the image
        }
    }

    public void displayItems() {
        Intent intent = new Intent(this, Items.class);
        intent.putExtra("photoPath",photoPath);
        startActivity(intent);
    }

    public void splitEvenly(View view) {
        Intent intent = new Intent(this, EvenSplit.class);
        startActivity(intent);
    }
}
