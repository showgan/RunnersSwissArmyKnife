package com.mastegoane.runnersswissarmyknife;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainViewModel extends AndroidViewModel {
    public MainViewModel(@NonNull Application application) {
        super(application);
        mApplication = application;
        mSharedPreferences = mApplication.getSharedPreferences("runnersswissarmyknife", MODE_PRIVATE);
    }

    public String millisecToTimeStr(long millisec) {
        String timeStr;
        final long hours = TimeUnit.MILLISECONDS.toHours(millisec);
        final long minutes = TimeUnit.MILLISECONDS.toMinutes(millisec);
        final long seconds = TimeUnit.MILLISECONDS.toSeconds(millisec);
        if (millisec >= 3600000) {
            timeStr = String.format(Locale.ENGLISH, "%2d:%02d:%02d",
                    hours,
                    minutes - TimeUnit.HOURS.toMinutes(hours),
                    seconds - TimeUnit.MINUTES.toSeconds(minutes));

        } else {
            timeStr = String.format(Locale.ENGLISH, "%2d:%02d",
                    minutes,
                    seconds - TimeUnit.MINUTES.toSeconds(minutes));
        }
        return timeStr;
    }

    public int getCurrentFragmentIndex() {
        return mCurrentFragmentIndex;
    }

    public void setCurrentFragmentIndex(int index) {
        this.mCurrentFragmentIndex = index;
    }

    public int getSpeedEntryIndex() {
        return mSpeedEntryIndex;
    }

    public void setSpeedEntryIndex(int index) {
        this.mSpeedEntryIndex = index;
        final SharedPreferences.Editor sharedPrefEditor = mSharedPreferences.edit();
        sharedPrefEditor.putInt("sp_speed_entry_index", index);
        sharedPrefEditor.apply();
    }

    public long getCurrentPaceMillisecPerKm() {
        return mSpeedEntryIndex * mStepMillisecPerKm + mStaringMillisecPerKm;
    }

    public int getDistanceEntryIndex() {
        return mDistanceEntryIndex;
    }

    public void setDistanceEntryIndex(int index) {
        this.mDistanceEntryIndex = index;
        final SharedPreferences.Editor sharedPrefEditor = mSharedPreferences.edit();
        sharedPrefEditor.putInt("sp_distance_entry_index", index);
        sharedPrefEditor.apply();
    }

    public long getStaringMillisecPerKm() {
        return mStaringMillisecPerKm;
    }

    public long getStepMillisecPerKm() {
        return mStepMillisecPerKm;
    }

    public float getKm2Mile() {
        return mKm2Mile;
    }

    private final Application mApplication;

    private final SharedPreferences mSharedPreferences;

    private int mCurrentFragmentIndex = 1;
    private int mSpeedEntryIndex = 0;

    private int mDistanceEntryIndex = 0;

    private final long mStaringMillisecPerKm = 2 * 60 * 1000; // 2 minutes per km
    private final long mStepMillisecPerKm = 1000;

    private final float mKm2Mile = 1.60934f;

    private static final String TAG = MainViewModel.class.getSimpleName();
}
