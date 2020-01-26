package com.example.deltahacks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Language extends AppCompatActivity{
    Button language_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        language_button = (Button) findViewById(R.id.french_button);

        language_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Language.this, Level.class); //from, to
                startActivity(in); //this line makes it crash
            }
        });
    }
}
