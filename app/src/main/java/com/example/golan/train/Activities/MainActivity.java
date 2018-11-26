package com.example.golan.train.Activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.golan.train.Fragments.LogIn_Fragment;
import com.example.golan.train.Fragments.SignUp_Fragment;
import com.example.golan.train.R;

public class MainActivity extends AppCompatActivity {

    private LogIn_Fragment logIn_Fragment;
    private SignUp_Fragment signUpFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO initialize Fire Base

        logIn_Fragment = new LogIn_Fragment();
        signUpFragment = new SignUp_Fragment();


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.placeholder,logIn_Fragment);
        ft.commit();
    }
}
