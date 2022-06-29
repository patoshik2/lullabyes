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
import androidx.annotation.Nullable;
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

public class NewAudioPost extends AppCompatActivity {
    private Button chooseAudio;
    protected ImageView imageView;
    private EditText editTextName;
    private EditText editTextpriority;
    private EditText editlanguges;
private EditText editClassifirkator;

    private ProgressBar progressBar;
    private Uri postImageUri = null;
    private Uri postAudioUri = null;
    public StorageReference mStorageRef;
    public FirebaseFirestore firebaseFirestore;
    private  String user_id;
    private Bitmap compressedImageFile;
     private Bitmap compressedAudioFile;

    private  final int CODE_ONE_VIDEO = 1;
    private  final int CODE_MULTIPEAUDIO = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_audio_post2);
        firebaseFirestore = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();


        Button sendNewPost = (Button) findViewById(R.id.send_new_audiopost);
        imageView = (ImageView) findViewById(R.id.audiopostimg);
        editTextName = (EditText) findViewById(R.id.edin_name_audiopost);
        editClassifirkator = (EditText) findViewById(R.id.edin_class_audiopost);

       editTextpriority = (EditText) findViewById(R.id.edin_priority_audiopost);
        EditText editType = (EditText) findViewById(R.id.type_audio);

        EditText editTextClassifikAutor = (EditText) findViewById(R.id.edin_class_audiopost);
        progressBar = (ProgressBar)findViewById(R.id.login_progressbar_audiopostsending);
        editlanguges = (EditText) findViewById(R.id.edin_languge_audiopost);
        findViewById(R.id.audiofile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent,CODE_MULTIPEAUDIO);
            }
        });



        sendNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nameTaile = editTextName.getText().toString();
final  String classifikAutor = editClassifirkator.getText().toString();
final  String editlangugess = editlanguges.getText().toString();
final  String editTypeS  = editType.getText().toString();

    if (!TextUtils.isEmpty(nameTaile) && postImageUri != null) {
        final String nameTailePriority = editTextpriority.getText().toString();
        progressBar.setVisibility(View.VISIBLE);

        final String randomName = UUID.randomUUID().toString();

        // PHOTO UPLOAD
        File newImageFile = new File(postImageUri.getPath());
        try {

            compressedImageFile = new Compressor(NewAudioPost.this)
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

        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {

                mStorageRef.child("post_images").child(randomName + ".jpg").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {

                            File newThumbFile = new File(postImageUri.getPath());
                            try {

                                compressedImageFile = new Compressor(NewAudioPost.this)
                                        .setMaxHeight(100)
                                        .setMaxWidth(100)
                                        .setQuality(1)
                                        .compressToBitmap(newThumbFile);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            // Audio UPLOAD
                            final String randomNameAudio = UUID.randomUUID().toString();



                            //  File newAudioFile = new File(postAudioUri.getPath());
                            StorageReference storageReference1 = mStorageRef.child("post_audio").child(randomNameAudio + ".mp3");
UploadTask uploadTask1 = storageReference1.putFile(postAudioUri);
                            uploadTask1.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    mStorageRef.child("post_audio").child(randomNameAudio + ".mp3").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task1) {
                                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                            compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                            byte[] thumbData = baos.toByteArray();

StorageReference storageReference2  =mStorageRef.child("post_images/thumbs")
        .child(randomName + ".jpg");
                                            UploadTask uploadTask2 = storageReference2.putBytes(thumbData);


                                            uploadTask2.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                                    mStorageRef.child("post_images/thumbs")
                                                            .child(randomName + ".jpg").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Uri> task2) {


                                                            String downloadthumbUri = taskSnapshot.getMetadata().toString();

                                                            Map<String, Object> postMap = new HashMap<>();
                                                            postMap.put("image_url", task.getResult().toString());
                                                            postMap.put("audio_url", task1.getResult().toString());
                                                            postMap.put("priority", nameTailePriority);
                                                            postMap.put("image_thumb", task2.getResult().toString());
                                                            postMap.put("name", nameTaile);
                                                            postMap.put("search", nameTaile.toLowerCase());
                                                            postMap.put("classautor", classifikAutor);
                                                            postMap.put("user_id", user_id);
                                                            postMap.put("timestamp", FieldValue.serverTimestamp());
                                                            postMap.put("type", editTypeS);
                                                            postMap.put("editlanguges", editlangugess );
postMap.put("like", 0 );

                                                            firebaseFirestore.collection("Posts").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<DocumentReference> task) {

                                                                    if (task.isSuccessful()) {

                                                                        Toast.makeText(NewAudioPost.this, "Post was added", Toast.LENGTH_LONG).show();
                                                                        Intent mainIntent = new Intent(NewAudioPost.this, MainActivity.class);
                                                                        startActivity(mainIntent);
                                                                        finish();

                                                                    } else {


                                                                    }

                                                                    progressBar.setVisibility(View.INVISIBLE);

                                                                }
                                                            });
                                                        }
                                                    });



                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                    //Error handling

                                                }
                                            });
                                        }
                                    });


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
                        .setMinCropResultSize(300,200)
                        .setAspectRatio(3,2)
                        .start(NewAudioPost.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                progressBar.setVisibility(View.VISIBLE);
                postImageUri = result.getUri();
                imageView.setImageURI(postImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }else if (requestCode == CODE_MULTIPEAUDIO && resultCode == RESULT_OK){
           if (data != null)
          postAudioUri = data.getData();

        }
    }
}
