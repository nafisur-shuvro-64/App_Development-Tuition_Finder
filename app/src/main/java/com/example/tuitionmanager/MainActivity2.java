package com.example.tuitionmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity2 extends AppCompatActivity {
    EditText regemail, regpassword;
    TextView forget,home;
    Button btn;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
         regemail = findViewById(R.id.email);
         regpassword = findViewById(R.id.password);
         btn = findViewById(R.id.loginbtn);
         forget=findViewById(R.id.forgetpass);
         home=findViewById(R.id.back_to_home);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();

            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity2.this,ForgetPassStudent.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity2.this,studentOrteacher.class);
                startActivity(intent);
            }
        });


    }

    private void performLogin() {
        String email = regemail.getText().toString();

        String password = regpassword.getText().toString();

        if (!email.matches(emailPattern)) {
            regemail.setError("Enter Correct email");
        } else if (password.isEmpty() || password.length() < 6) {
            regpassword.setError("Enter proper password");
        } else {
            progressDialog.setMessage("Please wait while login");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        sendUsertoNextActivity();
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity2.this,"Login Successful",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity2.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void sendUsertoNextActivity() {
        Intent intent = new Intent(MainActivity2.this,AfterStudentLogin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}