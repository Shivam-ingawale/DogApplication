package com.shivamingawale.dogapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.RequestQueue;
import com.shivamingawale.dogapplication.Adapters.BreedImageRecyclerViewAdapter;
import com.shivamingawale.dogapplication.Adapters.VolleySinglton;
import com.shivamingawale.dogapplication.ApiServices.ApiServices;
import com.shivamingawale.dogapplication.Repo.DogListRepo;
import com.shivamingawale.dogapplication.ui.home.HomeFragment;
import com.shivamingawale.dogapplication.ui.home.HomeViewModel;

public class BreedDetailScreen extends AppCompatActivity {


    TextView textView;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    ImageButton backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        setContentView(R.layout.activity_breed_detail_screen);

        recyclerView = findViewById(R.id.recyclerView1);
        backButton = findViewById(R.id.backButton);
        textView = findViewById(R.id.textBreed);
        progressBar = findViewById(R.id.progressBar_cyclic);
        backButton.setAlpha(0.0f);
        Intent intent = getIntent();
        String breed = (String) intent.getExtras().get("breedName");
        String breedName = breed.toUpperCase();

        BreedImageRecyclerViewAdapter breedImageRecyclerViewAdapter = new BreedImageRecyclerViewAdapter();
        //        ImageRecyclerViewAdapter  breedImageRecyclerViewAdapter = new ImageRecyclerViewAdapter(DogListRepo.getDogListRepo().getDogImagesUrl());

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        RequestQueue requestQueue1;
        requestQueue1 = VolleySinglton.getvolleySinglton(this).getRequestQueue();
        DogListRepo.getDogListRepo().clearDogImagesUrl();
        int  i = 0;
        do {
            requestQueue1.add(ApiServices.getRandomImageByBreed(breed));
            i++;
        } while ((i < 30));

//        Log.d("tag", "onCreate: ");


        Handler handler = new Handler();
        handler.postDelayed(() -> {

            textView.setText(breedName);
            backButton.setAlpha(1.0f);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(breedImageRecyclerViewAdapter);

            Log.d("tag", "recycler view set");
            progressBar.setVisibility(View.GONE);
        }, 5000);

        backButton.setClickable(true);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BreedDetailScreen.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }



}


/*
package com.shivamingawale.dogapplication;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.RequestQueue;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shivamingawale.dogapplication.Adapters.VolleySinglton;
import com.shivamingawale.dogapplication.ApiServices.ApiServices;
import com.shivamingawale.dogapplication.Repo.DogListRepo;

import java.io.File;
import java.util.Date;

public class BreedDetailScreen extends AppCompatActivity {


    RoundedImageView imageView;
    TextView textView;
    ProgressBar progressBar;
    public static final int PERMISSION_WRITE = 0;
    ImageButton imageButton;
//    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breed_detail_screen);
//        recyclerView= findViewById(R.id.recycler);
        imageView = findViewById(R.id.imageView1);
        imageButton = findViewById(R.id.archive);
        textView = findViewById(R.id.textBreed);
        progressBar = findViewById(R.id.progressBar_cyclic);

        Intent intent = getIntent();
        String breedName = (String) intent.getExtras().get("breedName");


        RequestQueue requestQueue1;
        requestQueue1 = VolleySinglton.getvolleySinglton(this).getRequestQueue();
        DogListRepo.getDogListRepo().clearDogImagesUrl();
//        for (int i = 0; i < 10; i++) {
        requestQueue1.add(ApiServices.getRandomImageByBreed(breedName));
//        }



        Handler handler = new Handler();
        handler.postDelayed(() -> {

            textView.setText(breedName);
//            recyclerView.setAdapter(imageRecyclerViewAdapter);
            progressBar.setVisibility(View.GONE);
        }, 1000);

//        imageView.setImageResource();
        imageButton.setClickable(true);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) {
                     Download download = new Download();
                    download.execute();
                    imageButton.setImageResource(R.drawable.ic_archive_tick);
                    imageButton.setClickable(false);
//                  Toast.makeText(this, "Image Saved", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }


    public class Download extends AsyncTask<Void, Void, Void> {

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            Log.d("tag", "onPreExecute: ");
//                progressBar.setVisibility(View.VISIBLE);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("mmddyyyyhhmmss");
                String date = dateFormat.format(new Date());
                String filename = "photo" + date;

                DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri downloadUri = Uri.parse(DogListRepo.getDogListRepo().getDogImagesUrl().get(0));
                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(false)
                        .setTitle(filename)
                        .setMimeType("jpeg") // Your file type. You can use this code to download other file types also.
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, File.separator + filename + ".jpg");
                dm.enqueue(request);
                Log.d("tag", "doInBackGround: " + "dir:   " + Environment.DIRECTORY_PICTURES + "   " + File.separator + filename + ".jpg");
//                    Toast.makeText(getApplicationContext(), "Image download started.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
//                    Toast.makeText(getApplicationContext(), "Image download failed.", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
//                progressBar.setVisibility(View.GONE);

        }
    }


    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if ((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_WRITE);
            return false;
        }
        return true;
    }
}


 */