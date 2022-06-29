package com.eguo.lullabyes;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.eguo.lullabyes.Adapters.BookFireAdapter;
import com.eguo.lullabyes.FIreBaseFragments.AudioFireFragment;
import com.eguo.lullabyes.FIreBaseFragments.BookFireFragment;
import com.eguo.lullabyes.FIreBaseFragments.HomeFragment;
import com.eguo.lullabyes.FIreBaseFragments.SearchFireFragment;
import com.eguo.lullabyes.FIreBaseFragments.SettingFireFragment;
import com.eguo.lullabyes.MediaPleyerFireBase.MediaPleyerFireBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity  {
    public ViewPager viewPager;
 Toolbar toolbar;
 @SuppressLint("StaticFieldLeak")
 static MainActivity instance;
 public BookFireFragment bookFireFragment;
 public AudioFireFragment audioFireFragment;
 public SearchFireFragment searchFireFragment;
 public SettingFireFragment settingFireFragment;
 public HomeFragment homeFragment;
 public FirebaseFirestore firebaseStorage;
  ImageView audioBtn;
    SeekBar seekBar;
    Context context;
   public BookFireAdapter bookFireAdapter;

Switch aSwitch;
public BottomNavigationView bottomNavigationView;
public   FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
mAuth = FirebaseAuth.getInstance();
        setSupportActionBar(toolbar);
        //bookFireAdapter = new BookFireAdapter(,null);
        bottomNavigationView  = findViewById(R.id.main_bottom_navigation);
        firebaseStorage = FirebaseFirestore.getInstance();

seekBar = (SeekBar) findViewById(R.id.seekBarFireBase);


if (!isOnline(getApplicationContext())){

    Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
    MainActivity.this.finish();

}

       /* DataFragmentOne dataFragmentOne = new DataFragmentOne();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.relativ, dataFragmentOne).commit();*/
       if(mAuth.getCurrentUser()!=null) {
String user_id = mAuth.getCurrentUser().getProviderId();
           firebaseStorage.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
               @Override
               public void onComplete(@NonNull Task<DocumentSnapshot> task) {
if(task.isSuccessful()){
    String user_mail = task.getResult().getString("mail");
    String user_priority  = task.getResult().getString("priority");
}
               }
           });
           homeFragment  = new HomeFragment();
           audioFireFragment = new AudioFireFragment();
           bookFireFragment = new BookFireFragment();
           searchFireFragment = new SearchFireFragment();
           settingFireFragment = new SettingFireFragment();

           replaceFragment(homeFragment);
           bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
               @Override
               public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                   switch (menuItem.getItemId()) {
                       case R.id.home_havigatbottom:
                           replaceFragment(homeFragment);
                           return true;
                       case R.id.book_navigaitbottom:
                           replaceFragment(bookFireFragment);
                           return true;
                       case R.id.audio_navigatbottom:

                           replaceFragment(audioFireFragment);
                           return true;
                       case R.id.searc_navigatbottom:
                           replaceFragment(searchFireFragment);
                           return true;
                       case R.id.setting_navigatbottom:
                           replaceFragment(settingFireFragment);
                           return true;

                       default:
                           return false;
                   }


               }
           });
       }
      if (isFirstTime()) {
          // What you do when the Application is Opened First time Goes here
Intent intent = new Intent(MainActivity.this, LoginActivity.class);
          startActivity(intent);
//          AuntificationFragment auntificationFragment = new AuntificationFragment();
//          FragmentManager manageraunt = getSupportFragmentManager();
//          FragmentTransaction transaction = manageraunt.beginTransaction();
//          transaction.setCustomAnimations(R.anim.enter_to_right, R.anim.exit_to_right);
//          transaction.addToBackStack(null);
//          transaction.add(R.id.relativ,auntificationFragment,auntificationFragment.getTag()).commit();

      }


          //viewPager = (ViewPager)findViewById(R.id.container_for_data);

          //  setupViewPager(viewPager);


    }
   public void buttonPlayChange(Activity activity){


                    if (MediaPleyerFireBase.instance().getMediaplayer().isPlaying()) {

                        audioBtn.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_play_arrow_black_24dp));
                    } else {

                        audioBtn.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_pause_grean_24dp));
                    }}














    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_for_part_player,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        item.getItemId();// case blocks for other MenuItems (if any)
        return false;
    }
    private boolean isFirstTime()
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBeforee", false);
        if (!ranBefore) {
            // first time


            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBeforee", true);
            editor.apply();
        }
        return !ranBefore;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private  void replaceFragment(Fragment fragment){
        @SuppressLint("CommitTransaction") FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }
    public static boolean isOnline(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }
        return false;
    }
public void finish(){
        finish();
}
    public static MainActivity instance() {
        if(instance==null)

            instance = new MainActivity();
        return instance;
    }
}
