package com.example.earning_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.earning_app.Fragments.HomeFragment;
import com.example.earning_app.Fragments.ProfileFragment;
import com.example.earning_app.Fragments.RewardsFragment;
import com.example.earning_app.databinding.ActivityMainBinding;
import com.iammert.library.readablebottombar.ReadableBottomBar;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container , new HomeFragment());
        transaction.commit();

        binding.readableBottomBar.setOnItemSelectListener(new ReadableBottomBar.ItemSelectListener() {
            @Override
            public void onItemSelected(int i) {

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                switch (i){

                    case 0:
                        transaction.replace(R.id.container , new HomeFragment());
                        break;

                    case 1:
                        transaction.replace(R.id.container , new RewardsFragment());
                        break;

                    case 2:
                        transaction.replace(R.id.container , new ProfileFragment());
                        break;
                }

                transaction.commit();

            }
        });
    }
}