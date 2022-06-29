package com.eguo.lullabyes.Filter;

import android.widget.Filter;

import com.eguo.lullabyes.Adapters.AllContentAdapter;
import com.eguo.lullabyes.Content.AllContent;

import java.util.ArrayList;

/**
 * Created by Владимир on 05.06.2018.
 */

public class FilterAllContent extends Filter {
    ArrayList<AllContent> filterList;
    AllContentAdapter adapter;

    public FilterAllContent(ArrayList<AllContent> filterList, AllContentAdapter adapter) {
        this.filterList = filterList;
        this.adapter =adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (constraint != null && constraint.length()>0){
            constraint = constraint.toString().toUpperCase();
            ArrayList<AllContent> filteredFairyTales = new ArrayList<>();
            for(int i = 0; i<filterList.size(); i++){
                if(filterList.get(i).getName().toUpperCase().contains(constraint)){
                    filteredFairyTales.add(filterList.get(i));
                }
            }

            results.count = filteredFairyTales.size();
            results.values = filteredFairyTales;

        }else{
            results.count = filterList.size();
            results.values = filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.list = (ArrayList<AllContent>) results.values;
        adapter.notifyDataSetChanged();
    }
}
