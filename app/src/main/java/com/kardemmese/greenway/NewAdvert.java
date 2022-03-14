package com.kardemmese.greenway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewAdvert extends AppCompatActivity {

    EditText aMarketName;
    EditText aProductName;
    Button btncont;

    DatabaseReference AdvertDbref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_advert);

        aMarketName = findViewById(R.id.editTextTextPersonName);
        aProductName = findViewById(R.id.editTextTextPersonName2);
        btncont = findViewById(R.id.button2);

        AdvertDbref = FirebaseDatabase.getInstance().getReference().child("Adverts");

        btncont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertAdvertsData();
            }
        });



    }
    private void  insertAdvertsData(){
        String Mname = aMarketName.getText().toString();
        String Pname = aProductName.getText().toString();

        Adverts adverts = new Adverts(Mname,Pname);

        AdvertDbref.push().setValue(adverts);
        Toast.makeText(NewAdvert.this,"Data inserted!",Toast.LENGTH_SHORT).show();

    }

    /*public void cont(View view) {
        Intent intent = new Intent(NewAdvert.this,MapsActivity.class);
        startActivity(intent);
    }*/

    public void Location(View view) {
        Intent intent = new Intent(NewAdvert.this,AllList.class);
        startActivity(intent);
    }
}