package com.mastegoane.runnersswissarmyknife;

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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DistanceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DistanceFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DistanceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DistanceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DistanceFragment newInstance(String param1, String param2) {
        DistanceFragment fragment = new DistanceFragment();
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
//        return inflater.inflate(R.layout.fragment_distance, container, false);
        mBinding = FragmentDistanceBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        ArrayList<String> timeValues = new ArrayList<>();
        ArrayList<String> distanceKmValues = new ArrayList<>();
        ArrayList<String> distanceMileValues = new ArrayList<>();
        long currentTimeMillisec = 5 * 60000; // starting from 5 minutes
        final long currentPaceMillisecPerKm = mMainViewModel.getCurrentPaceMillisecPerKm();
        for (int index = 0; index < 120; ++index) {
            final String currentTimeStr = mMainViewModel.millisecToTimeStr(currentTimeMillisec);
            timeValues.add(currentTimeStr);

            final float distanceKm = (float)currentTimeMillisec / currentPaceMillisecPerKm;
            final String distanceKmStr = String.format("%.2f", distanceKm);
            distanceKmValues.add(distanceKmStr);

            final float distanceMile = distanceKm / mMainViewModel.getKm2Mile();
            final String distanceMileStr = String.format("%.2f", distanceMile);
            distanceMileValues.add(distanceMileStr);

            currentTimeMillisec = currentTimeMillisec + 5 * 60000; // step by 5 minutes
        }
        mBinding.wheel3dViewTime.setEntries(timeValues);
        mBinding.wheel3dViewDistanceKm.setEntries(distanceKmValues);
        mBinding.wheel3dViewDistanceMile.setEntries(distanceMileValues);


        mBinding.wheel3dViewTime.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView view, int oldIndex, int newIndex) {
                mBinding.wheel3dViewDistanceKm.setCurrentIndex(newIndex);
                mBinding.wheel3dViewDistanceMile.setCurrentIndex(newIndex);
//                mCurrentIndexLD.setValue(newIndex);
                mMainViewModel.setDistanceEntryIndex(newIndex);
            }
        });
        mBinding.wheel3dViewDistanceKm.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView view, int oldIndex, int newIndex) {
                mBinding.wheel3dViewTime.setCurrentIndex(newIndex);
                mBinding.wheel3dViewDistanceMile.setCurrentIndex(newIndex);
//                mCurrentIndexLD.setValue(newIndex);
                mMainViewModel.setDistanceEntryIndex(newIndex);
            }
        });
        mBinding.wheel3dViewDistanceMile.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView view, int oldIndex, int newIndex) {
                mBinding.wheel3dViewTime.setCurrentIndex(newIndex);
                mBinding.wheel3dViewDistanceKm.setCurrentIndex(newIndex);
//                mCurrentIndexLD.setValue(newIndex);
                mMainViewModel.setDistanceEntryIndex(newIndex);
            }
        });

        mBinding.wheel3dViewTime.setCurrentIndex(mMainViewModel.getDistanceEntryIndex(), true);

        final long currentPaceMile = Math.round(currentPaceMillisecPerKm * mMainViewModel.getKm2Mile());
        mBinding.textViewPaceKm.setText(mMainViewModel.millisecToTimeStr(currentPaceMillisecPerKm));
        mBinding.textViewPaceMile.setText(mMainViewModel.millisecToTimeStr(currentPaceMile));

        final float currentSpeedKm = (float)(3600000) / currentPaceMillisecPerKm;
        final float currentSpeedMile = currentSpeedKm / mMainViewModel.getKm2Mile();
        mBinding.textViewSpeedKm.setText(String.format("%.2f", currentSpeedKm));
        mBinding.textViewSpeedMile.setText(String.format("%.2f", currentSpeedMile));
    }

    private MainViewModel mMainViewModel;
    private FragmentDistanceBinding mBinding;

//    private MutableLiveData<Integer> mCurrentIndexLD = new MutableLiveData<>();

    private static final String TAG = FragmentDistanceBinding.class.getSimpleName();

}