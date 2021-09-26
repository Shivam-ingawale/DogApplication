package com.shivamingawale.dogapplication.ui.gallery;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.RequestQueue;
import com.shivamingawale.dogapplication.Adapters.ImageRecyclerViewAdapter;
import com.shivamingawale.dogapplication.Adapters.VolleySinglton;
import com.shivamingawale.dogapplication.ApiServices.ApiServices;
import com.shivamingawale.dogapplication.Repo.DogListRepo;
import com.shivamingawale.dogapplication.databinding.FragmentGalleryBinding;


public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textGallery;
        final RecyclerView recyclerView =binding.recyclerView;
        final  ProgressBar progressBar_cyclic = binding.progressBarGallery;

        ImageRecyclerViewAdapter imageRecyclerViewAdapter = new ImageRecyclerViewAdapter(DogListRepo.getDogListRepo().getDogAllImageUrl());

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        DogListRepo.getDogAllImageUrl().clear();
        RequestQueue requestQueue;
        requestQueue = VolleySinglton.getvolleySinglton(requireContext()).getRequestQueue();
        for (int i = 0; i < 10; i++) {
            requestQueue.add(ApiServices.getRandomImage());
//            requestQueue.add(ApiServices.getRandomImageByBreed("husky"));
        }


        recyclerView.setLayoutManager(gridLayoutManager);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                recyclerView.setAdapter(imageRecyclerViewAdapter);
                progressBar_cyclic.setVisibility(View.GONE);

            }
        }, 2000);





//        recyclerView.setAdapter(imageRecyclerViewAdapter);

        recyclerView.setHasFixedSize(true);


        galleryViewModel.getText().observe(getViewLifecycleOwner(), s -> {
//                textView.setText(s);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}