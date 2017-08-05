package com.example.harpreet.scrapper.Tabs_Category;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.CubeInTransformer;
import com.ToxicBakery.viewpager.transforms.FlipVerticalTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;
import com.example.harpreet.scrapper.Different_Forms.Contact_Forms;
import com.example.harpreet.scrapper.Different_Forms.Cost_Camera;
import com.example.harpreet.scrapper.Different_Forms.Deals_In;
import com.example.harpreet.scrapper.Different_Forms.Name_Form;
import com.example.harpreet.scrapper.Different_Forms.Rate_Weight;
import com.example.harpreet.scrapper.Different_Forms.Transection_Record;
import com.example.harpreet.scrapper.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Transactions#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Transactions extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int  count=0;
    private ImageView tPrevious;
    private ImageView tNext;

    private ViewPager transaction_pager;
    private TabLayout transaction_tabs;


    public Transactions() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Transactions newInstance(String param1, String param2) {
        Transactions fragment = new Transactions();
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

        View view= inflater.inflate(R.layout.transactions, container, false);

        transaction_pager = (ViewPager)view. findViewById(R.id.trasaction_pager);
        transaction_pager.setAdapter(new myAdapter(getFragmentManager()));
        //  pager.setPageTransformer(true, new ZoomOutSlideTransformer());
        transaction_tabs = (TabLayout) view.findViewById(R.id.tablayout);
        transaction_tabs.setupWithViewPager(transaction_pager);
        transaction_pager.setPageTransformer(true, new CubeInTransformer());




        /*tNext= (ImageView) view.findViewById(R.id.tNext);
        tPrevious= (ImageView) view.findViewById(R.id.tPrevious);

        getFragmentManager().beginTransaction().add(R.id.transaction_Section,new Transection_Record()).commit();


        tNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(count==0)
                {
                  //  Toast.makeText(getActivity(), "Inside 0", Toast.LENGTH_SHORT).show();

                   *//* FragmentManager fm = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    FragmentTransactionExtended fragmentTransactionExtended = new FragmentTransactionExtended(getActivity(), fragmentTransaction, new Transection_Record(), new Rate_Weight(), R.id.transaction_Section);
                    fragmentTransactionExtended.addTransition(FragmentTransactionExtended.GLIDE);
                    fragmentTransactionExtended.commit();*//*
                    getFragmentManager().beginTransaction().replace(R.id.transaction_Section,new Rate_Weight()).commit();


                    count++;
                }
               else if(count==1)
                {
                    getFragmentManager().beginTransaction().replace(R.id.transaction_Section,new Cost_Camera()).commit();


                }





            }
        });*/
        return view;

    }
    class myAdapter extends FragmentStatePagerAdapter {


        public myAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {


            if (position == 0) {
                return new Transection_Record();
                //  return null;
            } else if (position==1)

            {
                //return new category();
                //return null;
                return new Rate_Weight();
            }
            else if(position==2)
            {
                return  new Cost_Camera();
            }
            else return null;


        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if (position == 0) {
                return "Transaction Record";
            } else if (position==1){
                return "Rate";
            }
            else if(position==2)
            {
                return "Upload";
            }
            else return null;



        }


    }

}
