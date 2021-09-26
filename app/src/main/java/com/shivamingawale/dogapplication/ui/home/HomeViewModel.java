package com.shivamingawale.dogapplication.ui.home;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.RequestQueue;
import com.shivamingawale.dogapplication.Adapters.VolleySinglton;
import com.shivamingawale.dogapplication.ApiServices.ApiServices;
import com.shivamingawale.dogapplication.Repo.DogListRepo;

import java.util.ArrayList;

import static androidx.core.content.ContentProviderCompat.requireContext;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
//    private MutableLiveData<ArrayList<String>> arrayListMutableLiveData;



    public HomeViewModel() {


        mText = new MutableLiveData<>();
//        arrayListMutableLiveData = new MutableLiveData<>();
//        arrayListMutableLiveData.setValue(DogListRepo.getDogListRepo().getDogNameList());
        mText.setValue("This is home fragment");
    }

    private Context requireContext() {
        return requireContext();
    }

    public LiveData<String> getText() {

        return mText;
    }
}