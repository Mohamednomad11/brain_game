package com.nomad.mybrainmemory;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nomad.mybrainmemory.jigsawpuzzle.adapter.StorePreference;
import com.nomad.mybrainmemory.util.FirebaseStorageUtils;
import com.nomad.mybrainmemory.util.ImageUtils;
import com.nomad.mybrainmemory.util.StaticConstants;

import java.io.File;
import java.util.List;

public class MenuScreen extends AppCompatActivity {
    LinearLayout matchingButton, jigsawButton;

    TextView nameText,adminText;

    ProgressBar progressBar;

    Button generateReport;


    LinearLayout topProfile;

    LinearLayout gameMenu;

    LinearLayout llAdminInfo;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_screen);
        matchingButton = findViewById(R.id.ll_image_matching);
        jigsawButton = findViewById(R.id.ll_jigsaw_puzzle);
        nameText = findViewById(R.id.name_text);
        adminText = findViewById(R.id.nameTextView);
        nameText.setText(StaticConstants.CURRENT_USER_NAME);

        generateReport = findViewById(R.id.btn_generate_report);
        gameMenu = findViewById(R.id.ll_game_menu);
        llAdminInfo = findViewById(R.id.ll_admin_info);

        topProfile = findViewById(R.id.ll_top_profile);

        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);

        llAdminInfo.setVisibility(View.INVISIBLE);



        StorePreference storePreference = new StorePreference(this);

       String userType =  storePreference.getStringValue(StaticConstants.KEY_USER_TYPE);

       if(userType.equals(StaticConstants.VAL_USER_TYPE_USER)){

           llAdminInfo.setVisibility(View.GONE);

           generateReport.setVisibility(View.INVISIBLE);

       }else if(userType.equals(StaticConstants.VAL_USER_TYPE_ADMIN)){
           topProfile.setVisibility(View.GONE);
           gameMenu.setVisibility(View.GONE);
           adminText.setText(StaticConstants.CURRENT_USER_NAME);
           llAdminInfo.setVisibility(View.VISIBLE);
           generateReport.setVisibility(View.VISIBLE);
       }

        matchingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuScreen.this, MatchingStartScreen.class);
                startActivity(i);
            }
        });

        jigsawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuScreen.this, JigsawPuzzleStartScreen.class);
                startActivity(i);
            }
        });

        generateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuScreen.this, SearchReportActivity.class);
                startActivity(i);
            }
        });


//        progressBar.setVisibility(View.VISIBLE);
//        downLoadMatchingImage();
    }

    private void downLoadMatchingImage(){
        StorageReference folderRef = storage.getReferenceFromUrl("gs://my-brain-memory.appspot.com/match/");
        folderRef.listAll()
                .addOnSuccessListener(listResult -> {
                    // List of items within the "folder" successfully obtained
                    List<StorageReference> imageRefs = listResult.getItems();
                    // Iterate through the list of image references and do whatever you want with them
                    int size = imageRefs.size();
                    int i = 0;
                    for (StorageReference imageRef : imageRefs) {
                        // Do something with each image reference, such as download the image or get its metadata
                        // For example, you can download each image using imageRef.getBytes() as shown in the previous answer.
                        Log.e("ImageDownload",imageRef.getName());
                        // Replace "image.jpg" with the actual name of your image file
                        i++;

                        int finalI = i;
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Got the download URL for 'users/me/profile.png'
                                Log.e("DownloadUrl",uri.toString());
                                FirebaseStorageUtils.downloadImageAsBitmapFromFirebaseStorage(uri.toString(), new FirebaseStorageUtils.OnBitmapDownloadListener() {
                                    @Override
                                    public void onBitmapDownloaded(Bitmap bitmap) {
                                        // Use the downloaded bitmap here
                                        // For example, display it in an ImageView
//                                imageView.setImageBitmap(bitmap);
                                      String savedImagePath =   ImageUtils.saveImageToInternalStorage(getApplicationContext(),bitmap,imageRef.getName());

                                      Log.e("SavedImagepath", savedImagePath.toString());

                                      StaticConstants.MATCHING_IMAGE_LIST.add(savedImagePath);
                                      if(finalI ==size)
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }

                                    @Override
                                    public void onBitmapDownloadFailed(Exception exception) {
                                        // Handle the download failure
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });


                    }
                })
                .addOnFailureListener(exception -> {
                    // Failed to get the list of items within the "folder", handle the error
                    Log.e("FirebaseStorage", "Error listing items: " + exception.getMessage());
                    progressBar.setVisibility(View.INVISIBLE);
                });

    }
}
