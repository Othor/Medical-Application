package com.example.jamie.autosearch_test1.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jamie.autosearch_test1.R;
import com.example.jamie.autosearch_test1.model.DataModel;

import java.util.List;

/**
 * Created by Jamie on 10/8/2017.
 */
public class Adapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataModel> item;

    public Adapter(Activity activity, List<DataModel> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int location) {
        return item.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_item, null);

        TextView txt_name = (TextView) convertView.findViewById(R.id.txt_name);
        txt_name.setText(item.get(position).getName());

        TextView txt_age = (TextView) convertView.findViewById(R.id.txt_age);
        txt_age.setText(item.get(position).getAge());

        TextView txt_email = (TextView) convertView.findViewById(R.id.txt_email);
        txt_email.setText(item.get(position).getEmail());

        return convertView;
    }
}
