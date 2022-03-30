package com.kardemmese.greenway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterScreen extends AppCompatActivity {

    EditText aUserName, aEmail, aPassword;
    Button aRegisterBtn;
    TextView aLoginBtn;
    FirebaseAuth aAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        aUserName = findViewById(R.id.UserName);
        aEmail = findViewById(R.id.Email);
        aPassword = findViewById(R.id.Password);
        aRegisterBtn = findViewById(R.id.btnRegister);
        aLoginBtn = findViewById(R.id.LoginHereText);

        aAuth = FirebaseAuth.getInstance();


        if(aAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), RegisterScreen.class));
            finish();
        }
        aRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = aEmail.getText().toString().trim();
                String password = aPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    aEmail.setError("You must enter an Email.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    aPassword.setError("You must enter an password");
                    return;
                }
                if(password.length() < 6){
                    aPassword.setError("Your password mus be at least 6 character long.");
                    return;
                }

                aAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterScreen.this,"Your account is created.", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), RegisterScreen.class));
                        }else{
                            Toast.makeText(RegisterScreen.this, "Error !!!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        aLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginScreen.class));
            }
        });

    }
}