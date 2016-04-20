package com.example.android.gramalab.fragments;


import android.content.Context;
import android.content.Intent;
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
import com.example.android.gramalab.activities.games.CompleteGameActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SelectGameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SelectGameFragment extends Fragment implements View.OnClickListener{

    public static final String TAG = SelectGameFragment.class.getName();
    private OnFragmentInteractionListener mListener;

    private View view;
    private Animation animBtn;

    ButtonAnimation buttonsAnimation;

    private static SelectGameFragment instance = null;

    private ViewPager customViewPager;

    private ImageButton btnSelectGameComplete;
    private ImageButton btnSelectGameCorrect;
    private ImageButton btnSelectGameFractionate;
    private ImageButton btnSelectGameIdentify;
    private ImageButton btnSelectGameOrder;

    public SelectGameFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
            if(btnSelectGameComplete != null && btnSelectGameCorrect != null
                    && btnSelectGameFractionate != null && btnSelectGameIdentify != null
                    && btnSelectGameOrder != null && instance == null) {
                instance = this;
                buttonsAnimation.showAnimation(btnSelectGameComplete, buttonsAnimation.animTranslateYInverted);
                buttonsAnimation.showAnimation(btnSelectGameIdentify, buttonsAnimation.animTranslate);
                buttonsAnimation.showAnimation(btnSelectGameOrder, buttonsAnimation.animTranslateInverted);
                buttonsAnimation.showAnimation(btnSelectGameFractionate, buttonsAnimation.animTranslate);
                buttonsAnimation.showAnimation(btnSelectGameCorrect, buttonsAnimation.animTranslateY);
            }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_select_game, container, false);

        animBtn = AnimationUtils.loadAnimation(getActivity(), R.anim.btn_scale);

        buttonsAnimation = new ButtonAnimation(getActivity()) {};

        customViewPager = (ViewPager) getActivity().findViewById(R.id.custom_view_pager);

        btnSelectGameComplete = (ImageButton) view.findViewById(R.id.btn_select_game_complete);
        btnSelectGameCorrect = (ImageButton) view.findViewById(R.id.btn_select_game_correct);
        btnSelectGameFractionate = (ImageButton) view.findViewById(R.id.btn_select_game_fractionate);
        btnSelectGameIdentify = (ImageButton) view.findViewById(R.id.btn_select_game_identify);
        btnSelectGameOrder = (ImageButton) view.findViewById(R.id.btn_select_game_order);

        btnSelectGameComplete.setOnClickListener(this);
        btnSelectGameCorrect.setOnClickListener(this);
        btnSelectGameFractionate.setOnClickListener(this);
        btnSelectGameIdentify.setOnClickListener(this);
        btnSelectGameOrder.setOnClickListener(this);

        if(instance != null) {
            btnSelectGameComplete.setVisibility(View.VISIBLE);
            btnSelectGameCorrect.setVisibility(View.VISIBLE);
            btnSelectGameFractionate.setVisibility(View.VISIBLE);
            btnSelectGameIdentify.setVisibility(View.VISIBLE);
            btnSelectGameOrder.setVisibility(View.VISIBLE);
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
                    case R.id.btn_select_game_complete:
                        Intent intent = new Intent(getContext(), CompleteGameActivity.class);
                        startActivity(intent);
                        Log.d(TAG, "TODO");
                        break;
                    case R.id.btn_select_game_correct:
                        // TODO: Make useful
                        Log.d(TAG, "TODO");
                        break;
                    case R.id.btn_select_game_fractionate:
                        // TODO: Make useful
                        Log.d(TAG, "TODO");
                        break;
                    case R.id.btn_select_game_identify:
                        // TODO: Make useful
                        Log.d(TAG, "TODO");
                        break;
                    case R.id.btn_select_game_order:
                        // TODO: Make useful
                        Log.d(TAG, "TODO");
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
