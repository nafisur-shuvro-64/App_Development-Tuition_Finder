package com.example.tuitionmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateTutorProfile extends AppCompatActivity {
    private EditText editusername,editphone,editdistrict,editsubject;
    private TextView textmail;
    private String name,mobile,location,subject,email;
    private FirebaseAuth authProfile;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tutor_profile);
        getSupportActionBar().setTitle("Update Profile");
        progressBar=findViewById(R.id.progressbar);
        editusername=findViewById(R.id.edittext_updateusername);
        editphone=findViewById(R.id.edittext_updatephn);
        editdistrict=findViewById(R.id.edittext_updatedis);
        editsubject=findViewById(R.id.edittext_updatesub);
        textmail=findViewById(R.id.edittext_updateemail);
        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        showProfile(firebaseUser);
        Button buttonUpdate = findViewById(R.id.button_updatepro);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile(firebaseUser);
            }
        });
    }

    private void updateProfile(FirebaseUser firebaseUser) {
        name = editusername.getText().toString();
        email= textmail.getText().toString();
        mobile = editphone.getText().toString();
        location = editdistrict.getText().toString();
        subject = editsubject.getText().toString();
        Usedata writeData = new Usedata(name, mobile, location, subject, email);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tutors");
        String uid = firebaseUser.getUid();
        progressBar.setVisibility(View.VISIBLE);
        reference.child(uid).setValue(writeData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                    firebaseUser.updateProfile(profileUpdate);
                    Toast.makeText(UpdateTutorProfile.this,"Profile updated",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(UpdateTutorProfile.this,AfterTutorLogin.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    try{
                        throw task.getException();
                    }catch (Exception e){
                        Toast.makeText(UpdateTutorProfile.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void showProfile(FirebaseUser firebaseUser) {
        String userid = firebaseUser.getUid();
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("tutors");
        progressBar.setVisibility(View.VISIBLE);
        reff.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usedata helperClass2 =snapshot.getValue(Usedata.class);
                if(helperClass2!=null)
                {
                    name = helperClass2.username;
                    email = helperClass2.email;
                    mobile= helperClass2.phone;
                    location = helperClass2.district;
                    subject = helperClass2.subject;


                    editusername.setText(name);
                    textmail.setText(email);
                    editphone.setText(mobile);
                    editdistrict.setText(location);
                    editsubject.setText(subject);
                }
                else
                {
                    Toast.makeText(UpdateTutorProfile.this,"Something went wrong!",Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateTutorProfile.this,"Something went wrong!",Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}