package com.mastegoane.runnersswissarmyknife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.mastegoane.runnersswissarmyknife.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    public void buttonChangeLanguageClicked(View view) {
        startActivity(new Intent(this, SelectLanguageActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMainViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(MainViewModel.class);

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        final boolean firstTimeEverLanguage = mSharedPreferences.getBoolean("sp_first_time_ever_language", true);
        if (firstTimeEverLanguage) {
            startActivity(new Intent(this, SelectLanguageActivity.class));
        }

//        Configuration config = getResources().getConfiguration();
//        Toast.makeText(this, "YYY "  + config.smallestScreenWidthDp, Toast.LENGTH_LONG).show();
////        Log.d(TAG, "YYY "  + config.smallestScreenWidthDp);

        mFragmentManager = getSupportFragmentManager();

        final int fragmentIndex = mSharedPreferences.getInt("sp_fragment_index", 1);
        if (fragmentIndex == 1) {
            mBinding.radioGroupMainSelectFragment.check(R.id.radioButtonMainSpeed);
        } else {
            mBinding.radioGroupMainSelectFragment.check(R.id.radioButtonMainDistance);
        }

        final int speedEntryIndex = mSharedPreferences.getInt("sp_speed_entry_index", 240);
        mMainViewModel.setSpeedEntryIndex(speedEntryIndex);
        final int distanceEntryIndex = mSharedPreferences.getInt("sp_distance_entry_index", 5);
        mMainViewModel.setDistanceEntryIndex(distanceEntryIndex);

        selectFragment(fragmentIndex);

        mBinding.radioGroupMainSelectFragment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int fragmentIndex = 1;
                if (checkedId == R.id.radioButtonMainDistance) {
                    fragmentIndex = 2;
                }
                final SharedPreferences.Editor sharedPrefEditor = mSharedPreferences.edit();
                sharedPrefEditor.putInt("sp_fragment_index", fragmentIndex);
                sharedPrefEditor.apply();
                selectFragment(fragmentIndex);
            }
        });
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        final SharedPreferences.Editor sharedPrefEditor = mSharedPreferences.edit();
//        sharedPrefEditor.putInt("sp_speed_entry_index", mMainViewModel.getSpeedEntryIndex());
//        sharedPrefEditor.putInt("sp_distance_entry_index", mMainViewModel.getDistanceEntryIndex());
//        sharedPrefEditor.commit();
//    }

    private void selectFragment(int index) {
        final FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        switch (index) {
            case 1:
                SpeedFragment speedFragment = new SpeedFragment();
                fragmentTransaction.replace(R.id.frameLayoutMainFragment, speedFragment, null);
//                fragmentTransaction.addToBackStack("Speed");
                break;
            default:
                DistanceFragment distanceFragment = new DistanceFragment();
                fragmentTransaction.replace(R.id.frameLayoutMainFragment, distanceFragment, null);
//                fragmentTransaction.addToBackStack("Distance");
                break;
        }
        mMainViewModel.setCurrentFragmentIndex(index);
        fragmentTransaction.commit();
    }

    private MainViewModel mMainViewModel;
    private ActivityMainBinding mBinding;
    private FragmentManager mFragmentManager;

//    private LiveData<Integer> mScoreLD = GlobalSettings.inst().getScore();

    private GlobalSettings.Language mLanguage = GlobalSettings.Language.ENGLISH;

    private static final String TAG = MainActivity.class.getSimpleName();
}
