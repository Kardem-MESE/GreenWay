package com.kardemmese.greenway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText aEmail,aPassword;
    Button aLoignBtn;
    TextView aCreateBtn;
    FirebaseAuth aAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        aEmail = findViewById(R.id.Email);
        aPassword = findViewById(R.id.Password);
        aAuth = FirebaseAuth.getInstance();
        aLoignBtn = findViewById(R.id.btnLogin);
        aCreateBtn = findViewById(R.id.LoginHereText);

        aLoignBtn.setOnClickListener(new View.OnClickListener() {
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

                //authenticate the user

                aAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Logged in succefcully.", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), Blank1Fragment.class));
                        }else{
                            Toast.makeText(LoginActivity.this, "Error !!!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });

        aCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

    }
}