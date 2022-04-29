package com.example.workfitv08.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.workfitv08.R;
import com.example.workfitv08.main.MainView;

public class Login extends AppCompatActivity {

    Button main_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);


        main_btn = (Button) findViewById(R.id.main_btn);
        main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainView.class);
                startActivity(intent);
            }
        });
    }

}
