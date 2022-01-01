package com.mastegoane.runnersswissarmyknife;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
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
            timeStr = String.format("%2d:%02d:%02d",
                    hours,
                    minutes - TimeUnit.HOURS.toMinutes(hours),
                    seconds - TimeUnit.MINUTES.toSeconds(minutes));

        } else {
            timeStr = String.format("%2d:%02d",
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

    public int getDistanceEntryIndex() {
        return mDistanceEntryIndex;
    }

    public void setDistanceEntryIndex(int index) {
        this.mDistanceEntryIndex = index;
        final SharedPreferences.Editor sharedPrefEditor = mSharedPreferences.edit();
        sharedPrefEditor.putInt("sp_distance_entry_index", index);
        sharedPrefEditor.apply();
    }

    private final Application mApplication;

    private final SharedPreferences mSharedPreferences;

    private int mCurrentFragmentIndex = 1;
    private int mSpeedEntryIndex = 0;
    private int mDistanceEntryIndex = 0;

    private static final String TAG = MainViewModel.class.getSimpleName();
}
