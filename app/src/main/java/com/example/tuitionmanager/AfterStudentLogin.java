package com.example.tuitionmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class AfterStudentLogin extends AppCompatActivity {

    RecyclerView recview;
    AdapterClass adapter;
    BottomNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_student_login);
        getSupportActionBar().setTitle("List");

        recview=findViewById(R.id.recview);
        navigationView=findViewById(R.id.bottomnav);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.menu_home:
                        startActivity(getIntent());
                        finish();
                        overridePendingTransition(0,0);
                        break;
                    case R.id.menu_logout:
                        Intent intent2= new Intent(AfterStudentLogin.this,MainActivity2.class);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });
        recview.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<HelperClass2> options =
                new FirebaseRecyclerOptions.Builder<HelperClass2>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("tutors"), HelperClass2.class)
                        .build();
        adapter= new AdapterClass(options);
        recview.setAdapter(adapter);

    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.searchmenu,menu);

        MenuItem item= menu.findItem(R.id.search);

        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processsearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processsearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void processsearch(String query) {
        FirebaseRecyclerOptions<HelperClass2> options =
                new FirebaseRecyclerOptions.Builder<HelperClass2>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("tutors").orderByChild("subject").startAt(query).endAt(query+"uf8ff"), HelperClass2.class)
                        .build();
        adapter= new AdapterClass(options);
        adapter.startListening();
        recview.setAdapter(adapter);
    }
}