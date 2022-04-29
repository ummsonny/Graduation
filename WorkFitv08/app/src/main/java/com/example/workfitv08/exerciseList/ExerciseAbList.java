package com.example.workfitv08.exerciseList;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.workfitv08.R;
import com.example.workfitv08.poseCamera.PoseEstimationMain;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class ExerciseAbList extends AppCompatActivity {

    Button select_pose_btn;
    Button camera_btn;
    Button cancel_pose_fragment_btn;
    View exercise_list_layout;
    View pose_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_list);

        exercise_list_layout = (View) findViewById(R.id.exercise_list_layout);
        pose_fragment = (View) findViewById(R.id.pose_fragment);

        select_pose_btn = (Button) findViewById(R.id.select_pose_btn);
        camera_btn = (Button) findViewById(R.id.camera_btn);
        cancel_pose_fragment_btn = (Button) findViewById(R.id.cancel_pose_fragment_btn);

        select_pose_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise_list_layout.setBackgroundColor(Color.parseColor("#80000000"));
                exercise_list_layout.setClickable(false);

                pose_fragment.setVisibility(View.VISIBLE);
                pose_fragment.setClickable(true);
            }
        });

        camera_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                exercise_list_layout.setBackgroundColor(Color.parseColor("#00000000"));
                exercise_list_layout.setClickable(true);

                pose_fragment.setVisibility(View.GONE);
                pose_fragment.setClickable(false);

                Intent intent = new Intent(getApplicationContext(), PoseEstimationMain.class);
                startActivity(intent);
            }
        });

        cancel_pose_fragment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exercise_list_layout.setBackgroundColor(Color.parseColor("#00000000"));
                exercise_list_layout.setClickable(true);

                pose_fragment.setVisibility(View.GONE);
                pose_fragment.setClickable(false);
            }
        });








    }


}
