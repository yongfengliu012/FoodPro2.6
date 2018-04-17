package xwang.george.foodpro2;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by george on 10/10/2017.
 */

public class AtyAddingNewFood extends ListActivity {
    private SimpleCursorAdapter cursorAdapter;
    private EditText foodname, caloriePerUnit, foodUnit;
    private Button btnAddNewFood;
    private FoodDB foodDB;
    private SQLiteDatabase dbReader, dbWriter;
    private Cursor addFoodCursor;
    private View.OnClickListener clickToAddNewFoodListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ContentValues newFoodValues = new ContentValues();
            newFoodValues.put("foodname", foodname.getText().toString());
            newFoodValues.put("calorie", (Double.parseDouble(caloriePerUnit.getText().toString()))/(Double.parseDouble(foodUnit.getText().toString())));
            newFoodValues.put("unit", "g");
            dbWriter.insert(FoodDB.FOOD_TABLE, null, newFoodValues);
            Toast.makeText(AtyAddingNewFood.this,"saved", Toast.LENGTH_SHORT).show();
            refreshData();
            foodname.setText("");
            caloriePerUnit.setText("");
            foodUnit.setText("");
        }
    };

    private void refreshData() {
        cursorAdapter.changeCursor(dbReader.query(FoodDB.FOOD_TABLE, null, null, null, null, null, null));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_new_food);
        foodDB = new FoodDB(this);
        dbReader = foodDB.getReadableDatabase();
        dbWriter = foodDB.getWritableDatabase();
        foodname = (EditText) findViewById(R.id.foodname);
        caloriePerUnit = (EditText) findViewById(R.id.caloriePerUnit);
        foodUnit = (EditText) findViewById(R.id.foodUnit);
        btnAddNewFood = (Button) findViewById(R.id.btnAddNewFood);
        addFoodCursor = dbReader.query(FoodDB.FOOD_TABLE, null, null, null, null, null, null);
        cursorAdapter = new SimpleCursorAdapter(this, R.layout.activity_add_new_food_list_view, addFoodCursor,
                new String[]{"foodname", "calorie", "unit"}, new int[]{R.id.textFoodName, R.id.textCaloriePerUnit, R.id.textFoodUnit});
        setListAdapter(cursorAdapter);
        btnAddNewFood.setOnClickListener(clickToAddNewFoodListener);
        getListView().setOnItemLongClickListener(listViewLongClickListener);
        refreshData();
    }
    private AdapterView.OnItemLongClickListener listViewLongClickListener =
            new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    new AlertDialog.Builder(AtyAddingNewFood.this).setTitle("Message").setMessage("You sure you want to delete this row?").setNegativeButton("No", null).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Cursor c = cursorAdapter.getCursor();
                            c.moveToPosition(position);
                            int itemId = c.getInt(c.getColumnIndex("_id"));
                            dbWriter.delete(FoodDB.FOOD_TABLE, "_id = ?", new String[]{"" + itemId});
                            refreshData();
                        }
                    }).show();
                    return true;
                }
            };
}

