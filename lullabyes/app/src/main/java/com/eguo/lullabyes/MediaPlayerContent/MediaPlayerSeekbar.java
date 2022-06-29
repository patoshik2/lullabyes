package com.eguo.lullabyes.MediaPlayerContent;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.SeekBar;

import com.eguo.lullabyes.R;


/**
 * Created by Владимир on 02.06.2018.
 */

public class MediaPlayerSeekbar extends androidx.appcompat.widget.AppCompatSeekBar {
SeekBar seekBar;
MediaPlayer   mediaPlayer;
    public MediaPlayerSeekbar(Context context) {
        super(context);
        seekBar = (SeekBar) findViewById(R.id.seekBar);


    }

    public void setMediaPlayerContent(MediaPlayer mediaPlayer){
        this.mediaPlayer= mediaPlayer;
    }

public void getSeekBar(){






}
}
