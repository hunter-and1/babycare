package com.wicoding.injaz.babycare;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class FragmentGraph extends Fragment {

    Button buttonCO2,buttonHeath,buttonTemp,buttonSleep;
    public FragmentGraph() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_graph, container, false);
        buttonCO2 = (Button)v.findViewById(R.id.buttonCO2);
        buttonCO2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),DataActivity.class);
                i.putExtra("type","co2");
                startActivity(i);
            }
        });

        buttonHeath = (Button)v.findViewById(R.id.buttonHeath);
        buttonHeath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),DataActivity.class);
                i.putExtra("type","heath");
                startActivity(i);
            }
        });

        buttonTemp = (Button)v.findViewById(R.id.buttonTemp);
        buttonTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),DataActivity.class);
                i.putExtra("type","temp");
                startActivity(i);
            }
        });

        buttonSleep = (Button)v.findViewById(R.id.buttonSleep);
        buttonSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),DataActivity.class);
                i.putExtra("type","sleep");
                startActivity(i);
            }
        });

        return v;
    }

}
