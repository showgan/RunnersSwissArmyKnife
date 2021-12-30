package com.mastegoane.runnersswissarmyknife;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        mSharedPreferences = newBase.getSharedPreferences("runnersswissarmyknife", MODE_PRIVATE);
        final int languageInt = mSharedPreferences.getInt("sp_language", GlobalSettings.Language.ADIGA.ordinal());
        GlobalSettings.inst().setLanguage(GlobalSettings.Language.values()[languageInt]);
        String localeTag;
        switch (GlobalSettings.Language.values()[languageInt]) {
            case ADIGA:
                localeTag = "ady";
                break;
            case ARABIC:
                localeTag = "ar";
                break;
            case TURKISH:
                localeTag = "tr";
                break;
            case HEBREW:
                localeTag = "iw";
                break;
            default:
                localeTag = "en";
                break;
        }
        Locale locale = Locale.forLanguageTag(localeTag);
//        Locale.getISOLanguages();
        if (locale == null) {
            locale = Locale.ENGLISH;
        }
        Log.d(TAG, "language: " + locale.getDisplayLanguage());
        Locale.setDefault(locale);
        Configuration configuration = newBase.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        Context newContext;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            newContext = newBase.createConfigurationContext(configuration);
        } else {
            Resources resources = newBase.getResources();
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
            newContext = newBase;
        }
        super.attachBaseContext(newContext);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        mPx2dpFactor = 1.0f / displayMetrics.density;
        mDp2pxFactor = displayMetrics.density;
    }

    protected SharedPreferences mSharedPreferences = null;
    protected float mPx2dpFactor = 0;
    protected float mDp2pxFactor = 0;
    private static final String TAG = BaseActivity.class.getSimpleName();
}
