package com.example.tuitionmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassStudent extends AppCompatActivity {
    private Button frgtbtn;
    private EditText frgtemail;
    private String email;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_student);
        frgtbtn = findViewById(R.id.button_password_reset);
        frgtemail=findViewById(R.id.editText_password_reset_email);
        auth = FirebaseAuth.getInstance();
        frgtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }

    private void validateData() {
        email = frgtemail.getText().toString();
        if(email.isEmpty())
        {
            frgtemail.setError("Required");
        }
        else
        {
            forgetpass();
        }
    }

    private void forgetpass() {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(ForgetPassStudent.this, "Check your email", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgetPassStudent.this,MainActivity2.class));
                    finish();
                }
                else
                {
                    Toast.makeText(ForgetPassStudent.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}