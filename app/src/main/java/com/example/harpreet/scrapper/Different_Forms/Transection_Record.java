package com.example.harpreet.scrapper.Different_Forms;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.harpreet.scrapper.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Transection_Record#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Transection_Record extends Fragment implements DatePickerDialog.OnDateSetListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText datePicker;
    private static int dialogId=0;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Transection_Record() {
        // Required empty public constructor
    }


    public static Transection_Record newInstance(String param1, String param2) {
        Transection_Record fragment = new Transection_Record();
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
               View view= inflater.inflate(R.layout.transection__record, container, false);

                 datePicker= (EditText) view.findViewById(R.id.transactio_Id);

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             datePicker();
            }
        });

                return view;




    }

    public void datePicker()
    {
        DatePickerFragment pickerFragment=new DatePickerFragment();
        pickerFragment.show(getFragmentManager(),"Date");
    }
    private void setDate(Calendar calendar)
    {
        DateFormat dateformat=DateFormat.getDateInstance(DateFormat.MEDIUM);
        datePicker.setText(dateformat.format(calendar.getTime()));
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar cal=new GregorianCalendar(year,month,dayOfMonth);
        setDate(cal);

    }

    public static class DatePickerFragment extends DialogFragment
    {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {


            Calendar cal=Calendar.getInstance();

            int year=cal.get(Calendar.YEAR);
            int month=cal.get(Calendar.MONTH);
            int day=cal.get(Calendar.DAY_OF_MONTH);

            return null;
            //return new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener,year,month,day);
        }
    }
}
