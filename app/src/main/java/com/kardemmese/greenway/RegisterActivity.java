package com.kardemmese.greenway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText aFullName,aEmail,aPassword;
    Button aRegisterBtn;
    TextView aLoginBtn;
    FirebaseAuth aAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        aFullName = findViewById(R.id.inputUsernamee);
        aEmail = findViewById(R.id.inputEmail);
        aPassword = findViewById(R.id.inputPassword);
        aRegisterBtn = findViewById(R.id.btnRegister);
        aLoginBtn = findViewById(R.id.btnlogin);
        aAuth = FirebaseAuth.getInstance();

        if(aAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        aRegisterBtn.setOnClickListener(new View.OnClickListener() {
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
                aAuth.createUserWithEmailAndPassword(inputEmail,inputPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           Toast.makeText(RegisterActivity.this,"User Created.",Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getApplicationContext(),MainActivity.class));
                       }else{
                           Toast.makeText(RegisterActivity.this,"Error!" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                       }
                    }
                });
            }
        });
        aLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }
    public void register(View view) {
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    public void account(View view) {
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}