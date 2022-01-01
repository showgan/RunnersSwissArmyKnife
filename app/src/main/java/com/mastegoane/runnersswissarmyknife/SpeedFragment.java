package com.mastegoane.runnersswissarmyknife;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cncoderx.wheelview.OnWheelChangedListener;
import com.cncoderx.wheelview.WheelView;
import com.mastegoane.runnersswissarmyknife.databinding.FragmentDistanceBinding;
import com.mastegoane.runnersswissarmyknife.databinding.FragmentSpeedBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpeedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SpeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SpeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SpeedFragment newInstance(String param1, String param2) {
        SpeedFragment fragment = new SpeedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_speed, container, false);
        mBinding = FragmentSpeedBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        // minPerKm <--> kmPerHour
        // km <--> time
        ArrayList<String> minPerKmValues = new ArrayList<>();
        ArrayList<String> kmPerHourValues = new ArrayList<>();
        ArrayList<String> minPerMileValues = new ArrayList<>();
        ArrayList<String> milePerHourValues = new ArrayList<>();
        long currentPaceMillisecPerKm = mStaringMillisecPerKm;
        for (int index = 0; index < 1000; ++index) {
            final String minPerKm = mMainViewModel.millisecToTimeStr(currentPaceMillisecPerKm);
            minPerKmValues.add(minPerKm);

            final float currentSpeed = 60f / (currentPaceMillisecPerKm / 60000f);
            final String  kmPerHour = String.format("%.2f", currentSpeed);
            kmPerHourValues.add(kmPerHour);

            final String minPerMile = mMainViewModel.millisecToTimeStr(Math.round(currentPaceMillisecPerKm * mKm2Mile));
            minPerMileValues.add(minPerMile);

            final String milePerHour = String.format("%.2f", currentSpeed / mKm2Mile);
            milePerHourValues.add(milePerHour);

            currentPaceMillisecPerKm = currentPaceMillisecPerKm + mStepMillisecPerKm;
        }
        mBinding.wheel3dViewMinPerKm.setEntries(minPerKmValues);
        mBinding.wheel3dViewKmPerHour.setEntries(kmPerHourValues);
        mBinding.wheel3dViewMinPerMile.setEntries(minPerMileValues);
        mBinding.wheel3dViewMilePerHour.setEntries(milePerHourValues);

        mBinding.wheel3dViewMinPerKm.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView view, int oldIndex, int newIndex) {
                final String minPerKmStr = view.getItem(newIndex).toString();
                mBinding.wheel3dViewKmPerHour.setCurrentIndex(newIndex);
                mBinding.wheel3dViewMinPerMile.setCurrentIndex(newIndex);
                mBinding.wheel3dViewMilePerHour.setCurrentIndex(newIndex);
                mCurrentIndexLD.setValue(newIndex);
            }
        });

        mBinding.wheel3dViewKmPerHour.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView view, int oldIndex, int newIndex) {
                final CharSequence text = view.getItem(newIndex);
                mBinding.wheel3dViewMinPerKm.setCurrentIndex(newIndex);
                mBinding.wheel3dViewMinPerMile.setCurrentIndex(newIndex);
                mBinding.wheel3dViewMilePerHour.setCurrentIndex(newIndex);
                mCurrentIndexLD.setValue(newIndex);
            }
        });

        mBinding.wheel3dViewMinPerMile.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView view, int oldIndex, int newIndex) {
                final CharSequence text = view.getItem(newIndex);
                mBinding.wheel3dViewMinPerKm.setCurrentIndex(newIndex);
                mBinding.wheel3dViewKmPerHour.setCurrentIndex(newIndex);
                mBinding.wheel3dViewMilePerHour.setCurrentIndex(newIndex);
                mCurrentIndexLD.setValue(newIndex);
            }
        });

        mBinding.wheel3dViewMilePerHour.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView view, int oldIndex, int newIndex) {
                final CharSequence text = view.getItem(newIndex);
                mBinding.wheel3dViewMinPerKm.setCurrentIndex(newIndex);
                mBinding.wheel3dViewKmPerHour.setCurrentIndex(newIndex);
                mBinding.wheel3dViewMinPerMile.setCurrentIndex(newIndex);
                mCurrentIndexLD.setValue(newIndex);
            }
        });

        mBinding.wheel3dViewMinPerKm.setCurrentIndex(mMainViewModel.getSpeedEntryIndex(), true);
        mCurrentIndexLD.setValue(mMainViewModel.getSpeedEntryIndex());

        mCurrentIndexLD.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                final float factor = ((mStaringMillisecPerKm + integer * mStepMillisecPerKm));
                final long time1k = (long)(1f * factor);
                final long time3k = (long)(3f * factor);
                final long time5k = (long)(5f * factor);
                final long time10k = (long)(10f * factor);
                final long time21k = (long)(21f * factor);
                final long time42k = (long)(42f * factor);
                final String time1kStr = mMainViewModel.millisecToTimeStr(time1k);
                final String time3kStr = mMainViewModel.millisecToTimeStr(time3k);
                final String time5kStr = mMainViewModel.millisecToTimeStr(time5k);
                final String time10kStr = mMainViewModel.millisecToTimeStr(time10k);
                final String time21kStr = mMainViewModel.millisecToTimeStr(time21k);
                final String time42kStr = mMainViewModel.millisecToTimeStr(time42k);


                mBinding.textViewTime1k.setText(time1kStr);
                mBinding.textViewTime3k.setText(time3kStr);
                mBinding.textViewTime5k.setText(time5kStr);
                mBinding.textViewTime10k.setText(time10kStr);
                mBinding.textViewTime21k.setText(time21kStr);
                mBinding.textViewTime42k.setText(time42kStr);

                mMainViewModel.setSpeedEntryIndex(integer);
            }
        });
    }

    private MainViewModel mMainViewModel;
    private FragmentSpeedBinding mBinding;

    private final long mStaringMillisecPerKm = 2 * 60 * 1000; // 2 minutes per km
    private final long mStepMillisecPerKm = 1000; // 1000 milliseconds
    private final float mKm2Mile = 1.60934f;
    private MutableLiveData<Integer> mCurrentIndexLD = new MutableLiveData<>();

    private static final String TAG = FragmentSpeedBinding.class.getSimpleName();
}