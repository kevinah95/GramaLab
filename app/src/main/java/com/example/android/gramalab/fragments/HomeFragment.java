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
import com.example.android.gramalab.views.PetsView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{

    public static final String TAG = HomeFragment.class.getName();
    private OnFragmentInteractionListener mListener;

    private View view;
    private Animation animBtn;
    private ViewPager customViewPager;

    private ImageButton homeButton;
    private ImageButton optionsButton;
    private ImageButton exitButton;

    private Animation animTranslate;
    private Animation animTranslateInverted;
    private Animation animTranslateY;

    private PetsView petsViewPoint;
    private PetsView petsViewAccent;
    private PetsView petsViewComma;

    private static final int POSITION_SELECT_LEVEL_FRAGMENT = 1;

    private static HomeFragment instance = null;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        animBtn = AnimationUtils.loadAnimation(getActivity(), R.anim.btn_scale);

        createPets();

        homeButton = (ImageButton) view.findViewById(R.id.btn_play);
        optionsButton = (ImageButton) view.findViewById(R.id.btn_options);
        exitButton = (ImageButton) view.findViewById(R.id.btn_exit);

        animTranslate = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_translate);
        animTranslateInverted = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_translate_reverse);
        animTranslateY = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_translate_y);

        if(instance == null) {
            instance = this;
            showAnimation(homeButton, animTranslate);
            showAnimation(optionsButton, animTranslateInverted);
            showAnimation(exitButton, animTranslateY);
        }
        else {
            homeButton.setVisibility(View.VISIBLE);
            optionsButton.setVisibility(View.VISIBLE);
            exitButton.setVisibility(View.VISIBLE);
        }


        // Solution -> http://stackoverflow.com/a/12153121/4752488
        customViewPager = (ViewPager) getActivity().findViewById(R.id.custom_view_pager);

        homeButton.setOnClickListener(this);
        optionsButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);

        return view;
    }

    public void createPets() {
        petsViewPoint = (PetsView) view.findViewById(R.id.petsViewPoint);
        petsViewPoint.setWIDTH_POSITON_PORCENTAGE(0.70f);
        petsViewPoint.setHEIGHT_POSITON_PORCENTAGE(0.13f);
        petsViewPoint.setVECTOR_SCALABLE_PORCENTAGE(0.17F);

        petsViewAccent = (PetsView) view.findViewById(R.id.petsViewAccent);
        petsViewAccent.setWIDTH_POSITON_PORCENTAGE(0.45f);
        petsViewAccent.setHEIGHT_POSITON_PORCENTAGE(0.26f);
        petsViewAccent.setVECTOR_SCALABLE_PORCENTAGE(0.105f);

        petsViewComma = (PetsView) view.findViewById(R.id.petsViewComma);
        petsViewComma.setWIDTH_POSITON_PORCENTAGE(0.24f);
        petsViewComma.setHEIGHT_POSITON_PORCENTAGE(0.15f);
        petsViewComma.setVECTOR_SCALABLE_PORCENTAGE(0.105f);
    }

    public void showAnimation(final ImageButton pButton, final Animation pAnimationType) {

        new CountDownTimer(500, 1) {
            public void onFinish() {
                pButton.startAnimation(pAnimationType);
                new CountDownTimer(500, 1) {
                    public void onFinish() {
                        pButton.setVisibility(View.VISIBLE);
                    }
                    public void onTick(long millisUntilFinished) {}
                }.start();
            }
            public void onTick(long millisUntilFinished) {}
        }.start();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(final View v) {
        v.startAnimation(animBtn);
        new CountDownTimer(250, 1) {
            public void onFinish() {
                switch (v.getId()) {
                    case R.id.btn_play:
                        customViewPager.setCurrentItem(POSITION_SELECT_LEVEL_FRAGMENT, true);
                        break;
                    case R.id.btn_options:
                        // TODO: Make useful
                        Log.d(TAG,"TODO");
                        break;
                    case R.id.btn_exit:
                        getActivity().finish();
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
