package com.motoharu.cleaningapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.SQLException;

import Database.DBHelper;
import ObjectModel.User;


//This class will show all user information including addresses and name
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StatsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class StatsFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DBHelper dbhelper;
    private User user;


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatsFragment newInstance(String param1, String param2) {
        StatsFragment fragment = new StatsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public StatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        dbhelper = new DBHelper(getActivity());
        user = User.getInstance();
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stats,
                container, false);

        TextView phoneTV = (TextView) view.findViewById(R.id.phoneTV);
        phoneTV.setText(user.getphoneNumber());

        TextView nameTV = (TextView) view.findViewById(R.id.nameStats);
        nameTV.setText(user.getname());

        TextView secnameTV = (TextView) view.findViewById(R.id.secnameStats);
        secnameTV.setText(user.getsurname());

        TextView surnameTV = (TextView) view.findViewById(R.id.surnameStats);
        surnameTV.setText(user.getlastname());

        TextView accBdTV = (TextView) view.findViewById(R.id.accBdStats);
        accBdTV.setText(user.getaccBd());

        TextView accStreetTV = (TextView) view.findViewById(R.id.accStrStats);
        accStreetTV.setText(user.getaccStr());

        TextView accApTV = (TextView) view.findViewById(R.id.accApStats);
        accApTV.setText(user.getaccAp());

        TextView retBdTV = (TextView) view.findViewById(R.id.retBdStats);
        retBdTV.setText(user.getretBd());

        TextView retStrTV = (TextView) view.findViewById(R.id.retStreetStats);
        retStrTV.setText(user.getretStr());

        TextView retApTV = (TextView) view.findViewById(R.id.retApStats);
        retApTV.setText(user.getretAp());

        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
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
        public void onFragmentInteraction(Uri uri);
    }

}
