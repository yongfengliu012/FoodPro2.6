package xwang.george.foodpro2;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SimpleCursorAdapter;

/**
 * Created by george on 10/10/2017.
 */

public class AtyMealAnalyzing extends ListActivity {
    private DayCalorieDB dayCalorieDB;
    private SQLiteDatabase dayCalorieDBReader;
    private Cursor mealAnalyzingCursor;
    private SimpleCursorAdapter cursorAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_analyzing);
        dayCalorieDB = new DayCalorieDB(this);
        dayCalorieDBReader = dayCalorieDB.getReadableDatabase();
        mealAnalyzingCursor = dayCalorieDBReader.query(DayCalorieDB.DAY_CALORIE_DB, null, null, null, null, null, null);
        cursorAdapter = new SimpleCursorAdapter(this, R.layout.activity_meal_analyzing_list_view, mealAnalyzingCursor,
                new String[]{"_id", "breakfast", "lunch", "dinner", "total"}, new int[]{R.id.idForDay, R.id.breakfastForDay, R.id.lunchForDay, R.id.dinnerForDay, R.id.totalForDay});
        setListAdapter(cursorAdapter);

    }
}
