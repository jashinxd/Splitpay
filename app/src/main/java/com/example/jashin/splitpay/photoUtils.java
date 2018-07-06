package com.example.jashin.splitpay;

import java.io.*;
import java.lang.String;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.Context;
import android.os.Environment;

public class photoUtils {

    private String mCurrentPhotoPath;
    private Context c;
    private File pic;

    public photoUtils(Context context) {
        this.c = context;
    }

    public void createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = c.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        pic = image;
    }

    public String getMCurrentPhotoPath() {
        return mCurrentPhotoPath;
    }

    public File getPic() {
        return pic;
    }

}
