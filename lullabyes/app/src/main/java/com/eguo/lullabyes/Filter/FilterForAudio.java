package com.eguo.lullabyes.Filter;

import android.widget.Filter;

import com.eguo.lullabyes.Adapters.AudioAdapter;
import com.eguo.lullabyes.Content.AllContent;

import java.util.ArrayList;

/**
 * Created by Владимир on 28.03.2018.
 */

public class FilterForAudio extends Filter {
    ArrayList<AllContent> filterList;
 AudioAdapter adapter;
    public  FilterForAudio(ArrayList<AllContent> filterList, AudioAdapter adapter){
        this.filterList = filterList;
        this.adapter = adapter;
    }
    @Override
    protected Filter.FilterResults performFiltering(CharSequence constraint) {
        Filter.FilterResults results = new Filter.FilterResults();
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
    protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
        adapter.audioContents = (ArrayList<AllContent>) results.values;
        adapter.notifyDataSetChanged();
    }


}
