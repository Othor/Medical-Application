package com.example.jamie.autosearch_test1.fragment;


import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.jamie.autosearch_test1.MainActivity;
import com.example.jamie.autosearch_test1.R;
import com.example.jamie.autosearch_test1.model.DataModel;
import com.example.jamie.autosearch_test1.settings.Settings;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HealthScoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HealthScoreFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public HealthScoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HealthScoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HealthScoreFragment newInstance(String param1, String param2) {
        HealthScoreFragment fragment = new HealthScoreFragment();
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


    @InjectView(R.id.sliding_layout)
    SlidingUpPanelLayout slidingUpPanelLayout;
    @InjectView(R.id.iv_score_bar_details)
    ImageView ivScoreBarDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("My Health Score");
        View view = inflater.inflate(R.layout.fragment_health_score, container, false);
        ButterKnife.inject(this, view);



        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View view, float v) {

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                    llBottomAction.setTranslationY(-(getResources().getDimension(R.dimen.action_bar_height) * v));
//                    etWeight.setAlpha(v);
//                    ivWCrcl.setScaleX(v);
//                    ivWCrcl.setScaleY(v);
//                    tvWCrcl.setScaleX(v);
//                    tvWCrcl.setScaleY(v);
//                }
//                tvWCrcl.setText((int)(100 - (v * 100)) + "");
                ivScoreBarDetails.setRotation(v * 360);

            }

            @Override
            public void onPanelStateChanged(View view, SlidingUpPanelLayout.PanelState panelState, SlidingUpPanelLayout.PanelState panelState1) {

            }
        });

        return view;
    }

}
