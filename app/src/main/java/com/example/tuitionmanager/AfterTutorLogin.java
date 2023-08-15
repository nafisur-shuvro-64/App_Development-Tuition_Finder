package com.example.tuitionmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AfterTutorLogin extends AppCompatActivity {
    private TextView nametext,phonetext,mailtext,districttext,subtext;
    private String username,email,phone,district,subject;
    private FirebaseAuth authProfile;
    private Button update,logout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_tutor_login);
        getSupportActionBar().setTitle("Home");
        nametext=findViewById(R.id.tutorname);
        phonetext=findViewById(R.id.tutorphone);
        mailtext=findViewById(R.id.tutoremail);
        districttext=findViewById(R.id.tutordis);
        subtext=findViewById(R.id.tutorsub);
        update=findViewById(R.id.tutordetails);
        logout=findViewById(R.id.tutorout);
        authProfile =FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        if (firebaseUser == null)
        {
            Toast.makeText(AfterTutorLogin.this,"Something went wrong!",Toast.LENGTH_LONG).show();
        }
        else
        {
            showUserProfile(firebaseUser);
        }
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AfterTutorLogin.this,UpdateTutorProfile.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authProfile.signOut();
                Toast.makeText(AfterTutorLogin.this,"Logged Out",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AfterTutorLogin.this,TutorLogin.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String userid = firebaseUser.getUid();
        DatabaseReference referenceProfile =FirebaseDatabase.getInstance().getReference("tutors");
        referenceProfile.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usedata helperClass2 =snapshot.getValue(Usedata.class);
                if(helperClass2!=null)
                {
                    username = helperClass2.username;
                    email = helperClass2.email;
                    phone= helperClass2.phone;
                    district = helperClass2.district;
                    subject = helperClass2.subject;


                    nametext.setText(username);
                    phonetext.setText(phone);
                    mailtext.setText(email);
                    districttext.setText(district);
                    subtext.setText(subject);
                }
                else{
                    Toast.makeText(AfterTutorLogin.this,"Something went too wrong!",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AfterTutorLogin.this,"Something went wrong!",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu items
        getMenuInflater().inflate(R.menu.common_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //when any option is selected

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.menu_refresh)
        {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0,0);
        }
        else if(id==R.id.menu_update_profile)
        {
            Intent intent = new Intent(AfterTutorLogin.this,UpdateTutorProfile.class);
            startActivity(intent);
        }
        else if(id == R.id.menu_logout)
        {
            authProfile.signOut();
            Toast.makeText(AfterTutorLogin.this,"Logged Out",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(AfterTutorLogin.this,TutorLogin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        else
        {
            Toast.makeText(AfterTutorLogin.this,"Something went wrong!",Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}