package com.shivamingawale.dogapplication.ui.home;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.RequestQueue;
import com.shivamingawale.dogapplication.Adapters.VolleySinglton;
import com.shivamingawale.dogapplication.ApiServices.ApiServices;
import com.shivamingawale.dogapplication.BreedDetailScreen;
import com.shivamingawale.dogapplication.MainActivity;
import com.shivamingawale.dogapplication.R;
import com.shivamingawale.dogapplication.Repo.DogListRepo;
import com.shivamingawale.dogapplication.databinding.FragmentHomeBinding;
//import com.shivamingawale.dogapplication.ui.slideshow.SlideshowFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ListView listView = binding.listView;
        if (DogListRepo.getDogListRepo().getDogNameList().isEmpty()) {
                Toast.makeText(getActivity(),"No Internet Access",Toast.LENGTH_LONG).show();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.dog_breed_name,
                DogListRepo.getDogListRepo().getDogNameList());

        listView.setAdapter(arrayAdapter);
//
//        Handler handler2 = new Handler();
//        handler2.postDelayed(new Runnable() {
//            public void run() {
//                progressBar.setVisibility(View.GONE);
//            }
//        }, 2000);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), BreedDetailScreen.class);
//                Intent intent = new Intent(getContext(), SlideshowFragment.class);
                String breedName = DogListRepo.getDogListRepo().getDogNameList().get(i);
                intent.putExtra("breedName", breedName);

                startActivity(intent);


//                Log.e("TAG", "clicked"+DogListRepo.getDogListRepo().getDogNameList().get(i));
            }
        });
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}