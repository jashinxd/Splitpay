package com.example.jashin.splitpay;

import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.TextAnnotation;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

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

    public void run() {
            detectText();
    }
}
