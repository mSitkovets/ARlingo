package com.example.deltahacks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Level extends AppCompatActivity {
    Button level_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

       // level_button = (Button) findViewById(R.id.start_btn);

//        level_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              //  Intent in = new Intent(Level.this, AR.class); //from, to
//                //startActivity(in); //this line makes it crash
//            }
//        });
    }
}
