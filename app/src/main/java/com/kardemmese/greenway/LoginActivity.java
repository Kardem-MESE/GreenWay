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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText aEmail, aPassword;
    Button aLoginBtn;
    TextView aCreateBtn;
    FirebaseAuth aAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        aEmail = findViewById(R.id.inputEmail);
        aPassword = findViewById(R.id.inputPassword);
        aAuth = FirebaseAuth.getInstance();
        aLoginBtn = findViewById(R.id.btnlogin);
        aCreateBtn = findViewById(R.id.button3);

        aLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputEmail = aEmail.getText().toString().trim();
                String inputPassword = aPassword.getText().toString().trim();

                if(TextUtils.isEmpty(inputEmail)){
                    aEmail.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(inputPassword)){
                    aPassword.setError("Password is required.");
                    return;
                }
                if(inputPassword.length() < 6){
                    aPassword.setError("Password Must be >= characters.");
                    return;
                }
                aAuth.signInWithEmailAndPassword(inputEmail,inputPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Logged in Successfully.",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(LoginActivity.this,"Error!" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        aCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });
    }
    public void login(View view) {
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void signup(View view) {
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
}