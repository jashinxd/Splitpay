package com.example.jashin.splitpay;

//import android.Manifest;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.support.annotation.NonNull;
//import android.support.v4.content.FileProvider;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.api.client.extensions.android.http.AndroidHttp;
//import com.google.api.client.googleapis.json.GoogleJsonResponseException;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.gson.GsonFactory;
//import com.google.api.services.vision.v1.Vision;
//import com.google.api.services.vision.v1.VisionRequest;
//import com.google.api.services.vision.v1.VisionRequestInitializer;
//import com.google.api.services.vision.v1.model.AnnotateImageRequest;
//import com.google.api.services.vision.v1.model.AnnotateImageResponse;
//import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
//import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
//import android.util.Log;

import com.google.auth.oauth2.ComputeEngineCredentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.EntityAnnotation;
//import com.google.api.services.vision.v1.model.Feature;
//import com.google.api.services.vision.v1.model.Image;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.AnnotateImageResponse;


//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
//import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
//import com.google.android.gms.location.LocationListener;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;

//import java.io.ByteArrayOutputStream;
//import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
//import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
//import java.util.Locale;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;


public class imgProc implements Runnable {

    //private static final String CLOUD_VISION_API_KEY = "AIzaSyD7Qsk5Z5rk1owJV3kehudiM3eUZNpUSss";
    private String filePath;

    public imgProc(String filePath) {
        this.filePath = filePath;

    }

    public String getFilePath() {
        return this.filePath;
    }

    public static void authImplicit() {
        // If you don't specify credentials when constructing the client, the client library will
        // look for credentials via the environment variable GOOGLE_APPLICATION_CREDENTIALS.
        Storage storage = StorageOptions.getDefaultInstance().getService();

        System.out.println("Buckets:");
        Page<Bucket> buckets = storage.list();
        for (Bucket bucket : buckets.iterateAll()) {
            System.out.println(bucket.toString());
        }
    }

    static void authCompute() {
        // Explicitly request service account credentials from the compute engine instance.
        GoogleCredentials credentials = ComputeEngineCredentials.create();
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        System.out.println("Buckets:");
        Page<Bucket> buckets = storage.list();
        for (Bucket bucket : buckets.iterateAll()) {
            System.out.println(bucket.toString());
        }
    }

    public void detectText() throws IOException{
        authImplicit();
        List<AnnotateImageRequest> requests = new ArrayList<>();

        ByteString imgBytes = ByteString.readFrom(new FileInputStream(this.filePath));

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(com.google.cloud.vision.v1.Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);
        System.out.println("gets here1");
        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            System.out.println("gets here");
            //Log.d("gets here", "swag");
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();


            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    //Log.d("Error", res.getError().getMessage());
                    return;
                }

                // For full list of available annotations, see http://g.co/cloud/vision/docs
                for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
                    //Log.d("Photo Text", annotation.getDescription());
                    //Log.d("Photo Position", annotation.getBoundingPoly().toString());
                    System.out.printf("Text: %s\n", annotation.getDescription());
                    System.out.printf("Position : %s\n", annotation.getBoundingPoly());
                }
            }
        }
    }

    public void run() {
        try {
            detectText();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException");
        }
    }
}
