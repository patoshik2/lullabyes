package com.eguo.lullabyes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
 private  EditText reg_mail,reg_password,reg_password_two;
    private  ProgressBar progressBar;
 public FirebaseAuth firebaseAuth;
 public FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
       firebaseAuth = FirebaseAuth.getInstance();
       firebaseFirestore = FirebaseFirestore.getInstance();
        reg_mail = (EditText) findViewById(R.id.registr_mail);
        reg_password = (EditText) findViewById(R.id.registr_password);
        reg_password_two = (EditText) findViewById(R.id.registr_password_two);
        Button registrdone = (Button) findViewById(R.id.registrdone);
        progressBar = (ProgressBar) findViewById(R.id.login_progressbar_registretion);

    registrdone.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            final String loginMail = reg_mail.getText().toString();
            String loginPassword = reg_password.getText().toString();
 String loginPasswordTwo = reg_password_two.getText().toString();

            if (!TextUtils.isEmpty(loginMail)&& !TextUtils.isEmpty(loginPassword)&& !TextUtils.isEmpty(loginPasswordTwo)){
              if(loginPassword.equals(loginPasswordTwo)){
                  progressBar.setVisibility(View.VISIBLE);

firebaseAuth.createUserWithEmailAndPassword(loginMail, loginPassword). addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
if (task.isSuccessful()){



    final String user_id = firebaseAuth.getCurrentUser().getUid();
    Map<String, Object> userMap = new HashMap<>();
    userMap.put("mail", loginMail);
    userMap.put("priority", "2");
    firebaseFirestore.collection("Users").document(user_id).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if(task.isSuccessful()){
                sendToMain();

            }else {
                String massege = task.getException().getMessage();
                Toast.makeText(RegistrationActivity.this,"Error :" + massege, Toast.LENGTH_LONG).show();
            }
        }
    });

    
    sendToMain();




}else {
    String errormMassage = task.getException().getMessage();
    Toast.makeText(RegistrationActivity.this, "Ошибка" + errormMassage , Toast.LENGTH_LONG).show();

}




progressBar.setVisibility(View.INVISIBLE);


    }
});
              }else {
                  Toast.makeText(RegistrationActivity.this, " Пароли не совпадают" , Toast.LENGTH_LONG).show();

              }
            }
        }
    });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser curentUser = firebaseAuth.getCurrentUser();
        if (curentUser!= null){
sendToMain();

        }
    }
    private void sendToMain() {
        Intent mainIntent  = new Intent(RegistrationActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
