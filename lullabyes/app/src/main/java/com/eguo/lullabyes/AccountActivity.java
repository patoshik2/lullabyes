package com.eguo.lullabyes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class AccountActivity extends AppCompatActivity {
String currentUserEmail, curentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        currentUserEmail = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail();
        TextView textMyEmail = findViewById(R.id.activ_account_my_login);
        textMyEmail.setText(currentUserEmail);
       TextView typeAccount = (TextView) findViewById(R.id.type_accout);
       Button login_reg = findViewById(R.id.login_reg2);
        login_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();
                startActivity(new Intent(AccountActivity.this, RegistrationActivity.class));
                MainActivity.instance().finish();
            }
        });
         curentUserId = firebaseAuth.getCurrentUser().getUid();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Users").document(curentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    String user_mail = Objects.requireNonNull(task.getResult()).getString("mail");
                    final String user_priority = task.getResult().getString("priority");

                    assert user_priority != null;
                    if(Integer.parseInt(user_priority) == 1){
                        typeAccount.setText("У вас гостевой аккаунт: \n  для того чтобы увеличить привилегии \n зарегистрируйте аккаунт.");

                     login_reg.setVisibility(View.VISIBLE);
                    }else if (Integer.parseInt(user_priority)==2 ){
                        typeAccount.setText("Вы зарегистрировали Аккаунт");
                        login_reg.setVisibility(View.INVISIBLE);
                    }




                }}});


    }
}
