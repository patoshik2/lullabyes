package com.eguo.lullabyes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.eguo.lullabyes.Content.AllContentDataBase;
import com.eguo.lullabyes.MediaPleyerFireBase.MediaPleyerFireBase;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class AudioActivity extends AppCompatActivity {
   @SuppressLint("StaticFieldLeak")
   static AudioActivity instance ;
    private TextView taileName;
    private ImageView imageView;
    private ImageView battonplaystop;
    private ImageView btn_like_audio;
    private   String strName, strDesk,urlAudio, strUrlImg;
    private String blogPostId;
    public FirebaseFirestore firebaseFirestore;
    private String currentUserID;
    private SeekBar seekBar;
    public  List<AllContentDataBase> allContentDataBases;
    int forfound;
    private AdView mAdView;
    MediaPleyerFireBase mediaPleyerFireBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        currentUserID = firebaseAuth.getCurrentUser().getUid();
        blogPostId =  getIntent().getStringExtra("blog_post_id");
        imageView = (ImageView) findViewById(R.id.imageView3);
        taileName = (TextView) findViewById(R.id.audio_name_inplayer1);
        btn_like_audio = (ImageView) findViewById(R.id.btn_like_audio);
        battonplaystop = (ImageView) findViewById(R.id.play_audio_button1);
        ImageView btn_nextmiuzik = (ImageView) findViewById(R.id.img_btn_forward);
        ImageView img_btn_rewind = (ImageView) findViewById(R.id.img_btn_rewind);
        mAdView = findViewById(R.id.adsTaileActivit);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        btn_nextmiuzik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPleyerFireBase.instance().nextmiuzik();
                blogPostId  = MediaPleyerFireBase.instance().setIdaudioBook();

                firebaseFirestore.collection("Posts").document(blogPostId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            urlAudio = task.getResult().getString("audio_url");
                            firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").document(currentUserID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                                    if(documentSnapshot.exists()){
                                        // Toast.makeText(getApplicationContext(), "change icon", Toast.LENGTH_LONG).show();

                                        btn_like_audio.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_launcherred));

                                    }else{
                                        btn_like_audio.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_launchergrey));
                                    }
                                }
                            });
                            strName = task.getResult().getString("name");
                            taileName.setText(strName);
                            strUrlImg = task.getResult().getString("image_url");;
                            Glide.with(AudioActivity.this).load(strUrlImg).into(imageView);

                          /*  MediaPleyerFireBase.instance().stopMediaPlayerContent();
                            if (MediaPleyerFireBase.instance().getMediaplayer() == null) {
                                MediaPleyerFireBase.instance().createMediaPlayer(urlAudio);
                                battonplaystop.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_pause_grean_24dp));
                                getSeekBarStatus();
                            }*/

                        }else {

                        }
                    }
                });


            }
        });
img_btn_rewind.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        MediaPleyerFireBase.instance().rewindmuzic();

        blogPostId  = MediaPleyerFireBase.instance().setIdaudioBook();
        firebaseFirestore.collection("Posts").document(blogPostId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    urlAudio = task.getResult().getString("audio_url");
                    firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").document(currentUserID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                            if(documentSnapshot.exists()){
                                // Toast.makeText(getApplicationContext(), "change icon", Toast.LENGTH_LONG).show();

                                btn_like_audio.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_launcherred));

                            }else{
                                btn_like_audio.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_launchergrey));
                            }
                        }
                    });
                    strName = task.getResult().getString("name");
                    taileName.setText(strName);
                    strUrlImg = task.getResult().getString("image_url");;
                    Glide.with(AudioActivity.this).load(strUrlImg).into(imageView);

                    /*MediaPleyerFireBase.instance().stopMediaPlayerContent();
                    if (MediaPleyerFireBase.instance().getMediaplayer() == null) {
                        MediaPleyerFireBase.instance().createMediaPlayer(urlAudio);
                        battonplaystop.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_pause_grean_24dp));
                        getSeekBarStatus();
                    }*/

                }else {

                }
            }
        });




    }
});


seekBar = (SeekBar) findViewById(R.id.seekBarFireBase1);
        firebaseFirestore.collection("Posts").document(blogPostId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
urlAudio = task.getResult().getString("audio_url");
                    firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").document(currentUserID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                            if(documentSnapshot.exists()){
                                // Toast.makeText(getApplicationContext(), "change icon", Toast.LENGTH_LONG).show();

                                btn_like_audio.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_launcherred));

                            }else{
                                btn_like_audio.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_launchergrey));
                            }
                        }
                    });
                    strName = task.getResult().getString("name");
                    taileName.setText(strName);
                    strUrlImg = task.getResult().getString("image_url");;
                    Glide.with(AudioActivity.this).load(strUrlImg).into(imageView);

                    MediaPleyerFireBase.instance().stopMediaPlayerContent();
                    if (MediaPleyerFireBase.instance().getMediaplayer() == null) {
                        MediaPleyerFireBase.instance().createMediaPlayer(urlAudio);
                        battonplaystop.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_pause_grean_24dp));
                        getSeekBarStatus();
                    }

                }else {

                }
            }
        });

        btn_like_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").document(currentUserID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (!task.getResult().exists()){
                            Map<String, Object> likeMap = new HashMap<>();
                            likeMap.put("timestamp", FieldValue.serverTimestamp());

                            firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").document(currentUserID).set(likeMap);
                            firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                    assert queryDocumentSnapshots != null;
                                    if (!queryDocumentSnapshots.isEmpty()) {
                                        int count = queryDocumentSnapshots.size();
                                        //   firebaseFirestore.collection("Posts").document(blogPostId).update("like", String.valueOf(count));
                                        firebaseFirestore.collection("Posts").document(blogPostId).update("like", count);


                                    }
                                }
                            });
                        }else {
                            firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").document(currentUserID).delete();
                            firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                    assert queryDocumentSnapshots != null;
                                    if (!queryDocumentSnapshots.isEmpty()) {
                                        int count = queryDocumentSnapshots.size();
                                        //   firebaseFirestore.collection("Posts").document(blogPostId).update("like", String.valueOf(count));
                                        firebaseFirestore.collection("Posts").document(blogPostId).update("like", count);


                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
        battonplaystop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MediaPleyerFireBase.instance().getMediaplayer() == null) {
                    MediaPleyerFireBase.instance().createMediaPlayer(urlAudio);
                    battonplaystop.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_pause_grean_24dp));

                }
                if (MediaPleyerFireBase.instance().getMediaplayer().isPlaying()) {
                    MediaPleyerFireBase.instance().pauseMediaPlayerContent();
                    battonplaystop.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_play_arrow_black_24dp));
                } else {
                    MediaPleyerFireBase.instance().continueMediaPlayerContent();
                    battonplaystop.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_pause_grean_24dp));

                }
            }
        });


    }
    public void getSeekBarStatus(){

        new Thread(new Runnable() {

            @Override
            public void run() {
                // mp is your MediaPlayer
                // progress is your ProgressBar

                int currentPosition = MediaPleyerFireBase.instance().getCurentPositionMP();
                int total = MediaPleyerFireBase.instance().getDurationMP();
                seekBar.setMax(total);
                while (MediaPleyerFireBase.instance().getMediaplayer() != null && currentPosition < total) {
                    try {
                        Thread.sleep(1000);
                        currentPosition =MediaPleyerFireBase.instance().getCurentPositionMP();
                    } catch (InterruptedException e) {
                        return;
                    }
                    seekBar.setProgress(currentPosition);

                }
            }
        }).start();





        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(final SeekBar seekBar, int ProgressValue, boolean fromUser) {
                if (fromUser) {
                    MediaPleyerFireBase.instance().setSeekTo(ProgressValue);//if user drags the seekbar, it gets the position and updates in textView.
                    seekBar.setProgress(ProgressValue);
                }
                final long mMinutes=(ProgressValue/1000)/60;//converting into minutes
                final int mSeconds=((ProgressValue/1000)%60);//converting into seconds
                // SongProgress.setText(mMinutes+":"+mSeconds);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void setAudioThings(List<AllContentDataBase> allContentDataBases, int forfound){
this.allContentDataBases  = allContentDataBases;
this.forfound = forfound;

    }


    @Override
    protected void onPause() {
        super.onPause();
        MediaPleyerFireBase.instance().pauseMediaPlayerContent();
    }

    public static AudioActivity instance() {
        if(instance==null)

            instance = new AudioActivity();
        return instance;
    }
}
