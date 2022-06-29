package com.eguo.lullabyes;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;

public class TaileActivity extends AppCompatActivity {

  private   TextView taileName,taileText;
    private ImageView taileImg;
  private   String strName, strDesk, strUrlImg;
    private String blogPostId;
    int  intImg;
    private FirebaseFirestore firebaseFirestore;
    Toolbar toolbar;
    public static final String APP_PREFERENCES = "setting_mode";
    public static final String SWITCH = "switch";
    public  Boolean switchOnOff ;
    NestedScrollView nestedScrollView;
    ViewPager viewPager;
    private AdView mAdView;
    String user_priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taile);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
      String currentUserId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        FloatingActionButton fab =(FloatingActionButton)findViewById(R.id.btn_like_taile);
        firebaseFirestore = FirebaseFirestore.getInstance();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (!task.getResult().exists()){
                            Map<String, Object> likeMap = new HashMap<>();
                            likeMap.put("timestamp", FieldValue.serverTimestamp());

                            firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").document(currentUserId).set(likeMap);



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
                            firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").document(currentUserId).delete();
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

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        String currentUserID = firebaseAuth.getCurrentUser().getUid();






        blogPostId =  getIntent().getStringExtra("blog_post_id");
        taileImg = (ImageView) findViewById(R.id.taile_img);
        taileName = (TextView) findViewById(R.id.tailefire_name);

          nestedScrollView = (NestedScrollView) findViewById(R.id.background_taile) ;



        final String user_id = firebaseAuth.getCurrentUser().getUid();

        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    String user_mail = task.getResult().getString("mail");

                   user_priority = task.getResult().getString("priority");
                    //   Toast.makeText(getContext(), "Error " + user_priority, Toast.LENGTH_LONG).show();
                    assert user_priority != null;
                    if (Integer.parseInt(user_priority)==1){
    mAdView = findViewById(R.id.adsTaileActivit);
    AdRequest adRequest = new AdRequest.Builder().build();
    mAdView.loadAd(adRequest);

}


                    firebaseFirestore.collection("Posts").document(blogPostId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").document(currentUserId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                                        if(documentSnapshot.exists()){
                                            // Toast.makeText(getApplicationContext(), "change icon", Toast.LENGTH_LONG).show();

                                            fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_launcherred));

                                        }else{
                                            fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_launchergrey));
                                        }
                                    }
                                });
                                strDesk = task.getResult().getString("desc");
                                strName = task.getResult().getString("name");
                                taileName.setText(strDesk);
                                strUrlImg = task.getResult().getString("image_url");;
                                Glide.with(TaileActivity.this).load(strUrlImg).into(taileImg);
                                toolbar = (Toolbar) findViewById(R.id.toolbar_taile_activ);
                                setSupportActionBar(toolbar);
                                getSupportActionBar().setTitle(strName);
                            }else {

                            }
                        }
                    });










                }
            }});



























    }
    public  void  setImage(String download){
        ImageView imageView = findViewById(R.id.tailesImage);
        Glide.with(TaileActivity.this).load(download).into(imageView);
    }

}
