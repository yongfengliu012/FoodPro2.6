package xwang.george.foodpro2;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by george on 10/10/2017.
 */

public class AtyExerciseReminder extends Activity {
    private EditText todayGoalReminder, addExerciseForReminder, leftCaloriesForReminder;
    private Button btnAddExerciseForReminder, btnStartNewDay, btnSetGoalForReminder;
    private Double todayCalorieGoal = 0.0, exerciseAdded = 0.0, leftCalories = 0.0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_reminder);
        todayGoalReminder = (EditText) findViewById(R.id.todayGoalReminder);
        addExerciseForReminder = (EditText) findViewById(R.id.addExerciseForReminder);
        leftCaloriesForReminder = (EditText) findViewById(R.id.leftCaloriesForReminder);
        btnAddExerciseForReminder = (Button) findViewById(R.id.btnSubmitExercise);
        btnStartNewDay = (Button) findViewById(R.id.startNewDayForReminder);
        btnSetGoalForReminder = (Button) findViewById(R.id.btnSetGoalForReminder);
        btnSetGoalForReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!todayGoalReminder.getText().toString().equals("")){
                    todayCalorieGoal = Double.parseDouble(todayGoalReminder.getText().toString());
                    leftCalories = todayCalorieGoal;
                }
            }
        });
        btnAddExerciseForReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!addExerciseForReminder.getText().toString().equals("")){
                    exerciseAdded = exerciseAdded + Double.parseDouble(addExerciseForReminder.getText().toString());
                    leftCaloriesForReminder.setText((leftCalories-exerciseAdded) + "");
                }
            }
        });
        btnStartNewDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todayCalorieGoal = 0.0; exerciseAdded = 0.0; leftCalories = 0.0;
                todayGoalReminder.setText("");
                addExerciseForReminder.setText("");
                leftCaloriesForReminder.setText("");
            }
        });
    }
}
