package com.example.earning_app.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.earning_app.Activities.ScratchCardActivity;
import com.example.earning_app.Activities.SpinnerActivity;
import com.example.earning_app.Models.UserModel;
import com.example.earning_app.R;
import com.example.earning_app.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseUser user;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding =  FragmentHomeBinding.inflate(inflater, container, false);

        auth = FirebaseAuth.getInstance();
        database =FirebaseDatabase.getInstance();
        user = auth.getCurrentUser();


        binding.dailyReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final SweetAlertDialog dialog = new SweetAlertDialog(getContext() , SweetAlertDialog.PROGRESS_TYPE);
                dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DCB6"));
                dialog.setTitleText("loading");
                dialog.setCancelable(false);
                dialog.show();

                final Date currentDate = Calendar.getInstance().getTime();
                final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy" , Locale.ENGLISH);

                database.getReference().child("Daily Check").child(user.getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if (snapshot.exists()){
                                    String dbDateString = snapshot.child("Date").getValue(String.class);

                                    try {

                                        assert dbDateString != null;
                                        Date dbDate = dateFormat.parse(dbDateString);

                                        String dates = dateFormat.format(currentDate);
                                        Date date = dateFormat.parse(dates);

                                        if (date.after(dbDate) && date.compareTo(dbDate) != 0){

                                            database.getReference().child("Users").child(user.getUid())
                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                            UserModel model = snapshot.getValue(UserModel.class);

                                                            int currentCoins = (int) model.getCoins();
                                                            int update = currentCoins + 20;

                                                            int spinCoin = model.getSpins();
                                                            int updateSpin = spinCoin + 2;

                                                            HashMap<String , Object> map = new HashMap<>();
                                                            map.put("coins" , update);
                                                            map.put("spins" , updateSpin);

                                                            database.getReference().child("Users").child(user.getUid()).updateChildren(map);

                                                            Date newDate = Calendar.getInstance().getTime();
                                                            String newDateString = dateFormat.format(newDate);

                                                            HashMap<String , Object> dateMap = new HashMap<>();
                                                            dateMap.put("Date" , newDateString);

                                                            database.getReference().child("Daily Check").child(FirebaseAuth.getInstance().getUid())
                                                                    .setValue(dateMap)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {

                                                                            dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                                                            dialog.setTitleText("Success");
                                                                            dialog.setContentText("Coins added");
                                                                            dialog.setConfirmButton("Dismiss", new SweetAlertDialog.OnSweetClickListener() {
                                                                                @Override
                                                                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                                                                    dialog.dismissWithAnimation();

                                                                                }
                                                                            }).show();

                                                                        }
                                                                    });

                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                                                        }
                                                    });
                                        }

                                        else {
                                            dialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                            dialog.setTitleText("Failed");
                                            dialog.setContentText("you have already redemed , come back tomorrow");
                                            dialog.setConfirmButton("Dismiss" , null);
                                            dialog.show();

                                        }

                                    }
                                    catch (ParseException e){
                                        throw new RuntimeException(e);
                                    }
                                }
                                else {

                                    Toast.makeText(getContext(), "Data not exists", Toast.LENGTH_SHORT).show();

                                    dialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
                                    dialog.setTitleText("System Busy");
                                    dialog.setContentText("System is busy , please try later");
                                    dialog.setConfirmButton("Dismiss", new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                                            dialog.dismissWithAnimation();

                                        }
                                    });
                                    dialog.dismiss();

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismissWithAnimation();

                            }
                        });

            }
        });

        binding.spinWheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext() , SpinnerActivity.class);
                startActivity(intent);

            }
        });

        binding.scratchCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext() , ScratchCardActivity.class);
                startActivity(intent);

            }
        });

        binding.referAndEarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Not Availabel Right Now , Come back later", Toast.LENGTH_SHORT).show();
            }
        });

        binding.watchVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Not Availabel Right Now , Come back later", Toast.LENGTH_SHORT).show();
            }
        });

        binding.playQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Not Availabel Right Now , Come back later", Toast.LENGTH_SHORT).show();
            }
        });

        database.getReference().child("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                UserModel model = snapshot.getValue(UserModel.class);

                if (snapshot.exists()){

                    binding.userName.setText(model.getName());
                    binding.contact.setText(model.getMobileNumber());
                    binding.coins.setText(model.getCoins() + "");

                    Picasso.get()
                            .load(model.getProfile())
                            .placeholder(R.drawable.profile_image)
                            .into(binding.userProfile);
                }
                else {

                    Toast.makeText(getContext(), "data not exist", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        return binding.getRoot();
    }
}