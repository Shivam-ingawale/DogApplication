package com.shivamingawale.dogapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.RequestQueue;
import com.shivamingawale.dogapplication.Adapters.VolleySinglton;
import com.shivamingawale.dogapplication.ApiServices.ApiServices;
import com.shivamingawale.dogapplication.Repo.DogListRepo;

public class SplashScreen extends AppCompatActivity {
    LottieAnimationView lottieAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);


        lottieAnimationView = findViewById(R.id.lottieAnimationView2);
        RequestQueue requestQueue;
        requestQueue = VolleySinglton.getvolleySinglton(this).getRequestQueue();
        if(DogListRepo.getDogListRepo().getDogNameList().isEmpty()) {
            requestQueue.add(ApiServices.getListAllBreeds());
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lottieAnimationView.pauseAnimation();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3100);

    }
}