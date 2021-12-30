package com.mastegoane.runnersswissarmyknife;

import android.os.Bundle;
import com.mastegoane.runnersswissarmyknife.databinding.ActivityMainBinding;
import com.pl.wheelview.WheelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        final WheelView wheelViewTimePicker = mBinding.timePicker;

//        wheelViewTimePicker.setCyclic(true);
        ArrayList<String> list1 = new ArrayList(Arrays.asList("1", "2", "3", "4", "5", "6", "7"));
        wheelViewTimePicker.setData(list1);

        wheelViewTimePicker.setItemNumber(5);
        wheelViewTimePicker.setDefault(5);


        wheelViewTimePicker.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                mBinding.result.setText("Selected: " + text + " " + id);
            }

            @Override
            public void selecting(int id, String text) {
                mBinding.result.setText("Selecting: " + text + " " + id);
            }
        });

    }

    private ActivityMainBinding mBinding;
//    private MainViewModel mMainViewModel;

//    private LiveData<Integer> mScoreLD = GlobalSettings.inst().getScore();

    private GlobalSettings.Language mLanguage = GlobalSettings.Language.ENGLISH;


    private static final String TAG = MainActivity.class.getSimpleName();
}
