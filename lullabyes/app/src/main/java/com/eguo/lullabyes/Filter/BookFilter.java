package com.eguo.lullabyes.Filter;

import android.widget.Filter;

import com.eguo.lullabyes.Adapters.BookAdapter;
import com.eguo.lullabyes.Content.AllContent;

import java.util.ArrayList;

/**
 * Created by Владимир on 27.03.2018.
 */

public class BookFilter extends Filter {
    ArrayList<AllContent> filterList;
    BookAdapter adapter;
    public  BookFilter(ArrayList<AllContent> filterList, BookAdapter adapter){
        this.filterList = filterList;
        this.adapter = adapter;
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
        adapter.allContents = (ArrayList<AllContent>) results.values;
        adapter.notifyDataSetChanged();
    }
}
