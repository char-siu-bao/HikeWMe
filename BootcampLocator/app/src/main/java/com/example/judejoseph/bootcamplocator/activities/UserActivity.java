package com.example.judejoseph.bootcamplocator.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.judejoseph.bootcamplocator.R;
import com.example.judejoseph.bootcamplocator.fragments.UserFragement;

public class UserActivity extends AppCompatActivity {

    private UserFragement userFragement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        setTitle("Find Trails Around You");

        userFragement = (UserFragement) getSupportFragmentManager().findFragmentById(R.id.container_user);

        if(userFragement == null){
            userFragement = UserFragement.newInstance();
            getSupportFragmentManager().
                    beginTransaction().
                    add(R.id.container_user, userFragement).
                    commit();
        }

    }
}
