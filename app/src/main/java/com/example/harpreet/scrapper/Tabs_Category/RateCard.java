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

import com.example.harpreet.scrapper.Different_Forms.Contact_Forms;
import com.example.harpreet.scrapper.Different_Forms.Deals_In;
import com.example.harpreet.scrapper.Different_Forms.Name_Form;
import com.example.harpreet.scrapper.R;
import com.example.harpreet.scrapper.RateCardFragments.LastWeek;
import com.example.harpreet.scrapper.RateCardFragments.TodayRate;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RateCard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RateCard extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ViewPager pager;
    private TabLayout tabs;


    public RateCard() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RateCard newInstance(String param1, String param2) {
        RateCard fragment = new RateCard();
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

        View view = inflater.inflate(R.layout.fragment_rate_card, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(new myAdapter(getFragmentManager()));
        //  pager.setPageTransformer(true, new ZoomOutSlideTransformer());
        tabs = (TabLayout) view.findViewById(R.id.tablayout);
        tabs.setupWithViewPager(pager);

        return view;
    }

    class myAdapter extends FragmentStatePagerAdapter {


        public myAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                return TodayRate.newInstance("", "");

            } else if (position == 1) {

                return LastWeek.newInstance("", "");
            } else return null;


        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if (position == 0) {
                return "Today's Rate";
            } else if (position == 1) {
                return "Last Week ";
            }
            else return null;


        }


    }


}
