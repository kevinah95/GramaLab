package com.example.android.gramalab.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import com.example.android.gramalab.R;
import com.example.android.gramalab.activities.MainActivity;
import com.example.android.gramalab.dialogs.ViewDialog;
import com.example.android.gramalab.dialogs.ViewDialogLevel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OptionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class OptionsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private View view;

    private ImageButton btnAbout;
    private ImageButton btnLevel;
    private CheckBox checkBoxMusic;

    public OptionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_options, container, false);

        checkBoxMusic = (CheckBox) view.findViewById(R.id.checkBoxMusic);
        checkBoxMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked())
                    MainActivity.mainScreenSound.reset();
                else
                    MainActivity.mainScreenSound.stop();
            }
        });
        checkBoxMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxMusic.isChecked()){
                    // ("Checked");
                    MainActivity.mainScreenSound.start();
                    //MainActivity.mainScreenSound.setLooping(true);
                }else{
                    // ("Un-Checked");
                    MainActivity.mainScreenSound.stop();
                }
            }
        });

        btnLevel = (ImageButton) view.findViewById(R.id.btn_level);
        btnLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDialogLevel alert = new ViewDialogLevel();
                alert.showDialog(getActivity());
            }
        });

        btnAbout = (ImageButton) view.findViewById(R.id.btn_about);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDialog alert = new ViewDialog();
                alert.showDialog(getActivity());
            }
        });
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
