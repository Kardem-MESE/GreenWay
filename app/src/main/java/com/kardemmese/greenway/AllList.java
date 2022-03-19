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
    EditText aAddress;
    EditText aCountry;
    Button btnsave;


    DatabaseReference mapDbref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_list);

        aName = findViewById(R.id.editTextTextPersonName5);
        aAddress = findViewById(R.id.editTextTextPersonName3);
        aCountry = findViewById(R.id.editTextTextPersonName4);
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
        String add = aAddress.getText().toString();
        String coun = aCountry.getText().toString();


        Adverts adverts = new Adverts(add,coun);

        mapDbref.push().setValue(adverts);
        Toast.makeText(AllList.this,"Data inserted!",Toast.LENGTH_SHORT).show();

    }

    public void SAVE(View view) {
        Intent intent = new Intent(AllList.this,NewAdvert.class);
        startActivity(intent);
    }
}