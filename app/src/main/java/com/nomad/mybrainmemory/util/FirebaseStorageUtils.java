package com.nomad.mybrainmemory.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.ByteArrayOutputStream;

public class FirebaseStorageUtils {

    public static void downloadImageAsBitmapFromFirebaseStorage(String imageUrl, final OnBitmapDownloadListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(imageUrl);

        final long ONE_MEGABYTE = 1024 * 1024;
        storageRef.getBytes(ONE_MEGABYTE)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        // Convert the downloaded byte array to a Bitmap
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        listener.onBitmapDownloaded(bitmap);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception exception) {
                        Log.e("FirebaseStorageUtils", "Error downloading image: " + exception.getMessage());
                        listener.onBitmapDownloadFailed(exception);
                    }
                });
    }

    public interface OnBitmapDownloadListener {
        void onBitmapDownloaded(Bitmap bitmap);
        void onBitmapDownloadFailed(Exception exception);
    }
}
