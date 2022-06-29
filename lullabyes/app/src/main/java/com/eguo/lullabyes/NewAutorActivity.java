package com.eguo.lullabyes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import id.zelory.compressor.Compressor;

public class NewAutorActivity extends AppCompatActivity {
    protected ImageView imageView;
    private EditText editTextName,edinPriority, editlanguge;
    private ProgressBar progressBar;
    private Uri postImageUri = null;


    public StorageReference mStorageRef;
    public FirebaseFirestore firebaseFirestore;
    public   String user_id;
    public Bitmap compressedImageFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_autor);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();

        Button sendNewPost = (Button) findViewById(R.id.send_new_autorpost);
        imageView = (ImageView) findViewById(R.id.autorpostimg);
        editTextName = (EditText) findViewById(R.id.edin_name_autorpost);
        edinPriority = (EditText) findViewById(R.id.edin_priority_autorpost);
        editlanguge  = (EditText) findViewById(R.id.edin_langu_autorpost);


        progressBar = (ProgressBar)findViewById(R.id.login_progressbar_autorpostsending);

        sendNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nameTaile = editTextName.getText().toString();



                if(!TextUtils.isEmpty(nameTaile) && postImageUri != null){
                    final String priorityString = edinPriority.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    final String editlanguges = editlanguge.getText().toString();
                    final String randomName = UUID.randomUUID().toString();

                    // PHOTO UPLOAD
                    File newImageFile = new File(postImageUri.getPath());
                    try {

                        compressedImageFile = new Compressor(NewAutorActivity.this)
                                .setMaxHeight(720)
                                .setMaxWidth(720)
                                .setQuality(50)
                                .compressToBitmap(newImageFile);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageData = baos.toByteArray();





                    // PHOTO UPLOAD
                    final StorageReference storageReference =mStorageRef.child("post_images").child(randomName + ".jpg");

                    UploadTask uploadTask = storageReference.putBytes(imageData);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mStorageRef.child("post_images").child(randomName + ".jpg").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {

                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if(task.isSuccessful()){

                                        File newThumbFile = new File(postImageUri.getPath());
                                        try {

                                            compressedImageFile = new Compressor(NewAutorActivity.this)
                                                    .setMaxHeight(100)
                                                    .setMaxWidth(100)
                                                    .setQuality(1)
                                                    .compressToBitmap(newThumbFile);

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                        compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                        byte[] thumbData = baos.toByteArray();







                                        final StorageReference storageReference1 = mStorageRef.child("post_images/thumbs") .child(randomName + ".jpg");
                                        UploadTask uploadTask1 = storageReference1.putBytes(thumbData);
                                        uploadTask1.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                mStorageRef.child("post_images/thumbs").child(randomName + ".jpg").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                    @Override public void onComplete(@NonNull Task<Uri> task1) {
                                                        if(task1.isSuccessful()){
                                                            Map<String, Object> postMap = new HashMap<>();
                                                            postMap.put("image_url", task.getResult().toString());
                                                            postMap.put("image_thumb", task1.getResult().toString());
                                                            postMap.put("priority", priorityString);

                                                            postMap.put("editlanguges", editlanguges);
                                                            postMap.put("name", nameTaile);
                                                            postMap.put("search", nameTaile.toLowerCase());
postMap.put("type", "autor");

                                                            postMap.put("user_id", user_id);
                                                            postMap.put("timestamp", FieldValue.serverTimestamp());



                                                            firebaseFirestore.collection("Autor").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {@Override public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                if(task.isSuccessful()){
                                                                    Toast.makeText(NewAutorActivity.this, "Post was added", Toast.LENGTH_LONG).show();
                                                                    Intent mainIntent = new Intent(NewAutorActivity.this, MainActivity.class);startActivity(mainIntent);finish();
                                                                } else {

                                                                }
                                                                progressBar.setVisibility(View.INVISIBLE);
                                                            }}); }}});




                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                                //Error handling

                                            }
                                        });


                                    } else {

                                        progressBar.setVisibility(View.INVISIBLE);

                                    }
                                }
                            });
                        }
                    });











                }

            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(100,100)
                        .setAspectRatio(1,1)
                        .start(NewAutorActivity.this);
            }
        });






    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                postImageUri = result.getUri();
                imageView.setImageURI(postImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }
}
