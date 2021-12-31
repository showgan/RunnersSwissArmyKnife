package com.mastegoane.runnersswissarmyknife;

import android.os.Bundle;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
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
//            final String minPerKm = String.format("%02d:%02d",
//                    TimeUnit.MILLISECONDS.toMinutes(currentPaceMillisecPerKm),
//                    TimeUnit.MILLISECONDS.toSeconds(currentPaceMillisecPerKm) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(currentPaceMillisecPerKm)));
            final String minPerKm = millisecToTimeStr(currentPaceMillisecPerKm);
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
                wheel3dViewKmPerHour.setCurrentIndex(newIndex);
                mCurrentIndexLD.setValue(newIndex);
            }
        });

        wheel3dViewKmPerHour.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView view, int oldIndex, int newIndex) {
                final CharSequence text = view.getItem(newIndex);
                wheel3dViewMinPerKm.setCurrentIndex(newIndex);
                mCurrentIndexLD.setValue(newIndex);
            }
        });

        wheel3dViewMinPerKm.setCurrentIndex(240, true);

        mCurrentIndexLD.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
//                final float factor = ((mStaringMillisecPerKm + integer * mStepMillisecPerKm)  / 1000f / 60f);
                final float factor = ((mStaringMillisecPerKm + integer * mStepMillisecPerKm));
//                final float time1k = 1f * factor;
//                final float time3k = 3f * factor;
//                final float time5k = 5f * factor;
//                final float time10k = 10f * factor;
//                final float time21k = 21f * factor;
//                final float time42k = 42f * factor;
                final long time1k = (long)(1f * factor);
                final long time3k = (long)(3f * factor);
                final long time5k = (long)(5f * factor);
                final long time10k = (long)(10f * factor);
                final long time21k = (long)(21f * factor);
                final long time42k = (long)(42f * factor);

//                final String time1kStr = String.format("%02d:%02d",
//                        TimeUnit.MILLISECONDS.toMinutes(time1k),
//                        TimeUnit.MILLISECONDS.toSeconds(time1k) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time1k)));
                final String time1kStr = millisecToTimeStr(time1k);

//                final String time3kStr = String.format("%02d:%02d",
//                        TimeUnit.MILLISECONDS.toMinutes(time3k),
//                        TimeUnit.MILLISECONDS.toSeconds(time3k) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time3k)));
                final String time3kStr = millisecToTimeStr(time3k);

//                final String time5kStr = String.format("%02d:%02d",
//                        TimeUnit.MILLISECONDS.toMinutes(time5k),
//                        TimeUnit.MILLISECONDS.toSeconds(time5k) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time5k)));
                final String time5kStr = millisecToTimeStr(time5k);

//                final String time10kStr = String.format("%02d:%02d",
//                        TimeUnit.MILLISECONDS.toMinutes(time10k),
//                        TimeUnit.MILLISECONDS.toSeconds(time10k) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time10k)));
                final String time10kStr = millisecToTimeStr(time10k);

//                final String time21kStr = String.format("%02d:%02d",
//                        TimeUnit.MILLISECONDS.toMinutes(time21k),
//                        TimeUnit.MILLISECONDS.toSeconds(time21k) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time21k)));
                final String time21kStr = millisecToTimeStr(time21k);

//                final String time42kStr = String.format("%02d:%02d",
//                        TimeUnit.MILLISECONDS.toMinutes(time42k),
//                        TimeUnit.MILLISECONDS.toSeconds(time42k) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time42k)));
                final String time42kStr = millisecToTimeStr(time42k);


//                mBinding.textViewValue1k.setText(String.format("%2f", time1k));
//                mBinding.textViewValue3k.setText(String.format("%2f", time3k));
//                mBinding.textViewValue5k.setText(String.format("%2f", time5k));
//                mBinding.textViewValue10k.setText(String.format("%2f", time10k));
//                mBinding.textViewValue21k.setText(String.format("%2f", time21k));
//                mBinding.textViewValue42k.setText(String.format("%2f", time42k));
                mBinding.textViewValue1k.setText(time1kStr);
                mBinding.textViewValue3k.setText(time3kStr);
                mBinding.textViewValue5k.setText(time5kStr);
                mBinding.textViewValue10k.setText(time10kStr);
                mBinding.textViewValue21k.setText(time21kStr);
                mBinding.textViewValue42k.setText(time42kStr);
            }
        });
    }

    private String millisecToTimeStr(long millisec) {
        String timeStr;
        final long hours = TimeUnit.MILLISECONDS.toHours(millisec);
        final long minutes = TimeUnit.MILLISECONDS.toMinutes(millisec);
        final long seconds = TimeUnit.MILLISECONDS.toSeconds(millisec);
        if (millisec >= 3600000) {
            timeStr = String.format("%02d:%02d:%02d",
                    hours,
                    minutes - TimeUnit.HOURS.toMinutes(hours),
                    seconds - TimeUnit.MINUTES.toSeconds(minutes));
//                    seconds - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.MINUTES.toSeconds(minutes));

        } else {
//            timeStr = String.format("%02d:%02d",
//                    TimeUnit.MILLISECONDS.toMinutes(millisec),
//                    TimeUnit.MILLISECONDS.toSeconds(millisec) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisec)));
            timeStr = String.format("%02d:%02d",
                    minutes,
                    seconds - TimeUnit.MINUTES.toSeconds(minutes));
        }
        return timeStr;
    }

    private ActivityMainBinding mBinding;
//    private MainViewModel mMainViewModel;

//    private LiveData<Integer> mScoreLD = GlobalSettings.inst().getScore();

    private GlobalSettings.Language mLanguage = GlobalSettings.Language.ENGLISH;

    private final long mStaringMillisecPerKm = 2 * 60 * 1000; // 2 minutes per km
    private final long mStepMillisecPerKm = 1000; // 1000 milliseconds
    private MutableLiveData<Integer> mCurrentIndexLD = new MutableLiveData<>();

    private static final String TAG = MainActivity.class.getSimpleName();
}
