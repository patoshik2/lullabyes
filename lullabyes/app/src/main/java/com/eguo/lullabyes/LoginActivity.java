package com.eguo.lullabyes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    private static final String TAG ="111112" ;
    public EditText emailedittext, passwordedit;
    TextView contin_withou_reg;
FirebaseAuth auth;
SignInButton sing_google ;
//GoogleSignInClient mGoogleSignInClient;
    public FirebaseFirestore firebaseFirestore;

FirebaseAuth.AuthStateListener authStateListener;



    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        emailedittext = (EditText) findViewById(R.id.login_mail);
        passwordedit = (EditText) findViewById(R.id.login_password);
        Button singinacunt  = (Button) findViewById(R.id.login_aun);
        Button login_reg = (Button) findViewById(R.id.login_reg);
        firebaseFirestore = FirebaseFirestore.getInstance();
        contin_withou_reg =(TextView) findViewById(R.id.contin_withou_reg);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();



      //  mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

//sing_google = findViewById( R.id.sign_in_button);
        singinacunt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailedittext.getText().toString();
                String password = passwordedit.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

                    Toast.makeText(getApplicationContext(), "Вы оставили поле пустым!" , Toast.LENGTH_LONG).show();

                } else {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(getApplicationContext(), "Вы вошли в гостевой аккаунт!" , Toast.LENGTH_LONG).show();

                            }else {
                                Toast.makeText(getApplicationContext(), "Проверти правильность пароля или почты." , Toast.LENGTH_LONG).show();

                            }
                        }
                    });


                }
            }
        });

/*sing_google.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        singIn();

    }
});*/
        contin_withou_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String randomName = UUID.randomUUID().toString();
 final String randompassword = UUID.randomUUID().toString();


                auth.createUserWithEmailAndPassword(randomName +"@gmail.com", randompassword). addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){



                            final String user_id = auth.getCurrentUser().getUid();
                            Map<String, Object> userMap = new HashMap<>();
                            userMap.put("mail", randomName +"@gmail.com");
                            userMap.put("priority", "1");
                            firebaseFirestore.collection("Users").document(user_id).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        auth.signInWithEmailAndPassword(randomName +"@gmail.com", randompassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(getApplicationContext(), "Вы вошли в гостевой аккаунт" , Toast.LENGTH_LONG).show();

                                                }
                                            }
                                        });

                                    }else {
                                        String massege = task.getException().getMessage();
                                        Toast.makeText(LoginActivity.this,"Error :" + massege, Toast.LENGTH_LONG).show();
                                    }
                                }
                            });







                        }else {
                            String errormMassage = task.getException().getMessage();
                            Toast.makeText(LoginActivity.this, "Ошибка" + errormMassage , Toast.LENGTH_LONG).show();

                        }




                      //  progressBar.setVisibility(View.INVISIBLE);


                    }
                });





            }
        });



login_reg.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
    }
});
       authStateListener = new FirebaseAuth.AuthStateListener() {
           @Override
           public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               if (firebaseAuth.getCurrentUser()!= null){
                   startActivity(new Intent(LoginActivity.this, MainActivity.class));
               }
           }
       };



    }

/*    private void singIn() {
        Intent signInIntent;
        signInIntent =mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }*/

/*    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }


        }
    }*/

   /* private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                            updateUI(null);
                        }

                        // ...
                    }
                });
    }*/



/*    private void updateUI(FirebaseUser acct) {
        String personEmail = acct.getEmail();
        String personId = acct.getUid();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("mail", personEmail);
        userMap.put("priority", "2");
        firebaseFirestore.collection("Users").document(personId).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){




                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    Toast.makeText(LoginActivity.this,"Добро пожаловать!!!", Toast.LENGTH_LONG).show();
                }else {
                    String massege = task.getException().getMessage();
                    Toast.makeText(LoginActivity.this,"Error :" + massege, Toast.LENGTH_LONG).show();
                }
            }
        });

    }*/


}
