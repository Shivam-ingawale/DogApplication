package com.shivamingawale.dogapplication.Adapters;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shivamingawale.dogapplication.BreedDetailScreen;
import com.shivamingawale.dogapplication.MainActivity;
import com.shivamingawale.dogapplication.R;
import com.shivamingawale.dogapplication.Repo.DogListRepo;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

//import static com.shivamingawale.dogapplication.BreedDetailScreen.checkPermission;

public class BreedImageRecyclerViewAdapter extends RecyclerView.Adapter<BreedImageRecyclerViewAdapter.ViewHolder> {

    ArrayList<String> modelsArrayList = DogListRepo.getDogListRepo().getDogImagesUrl();
    DownloadManager dm;
    public static final int PERMISSION_WRITE = 0;

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View imageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.breed_images,parent,false);
        return new ViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        boolean imageFromNet=false;
        if (!DogListRepo.getDogListRepo().getDogImagesUrl().isEmpty()) {
         Glide.with(holder.roundedBreed).load(modelsArrayList.get(position)).into(holder.roundedBreed);
            holder.imageDownload.setAlpha(1f);
            imageFromNet=true;
            holder.imageDownload.setClickable(true);
        } else {
            holder.roundedBreed.setImageResource(R.drawable.ic_error_6641731);
            holder.imageDownload.setAlpha(0.0f);
            holder.imageDownload.setClickable(false);

        }

        boolean finalImageFromNet = imageFromNet;
        holder.imageDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = ((ViewHolder) holder).itemView.getContext();
                if (checkPermission(context)&& finalImageFromNet) {
//                    new Downloading().execute(String.valueOf(DogListRepo.getDogListRepo().getDogImagesUrl()));

                    dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

                    Download download = new Download(DogListRepo.getDogListRepo().getDogImagesUrl().get(position));
                    download.execute();

                    holder.imageDownload.setImageResource(R.drawable.ic_archive_tick);
                    holder.imageDownload.setClickable(false);
                  Toast.makeText(context, "Image Saved", Toast.LENGTH_SHORT).show();

                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return 10;
    }



    static class ViewHolder extends RecyclerView.ViewHolder{

//        RoundedImageView roundedBreed;
        ImageView roundedBreed;
        ImageButton imageDownload;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            roundedBreed = itemView.findViewById(R.id.imageBreed);
            imageDownload = itemView.findViewById(R.id.imageDownButton);
        }
    }
    public class Download extends AsyncTask<Void, Void, Void> {

        String imageurl;
        public Download(String s) {
            imageurl = s;
        }



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

                Uri downloadUri = Uri.parse(imageurl);
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
    public boolean checkPermission(Context context) {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        if ((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_WRITE);
            return false;
        }
        return true;
    }

}
