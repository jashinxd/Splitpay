package com.example.jashin.splitpay;



import android.content.Context;

import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.util.IOUtils;
//import com.google.api.client.util.IOUtils;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.TextAnnotation;
//import com.google.protobuf.ByteString;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class imgProc implements Runnable {

    //private static final String CLOUD_VISION_API_KEY = "AIzaSyD7Qsk5Z5rk1owJV3kehudiM3eUZNpUSss";
    private String filePath;

    public imgProc(String filePath) {
        this.filePath = filePath;

    }

    public String getFilePath() {
        return this.filePath;
    }



    public void detectText() {
        Vision.Builder visionBuilder = new Vision.Builder(
                new NetHttpTransport(),
                new AndroidJsonFactory(),
                null);

        visionBuilder.setVisionRequestInitializer(
                new VisionRequestInitializer("AIzaSyD7Qsk5Z5rk1owJV3kehudiM3eUZNpUSss"));

        Vision vision = visionBuilder.build();

        try {

            File initialFile = new File(this.filePath);
            InputStream inputStream = new FileInputStream(initialFile);
            byte[] photoData = IOUtils.toByteArray(inputStream);
            inputStream.close();
            Image inputImage = new Image();
            inputImage.encodeContent(photoData);

            Feature desiredFeature = new Feature();
            desiredFeature.setType("TEXT_DETECTION");

            AnnotateImageRequest request = new AnnotateImageRequest();
            request.setImage(inputImage);
            request.setFeatures(Arrays.asList(desiredFeature));

            BatchAnnotateImagesRequest batchRequest =
                    new BatchAnnotateImagesRequest();
            batchRequest.setRequests(Arrays.asList(request));

            BatchAnnotateImagesResponse batchResponse =
                    vision.images().annotate(batchRequest).execute();

            final TextAnnotation text = batchResponse.getResponses()
                    .get(0).getFullTextAnnotation();
            System.out.println("getting here");
            System.out.println(text.getText());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    public static void authImplicit() {
        // If you don't specify credentials when constructing the client, the client library will
        // look for credentials via the environment variable GOOGLE_APPLICATION_CREDENTIALS.
        System.out.println("adnskandja");
        try {
            Storage storage = StorageOptions.newBuilder()
                    .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("/storage/emulated/0/Android/data/com.example.jashin.splitpay/files/keyfile/keyfile.json")))
                    .setProjectId("splitpay-209720")
                    .build()
                    .getService();
            System.out.println("Buckets:");
            Page<Bucket> buckets = storage.list();
            for (Bucket bucket : buckets.iterateAll()) {
                System.out.println(bucket.toString());
            }
        }
        catch (IOException e) {
            System.out.println("gets to catch");
            e.printStackTrace();
            System.exit(0);
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

        System.out.println(System.getenv());
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
    */

    public void run() {
            detectText();
    }
}
