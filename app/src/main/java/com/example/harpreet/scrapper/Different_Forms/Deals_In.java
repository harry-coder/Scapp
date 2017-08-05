package com.example.harpreet.scrapper.Different_Forms;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.harpreet.scrapper.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.rey.material.widget.CheckBox;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Deals_In#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Deals_In extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private AVLoadingIndicatorView avl3;
    private DatabaseReference mdatabase;
    private HashMap<String, String> loadMap;
    private String onpquantity, plasticquantity, ironquantity, recordquantity;
    private MaterialSpinner dealer_TypeScrap, we_DealIn, VendorId;
    private CheckBox checkonp, checkplastic, checkrecord, checkglass, checkiron;

    private String name, nickname, type, location, coordinates, contact1, contact2, accountnumber, ifsc;

    SharedPreferences sharedpreferences;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button submit3;
    private EditText text_onp, text_plastic, text_iron, text_record;
    private ToggleButton onp, plastic, iron, record;
    public static final String namePrefs = "namepref";
    public static final String contactPrefs = "contactpref";

    public Deals_In() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Deals_In newInstance(String param1, String param2) {
        Deals_In fragment = new Deals_In();
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
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.deals__in, container, false);


        avl3= (AVLoadingIndicatorView) view.findViewById(R.id.avl3);
        //retrieving data


        /*SharedPreferences nprefs = getActivity().getSharedPreferences(namePrefs, MODE_PRIVATE);
        name = nprefs.getString("Name", null);
        nickname = nprefs.getString("NickName", null);
        location = nprefs.getString("Location", null);
        coordinates = nprefs.getString("Coordinates", null);
        SharedPreferences cprefs = getActivity().getSharedPreferences(contactPrefs, MODE_PRIVATE);
        contact1 = cprefs.getString("contact1", null);
        contact2 = cprefs.getString("contact2", null);

        accountnumber = cprefs.getString("accountnumber", null);
        ifsc = cprefs.getString("ifsc", null);
*/

        //edit text
        text_onp = (EditText) view.findViewById(R.id.text_onp);
        text_plastic = (EditText) view.findViewById(R.id.text_plastic);
        text_record = (EditText) view.findViewById(R.id.text_record);
        text_iron = (EditText) view.findViewById(R.id.text_iron);

        //check box
        checkonp = (CheckBox) view.findViewById(R.id.onpcheckBox);
        checkplastic = (CheckBox) view.findViewById(R.id.plasticcheckBox);
        checkrecord = (CheckBox) view.findViewById(R.id.recordcheckBox);
        checkglass = (CheckBox) view.findViewById(R.id.glasscheckBox);
        checkiron = (CheckBox) view.findViewById(R.id.ironcheckBox);


        submit3 = (Button) view.findViewById(R.id.submit3);


        plastic = (ToggleButton) view.findViewById(R.id.toggle_plastic);
        iron = (ToggleButton) view.findViewById(R.id.toggle_iron);
        record = (ToggleButton) view.findViewById(R.id.toggle_record);
        onp = (ToggleButton) view.findViewById(R.id.toggle_onp);

        loadMap = new HashMap<>();


        submit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                avl3.setVisibility(View.VISIBLE);
                avl3.smoothToShow();
                /*loadMap.put("Name", name);
                loadMap.put("Nick Name", nickname);
                loadMap.put("Type", type);
                loadMap.put("Location", location);
                loadMap.put("Coordinates", coordinates);
                loadMap.put("Contact 1", contact1);
                loadMap.put("Contact 2", contact2);
                loadMap.put("Account Number", accountnumber);
                loadMap.put("IFSC", ifsc);
*/
                SharedPreferences prefs = getActivity().getSharedPreferences(namePrefs, MODE_PRIVATE);
                String restoredText = prefs.getString("Name", null);
                mdatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://scrapper-189c9.firebaseio.com/User 1/" + restoredText);

                if (text_onp.getVisibility() == View.VISIBLE) {

                    onpquantity = text_onp.getText().toString();
                    mdatabase.child("Vendor ONP Quantity").setValue(onpquantity);

                }
                if (text_plastic.getVisibility() == View.VISIBLE) {

                    plasticquantity = text_plastic.getText().toString();
                    mdatabase.child("Vendor plastic Quantity").setValue(plasticquantity);

                }
                if (text_iron.getVisibility() == View.VISIBLE) {

                    ironquantity = text_iron.getText().toString();
                    mdatabase.child("Vendor iron Quantity").setValue(ironquantity);

                }
                if (text_record.getVisibility() == View.VISIBLE) {

                    recordquantity = text_record.getText().toString();
                    mdatabase.child("Vendor record Quantity").setValue(recordquantity);

                }


                if (checkonp.isChecked()) {


                    mdatabase.child("1-Okhlee deals ").setValue("ONP");
                }
                if (checkplastic.isChecked()) {

                    mdatabase.child("2-Okhlee deals in ").setValue("Plastic");
                }
                if (checkrecord.isChecked()) {
                    mdatabase.child("3-Okhlee deals in ").setValue("Record");

                }
                if (checkglass.isChecked()) {
                    mdatabase.child("4-Okhlee deals in ").setValue("Glass");
                } else if (checkiron.isChecked()) {
                    mdatabase.child("5-Okhlee deals in ").setValue("Iron");
                }

                /*checkonp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        {
                            mdatabase.child("Okhlee deals in ").setValue( "ONP");
                        }

                    }
                });
                checkplastic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        {
                            mdatabase.child("Okhlee deals in ").setValue( "Plastic");
                        }

                    }
                });

                checkrecord.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        {
                            mdatabase.child("Okhlee deals in ").setValue( "Record");
                        }

                    }
                });
                checkglass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        {
                            mdatabase.child("Okhlee deals in ").setValue( "Glass");
                        }

                    }
                });
                checkiron.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        {
                            mdatabase.child("Okhlee deals in ").setValue( "Iron");
                        }

                    }
                });
*/


                mdatabase.child("Date of Transaction").setValue(new SimpleDateFormat("yyyy/MM/dd_HHmmss").format(new Date())).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(getActivity(), "Data Added Successfully!!", Toast.LENGTH_SHORT).show();

                            avl3.smoothToHide();
                            ViewPager pager = (ViewPager) getActivity().findViewById(R.id.pager);

                            pager.animate();
                            pager.setCurrentItem(0, true);


                        }
                    }
                });
            }
        });

        onp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked)

                {

                    text_onp.setVisibility(View.VISIBLE);
                    text_onp.setHint("Quantity in Tons");
                    YoYo.with(Techniques.FlipInX)
                            .duration(700)
                            .repeat(1)
                            .playOn(text_onp);

                } else {
                    text_onp.setVisibility(View.INVISIBLE);
                }
            }
        });
        plastic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked)

                {
                    text_plastic.setVisibility(View.VISIBLE);
                    text_plastic.setHint("Quantity in Tons");
                    YoYo.with(Techniques.FlipInX)
                            .duration(700)
                            .repeat(1)
                            .playOn(text_plastic);

                } else {
                    text_plastic.setVisibility(View.INVISIBLE);
                }

            }
        });
        iron.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)

                {
                    text_iron.setVisibility(View.VISIBLE);
                    text_iron.setHint("Quantity in Tons");
                    YoYo.with(Techniques.FlipInX)
                            .duration(700)
                            .repeat(1)
                            .playOn(text_iron);

                } else {
                    text_iron.setVisibility(View.INVISIBLE);
                }


            }
        });
        record.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked)

                {
                    text_record.setVisibility(View.VISIBLE);
                    text_record.setHint("Quantity in Tons");
                    YoYo.with(Techniques.FlipInX)
                            .duration(700)
                            .repeat(1)
                            .playOn(text_record);

                } else {
                    text_record.setVisibility(View.INVISIBLE);
                }

            }
        });



        /*dealer_TypeScrap = (MaterialSpinner)view. findViewById(R.id.scrap_type);
        dealer_TypeScrap.setItems("ONP", "Plastics", "Iron", "Bottle", "Glass","Iron");
        dealer_TypeScrap.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

        we_DealIn = (MaterialSpinner)view. findViewById(R.id.we_deal);
        we_DealIn.setItems("ONP", "Record");
        we_DealIn.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

        VendorId = (MaterialSpinner)view. findViewById(R.id.vendor_id);
        VendorId.setItems("Aadhar", "PAN", "Photo", "Thumb");
        VendorId.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

*/


        return view;
    }

    private void setDataForVendorId(Spinner spinner) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }

        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add("PAN");
        adapter.add("Aadhar");
        adapter.add("Photo");
        adapter.add("Thumb");

        adapter.add("What are we dealing into"); //This is the text that will be displayed as hint.

        spinner.setAdapter(adapter);


    }

    private void setDataForWhatWeDzealSpinner(Spinner spinner) {


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }

        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add("ONP");
        adapter.add("Record");

        adapter.add("What are we dealing into"); //This is the text that will be displayed as hint.

        spinner.setAdapter(adapter);

    }

    private void setDataForDealerTypeSpinner(Spinner spinner) {


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }

        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add("ONP");
        adapter.add("Record");
        adapter.add("Plastic");
        adapter.add("Bottle");
        adapter.add("Glass");
        adapter.add("Iron");
        adapter.add("Deals in type odf scrap"); //This is the text that will be displayed as hint.

        spinner.setAdapter(adapter);
    }

}
