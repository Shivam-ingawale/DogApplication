package com.shivamingawale.dogapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shivamingawale.dogapplication.R;
import com.shivamingawale.dogapplication.Repo.DogListRepo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ImageRecyclerViewAdapter extends  RecyclerView.Adapter<ImageRecyclerViewAdapter.ViewHolder> {


    public ImageRecyclerViewAdapter(ArrayList<String> modelsArrayList) {
        this.modelsArrayList = modelsArrayList;
    }

    ArrayList<String> modelsArrayList;
//    ArrayList<String> modelsArrayList = DogListRepo.getDogListRepo().getDogAllImageUrl();
//    ArrayList<String> modelsArrayList = DogListRepo.getDogListRepo().getDogImagesUrl();




    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        return new ViewHolder(cardView);
    }

    @Override
        public void onBindViewHolder(@NonNull @NotNull ImageRecyclerViewAdapter.ViewHolder holder, int position) {
//            holder.title.setText( );
//            holder.tags.setText( );

            Glide.with(holder.roundedImageView).load(modelsArrayList.get(position)).into(holder.roundedImageView);



    }

        @Override
        public int getItemCount() {
            return modelsArrayList.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder{

//            TextView title;
            RoundedImageView roundedImageView;
//            ImageButton imageButton;

            public ViewHolder(@NonNull @NotNull View itemView) {
                super(itemView);


                roundedImageView = itemView.findViewById(R.id.imagePost);

//                imageView = itemView.findViewById(R.id.imageView);

            }
        }




}
