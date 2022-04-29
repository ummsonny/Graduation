package com.example.workfitv08.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.workfitv08.R;
import com.example.workfitv08.exerciseList.ExerciseAbList;
import com.example.workfitv08.exerciseList.ExerciseEntireList;
import com.example.workfitv08.exerciseList.ExerciseLowerList;

public class MainView extends AppCompatActivity {

    Button exercise_list_entire_btn;
    Button exercise_list_lower_btn;
    Button exercise_list_ab_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);


        exercise_list_entire_btn = (Button) findViewById(R.id.exercise_list_entire_btn);
        exercise_list_lower_btn = (Button) findViewById(R.id.exercise_list_lower_btn);
        exercise_list_ab_btn = (Button) findViewById(R.id.exercise_list_ab_btn);

        exercise_list_entire_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ExerciseEntireList.class);
                startActivity(intent);
            }
        });

        exercise_list_lower_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ExerciseLowerList.class);
                startActivity(intent);
            }
        });

        exercise_list_ab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ExerciseAbList.class);
                startActivity(intent);
            }
        });
    }

}
