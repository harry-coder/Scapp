package com.example.harpreet.scrapper.Different_Forms;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.harpreet.scrapper.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link location#newInstance} factory method to
 * create an instance of this fragment.
 */
public class location extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int RequestCode=9;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText location,coordinate;

    private Intent intent;

    public location() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static location newInstance(String param1, String param2) {
        location fragment = new location();
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

        View view = inflater.inflate(R.layout.location, container, false);

        location = (EditText) view.findViewById(R.id.location);

        coordinate= (EditText) view.findViewById(R.id.coordinate);


        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();





                try {
                    intent=builder.build(getActivity());

                    startActivityForResult(intent,RequestCode);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==RequestCode)
        {
            if(resultCode==RESULT_OK)
            {
                Place place=PlacePicker.getPlace(getActivity(),data);

                String address= (String) place.getAddress();

                LatLng lat_lon=place.getLatLng();

                location.setText(address);



                Toast.makeText(getActivity(), "Your latitude and longitude"+lat_lon, Toast.LENGTH_SHORT).show();
            }
        }
        else

        super.onActivityResult(requestCode, resultCode, data);


    }
}
