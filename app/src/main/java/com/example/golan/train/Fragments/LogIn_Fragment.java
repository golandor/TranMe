package com.example.golan.train.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.golan.train.R;

public class LogIn_Fragment extends Fragment {

    private Button buttonLogin;
    private Button buttonJoinUs;
    private EditText editTextMail;
    private EditText editTextPassword;

    private SignUp_Fragment signUpFragment;
    private LogIn_Fragment logIn_fragment;

    public LogIn_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in_, container, false);

        signUpFragment = new SignUp_Fragment();
        logIn_fragment = new LogIn_Fragment();

        //TODO initialize Fire Base

        // initialize Attributes
        initializeAttributes(view);

        setLogInBtn(view);
        setJoinUsBtn(view);

        return view;
    }

    private void setJoinUsBtn(View view) {
        buttonJoinUs =  view.findViewById(R.id.joinUsId);
        buttonJoinUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(signUpFragment);
            }
        });
    }

    private void setLogInBtn(View view) {
        buttonLogin =  view.findViewById(R.id.Loginbtn);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction().addToBackStack(null);// when push the back btn we go back to the previous fragment
        ft.replace(R.id.placeholder, fragment);
        ft.commit();
    }


    private void userLogin() {
        String email = editTextMail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            // email is empty
            Toast.makeText(getActivity(), "Please Enter Email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)) {
            // password is empty
            Toast.makeText(getActivity(), "Please Enter Password", Toast.LENGTH_SHORT).show();
        }
    }

    public void emailOrPassIncorrect() {
        Toast.makeText(getActivity(), "E-mail or Password incorrect", Toast.LENGTH_SHORT).show();
    }

    public void initializeAttributes(View view) {
        editTextMail =  view.findViewById(R.id.emailLoginId);
        editTextPassword =  view.findViewById(R.id.passLoginId);
    }
}