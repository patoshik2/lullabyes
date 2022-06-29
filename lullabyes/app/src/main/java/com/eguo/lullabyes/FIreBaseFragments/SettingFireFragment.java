package com.eguo.lullabyes.FIreBaseFragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.android.billingclient.api.BillingClient;
import com.eguo.lullabyes.AboutActivity;
import com.eguo.lullabyes.AccountActivity;
import com.eguo.lullabyes.BillingPayActivity;
import com.eguo.lullabyes.LoginActivity;
import com.eguo.lullabyes.MainActivity;
import com.eguo.lullabyes.Moderator;
import com.eguo.lullabyes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFireFragment extends Fragment {
    private Switch aSwitch;
    Context context;
    private Button went_to_newpost;
    private TextView taileText, testing;
    private NestedScrollView nestedScrollView;
    private static final String APP_PREFERENCES = "setting_mode";

    private static final String SWITCH = "switch";
    private Boolean switchOnOff ;
    private FirebaseAuth mAuth;
    private BillingClient billingClient;
    public SettingFireFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_setting_fire, container, false);
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_taile);
        went_to_newpost = (Button) view.findViewById(R.id.went_activ_newpostsend);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.background_taile) ;
        taileText = (TextView) view.findViewById(R.id.taile_text);
        aSwitch = (Switch) view.findViewById(R.id.switch_night);
        Button billding = (Button) view.findViewById(R.id.billding);
        Button button_about = (Button) view.findViewById(R.id.about_app);
        Button accountkabinet = (Button) view.findViewById(R.id.accountkabinet);
        accountkabinet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AccountActivity.class));
            }
        });
        button_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AboutActivity.class));
            }
        });

billding.setVisibility(View.INVISIBLE);
        billding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), BillingPayActivity.class);
                startActivity(intent);


            }
        });


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        Button opent_login_act = (Button) view.findViewById(R.id.opent_login_act);

        went_to_newpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Moderator.class);
                startActivity(intent);
            }
        });
        Button exit_to_account = (Button) view.findViewById(R.id.exit_to_account);
        exit_to_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();

               MainActivity.instance().finish();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        opent_login_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });
        loadMode();
//        updateView();


       /* aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    saveMode();


                }else {
                    saveMode();

                }
            }
        });*/

        final String user_id = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    String user_mail = task.getResult().getString("mail");

                    String user_priority = task.getResult().getString("priority");
                  //  Toast.makeText(getContext(), "Error " + user_priority, Toast.LENGTH_LONG).show();
                    if (Integer.parseInt(user_priority) == 4 ) {
                        went_to_newpost.setVisibility(view.VISIBLE);
                    }
                }
            }});




                    return view;
    }


    public void  saveMode(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SWITCH, aSwitch.isChecked());
        editor.apply();
    }
    public void  loadMode(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        switchOnOff = sharedPreferences.getBoolean(SWITCH, false);
    }
    public  void  updateView(){
        aSwitch.setChecked(switchOnOff);
    }


}
