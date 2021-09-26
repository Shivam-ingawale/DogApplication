package com.shivamingawale.dogapplication.Repo;

import java.util.ArrayList;

public class DogListRepo {

    private static DogListRepo dogListRepo;

        ArrayList<String> dogNameList = new ArrayList<>();
        ArrayList<String> dogImagesUrl = new ArrayList<>();
        ArrayList<String> dogAllImageUrl = new ArrayList<>();

    public static ArrayList<String> getDogAllImageUrl() {
        return dogListRepo.dogAllImageUrl;
    }

    public void setDogAllImageUrl(String dogAllImageUrl) {
        DogListRepo.getDogAllImageUrl().add(dogAllImageUrl);
    }


    public ArrayList<String> getDogImagesUrl() {
        return dogListRepo.dogImagesUrl;
    }

    public void setDogImagesUrl(String imagesUrl) {
        dogListRepo.dogImagesUrl.add(imagesUrl);
    }
    public void clearDogImagesUrl(){
        dogListRepo.dogImagesUrl.clear();
    }

    public ArrayList<String> getDogNameList() {
            return dogListRepo.dogNameList;
        }
    public void setDogNameList(String best) {
            dogListRepo.dogNameList.add(best);
        }
    public void setDogNameList(ArrayList<String> dogNameList) {
        this.dogNameList = dogNameList;
    }

    public static DogListRepo getDogListRepo() {
        if (dogListRepo==null){
            dogListRepo = new DogListRepo();
        }
        return dogListRepo;
    }

}
