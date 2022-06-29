package com.eguo.lullabyes.Adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.eguo.lullabyes.Content.AllContent;
import com.eguo.lullabyes.Filter.FilterAllContent;
import com.eguo.lullabyes.R;

import java.util.ArrayList;

import static com.eguo.lullabyes.Content.AllContent.ONE_TYPE;
import static com.eguo.lullabyes.Content.AllContent.TWO_TYPE;

/**
 * Created by Владимир on 11.05.2018.
 */

public class AllContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private ResyclerItemClickListener listener;
 public  ArrayList<AllContent> list;
private FilterAllContent filter;
public  AllContentAdapter(ArrayList<AllContent> list, ResyclerItemClickListener listener) {
    this.filterList =list;
    this.list = list;
    this.listener = listener;
}

    @Override
    public int getItemViewType(int position) {
AllContent allContent = list.get(position);
if(allContent != null){
   return allContent.getType();

}
return 0;
}
ArrayList<AllContent> filterList;
    @Override
    public Filter getFilter() {
        if(filter==null){
            filter=new FilterAllContent(filterList,this);

        }


        return filter;
    }

    class  ViewHolderTale extends  RecyclerView.ViewHolder{
        ImageView img;
        TextView nameTailes, deskriptiontext;

        public ViewHolderTale(View itemView) {
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.tailesImage);
            nameTailes =(TextView) itemView.findViewById(R.id.nameTailes);
        }
        public  void  bind(final AllContent allContent, final ResyclerItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickListener(allContent,getLayoutPosition());
                }
            });
        }
    }
   public static   class  ViewHolderAudio extends  RecyclerView.ViewHolder{
         private TextView txtName, txtSinger;
         private ImageView ivPlay, ivSrop, ivMuzic;

        public ViewHolderAudio(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtSinger = (TextView) itemView.findViewById(R.id.txtSinger);
            ivPlay = (ImageView) itemView.findViewById(R.id.ivPlay);
            //  ivSrop = (ImageView) itemView.findViewById(R.id.ivStop);
            ivMuzic = (ImageView) itemView.findViewById(R.id.ivMusic);
            //  seekBar = (SeekBar) itemView.findViewById(R.id.seekbar);
        }
         public  void  bind(final AllContent allContent, final ResyclerItemClickListener listener){
             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     listener.onClickListener(allContent,getLayoutPosition());
                 }
             });
         }
    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view;
    switch (viewType) {
        case ONE_TYPE:
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_for_tales, parent, false);
            return  new ViewHolderTale(view);
 case TWO_TYPE:
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_for_audio, parent, false);
            return  new ViewHolderAudio(view);

    }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
AllContent allContent = list.get(position);


    switch (allContent.getType()){
        case ONE_TYPE:
            ((ViewHolderTale) holder).nameTailes.setText(allContent.getName());
            ((ViewHolderTale) holder).img.setImageResource(allContent.getImage());
            ((ViewHolderTale) holder).bind(allContent, listener);
            break;
        case  TWO_TYPE:
            ((ViewHolderAudio) holder).txtName.setText(allContent.getName());
            ((ViewHolderAudio) holder).txtSinger.setText(allContent.getSinger());
             ((ViewHolderAudio) holder).ivMuzic.setImageResource(allContent.getImage());
            ((ViewHolderAudio) holder).bind(allContent, listener);
break;

    }


    }


    public interface ResyclerItemClickListener{
        void  onClickListener(AllContent allContent, int position);

    }
    @Override
    public int getItemCount() {
        return list.size();
    }


}
