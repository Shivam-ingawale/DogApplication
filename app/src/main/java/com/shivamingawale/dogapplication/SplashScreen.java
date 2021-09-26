package com.shivamingawale.dogapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.PrecomputedTextCompat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.PrecomputedText;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.RequestQueue;
import com.shivamingawale.dogapplication.Adapters.VolleySinglton;
import com.shivamingawale.dogapplication.ApiServices.ApiServices;
import com.shivamingawale.dogapplication.Repo.DogListRepo;

import javax.xml.transform.Result;

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


        ApiData apiData = new ApiData();
        apiData.execute();


    }

    public class ApiData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            RequestQueue requestQueue;
            requestQueue = VolleySinglton.getvolleySinglton(SplashScreen.this).getRequestQueue();
            if (DogListRepo.getDogListRepo().getDogNameList().isEmpty()) {
                requestQueue.add(ApiServices.getListAllBreeds());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    lottieAnimationView.pauseAnimation();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);

        }
    }


}
