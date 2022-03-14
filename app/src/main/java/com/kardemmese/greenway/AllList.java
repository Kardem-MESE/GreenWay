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

public class AllList extends AppCompatActivity implements View.OnClickListener {
private DatabaseReference mDatabase;
private Button btnsave;
private Button btnProceed;
private TextView textViewLatitude;
private TextView textViewLongitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_list);
        btnProceed = (Button) findViewById(R.id.btnProceed);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        textViewLatitude=(TextView) findViewById(R.id.textViewLatitude);
        textViewLongitude=(TextView) findViewById(R.id.textViewLongitude);
        btnsave=(Button) findViewById(R.id.btnsave);
        btnsave.setOnClickListener(this);
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AllList.this,MapsActivity.class);
                startActivity(i);
            }
        });
    }
    private void saveUserInformation(){
        double latitude = Double.parseDouble(textViewLatitude.getText().toString().trim());
        double longitude = Double.parseDouble(textViewLongitude.getText().toString().trim());
        Userinformation userinformation = new Userinformation(latitude,longitude);
        mDatabase.child("Users").setValue(userinformation);
        Toast.makeText(this,"Saved",Toast.LENGTH_LONG).show();
    }
    //emrahı çok seviyom emrenin aq

    @Override
    public void onClick(View view) {
        if (view == btnProceed){
            finish();
        }
        if (view == btnsave){
            saveUserInformation();

            textViewLatitude.setText("");
            textViewLongitude.setText("");


        }

    }

    public void PROCEED(View view) {
        Intent intent = new Intent(AllList.this,MapsActivity.class);
        startActivity(intent);
    }
}