package com.example.tuitionmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TutorDetails extends AppCompatActivity {
    TextView userdet,disdet,subdet,emaildet,phndet;
    Button callbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_details);
        userdet=findViewById(R.id.usernamedet);
        disdet=findViewById(R.id.districtdet);
        subdet=findViewById(R.id.subjectdet);
        emaildet=findViewById(R.id.emaildet);
        phndet=findViewById(R.id.phndet);
        Intent data = getIntent();
        String userData = data.getStringExtra("username");
        String subData = data.getStringExtra("subject");
        String disData= data.getStringExtra("district");
        String emaildata= data.getStringExtra("email");
        String phndata= data.getStringExtra("phone");
        userdet.setText(userData);
        subdet.setText(subData);
        disdet.setText(disData);
        emaildet.setText(emaildata);
        phndet.setText(phndata);

        callbtn=findViewById(R.id.calltutor);
        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uri = "tel:" + phndata.trim();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu items
        getMenuInflater().inflate(R.menu.menu2,menu);
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

        else if(id == R.id.menu_logout)
        {
            Toast.makeText(TutorDetails.this,"Logged Out",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(TutorDetails.this,MainActivity2.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        else
        {
            Toast.makeText(TutorDetails.this,"Something went wrong!",Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}