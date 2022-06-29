package com.eguo.lullabyes.Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eguo.lullabyes.Content.AllContent;
import com.eguo.lullabyes.Filter.BookFilter;
import com.eguo.lullabyes.R;

import java.util.ArrayList;

/**
 * Created by Владимир on 27.03.2018.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyHolderForTales>  {
    Context c;
   public ArrayList<AllContent> allContents;
    LayoutInflater inflater;

    ArrayList<AllContent> filterList;
    BookFilter filter;
private  ResyclerItemClickListener listener;

    public BookAdapter(Context c, ArrayList<AllContent> falryTales, ResyclerItemClickListener listener) {
        this.c = c;
        this.allContents = falryTales;
        this.filterList = falryTales;
        this.listener = listener;
    }


    public class MyHolderForTales extends RecyclerView.ViewHolder {
        ImageView img;
        TextView nameTailes, deskriptiontext;

        public MyHolderForTales(View itemView) {
super(itemView);
            img=(ImageView) itemView.findViewById(R.id.tailesImage);
            nameTailes =(TextView) itemView.findViewById(R.id.nameTailes);
        }
        public  void  bind (final AllContent allContent, final ResyclerItemClickListener listener){

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickListener(allContent,getLayoutPosition());
                }
            });
        }

    }


    @Override
    public MyHolderForTales onCreateViewHolder(ViewGroup parent, int viewType) {

        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_for_tales,null);
        MyHolderForTales holder = new MyHolderForTales(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyHolderForTales holder, final int position) {
        AllContent allContent = allContents.get(position);
        holder.nameTailes.setText(allContents.get(position).getName());
       holder.img.setImageResource(allContents.get(position).getImage());
holder.bind(allContent, listener);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return allContents.size();
    }

//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        if (inflater == null) {
//            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        }
//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.model_for_tales, null);
//        }
//        MyHolder holder = new MyHolder(convertView);
//        holder.nameTailes.setText(bookContents.get(position).getName());
//        holder.img.setImageResource(bookContents.get(position).getImage());
//        holder.setItemClickListenert(new ItemClickListener() {
//            @Override
//            public void onItemClick(View v) {
//                // Toast.makeText(c,falryTales.get(position).getName(), Toast.LENGTH_LONG).show();
//
//            }
//        });
//        return convertView;
//    }





    public interface ResyclerItemClickListener{
        void  onClickListener(AllContent allContent, int position);

    }

}