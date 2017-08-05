package com.example.harpreet.scrapper.RateCardFragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.harpreet.scrapper.CustomElements.CustomFontTextView;
import com.example.harpreet.scrapper.CustomElements.CustomToast;
import com.example.harpreet.scrapper.CustomElements.RateCardElements;
import com.example.harpreet.scrapper.R;
import com.example.harpreet.scrapper.volly.MyApplication;
import com.example.harpreet.scrapper.volly.vollySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.zip.Inflater;


public class TodayRate extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<String> quantity;
    private String mParam1;
    private String mParam2;
    private Button bt_response;
    private vollySingleton volly;
    private RequestQueue queue;
    private RecyclerView rw_ratecard;
    private ArrayList<RateCardElements> globalList;
    private rateCardAdapter adapter;
    private ProgressDialog progressDialog;
    private RecyclerView rv_horizontal;
    private quantityAdapter quantityadapter;


    public TodayRate() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TodayRate newInstance(String param1, String param2) {
        TodayRate fragment = new TodayRate();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void addQuantity() {
        quantity = new ArrayList<>();
        quantity.add("15 MT");
        quantity.add("30 MT");
        quantity.add("45 MT");
        quantity.add("60 MT");
        quantity.add("75 MT");
        quantity.add("90 MT");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        volly = vollySingleton.getInstance();
        queue = volly.getRequestQueue();
        globalList = new ArrayList<>();
        progressDialog = new ProgressDialog(getActivity());
        addQuantity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_today_rate, container, false);

        rw_ratecard = (RecyclerView) view.findViewById(R.id.rw_ratecard);

        quantityadapter = new quantityAdapter(getActivity(), quantity);
        rv_horizontal = (RecyclerView) view.findViewById(R.id.rv_horizontal);
        rv_horizontal.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rv_horizontal.setAdapter(quantityadapter);


        getSheetResponse(MyApplication.sheet1, "15");
        adapter = new rateCardAdapter(getActivity());


        rw_ratecard.setLayoutManager(new LinearLayoutManager(getActivity()));
        rw_ratecard.setAdapter(adapter);




        /*bt_response= (Button) view.findViewById(R.id.response);

        bt_response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSheetResponse();

            }
        });*/

        return view;
    }


    public void getSheetResponse(final String url, final String sheet) {
        progressDialog.setMessage("Loading latest rates..");
        progressDialog.show();
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                final JsonObjectRequest jsonRequest = new JsonObjectRequest(JsonObjectRequest.Method.GET, url,

                        null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (response == null && response.length() == 0) {
                            return;
                        } else {
                            try {

                                globalList = getSheetData(response, sheet);
                                adapter.setSource(globalList);

                                progressDialog.dismiss();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                queue.add(jsonRequest);


                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


    }

    private ArrayList<RateCardElements> getSheetData(JSONObject response, String sheet) throws JSONException {

        ArrayList<RateCardElements> rateCardObjectList = new ArrayList<>();
        String millName, rate, distance, recommend = null;
        JSONArray jsonSheetArray = response.getJSONArray(sheet);

        for (int i = 0; i < jsonSheetArray.length(); i++) {
            JSONObject sheetDataObject = jsonSheetArray.getJSONObject(i);


            millName = sheetDataObject.getString("Mill_name");
            rate = sheetDataObject.getString("Rate");
            distance = sheetDataObject.getString("Distance");


            RateCardElements sheetDataElements = new RateCardElements();

            sheetDataElements.setMillName(millName);
            sheetDataElements.setRate(rate);
            sheetDataElements.setDistance(distance);

            if (!sheetDataObject.isNull("Recommend")) {


                recommend = sheetDataObject.getString("Recommend");
                sheetDataElements.setRecommend(recommend);
            }


            rateCardObjectList.add(sheetDataElements);
        }

        return rateCardObjectList;

    }


    class rateCardAdapter extends RecyclerView.Adapter<rateCardAdapter.myHolder> {

        LayoutInflater inflator;
        Context context;
        ArrayList<RateCardElements> dataSourceList = new ArrayList<>();

        rateCardAdapter(Context context) {
            this.context = context;
            inflator = LayoutInflater.from(context);
        }

        @Override
        public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View view = inflator.inflate(R.layout.ratecardsingleitem, parent, false);

            return new myHolder(view);
        }

        void setSource(ArrayList<RateCardElements> list) {
            this.dataSourceList = list;
            notifyItemRangeChanged(0, list.size());
        }

        @Override
        public void onBindViewHolder(myHolder holder, int position) {

            RateCardElements data = dataSourceList.get(position);
            holder.tv_millName.setText(data.getMillName());
            holder.tv_distance.setText(String.valueOf(data.getDistance()));
            holder.tv_rate.setText(String.valueOf(" ₹ " + data.getRate() + "/kg"));

            holder.tv_recommended.setVisibility(View.GONE);
            if (data.getRecommend() != null && data.getRecommend().length() != 0 && !data.getRecommend().isEmpty()) {
                System.out.println(data.getMillName() + position);
                holder.tv_recommended.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public int getItemCount() {


            return dataSourceList.size();
        }

        class myHolder extends RecyclerView.ViewHolder {


            TextView tv_millName, tv_recommended, tv_rate, tv_distance;
            ImageView im_details;
            AlertDialog dialog = null;


            Button bt_cancel, bt_call;


            myHolder(View itemView) {
                super(itemView);
                tv_millName = (TextView) itemView.findViewById(R.id.tv_millname);
                tv_rate = (TextView) itemView.findViewById(R.id.tv_rate);
                tv_distance = (TextView) itemView.findViewById(R.id.tv_distance);
                tv_recommended = (TextView) itemView.findViewById(R.id.tv_recommended);
                im_details = (ImageView) itemView.findViewById(R.id.im_details);
                im_details.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder malert = new AlertDialog.Builder(context);


                        View view1 = getActivity().getLayoutInflater().inflate(R.layout.ratecard_dialog, null);

                        bt_cancel = (Button) view1.findViewById(R.id.bt_cancel);
                        bt_call = (Button) view1.findViewById(R.id.bt_call);

                        bt_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialog.dismiss();
                            }
                        });

                        bt_call.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:8377845260"));
                                startActivity(intent);
                            }
                        });


                        malert.setView(view1);

                        dialog = malert.create();
                        dialog.show();
                    }
                });
            }

            public void setInvisible() {

                tv_recommended.setVisibility(View.GONE);
            }

        }

    }

    public class quantityAdapter extends RecyclerView.Adapter<quantityAdapter.quantityHolder> {
        LayoutInflater inflater;
        Context context;
        ArrayList<String> quantityList = new ArrayList<>();
        private int lastCheckedPosition = -1;

        quantityAdapter(Context context, ArrayList<String> list) {
            this.context = context;
            this.quantityList = list;
            inflater = LayoutInflater.from(context);

        }


        @Override
        public quantityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.horizontal_recycleview_singleitem, parent, false);


            return new quantityHolder(view);
        }

        @Override
        public void onBindViewHolder(quantityHolder holder, int position) {
            holder.tb_quantitybutton.setText(quantityList.get(position));
            holder.tb_quantitybutton.setTextOn(quantityList.get(position));
            holder.tb_quantitybutton.setTextOff(quantityList.get(position));
            holder.tb_quantitybutton.setChecked(position == lastCheckedPosition);

        }

        @Override
        public int getItemCount() {
            return 6;
        }

        public class quantityHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
            ToggleButton tb_quantitybutton;

            public quantityHolder(View itemView) {
                super(itemView);
                tb_quantitybutton = (ToggleButton) itemView.findViewById(R.id.tb_quantityvalue);

                tb_quantitybutton.setOnCheckedChangeListener(quantityHolder.this);
                tb_quantitybutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        lastCheckedPosition = getAdapterPosition();
                        notifyItemRangeChanged(0, quantityList.size());


                    }
                });
            }


            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {
                   /* tb_quantitybutton.setTextColor(Color.WHITE);
*//*
                    tb_quantitybutton.setBackgroundColor(getResources().getColor(R.color.bgColor));
*/



                    if (getAdapterPosition() == 0) {


                        getSheetResponse(MyApplication.sheet1, "15");

                    } else if (getAdapterPosition() == 1) {
                        getSheetResponse(MyApplication.sheet2, "30");
                    } else if (getAdapterPosition() == 2) {
                        getSheetResponse(MyApplication.sheet3, "45");
                    } else if (getAdapterPosition() == 3) {
                        getSheetResponse(MyApplication.sheet4, "60");
                    } else if (getAdapterPosition() == 4) {
                        getSheetResponse(MyApplication.sheet5, "75");
                    } else {
                        CustomToast.showToast("No sheet available for this unit", context);
                    }
                }

            }

        }
    }
}
