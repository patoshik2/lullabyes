package com.eguo.lullabyes.MediaPlayerContent;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.SeekBar;

/**
 * Created by Владимир on 31.05.2018.
 */

public class MediaPlayerContent  {

    private static MediaPlayerContent instance;


Context context;
int resid;
SeekBar seekBar;
MediaPlayerSeekbar mediaPlayerSeekbar;
    public static MediaPlayerContent instance() {
        if(instance==null)
            instance = new MediaPlayerContent();
        return instance;
    }

    private MediaPlayer mediaPlayer;

    public void createMediaPlayer(Context ctx, int res) {

            mediaPlayer = MediaPlayer.create(ctx, res);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.start();

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
    boolean b = true;
    if(mediaPlayer == null) {
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
}
