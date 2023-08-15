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

public class TutorRegister extends AppCompatActivity {
    EditText regusername,regemail,regphone,regdistrict,regsubject,regpassword,regrepassword;
    Button button;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_register);
        regusername =(findViewById(R.id.username_reg_tutor));
        regemail =(findViewById(R.id.email_tutor));
        regphone =(findViewById(R.id.mobile_tutor));
        regdistrict =(findViewById(R.id.tutor_district));
        regsubject =(findViewById(R.id.tutor_subject));
        regpassword =(findViewById(R.id.password_tutor));
        regrepassword =(findViewById(R.id.repassword_tutor));
        button =(findViewById(R.id.tutor_registerbtn));
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformAuth();

            }
            private void PerformAuth() {
                database=FirebaseDatabase.getInstance();
                reference=database.getReference("tutors");
                String username = regusername.getText().toString();
                String email = regemail.getText().toString();
                String phone = regphone.getText().toString();
                String district = regdistrict.getText().toString();
                String subject = regsubject.getText().toString();
                String password = regpassword.getText().toString();
                String repassword = regrepassword.getText().toString();

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
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if(user == null) return;
                                String uid = user.getUid();

                                HelperClass2 helperClass = new HelperClass2(username,email,phone,district,subject,password,repassword);
                                reference.child(uid).setValue(helperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            sendUsertoNextActivity();
                                            progressDialog.dismiss();
                                            Toast.makeText(TutorRegister.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(TutorRegister.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }
        });
    }

    private void sendUsertoNextActivity() {
        Intent intent = new Intent(TutorRegister.this,TutorLogin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    }
