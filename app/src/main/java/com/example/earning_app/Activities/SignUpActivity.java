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
import com.example.earning_app.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseUser user;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = auth.getCurrentUser();

        dialog = new ProgressDialog(SignUpActivity.this);
        dialog.setTitle("Create Account");
        dialog.setMessage("Creating your account");

        binding.btnSignUp.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.show();

                auth.createUserWithEmailAndPassword(binding.editEmail.getText().toString() , binding.editPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                dialog.dismiss();
                                if (task.isSuccessful()){

                                    FirebaseUser user = auth.getCurrentUser();

                                    String email = binding.editEmail.getText().toString();
                                    String refer = email.substring(0 , email.lastIndexOf("@"));
                                    String referCode = refer.replace("." , "");

                                    HashMap<String , Object> map = new HashMap<>();


                                    map.put("name" , binding.editName.getText().toString());
                                    map.put("mobileNumber" , binding.editMobileNumber.getText().toString());
                                    map.put("email" , binding.editEmail.getText().toString());
                                    map.put("password" , binding.editPassword.getText().toString());
                                    map.put("profile" , "https://firebasestorage.googleapis.com/v0/b/earning-app-database-78ae4.appspot.com/o/profile_image.png?alt=media&token=29fa6252-28f2-4c82-99bd-f885de0b00a6");
                                    map.put("referCode" , referCode);
                                    map.put("coins" , 20);
                                    map.put("spins" , 4);

                                    Date date = Calendar.getInstance().getTime();
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy" , Locale.ENGLISH);
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTime(date);

                                    calendar.add(Calendar.DAY_OF_MONTH , -1);
                                    Date previousDate = calendar.getTime();

                                    String dataString = dateFormat.format(previousDate);

                                    database.getReference().child("Daily Check").child(user.getUid()).child("Date").setValue(dataString);



                                    database.getReference().child("Users").child(user.getUid()).setValue(map);

                                    Intent intent = new Intent(SignUpActivity.this , MainActivity.class);
                                    startActivity(intent);


                                    Toast.makeText(SignUpActivity.this, "Your account is created", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        binding.alreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this , SignInActivity.class);
                startActivity(intent);
            }
        });

        binding.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SignUpActivity.this , SignInActivity.class);
                startActivity(intent);

            }
        });
    }
}