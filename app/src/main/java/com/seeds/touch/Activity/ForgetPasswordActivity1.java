package com.seeds.touch.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.seeds.touch.Management.Interface.PostTimeInterface;
import com.seeds.touch.R;
import com.seeds.touch.Server.Server;
import com.seeds.touch.Technical.Helper;

import java.io.IOException;

public class ForgetPasswordActivity1 extends AppCompatActivity {
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password1);
    }


}
