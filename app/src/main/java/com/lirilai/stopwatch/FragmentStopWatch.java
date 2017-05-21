package com.lirilai.stopwatch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lirilai on 21.05.2017.
 */

public class FragmentStopWatch extends Fragment {

    public FragmentStopWatch newInstance() {

        FragmentStopWatch fragment = new FragmentStopWatch();
        Bundle bundle = new Bundle();

        fragment.setArguments(bundle);
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
