package com.shivamingawale.dogapplication.ApiServices;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shivamingawale.dogapplication.Repo.DogListRepo;
import com.shivamingawale.dogapplication.ui.gallery.GalleryFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ApiServices {

    public static JsonObjectRequest getRandomImage() {
        JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, "https://dog.ceo/api/breeds/image/random", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if ("success".equals(response.getString("status"))) {
                        Log.d("TAG", "getRandomImage onResponse: " + response.getString("message"), null);
                        if (DogListRepo.getDogAllImageUrl().size() < 10) {
                            String arr = (response.getString("message"));

                            DogListRepo.getDogListRepo().setDogAllImageUrl(arr);

                        }
                    }
                } catch (JSONException e) {
                    Log.e("TAG", "getRandomImage onResponse: Error in fetch result!!", null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "getRandomImage onErrorResponse: " + error.toString(), null);
            }
        });
        return request2;
    }

    public static JsonObjectRequest getRandomImageByBreed(String Breed) {
//        Log.e("TAG","resrandom");
        JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET, "https://dog.ceo/api/breed/" + Breed + "/images/random", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if ("success".equals(response.getString("status"))) {
                        String dog = (response.getString("message"));
                        if (!DogListRepo.getDogListRepo().getDogImagesUrl().contains(dog)) {
                            DogListRepo.getDogListRepo().setDogImagesUrl(dog);

//                            Log.d("tag", "res" + response.getString("message")+" size "+DogListRepo.getDogListRepo().getDogImagesUrl().size());
                        }
                    }
                } catch (JSONException e) {
                    Log.d("tag", "dog random images by breed res:"+DogListRepo.getDogListRepo().getDogImagesUrl().size());

                    Log.e("TAG", "getRandomImageByBreed onResponse: Error in fetch result!!", null);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "getRandomImageByBreed onErrorResponse: " + error.toString(), null);
            }
        });

        return request1;
    }


    //    requestQueue=VolleySinglton.getvolleySinglton(this).getRequestQueue();
    public static JsonObjectRequest getListAllBreeds() {
//            Log.e("TAG", "in get list all breed");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, " https://dog.ceo/api/breeds/list/all", null, response -> {
            try {
//                    Log.e("TAG", "in try  get list all breed");
                if ("success".equals(response.getString("status"))) {
                    JSONObject message = response.getJSONObject("message");
                    Iterator keys = message.keys();
                    while (keys.hasNext()) {
                        String breeds = (String) keys.next();
                        DogListRepo.getDogListRepo().setDogNameList(breeds);
//                            Log.e("TAG", "breeds:"+breeds);

                    }
                }
            } catch (JSONException e) {

                Log.e("TAG", "getListAllBreeds onResponse: Error in fetch result!!", null);
            }

        }, error -> Log.e("TAG", "getListAllBreeds onErrorResponse: " + error.toString(), null));
        return request;
    }
}
