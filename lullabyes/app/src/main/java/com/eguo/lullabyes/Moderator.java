package com.eguo.lullabyes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Moderator extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moderator);
        Button loadaudio = findViewById(R.id.load_audio);
        Button loadtaile = findViewById(R.id.load_tailes);
        Button deletetile = findViewById(R.id.delete_tile_btn);
         Button autor = findViewById(R.id.addautor_tile_btn);

        autor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewAutorActivity.class);
                startActivity(intent);
            }
        });

        loadtaile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewPostActivity.class);
                startActivity(intent);
            }
        });
        loadaudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewAudioPost.class);
                startActivity(intent);
            }
        });
        deletetile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DeletePost.class);
                startActivity(intent);
            }
        });
    }
}
