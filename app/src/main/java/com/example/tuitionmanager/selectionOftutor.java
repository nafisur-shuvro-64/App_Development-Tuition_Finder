package com.example.tuitionmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class selectionOftutor extends AppCompatActivity {
    Button button;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.button1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTutorLogin();
            }
        });
        button2=(Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openregister_activity();
            }
        });

    }
    public void openTutorLogin(){
        Intent intent =new Intent(this,TutorLogin.class);
        startActivity(intent);
    }
    public void openregister_activity(){
        Intent intent =new Intent(this,TutorRegister.class);
        startActivity(intent);
    }
}