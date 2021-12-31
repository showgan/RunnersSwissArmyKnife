package com.mastegoane.runnersswissarmyknife;

import android.os.Bundle;
import com.cncoderx.wheelview.OnWheelChangedListener;
import com.cncoderx.wheelview.WheelView;
import com.mastegoane.runnersswissarmyknife.databinding.ActivityMainBinding;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());


        final WheelView wheel3dViewMinPerKm = mBinding.wheel3dViewMinPerKm;
        final WheelView wheel3dViewKmPerHour = mBinding.wheel3dViewKmPerHour;

        // minPerKm <--> kmPerHour
        // km <--> time
        ArrayList<String> minPerKmValues = new ArrayList<>();
        ArrayList<String> kmPerHourValues = new ArrayList<>();
        long currentPaceMillisecPerKm = mStaringMillisecPerKm;
        for (int index = 0; index < 1000; ++index) {
//            final String minPerKm = String.format("%.2f", currentPace);
            final String minPerKm = String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(currentPaceMillisecPerKm),
                    TimeUnit.MILLISECONDS.toSeconds(currentPaceMillisecPerKm) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(currentPaceMillisecPerKm)));

            minPerKmValues.add(minPerKm);

            final float currentSpeed = 60f / (currentPaceMillisecPerKm / 60000f);
            final String  kmPerHour = String.format("%.2f", currentSpeed);
            kmPerHourValues.add(kmPerHour);

            currentPaceMillisecPerKm = currentPaceMillisecPerKm + mStepMillisecPerKm;
        }
        wheel3dViewMinPerKm.setEntries(minPerKmValues);
        wheel3dViewKmPerHour.setEntries(kmPerHourValues);

        wheel3dViewMinPerKm.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView view, int oldIndex, int newIndex) {
                final String minPerKmStr = view.getItem(newIndex).toString();
                mBinding.result1.setText("Selected: " + newIndex + " " + minPerKmStr);
                wheel3dViewKmPerHour.setCurrentIndex(newIndex);
                mCurrentIndex = newIndex;
                updateValues();
            }
        });

        wheel3dViewKmPerHour.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView view, int oldIndex, int newIndex) {
                final CharSequence text = view.getItem(newIndex);
                mBinding.result2.setText("Selected: " + newIndex + " " + text);
                wheel3dViewMinPerKm.setCurrentIndex(newIndex);
                mCurrentIndex = newIndex;
                updateValues();
            }
        });

        wheel3dViewMinPerKm.setCurrentIndex(240, true);
    }

    private void updateValues() {
        final float factor = ((mStaringMillisecPerKm + mCurrentIndex * mStepMillisecPerKm)  / 1000f / 60f);
        final float time1k = 1f * factor;
        final float time3k = 3f * factor;
        final float time5k = 5f * factor;
        final float time10k = 10f * factor;
        final float time21k = 21f * factor;
        final float time42k = 42f * factor;
        mBinding.textViewValue1k.setText(String.format("%2f", time1k));
        mBinding.textViewValue3k.setText(String.format("%2f", time3k));
        mBinding.textViewValue5k.setText(String.format("%2f", time5k));
        mBinding.textViewValue10k.setText(String.format("%2f", time10k));
        mBinding.textViewValue21k.setText(String.format("%2f", time21k));
        mBinding.textViewValue42k.setText(String.format("%2f", time42k));
    }

    private ActivityMainBinding mBinding;
//    private MainViewModel mMainViewModel;

//    private LiveData<Integer> mScoreLD = GlobalSettings.inst().getScore();

    private GlobalSettings.Language mLanguage = GlobalSettings.Language.ENGLISH;

    private long mStaringMillisecPerKm = 2 * 60 * 1000; // 2 minutes per km
    private long mStepMillisecPerKm = 1000; // 1000 milliseconds
    private long mCurrentIndex = 0;

    private static final String TAG = MainActivity.class.getSimpleName();
}
