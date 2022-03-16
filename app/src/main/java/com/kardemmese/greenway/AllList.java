package com.kardemmese.greenway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AllList extends AppCompatActivity {

    EditText aName;
    EditText aLatitude;
    EditText aLongitude;
    Button btnsave;


    DatabaseReference mapDbref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_list);

        aName = findViewById(R.id.editTextTextPersonName5);
        aLatitude = findViewById(R.id.editTextTextPersonName3);
        aLongitude = findViewById(R.id.editTextTextPersonName4);
        btnsave= findViewById(R.id.btnsave);


        mapDbref = FirebaseDatabase.getInstance().getReference().child("Maps");

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertAdvertsData();
            }
        });



    }
    private void  insertAdvertsData(){
        String lat = aLatitude.getText().toString();
        String lon = aLongitude.getText().toString();

        Adverts adverts = new Adverts(lat,lon);

        mapDbref.push().setValue(adverts);
        Toast.makeText(AllList.this,"Data inserted!",Toast.LENGTH_SHORT).show();

    }

    public void SAVE(View view) {
        Intent intent = new Intent(AllList.this,NewAdvert.class);
        startActivity(intent);
    }
}