package com.example.golan.train.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.golan.train.BL.MyService;
import com.example.golan.train.BL.User;
import com.example.golan.train.Fragments.LogIn_Fragment;
import com.example.golan.train.Fragments.SignUp_Fragment;
import com.example.golan.train.Interfaces.fromFragmentToMainActivity;
import com.example.golan.train.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity implements fromFragmentToMainActivity {

    private LogIn_Fragment logIn_Fragment;
    private SignUp_Fragment signUpFragment;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase;
    private MyService myService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myService = new MyService(this);
        initFireBase();


        if(firebaseAuth.getCurrentUser() != null){

            startActivity(new Intent(getApplicationContext(),MainScreenActivity.class));
            finish();
        }else{

            logIn_Fragment = new LogIn_Fragment();
            signUpFragment = new SignUp_Fragment();


            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.placeholder,logIn_Fragment);
            ft.commit();
        }
    }


    public void moveToActivity(){
        startActivity(new Intent(getApplicationContext(),MainScreenActivity.class));
    }

    @Override
    public void backFromJoinUsFragment(String mail,String password) {
        System.out.println("in backFromJoinUsFragment");
        signUpNewUserWithEmailPass(mail,password);
    }
    @Override
    public void backFromDetailsFragment(User user) throws JSONException {
        writeNewUser(user);
    }

    private void writeNewUser(User user) throws JSONException {
        myService.signUpNewUser(user);
    }


    public void initFireBase() {

        firebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
    }

    private void signUpNewUserWithEmailPass(String mail, String password) {
        System.out.println("in signUpNewUserWithEmailPass");
        firebaseAuth.createUserWithEmailAndPassword(mail,password)
                .addOnCompleteListener(this, task -> {
                    FirebaseMessaging.getInstance().subscribeToTopic(firebaseAuth.getCurrentUser().getUid())
                            .addOnCompleteListener(task1 -> System.out.println("Sign up complete"));
                    if(!task.isSuccessful()) {

                        Toast.makeText(MainActivity.this, "Sign up has Failed, no internet connection",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
