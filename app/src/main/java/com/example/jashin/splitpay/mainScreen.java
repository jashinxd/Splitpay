package com.example.jashin.splitpay;

import java.io.File;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        context = getApplicationContext();
//
//        Button addBtn = (Button) findViewById(R.id.addBtn);
//        addBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EditText firstNumEditText = (EditText) findViewById(R.id.firstNumEditText);
//                EditText secondNumEditText = (EditText) findViewById(R.id.secondNumEditText);
//                TextView resultTextView = (TextView) findViewById(R.id.resultTextView);
//
//                int num1 = Integer.parseInt(firstNumEditText.getText().toString());
//                int num2 = Integer.parseInt(secondNumEditText.getText().toString());
//                int result = num1 + num2;
//                resultTextView.setText(result + "");
//            }
//        });
    }

    public void takePhoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoUtils pU = new photoUtils(context);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            String photoPath = "";
            try {
                pU.createImageFile();
                photoFile = pU.getPic();
                photoPath = pU.getMCurrentPhotoPath();
            } catch (Exception ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                //Log.d("Photo Path", photoPath);
                // Process the image
                while (photoFile.length() == 0) {
                }
                System.out.println(photoPath);
                imgProc iP = new imgProc(photoPath);
                Thread t = new Thread(iP);
                t.start();
            }
        }
    }

//    @Override
//    protected void onResume() {
//        takePhoto();
//    }
}
