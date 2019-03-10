package com.example.golan.train.Activities;

import com.example.golan.train.BL.User;

public interface fromFragmentToMainActivity {
    public void backFromJoinUsFragment(String mail,String password);
    public void backFromDetailsFragment(User user);
}
