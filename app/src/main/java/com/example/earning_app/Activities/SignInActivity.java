package com.example.earning_app.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.earning_app.MainActivity;
import com.example.earning_app.R;
import com.example.earning_app.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    ActivitySignInBinding binding;
    FirebaseAuth auth;
    FirebaseUser user;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        dialog = new ProgressDialog(SignInActivity.this);
        dialog.setTitle("Create Account");
        dialog.setMessage("Login");

        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.show();

                auth.signInWithEmailAndPassword(binding.edtLogId.getText().toString() , binding.edtLogPass.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                dialog.dismiss();
                                if (task.isSuccessful()){

                                    Intent intent = new Intent(SignInActivity.this , MainActivity.class);
                                    startActivity(intent);

                                    Toast.makeText(SignInActivity.this, "Successful login", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(SignInActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SignInActivity.this , SignUpActivity.class);
                startActivity(intent);
            }
        });

        if (user != null){

            Intent intent = new Intent(SignInActivity.this , MainActivity.class);
            startActivity(intent);
        }


    }
}