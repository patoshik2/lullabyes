package com.eguo.lullabyes.MediaPleyerFireBase;

import android.media.MediaPlayer;

import com.eguo.lullabyes.AudioActivity;
import com.eguo.lullabyes.Content.AllContentDataBase;

import java.io.IOException;
import java.util.List;

public class MediaPleyerFireBase {
    private static MediaPleyerFireBase instance;
   private List<AllContentDataBase> allContentDataBases;
   AudioActivity audioActivity;
   String  bookid;
    int forfound;
   public void setMediapleyer( List<AllContentDataBase>allContentDataBases, int forfound){
       this.allContentDataBases = allContentDataBases;
       this.forfound = forfound;
   }


    public static MediaPleyerFireBase instance() {
        if(instance==null)

            instance = new MediaPleyerFireBase();
        return instance;
    }
    private MediaPlayer mediaPlayer;

    public void createMediaPlayer(String s) {
if (mediaPlayer == null) {
    mediaPlayer  = new MediaPlayer();
    try {
        mediaPlayer.setDataSource(s);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        mediaPlayer.prepare();
    } catch (IOException e) {
        e.printStackTrace();
    }
}else {
    try {
        stopMediaPlayerContent();
        mediaPlayer  = new MediaPlayer();
        mediaPlayer.setDataSource(s);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        mediaPlayer.prepare();
    } catch (IOException e) {
        e.printStackTrace();
    }
}


    }
    public void stopMediaPlayerContent() {
        if(mediaPlayer!=null)

            mediaPlayer.stop();
        mediaPlayer = null;
    }


    public  void pauseMediaPlayerContent(){
        if(mediaPlayer!=null)
            mediaPlayer.pause();

    }
    public  void continueMediaPlayerContent(){
        if(mediaPlayer!=null)
            mediaPlayer.start();
    }
    public  MediaPlayer  getMediaplayer(){
        return mediaPlayer;
    }
    public int getCurentPositionMP(){

        return    mediaPlayer.getCurrentPosition();

    }

    public  int getDurationMP(){
        return   mediaPlayer.getDuration();

    }
    public void setSeekTo (int seekTo){
        mediaPlayer.seekTo(seekTo);
    }

    public boolean getStatusIsMedia(){
        boolean b ;
        if(mediaPlayer == null) {
            b = false;
        }else if (mediaPlayer.isPlaying()){
            b = true;
        }else {
            b = false;
        }
        return b;
    }

    public boolean getStatusPlaying(){
        boolean a = false;
        if(mediaPlayer.isPlaying()){
            a = true;
        }
        return a;
    }
    public void nextmiuzik(){

        stopMediaPlayerContent();
        final String urlAudio = allContentDataBases.get(forfound+ 1).getAudio_url();
        forfound ++;

        bookid =allContentDataBases.get(forfound).TailePostId;


        if (mediaPlayer == null) {
            mediaPlayer  = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(urlAudio);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                stopMediaPlayerContent();
                mediaPlayer  = new MediaPlayer();
                mediaPlayer.setDataSource(urlAudio);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    }

public  void rewindmuzic(){
    stopMediaPlayerContent();
    final String urlAudio = allContentDataBases.get(forfound - 1).getAudio_url();
    forfound --;
    bookid =allContentDataBases.get(forfound).TailePostId;
    if (mediaPlayer == null) {
        mediaPlayer  = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(urlAudio);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }else {
        try {
            stopMediaPlayerContent();
            mediaPlayer  = new MediaPlayer();
            mediaPlayer.setDataSource(urlAudio);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }
    public String setIdaudioBook(){


        return bookid;
    }

}
