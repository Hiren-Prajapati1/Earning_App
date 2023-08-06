package com.example.earning_app.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.earning_app.Models.UserModel;
import com.example.earning_app.R;
import com.example.earning_app.SpinWheel.LuckyItem;
import com.example.earning_app.SpinWheel.LuckyWheelView;
import com.example.earning_app.databinding.ActivitySpinnerBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SpinnerActivity extends AppCompatActivity {

    ActivitySpinnerBinding binding;
    List<LuckyItem>data;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;

    long cash;
    int currentSpin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpinnerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        user = auth.getCurrentUser();

        data = new ArrayList<>();

        loadCoin();

        LuckyItem luckyItem1 = new LuckyItem();
        luckyItem1.topText = "5";
        luckyItem1.secondaryText = "Coins";
        luckyItem1.textColor = Color.parseColor("#212121");
        luckyItem1.color = Color.parseColor("#eceff1");
        data.add(luckyItem1);

        LuckyItem luckyItem2 = new LuckyItem();
        luckyItem2.topText = "10";
        luckyItem2.secondaryText = "Coins";
        luckyItem2.textColor = Color.parseColor("#00cf00");
        luckyItem2.color = Color.parseColor("#ffffff");
        data.add(luckyItem2);

        LuckyItem luckyItem3 = new LuckyItem();
        luckyItem3.topText = "15";
        luckyItem3.secondaryText = "Coins";
        luckyItem3.textColor = Color.parseColor("#212121");
        luckyItem3.color = Color.parseColor("#eceff1");
        data.add(luckyItem3);

        LuckyItem luckyItem4 = new LuckyItem();
        luckyItem4.topText = "20";
        luckyItem4.secondaryText = "Coins";
        luckyItem4.textColor = Color.parseColor("#7f00d9");
        luckyItem4.color = Color.parseColor("#ffffff");
        data.add(luckyItem4);

        LuckyItem luckyItem5 = new LuckyItem();
        luckyItem5.topText = "25";
        luckyItem5.secondaryText = "Coins";
        luckyItem5.textColor = Color.parseColor("#212121");
        luckyItem5.color = Color.parseColor("#eceff1");
        data.add(luckyItem5);

        LuckyItem luckyItem6 = new LuckyItem();
        luckyItem6.topText = "30";
        luckyItem6.secondaryText = "Coins";
        luckyItem6.textColor = Color.parseColor("#dc0000");
        luckyItem6.color = Color.parseColor("#ffffff");
        data.add(luckyItem6);

        LuckyItem luckyItem7 = new LuckyItem();
        luckyItem7.topText = "35";
        luckyItem7.secondaryText = "Coins";
        luckyItem7.textColor = Color.parseColor("#212121");
        luckyItem7.color = Color.parseColor("#eceff1");
        data.add(luckyItem7);

        LuckyItem luckyItem8 = new LuckyItem();
        luckyItem8.topText = "0";
        luckyItem8.secondaryText = "Coins";
        luckyItem8.textColor = Color.parseColor("#008bff");
        luckyItem8.color = Color.parseColor("#ffffff");
        data.add(luckyItem8);

        binding.wheelview.setData(data);
        binding.wheelview.setRound(5);

        binding.spinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random random = new Random();
                int randomNumber = random.nextInt(8);
                binding.wheelview.startLuckyWheelWithTargetIndex(randomNumber);

            }
        });

        binding.wheelview.setLuckyRoundItemSelectedListener(new LuckyWheelView.LuckyRoundItemSelectedListener() {
            @Override
            public void LuckyRoundItemSelected(int index) {

                updateCash(index);

            }
        });

    }

    private void loadCoin() {

        reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                UserModel model = snapshot.getValue(UserModel.class);

                binding.coin.setText(String.valueOf(model.getCoins()));

                currentSpin = model.getSpins();

                binding.currentSp.setText(String.valueOf(currentSpin));

                if (currentSpin == 0){

                    binding.spinBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(SpinnerActivity.this, "invalid", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void updateCash(int index) {

        cash = 0;

        switch (index){

            case 0:
                cash = 5;
                break;

            case 1:
                cash = 10;
                break;

            case 3:
                cash = 15;
                break;

            case 4:
                cash = 20;
                break;

            case 5:
                cash = 25;
                break;

            case 6:
                cash = 30;
                break;

            case 7:
                cash = 35;
                break;

            case 8:
                cash = 40;
                break;

        }

        String value = String.valueOf(cash);

        updateCoins(value);

    }

    private void updateCoins(String value) {

        int currentCoins = Integer.parseInt(binding.coin.getText().toString());
        int updateCoins = currentCoins + Integer.parseInt(value);

        int updatedSpin = currentSpin - 1;

        HashMap<String , Object> map = new HashMap<>();
        map.put("coins" , updateCoins);
        map.put("spins" , updatedSpin);

        reference.child(user.getUid())
                .updateChildren(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){

                            Toast.makeText(SpinnerActivity.this, "Coin added", Toast.LENGTH_SHORT).show();

                        }
                        else {

                            Toast.makeText(SpinnerActivity.this, "error", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

    }
}