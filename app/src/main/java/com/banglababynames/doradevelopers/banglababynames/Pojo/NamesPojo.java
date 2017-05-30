package com.banglababynames.doradevelopers.banglababynames.Pojo;

/**
 * Created by amirdora on 5/24/2017.
 */

public class NamesPojo {

    String mNameEnglish;
    String mNameBangla;
    String mMeaningEnglish;
    String mMeaningBangla;
    String mGender;


    public NamesPojo() {
    }
    public NamesPojo(String mNameEnglish, String mNameBangla) {
        this.mNameEnglish = mNameEnglish;
        this.mNameBangla = mNameBangla;
    }


    public NamesPojo(String mNameEnglish, String mNameBangla, String mMeaningEnglish, String mMeaningBangla, String mGender) {
        this.mNameEnglish = mNameEnglish;
        this.mNameBangla = mNameBangla;
        this.mMeaningEnglish = mMeaningEnglish;
        this.mMeaningBangla = mMeaningBangla;
        this.mGender = mGender;
    }

    public String getmNameEnglish() {
        return mNameEnglish;
    }

    public String getmNameBangla() {
        return mNameBangla;
    }

    public String getmMeaningEnglish() {
        return mMeaningEnglish;
    }

    public String getmMeaningBangla() {
        return mMeaningBangla;
    }

    public String getmGender() {
        return mGender;
    }

}
