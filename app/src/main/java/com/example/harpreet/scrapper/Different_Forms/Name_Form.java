package com.example.harpreet.scrapper.Different_Forms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.harpreet.scrapper.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.Random;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


public class Name_Form extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private HashMap<String, String> loadMap;
    private SharedPreferences mobileNumberpref;
    private String mypref = "usercontact";
    String usercontact;

    // private ArrayList<String> spinnerItems=new ArrayList<>();
    private String vendorlocation, vendorcoordinates, vendorname, vendornickname, vendortype;

    private DatabaseReference mdatabase;

    SharedPreferences sharedpreferences;
    public static final String namePrefs = "namepref";
    private EditText location, coordinates, name, nickname;
    private MaterialSpinner spinner;
    private Button submit;
    AVLoadingIndicatorView avi;


    private Intent intent;
    private int RequestCode = 10;

    public Name_Form() {
        // Required empty public constructor
    }


    public static Name_Form newInstance(String param1, String param2) {
        Name_Form fragment = new Name_Form();
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
        mobileNumberpref = getActivity().getSharedPreferences(mypref, MODE_PRIVATE);
        usercontact = mobileNumberpref.getString("contact", null);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.name__form, container, false);
        location = (EditText) view.findViewById(R.id.location);
        coordinates = (EditText) view.findViewById(R.id.city_coordinates);

        loadMap = new HashMap<>();
        submit = (Button) view.findViewById(R.id.submit);
        name = (EditText) view.findViewById(R.id.username);
        nickname = (EditText) view.findViewById(R.id.usernickname);
        avi = (AVLoadingIndicatorView) view.findViewById(R.id.avi);

        sharedpreferences = getActivity().getSharedPreferences(namePrefs, MODE_PRIVATE);

        mdatabase = FirebaseDatabase.getInstance().getReference(usercontact);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(validation()) {
                   enableButton();
               }
               }
        });


        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    intent = builder.build(getActivity());

                    startActivityForResult(intent, RequestCode);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        spinner = (MaterialSpinner) view.findViewById(R.id.spinner);

        spinner.setItems("L1", "L2", "L3", "Bottle", "Trader", "Corporate", "Others");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                vendortype = item.toString();
            }
        });


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return view;
    }

    private void enableButton() {


        avi.setVisibility(View.VISIBLE);
        avi.smoothToShow();


        vendorname = name.getText().toString();
        vendornickname = nickname.getText().toString();
        vendorlocation = location.getText().toString();
        vendorcoordinates = coordinates.getText().toString();

        // long a = new Random().nextInt(1000000000);
        String randomName = vendorname + System.currentTimeMillis();

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("Name", randomName);
                /*editor.putString("NickName", vendornickname);

                editor.putString("Location",vendorlocation);

                editor.putString("Coordinates", vendorcoordinates);*/
        editor.apply();


        loadMap.put("Name", vendorname);
        loadMap.put("Nick Name", vendornickname);
        loadMap.put("Type", vendortype);
        loadMap.put("Location", vendorlocation);
        loadMap.put("Coordinates", vendorcoordinates);

        mdatabase.child(randomName).setValue(loadMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    avi.hide();
                    Toast.makeText(getActivity(), "Successfully Submitted.", Toast.LENGTH_SHORT).show();

                    ViewPager pager = (ViewPager) getActivity().findViewById(R.id.pager);


                    pager.setCurrentItem(1, true);

                    clearFields(location, name, nickname, coordinates);

                }
            }
        });


    }
        /*});*/


    /*public boolean runTimePermission() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, 100);

            return true;
        }
        return false;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
                enableButton();
            } else {
                runTimePermission();
            }

        }

    }
*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RequestCode) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getActivity(), data);

                String address = (String) place.getAddress();

                LatLng lat_lon = place.getLatLng();


                location.setText(address);

                coordinates.setText(String.valueOf(lat_lon));


                //  Toast.makeText(getActivity(), "Your latitude and longitude" + lat_lon, Toast.LENGTH_SHORT).show();
            }
        } else

            super.onActivityResult(requestCode, resultCode, data);


    }

    public void clearFields(View... view) {

        location.getText().clear();
        name.getText().clear();
        nickname.getText().clear();
        coordinates.getText().clear();


    }

    public boolean validation()
    {
        boolean valid =true;
        vendorname = name.getText().toString();
        vendornickname = nickname.getText().toString();
        vendorlocation = location.getText().toString();
        vendorcoordinates = coordinates.getText().toString();

        if(TextUtils.isEmpty(vendorname))
        {
            name.setError("Name cannot be empty");
            name.requestFocus();
            valid=false;
            return valid;
        }
        if(TextUtils.isDigitsOnly(vendorname))
        {
            name.setError("Name cannot be numeric");
            name.requestFocus();
            valid=false;
            return valid;
        }
        if(TextUtils.isEmpty(vendorlocation))
        {
            location.setError("Please select location");
            location.requestFocus();
            valid=false;
            return valid;
        }

        if(TextUtils.isEmpty(vendorcoordinates))
        {
            coordinates.setError("Coordinates cannot be empty");
            coordinates.requestFocus();
            valid=false;
            return valid;
        }
        return valid;

    }

}


