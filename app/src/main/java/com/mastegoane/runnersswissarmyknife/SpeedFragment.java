package com.mastegoane.runnersswissarmyknife;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
    private MainViewModel mMainViewModel;
    private FragmentSpeedBinding mBinding;
    private static final String TAG = FragmentSpeedBinding.class.getSimpleName();
}