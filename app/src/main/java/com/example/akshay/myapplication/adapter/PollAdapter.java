package com.example.akshay.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akshay.myapplication.R;
import com.example.akshay.myapplication.dao.PollEntity;

import java.util.ArrayList;

/**
 * Created by Akshay on 3/22/2017.
 */

public class PollAdapter extends ArrayAdapter<PollEntity> {

    private LayoutInflater mInflater;
    private ArrayList<PollEntity> pollData;
    private int mViewResourceId;
    private Context context;

    public PollAdapter(Context context, int textViewResourceId, ArrayList<PollEntity> bagData) {
        super(context, textViewResourceId, bagData);
        this.context = context;
        this.pollData = bagData;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);

        PollEntity pollObject = pollData.get(position);

        if (pollObject != null) {
            TextView pollName = (TextView) convertView.findViewById(R.id.textLabelPollName);
            TextView pollStartDate = (TextView) convertView.findViewById(R.id.textViewStartDate);
            TextView pollEndDate = (TextView) convertView.findViewById(R.id.textViewEndDate);

            if (pollName != null) {
                pollName.setText(pollObject.getPollName());
            }
            if (pollStartDate != null) {
                pollStartDate.setText(pollObject.getPollStartDate());
            }
            if (pollEndDate != null) {
                pollEndDate.setText(pollObject.getPollEndDate());
            }
        }

        return convertView;
    }
}
