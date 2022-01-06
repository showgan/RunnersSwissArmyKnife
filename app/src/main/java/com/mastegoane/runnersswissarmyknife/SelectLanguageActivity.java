package com.mastegoane.runnersswissarmyknife;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.mastegoane.runnersswissarmyknife.databinding.ActivitySelectLanguageBinding;

public class SelectLanguageActivity extends BaseActivity {

    public void buttonLanguageClicked(View view) {
        mRestart = false;
        mBinding.buttonSelectLanguageEnglish.setSelected(false);
        mBinding.buttonSelectLanguageArabic.setSelected(false);
        mBinding.buttonSelectLanguageHebrew.setSelected(false);
        mBinding.buttonSelectLanguageTurkish.setSelected(false);
        mBinding.buttonSelectLanguageAdiga.setSelected(false);
        mLanguage = GlobalSettings.Language.ENGLISH;
        if (view == mBinding.buttonSelectLanguageArabic) {
            mLanguage = GlobalSettings.Language.ARABIC;
        } else if (view == mBinding.buttonSelectLanguageTurkish) {
            mLanguage = GlobalSettings.Language.TURKISH;
        } else if (view == mBinding.buttonSelectLanguageHebrew) {
            mLanguage = GlobalSettings.Language.HEBREW;
        } else if (view == mBinding.buttonSelectLanguageAdiga) {
            mLanguage = GlobalSettings.Language.ADIGA;
        }
        view.setSelected(true);
        mBinding.buttonSelectLanguageSelect.setEnabled(true);
    }

    public void buttonSelectClicked(View view) {
        mRestart = true;
        mBinding.buttonSelectLanguageSelect.setEnabled(false);
//        final SharedPreferences sharedPreferences = getSharedPreferences("runnersswissarmyknife", MODE_PRIVATE);
//        final SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        final SharedPreferences.Editor sharedPrefEditor = mSharedPreferences.edit();
        sharedPrefEditor.putInt("sp_language", mLanguage.ordinal());
        GlobalSettings.inst().setLanguage(mLanguage);
        sharedPrefEditor.putBoolean("sp_first_time_ever_language", false);
        sharedPrefEditor.apply();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySelectLanguageBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mRestart = false;
        mBinding.buttonSelectLanguageEnglish.setSelected(false);
        mBinding.buttonSelectLanguageArabic.setSelected(false);
        mBinding.buttonSelectLanguageHebrew.setSelected(false);
        mBinding.buttonSelectLanguageTurkish.setSelected(false);
        mBinding.buttonSelectLanguageAdiga.setSelected(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRestart) {
            // Now restart the app
            Intent intent1 = new Intent(this, MainActivity.class);
//            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent1.putExtra(KEY_RESTART_INTENT, nextIntent);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            this.startActivity(intent1);
            if (this instanceof Activity) {
                ((Activity) this).finish();
            }
            Runtime.getRuntime().exit(0);
        }
    }

    private ActivitySelectLanguageBinding mBinding;
    private GlobalSettings.Language mLanguage = GlobalSettings.Language.TURKISH;
    private boolean mRestart = false;
    private static final String TAG = SelectLanguageActivity.class.getSimpleName();
}
