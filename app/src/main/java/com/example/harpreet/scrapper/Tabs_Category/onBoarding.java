package com.example.harpreet.scrapper.Tabs_Category;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.example.harpreet.scrapper.Different_Forms.Contact_Forms;
import com.example.harpreet.scrapper.Different_Forms.Deals_In;
import com.example.harpreet.scrapper.Different_Forms.Name_Form;
import com.example.harpreet.scrapper.Different_Forms.location;
import com.example.harpreet.scrapper.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link onBoarding#newInstance} factory method to
 * create an instance of this fragment.
 */
public class onBoarding extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private  ViewPager pager;
    private TabLayout tabs;


    public onBoarding() {
        // Required empty public constructor
    }

    public static onBoarding newInstance(String param1, String param2) {
        onBoarding fragment = new onBoarding();
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
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.on_boarding, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
       // ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Account");
/*


        spinner= (Spinner) view.findViewById(R.id.spinner);
        spinnerItems.add("Type1");
        spinnerItems.add("Type2");
        spinnerItems.add("Type3");
        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,spinnerItems);
        spinner.setAdapter(adapter);
*/

        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(new myAdapter(getFragmentManager()));
        //  pager.setPageTransformer(true, new ZoomOutSlideTransformer());
        tabs = (TabLayout) view.findViewById(R.id.tablayout);
        tabs.setupWithViewPager(pager);
        pager.setPageTransformer(true, new RotateUpTransformer());



        return view;
    }

    class myAdapter extends FragmentStatePagerAdapter {


        public myAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {


            if (position == 0) {
                return Name_Form.newInstance("", "");
                //  return null;
            } else if (position == 1)

            {
                //return new category();
                //return null;
                return Contact_Forms.newInstance("", "");
            } else if (position == 2) {
                return Deals_In.newInstance("", "");
            } else return null;


        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if (position == 0) {
                return "Basic";
            } else if (position == 1) {
                return "Contact ";
            } else if (position == 2) {
                return "Deals In";
            } else return null;


        }


    }


}
