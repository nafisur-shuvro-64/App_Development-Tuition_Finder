package com.example.tuitionmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register_activity extends AppCompatActivity {
    EditText regusername,regemail,regphone,regpassword,regrepassword;
    Button regbutton;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        regusername =(findViewById(R.id.username_reg));
        regemail =(findViewById(R.id.email));
        regphone =(findViewById(R.id.mobile));
        regpassword =(findViewById(R.id.password_reg));
        regrepassword =(findViewById(R.id.repassword));
        regbutton =(findViewById(R.id.registerbtn));
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformAuth();

            }

            private void PerformAuth() {
                database=FirebaseDatabase.getInstance();
                reference=database.getReference("students");
                String username = regusername.getText().toString();
                String email = regemail.getText().toString();
                String phone = regphone.getText().toString();
                String password = regpassword.getText().toString();
                String repassword = regrepassword.getText().toString();
                HelperClass helperClass = new HelperClass(username,email,phone,password,repassword);
                reference.child(username).setValue(helperClass);
                if(!email.matches(emailPattern))
                {
                    regemail.setError("Enter Correct email");
                }
                else if(password.isEmpty() || password.length()<6)
                {
                    regpassword.setError("Enter proper password");
                }
                else if(!password.equals(repassword))
                {
                    regrepassword.setError("Password not matched!!");
                }
                else
                {
                    progressDialog.setMessage("Please wait while registration");
                    progressDialog.setTitle("Registration");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                sendUsertoNextActivity();
                                progressDialog.dismiss();
                                Toast.makeText(register_activity.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(register_activity.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }
        });
    }

    private void sendUsertoNextActivity() {
        Intent intent = new Intent(register_activity.this,MainActivity2.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}