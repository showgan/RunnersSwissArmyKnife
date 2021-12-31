package com.mastegoane.runnersswissarmyknife;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class MainViewModel extends AndroidViewModel {
    public MainViewModel(@NonNull Application application) {
        super(application);
        mApplication = application;
//        mSharedPreferences = mApplication.getSharedPreferences("runnersswissarmyknife", MODE_PRIVATE);
    }

    private final Application mApplication;

    private static final String TAG = MainViewModel.class.getSimpleName();
}
