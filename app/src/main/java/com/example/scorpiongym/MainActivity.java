package com.example.scorpiongym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button btn1;
    private EditText Userlogin;
    private EditText Passwordlogin;
    private FirebaseAuth firebaseAuth;

    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        Userlogin = (EditText) findViewById(R.id.email);
        Passwordlogin = (EditText) findViewById(R.id.password);
        firebaseAuth = FirebaseAuth.getInstance();



        btn1 = (Button) findViewById(R.id.btn_login);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Inicio();
            }
        });
    }

    private void Inicio() {
        String email = Userlogin.getText().toString().trim();
        String pass  = Passwordlogin.getText().toString().trim();

        if (email.isEmpty()){
            Userlogin.setError("Email es requerido");
            Userlogin.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Userlogin.setError("Email no valido");
            Userlogin.requestFocus();
            return;
        }
        if (pass.isEmpty()){
            Passwordlogin.setError("Password es requerido");
            Passwordlogin.requestFocus();
            return;
        }
        if (pass.length()<6){
            Passwordlogin.setError("Minimo 6 digitos");
            Passwordlogin.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, MainActivity2.class));
                }else {
                    Toast.makeText(MainActivity.this, "Algo esta fallando", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}