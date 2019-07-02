package com.example.golan.train.Interfaces;

import com.example.golan.train.BL.User;

import org.json.JSONException;

public interface fromFragmentToMainActivity {
    public void backFromJoinUsFragment(String mail,String password);
    public void backFromDetailsFragment(User user) throws JSONException;
}
