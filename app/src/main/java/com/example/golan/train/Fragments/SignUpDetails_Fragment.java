package com.example.golan.train.Fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.golan.train.BL.User;
import com.example.golan.train.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import java.util.Calendar;

public class SignUpDetails_Fragment extends Fragment {

    private EditText et_fullName, et_weigh, et_height;
    private String editTextFullName;
    private double weigh, height;
    private Button regBtn;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private LogIn_Fragment logIn_fragment;
    private  String gender;
    private RadioGroup radio_group;
    private RadioButton genderRadioBtn;

    private FirebaseAuth mAuth;

    public SignUpDetails_Fragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_details_, container, false);
        logIn_fragment = new LogIn_Fragment();

        mDisplayDate = view.findViewById(R.id.birthDate);
        radio_group = view.findViewById(R.id.radio_group);


        initializeAttributes(view);
        setRegBtn(view);
        mDisplayDateListener();
        setmDisplayDateListener();

        return view;
    }
    private void setRegBtn(View view) {

        int rgs_id = radio_group.getCheckedRadioButtonId();
        genderRadioBtn = view.findViewById(rgs_id);
        gender = genderRadioBtn.getText().toString();

        regBtn = view.findViewById(R.id.registerId);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateReg()){
                    Toast.makeText(getActivity(), "Sign up has Failed",Toast.LENGTH_SHORT).show();
                }
                else {
                    User newUser = new User(editTextFullName,"userIdForNow", gender, weigh,height);
//                    onSignUpSuccess();
                }
            }
        });
    }



    private boolean validateReg() {
        boolean valid = true;
        editTextFullName = et_fullName.getText().toString().trim();
        //TODO fix editTextFullName.contains(("[0-9]+")))
        if(editTextFullName.isEmpty()|| et_fullName.length()>20 || editTextFullName.contains(("[0-9]+"))){
            et_fullName.setError("Please Enter Valid Name (Up To 20 Chars)");
            et_fullName.requestFocus();
            valid = false;
        }

        try{
            weigh = Double.parseDouble(et_weigh.getText().toString());
        }

        catch (NumberFormatException e){
            et_weigh.setError("Not a Valid Weigh");
            et_weigh.requestFocus();
            valid = false;
        }

        try{
            height = Double.parseDouble(et_height.getText().toString());
            if(!(et_height.getText().toString().contains("."))){
                et_height.setError("Not a Valid Height (Example pf valid Height: 1.75 )");
                et_height.requestFocus();
                valid = false;
            }
        }

        catch (NumberFormatException e){
            et_height.setError("Not a Valid height");
            et_height.requestFocus();            valid = false;
        }

        if(mDisplayDate.getText().toString().equals(mDisplayDate.getContext().getResources().getString(R.string.birth_date))){
            mDisplayDate.setError("You have to enter Birth Date");
            mDisplayDate.requestFocus();
            valid = false;
        }

        return valid;
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction ft = getFragmentManager().beginTransaction().addToBackStack(null);// when push the back btn we go back to the previous fragment
        ft.replace(R.id.placeholder,fragment);
        ft.commit();
    }

    private void mDisplayDateListener(){
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
    }
    private void setmDisplayDateListener(){

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(this.toString(), "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };
    }


    private void initializeAttributes(View view) {
        et_fullName =  view.findViewById(R.id.fullNameOnDetails);
        et_weigh =  view.findViewById(R.id.weighDetails);
        et_height =  view.findViewById(R.id.heightDetails);
    }


}