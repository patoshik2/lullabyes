package com.eguo.lullabyes.Adapters;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eguo.lullabyes.Content.AllContent;
import com.eguo.lullabyes.Filter.FilterForAudio;
import com.eguo.lullabyes.R;

import java.util.ArrayList;

/**
 * Created by Владимир on 28.03.2018.
 */

public class AudioAdapter  extends RecyclerView.Adapter<AudioAdapter.HolderForAudio> {
    private Context context;
private ResyclerItemClickListener listener;

    private int selectedPosition;
    public ArrayList<AllContent> audioContents;
    //1!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public static class HolderForAudio extends RecyclerView.ViewHolder{
      private TextView txtName, txtSinger;
      private ImageView ivPlay, ivMuzic;
         public HolderForAudio(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtSinger = (TextView) itemView.findViewById(R.id.txtSinger);
            ivPlay = (ImageView) itemView.findViewById(R.id.ivPlay);
            ivMuzic = (ImageView) itemView.findViewById(R.id.ivMusic);
        }
        public  void  bind (final AllContent audioContent, final ResyclerItemClickListener listener){
             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     listener.onClickListener(audioContent,getLayoutPosition());
                 }
             });
        }
    }


    @Override
    public HolderForAudio onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_for_audio, parent, false);
        return new HolderForAudio(view);
    }

    @Override
    public void onBindViewHolder(final HolderForAudio holder, int position) {
AllContent  audioContent = audioContents.get(position);

if(audioContent!= null){
    if (selectedPosition == position){


       // holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryLite));
      holder.ivPlay.setVisibility(View.VISIBLE);
    }else {
        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        holder.ivPlay.setVisibility(View.INVISIBLE);
    }
    holder.txtSinger.setText(audioContent.getSinger());
    holder.txtName.setText(audioContent.getName());
    holder.ivMuzic.setImageResource(audioContent.getImage());


    holder.bind(audioContent, listener);


}
    }

    ArrayList<AllContent> filterList;


    private FilterForAudio filter;

    public AudioAdapter(Context c,  ArrayList<AllContent> arrayList, ResyclerItemClickListener listener){
        this.context = c;
this.listener = listener;
        this.audioContents = arrayList;
         this.filterList = arrayList;
    }






    @Override
    public int getItemCount() {
        return audioContents.size();
    }





public interface ResyclerItemClickListener{
        void  onClickListener(AllContent audioContent, int position);

}

public void setSelectedPosition(int selectedPosition){
        this.selectedPosition = selectedPosition;
}

    public int getSelectedPosition() {
        return selectedPosition;
    }

/*
 @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final HolderForAudio holderForAudio ;

        if (convertView == null) {
            holderForAudio = new HolderForAudio();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout, null);


            convertView.setTag(holderForAudio);

        } else {
            holderForAudio = (HolderForAudio) convertView.getTag();
            this.convertView = convertView;

        }
        final AudioContent audioTales = arrayList.get(position);
        holderForAudio.txtSinger.setText(audioTales.getSinger());
        holderForAudio.txtName.setText(audioTales.getName());
        holderForAudio.mediaPlayer = MediaPlayer.create(context, audioTales.getSong());
        holderForAudio.ivMuzic.setImageResource(audioTales.getImage());

        holderForAudio.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        holderForAudio.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                holderForAudio.seekBar.setMax( holderForAudio.mediaPlayer.getDuration());
                holderForAudio.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser)
                            holderForAudio.mediaPlayer.seekTo(progress);
                        holderForAudio.seekBar.setProgress(progress);

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while ( holderForAudio.mediaPlayer != null) {
                            try {
                                Message msg = new Message();
                                msg.what =  holderForAudio.

                                Thread.sleep(1000);
                                holderForAudio.seekBar.setProgress( holderForAudio.mediaPlayer.getCurrentPosition());
                            } catch (InterruptedException e) {

                            }
                        }
                    }


                }).start();


            }


        });

        holderForAudio.ivSrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holderForAudio.mediaPlayer.seekTo(0);
                holderForAudio.mediaPlayer.pause();
                if(!flag){
                    holderForAudio.ivPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                }
            }
        });

        holderForAudio.ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holderForAudio.mediaPlayer.isPlaying()) {
                    holderForAudio.mediaPlayer.start();

                    holderForAudio.ivPlay.setImageResource(R.drawable.ic_pause_black_24dp);
                    flag = false;








                } else {
                    holderForAudio.ivPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    flag = true;

                    holderForAudio.mediaPlayer.pause();

                }


            }
        });

        return convertView;

    }
*/


}
