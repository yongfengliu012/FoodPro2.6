package xwang.george.foodpro2;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by george on 10/10/2017.
 */

public class AtyDailyLog extends Activity {
    private EditText calorieNeedPerDay, foodWeight, breakfastCalorie, lunchCalorie, dinnerCalorie, totalCalorie;
    private Spinner mealSpinner, foodSpinner;
    private Button btnAddFoodForMeal, btnSubmitMeal, btnFinishDay;
    private Cursor userCursor, foodCursor;
    private ArrayAdapter<String> mealSpinnerAdapter, foodSpinnerAdapter;
    private FoodDB foodDb;
    private DayCalorieDB dayCalorieDB;
    private SQLiteDatabase foodDbReader, dayCalorieDBWriter;
    private String mealSelected = "Breakfast";
    private String foodSelected = "";
    private Double doubleCaloriesNeedPerDay = 0.0;
    private Double foodCaloriePerUnit = 0.0;
    //private Double foodSelectedCalorie = 0.0;
    private Double totalCalorieForDay = 0.0;
    private Double breakfastCalorieForDay = 0.0, lunchCalorieForDay=0.0, dinnerCalorieForDay=0.0;
    public static final String BREAKFAST="BREAKFAST";
    public static final String LUNCH="LUNCH";
    public static final String DINNER="DINNER";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_log);
        foodDb = new FoodDB(this);
        dayCalorieDB = new DayCalorieDB(this);
        dayCalorieDBWriter = dayCalorieDB.getWritableDatabase();
        foodDbReader = foodDb.getReadableDatabase();
        calorieNeedPerDay = (EditText) findViewById(R.id.calorieNeedPerDay);
        calorieNeedPerDay.setText(caculateCalorieNeedPerDay().toString());
        foodWeight = (EditText) findViewById(R.id.foodWeight);
        breakfastCalorie = (EditText) findViewById(R.id.breakfastCalorie);
        lunchCalorie = (EditText) findViewById(R.id.lunchCalorie);
        dinnerCalorie = (EditText) findViewById(R.id.dinnerCalorie);
        totalCalorie = (EditText) findViewById(R.id.totalCalorie);
        mealSpinner = (Spinner) findViewById(R.id.mealSpinner);
        mealSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        mealSpinner.setAdapter(mealSpinnerAdapter);
        mealSpinnerAdapter.add("Breakfast");
        mealSpinnerAdapter.add("Lunch");
        mealSpinnerAdapter.add("Dinner");

        mealSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mealSelected = mealSpinner.getItemAtPosition(position).toString();
                Toast.makeText(AtyDailyLog.this, "you selected " + mealSelected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        foodSpinner = (Spinner) findViewById(R.id.foodSpinner);
        foodSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        foodSpinner.setAdapter(foodSpinnerAdapter);

        foodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String foodItemSelected = foodSpinner.getItemAtPosition(position).toString();
                foodSelected = TextUtils.substring(foodItemSelected, 0, foodItemSelected.indexOf("/"));
                foodCaloriePerUnit = Double.parseDouble(foodItemSelected.substring(foodItemSelected.indexOf("/")+1));
                //foodCaloriePerUnit = Double.parseDouble(TextUtils.substring(foodItemSelected, foodItemSelected.indexOf("/"), -1));
                Toast.makeText(AtyDailyLog.this, "you selected food " + foodSelected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addFoodToFoodSpinnerAdapter();
        btnAddFoodForMeal = (Button) findViewById(R.id.btnAddFoodForMeal);
        btnAddFoodForMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // foodSelectedCalorie = foodSelectedCalorie + foodCaloriePerUnit * Double.parseDouble(foodWeight.getText().toString());
               // totalCalorieForDay = totalCalorieForDay + foodCaloriePerUnit * Double.parseDouble(foodWeight.getText().toString());
                if(!foodWeight.getText().toString().equals("")) {
                    if (mealSelected.toString().toUpperCase().equals(BREAKFAST)) {
                        breakfastCalorieForDay = breakfastCalorieForDay + foodCaloriePerUnit * Double.parseDouble(foodWeight.getText().toString());
                       // totalCalorieForDay = totalCalorieForDay + breakfastCalorieForDay;
                    } else if (mealSelected.toString().toUpperCase().equals(LUNCH)) {
                        lunchCalorieForDay = lunchCalorieForDay + foodCaloriePerUnit * Double.parseDouble(foodWeight.getText().toString());
                        //totalCalorieForDay = totalCalorieForDay + lunchCalorieForDay;
                    } else if (mealSelected.toString().toUpperCase().equals(DINNER)) {
                        dinnerCalorieForDay = dinnerCalorieForDay + foodCaloriePerUnit * Double.parseDouble(foodWeight.getText().toString());
                        //totalCalorieForDay = totalCalorieForDay + dinnerCalorieForDay;
                    }
                }
                foodWeight.setText("");
            }
        });
        btnSubmitMeal = (Button) findViewById(R.id.btnSubmitMeal);
        btnSubmitMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateCalorieToMealBox();
            }
        });
        btnFinishDay = (Button) findViewById(R.id.btnFinishDay);
        btnFinishDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndRefreshDataForDay();
            }
        });
    }

    private void saveAndRefreshDataForDay() {
        ContentValues calorieValues = new ContentValues();
        calorieValues.put("breakfast", breakfastCalorieForDay);
        calorieValues.put("lunch", lunchCalorieForDay);
        calorieValues.put("dinner", dinnerCalorieForDay);
        calorieValues.put("total", totalCalorieForDay);
        dayCalorieDBWriter.insert(DayCalorieDB.DAY_CALORIE_DB, null, calorieValues);
        Toast.makeText(AtyDailyLog.this, "day calories saved", Toast.LENGTH_SHORT).show();
        breakfastCalorie.setText("");
        lunchCalorie.setText("");
        dinnerCalorie.setText("");
        totalCalorie.setText("");
        totalCalorieForDay = 0.0;
        breakfastCalorieForDay = 0.0;
        lunchCalorieForDay = 0.0;
        dinnerCalorieForDay = 0.0;
    }

    private void populateCalorieToMealBox() {
        if(mealSelected.toUpperCase().equals(BREAKFAST)){
            breakfastCalorie.setText(breakfastCalorieForDay.toString());

            //foodSelectedCalorie = 0.0;
        }
        else if(mealSelected.toUpperCase().equals(LUNCH)){
            lunchCalorie.setText(lunchCalorieForDay.toString());

            //foodSelectedCalorie = 0.0;
        }
        else if(mealSelected.toUpperCase().equals(DINNER)){
            dinnerCalorie.setText(dinnerCalorieForDay.toString());

            //foodSelectedCalorie = 0.0;
        }
        totalCalorieForDay = breakfastCalorieForDay + lunchCalorieForDay + dinnerCalorieForDay;
        totalCalorie.setText(totalCalorieForDay.toString());
        foodWeight.setText("");
    }

    private void addFoodToFoodSpinnerAdapter() {
        foodCursor = foodDbReader.query(FoodDB.FOOD_TABLE, null, null, null, null, null, null);
        String foodname;
        Double foodCaloriePerUnit;
        while (foodCursor != null && foodCursor.moveToNext()) {
            foodname = foodCursor.getString(foodCursor.getColumnIndex("foodname"));
            foodCaloriePerUnit = foodCursor.getDouble(foodCursor.getColumnIndex("calorie"));
            foodSpinnerAdapter.add(foodname + "/" + foodCaloriePerUnit.toString());
        }
    }

    private Double caculateCalorieNeedPerDay(){
        userCursor = getContentResolver().query(UserDBContentProvider.USER_URI, null, null, null, null);
        int age = 0;
        Double weight = 0.0, height=0.0;
        String gender = "";
        if(userCursor!=null && userCursor.moveToFirst()){
            gender = userCursor.getString(userCursor.getColumnIndex("gender"));
            age = userCursor.getInt(userCursor.getColumnIndex("age"));
            weight = userCursor.getDouble(userCursor.getColumnIndex("weight"));
            height = userCursor.getDouble(userCursor.getColumnIndex("height"));
            if(gender.toUpperCase().equals("MALE")){

                doubleCaloriesNeedPerDay = 10*weight + 6.25 * height - 5*age +5;
            }else if(gender.toUpperCase().equals("FEMALE")){
                doubleCaloriesNeedPerDay = 10 * weight + 6.25 *height - 5 * age - 161;
            }
        }
        return doubleCaloriesNeedPerDay;
    }
}
