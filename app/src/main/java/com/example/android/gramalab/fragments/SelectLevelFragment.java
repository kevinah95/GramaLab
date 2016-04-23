package com.example.android.gramalab.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.example.android.gramalab.R;
import com.example.android.gramalab.activities.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SelectLevelFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SelectLevelFragment extends Fragment implements View.OnClickListener{

    public static final String TAG = SelectLevelFragment.class.getName();

    private OnFragmentInteractionListener mListener;
    private View view;
    private Animation animBtn;
    private ViewPager customViewPager;

    ButtonAnimation buttonsAnimation;

    private ImageButton btnSelectLevelFirstCycle;
    private ImageButton btnSelectLevelSecondCycle;

    private static final int POSITION_SELECT_GAME_FRAGMENT = 2;

    private static SelectLevelFragment instance = null;

    public SelectLevelFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
            if(btnSelectLevelFirstCycle != null && btnSelectLevelSecondCycle != null && instance == null)
            {
                instance = this;
                buttonsAnimation.showAnimation(btnSelectLevelFirstCycle, buttonsAnimation.animTranslate);
                buttonsAnimation.showAnimation(btnSelectLevelSecondCycle, buttonsAnimation.animTranslateInverted);
            }
            Log.d("MyFragment", "Fragment is visible.");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_select_level, container, false);
        animBtn = AnimationUtils.loadAnimation(getActivity(), R.anim.btn_scale);

        buttonsAnimation = new ButtonAnimation(getActivity()) {};
        customViewPager = (ViewPager) getActivity().findViewById(R.id.custom_view_pager);

        btnSelectLevelFirstCycle = (ImageButton) view.findViewById(R.id.btn_select_level_first_cycle);
        btnSelectLevelSecondCycle = (ImageButton) view.findViewById(R.id.btn_select_level_second_cycle);
        btnSelectLevelFirstCycle.setOnClickListener(this);
        btnSelectLevelSecondCycle.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(final View v)
    {
        v.startAnimation(animBtn);
        new CountDownTimer(250, 1)
        {
            public void onFinish()
            {
                switch (v.getId()) {
                    case R.id.btn_select_level_first_cycle:
                        // TODO: Make useful
                        MainActivity.isFirstLevel = true;
                        customViewPager.setCurrentItem(POSITION_SELECT_GAME_FRAGMENT, true);
                        Log.d(TAG, "TODO");
                        break;
                    case R.id.btn_select_level_second_cycle:
                        // TODO: Make useful
                        MainActivity.isFirstLevel = false;
                        customViewPager.setCurrentItem(POSITION_SELECT_GAME_FRAGMENT, true);
                        Log.d(TAG,"TODO");
                        break;
                }
            }
            public void onTick(long millisUntilFinished) {}
        }.start();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
