package com.nomad.mybrainmemory.util;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {

    public static String saveImageToInternalStorage(Context context, Bitmap bitmap, String fileName) {
        String filePath = null;
        try {
            // Get the directory for internal storage
            File internalStorageDir = context.getFilesDir();

            // Create a file with the given name in the internal storage directory
            File imageFile = new File(internalStorageDir, fileName);

            // Convert the Bitmap to a file
            FileOutputStream outputStream = new FileOutputStream(imageFile);

            // Compress the Bitmap and write it to the file
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

            // Close the FileOutputStream
            outputStream.close();

            // Get the absolute path of the saved file
            filePath = imageFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    public static Bitmap loadImageFromInternalStorage(Context context, String filePath) {
        Bitmap bitmap = null;
        try {
            // Read the file from internal storage
            File imageFile = new File(filePath);
            bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}

