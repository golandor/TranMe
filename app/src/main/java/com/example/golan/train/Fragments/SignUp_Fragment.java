package com.example.golan.train.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.golan.train.Interfaces.fromFragmentToMainActivity;
import com.example.golan.train.R;


public class SignUp_Fragment extends Fragment {

    private EditText et_editTextMail, et_editTextMailConfirm, et_editTextPassword, et_editTextPasswordConfirm;
    private String editTextMail, editTextMailConfirm, editTextPassword, editTextPasswordConfirm;
    private Button nextBtn;

    private SignUpDetails_Fragment detailsFragment;
    public fromFragmentToMainActivity listener;



    public SignUp_Fragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_, container, false);

        detailsFragment = new SignUpDetails_Fragment();

        initializeAttributes(view);

        setNextBtn(view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof fromFragmentToMainActivity) {
            //init the listener
            listener = (fromFragmentToMainActivity)context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement InteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void listenerTrigger() {
        this.listener.backFromJoinUsFragment(editTextMail,editTextPassword);
        setFragment(detailsFragment);
    }

    private void setNextBtn(View view) {
        nextBtn = view.findViewById(R.id.nextSignUpId);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validate()){
                    Toast.makeText(getActivity(), "Sign up has Failed",Toast.LENGTH_SHORT).show();
                }
                else {
//                    listenerTrigger();
                    setFragment(detailsFragment);
                }
            }
        });
    }
    private void setFragment(Fragment fragment){
        final Bundle bundle = new Bundle();
        bundle.putString("mail",editTextMail);
        bundle.putString("password:",editTextPassword);
        fragment.setArguments(bundle);
        FragmentTransaction ft = getFragmentManager().beginTransaction().addToBackStack(null);// when push the back btn we go back to the previous fragment
        ft.replace(R.id.placeholder,fragment);
        ft.commit();
    }


    public boolean validate(){
        boolean valid = true;

        //init strings
        editTextMail = et_editTextMail.getText().toString().trim();
        editTextMailConfirm = et_editTextMailConfirm.getText().toString().trim();
        editTextPassword = et_editTextPassword.getText().toString().trim();
        editTextPasswordConfirm = et_editTextPasswordConfirm.getText().toString().trim();

        if(editTextMail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(editTextMail).matches()){
            et_editTextMail.setError("Please Enter Valid Email Address");
            et_editTextMail.requestFocus();
            valid = false;
        }
        if(!editTextMailConfirm.equals(editTextMail)){
            et_editTextMailConfirm.setError("Email Confirm Does Not Match Email Address");
            valid = false;
        }
        if(editTextPassword.isEmpty() || editTextPassword.contains(" ") || editTextPassword.length()>9 || editTextPassword.length()<6){
            et_editTextPassword.setError("Please Enter Valid Password, 6-9 chars");
            et_editTextPassword.requestFocus();
            valid = false;
        }
        if(!editTextPasswordConfirm.equals(editTextPassword)){
            et_editTextPasswordConfirm.setError("Password Confirm Does Not Match Password");
            et_editTextPasswordConfirm.requestFocus();
            valid = false;
        }
        return valid;
    }


    public void initializeAttributes(View view){
        et_editTextMail = (EditText) view.findViewById(R.id.emailSignUpId);
        et_editTextMailConfirm = (EditText) view.findViewById(R.id.emailSignUpConfirmId);
        et_editTextPassword = (EditText) view.findViewById(R.id.passSignUpId);
        et_editTextPasswordConfirm = (EditText) view.findViewById(R.id.passSignUpCConfirmId);
    }
}
