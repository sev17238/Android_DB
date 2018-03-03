package com.example.sdiego.android_db;

/**
 * Created by SDiego on 01/03/2018.
 */

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.TextView;
import java.util.List;

/**
 * Created by SDiego on 21/02/2018.
 */

public class ItemListAdapter extends BaseAdapter {

    private Context context;
    private List<Pais> items;

    public ItemListAdapter(Context acontext,List<Pais> aitemlist){
        this.context = acontext;
        this.items = aitemlist;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context,R.layout.item_activity,null);


        TextView country = (TextView)v.findViewById(R.id.Country);
        TextView continent = (TextView)v.findViewById(R.id.Continent);

        country.setText(items.get(position).getNombrepais());
        continent.setText(items.get(position).getContinente());

        v.setTag(items.get(position).getNombrepais());
        return v;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
