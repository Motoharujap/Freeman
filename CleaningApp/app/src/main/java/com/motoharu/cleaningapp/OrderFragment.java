package com.motoharu.cleaningapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import Database.DBHelper;
import Logic.Dialogs;
import Logic.InterfaceDialogCallback;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OrderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class OrderFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String FILL_DATA = "dont fill data";
    //variables
    private InterfaceDialogCallback _yesCallback, _noCallback;
    // TODO: Rename and change types of parameters
    int empty = 0;
    private String mParam1;
    private String mParam2;
    public static int count = 0;
    public static int statusCount = 0;
    private static int summCount = 0;
    DBHelper mDBhelper;
    TextView summ, shirtsQ;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }
        mDBhelper = new DBHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order,
                container, false);

        statusCount = 0;
        //Order status buttons, just background changing, no clicking function
        Button statOne = (Button) view.findViewById(R.id.status_one);
        Button statTwo = (Button) view.findViewById(R.id.status_two);
        Button statThree = (Button) view.findViewById(R.id.status_three);
        final Button statusShow = (Button) view.findViewById(R.id.statusShow);
        final LinearLayout status = (LinearLayout) view.findViewById(R.id.statusLayout);
        statusShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statusCount==0) {
                    status.setVisibility(View.VISIBLE);
                    statusCount++;
                }else {
                    status.setVisibility(View.GONE);
                    statusCount = 0;
                }
            }
        });
        Button plus = (Button) view.findViewById(R.id.plusButton);
        Button sendOrder = (Button) view.findViewById(R.id.sendOrder);
        final Button minus = (Button) view.findViewById(R.id.minusButton);
        shirtsQ = (TextView) view.findViewById(R.id.shirtsQ);
        summ = (TextView) view.findViewById(R.id.summTV);
        String test = summ.getText().toString();
        int summInt = Integer.parseInt(summ.getText().toString());
        if (test.equals(0)) {
            minus.setEnabled(false);
            sendOrder.setEnabled(false);
        }
        else {
            minus.setEnabled(true);
            sendOrder.setEnabled(true);
        }

        sendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDBhelper = new DBHelper(getActivity());
                Cursor cursor = mDBhelper.getAllUsers();
                while (cursor.moveToNext()) {
                    empty = cursor.getColumnIndex(DBHelper.RETURN_ADDRESS_AP);
                }
                if (empty == 0) {
                    Intent intent = new Intent(getActivity(), Confirm_order.class);
                    intent.putExtra("SUMM", summCount);
                    intent.putExtra("SHIRTSQ", count);
                    count = 0;
                    summCount = 0;
                    startActivity(intent);
                } else {
                    _yesCallback = new InterfaceDialogCallback() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getActivity(), Confirm_order.class);
                            intent.putExtra("SUMM", summCount);
                            intent.putExtra(FILL_DATA, true);
                            intent.putExtra("SHIRTSQ", count);
                            count = 0;
                            summCount = 0;
                            startActivity(intent);
                        }
                    };
                    _noCallback = new InterfaceDialogCallback() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getActivity(), Confirm_order.class);
                            intent.putExtra("SUMM", summCount);
                            intent.putExtra(FILL_DATA, false);
                            intent.putExtra("SHIRTSQ", count);
                            count = 0;
                            summCount = 0;
                            startActivity(intent);
                        }
                    };
                    Dialogs.makeYesNoDialog(getActivity(), "Внимание!", "Воспользоваться данными профиля для формирования заказа?", _yesCallback, _noCallback);
                }
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minus.setEnabled(true);
                count++;
                summCount = summCount + 500;
                shirtsQ.setText(String.valueOf(count));
                summ.setText(summCount + "руб.");
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     if (count > 0) {
                         count--;
                         summCount = summCount - 500;
                         shirtsQ.setText(String.valueOf(count));
                         summ.setText(summCount + "руб.");
                     }
                     else
                         minus.setEnabled(false);
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
