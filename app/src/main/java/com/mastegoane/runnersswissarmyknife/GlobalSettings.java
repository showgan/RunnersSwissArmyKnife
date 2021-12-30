package com.mastegoane.runnersswissarmyknife;

public class GlobalSettings {
    public enum Language {ENGLISH, ADIGA, ARABIC, TURKISH, HEBREW}

    private GlobalSettings() {
        mLanguage = Language.ENGLISH;
//        mScoreLD = new MutableLiveData<>(0);
    }

    public static synchronized GlobalSettings inst() {
        return mInstance;
    }

    public void setLanguage(Language language) {
        mLanguage = language;
    }

    public Language getLanguage() {
        return mLanguage;
    }

//    public LiveData<Integer> getScore() {
//        return mScoreLD;
//    }
//
//    public void setScore(int score) {
//        this.mScoreLD.setValue(score);
//    }

    private static final GlobalSettings mInstance = new GlobalSettings();
    private Language mLanguage;
//    private MutableLiveData<Integer> mScoreLD;

    private static final String TAG = GlobalSettings.class.getSimpleName();
}
