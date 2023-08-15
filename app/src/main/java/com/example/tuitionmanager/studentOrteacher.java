package com.example.tuitionmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class studentOrteacher extends AppCompatActivity {
    Button Student;
    Button Tutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_orteacher);
        Student=(Button)findViewById(R.id.student);

        Student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });
        Tutor=(Button)findViewById(R.id.tutor);
        Tutor.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               openselectionOftutor();
           }
       });

    }
    public void openMainActivity(){
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void openselectionOftutor(){
        Intent intent =new Intent(this,selectionOftutor.class);
        startActivity(intent);
    }
}